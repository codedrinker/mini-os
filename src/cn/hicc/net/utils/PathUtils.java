package cn.hicc.net.utils;

import java.io.File;

public class PathUtils {
	// ����Ƕ���ĳ��������õ������·���;���·���Ĵ�ŵط��������޸ĺ�ʹ���������ǱȽϼ򵥵�
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
