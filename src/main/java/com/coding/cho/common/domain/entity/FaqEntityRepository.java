package com.coding.cho.common.domain.entity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FaqEntityRepository extends JpaRepository<FaqEntity, Long>{

	List<FaqEntity> findByDivision(FaqDivision divsion);
	
	Page<FaqEntity> findByDivision(FaqDivision divsion, Pageable pageable);

	//Page<FaqEntity> findByQuestionContaining(String search, Pageable pageable);




	Page<FaqEntity> findByQuestionOrAnswerContaining(String search, String search2, Pageable pageable);

	Page<FaqEntity> findByQuestionContainingOrAnswerContaining(String search, String search2, Pageable pageable);

	//List<FaqEntity> findByQuestionContaining(String search);


}
