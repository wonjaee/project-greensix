package com.coding.cho.order.service;

import org.springframework.ui.Model;

public interface CartService {

	void cartList(String email, Model model);

	boolean saveProcess(String name, long gno);

	void storeList(Model model, String url);

	void storeSelect(String name, String store);

	void countDownProcess(String name, long gno);

	void itemDeleteProcess(String email, long gno);

}
