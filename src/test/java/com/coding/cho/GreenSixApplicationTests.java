package com.coding.cho;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.coding.cho.common.domain.entity.FaqBoardEntityRepository;
import com.coding.cho.common.domain.entity.FaqDivision;
import com.coding.cho.common.domain.entity.FaqEntity;
import com.coding.cho.common.domain.entity.FaqEntityRepository;
import com.coding.cho.common.domain.entity.MemberEntity;
import com.coding.cho.common.domain.entity.MemberEntityRepository;
import com.coding.cho.common.domain.entity.MyRole;
import com.coding.cho.common.utils.HtmlEscapeUtils;
import com.coding.cho.goods.GoodsEntity;
import com.coding.cho.map.StoreEntity;
import com.coding.cho.map.StoreEntityRepository;
import com.coding.cho.order.CartEntity;
import com.coding.cho.order.CartItemEntity;
import com.coding.cho.order.CartItemEntityRepository;

@SpringBootTest
class GreenSixApplicationTests {
	
	@Autowired
	FaqEntityRepository faq;
	
	@Autowired
	MemberEntityRepository mrp;
	
	@Autowired
	CartItemEntityRepository ciRepo;
	
	@Autowired
	StoreEntityRepository sr;
	
	@Autowired
	FaqBoardEntityRepository faqBoard;
	
	//@Test
	void 카드담기(){
		ciRepo.save(CartItemEntity.builder()
				.cart(CartEntity.builder().no(476).build())
				.goods(GoodsEntity.builder().no(12).build())
				.count(2)
				.build());
	}
	
	//@Test
	void 가맹점넣기() {
		sr.save(StoreEntity.builder()
				.name("강남")
				.engName("gangnam")
				.callNumber("02-3481-1005")
				.faxNumber("02-3481-9122")
				.address("서울 강남구 테헤란로5길 24, 장연빌딩/ 3~7층")
				.build());
	}
	
	//@Test
	void 테스트() {
		String title="<a href='#'>안녕하세요--</a>";
		
		String result=HtmlEscapeUtils.escapeHtml(title);
		System.out.println(result);
	}

	//@Test
	void contextLoads() {
		
		//*
		for(FaqDivision div :FaqDivision.values()) {
			
			IntStream.rangeClosed(1, 20).forEach(i->{
				faq.save(FaqEntity.builder()
						.division(div)
						.question(div.getKoDiv()+" 질문 "+i)
						.answer(div.getKoDiv()+" 답 "+i)
						.build());
			});
			
		}
		//*/
	}
	
	
	
	//@Test
	void 가맹점() {
		
		
		
	}
	@Autowired
	PasswordEncoder encoder;
	
	//@Test
	void 어드민계정() {
		mrp.save(MemberEntity.builder().email("대구").pass(encoder.encode("1234")).name("대구점").build().addRole(MyRole.STORE));
		mrp.save(MemberEntity.builder().email("울산").pass(encoder.encode("1234")).name("울산점").build().addRole(MyRole.STORE));
		mrp.save(MemberEntity.builder().email("부산").pass(encoder.encode("1234")).name("부산점").build().addRole(MyRole.STORE));
		mrp.save(MemberEntity.builder().email("부산경성대").pass(encoder.encode("1234")).name("부산경성대점").build().addRole(MyRole.STORE));
		mrp.save(MemberEntity.builder().email("광주첨단").pass(encoder.encode("1234")).name("광주첨단점").build().addRole(MyRole.STORE));
		mrp.save(MemberEntity.builder().email("그린아이티").pass(encoder.encode("1234")).name("그린아이티점").build().addRole(MyRole.STORE));
		
		
		
	}

}
