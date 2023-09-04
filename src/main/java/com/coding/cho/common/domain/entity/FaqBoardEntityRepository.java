package com.coding.cho.common.domain.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.cho.common.domain.dto.FaqBoardSaveDTO;

public interface FaqBoardEntityRepository extends JpaRepository<FaqBoardEntity, Long>{

	void save(FaqBoardSaveDTO dto);

	Page<FaqBoardEntity> findByTitleContaining(String searchKeyword, Pageable pageable);

}
