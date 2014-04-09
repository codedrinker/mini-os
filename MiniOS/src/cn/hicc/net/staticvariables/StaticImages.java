package cn.hicc.net.staticvariables;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StaticImages {
	private static BufferedImage computerImage = null;
	private static String adapterImagePath = System.getProperty("user.dir")
			+ File.separator + "images" + File.separator + "adapter"
			+ File.separator;

	private static String osImagePath = System.getProperty("user.dir")
			+ File.separator + "images" + File.separator + "os"
			+ File.separator;

	private static BufferedImage keyboardImage = null;
	private static BufferedImage mouseImage = null;
	private static BufferedImage printerImage = null;
	private static BufferedImage winImage = null;

	private static BufferedImage videoImage = null;

	static {
		try {
			computerImage = ImageIO
					.read(new File(osImagePath + "computer.gif"));
			keyboardImage = ImageIO.read(new File(adapterImagePath
					+ "keyboard.gif"));
			mouseImage = ImageIO.read(new File(adapterImagePath + "mouse.gif"));
			printerImage = ImageIO.read(new File(adapterImagePath
					+ "printer.gif"));
			videoImage = ImageIO.read(new File(adapterImagePath + "video.gif"));
			winImage = ImageIO.read(new File(osImagePath + "win.gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static BufferedImage getWinImage() {
		return winImage;
	}

	public static BufferedImage getComputerImage() {
		return computerImage;
	}

	public static String getImagePath() {
		return adapterImagePath;
	}

	public static BufferedImage getKeyboardImage() {
		return keyboardImage;
	}

	public static BufferedImage getMouseImage() {
		return mouseImage;
	}

	public static BufferedImage getPrinterImage() {
		return printerImage;
	}

	public static BufferedImage getVideoImage() {
		return videoImage;
	}

}
