package com.dakewe.core.mybatis.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dakewe.core.secure.ZingUser;
import com.dakewe.core.secure.utils.SecureUtil;
import com.dakewe.core.tool.constant.ZingConstant;
import com.dakewe.core.tool.utils.BeanUtil;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 业务封装基础类
 *
 * @param <M> mapper
 * @param <T> model
 * @author Zing
 */
@Validated
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

	private Class<T> modelClass;

	@SuppressWarnings("unchecked")
	public BaseServiceImpl() {
		Type type = this.getClass().getGenericSuperclass();
		this.modelClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[1];
	}

	@Override
	public boolean save(T entity) {
		ZingUser user = SecureUtil.getUser();
		LocalDateTime now = LocalDateTime.now();
		entity.setCreateUser(user.getUserId());
		entity.setCreateTime(now);
		entity.setUpdateUser(user.getUserId());
		entity.setUpdateTime(now);
		entity.setStatus(ZingConstant.DB_STATUS_NORMAL);
		entity.setIsDeleted(ZingConstant.DB_NOT_DELETED);
		return super.save(entity);
	}

	@Override
	public boolean updateById(T entity) {
		ZingUser user = SecureUtil.getUser();
		entity.setUpdateUser(user.getUserId());
		entity.setUpdateTime(LocalDateTime.now());
		return super.updateById(entity);
	}

	@Override
	public boolean deleteLogic(@NotEmpty List<Integer> ids) {
		ZingUser user = SecureUtil.getUser();
		T entity = BeanUtil.newInstance(modelClass);
		entity.setUpdateUser(user.getUserId());
		entity.setUpdateTime(LocalDateTime.now());
		return super.update(entity, Wrappers.<T>update().lambda().in(T::getId, ids)) && super.removeByIds(ids);
	}

}
