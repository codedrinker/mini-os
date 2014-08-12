package cn.hicc.net.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import cn.hicc.net.model.*;

public class CommandDao {
	private String rootPath = "d:\\disk\\";

	// �����
	public String disposeCom(String command) 
	{
		String temp = command.trim();
		String[] commands = temp.split(" ");
		if (commands.length == 2)
		{
			// ���������ļ�
			if ("create".equalsIgnoreCase(commands[0])) 
			{
				if ("app".equalsIgnoreCase(getExtensionName(commands[1]))
						|| "file".equalsIgnoreCase(getExtensionName(commands[1]))) {
					return CreateFile(commands[1]);
				} else {
					return "�������������չ���Ƿ���ȷ";
				}

			}
			// ɾ�������ļ�
			else if ("delete".equalsIgnoreCase(commands[0])) {
				return deleteFile(commands[1]);
			}
			//�鿴�ļ�
			else if ("type".equalsIgnoreCase(commands[0])) {
				return OpenFile(commands[1]);
			}
			//����Ŀ¼
			else if ("makdir".equalsIgnoreCase(commands[0])) {
				return createDir(commands[1]);
			}
			//ɾ��Ŀ¼
			else if ("deldir".equalsIgnoreCase(commands[0])) {
				return deleteDir(commands[1]);
			}
			//����
			else if ("run".equalsIgnoreCase(commands[0])) {
				return "����" + commands[1] + "�ɹ�";
			} else {
				return "���������������";
			}
		} else {
			return "���������������";
		}
	}
	
	//�鿴�ļ�
	public String OpenFile(String fileName)
	{
		String temp = "" ;
		try {
			// ����ѡȡ���ļ�
			BufferedReader buf = new BufferedReader(new FileReader(new File(rootPath + fileName)));
			String lineSeparator = System.getProperty("line.separator");
			// ȡ��ϵͳ�����Ļ����ַ�
			String text;// ��ȡ�ļ������������ֱ༭��
			while ((text = buf.readLine()) != null) 
			{
				temp += (text + lineSeparator);
			}

			buf.close();
		} catch (IOException e) 
		{
			return "���ļ�ʧ�ܣ�" ;
		}
		return temp ;
	}
	
	//���������ļ�
	public String CreateFile(String destFileName) {
		File file = new File(rootPath + destFileName);

		if (file.exists()) {
			return "���������ļ�" + destFileName + "ʧ�ܣ�Ŀ���ļ��Ѵ��ڣ�";
		}

		if (destFileName.endsWith(File.separator)) {
			return "���������ļ�" + destFileName + "ʧ�ܣ�Ŀ�겻����Ŀ¼��";
		}

		if (!file.getParentFile().exists()) 
		{
			if (!file.getParentFile().mkdirs()) {
				return "Ŀ���ļ�����·�������ڣ�׼������������" + "\n" + "����Ŀ¼�ļ����ڵ�Ŀ¼ʧ�ܣ�";
			}
		}

		// ����Ŀ���ļ�
		try {
			if (file.createNewFile()) 
			{
				// ִ��xml�ļ�
				ProcessDao pd = new ProcessDao();
				OSProcess op = new OSProcess();
				op.setNoteId(destFileName);
				op.setNoteName(destFileName);
				op.setExt(getExtensionName(destFileName));
				pd.add(op);
				
				return "���������ļ�" + destFileName + "�ɹ���";
			} else {
				return "���������ļ�" + destFileName + "ʧ�ܣ�";
			}
		} catch (IOException e) 
		{
			return "���������ļ�" + destFileName + "ʧ�ܣ�";
		}
	}

	// ɾ���ļ�
	public String deleteFile(String command) {
		File file = new File(rootPath + command);
		if (!file.exists()) {
			return "ɾ�������ļ�" + command + "ʧ�ܣ�Ŀ���ļ������ڣ�";
		}
		if (file.delete()) {
			// ִ��xml�ļ�
			ProcessDao pd = new ProcessDao();
			pd.delete(command);
			return "ɾ�������ļ�" + command + "�ɹ���";
		} else {
			return "ɾ�������ļ�" + command + "ʧ�ܣ�";
		}
	}

	// ����Ŀ¼
	public String createDir(String destDirName) {
		File dir = new File(rootPath + destDirName);
		if (dir.exists()) {
			return "����Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�Ѵ��ڣ�";
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// ��������Ŀ¼
		if (dir.mkdirs()) {
			return "����Ŀ¼" + destDirName + "�ɹ���";
		} else {
			return "����Ŀ¼" + destDirName + "ʧ�ܣ�";
		}
	}

	// ɾ��Ŀ¼
	public String deleteDir(String destDirName) 
	{
		File file = new File(rootPath + destDirName);
		if (!file.exists()) {
			return "ɾ��Ŀ¼" + destDirName + "ʧ�ܣ�Ŀ��Ŀ¼�����ڣ�";
		}
		if(deleteFile(file))
		{
			return "ɾ��Ŀ¼" + destDirName + "�ɹ�!";
		}
		return null;
	}
	
	// ɾ���ļ���
	public boolean deleteFile(File file) 
	{
		boolean temp = false ;
		if (file.exists()) // �ж��ļ��Ƿ����
		{
			if (file.isFile()) // �ж��Ƿ����ļ�
			{
				ProcessDao pd = new ProcessDao();
				pd.delete(file.getPath());
				file.delete();
			} else if (file.isDirectory())// �����������һ��Ŀ¼
			{
				File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
				for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
					this.deleteFile(files[i]); // ��ÿ���ļ� ������������е���
				}
			}
			file.delete();
			temp = true ;
		}
		return temp;
	}
	
	// ��ȡ��չ��
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
		String str = cd.disposeCom("type wwww\\mysql_���ݿ�_servlet_jsp_ajax_��������.txt");
		// String str = cd.disposeCom("delete wclxyn.app");
		System.out.println(str);
	}

}
