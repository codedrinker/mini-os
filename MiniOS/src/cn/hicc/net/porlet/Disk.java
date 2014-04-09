package cn.hicc.net.porlet;

import java.awt.Color;

import javax.swing.JButton;

public class Disk {

	private static JButton disks[] = new JButton[48];

	private static Disk instance = null;

	static {
		for (int i = 0; i < 48; i++) {
			disks[i] = new JButton();
			disks[i].setName(String.valueOf(i));
			if (i < 3)
				disks[i].setBackground(Color.DARK_GRAY);
			else
				disks[i].setBackground(Color.LIGHT_GRAY);
		}
	}

	public static JButton[] getDisks() {
		return disks;
	}

	// 使用单例模式实现对对象的创建
	public static synchronized Disk getInstance() {
		if (instance == null) {
			instance = new Disk();
		}
		return instance;
	}

	private Disk() {

	}
}
