package cn.hicc.net.porlet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingWorker;

import cn.hicc.net.dao.MenuDao;
import cn.hicc.net.dao.ProcessDao;
import cn.hicc.net.utils.FrameUtils;
import cn.hicc.net.utils.PathUtils;

public class Menus implements ActionListener {

	// 菜单栏
	private JMenuBar menuBar = new JMenuBar();

	// ButtonGroup
	// 在所有的单选框里面，必须放在这里里面，不然起不到绑定到一起的效果
	private ButtonGroup bg = new ButtonGroup();

	// 主菜单
	private JMenu systemMenu = new JMenu("系统");
	private JMenu setMenu = new JMenu("设置");
	private JMenu manageMenu = new JMenu("管理");
	private JMenu styleMenu = new JMenu("风格");
	private JMenu helpMenu = new JMenu("帮助");
	private JMenu aboutMeMenu = new JMenu("关于");

	// 一级菜单
	// 系统
	private JMenuItem shutdown = new JMenuItem("关机");
	private JMenuItem restart = new JMenuItem("重启");
	private JMenuItem logoff = new JMenuItem("注销");

	// 设置
	private JMenu setProcess = new JMenu("进程");
	private JMenu setDisc = new JMenu("磁盘");

	// 管理
	private JMenu disc = new JMenu("磁盘");
	private JMenu memory = new JMenu("内存");
	private JMenu equipment = new JMenu("设备");
	// 帮助
	private JMenuItem helpMenuItem = new JMenuItem("帮助文档");
	// 关于
	private JMenuItem askonline = new JMenuItem("在线咨询");
	private JMenuItem version = new JMenuItem("程序版本");

	// 风格

	private JRadioButtonMenuItem fashionItem = new JRadioButtonMenuItem("时尚");
	private JRadioButtonMenuItem oldItem = new JRadioButtonMenuItem("怀旧");
	private JRadioButtonMenuItem classicItem = new JRadioButtonMenuItem("经典");

	// 二级菜单
	// 设置->进程
	private JRadioButtonMenuItem sp1 = new JRadioButtonMenuItem("先进先出算法");
	private JRadioButtonMenuItem sp2 = new JRadioButtonMenuItem("最高响应比优先调度算法");
	private JRadioButtonMenuItem sp3 = new JRadioButtonMenuItem("优先级调度算法");
	private JRadioButtonMenuItem sp4 = new JRadioButtonMenuItem("时间片轮转调度算法");

	// 设置->磁盘
	private JRadioButtonMenuItem sd1 = new JRadioButtonMenuItem("先来先服务调度算法");
	private JRadioButtonMenuItem sd2 = new JRadioButtonMenuItem("最短查找时间优先算法");
	private JRadioButtonMenuItem sd3 = new JRadioButtonMenuItem("扫描算法");

	// 管理->磁盘
	private JMenuItem md1 = new JMenuItem("整理");
	private JMenuItem md2 = new JMenuItem("格式化");

	// 管理->内存
	private JMenuItem cleaner = new JMenuItem("整理");
	private JMenuItem formatting = new JMenuItem("清理");

	// 管理->设备
	private JMenuItem me1 = new JMenuItem("修改设备");
	private JMenuItem me2 = new JMenuItem("删除设备");

	public Menus() {
		// 系统
		shutdown.addActionListener(new ShutdownListener());
		restart.addActionListener(new HandleListener());
		logoff.addActionListener(new HandleListener());
		systemMenu.add(shutdown);
		systemMenu.add(restart);
		systemMenu.add(logoff);
		menuBar.add(systemMenu);

		// 设置
		// 进程

		sp1.addActionListener(new HandleListener());
		sp2.addActionListener(new HandleListener());
		sp3.addActionListener(new HandleListener());
		sp4.addActionListener(new HandleListener());
		bg.add(sp1);
		bg.add(sp2);
		bg.add(sp3);
		bg.add(sp4);
		setProcess.add(sp1);
		setProcess.add(sp2);
		setProcess.add(sp3);
		setProcess.add(sp4);
		setMenu.add(setProcess);

		// 磁盘

		sd1.addActionListener(new HandleListener());
		sd2.addActionListener(new HandleListener());
		sd3.addActionListener(new HandleListener());
		bg.add(sd1);
		bg.add(sd2);
		bg.add(sd3);
		setDisc.add(sd1);
		setDisc.add(sd2);
		setDisc.add(sd3);
		setMenu.add(setDisc);

		// 将设置按钮添加进菜单栏
		menuBar.add(setMenu);

		// 管理
		// 磁盘
		md1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProcessDao pd = new ProcessDao();
				pd.diskTrim();
				JOptionPane.showMessageDialog(new JFrame(), "磁盘整理成功");
			}
		});
		md2.addActionListener(new ClearDiskLinstener());
		disc.add(md1);
		disc.add(md2);
		manageMenu.add(disc);

		// 内存
		cleaner.addActionListener(new SortMemoryLinstener());
		formatting.addActionListener(new ClearDiskLinstener());
		memory.add(cleaner);
		memory.add(formatting);
		manageMenu.add(memory);

		// 设备

		me1.addActionListener(new ClearDiskLinstener());
		me2.addActionListener(new ClearDiskLinstener());
		equipment.add(me1);
		equipment.add(me2);
		manageMenu.add(equipment);

		// 将管理菜单添加进菜单栏
		menuBar.add(manageMenu);

		// 风格
		fashionItem.addActionListener(new StyleListener());
		oldItem.addActionListener(new StyleListener());
		classicItem.addActionListener(new StyleListener());
		bg.add(fashionItem);
		bg.add(oldItem);
		bg.add(classicItem);
		styleMenu.add(fashionItem);
		styleMenu.add(oldItem);
		styleMenu.add(classicItem);
		menuBar.add(styleMenu);

		// 帮助

		helpMenuItem.addActionListener(new DocumentListener());
		helpMenu.add(helpMenuItem);
		menuBar.add(helpMenu);
		// 关于
		askonline.addActionListener(new AboutListener());
		version.addActionListener(new VesionListener());
		// 注释掉了在线咨询
		// aboutMeMenu.add(askonline);
		aboutMeMenu.add(version);
		menuBar.add(aboutMeMenu);

	}

	public JMenu getSystemMenu() {
		return systemMenu;
	}

	public JMenu getSetMenu() {
		return setMenu;
	}

	public JMenu getHelpMenu() {
		return helpMenu;
	}

	public JMenu getAboutMeMenu() {
		return aboutMeMenu;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	// 注销
	public JMenuItem getLogoff() {
		return logoff;
	}

	// 关机
	public JMenuItem getShutdown() {
		return shutdown;
	}

	// 重启
	public JMenuItem getRestart() {
		return restart;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	// 定义一个监听器类实现所有没有实现方法的友情提示
	private class HandleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame.setDefaultLookAndFeelDecorated(true);// 使用最新的SWING外观
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "暂未添加功能!!!请谅解");

		}
	}

	// 设置软件风格的监听类
	private class StyleListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MenuDao md = new MenuDao();
			if (fashionItem.equals(e.getSource())) {
				md.setStyle(1 + "");
				JOptionPane.showMessageDialog(null, "设置成功，重启程序之后生效");
			} else if (oldItem.equals(e.getSource())) {
				md.setStyle(2 + "");
				JOptionPane.showMessageDialog(null, "设置成功，重启程序之后生效");
			} else {
				md.setStyle(3 + "");
				JOptionPane.showMessageDialog(null, "设置成功，重启程序之后生效");
			}
		}

	}

	// 设置从启的监听类
	private class RestartListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SwingWorker<Integer, String>() {

				@Override
				protected Integer doInBackground() throws Exception {

					return null;
				}

				@Override
				protected void done() {
					super.done();
				}

				@Override
				protected void process(List<String> chunks) {
					super.process(chunks);
				}

			};
		}

	}

	// 添加关机的监听类
	private class ShutdownListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int status = JOptionPane.showConfirmDialog(null, "关闭提示", "确定关闭程序",
					JOptionPane.YES_NO_OPTION);
			if (status == 0)
				System.exit(0);
		}

	}

	// 添加格式化磁盘的操作
	private class ClearDiskLinstener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "此操作比较危险，程序内部禁用");
		}
	}

	// 添加整理内存的程序的内部类
	private class SortMemoryLinstener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ProcessDao pd = new ProcessDao();
			pd.ramTrim();
			JOptionPane.showMessageDialog(null, "内存整理成功");
		}

	}

	// 添加帮助文档的程序内部类
	private class DocumentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Runtime.getRuntime().exec(
						"hh " + "\"" + PathUtils.getCHMPath() + "\"");
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "打开文档失败，请检查程序安装路径");
			}
		}

	}

	// 添加联系我们的程序内部类
	private class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Runtime.getRuntime().exec(
						"hh " + "\"" + PathUtils.getCHMPath() + "\"");
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "打开文档失败，请检查程序安装路径");
			}
		}

	}

	private class VesionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			FrameUtils.center(new Version());
		}
	}

}