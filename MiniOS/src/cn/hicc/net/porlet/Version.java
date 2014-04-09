package cn.hicc.net.porlet;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.hicc.net.utils.PathUtils;

public class Version extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel MiniOS = null;
	private JLabel image = null;
	private JLabel copyright = null;
	public Version() {
		this.setName("version");
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("程序版本");
		this.setResizable(false);
		this.setVisible(true);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			copyright = new JLabel();
			copyright.setBounds(new Rectangle(50, 84, 208, 45));
			copyright.setText("版权所有：2010-2014 操作系统");
			image = new JLabel();
			image.setBounds(new Rectangle(2, 30, 121, 50));
			MiniOS = new JLabel();
			MiniOS.setText("MiniOS-2.0.6");
			MiniOS.setBounds(new Rectangle(60, 30, 217, 50));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(MiniOS, null);
			jContentPane.add(image, null);
			jContentPane.add(copyright, null);
		}
		return jContentPane;
	}

}
