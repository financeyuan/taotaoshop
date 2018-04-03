package com.yaoyao.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yaoyao.common.pojo.EasyUITreeNode;
import com.yaoyao.service.ItemCatService;

@Controller
public class ItemCatController {
	
	@Resource
	private ItemCatService itemCatService;
	
	@PostMapping(value = "/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name = "id",defaultValue = "0") Long parentId){
		List<EasyUITreeNode> itemCatList = itemCatService.getItemCatList(parentId);
		return itemCatList;
	}
}
