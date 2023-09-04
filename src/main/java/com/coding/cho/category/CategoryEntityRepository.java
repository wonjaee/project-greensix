package com.coding.cho.category;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryEntityRepository extends JpaRepository<CategoryEntity, Long>{
	
	//최상위카테고리 목록
	List<CategoryEntity> findByParentIsNull();
	
	//하위카테고리 목록
	List<CategoryEntity> findByParent(CategoryEntity parent);

	Optional<CategoryEntity> findByNameAndParent(String categornName, CategoryEntity parent);
	
	//카테고리 삭제
	//void deleteByNo(long no);
	
	
	//카테고리 수정
	/*
	@Modifying
	@Query("UPDATE CategoryEntity c SET c.name = :name WHERE c.no = :no")
	void updateByNoAndName(@Param("no") long no, @Param("name") String name);
	*/

}
