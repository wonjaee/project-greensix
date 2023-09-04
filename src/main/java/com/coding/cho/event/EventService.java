package com.coding.cho.event;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.coding.cho.goods.dto.GoodsListDTO;

public interface EventService {

	void save(EventSaveDTO dto);

	Map<String, String> tempUpload(MultipartFile temp);

	List<EventListDTO> list();

	ModelAndView eventList();

	void detail(long no, Model model);

	List<EventEntity> eventAllList();

	Page<EventEntity> eventList(Pageable pageable);

	void deleteProcess(long no);

	void detailProcess(long no, Model model);

	void updateProcess(long no, EventUpdateDTO dto);

	

}
