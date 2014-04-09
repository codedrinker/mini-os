package cn.hicc.net.porlet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import cn.hicc.net.dao.ProcessDao;
import cn.hicc.net.model.OSProcess;

public class Notebook extends JFrame {
	private static final long serialVersionUID = 1L;

	private JMenuItem menuOpen;
	private JMenuItem menuSave;
	private JMenuItem menuSaveAs;
	private JMenuItem menuClose;

	private JMenu editMenu;
	private JMenuItem menuCut;
	private JMenuItem menuCopy;
	private JMenuItem menuPaste;

	private JTextArea textArea;
	private JLabel stateBar;
	private JFileChooser fileChooser;

	private JPopupMenu popUpMenu;

	private String filePath;

	public Notebook() {
		super("�༭");
		setUpUIComponent();
		setUpEventListener();
		setVisible(true);
	}

	public Notebook(String filePath) {
		super("�༭");
		this.filePath = filePath;
		setUpUIComponent();
		setUpEventListener();
		setVisible(true);
	}

	private void setUpUIComponent() {
		// �õ���ǰϵͳ�ֱ���
		Dimension srcDim = Toolkit.getDefaultToolkit().getScreenSize();
		int a = (int) (srcDim.width - 300) / 2;
		int b = (int) (srcDim.height - 400) / 2;
		this.setBounds(a, b, 300, 400);// �ô��ھ���

		// �˵���
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("�ļ�");// ���á��ļ����˵�
		menuOpen = new JMenuItem("��");// ����"�ļ�"���Ӳ˵�"��"
		menuOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_MASK));// ����"��"�Ŀ�ݼ�

		menuSave = new JMenuItem("����");// ����"�ļ�"���Ӳ˵�"����"
		menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));// ����"����"�Ŀ�ݼ�

		menuSaveAs = new JMenuItem("���Ϊ");// "�ļ�"���Ӳ˵�"���Ϊ"

		menuClose = new JMenuItem("�ر�");// ����"�ļ�"���Ӳ˵�"�ر�"
		menuClose.setAccelerator // ����"�ر�"�Ŀ�ݼ�
				(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));

		fileMenu.add(menuOpen);
		fileMenu.addSeparator(); // �ָ���
		fileMenu.add(menuSave);
		// fileMenu.add(menuSaveAs);
		fileMenu.addSeparator(); // �ָ���
		fileMenu.add(menuClose);

		JMenu editMenu = new JMenu("�༭");// ���á��༭���˵�
		menuCut = new JMenuItem("����");// ����"�༭"���Ӳ˵�"����"
		menuCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_MASK)); // ����"�༭"�Ŀ�ݼ�

		menuCopy = new JMenuItem("����");// ����"�༭"���Ӳ˵�"����"
		menuCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_MASK)); // ����"����"�Ŀ�ݼ�

		menuPaste = new JMenuItem("ճ��");// ����"�༭"���Ӳ˵�"ճ��"
		menuPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_MASK));// ����"ճ��"�Ŀ�ݼ�
		editMenu.add(menuCut);
		editMenu.add(menuCopy);
		editMenu.add(menuPaste);

		menuBar.add(fileMenu);
		menuBar.add(editMenu);

		setJMenuBar(menuBar);

		textArea = new JTextArea();// ���ֱ༭����
		textArea.setFont(new Font("����", Font.PLAIN, 16));
		textArea.setLineWrap(true);
		JScrollPane panel = new JScrollPane(textArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		Container contentPane = getContentPane();
		contentPane.add(panel, BorderLayout.CENTER);

		// ״̬��
		stateBar = new JLabel("δ�޸�");
		stateBar.setHorizontalAlignment(SwingConstants.LEFT);
		stateBar.setBorder(BorderFactory.createEtchedBorder());
		contentPane.add(stateBar, BorderLayout.SOUTH);

		popUpMenu = editMenu.getPopupMenu();
		fileChooser = new JFileChooser();
		if (filePath != null) {
			open();
		}
	}

	private void setUpEventListener() {
		// ���´��ڹر�ť�¼�����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeFile();
			}
		});

		// �˵� - ��
		menuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFile();
			}
		});

		// �˵� - ����
		menuSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
		});

		// �˵� - ���Ϊ
		menuSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFileAs();
			}
		});

		// �˵� - �ر��ļ�
		menuClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFile();
			}
		});

		// �˵� - ����
		menuCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cut();
			}
		});

		// �˵� - ����
		menuCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
			}
		});

		// �˵� - ճ��
		menuPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paste();
			}
		});

		// �༭�������¼�
		textArea.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				processTextArea();
			}
		});

		// �༭������¼�
		textArea.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3)
					popUpMenu.show(editMenu, e.getX(), e.getY());
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					popUpMenu.setVisible(false);
			}
		});
	}

	private void openFile() {
		if (isCurrentFileSaved()) // �ļ��Ƿ�Ϊ����״̬
		{
			open(); // ��
		} else {
			// ��ʾ�Ի���
			int option = JOptionPane.showConfirmDialog(null, "�ļ����޸ģ��Ƿ񱣴棿",
					"�����ļ���", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null);
			switch (option) {
			case JOptionPane.YES_OPTION: // ȷ���ļ�����
			{
				saveFile(); // �����ļ�
				break;
			}
			case JOptionPane.NO_OPTION:// �����ļ�����
			{
				open();
				break;
			}
			}
		}
	}

	private boolean isCurrentFileSaved() {
		if (stateBar.getText().equals("δ�޸�")) {
			return true;
		} else {
			return false;
		}
	}

	private void open() {
		// fileChooser �� JFileChooser ��ʵ��
		// ��ʾ�ļ�ѡȡ�ĶԻ���

		if (this.filePath != null) {
			try {
				// ����ѡȡ���ļ�
				BufferedReader buf = new BufferedReader(new FileReader(
						new File(this.filePath)));

				setTitle(new File(this.filePath).toString());// �趨�ļ�����
				textArea.setText(""); // ���ǰһ���ļ�
				stateBar.setText("δ�޸�"); // �趨״̬��

				String lineSeparator = System.getProperty("line.separator");
				// ȡ��ϵͳ�����Ļ����ַ�
				String text;// ��ȡ�ļ������������ֱ༭��
				while ((text = buf.readLine()) != null) {
					textArea.append(text);
					textArea.append(lineSeparator);
				}

				buf.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "�����ļ�ʧ��",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			int option = fileChooser.showDialog(null, null);
			// ʹ���߰���ȷ�ϼ�
			if (option == JFileChooser.APPROVE_OPTION) {
				try {
					// ����ѡȡ���ļ�
					BufferedReader buf = new BufferedReader(new FileReader(
							fileChooser.getSelectedFile()));

					setTitle(fileChooser.getSelectedFile().toString());// �趨�ļ�����
					textArea.setText(""); // ���ǰһ���ļ�
					stateBar.setText("δ�޸�"); // �趨״̬��

					String lineSeparator = System.getProperty("line.separator");
					// ȡ��ϵͳ�����Ļ����ַ�
					String text;// ��ȡ�ļ������������ֱ༭��
					while ((text = buf.readLine()) != null) {
						textArea.append(text);
						textArea.append(lineSeparator);
					}

					buf.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.toString(), "�����ļ�ʧ��",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	private void saveFile() 
	{
		ProcessDao pd = new ProcessDao();
		OSProcess op = new OSProcess();
		op.setNoteId(filePath);
		op.setContent(textArea.getText());
		pd.update(op);
		File file = new File(getTitle());// �ӱ�����ȡ���ļ�����
		if (!file.exists()) // ��ָ�����ļ�������
		{
			saveFileAs(); // ִ�����Ϊ
		} else {
			try {
				// ����ָ�����ļ�
				BufferedWriter buf = new BufferedWriter(new FileWriter(file));
				buf.write(textArea.getText());// �����ֱ༭��������д���ļ�
				buf.close();
				stateBar.setText("δ�޸�"); // �趨״̬��Ϊδ�޸�
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "д���ļ�ʧ��",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void saveFileAs() {

		int option = fileChooser.showSaveDialog(null); // ��ʾ�ļ��Ի���
		if (option == JFileChooser.APPROVE_OPTION) // ���ȷ��ѡȡ�ļ�
		{
			File file = fileChooser.getSelectedFile(); // ȡ��ѡ����ļ�
			setTitle(file.toString()); // �ڱ��������趨�ļ�����
			try {
				file.createNewFile(); // �����ļ�
				saveFile();// �����ļ�����

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.toString(), "�޷��������ļ�",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void closeFile() {

		if (isCurrentFileSaved()) // �Ƿ��ѱ����ļ�
		{
			dispose();// �ͷŴ�����Դ������رճ���
		} else {
			int option = JOptionPane.showConfirmDialog(null, "�ļ����޸ģ��Ƿ񱣴棿",
					"�����ļ���", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null);

			switch (option) {
			case JOptionPane.YES_OPTION:// ȷ���ļ�����
				saveFile();// �����ļ�
				break;
			case JOptionPane.NO_OPTION:// �����ļ�����
				dispose();
			}
		}
	}

	private void cut() {
		textArea.cut();
		stateBar.setText("���޸�");
		popUpMenu.setVisible(false);
	}

	private void copy() {
		textArea.copy();
		popUpMenu.setVisible(false);
	}

	private void paste() {
		textArea.paste();
		stateBar.setText("���޸�");
		popUpMenu.setVisible(false);
	}

	private void processTextArea() {
		stateBar.setText("���޸�");
	}

	/*public static void main(String[] args) {
		new Notebook();
	}*/
}