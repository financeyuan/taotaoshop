package com.yaoyao.content.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yaoyao.common.pojo.EasyUIDataGridResult;
import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.jedis.service.JedisClient;
import com.yaoyao.mapper.TbContentMapper;
import com.yaoyao.pojo.TbContent;
import com.yaoyao.pojo.TbContentExample;
import com.yaoyao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	@Resource
	private TbContentMapper contentMapper;
	@Resource
	private JedisClient jedisClient;

	@Override
	public EasyUIDataGridResult getContentList(long categoryId, int page, int rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		// 获取查询结果
		List<TbContent> list = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		// 返回结果
		return result;
	}

	@Override
	public ShopResult addContent(TbContent content) {
		// 补充属性
		content.setCreated(new Date());
		content.setUpdated(new Date());
		// 添加
		contentMapper.insert(content);
		//同步缓存，由于首页大广告位的分类ID为89，content.getCategoryId()得到的便是89  
		jedisClient.set(INDEX_CONTENT, content.getCategoryId().toString());  
		// 返回结果
		return ShopResult.ok();
	}

	@Override
	public ShopResult updateContent(TbContent content) {
		// 填充属性
		content.setUpdated(new Date());
		// 更新内容
		contentMapper.updateByPrimaryKeyWithBLOBs(content);
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		jedisClient.set(INDEX_CONTENT, content.getCategoryId().toString());
		// 返回结果
		return ShopResult.ok();
	}

	@Override
	public ShopResult deleteContent(String ids) {
		String[] idList = ids.split(",");
		for (String id : idList) {
			// 删除内容
			contentMapper.deleteByPrimaryKey(Long.valueOf(id));
			jedisClient.hdel(INDEX_CONTENT, id);
		}
		// 返回结果
		return ShopResult.ok();
	}

	@Override
	public ShopResult getContent(long id) {
		TbContent content = contentMapper.selectByPrimaryKey(id);
		return ShopResult.ok(content);
	}

	@Override
	public List<TbContent> getContentListByCid(long cid) {
		// 首先查询缓存，如果缓存中存在的话，就直接将结果返回给前台展示，查询缓存不能影响业务流程
		try {
			String json = jedisClient.hget(INDEX_CONTENT, cid + "");
			// 如果从缓存中查到了结果
			if (StringUtils.isNotBlank(json)) {
				// 将json串转化为List<TbContent>
				List<TbContent> list = JSON.parseArray(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExample(example);
		// 添加缓存，不能影响业务流程
		try {
			String json = JSON.toJSONString(list);
			jedisClient.hset(INDEX_CONTENT, cid + "", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回结果
		return list;
	}

}
