package com.coding.cho.map;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

public interface StoreService {

	Page<StoreEntity> storeList(Pageable pageable);

	Page<StoreEntity> storeSearchList(String searchKeyword, Pageable pageable);

	List<StoreEntity> storeAllList();

	StoreEntity storeDetail(Long no);

	

	

	

}
