package com.coding.cho.menu;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.cho.category.CategoryEntity;
import com.coding.cho.goods.GoodsEntity;

public interface MenuRepository extends JpaRepository<GoodsEntity, Long>{


	List<GoodsEntity> findByCategory(CategoryEntity categoryEntity);

}
