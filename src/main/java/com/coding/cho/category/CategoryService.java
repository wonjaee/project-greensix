package com.coding.cho.category;

import org.springframework.ui.Model;

public interface CategoryService {

	void saveProcess(String[] name);

	void listProcess(long no, Model model);

	void listCategory(Model model);

	void deleteCategory(long no);

	void updateByNoAndName(long no, String name);

}
