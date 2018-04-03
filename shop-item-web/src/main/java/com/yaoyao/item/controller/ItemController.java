package com.yaoyao.item.controller;


import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaoyao.item.pojo.Item;
import com.yaoyao.pojo.TbItem;
import com.yaoyao.pojo.TbItemDesc;
import com.yaoyao.service.ItemService;
/**
 * 商品详情展示
 * @author Administrator
 *
 */
@RestController
public class ItemController {
	
	@Resource 
    private ItemService itemService;  
      
    @RequestMapping("/item/{itemId}")  
    public String showItem(@PathVariable Long itemId,Model model){  
        //获取商品基本信息  
        TbItem tbItem = itemService.getItemById(itemId);  
        Item item = new Item(tbItem);  
        //获取商品描述信息  
        TbItemDesc tbItemDesc = itemService.getItemDescById(itemId);  
        //返回给页面需要的对象  
        model.addAttribute("item", item);  
        model.addAttribute("itemDesc", tbItemDesc);  
        //返回逻辑视图  
        return "item";  
    }  

}