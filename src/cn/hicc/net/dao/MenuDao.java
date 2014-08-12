package cn.hicc.net.dao;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.hicc.net.model.OSMenu;
import cn.hicc.net.utils.MenuUtils;

public class MenuDao {

	// 得到配置文件里面的style的值
	public int getStyle() {
		try {
			Document document = MenuUtils.getDocument();
			NodeList list = document.getElementsByTagName("menu");
			Element menu_tag = (Element) list.item(0);
			OSMenu om = new OSMenu();
			om.setStyle(menu_tag.getElementsByTagName("style").item(0)
					.getTextContent());
			return Integer.parseInt(om.getStyle());
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	public void setStyle(String style) {
		try {
			Document document = MenuUtils.getDocument();
			NodeList list = document.getElementsByTagName("menu");
			Element student_tag = (Element) list.item(0);
			student_tag.getElementsByTagName("style").item(0).setTextContent(
					style);
			MenuUtils.write2Xml(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
