package com.coding.cho.goods;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.coding.cho.goods.dto.GoodsListDTO;
import com.coding.cho.goods.dto.GoodsSaveDTO;
import com.coding.cho.goods.dto.GoodsUpdateDTO;
import com.coding.cho.goods.dto.SaleSaveDTO;
import com.coding.cho.goods.dto.GoodsDetailDTO;

public interface GoodsService {

	Map<String, String> uploadSummernoteImgProcess(MultipartFile file);

	void save(GoodsSaveDTO dto, SaleSaveDTO saledto);

	Map<String, String> tempUpload(MultipartFile temp);

	List<GoodsListDTO> list();

	void detailProcess(long no, Model model);

	void updateProcess(long no, GoodsUpdateDTO dto, SaleSaveDTO savedto);

	void deleteProcess(long no);

	void saleList(Model model);

	void hotItemList(Model model);

	

	

	

}
