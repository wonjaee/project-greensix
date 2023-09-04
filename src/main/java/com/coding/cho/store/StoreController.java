package com.coding.cho.store;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coding.cho.map.StoreService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class StoreController {
	
	private final FranchiseeService fs;
	
	@ResponseBody
	@PutMapping("/store")
	public void Store(@RequestParam long storeNo, @RequestParam boolean status) {
		fs.updateStatus(storeNo,status );
	}
	
}
