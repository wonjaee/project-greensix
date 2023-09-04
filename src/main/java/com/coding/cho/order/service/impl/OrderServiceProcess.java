package com.coding.cho.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.coding.cho.common.domain.entity.MemberEntity;
import com.coding.cho.common.domain.entity.MemberEntityRepository;
import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.goods.GoodsEntityRepository;
import com.coding.cho.map.StoreEntity;
import com.coding.cho.order.CartEntity;
import com.coding.cho.order.CartEntityRepository;
import com.coding.cho.order.CartItemEntity;
import com.coding.cho.order.CartItemEntityRepository;
import com.coding.cho.order.OrderEntity;
import com.coding.cho.order.OrderEntityRepository;
import com.coding.cho.order.OrderItemEntity;
import com.coding.cho.order.OrderItemEntityRepository;
import com.coding.cho.order.OrderStatus;
import com.coding.cho.order.dto.OrderListDTO;
import com.coding.cho.order.service.OrderService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class OrderServiceProcess implements OrderService {
	
	private final CartItemEntityRepository ciRepo;
	private final CartEntityRepository cartRepo;
	private final MemberEntityRepository memRepo;
	private final OrderEntityRepository orderRepo;
	private final OrderItemEntityRepository oiRepo;
	private final GoodsEntityRepository gRepo;
	
	@Transactional
	@Override
	public void orderSaveProcess(String email, String uid) {
		
		MemberEntity member=memRepo.findAllByEmail(email);
		CartEntity cart=cartRepo.findByMember(member);
		StoreEntity store=cart.getStore();
		
		OrderEntity order=OrderEntity.builder().member(member).uid(uid).store(store).build().setStatus(OrderStatus.WAIT);
		
		orderRepo.save(order);
		
		List<CartItemEntity> cartItems=ciRepo.findByCart(cart);
		
		  for(int i=0; i<cartItems.size(); i++) { 
			  CartItemEntity cartItem=cartItems.get(i);
			  
			  oiRepo.save(OrderItemEntity.builder()
					  .goods(cartItem.getGoods())
					  .order(order) 
					  .count(cartItem.getCount())
					  .orderPrice(cartItem.getCount()*cartItem.getGoods().getPrice()) 
					  .build()); 
			  }
		 
		
		
		
	}

	@Override
	public void cartDeleteProcess(String email) {
		MemberEntity member=memRepo.findAllByEmail(email);
		CartEntity cart=cartRepo.findByMember(member);
		
		ciRepo.deleteByCart(cart);
		cartRepo.deleteByMember(member);
	}
	//주문 내역
	@Transactional
	@Override
	public void orderHistory(String name, Model model) {
		MemberEntity member=memRepo.findAllByEmail(name);
		List<OrderEntity> oe = orderRepo.findAllByMember(member);
		
		OrderListDTO orders =  new OrderListDTO();
		List<OrderListDTO> order =orders.toDto(oe, member);
		
		System.out.println(order);
		
		
		
		model.addAttribute("list",order);
	}

	
	

}