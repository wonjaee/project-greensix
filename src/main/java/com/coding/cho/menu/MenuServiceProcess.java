package com.coding.cho.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.coding.cho.category.CategoryEntity;
import com.coding.cho.category.CategoryEntityRepository;
import com.coding.cho.goods.GoodsEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MenuServiceProcess implements MenuService{
	
	private final GoodsEntityRepository gr;
	private final CategoryEntityRepository cr;
	
	
	@Override
	@Transactional
	public void goodsList(long categoryNo,Model model) {
		
		if(categoryNo==0) {  //카테고리 0번(메뉴 클릭시) 전체 상품 검색
			List<MenuListDTO> result= gr.findAll().stream().map(MenuListDTO::new) //findAll로 전체 검색
					.collect(Collectors.toList());
			model.addAttribute("list", result);
		}
		else { //0번 아닐경우
			CategoryEntity ce=cr.findById(categoryNo).orElseThrow(); //categoryNo로 검색해서 엔티티에 담기
			
			if(ce.getParent()==null) {   //부모카테고리가 없을경우 하위카테고리 재검색
				List<MenuListDTO> result=new ArrayList<>();	 //리스트 생성		
				for(int i=0; i<ce.getChildren().size(); i++) {	//하위카테고리 개수만큼 반복				
					List<MenuListDTO> result2=gr.findByCategory(ce.getChildren().get(i)).stream().map(MenuListDTO::new)
							.collect(Collectors.toList());   //하위카테고리 검색후 result2에 담기
					result2.forEach(ef->result.add(ef));   //result에 추가		
				}				
				model.addAttribute("list", result);
							
			}else {  //부모카테고리가 있을경우 바로 담기
				List<MenuListDTO> result= gr.findByCategory(cr.findById(categoryNo).orElseThrow()).stream().map(MenuListDTO::new)// 조회된 엔티티를 GoodsListDTO로 mapping //파일 용량이 큰경우 썸네일 기능을 사용하여
						.collect(Collectors.toList());
				model.addAttribute("list", result);				
			}
		}
	}




}
