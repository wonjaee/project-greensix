package com.coding.cho.category;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class CategoryServiceProcess implements CategoryService {

	private final CategoryEntityRepository categoryRepository;
	
	
	//1차~4차까지 일괄처리
	@Override
	public void saveProcess(String[] name) {
		
		CategoryEntity parent=null;
		
		for(int i=0; i<name.length; i++) {
			String categornName=name[i];
			if(categornName=="" || categornName==null)continue;
			
			//이미존재하는지 확인
			Optional<CategoryEntity> result= categoryRepository.findByNameAndParent(categornName, parent);
			if(result.isPresent()) {
				parent=result.get();//이미존재하는 카테고리는 다음으로
				log.debug(">>>이미 존재하는 카테고리 : "+parent.getName());
				continue;
			}
			
			//카테고리 저장
			CategoryEntity entity=CategoryEntity.builder()
			.name(categornName)
			.parent(parent)//최초저장은 1차 null적용
			.build();
			
			parent=categoryRepository.save(entity);
			log.debug(">>>카테고리 신규저장 : "+parent.getName());
		}
		
	}


	@Override
	public void listProcess(long no, Model model) {
		if(no==0)model.addAttribute("list", categoryRepository.findByParentIsNull());
		else model.addAttribute("list", categoryRepository.findByParent(categoryRepository.findById(no).orElseThrow()));
		
	}

	//카테고리 불러오기
	@Override
	public void listCategory(Model model) {
		List<CategoryEntity> result= categoryRepository.findAll();
		model.addAttribute("list",result);
		
	}

	//카테고리 삭제
	@Transactional
	@Override
	public void deleteCategory(long no) {
		categoryRepository.deleteById(no);;
	}

	//카테고리 수정
	@Transactional
	@Override
	public void updateByNoAndName(long no, String name) {
		categoryRepository.findById(no)
					.map(cate->cate.updateName(name))
					//JPA에서는 session유지된상황(@Transactional) 에서 Entity가 수정되면 update 실행
					.orElseThrow();
	}




	




	


	
	

}
