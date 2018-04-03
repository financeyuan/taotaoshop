package com.yaoyao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.yaoyao.utils.FastDFSClient;

@Controller
public class PictureController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {
		Map<Object,Object> result = new HashMap<Object,Object>();
		try {
			// 1.接收上传的文件
			// 2.获取扩展名
			String orignalName = uploadFile.getOriginalFilename();
			String extName = orignalName.substring(orignalName.lastIndexOf(".") + 1);
			// 3.上传到图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url = IMAGE_SERVER_URL + url;
			result.put("error", 0);
			result.put("url", url);
			return JSON.toJSONString(result);
		} catch (Exception e) {
			result.put("error", 0);
			result.put("message", "图片上传失败");
			return JSON.toJSONString(result);
		}
	}

}
