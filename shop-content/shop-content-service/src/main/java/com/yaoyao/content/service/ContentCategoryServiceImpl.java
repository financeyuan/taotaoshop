package com.yaoyao.content.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yaoyao.common.pojo.EasyUITreeNode;
import com.yaoyao.common.pojo.ShopResult;
import com.yaoyao.mapper.TbContentCategoryMapper;
import com.yaoyao.pojo.TbContentCategory;
import com.yaoyao.pojo.TbContentCategoryExample;
import com.yaoyao.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Resource
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		// 创建一个查询类
		TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
		// 设置查询条件
		Criteria criteria = contentCategoryExample.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 查询
		List<TbContentCategory> categoryList = contentCategoryMapper.selectByExample(contentCategoryExample);
		System.out.println(categoryList.size());
		// 将categoryList转换为List<EasyUITreeNode>
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory contentCategory : categoryList) {
			EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
			easyUITreeNode.setId(contentCategory.getId());
			easyUITreeNode.setText(contentCategory.getName());
			easyUITreeNode.setStatus(contentCategory.getIsParent() ? "closed" : "open");
			resultList.add(easyUITreeNode);
		}
		return resultList;
	}

	@Override
	public ShopResult addContentCategory(Long parentId, String name) {
		// 实例化一个对象
		TbContentCategory contentCategory = new TbContentCategory();
		// 填充属性值
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		// 状态。可选值:1(正常),2(删除)，刚添加的节点肯定是正常的
		contentCategory.setStatus(1);
		// 刚添加的节点肯定不是父节点
		contentCategory.setIsParent(false);
		// 数据库中现在默认的都是1，所以这里我们也写成1
		contentCategory.setSortOrder(1);
		// 保存当前操作时间
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 插入节点到数据库
		contentCategoryMapper.saveAndGetId(contentCategory);
		// 添加一个节点需要判断父节点是不是叶子节点，如果父节点是叶子节点的话，
		// 需要改成父节点状态
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return ShopResult.ok(contentCategory);
	}

	@Override
	public ShopResult updateContentCategory(Long id, String name) {
		// 通过id查询节点对象
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		// 判断新的name值与原来的值是否相同，如果相同则不用更新
		if (name != null && name.equals(contentCategory.getName())) {
			return ShopResult.ok();
		}
		contentCategory.setName(name);
		// 设置更新时间
		contentCategory.setUpdated(new Date());
		// 更新数据库
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		// 返回结果
		return ShopResult.ok();
	}

	// 通过父节点id来查询所有子节点，这是抽离出来的公共方法
	private List<TbContentCategory> getContentCategoryListByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		return list;
	}

	// 递归删除节点
	private void deleteNode(long parentId) {
		List<TbContentCategory> list = getContentCategoryListByParentId(parentId);
		for (TbContentCategory contentCategory : list) {
			contentCategory.setStatus(2);
			contentCategoryMapper.updateByPrimaryKey(contentCategory);
			if (contentCategory.getIsParent()) {
				deleteNode(contentCategory.getId());
			}
		}
	}

	@Override
	public ShopResult deleteContentCategory(Long id) {
		// 删除分类，就是改节点的状态为2
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		// 状态。可选值:1(正常),2(删除)
		contentCategory.setStatus(2);
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
		// 我们还需要判断一下要删除的这个节点是否是父节点，如果是父节点，那么就级联
		// 删除这个父节点下的所有子节点（采用递归的方式删除）
		if (contentCategory.getIsParent()) {
			deleteNode(contentCategory.getId());
		}
		// 需要判断父节点当前还有没有子节点，如果有子节点就不用做修改
		// 如果父节点没有子节点了，那么要修改父节点的isParent属性为false即变为叶子节点
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(contentCategory.getParentId());
		List<TbContentCategory> list = getContentCategoryListByParentId(parent.getId());
		// 判断父节点是否有子节点是判断这个父节点下的所有子节点的状态，如果状态都是2就说明
		// 没有子节点了，否则就是有子节点。
		boolean flag = false;
		for (TbContentCategory tbContentCategory : list) {
			if (tbContentCategory.getStatus() == 0) {
				flag = true;
				break;
			}
		}
		// 如果没有子节点了
		if (!flag) {
			parent.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		// 返回结果
		return ShopResult.ok();
	}

}