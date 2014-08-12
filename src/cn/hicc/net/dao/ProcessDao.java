package cn.hicc.net.dao;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cn.hicc.net.model.OSProcess;
import cn.hicc.net.utils.XmlUtils;

/*
 <os>
 <process>
 <id>2</id>
 <noteId>34</noteId>
 <diskNum>12</diskNum>
 <ramNum>6</ramNum>
 <content>i=9;i++;!A;</content>
 <ext>file</ext>
 <execute>false</execute>
 <block>false</block>
 </process>
 </os>
 */
public class ProcessDao {
	// ����Ӧ�����Ƕ�������ѯ���ڶ��е�ʱ�򣬲�ѯ���Ƿ�ִ�У����ִ����ʾ

	// ����һ����ѯxml�������һ�����ݵ�id���ݵķ���
	public String endid() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			Element student_tag = (Element) list.item(list.getLength() - 1);
			String id = student_tag.getElementsByTagName("id").item(0)
					.getTextContent();
			return id;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ����һ����ѯxml�������һ�����ݵ�diskNum���ݵķ���
	public String enddisk() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			Element student_tag = (Element) list.item(list.getLength() - 1);
			String diskNum = student_tag.getElementsByTagName("diskNum")
					.item(0).getTextContent();
			return diskNum;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ����һ����ѯxml�������һ�����ݵ�ramNum���ݵķ���
	public String endram() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			// ��Ϊ����file�ļ���ʱ��д���ڴ棬�������������������һ����ǰ����,����Ϊ�յľ���������Ȼ��return�õ����ֵ
			for (int i = list.getLength() - 1; i > 0; i--) {
				Element student_tag = (Element) list.item(i);
				String ramNum = student_tag.getElementsByTagName("ramNum")
						.item(0).getTextContent();
				if ("".equals(ramNum) || ramNum == null) {
					continue;
				}
				return ramNum;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��ѯ�������еĽ��̣���������JTree����Ҽ�ִ�е��ļ�
	public List<OSProcess> findexecute() {

		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			List<OSProcess> ops = new ArrayList<OSProcess>();
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("execute").item(0)
						.getTextContent().equals("true")) {
					OSProcess op = new OSProcess();

					op.setId(student_tag.getElementsByTagName("id").item(0)
							.getTextContent());
					op.setNoteId(student_tag.getElementsByTagName("noteId")
							.item(0).getTextContent());
					op.setNoteName(student_tag.getElementsByTagName("noteName")
							.item(0).getTextContent());
					op.setDiskNum(student_tag.getElementsByTagName("diskNum")
							.item(0).getTextContent());
					op.setRamNum(student_tag.getElementsByTagName("ramNum")
							.item(0).getTextContent());
					op.setContent(student_tag.getElementsByTagName("content")
							.item(0).getTextContent());
					ops.add(op);
				}
			}
			return ops;
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��ѯ�������еĽ��̣�������������Դ��ʱ��û�У������������еĽ���
	// ͨ�����������У���������������Դû�У���ô�͸��½��̵�blockΪtrue
	public List<OSProcess> findblock() {

		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			List<OSProcess> ops = new ArrayList<OSProcess>();
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("block").item(0)
						.getTextContent().equals("true")) {
					OSProcess op = new OSProcess();

					op.setId(student_tag.getElementsByTagName("id").item(0)
							.getTextContent());
					op.setNoteId(student_tag.getElementsByTagName("noteId")
							.item(0).getTextContent());
					op.setNoteName(student_tag.getElementsByTagName("noteName")
							.item(0).getTextContent());
					op.setDiskNum(student_tag.getElementsByTagName("diskNum")
							.item(0).getTextContent());
					op.setRamNum(student_tag.getElementsByTagName("ramNum")
							.item(0).getTextContent());
					op.setContent(student_tag.getElementsByTagName("content")
							.item(0).getTextContent());
					ops.add(op);
				}
			}
			return ops;
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��ӽ��̣�ͨ������JTree�ط��õ����ݣ�����µĽڵ㣬д��xml�ļ�
	public void add(OSProcess op) {
		try {
			// �õ���������
			Document document = XmlUtils.getDocument();
			// �ֱ𴴽��ڵ�
			Element element = document.createElement("process");
			Element id = document.createElement("id");
			Element noteId = document.createElement("noteId");
			Element noteName = document.createElement("noteName");
			Element diskNum = document.createElement("diskNum");
			Element ramNum = document.createElement("ramNum");
			Element content = document.createElement("content");
			Element ext = document.createElement("ext");
			Element execute = document.createElement("execute");
			Element block = document.createElement("block");

			// element����ӽڵ�

			element.appendChild(id);
			element.appendChild(noteId);
			element.appendChild(noteName);
			element.appendChild(diskNum);
			element.appendChild(ramNum);
			element.appendChild(content);
			element.appendChild(ext);
			element.appendChild(execute);
			element.appendChild(block);

			// �ȵ�model���󴫵ݹ�����ֵ
			// ���id
			if (endid() != null) {
				id
						.setTextContent(String.valueOf(Integer
								.parseInt(endid()) + 1));
			} else {
				id.setTextContent("1");
			}
			noteId.setTextContent(op.getNoteId());
			noteName.setTextContent(op.getNoteName());

			// �Զ��жϴ�������λ�ã�Ȼ��ȡ�������̵����λ�õ����Ȼ����ӽ�ȥ
			if (enddisk() != null) {
				diskNum.setTextContent(String.valueOf(Integer
						.parseInt(enddisk()) + 1));
			} else {
				diskNum.setTextContent("3");
			}

			// �жϴ����ļ��Ƿ�Ϊapp�ļ��������app�ļ���д���ڴ�仯 ������app�ļ���д��
			if (op.getExt().equals(".app")) {

				// �ж�xml�������һ�����ݵ��ڴ���Ƕ��٣�Ȼ��õ�����1����xml��
				if (endram() != null && !"".equals(endram())) {
					ramNum.setTextContent(String.valueOf(Integer
							.parseInt(endram()) + 1));
				} else {
					ramNum.setTextContent(String.valueOf("3"));
				}
			}

			content.setTextContent(op.getContent());
			ext.setTextContent(op.getExt());
			execute.setTextContent("false");
			block.setTextContent("false");

			// ���ڵ�
			document.getElementsByTagName("os").item(0).appendChild(element);

			// д��xml
			XmlUtils.write2Xml(document);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ�����̣�ͨ������JTree�ط�����ɾ������,����������ͨ��noteI�Ľ���ɾ��
	public void delete(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {
					student_tag.getParentNode().removeChild(student_tag);
					XmlUtils.write2Xml(document);
					return;
				}
			}
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �޸����̣���������JTree�ط���õ��޸Ķ��󣬶���������޸�
	public void update(OSProcess op) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(op.getNoteId())) {

					// �����������޸��ļ����ݵķ����������������Ҫ����ӣ�ĿǰҲ���޸�������ֵ����д��
					student_tag.getElementsByTagName("content").item(0)
							.setTextContent(op.getContent());
					XmlUtils.write2Xml(document);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �޸����һ�����ݵ�content
	public void updateEnd(String content) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			Element student_tag = (Element) list.item(list.getLength() - 1);

			// �����������޸��ļ����ݵķ����������������Ҫ����ӣ�ĿǰҲ���޸�������ֵ����д��
			student_tag.getElementsByTagName("content").item(0).setTextContent(
					content);
			XmlUtils.write2Xml(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ִ�н���ʱ��ĸ��½���
	public void execute(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// ֱ���޸�execute��Ϊtrue
					student_tag.getElementsByTagName("execute").item(0)
							.setTextContent("true");

					XmlUtils.write2Xml(document);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ������������Ժ�ִ�����ˣ���ִ�б�Ϊfalse
	public void unexecute(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// ֱ���޸�execute��Ϊfalse
					student_tag.getElementsByTagName("execute").item(0)
							.setTextContent("false");
					XmlUtils.write2Xml(document);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �ɾ������н����������е�ʱ������
	public void block(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// ֱ���޸�execute��Ϊfalse��ͬʱ�޸�block����Ϊ�������������˾Ͳ������ھ���������

					student_tag.getElementsByTagName("execute").item(0)
							.setTextContent("false");
					student_tag.getElementsByTagName("block").item(0)
							.setTextContent("true");
					XmlUtils.write2Xml(document);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���������з��ؾ������еĲ���
	public void unblock(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// ��block����false��execute���ó�true��ԭ��������
					student_tag.getElementsByTagName("execute").item(0)
							.setTextContent("true");
					student_tag.getElementsByTagName("block").item(0)
							.setTextContent("false");
					XmlUtils.write2Xml(document);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ͨ��noteId��ѯOSProcess�����Ա���º�����ʹ��
	public OSProcess find(String noteId) {

		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {
					OSProcess op = new OSProcess();

					op.setId(student_tag.getElementsByTagName("id").item(0)
							.getTextContent());
					op.setNoteId(student_tag.getElementsByTagName("noteId")
							.item(0).getTextContent());
					op.setNoteName(student_tag.getElementsByTagName("noteName")
							.item(0).getTextContent());
					op.setDiskNum(student_tag.getElementsByTagName("diskNum")
							.item(0).getTextContent());
					op.setRamNum(student_tag.getElementsByTagName("ramNum")
							.item(0).getTextContent());
					op.setContent(student_tag.getElementsByTagName("content")
							.item(0).getTextContent());
					return op;
				}
			}
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ��������ӵ�һ������
	public OSProcess endProcess() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			Element student_tag = (Element) list.item(list.getLength() - 1);
			OSProcess op = new OSProcess();
			op.setId(student_tag.getElementsByTagName("id").item(0)
					.getTextContent());
			op.setNoteId(student_tag.getElementsByTagName("noteId").item(0)
					.getTextContent());
			op.setNoteName(student_tag.getElementsByTagName("noteName").item(0)
					.getTextContent());
			op.setDiskNum(student_tag.getElementsByTagName("diskNum").item(0)
					.getTextContent());
			op.setRamNum(student_tag.getElementsByTagName("ramNum").item(0)
					.getTextContent());
			op.setContent(student_tag.getElementsByTagName("content").item(0)
					.getTextContent());
			return op;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ���һ��������������е�diskNum��ֵ

	public List getDiskNum() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			List disks = new ArrayList();
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (!"".equals(student_tag.getElementsByTagName("diskNum")
						.item(0).getTextContent())) {
					String disk = student_tag.getElementsByTagName("diskNum")
							.item(0).getTextContent();
					disks.add(disk);
				}
			}
			return disks;
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ���һ��������������е�ramNum��ֵ

	public List getRamNum() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			List rams = new ArrayList();
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				// ��xml�в���ڴ��Ų�����0�Ĳ�����չ��Ϊapp�Ľ���
				if (!"".equals(student_tag.getElementsByTagName("ramNum").item(
						0).getTextContent())
						&& student_tag.getElementsByTagName("ext").item(0)
								.getTextContent().equals(".app")) {
					String ram = student_tag.getElementsByTagName("ramNum")
							.item(0).getTextContent();
					rams.add(ram);
				}
			}
			return rams;
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ���һ��������������е��������еĳ����ramNum��ֵ

	public List getRunningRamNum() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			List rams = new ArrayList();
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);

				// ��xml�в���ڴ��Ų�����0�Ĳ�����չ��Ϊapp�Ĳ�������ִ�еĽ���
				if (!"".equals(student_tag.getElementsByTagName("ramNum").item(
						0).getTextContent())
						&& student_tag.getElementsByTagName("execute").item(0)
								.getTextContent().equals("true")
						&& student_tag.getElementsByTagName("ext").item(0)
								.getTextContent().equals(".app")) {
					String ram = student_tag.getElementsByTagName("ramNum")
							.item(0).getTextContent();
					rams.add(ram);
				}
			}
			return rams;
		} catch (DOMException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// ����������ķ�����ԭ����ͨ�����׵�β�������ݣ�Ȼ���3��������diskNum��ֵ
	public void diskTrim() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				// �����ĸı�diskNum��ֵΪi��ֵ
				student_tag.getElementsByTagName("diskNum").item(0)
						.setTextContent(String.valueOf(i + 3));
				XmlUtils.write2Xml(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���ڴ�����ķ�����ԭ����ͨ�����׵�β�������ݣ�Ȼ���3��������ramNum��ֵ

	public void ramTrim() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			int count = 3;
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("ext").item(0)
						.getTextContent().equals(".app")) {
					// �����ĸı�diskNum��ֵΪi��ֵ
					student_tag.getElementsByTagName("ramNum").item(0)
							.setTextContent(String.valueOf(count));
					count++;
				}
				XmlUtils.write2Xml(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
