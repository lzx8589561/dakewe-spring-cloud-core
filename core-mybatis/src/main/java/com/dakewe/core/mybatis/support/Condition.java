package com.dakewe.core.mybatis.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dakewe.core.tool.utils.BeanUtil;
import com.dakewe.core.tool.utils.Func;
import com.dakewe.core.tool.utils.StringUtil;

import java.util.Map;

/**
 * 分页工具
 *
 * @author Zing
 */
public class Condition {

	/**
	 * 转化成mybatis plus中的Page
	 *
	 * @param query
	 * @return
	 */
	public static <T> IPage<T> getPage(Query query) {
		Page<T> page = new Page<>(Func.toInt(query.getCurrent(), 1), Func.toInt(query.getSize(), 10));
		page.setAsc(Func.toStrArray(query.getAscs()));
		page.setDesc(Func.toStrArray(query.getDescs()));
		return page;
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
		return new QueryWrapper<>(entity);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
		query.remove("current");
		query.remove("size");
		QueryWrapper<T> qw = new QueryWrapper<>();
		qw.setEntity(BeanUtil.newInstance(clazz));
		if (Func.isNotEmpty(query)) {
			query.forEach((k, v) -> {
				if (Func.isNotEmpty(v)) {
					qw.like(StringUtil.humpToUnderline(k), v);
				}
			});
		}
		return qw;
	}

}
