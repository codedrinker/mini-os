package cn.hicc.net.model;

public class OSProcess {

	// �����Ƕ���һ������ͳһ���̵�ģ��
	// id����ÿһ�����̶����Լ������
	// ��xml������������̫�鷳������ȫ������String����

	private String id;

	// ����JTree����Ӧ�ڵ�����·������Ͼ���ʵ�����Բ����ڵ�ľ�����Ӧ������Ӧ���ж�
	private String noteId;

	// �����˽ڵ�����֣������ھ������к����������л�ý��̵�����

	private String noteName;

	// ����������ڵĴ��̺ţ���Ϊ�½����̺�ɾ�����̵�ͬʱҲ�ı���ռ�ô��̱�ŵ���ɫ
	private String diskNum;

	// ����������ڵ��ڴ�ţ���Ϊִ�н��̺ͽ���ִ�н����ı���ռ���ڴ��ŵ���ɫ
	private String ramNum;

	// �ļ���Ӧ�����ݣ������.app�ľ��������ļ��������.file�ľ����ı��ļ������Ƕ������ݵĴ���
	private String content;

	// �ļ���Ӧ����ר����һ�����ݰ���app������file
	private String ext;

	// �ж�����ļ��Ƿ�ִ�У����ʵ�о���true��ִ�о�false
	private String execute;

	// �ж�����ļ��Ƿ������������
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
