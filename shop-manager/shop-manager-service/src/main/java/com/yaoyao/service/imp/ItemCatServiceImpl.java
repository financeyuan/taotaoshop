package com.yaoyao.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yaoyao.common.pojo.EasyUITreeNode;
import com.yaoyao.mapper.TbItemCatMapper;
import com.yaoyao.pojo.TbItemCat;
import com.yaoyao.pojo.TbItemCatExample;
import com.yaoyao.pojo.TbItemCatExample.Criteria;
import com.yaoyao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Resource
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(Long id) {
		//根据父节点ID查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		//设置父节点
		criteria.andParentIdEqualTo(id);
		//执行查询
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		//将TbItemCat集合转换成EasyUITreeNode集合
		List<EasyUITreeNode> easyUITreeNodeList = new ArrayList<>();
		for(TbItemCat cat:list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(cat.getId());
			node.setStatus(cat.getIsParent()?"close":"open");
			node.setText(cat.getName());
			easyUITreeNodeList.add(node);
		}
		
		return easyUITreeNodeList;
		
	}

}
