package com.yaoyao.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yaoyao.common.pojo.EasyUIDataGridResult;
import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.content.service.ContentService;
import com.yaoyao.pojo.TbContent;

@RestController
public class ContentController {

	@Resource
	private ContentService contentSerive;

	@GetMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		EasyUIDataGridResult result = contentSerive.getContentList(categoryId, page, rows);
		return result;
	}

	@PostMapping("/content/save")
	@ResponseBody
	public ShopResult addContent(TbContent content) {
		ShopResult result = contentSerive.addContent(content);
		return result;
	}

	@PutMapping("/rest/content/edit")
	@ResponseBody
	public ShopResult updateContent(TbContent content) {
		ShopResult result = contentSerive.updateContent(content);
		return result;
	}

	@DeleteMapping("/content/delete")
	@ResponseBody
	public ShopResult deleteContent(String ids) {
		ShopResult result = contentSerive.deleteContent(ids);
		return result;
	}

	@GetMapping("/content/getContent")
	@ResponseBody
	public ShopResult getContent(Long id) {
		ShopResult result = contentSerive.getContent(id);
		return result;
	}

}
