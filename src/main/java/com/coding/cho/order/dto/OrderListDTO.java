package com.coding.cho.order.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;

import com.coding.cho.common.domain.entity.MemberEntity;
import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.order.CartItemEntity;
import com.coding.cho.order.OrderEntity;
import com.coding.cho.order.OrderItemEntity;
import com.coding.cho.order.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderListDTO {
	
	private long no;
	private LocalDateTime orderDate;
	private String uid;
	private OrderStatus orderStatus;
	private MemberEntity member; 
	private List<OrderItemEntity> orderItems;
	
	
	public OrderListDTO(List<OrderEntity> orders) {
		for(OrderEntity order : orders) {
			this.no=order.getNo();
		}
	}
	public List<OrderListDTO> toDto(List<OrderEntity> orders,MemberEntity member) {
		List<OrderListDTO> orderDto=new ArrayList<>();
		for(OrderEntity order : orders) {
			OrderListDTO orderDto2 = new OrderListDTO();
			orderDto2.setOrderDate(order.getOrderDate());
	        orderDto2.setUid(order.getUid());
	        orderDto2.setOrderStatus(order.getOrderStatus());
	        orderDto2.setMember(member);
	        orderDto2.setOrderItems(order.getOrderItems());
			orderDto.add(orderDto2);
		}
		return orderDto;
	}
	
}
