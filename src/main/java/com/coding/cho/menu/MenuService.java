package com.coding.cho.menu;

import java.util.List;

import org.springframework.ui.Model;

import com.coding.cho.goods.dto.GoodsListDTO;

public interface MenuService {

	void goodsList(long categoryNo, Model model);

	
}
