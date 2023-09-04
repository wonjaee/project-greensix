package com.coding.cho.goods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.coding.cho.category.CategoryEntity;
import com.coding.cho.goods.dto.GoodsSaveDTO;


public interface GoodsEntityRepository extends JpaRepository<GoodsEntity, Long>{

	List<GoodsEntity> findByCategory(CategoryEntity categoryEntity);


	List<GoodsEntity> findByOnSale(boolean onSale);

	List<GoodsEntity> findByHotItem(boolean hotItem);


	List<GoodsEntity> findByOnSaleAndSale_startDateLessThanEqualAndSale_endDateGreaterThanEqual(boolean b,
			LocalDateTime today, LocalDateTime today2);


	List<GoodsEntity> findByOnSaleAndSale_startDateIsNull(boolean b);


	List<GoodsEntity> findByOnSaleIsTrueAndSale_startDateLessThanEqualAndSale_endDateGreaterThanEqualOrOnSaleIsTrueAndSale_startDateIsNull(
			LocalDateTime today,LocalDateTime today2);


	List<GoodsEntity> findByOnSaleIsTrueAndSale_startDateIsNullOrOnSaleIsTrueAndSale_startDateLessThanEqualAndSale_endDateGreaterThanEqual(
			LocalDateTime today, LocalDateTime today2);
	

    
	
	//jpql 엔티티를 기준으로 처리
	@Query("SELECT g FROM GoodsEntity g "
			+ "JOIN g.sale s "
			+ "WHERE g.onSale = true "
			+ "AND ((s.startDate <= :today AND s.endDate >= :today) "
			+ "       OR (s.startDate IS NULL AND s.endDate IS NULL))")
	List<GoodsEntity> findByOnsaleToday(@Param("today") LocalDateTime today);



	




}
