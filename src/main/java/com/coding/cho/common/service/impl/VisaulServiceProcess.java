package com.coding.cho.common.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.coding.cho.common.domain.dto.visual.VisualDTO;
import com.coding.cho.common.domain.entity.VisualEntityRepository;
import com.coding.cho.common.service.VisaulService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class VisaulServiceProcess implements VisaulService {

	private final VisualEntityRepository repo;
	
	@Override
	public void visaulListProcess(Model model) {
		List<VisualDTO> result= repo.findAll().stream()
		.map(VisualDTO :: new )
		.collect(Collectors.toList());
		
		model.addAttribute("list", result);
		
	}

}
