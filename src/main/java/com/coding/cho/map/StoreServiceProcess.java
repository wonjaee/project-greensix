package com.coding.cho.map;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.coding.cho.common.domain.entity.MemberEntityRepository;





@Service
public class StoreServiceProcess implements StoreService {
	
	@Autowired
	private StoreEntityRepository repo;
	
	@Autowired
	private MemberEntityRepository mr;
	
	//업체 페이지별 리스트(10개씩)
	@Override
	public Page<StoreEntity> storeList(Pageable pageable) {
		return repo.findAll(pageable);
	}
	//키워드 검색
	@Override
	public Page<StoreEntity> storeSearchList(String searchKeyword, Pageable pageable) {
		return repo.findByNameContaining(searchKeyword, pageable);
	}
	
	//업체 전체 리스트
	@Override
	public List<StoreEntity> storeAllList() {
		
		return repo.findAll();
	}
	//특정 업체 상세보기
	@Override
	public StoreEntity storeDetail(Long no) {
		
		return repo.findById(no).get();
				
	}
	
	
	
	
	
}
