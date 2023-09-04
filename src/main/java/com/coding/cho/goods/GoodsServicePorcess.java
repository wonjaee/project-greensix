package com.coding.cho.goods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.coding.cho.category.CategoryEntity;
import com.coding.cho.category.CategoryEntityRepository;
import com.coding.cho.common.utils.FileUploadUtil;
import com.coding.cho.goods.dto.GoodsListDTO;
import com.coding.cho.goods.dto.GoodsSaveDTO;
import com.coding.cho.goods.dto.GoodsUpdateDTO;
import com.coding.cho.goods.dto.SaleListDTO;
import com.coding.cho.goods.dto.SaleSaveDTO;
import com.coding.cho.goods.dto.GoodsDetailDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GoodsServicePorcess implements GoodsService {

	private final AmazonS3Client client;
	private final GoodsEntityRepository gr;
	private final CategoryEntityRepository cr;
	private final GoodsImageEntityRepository ir;
	private final SaleEntityRepository salerepo;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	@Value("${cloud.aws.s3.temp-path}")
	private String path;
	@Value("${cloud.aws.s3.upload-path}")
	private String path2;

	@Override
	public Map<String, String> uploadSummernoteImgProcess(MultipartFile file) {
		return FileUploadUtil.s3Upload(client, bucketName, path, file);
	}

	@Override
	public void save(GoodsSaveDTO dto,SaleSaveDTO saledto) {

		// 1. 상품정보 저장
		GoodsEntity ge = gr
				.save(GoodsEntity.builder().name(dto.getName()).price(dto.getPrice()).content(dto.getContent())
						.hotItem(dto.isHotItem()).onSale(dto.isOnSale()).category(cr.findById(dto.getCategory()).orElseThrow()).build());
		int leg = dto.getBucketKey().length;
		for (int i = 0; i < leg; i++) {
			if (dto.getBucketKey()[i] == "")
				continue;
			String newUrl=FileUploadUtil.s3TempToSrc(client, bucketName, path+dto.getNewName()[i], path2+dto.getNewName()[i]);
			ir.save(GoodsImageEntity.builder().orgName(dto.getOrgName()[i]).newName(dto.getNewName()[i])
					.url(newUrl).bucketKey(path2+dto.getNewName()[i]).isDef(dto.getDef()[i]).goods(ge).build());
		}
		FileUploadUtil.clearTemp(client, bucketName, path);
		
		if(dto.isOnSale()) {
			salerepo.save(saledto.toEntity().goods(ge));
		}
	}

	@Override
	public Map<String, String> tempUpload(MultipartFile temp) {
		return FileUploadUtil.s3Upload(client, bucketName, path, temp);
	}

	@Override
	@Transactional
	public List<GoodsListDTO> list() {
		return gr.findAll().stream().map(GoodsListDTO::new)// 조회된 엔티티를 GoodsListDTO로 mapping //파일 용량이 큰경우 썸네일 기능을 사용하여
				.collect(Collectors.toList());
	}
	
	
	@Transactional
	@Override
	public void hotItemList(Model model) {
		model.addAttribute("list", gr.findByHotItem(true).stream()
										.limit(4)
										.map(GoodsListDTO::new)
										.collect(Collectors.toList()));
		
	}

	@Override
	@Transactional
	public void detailProcess(long no, Model model) {
		GoodsDetailDTO dto = gr.findById(no)
				.map(GoodsDetailDTO::new)
				.orElseThrow();
		model.addAttribute("detail", dto);
	}

	@Transactional
	@Override
	public void updateProcess(long no, GoodsUpdateDTO dto,SaleSaveDTO savedto) {

		GoodsEntity entity = gr.findById(no).orElseThrow();
		CategoryEntity category = cr.findById(dto.getCategory()).orElseThrow();
		List<GoodsImageEntity> list=entity.getGie();
		
		//이미지 수정안했을때 이미지 작업 X 처리
		//이미지 수정안했을때 - form 안에 BucketKey length 0
		//이미지 수정하면 length 1
		
			for(int i=0; i<list.size();i++) {
				if (dto.getBucketKey() == "")continue;
				String newUrl=FileUploadUtil.s3TempToSrc(client, bucketName, path+dto.getNewName()[i], path2+dto.getNewName()[i]);
				FileUploadUtil.delete(client, bucketName, list.get(i).getBucketKey());
				ir.save(list.get(i).update(dto, i ,newUrl,path2));
			}
		
			entity.update(dto, category);
		
		//????????
		if(entity.isOnSale()) {
			// 이미 존재하는 경우, 새로 생성할경우
			salerepo.findByGoods(entity).ifPresentOrElse(
					ent->ent.updateSale(savedto),//존재하면 update
					()->salerepo.save(savedto.toEntity().goods(entity)));//새로운 저장
			
		}
		
		FileUploadUtil.clearTemp(client, bucketName, path);
	}

	@Transactional
	@Override
	public void deleteProcess(long no) {
		GoodsEntity entity=gr.findById(no).orElseThrow();
		String bucketKey=entity.getGie().get(0).getBucketKey();
		FileUploadUtil.delete(client, bucketName, bucketKey);
		entity.getGie().forEach(en->ir.delete(en));
		//SaleEntity saEntity= salerepo.findByGnoNo(no).orElseThrow();
		salerepo.deleteByGoodsNo(no);
		gr.deleteById(no);
	}

	@Transactional
	@Override
	public void saleList(Model model) {
		
		LocalDateTime today= LocalDateTime.now();
		//DateTimeFormatter fomatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//String formattedToday=today.format(fomatter);
		/*
		 * Sort sort=Sort.by(Direction.ASC, "startDate"); Pageable
		 * pageable=PageRequest.of(0, 4, sort);
		 */
		System.out.println("========================");
		//List<GoodsEntity> result=gr.findByOnSaleIsTrueAndSale_startDateLessThanEqualAndSale_endDateGreaterThanEqualOrOnSaleIsTrueAndSale_startDateIsNull(today,today);
		//List<GoodsEntity> result=gr.findByOnSaleIsTrueAndSale_startDateIsNullOrOnSaleIsTrueAndSale_startDateLessThanEqualAndSale_endDateGreaterThanEqual(today,today);
		List<GoodsEntity> result=gr.findByOnsaleToday(today);

				// 오늘 판매 중인 상품 조회
		System.out.println("========================");
		result.forEach(System.out::println);
		model.addAttribute("list", result.stream()
				//.filter(goods->goods.getSale().getStartDate().isBefore(today) &&goods.getSale().getEndDate().isAfter(today))
				.map(GoodsListDTO::new)
				.limit(4)
				.collect(Collectors.toList()));
		//*/
		
		
	}

	

	
	

}
