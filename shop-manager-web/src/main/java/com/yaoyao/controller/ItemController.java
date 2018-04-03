package com.yaoyao.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yaoyao.common.pojo.EasyUIDataGridResult;
import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.pojo.TbItem;
import com.yaoyao.service.ItemService;

@RestController
public class ItemController {
	
	@Resource
	private ItemService itemService;
	
	@GetMapping(value = "/item/{ItemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long ItemId) {
		TbItem tbItem = itemService.getItemById(ItemId);
		return tbItem;
	}
	
	@GetMapping(value = "item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(int page,int rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
	@PostMapping(value = "/item/save")
	@ResponseBody
	public ShopResult addItem(TbItem tbItem,String desc) {
		try {
			ShopResult result = itemService.createItem(tbItem, desc);
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return ShopResult.build(500, "添加商品失败");
		}
	}

}
