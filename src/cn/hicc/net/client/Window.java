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

	// ����JTabbedPane
	private JTabbedPane viewJTabbedPane = null;
	// �����û��ӿڵ�JTabbedPane
	private JTabbedPane userImplTabbedPane = null;

	// ����Ķ�������ľ������к���������
	private JPanel blockJPanel = null;
	private JPanel orderJPanel = null;

	// �����û���ͼ�νӿں�����ӿ�
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

	// ��Ӿ������к��������е�jtable�͹�����
	private JScrollPane blockJScrollPane = null;
	private JScrollPane orderJScrollPane = null;
	private static JTable blockOnJScrollPaneJTable = null;
	private static JTable orderOnJScrollPaneJTable = null;

	// ������̵İ�ť
	private static JButton disks[] = Disk.getInstance().getDisks();
	private static JButton momeries[] = Memory.getInstance().getMomeries();

	// �������Ĺ���userImplTabbedPane�ķ���
	private JTabbedPane getUserImplTabbedPane() {
		if (userImplTabbedPane == null) {
			userImplTabbedPane = new JTabbedPane();
			// viewJTabbedPane.setTabPlacement(JTabbedPane.TOP);// ���ö����л���ʽ
			userImplTabbedPane.setBounds(new Rectangle(0, 0, 285, 202));

			userImplTabbedPane.addTab("ͼ�νӿ�", getGraphicsImplJPane());
			userImplTabbedPane.addTab("����ӿ�", getCommandImpJPane());
		}
		return userImplTabbedPane;
	}

	// �û���ͼ�νӿڵ�ѡ�����
	private JPanel getGraphicsImplJPane() {
		if (graphicsImplJPane == null) {
			graphicsImplJPane = new JPanel();
			graphicsImplJPane.setLayout(null);
			graphicsImplJPane.add(getGraphcisjScrollBar(), null);
		}
		return graphicsImplJPane;
	}

	// ���һ��ͼ�νӿڵĹ�����
	private JScrollPane getGraphcisjScrollBar() {
		if (graphcisjScrollBar == null) {
			graphcisjScrollBar = new JScrollPane();
			graphcisjScrollBar.setBounds(new Rectangle(0, 2, 280, 170));
			graphcisjScrollBar.setViewportView(getGraphicsjTree());
		}
		return graphcisjScrollBar;
	}

	// ���һ��ͼ�νӿڵĹ�����
	private JScrollPane getCommandjScrollBar() {
		if (commandjScrollBar == null) {
			commandjScrollBar = new JScrollPane();
			commandjScrollBar.setBounds(new Rectangle(0, 2, 280, 170));
			commandjScrollBar.setViewportView(getCommandJTextArea());
		}
		return commandjScrollBar;
	}

	// ���ͼ�νӿڵ����νṹ
	private JTree getGraphicsjTree() {
		if (graphicsjTree == null) {
			FileList dt = new FileList();
			graphicsjTree = dt.getTree();
		}
		return graphicsjTree;

	}

	// �û�������ӿڵ�ѡ�����
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

	// ����������ӿڵ����Ƶĺ�ɫ������˵���ʾ
	private JTextArea getCommandJTextArea() {
		if (commandJTextArea == null) {
			commandJTextArea = new JTextArea();
			commandJTextArea.setBounds(new Rectangle(0, 2, 285, 220));// �������õ��Ǻ�jtabedpane�Ĵ�С��һ����
			commandJTextArea.setBackground(Color.BLACK);// ���ñ���ɫΪ��ɫ
			commandJTextArea.setForeground(Color.WHITE);// ����ǰ��ɫΪ��ɫ
			commandJTextArea.setCaretColor(Color.WHITE);// ���ù����ɫ
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
						.append("\n �����ļ���create + �ո�  + <filepath>\n ɾ���ļ���delete + �ո�  + <filepath>\n "
								+ "ִ���ļ���run  + �ո�  + <filepath>\n �鿴�ļ���type  + �ո�  + <filepath>\n ����Ŀ¼��"
								+ "makdir + �ո�  +<filepath>\n ɾ��Ŀ¼��deldir + �ո�  + <filepath>\n�����Ļ��cls\t������help");
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

	// �������̵����
	// �������Ĺ���viewJTabbedPane�ķ���
	private JTabbedPane getViewJTabbedPane() {
		if (viewJTabbedPane == null) {
			viewJTabbedPane = new JTabbedPane();
			// viewJTabbedPane.setTabPlacement(JTabbedPane.TOP);// ���ö����л���ʽ
			viewJTabbedPane.setBounds(new Rectangle(0, 0, 320, 202));

			viewJTabbedPane.addTab("��������", getOrderJPanel());
			viewJTabbedPane.addTab("��������", getBlockJPanel());
		}
		return viewJTabbedPane;
	}

	// �������̵����
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

	// ���������Ь�Ĺ�����
	private JScrollPane getBlockJScrollPane() {
		if (blockJScrollPane == null) {
			blockJScrollPane = new JScrollPane();
			blockJScrollPane.setBounds(new Rectangle(0, 3, 316, 169));
			blockJScrollPane.setViewportView(getBlockOnJScrollPaneJTable());
		}
		return blockJScrollPane;
	}

	// ��Ӿ�����Ь�Ĺ�����
	private JScrollPane getOrderJScrollPane() {
		if (orderJScrollPane == null) {
			orderJScrollPane = new JScrollPane();
			orderJScrollPane.setBounds(new Rectangle(0, 3, 316, 169));
			orderJScrollPane.setViewportView(getOrderOnJScrollPaneJTable());
		}
		return orderJScrollPane;
	}

	// ��������Ա��
	private JTable getBlockOnJScrollPaneJTable() {
		if (blockOnJScrollPaneJTable == null) {
			DefaultTableModel tm1 = new DefaultTableModel();
			String[] titles = { "��������", "����ID", "�ڴ�ID", "����ID", "���ȼ�" };
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

	// ��Ӿ������еı��
	private JTable getOrderOnJScrollPaneJTable() {
		if (orderOnJScrollPaneJTable == null) {
			tm = new DefaultTableModel();
			String[] titles = { "��������", "����ID", "�ڴ�ID", "����ID", "���ȼ�" };
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

	// ��ϵͳ��������ߵ���������չʾ�������к��������еİ��
	private JPanel getWiewPanel() {
		if (wiewPanel == null) {
			wiewPanel = new JPanel();
			wiewPanel.setLayout(null);
			wiewPanel.setBounds(new Rectangle(13, 15, 320, 202));
			// wiewPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			// ����ע�͵��˽��̵İ��������panel�ı߿���ɫ
			wiewPanel.add(getViewJTabbedPane());

		}
		return wiewPanel;
	}

	// ��ϵͳ�����м�Ĳ��֣���������ʱ��Ƭ��ת���������еĽ��̣�Ŀǰ�ڴ��еı�����ֵ��������������������ٴ���ӹ����豸��ʹ�����
	private JPanel getDiskPanel() {
		if (diskPanel == null) {
			processValueJLabel = new JLabel();
			processValueJLabel.setBounds(new Rectangle(14, 130, 106, 25));
			processValueJLabel.setText("����ֵ");
			processJLabel = new JLabel();
			processJLabel.setBounds(new Rectangle(14, 72, 106, 25));
			processJLabel.setText("���н���");
			timeJLabel = new JLabel();
			timeJLabel.setBounds(new Rectangle(14, 14, 106, 25));
			timeJLabel.setText("ʱ��Ƭ");
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

	// ��������ӿں�ͼ�νӿ�ת����ģ��
	private JPanel getTeminalPanel() {
		if (teminalPanel == null) {
			teminalPanel = new JPanel();
			teminalPanel.setLayout(null);
			teminalPanel.setBounds(new Rectangle(495, 15, 285, 202));

			// ��ע�͵�����ı߿���ɫ��Ϊ����ʱ������
			// teminalPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			// ��ӽӿ�ѡ������
			teminalPanel.add(getUserImplTabbedPane());
		}

		return teminalPanel;
	}

	// �м��һ���󲿷ֵ��������ڴ�ʹ��̵�ģ��
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

	// �����沿�ֵ�����������Χ�豸��ģ��
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

	// �����ʱ��Ƭ��ת������
	private JProgressBar getTimeProgressBar() {
		if (timeProgressBar == null) {
			timeProgressBar = new JProgressBar();
			// ע�͵��˽������İٷֱ�
			// timeProgressBar.setStringPainted(true);
			timeProgressBar.setBounds(new Rectangle(14, 38, 106, 25));
			timeProgressBar.setValue(0);
		}
		return timeProgressBar;
	}

	// ���������ִ�н��̵��ı���
	private JTextField getProcessJTextField() {
		if (processJTextField == null) {
			processJTextField = new JTextField();
			processJTextField.setBounds(new Rectangle(14, 96, 104, 25));
		}
		return processJTextField;
	}

	// ���������ִ�е��ڴ������ֵ����ʾ�ı���
	private JTextField getProcessValueJTextField() {
		if (processValueJTextField == null) {
			processValueJTextField = new JTextField();
			processValueJTextField.setBounds(new Rectangle(14, 154, 104, 25));
		}
		return processValueJTextField;
	}

	// ����ڴ�ģ��
	private JPanel getMemoryJPanel() {
		if (memoryJPanel == null) {
			momeryTitleJLabel = new JLabel();
			momeryTitleJLabel.setBounds(new Rectangle(10, 5, 55, 25));
			momeryTitleJLabel.setText("�ڴ�");
			memoryJPanel = new JPanel();
			memoryJPanel.setLayout(null);
			memoryJPanel.setBounds(new Rectangle(12, 12, 306, 182));
			memoryJPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			memoryJPanel.add(momeryTitleJLabel, null);
			memoryJPanel.add(getMomeryBlockJPanel(), null);
		}
		return memoryJPanel;
	}

	// ���ͼ��˵��ģ��
	private JPanel getIllJPanel() {
		if (illJPanel == null) {
			soJLabel = new JLabel();
			soJLabel.setBounds(new Rectangle(10, 130, 60, 25));
			soJLabel.setText("ϵͳռ��");
			processingJLabel = new JLabel();
			processingJLabel.setBounds(new Rectangle(10, 100, 60, 25));
			processingJLabel.setText("��������");
			freeJLabel = new JLabel();
			freeJLabel.setBounds(new Rectangle(10, 70, 60, 25));
			freeJLabel.setText("��        ��");
			usingJLabel = new JLabel();
			usingJLabel.setBounds(new Rectangle(10, 40, 60, 25));
			usingJLabel.setText("ʹ        ��");
			illTitleJLabel = new JLabel();
			illTitleJLabel.setBounds(new Rectangle(10, 5, 55, 25));
			illTitleJLabel.setText("ͼ��");
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

	// ��Ӵ��̹���ģ��
	private JPanel getDiskJPanel() {
		if (diskJPanel == null) {
			diskTitleJLabel = new JLabel();
			diskTitleJLabel.setBounds(new Rectangle(10, 5, 55, 25));
			diskTitleJLabel.setText("����");
			diskJPanel = new JPanel();
			diskJPanel.setLayout(null);
			diskJPanel.setBounds(new Rectangle(484, 12, 269, 182));
			diskJPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			diskJPanel.add(diskTitleJLabel, null);
			diskJPanel.add(getDiskBlockJPanel(), null);
		}
		return diskJPanel;
	}

	// ����ڴ�ʱ���һ��button�ĵط�
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

	// �������һ�ѷ���ĵط�
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

	// ռ�õ�ͼ��
	private JButton getUsingJButton() {
		if (usingJButton == null) {
			usingJButton = new JButton();
			usingJButton.setBounds(new Rectangle(80, 40, 50, 25));
			usingJButton.setBackground(Color.GREEN);
		}
		return usingJButton;
	}

	// ���е�ͼ��
	private JButton getFreeJButton() {
		if (freeJButton == null) {
			freeJButton = new JButton();
			freeJButton.setBounds(new Rectangle(80, 70, 50, 25));
			freeJButton.setBackground(Color.LIGHT_GRAY);
		}
		return freeJButton;
	}

	// ���������е�ͼ��
	private JButton getProcessingJButton() {
		if (processingJButton == null) {
			processingJButton = new JButton();
			processingJButton.setBounds(new Rectangle(80, 100, 50, 25));
			processingJButton.setBackground(Color.PINK);
		}
		return processingJButton;
	}

	// ϵͳռ�õ�ͼ��
	private JButton getOsJButton() {
		if (osJButton == null) {
			osJButton = new JButton();
			osJButton.setBounds(new Rectangle(80, 130, 50, 25));
			osJButton.setBackground(Color.BLACK);
		}
		return osJButton;
	}

	// ��ӵ��豸�ѣ�ͨ����ť��ӵ�A
	private JButton getAdapterAButton() {
		if (adapterAButton == null) {
			adapterAButton = new JButton();
			adapterAButton.setBounds(new Rectangle(73, 8, 70, 70));
			adapterAButton
					.setIcon((new ImageIcon(StaticImages.getMouseImage())));

		}
		return adapterAButton;
	}

	// �豸B
	private JButton getAdapterBButton() {
		if (adapterBButton == null) {
			adapterBButton = new JButton();
			adapterBButton.setBounds(new Rectangle(241, 8, 70, 70));
			adapterBButton.setIcon((new ImageIcon(StaticImages
					.getKeyboardImage())));
		}
		return adapterBButton;
	}

	// �豸C
	private JButton getAdapterCButton() {
		if (adapterCButton == null) {
			adapterCButton = new JButton();
			adapterCButton.setBounds(new Rectangle(412, 8, 70, 70));
			adapterCButton.setIcon((new ImageIcon(StaticImages
					.getPrinterImage())));
		}
		return adapterCButton;
	}

	// �豸D
	private JButton getAdapterDButton() {
		if (adapterDButton == null) {
			adapterDButton = new JButton();
			adapterDButton.setBounds(new Rectangle(586, 8, 70, 70));
			adapterDButton
					.setIcon((new ImageIcon(StaticImages.getVideoImage())));
		}
		return adapterDButton;
	}

	// �������ķ�������ʵ���豸������

	public void buttonEnable() {
		adapterAButton.setEnabled(false);
		adapterBButton.setEnabled(false);
		adapterCButton.setEnabled(false);
		adapterDButton.setEnabled(false);
	}

	public static void main(String[] args) {

		// ���þ���Ĵ������Եĳ����
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

		// ���ʱ��Ƭ�Ĺ����¼�
		new Timer().schedule(new TimerTask() {
			public void run() {
				JFrame.setDefaultLookAndFeelDecorated(true);
			}
		}, 1000, 20);

		// ���ʱ��Ƭ�Ĺ����¼�
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
				// ԭ�������ǶԴ��������ˢ�£��������ﱻע�͵���
				// javax.swing.SwingUtilities.updateComponentTreeUI(new
				// Window());
			}

		}, 1000, 20);

		// �����Ƕ��ڴ��ڵĸ��µ�һ��ר�ŵ�Timer�ؼ�
		new Timer().schedule(new TimerTask() {
			public void run() {

				// ���ڶ��еط��Ķ�ʱˢ�¹���
				((DefaultTableModel) orderOnJScrollPaneJTable.getModel())
						.getDataVector().removeAllElements(); // ��յ�ǰJTable���
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

				// �����ڴ�ʹ��̵���ɫ���м�ʱ�ĸ���
				ProcessDao pd = new ProcessDao();
				List<String> diskNums = pd.getDiskNum();
				List<String> ramNums = pd.getRamNum();
				List<String> rrns = pd.getRunningRamNum();

				// Ϊ���Ǵ��̵�״̬ʱ���£��������������ȳ�ʼ�����̺��ڴ��ֵ�����Ƿ���ѭ��������ȫ����ɻ�ɫ��֮���ٽ�����xml�����������ݴ���
				for (int i = 3; i < 48; i++) {
					disks[i].setBackground(Color.LIGHT_GRAY);
				}
				for (int i = 3; i < 56; i++) {
					momeries[i].setBackground(Color.LIGHT_GRAY);
				}

				// ��xml������ȡ���������ݵĴ��̱����ɫ,���Ҽ�¼������ֵ
				for (String str : diskNums) {
					disks[Integer.parseInt(str)].setBackground(Color.GREEN);
				}

				// ��ռ�ݵ��ڴ�����ɫ
				for (String str : ramNums) {
					momeries[Integer.parseInt(str)].setBackground(Color.GREEN);
				}
				// ������ִ�е��ڴ��̺�ɫ
				for (String str : rrns) {
					momeries[Integer.parseInt(str)].setBackground(Color.PINK);
				}

				// ����Jtree��״չʾ���̵ĵط�����ˢ��
				graphicsjTree.revalidate();
				graphicsjTree.repaint();
				graphicsjTree.removeAll();
				graphicsjTree.treeDidChange();

			}
		}, 1000, 200);

		// JFrame.setDefaultLookAndFeelDecorated(true);// ʹ�����µ�SWING���
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
		// ��ʼ�����ڵ�ʱ��Ҳһ����ʼ������ģ��
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
