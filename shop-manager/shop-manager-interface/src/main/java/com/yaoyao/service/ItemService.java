package com.yaoyao.service;

import com.yaoyao.common.pojo.EasyUIDataGridResult;
import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.pojo.TbItem;
import com.yaoyao.pojo.TbItemDesc;

public interface ItemService {
	
	TbItem getItemById(Long itemId);
	
	EasyUIDataGridResult getItemList(int page,int rows);
	
	ShopResult createItem(TbItem tbItem,String desc) throws Exception;
	
	void insertItemDesc(long itemId,String desc);
	
	TbItemDesc getItemDescById(long itemId);

}
