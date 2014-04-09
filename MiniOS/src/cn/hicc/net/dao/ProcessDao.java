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
	// 这里应该这是多条件查询，在队列的时候，查询他是否执行，如果执行显示

	// 定义一个查询xml里面最后一个数据的id内容的方法
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

	// 定义一个查询xml里面最后一个数据的diskNum内容的方法
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

	// 定义一个查询xml里面最后一个数据的ramNum内容的方法
	public String endram() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			// 因为创建file文件的时候不写入内存，所以如下做法，从最后一个向前遍历,遇到为空的就跳过，不然就return得到最大值
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

	// 查询就绪队列的进程，这里是在JTree点击右键执行的文件
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

	// 查询阻塞队列的进程，这里是申请资源的时候没有，进入阻塞队列的进程
	// 通常是在运行中，如果发现申请的资源没有，那么就更新进程的block为true
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

	// 添加进程，通常是在JTree地方得到数据，添加新的节点，写入xml文件
	public void add(OSProcess op) {
		try {
			// 得到操作对象
			Document document = XmlUtils.getDocument();
			// 分别创建节点
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

			// element添加子节点

			element.appendChild(id);
			element.appendChild(noteId);
			element.appendChild(noteName);
			element.appendChild(diskNum);
			element.appendChild(ramNum);
			element.appendChild(content);
			element.appendChild(ext);
			element.appendChild(execute);
			element.appendChild(block);

			// 等到model对象传递过来的值
			// 添加id
			if (endid() != null) {
				id
						.setTextContent(String.valueOf(Integer
								.parseInt(endid()) + 1));
			} else {
				id.setTextContent("1");
			}
			noteId.setTextContent(op.getNoteId());
			noteName.setTextContent(op.getNoteName());

			// 自动判断磁盘最后的位置，然后取出来磁盘的最后位置的序号然后添加进去
			if (enddisk() != null) {
				diskNum.setTextContent(String.valueOf(Integer
						.parseInt(enddisk()) + 1));
			} else {
				diskNum.setTextContent("3");
			}

			// 判断创建文件是否为app文件，如果是app文件才写入内存变化 ，不是app文件不写入
			if (op.getExt().equals(".app")) {

				// 判断xml里面最后一个数据的内存块是多少，然后得到，加1放入xml中
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

			// 根节点
			document.getElementsByTagName("os").item(0).appendChild(element);

			// 写入xml
			XmlUtils.write2Xml(document);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除进程，通常是在JTree地方做的删除操作,这里是做的通过noteI的进行删除
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

	// 修给进程，这里是在JTree地方获得的修改对象，对整体进行修改
	public void update(OSProcess op) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(op.getNoteId())) {

					// 这里就添加了修改文件内容的方法，如果后期有需要再添加，目前也就修改内容是值得已写的
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

	// 修改最后一个数据的content
	public void updateEnd(String content) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			Element student_tag = (Element) list.item(list.getLength() - 1);

			// 这里就添加了修改文件内容的方法，如果后期有需要再添加，目前也就修改内容是值得已写的
			student_tag.getElementsByTagName("content").item(0).setTextContent(
					content);
			XmlUtils.write2Xml(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 执行进程时候的更新进程
	public void execute(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// 直接修改execute变为true
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

	// 进入就绪队列以后执行完了，把执行变为false
	public void unexecute(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// 直接修改execute变为false
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

	// 由就绪队列进入阻塞队列的时候变更的
	public void block(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// 直接修改execute变为false的同时修改block，因为进入阻塞队列了就不行在在就绪队列了

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

	// 由阻塞队列返回就绪队列的操作
	public void unblock(String noteId) {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("noteId").item(0)
						.getTextContent().equals(noteId)) {

					// 吧block设置false把execute设置成true还原就绪队列
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

	// 通过noteId查询OSProcess对象，以便更新和其他使用
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

	// 获得最后添加的一个数据
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

	// 添加一个获得数据中所有的diskNum的值

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

	// 添加一个获得数据中所有的ramNum的值

	public List getRamNum() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			List rams = new ArrayList();
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				// 在xml中查出内存编号不等于0的并且扩展名为app的进程
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

	// 添加一个获得数据中所有的正在运行的程序的ramNum的值

	public List getRunningRamNum() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			List rams = new ArrayList();
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);

				// 在xml中查出内存编号不等于0的并且扩展名为app的并且正在执行的进程
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

	// 做磁盘整理的方法，原理是通过从首到尾遍历数据，然后从3到最大更新diskNum的值
	public void diskTrim() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				// 逐条的改变diskNum的值为i的值
				student_tag.getElementsByTagName("diskNum").item(0)
						.setTextContent(String.valueOf(i + 3));
				XmlUtils.write2Xml(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 做内存整理的方法，原理是通过从首到尾遍历数据，然后从3到最大更新ramNum的值

	public void ramTrim() {
		try {
			Document document = XmlUtils.getDocument();
			NodeList list = document.getElementsByTagName("process");
			int count = 3;
			for (int i = 0; i < list.getLength(); i++) {
				Element student_tag = (Element) list.item(i);
				if (student_tag.getElementsByTagName("ext").item(0)
						.getTextContent().equals(".app")) {
					// 逐条的改变diskNum的值为i的值
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
