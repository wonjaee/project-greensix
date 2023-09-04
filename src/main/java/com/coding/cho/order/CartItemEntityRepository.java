package com.coding.cho.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coding.cho.goods.GoodsEntity;

public interface CartItemEntityRepository extends JpaRepository<CartItemEntity, Long> {

	Optional<CartItemEntity> findByCartAndGoods(CartEntity cart, GoodsEntity goods);

	void deleteByCartAndGoods(CartEntity cart, GoodsEntity goods);

	List<CartItemEntity> findByCart(CartEntity cart);

	void deleteByCart(CartEntity cart);


	

}
