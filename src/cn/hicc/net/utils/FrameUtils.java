package cn.hicc.net.utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtils {

	// 控制Frame让其居中显示
	public static void center(JFrame frame) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		int x = (int) screenSize.getWidth() / 2 - (int) frameSize.getWidth()
				/ 2;
		int y = (int) screenSize.getHeight() / 2 - (int) frameSize.getHeight()
				/ 2;
		frame.setLocation(x, y);

	}
}
