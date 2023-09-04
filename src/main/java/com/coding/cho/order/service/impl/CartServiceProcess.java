package com.coding.cho.order.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.coding.cho.common.domain.entity.MemberEntity;
import com.coding.cho.common.domain.entity.MemberEntityRepository;
import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.goods.GoodsEntityRepository;
import com.coding.cho.map.StoreDTO;
import com.coding.cho.map.StoreEntityRepository;
import com.coding.cho.order.CartEntity;
import com.coding.cho.order.CartEntityRepository;
import com.coding.cho.order.CartItemEntity;
import com.coding.cho.order.CartItemEntityRepository;
import com.coding.cho.order.dto.CartDTO;
import com.coding.cho.order.service.CartService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CartServiceProcess implements CartService {
	
	private final CartItemEntityRepository ciRepo;
	private final CartEntityRepository cartRepo;
	private final MemberEntityRepository memRepo;
	private final GoodsEntityRepository goodsRepo;
	private final StoreEntityRepository storeRepo;
	
	private CartEntity createCart(String email){
		return cartRepo.findByMember_email(email)
				//orElseGet(안쪽내용은 존재하지 않는경우에만 쿼리가 실행함.)
				.orElseGet(()->cartRepo.save(CartEntity.builder()
						.member(memRepo.findByEmail(email).orElseThrow())
						.build()));
	}
	
	@Transactional
	@Override
	public void cartList(String email, Model model) {
		
		model.addAttribute("cart", new CartDTO(createCart(email)));
		//성민 추가
		MemberEntity user=memRepo.findAllByEmail(email);
		model.addAttribute("user", user);
		//
	}

	//카드에담기
	@Transactional
	@Override
	public boolean saveProcess(String email, long gno) {
		//카트가 존재하는경우 카드생성
		CartEntity cart=createCart( email);
		if(cart.getStore()==null) {
			return false;
		}else {
			GoodsEntity goods=goodsRepo.findById(gno).orElseThrow();
			
			ciRepo.findByCartAndGoods(cart, goods)
				.ifPresentOrElse(
				//이미 저장된 상품이면 count++
				ent->ent.addCount(1), 
				//존재하지 않으면 카드에 저장
				()->ciRepo.save(CartItemEntity.builder()
						.cart(cart)
						.goods(goods)
						.count(1)
						.build()
						));
			return true;
		}
		
		
		
	}
	@Transactional
	@Override
	public void countDownProcess(String name, long gno) {
		
		CartEntity cart=createCart( name);
		GoodsEntity goods=goodsRepo.findById(gno).orElseThrow();
		
		CartItemEntity cie = ciRepo.findByCartAndGoods(cart, goods).get();
		if(cie.getCount()>1) {
			cie.deleteCount(1);
		}else {
			ciRepo.delete(cie);
		}
	
	}

	@Override
	public void storeList(Model model,String url) {
		model.addAttribute("url", url);
		model.addAttribute("list", storeRepo.findAll().stream().map(StoreDTO::new).collect(Collectors.toList()));
	}

	@Transactional
	@Override
	public void storeSelect(String email,String store) {
		CartEntity cart=cartRepo.findByMember_email(email).orElseThrow();
		cart.updateStore(storeRepo.findByName(store).orElseThrow());
		
	}

	@Transactional
	@Override
	public void itemDeleteProcess(String email, long gno) {
		CartEntity cart=cartRepo.findByMember_email(email).orElseThrow();
		GoodsEntity goods=goodsRepo.findById(gno).orElseThrow();
		
		ciRepo.deleteByCartAndGoods(cart, goods);
	}

}
