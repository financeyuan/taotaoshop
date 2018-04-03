package com.yaoyao.content.service;

import java.util.List;

import com.yaoyao.common.pojo.EasyUIDataGridResult;
import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.pojo.TbContent;

public interface ContentService {
	// 获取内容列表
	EasyUIDataGridResult getContentList(long categoryId, int page, int rows);

	// 添加内容 Shop
	ShopResult addContent(TbContent content);

	// 修改内容
	ShopResult updateContent(TbContent content);

	// 删除内容
	ShopResult deleteContent(String ids);

	// 获取单个内容信息
	ShopResult getContent(long id);

	// 根据内容分类id来获取内容列表
	List<TbContent> getContentListByCid(long cid);
}
