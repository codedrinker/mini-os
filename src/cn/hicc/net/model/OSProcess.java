package cn.hicc.net.model;

public class OSProcess {

	// 这里是定义一个关于统一进程的模型
	// id定义每一个进程都有自己的序号
	// 在xml多种数据类型太麻烦，于是全部都是String类型

	private String id;

	// 定义JTree所对应节点的相对路径，结合具体实例，对操作节点的具体响应做出对应的判断
	private String noteId;

	// 定义了节点的名字，方便在就绪队列和阻塞队列中获得进程的名字

	private String noteName;

	// 定义进程所在的磁盘号，作为新建进程和删除进程的同时也改变所占用磁盘编号的颜色
	private String diskNum;

	// 定义进程所在的内存号，作为执行进程和进程执行结束改变所占用内存编号的颜色
	private String ramNum;

	// 文件对应的内容，如果是.app的就行命令文件，如果是.file的就是文本文件，但是都有内容的存在
	private String content;

	// 文件对应的扩专名，一般内容包括app或者是file
	private String ext;

	// 判断这个文件是否执行，如果实行就是true不执行就false
	private String execute;

	// 判断这个文件是否进入阻塞队列
	private String block;

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	public String getExecute() {
		return execute;
	}

	public void setExecute(String execute) {
		this.execute = execute;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getId() {
		return id;
	}

	public String getNoteId() {
		return noteId;
	}

	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDiskNum() {
		return diskNum;
	}

	public void setDiskNum(String diskNum) {
		this.diskNum = diskNum;
	}

	public String getRamNum() {
		return ramNum;
	}

	public void setRamNum(String ramNum) {
		this.ramNum = ramNum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
