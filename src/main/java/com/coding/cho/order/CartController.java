package com.coding.cho.order;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coding.cho.order.service.CartService;
import com.coding.cho.order.service.impl.CartServiceProcess;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CartController {
	
	private final CartService service;

	@GetMapping("/cart")
	public String cart(Authentication auth, Model model) {
		
		service.cartList(auth.getName(), model);
		return "cart/list";
	}
	@GetMapping("/cart/store")
	public String store(Model model,HttpServletRequest request) {
		String url=request.getHeader("Referer");
		service.storeList(model,url);
		return "cart/store";
	}
	@PostMapping("/cart/store")
	public String store(Authentication auth, String store,String url) {
		service.storeSelect(auth.getName(),store);
		System.out.println(url);
		return "redirect:"+url;
	}
	
	//비동기 카드담기
	@ResponseBody
	@PostMapping("/cart")
	public ResponseEntity<Boolean> save(Authentication auth, long gno) {
		
		return ResponseEntity.ok().body(service.saveProcess(auth.getName(), gno));
	}
	@ResponseBody
	@PostMapping("/cart/countDown")
	public ResponseEntity<Boolean> countDown(Authentication auth, long gno) {
		service.countDownProcess(auth.getName(), gno);
		return ResponseEntity.ok().body(true);
	}
	//장바구니 상품 삭제
	@ResponseBody
	@DeleteMapping("/cart/delete/{gno}")
	public ResponseEntity<Boolean> deleteItem(Authentication auth, @PathVariable long gno) {
		service.itemDeleteProcess(auth.getName(), gno);
		return ResponseEntity.ok().body(true);
	}
	
}
