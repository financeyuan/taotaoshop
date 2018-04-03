package com.yaoyao.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.search.service.SearchItemService;

@RestController
public class IndexManagerController {
	
    @Resource  
    private SearchItemService searchItemService;  
      
    @RequestMapping("/index/import")  
    @ResponseBody  
    public ShopResult importIndex(){ 
    	System.out.println("1111");
    	ShopResult result = searchItemService.importItemsToIndex();  
        return result;  
    }  

}
