package cn.hicc.net.porlet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import cn.hicc.net.dao.ProcessDao;
import cn.hicc.net.model.OSProcess;

public class FileList implements MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private static int processId = 0;

	private boolean addOver = false;

	// ���徲̬ͼƬ���Եĵط�
	public static final ImageIcon ICON_COMPUTER = new ImageIcon(
			"images/computer.jpg");
	public static final ImageIcon ICON_DISK = new ImageIcon(
			"images/tree/disk.gif");
	public static final ImageIcon ICON_FOLDER = new ImageIcon(
			"images/tree/fileclose.gif");
	public static final ImageIcon ICON_EXPANDEDFOLDER = new ImageIcon(
			"images/tree/fileopen.gif");
	public static final ImageIcon ICON_EXE = new ImageIcon(
			"images/tree/app.gif");
	public static final ImageIcon ICON_TXT = new ImageIcon(
			"images/tree/file.gif");

	// �������ֲ�ͬ���ļ���ʽ�����ֲ�ͬ���Ҽ���ʽ
	private JPopupMenu fileMenu = null;
	private JPopupMenu exeMenu = null;

	// �����������İ�ť���¼�

	private JMenuItem addFile;
	private JMenuItem execute;
	private JMenuItem delete;
	private JMenuItem edit;
	private JMenuItem addTXT;
	private JMenuItem addEXE;

	private String newTXText = ".file";
	private String newEXEext = ".app";

	private JTree tree;
	JPopupMenu popMenu;
	JMenuItem addItem;
	JMenuItem delItem;
	JMenuItem editItem;

	DefaultMutableTreeNode dRoot;

	public FileList() {
		tree = new JTree();
		tree.setEditable(true);

		dRoot = new DefaultMutableTreeNode("D:/disk");
		/*
		 * File f = new File(System.getProperty("user.dir") + File.separator +
		 * "disk");// ����·��
		 */
		File f = new File("d:/disk");
		tree(f, dRoot);
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree
				.getCellRenderer();
		renderer.setOpenIcon(ICON_EXPANDEDFOLDER);
		renderer.setClosedIcon(ICON_FOLDER);
		renderer.setLeafIcon(ICON_TXT);

		DefaultTreeModel m1 = new DefaultTreeModel(dRoot);
		tree.setModel(m1);

		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addMouseListener(this);
		tree.setCellEditor(new DefaultTreeCellEditor(tree,
				new DefaultTreeCellRenderer()));

		exeMenu = new JPopupMenu();
		fileMenu = new JPopupMenu();

		addItem = new JMenu("�½�");
		addItem.addActionListener(this);
		addTXT = new JMenuItem("�½��ı��ļ�");
		addTXT.addActionListener(this);
		addEXE = new JMenuItem("�½�ִ���ļ�");
		addEXE.addActionListener(this);
		addFile = new JMenuItem("�½��ļ���");
		addFile.addActionListener(this);

		delItem = new JMenuItem("ɾ��");
		delItem.addActionListener(this);
		editItem = new JMenuItem("�޸�");
		editItem.addActionListener(this);

		execute = new JMenuItem("ִ��");
		execute.addActionListener(this);
		edit = new JMenuItem("�༭");
		edit.addActionListener(this);
		delete = new JMenuItem("ɾ��");
		delete.addActionListener(this);

		addItem.add(addTXT);
		addItem.add(addEXE);
		addItem.add(addFile);

		fileMenu.add(addItem);
		fileMenu.add(delItem);
		fileMenu.add(editItem);

		exeMenu.add(execute);
		exeMenu.add(edit);
		exeMenu.add(delete);
	}

	public void mouseClicked(MouseEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		TreeNode[] a = node.getPath();
		String st = "";
		for (int i = 0; i < a.length; i++) {
			if (i != a.length - 1) {
				st += a[i] + File.separator; // ����������һ��������һ����/ ��
			} else {
				st += a[i]; // ���һ������ѡ����Ǹ��Ͳ�Ҫ�ӡ�/ ��
			}
		}

		if ("file".equalsIgnoreCase(getExtensionName(st))) {
			execute.setVisible(false);
		} else {
			execute.setVisible(true);
		}

	}

	public void mouseEntered(MouseEvent e) {
		// System.out.println("1");
	}

	public void mouseExited(MouseEvent e) {
		// System.out.println("2");
	}

	public void mousePressed(MouseEvent e) {
		// System.out.println("2");
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();
		TreePath path = tree.getPathForLocation(e.getX(), e.getY()); // �ؼ������������ʹ��
		if (path == null) {
			return;
		}
		tree.setSelectionPath(path);

		if (e.getButton() == 3) {
			if (node != null) {
				if (node.isLeaf()) {
					exeMenu.show(tree, e.getX(), e.getY());
				} else if (!node.isLeaf()) {
					fileMenu.show(tree, e.getX(), e.getY());
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		// System.out.println("3");
	}

	// �Ҽ��˵�����
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		TreeNode[] a = node.getPath();
		String st = "";
		for (int i = 0; i < a.length; i++) {
			if (i != a.length - 1) {
				st += a[i] + File.separator; // ����������һ��������һ����/ ��
			} else {
				st += a[i]; // ���һ������ѡ����Ǹ��Ͳ�Ҫ�ӡ�/ ��
			}
		}

		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");// ���Է�����޸����ڸ�ʽ
		String time = dateFormat.format(now);

		if (e.getSource() == addTXT) {

			String us = JOptionPane.showInputDialog(null, "�������ļ�����", "������!",
					JOptionPane.NO_OPTION);
			if (!"".equals(us) && us != null) {
				time = us;
			}

			// �����ļ�
			File file = new File(st + File.separator + time + newTXText);
			try {
				file.createNewFile();
				((DefaultTreeModel) tree.getModel()).insertNodeInto(
						new DefaultMutableTreeNode(time + newTXText), node,
						node.getChildCount());
				tree.expandPath(tree.getSelectionPath());

				new Notebook(st + File.separator + time + newTXText);// �༭�ļ�

				// ִ��xml�ļ�
				ProcessDao pd = new ProcessDao();
				OSProcess op = new OSProcess();
				op.setId(String.valueOf(processId));
				processId++;
				op.setNoteId(st + File.separator + time + newTXText);
				op.setNoteName(time + newTXText);
				op.setExt(newTXText);
				pd.add(op);
			} catch (IOException e1) {

			}

		} else if (e.getSource() == addEXE) {

			String us = JOptionPane.showInputDialog(null, "�������ļ�����", "������!",
					JOptionPane.NO_OPTION);
			if (!"".equals(us) && us != null) {
				time = us;
			}

			// �����ļ�
			File file = new File(st + File.separator + time + newEXEext);
			try {
				file.createNewFile();
				((DefaultTreeModel) tree.getModel()).insertNodeInto(
						new DefaultMutableTreeNode(time + newEXEext), node,
						node.getChildCount());
				tree.expandPath(tree.getSelectionPath());
				new Notebook(st + File.separator + time + newEXEext);// �༭�ļ�

				// ִ��xml�ļ�
				ProcessDao pd = new ProcessDao();
				OSProcess op = new OSProcess();
				op.setId(String.valueOf(processId));
				processId++;
				op.setNoteId(st + File.separator + time + newEXEext);
				op.setNoteName(time + newEXEext);
				op.setExt(newEXEext);
				pd.add(op);

			} catch (IOException e1) {
				// System.out.println("���������ļ�" + st + "\\NewEXE" + time + ".exe"
				// + "ʧ�ܣ�");
			}

		} else if (e.getSource() == delItem) {
			if (node.isRoot()) {
				return;
			}

			File file = new File(st);
			deleteFile(file);

			// �жϸ��ڵ�Ҫ��Ϊ�յĻ�����ӽڵ�
			File f = new File("d:\\disk");
			File[] f1 = f.listFiles();
			if (f1.length == 0) {
				DefaultMutableTreeNode c1 = new DefaultMutableTreeNode(
						"---���ļ���Ϊ��---");
				dRoot.add(c1);
			}

			((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);

		} else if (e.getSource() == editItem) {
			tree.startEditingAtPath(tree.getSelectionPath());
		} else if (e.getSource() == addFile)// �����ļ���
		{
			String us = JOptionPane.showInputDialog(null, "�������ļ�������", "������!",
					JOptionPane.NO_OPTION);
			String destDirName = us;
			String temp = destDirName;
			File dir = new File(st + File.separator + destDirName);
			if (dir.exists()) {
				JOptionPane.showMessageDialog(null, "����Ŀ¼" + destDirName
						+ "ʧ�ܣ�Ŀ��Ŀ¼�Ѵ��ڣ�", "����", JOptionPane.WARNING_MESSAGE);
			}
			if (!destDirName.endsWith(File.separator)) {
				destDirName = destDirName + File.separator;
			}
			// ��������Ŀ¼
			if (dir.mkdirs()) {
				// System.out.println("����Ŀ¼" + destDirName + "�ɹ���");
			}

			DefaultMutableTreeNode c1 = new DefaultMutableTreeNode(temp);
			DefaultMutableTreeNode c2 = new DefaultMutableTreeNode(
					"---���ļ���Ϊ��---");
			c1.add(c2);
			((DefaultTreeModel) tree.getModel()).insertNodeInto(c1, node, node
					.getChildCount());
			tree.expandPath(tree.getSelectionPath());

		} else if (e.getSource() == edit) {
			new Notebook(st);
		} else if (e.getSource() == delete) {
			File ff = new File(st);
			ff.delete();

			ProcessDao pd = new ProcessDao();
			pd.delete(st);

			// �жϸ��ڵ�Ҫ��Ϊ�յĻ�����ӽڵ�
			File f = new File("d:\\disk");
			File[] f1 = f.listFiles();
			if (f1.length == 0) {
				DefaultMutableTreeNode c1 = new DefaultMutableTreeNode(
						"---���ļ���Ϊ��---");
				dRoot.add(c1);
			}

			((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
		} else if (e.getSource() == execute) { // ִ��
			if ("app".equalsIgnoreCase(getExtensionName(st))) {
				ProcessDao pd = new ProcessDao();
				pd.execute(st);
			}

		}
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

	// ɾ���ļ���
	public void deleteFile(File file) {
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
		}
	}

	// ��ʾĿ¼�ķ���
	public static void tree(File f, DefaultMutableTreeNode cbtn) {
		// �жϴ�������Ƿ�Ϊһ���ļ��ж���
		if (f.isDirectory()) {
			File[] t = f.listFiles();

			if (t.length == 0) {
				DefaultMutableTreeNode c2 = new DefaultMutableTreeNode(
						"---���ļ���Ϊ��---");
				cbtn.add(c2);
			}

			for (int i = 0; i < t.length; i++) {

				// �ж��ļ��б��еĶ����Ƿ�Ϊ�ļ��ж����������ִ��tree�ݹ飬ֱ���Ѵ��ļ����������ļ����Ϊֹ
				if (t[i].isDirectory()) {
					DefaultMutableTreeNode c = new DefaultMutableTreeNode(t[i]
							.getName());
					File f1 = new File(t[i].getPath());
					File[] t2 = f1.listFiles();
					if (t2.length == 0) {
						DefaultMutableTreeNode c2 = new DefaultMutableTreeNode(
								"---���ļ���Ϊ��---");
						c.add(c2);
					}
					cbtn.add(c);
					tree(t[i], c);
				} else {
					DefaultMutableTreeNode c = new DefaultMutableTreeNode(t[i]
							.getName());
					cbtn.add(c);
				}
			}
		}
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public JTree getTree() {
		return this.tree;
	}

}
