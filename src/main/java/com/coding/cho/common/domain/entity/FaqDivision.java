package com.coding.cho.common.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FaqDivision {
	
	USE(1,"use","카페 이용" ,"local_cafe.svg"),            //0
	POINT(2,"point","포인트","point.svg"),           //1
	MEMBER(3,"member","회원","member.svg"),          //2
	MEMBERSHIP(4,"membership","멤버쉽","membership.svg"), //3
	BENEFIT(5,"benefit","할인혜택","benefit.svg"),      //4
	COUPON(6,"coupon","쿠폰","coupon.svg"),           //5
	STORE(7,"store","가맹점","store.svg");            //6
	
	final int no;
	final String lower;
	final String koDiv;
	final String icon;

}
