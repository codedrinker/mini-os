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

	// �˵���
	private JMenuBar menuBar = new JMenuBar();

	// ButtonGroup
	// �����еĵ�ѡ�����棬��������������棬��Ȼ�𲻵��󶨵�һ���Ч��
	private ButtonGroup bg = new ButtonGroup();

	// ���˵�
	private JMenu systemMenu = new JMenu("ϵͳ");
	private JMenu setMenu = new JMenu("����");
	private JMenu manageMenu = new JMenu("����");
	private JMenu styleMenu = new JMenu("���");
	private JMenu helpMenu = new JMenu("����");
	private JMenu aboutMeMenu = new JMenu("����");

	// һ���˵�
	// ϵͳ
	private JMenuItem shutdown = new JMenuItem("�ػ�");
	private JMenuItem restart = new JMenuItem("����");
	private JMenuItem logoff = new JMenuItem("ע��");

	// ����
	private JMenu setProcess = new JMenu("����");
	private JMenu setDisc = new JMenu("����");

	// ����
	private JMenu disc = new JMenu("����");
	private JMenu memory = new JMenu("�ڴ�");
	private JMenu equipment = new JMenu("�豸");
	// ����
	private JMenuItem helpMenuItem = new JMenuItem("�����ĵ�");
	// ����
	private JMenuItem askonline = new JMenuItem("������ѯ");
	private JMenuItem version = new JMenuItem("����汾");

	// ���

	private JRadioButtonMenuItem fashionItem = new JRadioButtonMenuItem("ʱ��");
	private JRadioButtonMenuItem oldItem = new JRadioButtonMenuItem("����");
	private JRadioButtonMenuItem classicItem = new JRadioButtonMenuItem("����");

	// �����˵�
	// ����->����
	private JRadioButtonMenuItem sp1 = new JRadioButtonMenuItem("�Ƚ��ȳ��㷨");
	private JRadioButtonMenuItem sp2 = new JRadioButtonMenuItem("�����Ӧ�����ȵ����㷨");
	private JRadioButtonMenuItem sp3 = new JRadioButtonMenuItem("���ȼ������㷨");
	private JRadioButtonMenuItem sp4 = new JRadioButtonMenuItem("ʱ��Ƭ��ת�����㷨");

	// ����->����
	private JRadioButtonMenuItem sd1 = new JRadioButtonMenuItem("�����ȷ�������㷨");
	private JRadioButtonMenuItem sd2 = new JRadioButtonMenuItem("��̲���ʱ�������㷨");
	private JRadioButtonMenuItem sd3 = new JRadioButtonMenuItem("ɨ���㷨");

	// ����->����
	private JMenuItem md1 = new JMenuItem("����");
	private JMenuItem md2 = new JMenuItem("��ʽ��");

	// ����->�ڴ�
	private JMenuItem cleaner = new JMenuItem("����");
	private JMenuItem formatting = new JMenuItem("����");

	// ����->�豸
	private JMenuItem me1 = new JMenuItem("�޸��豸");
	private JMenuItem me2 = new JMenuItem("ɾ���豸");

	public Menus() {
		// ϵͳ
		shutdown.addActionListener(new ShutdownListener());
		restart.addActionListener(new HandleListener());
		logoff.addActionListener(new HandleListener());
		systemMenu.add(shutdown);
		systemMenu.add(restart);
		systemMenu.add(logoff);
		menuBar.add(systemMenu);

		// ����
		// ����

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

		// ����

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

		// �����ð�ť��ӽ��˵���
		menuBar.add(setMenu);

		// ����
		// ����
		md1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ProcessDao pd = new ProcessDao();
				pd.diskTrim();
				JOptionPane.showMessageDialog(new JFrame(), "��������ɹ�");
			}
		});
		md2.addActionListener(new ClearDiskLinstener());
		disc.add(md1);
		disc.add(md2);
		manageMenu.add(disc);

		// �ڴ�
		cleaner.addActionListener(new SortMemoryLinstener());
		formatting.addActionListener(new ClearDiskLinstener());
		memory.add(cleaner);
		memory.add(formatting);
		manageMenu.add(memory);

		// �豸

		me1.addActionListener(new ClearDiskLinstener());
		me2.addActionListener(new ClearDiskLinstener());
		equipment.add(me1);
		equipment.add(me2);
		manageMenu.add(equipment);

		// ������˵���ӽ��˵���
		menuBar.add(manageMenu);

		// ���
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

		// ����

		helpMenuItem.addActionListener(new DocumentListener());
		helpMenu.add(helpMenuItem);
		menuBar.add(helpMenu);
		// ����
		askonline.addActionListener(new AboutListener());
		version.addActionListener(new VesionListener());
		// ע�͵���������ѯ
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

	// ע��
	public JMenuItem getLogoff() {
		return logoff;
	}

	// �ػ�
	public JMenuItem getShutdown() {
		return shutdown;
	}

	// ����
	public JMenuItem getRestart() {
		return restart;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	// ����һ����������ʵ������û��ʵ�ַ�����������ʾ
	private class HandleListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFrame.setDefaultLookAndFeelDecorated(true);// ʹ�����µ�SWING���
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "��δ��ӹ���!!!���½�");

		}
	}

	// ����������ļ�����
	private class StyleListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			MenuDao md = new MenuDao();
			if (fashionItem.equals(e.getSource())) {
				md.setStyle(1 + "");
				JOptionPane.showMessageDialog(null, "���óɹ�����������֮����Ч");
			} else if (oldItem.equals(e.getSource())) {
				md.setStyle(2 + "");
				JOptionPane.showMessageDialog(null, "���óɹ�����������֮����Ч");
			} else {
				md.setStyle(3 + "");
				JOptionPane.showMessageDialog(null, "���óɹ�����������֮����Ч");
			}
		}

	}

	// ���ô����ļ�����
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

	// ��ӹػ��ļ�����
	private class ShutdownListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int status = JOptionPane.showConfirmDialog(null, "�ر���ʾ", "ȷ���رճ���",
					JOptionPane.YES_NO_OPTION);
			if (status == 0)
				System.exit(0);
		}

	}

	// ��Ӹ�ʽ�����̵Ĳ���
	private class ClearDiskLinstener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "�˲����Ƚ�Σ�գ������ڲ�����");
		}
	}

	// ��������ڴ�ĳ�����ڲ���
	private class SortMemoryLinstener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ProcessDao pd = new ProcessDao();
			pd.ramTrim();
			JOptionPane.showMessageDialog(null, "�ڴ�����ɹ�");
		}

	}

	// ��Ӱ����ĵ��ĳ����ڲ���
	private class DocumentListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Runtime.getRuntime().exec(
						"hh " + "\"" + PathUtils.getCHMPath() + "\"");
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "���ĵ�ʧ�ܣ��������װ·��");
			}
		}

	}

	// �����ϵ���ǵĳ����ڲ���
	private class AboutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Runtime.getRuntime().exec(
						"hh " + "\"" + PathUtils.getCHMPath() + "\"");
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "���ĵ�ʧ�ܣ��������װ·��");
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