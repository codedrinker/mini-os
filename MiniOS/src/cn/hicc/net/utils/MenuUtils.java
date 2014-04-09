package cn.hicc.net.utils;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class MenuUtils {

	// ԭ���������·�������������ʵ�֣�ԭ��������һ��ɰ�֮��Ҳ����ͨ�����ļ������ص���ִ�е�xml�ļ�
	private static String filepath = Thread.currentThread()
			.getContextClassLoader().getResource("menu.xml").getPath();

	// private static String filename = "src/exam.xml";
	// private InputStream is =
	// this.getClass().getClassLoader().getResourceAsStream("exam.xml");

	public static synchronized Document getDocument() throws Exception {
		// System.out.println(filepath);
		return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
				filepath);

	}

	public static synchronized void write2Xml(Document document)
			throws Exception {
		TransformerFactory.newInstance().newTransformer().transform(
				new DOMSource(document),
				new StreamResult(new FileOutputStream(filepath)));
	}
}