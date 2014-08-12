package cn.hicc.net.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import cn.hicc.net.model.*;

public class CommandDao {
	private String rootPath = "d:\\disk\\";

	// 命令处理
	public String disposeCom(String command) 
	{
		String temp = command.trim();
		String[] commands = temp.split(" ");
		if (commands.length == 2)
		{
			// 创建单个文件
			if ("create".equalsIgnoreCase(commands[0])) 
			{
				if ("app".equalsIgnoreCase(getExtensionName(commands[1]))
						|| "file".equalsIgnoreCase(getExtensionName(commands[1]))) {
					return CreateFile(commands[1]);
				} else {
					return "请检查您输入的扩展名是否正确";
				}

			}
			// 删除单个文件
			else if ("delete".equalsIgnoreCase(commands[0])) {
				return deleteFile(commands[1]);
			}
			//查看文件
			else if ("type".equalsIgnoreCase(commands[0])) {
				return OpenFile(commands[1]);
			}
			//创建目录
			else if ("makdir".equalsIgnoreCase(commands[0])) {
				return createDir(commands[1]);
			}
			//删除目录
			else if ("deldir".equalsIgnoreCase(commands[0])) {
				return deleteDir(commands[1]);
			}
			//运行
			else if ("run".equalsIgnoreCase(commands[0])) {
				return "运行" + commands[1] + "成功";
			} else {
				return "请检查您输入的命令";
			}
		} else {
			return "请检查您输入的命令";
		}
	}
	
	//查看文件
	public String OpenFile(String fileName)
	{
		String temp = "" ;
		try {
			// 开启选取的文件
			BufferedReader buf = new BufferedReader(new FileReader(new File(rootPath + fileName)));
			String lineSeparator = System.getProperty("line.separator");
			// 取得系统相依的换行字符
			String text;// 读取文件并附加至文字编辑区
			while ((text = buf.readLine()) != null) 
			{
				temp += (text + lineSeparator);
			}

			buf.close();
		} catch (IOException e) 
		{
			return "打开文件失败！" ;
		}
		return temp ;
	}
	
	//创建单个文件
	public String CreateFile(String destFileName) {
		File file = new File(rootPath + destFileName);

		if (file.exists()) {
			return "创建单个文件" + destFileName + "失败，目标文件已存在！";
		}

		if (destFileName.endsWith(File.separator)) {
			return "创建单个文件" + destFileName + "失败，目标不能是目录！";
		}

		if (!file.getParentFile().exists()) 
		{
			if (!file.getParentFile().mkdirs()) {
				return "目标文件所在路径不存在，准备创建。。。" + "\n" + "创建目录文件所在的目录失败！";
			}
		}

		// 创建目标文件
		try {
			if (file.createNewFile()) 
			{
				// 执行xml文件
				ProcessDao pd = new ProcessDao();
				OSProcess op = new OSProcess();
				op.setNoteId(destFileName);
				op.setNoteName(destFileName);
				op.setExt(getExtensionName(destFileName));
				pd.add(op);
				
				return "创建单个文件" + destFileName + "成功！";
			} else {
				return "创建单个文件" + destFileName + "失败！";
			}
		} catch (IOException e) 
		{
			return "创建单个文件" + destFileName + "失败！";
		}
	}

	// 删除文件
	public String deleteFile(String command) {
		File file = new File(rootPath + command);
		if (!file.exists()) {
			return "删除单个文件" + command + "失败，目标文件不存在！";
		}
		if (file.delete()) {
			// 执行xml文件
			ProcessDao pd = new ProcessDao();
			pd.delete(command);
			return "删除单个文件" + command + "成功！";
		} else {
			return "删除单个文件" + command + "失败！";
		}
	}

	// 创建目录
	public String createDir(String destDirName) {
		File dir = new File(rootPath + destDirName);
		if (dir.exists()) {
			return "创建目录" + destDirName + "失败，目标目录已存在！";
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs()) {
			return "创建目录" + destDirName + "成功！";
		} else {
			return "创建目录" + destDirName + "失败！";
		}
	}

	// 删除目录
	public String deleteDir(String destDirName) 
	{
		File file = new File(rootPath + destDirName);
		if (!file.exists()) {
			return "删除目录" + destDirName + "失败，目标目录不存在！";
		}
		if(deleteFile(file))
		{
			return "删除目录" + destDirName + "成功!";
		}
		return null;
	}
	
	// 删除文件夹
	public boolean deleteFile(File file) 
	{
		boolean temp = false ;
		if (file.exists()) // 判断文件是否存在
		{
			if (file.isFile()) // 判断是否是文件
			{
				ProcessDao pd = new ProcessDao();
				pd.delete(file.getPath());
				file.delete();
			} else if (file.isDirectory())// 否则如果它是一个目录
			{
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
			temp = true ;
		}
		return temp;
	}
	
	// 获取扩展名
	public String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static void main(String[] args) {
		CommandDao cd = new CommandDao();
		String str = cd.disposeCom("type wwww\\mysql_数据库_servlet_jsp_ajax_中文乱码.txt");
		// String str = cd.disposeCom("delete wclxyn.app");
		System.out.println(str);
	}

}
