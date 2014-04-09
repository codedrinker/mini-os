package cn.hicc.net.utils;

import java.io.File;

public class PathUtils {
	// 这个是定义的程序所有用到的相对路径和绝对路径的存放地方，这样修改和使用起来都是比较简单的
	private static String path = System.getProperty("user.dir");

	public static String getDocumentPath() {
		return path + File.separator + "document" + File.separator
				+ "help.html";
	}

	public static String getCHMPath() {
		return path + File.separator + "document" + File.separator
				+ "MiniOS.CHM";
	}

	public static String getVersionPath() {
		return path + File.separator + "images" + File.separator + "version"
				+ File.separator + "version.gif";
	}
}
