package com.kin.processing.util;

import java.io.File;

/**
 * 
 * @ClassName: MethodUtil
 * @Description: 公共方法
 * @author cxz
 * @date 2019年5月5日 下午6:33:30
 *
 */
public class MethodUtil {

	/**
	 * 
	 * @Title: createDir
	 * @Description: 根据路径创建文件目录
	 * @param destDirName
	 *        文件目录
	 * @return 文件目录
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs()) {
			System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else {
			System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}

}
