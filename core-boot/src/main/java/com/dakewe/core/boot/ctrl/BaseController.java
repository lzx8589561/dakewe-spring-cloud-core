package com.dakewe.core.boot.ctrl;

import com.dakewe.core.boot.file.ZingFile;
import com.dakewe.core.boot.file.ZingFileUtil;
import com.dakewe.core.secure.ZingUser;
import com.dakewe.core.secure.utils.SecureUtil;
import com.dakewe.core.tool.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 控制器封装类
 *
 * @author Zing
 */
public class BaseController {

	/**
	 * ============================     REQUEST    =================================================
	 */

	@Autowired
	private HttpServletRequest request;

	/**
	 * 获取request
	 *
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 获取当前用户
	 *
	 * @return ZingUser
	 */
	public ZingUser getUser() {
		return SecureUtil.getUser();
	}

	/** ============================     API_RESULT    =================================================  */

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data) {
		return R.data(data);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data, String msg) {
		return R.data(data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param code 状态码
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public <T> R<T> data(T data, String msg, int code) {
		return R.data(code, data, msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public R success(String msg) {
		return R.success(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param msg 消息
	 * @return R
	 */
	public R fail(String msg) {
		return R.fail(msg);
	}

	/**
	 * 返回ApiResult
	 *
	 * @param flag 是否成功
	 * @return R
	 */
	public R status(boolean flag) {
		return R.status(flag);
	}


	/**============================     FILE    =================================================  */

	/**
	 * 获取File封装类
	 *
	 * @param file 文件
	 * @return File
	 */
	public ZingFile getFile(MultipartFile file) {
		return ZingFileUtil.getFile(file);
	}

	/**
	 * 获取File封装类
	 *
	 * @param file 文件
	 * @param dir  目录
	 * @return File
	 */
	public ZingFile getFile(MultipartFile file, String dir) {
		return ZingFileUtil.getFile(file, dir);
	}

	/**
	 * 获取File封装类
	 *
	 * @param file        文件
	 * @param dir         目录
	 * @param path        路径
	 * @param virtualPath 虚拟路径
	 * @return File
	 */
	public ZingFile getFile(MultipartFile file, String dir, String path, String virtualPath) {
		return ZingFileUtil.getFile(file, dir, path, virtualPath);
	}

	/**
	 * 获取File封装类
	 *
	 * @param files 文件集合
	 * @return File
	 */
	public List<ZingFile> getFiles(List<MultipartFile> files) {
		return ZingFileUtil.getFiles(files);
	}

	/**
	 * 获取File封装类
	 *
	 * @param files 文件集合
	 * @param dir   目录
	 * @return File
	 */
	public List<ZingFile> getFiles(List<MultipartFile> files, String dir) {
		return ZingFileUtil.getFiles(files, dir);
	}

	/**
	 * 获取File封装类
	 *
	 * @param files       文件集合
	 * @param dir         目录
	 * @param path        目录
	 * @param virtualPath 虚拟路径
	 * @return File
	 */
	public List<ZingFile> getFiles(List<MultipartFile> files, String dir, String path, String virtualPath) {
		return ZingFileUtil.getFiles(files, dir, path, virtualPath);
	}


}
