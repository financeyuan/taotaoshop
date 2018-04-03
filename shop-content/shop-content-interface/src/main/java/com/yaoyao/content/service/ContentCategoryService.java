package com.yaoyao.content.service;

import java.util.List;

import com.yaoyao.common.pojo.EasyUITreeNode;
import com.yaoyao.common.pojo.ShopResult;


public interface ContentCategoryService {
	
	//获取内容列表
	List<EasyUITreeNode> getContentCategoryList(Long parentId);
	//添加内容分类
	ShopResult addContentCategory(Long parentId,String name);
	//修改内容分类
	ShopResult updateContentCategory(Long id,String name);
	//删除内容分类
	ShopResult deleteContentCategory(Long id);
	
}
