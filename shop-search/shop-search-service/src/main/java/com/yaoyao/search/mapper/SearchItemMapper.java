package com.yaoyao.search.mapper;

import java.util.List;

import com.yaoyao.common.pojo.SearchItem;

public interface SearchItemMapper {
	
	List<SearchItem> getSearchItemList();
	
	SearchItem getItemById(Long itemId);

}
