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

	// 定义静态图片属性的地方
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

	// 定义三种不同的文件形式有三种不同的右键方式
	private JPopupMenu fileMenu = null;
	private JPopupMenu exeMenu = null;

	// 定义各种情况的按钮和事件

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
		 * "disk");// 磁盘路径
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

		addItem = new JMenu("新建");
		addItem.addActionListener(this);
		addTXT = new JMenuItem("新建文本文件");
		addTXT.addActionListener(this);
		addEXE = new JMenuItem("新建执行文件");
		addEXE.addActionListener(this);
		addFile = new JMenuItem("新建文件夹");
		addFile.addActionListener(this);

		delItem = new JMenuItem("删除");
		delItem.addActionListener(this);
		editItem = new JMenuItem("修改");
		editItem.addActionListener(this);

		execute = new JMenuItem("执行");
		execute.addActionListener(this);
		edit = new JMenuItem("编辑");
		edit.addActionListener(this);
		delete = new JMenuItem("删除");
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
				st += a[i] + File.separator; // 如果不是最后一个就连上一个“/ ”
			} else {
				st += a[i]; // 最后一个即你选择的那个就不要加“/ ”
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
		TreePath path = tree.getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用
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

	// 右键菜单监听
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		TreeNode[] a = node.getPath();
		String st = "";
		for (int i = 0; i < a.length; i++) {
			if (i != a.length - 1) {
				st += a[i] + File.separator; // 如果不是最后一个就连上一个“/ ”
			} else {
				st += a[i]; // 最后一个即你选择的那个就不要加“/ ”
			}
		}

		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");// 可以方便地修改日期格式
		String time = dateFormat.format(now);

		if (e.getSource() == addTXT) {

			String us = JOptionPane.showInputDialog(null, "请输入文件名：", "请输入!",
					JOptionPane.NO_OPTION);
			if (!"".equals(us) && us != null) {
				time = us;
			}

			// 创建文件
			File file = new File(st + File.separator + time + newTXText);
			try {
				file.createNewFile();
				((DefaultTreeModel) tree.getModel()).insertNodeInto(
						new DefaultMutableTreeNode(time + newTXText), node,
						node.getChildCount());
				tree.expandPath(tree.getSelectionPath());

				new Notebook(st + File.separator + time + newTXText);// 编辑文件

				// 执行xml文件
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

			String us = JOptionPane.showInputDialog(null, "请输入文件名：", "请输入!",
					JOptionPane.NO_OPTION);
			if (!"".equals(us) && us != null) {
				time = us;
			}

			// 创建文件
			File file = new File(st + File.separator + time + newEXEext);
			try {
				file.createNewFile();
				((DefaultTreeModel) tree.getModel()).insertNodeInto(
						new DefaultMutableTreeNode(time + newEXEext), node,
						node.getChildCount());
				tree.expandPath(tree.getSelectionPath());
				new Notebook(st + File.separator + time + newEXEext);// 编辑文件

				// 执行xml文件
				ProcessDao pd = new ProcessDao();
				OSProcess op = new OSProcess();
				op.setId(String.valueOf(processId));
				processId++;
				op.setNoteId(st + File.separator + time + newEXEext);
				op.setNoteName(time + newEXEext);
				op.setExt(newEXEext);
				pd.add(op);

			} catch (IOException e1) {
				// System.out.println("创建单个文件" + st + "\\NewEXE" + time + ".exe"
				// + "失败！");
			}

		} else if (e.getSource() == delItem) {
			if (node.isRoot()) {
				return;
			}

			File file = new File(st);
			deleteFile(file);

			// 判断根节点要是为空的话，添加节点
			File f = new File("d:\\disk");
			File[] f1 = f.listFiles();
			if (f1.length == 0) {
				DefaultMutableTreeNode c1 = new DefaultMutableTreeNode(
						"---此文件夹为空---");
				dRoot.add(c1);
			}

			((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);

		} else if (e.getSource() == editItem) {
			tree.startEditingAtPath(tree.getSelectionPath());
		} else if (e.getSource() == addFile)// 创建文件夹
		{
			String us = JOptionPane.showInputDialog(null, "请输入文件夹名：", "请输入!",
					JOptionPane.NO_OPTION);
			String destDirName = us;
			String temp = destDirName;
			File dir = new File(st + File.separator + destDirName);
			if (dir.exists()) {
				JOptionPane.showMessageDialog(null, "创建目录" + destDirName
						+ "失败，目标目录已存在！", "警告", JOptionPane.WARNING_MESSAGE);
			}
			if (!destDirName.endsWith(File.separator)) {
				destDirName = destDirName + File.separator;
			}
			// 创建单个目录
			if (dir.mkdirs()) {
				// System.out.println("创建目录" + destDirName + "成功！");
			}

			DefaultMutableTreeNode c1 = new DefaultMutableTreeNode(temp);
			DefaultMutableTreeNode c2 = new DefaultMutableTreeNode(
					"---此文件夹为空---");
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

			// 判断根节点要是为空的话，添加节点
			File f = new File("d:\\disk");
			File[] f1 = f.listFiles();
			if (f1.length == 0) {
				DefaultMutableTreeNode c1 = new DefaultMutableTreeNode(
						"---此文件夹为空---");
				dRoot.add(c1);
			}

			((DefaultTreeModel) tree.getModel()).removeNodeFromParent(node);
		} else if (e.getSource() == execute) { // 执行
			if ("app".equalsIgnoreCase(getExtensionName(st))) {
				ProcessDao pd = new ProcessDao();
				pd.execute(st);
			}

		}
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

	// 删除文件夹
	public void deleteFile(File file) {
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
		}
	}

	// 显示目录的方法
	public static void tree(File f, DefaultMutableTreeNode cbtn) {
		// 判断传入对象是否为一个文件夹对象
		if (f.isDirectory()) {
			File[] t = f.listFiles();

			if (t.length == 0) {
				DefaultMutableTreeNode c2 = new DefaultMutableTreeNode(
						"---此文件夹为空---");
				cbtn.add(c2);
			}

			for (int i = 0; i < t.length; i++) {

				// 判断文件列表中的对象是否为文件夹对象，如果是则执行tree递归，直到把此文件夹中所有文件输出为止
				if (t[i].isDirectory()) {
					DefaultMutableTreeNode c = new DefaultMutableTreeNode(t[i]
							.getName());
					File f1 = new File(t[i].getPath());
					File[] t2 = f1.listFiles();
					if (t2.length == 0) {
						DefaultMutableTreeNode c2 = new DefaultMutableTreeNode(
								"---此文件夹为空---");
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
