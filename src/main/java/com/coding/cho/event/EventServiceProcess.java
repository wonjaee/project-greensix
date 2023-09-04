package com.coding.cho.event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.coding.cho.category.CategoryEntity;
import com.coding.cho.common.domain.dto.S3UploadDTO;
import com.coding.cho.common.utils.FileUploadUtil;
import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.goods.GoodsImageEntity;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EventServiceProcess implements EventService {

	private final AmazonS3Client client;
	private final EventEntityRepository er;
	private final EventImageRepository ir;

	@Value("${cloud.aws.s3.bucket}")
	private String bucketName;
	@Value("${cloud.aws.s3.upload-path2}")
	private String uploadPath;
	@Value("${cloud.aws.s3.temp-path2}")
	private String tempPath;

	@Override
	public void save(EventSaveDTO dto) {

		EventEntity ee = er.save(EventEntity.builder().name(dto.getName()).content(dto.getContent()).build());
		int leg = dto.getBucketKey().length;
		for (int i = 0; i < leg; i++) {
			if (dto.getBucketKey()[i] == "")
				continue;
			String newUrl = FileUploadUtil.s3TempToSrc(client, bucketName, tempPath + dto.getNewName()[i],
					uploadPath + dto.getNewName()[i]);
			ir.save(EventImageEntity.builder().orgName(dto.getOrgName()[i]).newName(dto.getNewName()[i]).url(newUrl)
					.bucketKey(uploadPath + dto.getNewName()[i]).isDef(dto.getDef()[i]).event(ee).build());
			/* FileUploadUtil.s3TempToSrc(client, bucketName, tempPath+, uploadPath); */
		}
		FileUploadUtil.clearTemp(client, bucketName, tempPath);
	}

	@Override
	public Map<String, String> tempUpload(MultipartFile temp) {

		return FileUploadUtil.s3Upload(client, bucketName, tempPath, temp);
	}

	//
	@Override
	@Transactional
	public List<EventListDTO> list() {
		List<EventEntity> en = er.findAll();

		;

		return en.stream().map(em -> new EventListDTO(em)).collect(Collectors.toList());
		// return er.findAll().stream().map(en-> new EventListDTO(en))// 조회된 엔티티를
		// EventListDTO로 mapping //파일 용량이 큰경우 썸네일 기능을 사용하여
		// .collect(Collectors.toList());
	}

	@Override
	public ModelAndView eventList() {
		ModelAndView mv = new ModelAndView("index/event/event");
		mv.addObject("event", er.findAll());
		return mv;
	}

	@Override
	@Transactional
	public void detail(long no, Model model) {

		model.addAttribute("detail", new EventListDTO2(er.findById(no).orElseThrow()));

	}

	@Override
	public List<EventEntity> eventAllList() {

		return er.findAll();
	}

	@Override
	@Transactional
	public Page<EventEntity> eventList(Pageable pageable) {

		return er.findAll(pageable);
	}

	@Transactional
	@Override
	public void deleteProcess(long no) {
		EventEntity en = er.findById(no).orElseThrow();
		String bucketKey = en.getGie().get(0).getBucketKey();
		FileUploadUtil.delete(client, bucketName, bucketKey);
		en.getGie().forEach(ei -> ir.delete(ei));
		er.deleteById(no);
	}

	@Transactional
	@Override
	public void detailProcess(long no, Model model) {
		EventDetailDTO dto = er.findById(no).stream().map(ee -> new EventDetailDTO(ee)).findFirst().orElseThrow();

		model.addAttribute("detail", dto);

	}

	
	  @Transactional
	  @Override public void updateProcess(long no, EventUpdateDTO dto) {
	  EventEntity entity = er.findById(no).orElseThrow(); entity.update(dto);
	  List<EventImageEntity> list=entity.getGie(); 
	  for(int i=0; i<list.size();i++){
	  if(dto.getBucketKey()[i] == "")continue; 
	  String newUrl=FileUploadUtil.s3TempToSrc(client, bucketName,tempPath+dto.getNewName()[i], uploadPath+dto.getNewName()[i]);
	  FileUploadUtil.delete(client, bucketName, list.get(i).getBucketKey());
	  ir.save(list.get(i).update(dto, i ,newUrl,uploadPath)); 
	  } entity.update(dto);
	  FileUploadUtil.clearTemp(client, bucketName, tempPath);
	  
	  
	  }
	 
	

}
