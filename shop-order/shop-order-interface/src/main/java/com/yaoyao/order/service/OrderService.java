package com.yaoyao.order.service;

import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.order.pojo.OrderInfo;

public interface OrderService {
	 //生成订单，OrderInfo当中包含了表单提交的所有数据。  
    ShopResult createOrder(OrderInfo orderInfo); 

}
