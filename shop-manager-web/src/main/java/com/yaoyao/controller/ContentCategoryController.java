package com.yaoyao.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yaoyao.common.pojo.EasyUITreeNode;
import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.content.service.ContentCategoryService;

@RestController
public class ContentCategoryController {

	@Resource
	private ContentCategoryService contentCategoryService;

	@GetMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}

	@PostMapping(value = "/content/category/creat")
	@ResponseBody
	public ShopResult addContentCategory(Long parentId, String name) {
		ShopResult result = contentCategoryService.addContentCategory(parentId, name);
		return result;
	}

	@PutMapping(value = "/content/category/update")
	@ResponseBody
	public ShopResult updateContentCategory(Long id, String name) {
		ShopResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}

	@DeleteMapping(value = "/content/category/delete")
	@ResponseBody
	public ShopResult deleteContentCategory(Long id) {
		ShopResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
}
