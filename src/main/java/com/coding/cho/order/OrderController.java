package com.coding.cho.order;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coding.cho.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OrderController {

	private final OrderService service;
	
	@ResponseBody
	@PostMapping("/cart/orderSave")
	public ResponseEntity<Boolean> orderSave(Authentication auth,@RequestParam String uid){
		service.orderSaveProcess(auth.getName(), uid);
		return ResponseEntity.ok().body(true);
	}
	
	@ResponseBody
	@DeleteMapping("/cart/del")
	public ResponseEntity<Boolean> cartDelete(Authentication auth){
		service.cartDeleteProcess(auth.getName());
		return ResponseEntity.ok().body(true);
	}
	
	//결제 내역 이동하고 뿌릴거임
	@GetMapping("/cart/history")
	public String orderHistory(Authentication auth,Model model) {
		service.orderHistory(auth.getName(),model);
		return "order/orderHistory";
	}
}
