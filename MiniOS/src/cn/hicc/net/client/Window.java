package cn.hicc.net.client;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import cn.hicc.net.dao.CommandDao;
import cn.hicc.net.dao.MenuDao;
import cn.hicc.net.dao.ProcessDao;
import cn.hicc.net.model.OSProcess;
import cn.hicc.net.porlet.Disk;
import cn.hicc.net.porlet.FileList;
import cn.hicc.net.porlet.Memory;
import cn.hicc.net.porlet.Menus;
import cn.hicc.net.staticvariables.StaticImages;
import cn.hicc.net.utils.FrameUtils;

public class Window extends JFrame implements KeyListener {
	private static int count = 2;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel wiewPanel = null;
	private JPanel diskPanel = null;
	private JPanel teminalPanel = null;
	private JPanel processPanel = null;
	private JPanel adapterPanel = null;
	private static JProgressBar timeProgressBar = null;
	private JLabel timeJLabel = null;
	private JLabel processJLabel = null;
	private JTextField processJTextField = null;
	private JLabel processValueJLabel = null;
	private JTextField processValueJTextField = null;

	// 定义JTabbedPane
	private JTabbedPane viewJTabbedPane = null;
	// 定义用户接口的JTabbedPane
	private JTabbedPane userImplTabbedPane = null;

	// 定义的队列里面的就绪队列和阻塞队列
	private JPanel blockJPanel = null;
	private JPanel orderJPanel = null;

	// 定义用户的图形接口和命令接口
	private JPanel graphicsImplJPane = null;
	private JPanel commandImpJPane = null;

	protected DefaultTreeModel m_model;
	protected JTextField m_display;

	private JPanel memoryJPanel = null;
	private JPanel illJPanel = null;
	private JPanel diskJPanel = null;
	private JLabel momeryTitleJLabel = null;
	private JLabel illTitleJLabel = null;
	private JLabel diskTitleJLabel = null;
	private JPanel momeryBlockJPanel = null;
	private JPanel diskBlockJPanel = null;
	private JLabel usingJLabel = null;
	private JButton usingJButton = null;
	private JLabel freeJLabel = null;
	private JButton freeJButton = null;
	private JLabel processingJLabel = null;
	private JButton processingJButton = null;
	private JLabel soJLabel = null;
	private JButton osJButton = null;
	private JButton adapterAButton = null;
	private JButton adapterBButton = null;
	private JButton adapterCButton = null;
	private JButton adapterDButton = null;
	private JScrollPane graphcisjScrollBar = null;
	private JScrollPane commandjScrollBar = null;

	private static JTree graphicsjTree = null;
	private JTextArea commandJTextArea = null;

	static DefaultTableModel tm;

	// 添加就绪队列和阻塞队列的jtable和滚动条
	private JScrollPane blockJScrollPane = null;
	private JScrollPane orderJScrollPane = null;
	private static JTable blockOnJScrollPaneJTable = null;
	private static JTable orderOnJScrollPaneJTable = null;

	// 定义磁盘的按钮
	private static JButton disks[] = Disk.getInstance().getDisks();
	private static JButton momeries[] = Memory.getInstance().getMomeries();

	// 定义具体的构造userImplTabbedPane的方法
	private JTabbedPane getUserImplTabbedPane() {
		if (userImplTabbedPane == null) {
			userImplTabbedPane = new JTabbedPane();
			// viewJTabbedPane.setTabPlacement(JTabbedPane.TOP);// 设置顶部切换方式
			userImplTabbedPane.setBounds(new Rectangle(0, 0, 285, 202));

			userImplTabbedPane.addTab("图形接口", getGraphicsImplJPane());
			userImplTabbedPane.addTab("命令接口", getCommandImpJPane());
		}
		return userImplTabbedPane;
	}

	// 用户的图形接口的选项组件
	private JPanel getGraphicsImplJPane() {
		if (graphicsImplJPane == null) {
			graphicsImplJPane = new JPanel();
			graphicsImplJPane.setLayout(null);
			graphicsImplJPane.add(getGraphcisjScrollBar(), null);
		}
		return graphicsImplJPane;
	}

	// 添加一个图形接口的滚动轴
	private JScrollPane getGraphcisjScrollBar() {
		if (graphcisjScrollBar == null) {
			graphcisjScrollBar = new JScrollPane();
			graphcisjScrollBar.setBounds(new Rectangle(0, 2, 280, 170));
			graphcisjScrollBar.setViewportView(getGraphicsjTree());
		}
		return graphcisjScrollBar;
	}

	// 添加一个图形接口的滚动轴
	private JScrollPane getCommandjScrollBar() {
		if (commandjScrollBar == null) {
			commandjScrollBar = new JScrollPane();
			commandjScrollBar.setBounds(new Rectangle(0, 2, 280, 170));
			commandjScrollBar.setViewportView(getCommandJTextArea());
		}
		return commandjScrollBar;
	}

	// 添加图形接口的树形结构
	private JTree getGraphicsjTree() {
		if (graphicsjTree == null) {
			FileList dt = new FileList();
			graphicsjTree = dt.getTree();
		}
		return graphicsjTree;

	}

	// 用户的命令接口的选项组件
	private JPanel getCommandImpJPane() {
		if (commandImpJPane == null) {
			commandImpJPane = new JPanel();
			commandImpJPane.setLayout(null);
			commandImpJPane.add(getCommandjScrollBar(), null);
			// commandImpJPane.setBackground(Color.BLACK);
			// commandImpJPane.add(getCommandJTextArea(), null);
		}
		return commandImpJPane;
	}

	// 这里是命令接口的类似的黑色的输入端的显示
	private JTextArea getCommandJTextArea() {
		if (commandJTextArea == null) {
			commandJTextArea = new JTextArea();
			commandJTextArea.setBounds(new Rectangle(0, 2, 285, 220));// 这里设置的是和jtabedpane的大小是一样的
			commandJTextArea.setBackground(Color.BLACK);// 设置背景色为黑色
			commandJTextArea.setForeground(Color.WHITE);// 设置前景色为白色
			commandJTextArea.setCaretColor(Color.WHITE);// 设置光标颜色
			commandJTextArea.setText("C:\\>");
			commandJTextArea.addKeyListener(this);
		}
		return commandJTextArea;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			CommandDao cd = new CommandDao();
			commandJTextArea.setCaretPosition(commandJTextArea.getDocument()
					.getLength());
			String porlet[] = commandJTextArea.getText().trim().split(
					"[\\t\\n]");
			String command = porlet[porlet.length - 1]
					.replaceAll("C:\\\\>", "");
			if ("cls".equals(command)) {
				commandJTextArea.setText("C:\\>");
			} else if ("help".equals(command)) {
				commandJTextArea
						.append("\n 创建文件：create + 空格  + <filepath>\n 删除文件：delete + 空格  + <filepath>\n "
								+ "执行文件：run  + 空格  + <filepath>\n 查看文件：type  + 空格  + <filepath>\n 创建目录："
								+ "makdir + 空格  +<filepath>\n 删除目录：deldir + 空格  + <filepath>\n清除屏幕：cls\t帮助：help");
			} else {
				commandJTextArea.append("\n" + cd.disposeCom(command));
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	// 阻塞进程的组件
	// 定义具体的构造viewJTabbedPane的方法
	private JTabbedPane getViewJTabbedPane() {
		if (viewJTabbedPane == null) {
			viewJTabbedPane = new JTabbedPane();
			// viewJTabbedPane.setTabPlacement(JTabbedPane.TOP);// 设置顶部切换方式
			viewJTabbedPane.setBounds(new Rectangle(0, 0, 320, 202));

			viewJTabbedPane.addTab("就绪队列", getOrderJPanel());
			viewJTabbedPane.addTab("阻塞队列", getBlockJPanel());
		}
		return viewJTabbedPane;
	}

	// 就绪进程的组件
	private JPanel getOrderJPanel() {
		if (orderJPanel == null) {
			orderJPanel = new JPanel();
			orderJPanel.setLayout(null);
			orderJPanel.add(getOrderJScrollPane(), null);
		}
		return orderJPanel;
	}

	private JPanel getBlockJPanel() {
		if (blockJPanel == null) {
			blockJPanel = new JPanel();
			blockJPanel.setLayout(null);
			blockJPanel.add(getBlockJScrollPane(), null);
		}
		return blockJPanel;
	}

	// 添加阻塞对鞋的滚动条
	private JScrollPane getBlockJScrollPane() {
		if (blockJScrollPane == null) {
			blockJScrollPane = new JScrollPane();
			blockJScrollPane.setBounds(new Rectangle(0, 3, 316, 169));
			blockJScrollPane.setViewportView(getBlockOnJScrollPaneJTable());
		}
		return blockJScrollPane;
	}

	// 添加就绪对鞋的滚动条
	private JScrollPane getOrderJScrollPane() {
		if (orderJScrollPane == null) {
			orderJScrollPane = new JScrollPane();
			orderJScrollPane.setBounds(new Rectangle(0, 3, 316, 169));
			orderJScrollPane.setViewportView(getOrderOnJScrollPaneJTable());
		}
		return orderJScrollPane;
	}

	// 添加阻塞对表格
	private JTable getBlockOnJScrollPaneJTable() {
		if (blockOnJScrollPaneJTable == null) {
			DefaultTableModel tm1 = new DefaultTableModel();
			String[] titles = { "进程名称", "进程ID", "内存ID", "磁盘ID", "优先级" };
			for (String str : titles) {
				tm1.addColumn(str);
			}
			ProcessDao pd = new ProcessDao();
			List<OSProcess> ops = pd.findblock();
			for (OSProcess op : ops) {
				Vector<String> row = new Vector<String>();
				row.add(op.getNoteName());
				row.add(op.getId());
				row.add(op.getRamNum());
				row.add(op.getDiskNum());
				row.add("1");
				tm1.addRow(row);
			}
			blockOnJScrollPaneJTable = new JTable(tm1);
		}
		return blockOnJScrollPaneJTable;
	}

	// 添加就绪队列的表格
	private JTable getOrderOnJScrollPaneJTable() {
		if (orderOnJScrollPaneJTable == null) {
			tm = new DefaultTableModel();
			String[] titles = { "进程名称", "进程ID", "内存ID", "磁盘ID", "优先级" };
			for (String str : titles) {
				tm.addColumn(str);
			}
			ProcessDao pd = new ProcessDao();
			List<OSProcess> ops = pd.findexecute();
			for (OSProcess op : ops) {
				Vector<String> row = new Vector<String>();
				row.add(op.getNoteName());
				row.add(op.getId());
				row.add(op.getRamNum());
				row.add(op.getDiskNum());
				row.add("1");
				tm.addRow(row);
			}
			orderOnJScrollPaneJTable = new JTable(tm);
		}
		return orderOnJScrollPaneJTable;
	}

	// 在系统上面最左边的区域，用来展示就绪队列和阻塞队列的版块
	private JPanel getWiewPanel() {
		if (wiewPanel == null) {
			wiewPanel = new JPanel();
			wiewPanel.setLayout(null);
			wiewPanel.setBounds(new Rectangle(13, 15, 320, 202));
			// wiewPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			// 这里注释掉了进程的版面的最大的panel的边框颜色
			wiewPanel.add(getViewJTabbedPane());

		}
		return wiewPanel;
	}

	// 在系统上面中间的部分，用来放置时间片轮转，正在运行的进程，目前内存中的变量的值，如果条件允许在这里再次添加关于设备的使用情况
	private JPanel getDiskPanel() {
		if (diskPanel == null) {
			processValueJLabel = new JLabel();
			processValueJLabel.setBounds(new Rectangle(14, 130, 106, 25));
			processValueJLabel.setText("变量值");
			processJLabel = new JLabel();
			processJLabel.setBounds(new Rectangle(14, 72, 106, 25));
			processJLabel.setText("运行进程");
			timeJLabel = new JLabel();
			timeJLabel.setBounds(new Rectangle(14, 14, 106, 25));
			timeJLabel.setText("时间片");
			diskPanel = new JPanel();
			diskPanel.setLayout(null);
			diskPanel.setBounds(new Rectangle(348, 15, 132, 202));
			diskPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			diskPanel.add(getTimeProgressBar(), null);
			diskPanel.add(timeJLabel, null);
			diskPanel.add(processJLabel, null);
			diskPanel.add(getProcessJTextField(), null);
			diskPanel.add(processValueJLabel, null);
			diskPanel.add(getProcessValueJTextField(), null);
		}
		return diskPanel;
	}

	// 用于命令接口和图形接口转换的模块
	private JPanel getTeminalPanel() {
		if (teminalPanel == null) {
			teminalPanel = new JPanel();
			teminalPanel.setLayout(null);
			teminalPanel.setBounds(new Rectangle(495, 15, 285, 202));

			// 先注释掉组件的边框颜色，为了暂时的美观
			// teminalPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			// 添加接口选项卡的组件
			teminalPanel.add(getUserImplTabbedPane());
		}

		return teminalPanel;
	}

	// 中间的一个大部分的用来做内存和磁盘的模块
	private JPanel getProcessPanel() {
		if (processPanel == null) {
			processPanel = new JPanel();
			processPanel.setLayout(null);
			processPanel.setBounds(new Rectangle(13, 230, 767, 206));
			processPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			processPanel.add(getMemoryJPanel(), null);
			processPanel.add(getIllJPanel(), null);
			processPanel.add(getDiskJPanel(), null);
		}
		return processPanel;
	}

	// 最下面部分的用来处理外围设备的模块
	private JPanel getAdapterPanel() {
		if (adapterPanel == null) {
			adapterPanel = new JPanel();
			adapterPanel.setLayout(null);
			adapterPanel.setBounds(new Rectangle(13, 447, 767, 83));
			adapterPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			adapterPanel.add(getAdapterAButton(), null);
			adapterPanel.add(getAdapterBButton(), null);
			adapterPanel.add(getAdapterCButton(), null);
			adapterPanel.add(getAdapterDButton(), null);
		}
		return adapterPanel;
	}

	// 定义的时间片轮转的区域
	private JProgressBar getTimeProgressBar() {
		if (timeProgressBar == null) {
			timeProgressBar = new JProgressBar();
			// 注释掉了进度条的百分比
			// timeProgressBar.setStringPainted(true);
			timeProgressBar.setBounds(new Rectangle(14, 38, 106, 25));
			timeProgressBar.setValue(0);
		}
		return timeProgressBar;
	}

	// 定义的正在执行进程的文本框
	private JTextField getProcessJTextField() {
		if (processJTextField == null) {
			processJTextField = new JTextField();
			processJTextField.setBounds(new Rectangle(14, 96, 104, 25));
		}
		return processJTextField;
	}

	// 定义的正在执行的内存里面的值的显示文本框
	private JTextField getProcessValueJTextField() {
		if (processValueJTextField == null) {
			processValueJTextField = new JTextField();
			processValueJTextField.setBounds(new Rectangle(14, 154, 104, 25));
		}
		return processValueJTextField;
	}

	// 添加内存模块
	private JPanel getMemoryJPanel() {
		if (memoryJPanel == null) {
			momeryTitleJLabel = new JLabel();
			momeryTitleJLabel.setBounds(new Rectangle(10, 5, 55, 25));
			momeryTitleJLabel.setText("内存");
			memoryJPanel = new JPanel();
			memoryJPanel.setLayout(null);
			memoryJPanel.setBounds(new Rectangle(12, 12, 306, 182));
			memoryJPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			memoryJPanel.add(momeryTitleJLabel, null);
			memoryJPanel.add(getMomeryBlockJPanel(), null);
		}
		return memoryJPanel;
	}

	// 添加图例说明模块
	private JPanel getIllJPanel() {
		if (illJPanel == null) {
			soJLabel = new JLabel();
			soJLabel.setBounds(new Rectangle(10, 130, 60, 25));
			soJLabel.setText("系统占用");
			processingJLabel = new JLabel();
			processingJLabel.setBounds(new Rectangle(10, 100, 60, 25));
			processingJLabel.setText("正在运行");
			freeJLabel = new JLabel();
			freeJLabel.setBounds(new Rectangle(10, 70, 60, 25));
			freeJLabel.setText("空        闲");
			usingJLabel = new JLabel();
			usingJLabel.setBounds(new Rectangle(10, 40, 60, 25));
			usingJLabel.setText("使        用");
			illTitleJLabel = new JLabel();
			illTitleJLabel.setBounds(new Rectangle(10, 5, 55, 25));
			illTitleJLabel.setText("图例");
			illJPanel = new JPanel();
			illJPanel.setLayout(null);
			illJPanel.setBounds(new Rectangle(330, 12, 142, 182));
			illJPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			illJPanel.add(illTitleJLabel, null);
			illJPanel.add(usingJLabel, null);
			illJPanel.add(getUsingJButton(), null);
			illJPanel.add(freeJLabel, null);
			illJPanel.add(getFreeJButton(), null);
			illJPanel.add(processingJLabel, null);
			illJPanel.add(getProcessingJButton(), null);
			illJPanel.add(soJLabel, null);
			illJPanel.add(getOsJButton(), null);
		}
		return illJPanel;
	}

	// 添加磁盘管理模块
	private JPanel getDiskJPanel() {
		if (diskJPanel == null) {
			diskTitleJLabel = new JLabel();
			diskTitleJLabel.setBounds(new Rectangle(10, 5, 55, 25));
			diskTitleJLabel.setText("磁盘");
			diskJPanel = new JPanel();
			diskJPanel.setLayout(null);
			diskJPanel.setBounds(new Rectangle(484, 12, 269, 182));
			diskJPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			diskJPanel.add(diskTitleJLabel, null);
			diskJPanel.add(getDiskBlockJPanel(), null);
		}
		return diskJPanel;
	}

	// 添加内存时候的一堆button的地方
	private JPanel getMomeryBlockJPanel() {
		if (momeryBlockJPanel == null) {
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			momeryBlockJPanel = new JPanel();
			momeryBlockJPanel.setLayout(new FlowLayout());
			momeryBlockJPanel.setBounds(new Rectangle(13, 34, 290, 133));

			for (int i = 0; i < 56; i++) {
				momeryBlockJPanel.add(momeries[i]);
			}
		}
		return momeryBlockJPanel;
	}

	// 定义磁盘一堆方块的地方
	private JPanel getDiskBlockJPanel() {
		if (diskBlockJPanel == null) {
			diskBlockJPanel = new JPanel();
			diskBlockJPanel.setLayout(new FlowLayout());
			diskBlockJPanel.setBounds(new Rectangle(13, 34, 245, 133));

			for (int i = 0; i < 48; i++) {
				diskBlockJPanel.add(disks[i]);
			}
		}
		return diskBlockJPanel;
	}

	// 占用的图例
	private JButton getUsingJButton() {
		if (usingJButton == null) {
			usingJButton = new JButton();
			usingJButton.setBounds(new Rectangle(80, 40, 50, 25));
			usingJButton.setBackground(Color.GREEN);
		}
		return usingJButton;
	}

	// 空闲的图例
	private JButton getFreeJButton() {
		if (freeJButton == null) {
			freeJButton = new JButton();
			freeJButton.setBounds(new Rectangle(80, 70, 50, 25));
			freeJButton.setBackground(Color.LIGHT_GRAY);
		}
		return freeJButton;
	}

	// 正在运行中的图例
	private JButton getProcessingJButton() {
		if (processingJButton == null) {
			processingJButton = new JButton();
			processingJButton.setBounds(new Rectangle(80, 100, 50, 25));
			processingJButton.setBackground(Color.PINK);
		}
		return processingJButton;
	}

	// 系统占用的图例
	private JButton getOsJButton() {
		if (osJButton == null) {
			osJButton = new JButton();
			osJButton.setBounds(new Rectangle(80, 130, 50, 25));
			osJButton.setBackground(Color.BLACK);
		}
		return osJButton;
	}

	// 添加的设备已，通过按钮添加的A
	private JButton getAdapterAButton() {
		if (adapterAButton == null) {
			adapterAButton = new JButton();
			adapterAButton.setBounds(new Rectangle(73, 8, 70, 70));
			adapterAButton
					.setIcon((new ImageIcon(StaticImages.getMouseImage())));

		}
		return adapterAButton;
	}

	// 设备B
	private JButton getAdapterBButton() {
		if (adapterBButton == null) {
			adapterBButton = new JButton();
			adapterBButton.setBounds(new Rectangle(241, 8, 70, 70));
			adapterBButton.setIcon((new ImageIcon(StaticImages
					.getKeyboardImage())));
		}
		return adapterBButton;
	}

	// 设备C
	private JButton getAdapterCButton() {
		if (adapterCButton == null) {
			adapterCButton = new JButton();
			adapterCButton.setBounds(new Rectangle(412, 8, 70, 70));
			adapterCButton.setIcon((new ImageIcon(StaticImages
					.getPrinterImage())));
		}
		return adapterCButton;
	}

	// 设备D
	private JButton getAdapterDButton() {
		if (adapterDButton == null) {
			adapterDButton = new JButton();
			adapterDButton.setBounds(new Rectangle(586, 8, 70, 70));
			adapterDButton
					.setIcon((new ImageIcon(StaticImages.getVideoImage())));
		}
		return adapterDButton;
	}

	// 定义具体的方法，来实现设备的显隐

	public void buttonEnable() {
		adapterAButton.setEnabled(false);
		adapterBButton.setEnabled(false);
		adapterCButton.setEnabled(false);
		adapterDButton.setEnabled(false);
	}

	public static void main(String[] args) {

		// 设置具体的窗口属性的程序块
		int status = new MenuDao().getStyle();
		if (status == 1) {
			JFrame.setDefaultLookAndFeelDecorated(true);
		} else if (status == 2) {
			JFrame.setDefaultLookAndFeelDecorated(false);
		} else {
			try {
				UIManager.setLookAndFeel(UIManager
						.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 添加时间片的滚动事件
		new Timer().schedule(new TimerTask() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
			}
		}, 1000, 20);

		// 添加时间片的滚动事件
		new Timer().schedule(new TimerTask() {
			public void run() {
				// new Window().getContentPane().removeAll();
				// new Window().validate();
				// orderOnJScrollPaneJTable.invalidate();
				// orderOnJScrollPaneJTable.validate();
				timeProgressBar.setValue(timeProgressBar.getValue() + 1);
				if (timeProgressBar.getValue() == 100) {
					timeProgressBar.setValue(0);
				}
				// 原来这里是对窗口组件的刷新，现在这里被注释掉了
				// javax.swing.SwingUtilities.updateComponentTreeUI(new
				// Window());
			}

		}, 1000, 20);

		// 这里是对于窗口的更新的一个专门的Timer控件
		new Timer().schedule(new TimerTask() {
			public void run() {

				// 对于队列地方的定时刷新功能
				((DefaultTableModel) orderOnJScrollPaneJTable.getModel())
						.getDataVector().removeAllElements(); // 清空当前JTable表格
				ProcessDao pd1 = new ProcessDao();
				List<OSProcess> ops = pd1.findexecute();
				for (OSProcess op : ops) {
					Vector<String> row = new Vector<String>();
					row.add(op.getNoteName());
					row.add(op.getId());
					row.add(op.getRamNum());
					row.add(op.getDiskNum());
					row.add("1");
					tm.addRow(row);
				}

				// 对于内存和磁盘的颜色进行及时的更新
				ProcessDao pd = new ProcessDao();
				List<String> diskNums = pd.getDiskNum();
				List<String> ramNums = pd.getRamNum();
				List<String> rrns = pd.getRunningRamNum();

				// 为了是磁盘的状态时最新，于是在这里首先初始化磁盘和内存的值，就是放在循环里面先全部变成灰色，之后再交给在xml掉出来的数据处理
				for (int i = 3; i < 48; i++) {
					disks[i].setBackground(Color.LIGHT_GRAY);
				}
				for (int i = 3; i < 56; i++) {
					momeries[i].setBackground(Color.LIGHT_GRAY);
				}

				// 在xml数据中取出的有内容的磁盘变成蓝色,并且记录其具体的值
				for (String str : diskNums) {
					disks[Integer.parseInt(str)].setBackground(Color.GREEN);
				}

				// 把占据的内存编程蓝色
				for (String str : ramNums) {
					momeries[Integer.parseInt(str)].setBackground(Color.GREEN);
				}
				// 把正在执行的内存编程红色
				for (String str : rrns) {
					momeries[Integer.parseInt(str)].setBackground(Color.PINK);
				}

				// 对于Jtree树状展示磁盘的地方进行刷新
				graphicsjTree.revalidate();
				graphicsjTree.repaint();
				graphicsjTree.removeAll();
				graphicsjTree.treeDidChange();

			}
		}, 1000, 200);

		// JFrame.setDefaultLookAndFeelDecorated(true);// 使用最新的SWING外观
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Window thisClass = new Window();
				Menus menus = new Menus();
				JMenuBar menuBar = menus.getMenuBar();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				FrameUtils.center(thisClass);
				thisClass.setJMenuBar(menuBar);
				thisClass.setIconImage(StaticImages.getWinImage());
				thisClass.setVisible(true);
			}
		});
	}

	public Window() {
		super();
		initialize();
	}

	private void initialize() {
		// 初始化窗口的时候也一并初始化磁盘模块
		Disk.getInstance();
		Memory.getInstance();
		this.setSize(800, 600);
		this.setContentPane(getJContentPane());
		this.setTitle("MiniOS");
		this.setResizable(false);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getWiewPanel(), null);
			jContentPane.add(getDiskPanel(), null);
			jContentPane.add(getTeminalPanel(), null);
			jContentPane.add(getProcessPanel(), null);
			jContentPane.add(getAdapterPanel(), null);
		}
		return jContentPane;
	}
}
