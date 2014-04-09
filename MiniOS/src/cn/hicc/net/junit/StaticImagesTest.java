package cn.hicc.net.junit;

import javax.swing.JFrame;

import cn.hicc.net.staticvariables.StaticImages;


public class StaticImagesTest {
	public static void main(String[] args) {

		JFrame f = new JFrame();
		f.setBounds(200, 300,800,600);
		f.setIconImage(StaticImages.getComputerImage());
		f.setVisible(true);
	}
}
