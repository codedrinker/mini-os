package cn.hicc.net.porlet;

import java.awt.Color;

import javax.swing.JButton;

public class Memory {
	private static JButton momeries[] = new JButton[56];
	static {
		for (int i = 0; i < 56; i++) {
			momeries[i] = new JButton();
			momeries[i].setName(String.valueOf(i));
			if (i < 3)
				momeries[i].setBackground(Color.DARK_GRAY);
			else
				momeries[i].setBackground(Color.LIGHT_GRAY);
		}
	}

	public static JButton[] getMomeries() {
		return momeries;
	}

	private Memory() {

	}

	private static Memory instance;

	public static synchronized Memory getInstance() {
		if (instance == null) {
			instance = new Memory();
		}
		return instance;
	}

}
