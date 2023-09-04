package com.coding.cho.order.service;

import org.springframework.ui.Model;

public interface OrderService {

	void orderSaveProcess(String email, String uid);

	void cartDeleteProcess(String email);

	void orderHistory(String name, Model model);

}
