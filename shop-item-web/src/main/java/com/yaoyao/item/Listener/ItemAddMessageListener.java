package com.yaoyao.item.Listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.yaoyao.item.pojo.Item;
import com.yaoyao.pojo.TbItem;
import com.yaoyao.pojo.TbItemDesc;
import com.yaoyao.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class ItemAddMessageListener implements MessageListener {
	@Resource
	private ItemService itemService;
	@Resource
	private FreeMarkerConfigurer freeMarkerConfiger;
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;

	@Override
	public void onMessage(Message message) {
		try {
			//从消息中获取商品ID
			TextMessage textMessage = (TextMessage)message;
			String itemIdStr = textMessage.getText();
			Long itemId = Long.parseLong(itemIdStr);
			//等待事务的提交，采用三次尝试的机会
			TbItem tbItem = null;
			for(int i=0;i<3;i++){
				//根据商品id查询商品信息及商品描述
				Thread.sleep(1000);
				tbItem = itemService.getItemById(itemId);
				if(tbItem != null){
					break;
				}
			}
			Item item = new Item(tbItem);
			TbItemDesc itemDesc = itemService.getItemDescById(itemId);
			//使用freemarker生成静态页面
			Configuration configuration = freeMarkerConfiger.getConfiguration();
			//创建模板并加载模板对象
			Template template = configuration.getTemplate("item.ftl");
			//准备模板需要的数据
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc", itemDesc);
			//指定输出的目录及文件名
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(new File(HTML_OUT_PATH+itemIdStr+".html")),"UTF-8");
			//生成静态页面
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
