package cn.hicc.net.junit;

import java.util.List;

import org.junit.Test;

import cn.hicc.net.dao.ProcessDao;
import cn.hicc.net.model.OSProcess;

public class ProcessDaoTest {
	@Test
	public void add() {
		ProcessDao pd = new ProcessDao();
		OSProcess op = new OSProcess();
		op.setId("1");
		op.setNoteId("2");
		op.setDiskNum("3");
		op.setRamNum("4");
		op.setContent("j=90;j++;");
		op.setExt("file");
		pd.add(op);
	}

	@Test
	public void findexecute() {
		ProcessDao pd = new ProcessDao();
		List<OSProcess> list = pd.findexecute();
		for (OSProcess op : list) {
			System.out.println(op.getContent() + "+" + op.getDiskNum() + ":"
					+ op.getNoteId());
		}
	}

	@Test
	public void findblock() {
		ProcessDao pd = new ProcessDao();
		List<OSProcess> list = pd.findblock();
		for (OSProcess op : list) {
			System.out.println(op.getContent() + "+" + op.getDiskNum() + ":"
					+ op.getNoteId());
		}
	}

	@Test
	public void delete() {
		ProcessDao pd = new ProcessDao();
		pd.delete("34");
	}

	@Test
	public void update() {
		ProcessDao pd = new ProcessDao();
		OSProcess op = new OSProcess();
		op.setId("1");
		op.setNoteId("34");
		op.setDiskNum("3");
		op.setRamNum("4");
		op.setContent("update;");
		op.setExt("file");
		pd.update(op);
	}

	@Test
	public void execute() {
		ProcessDao pd = new ProcessDao();
		pd.execute("34");
	}

	@Test
	public void unexecute() {
		ProcessDao pd = new ProcessDao();
		pd.unexecute("34");
	}

	@Test
	public void block() {
		ProcessDao pd = new ProcessDao();
		pd.block("34");
	}

	@Test
	public void unblock() {
		ProcessDao pd = new ProcessDao();
		pd.unblock("34");
	}

	@Test
	public void enddisk() {
		ProcessDao pd = new ProcessDao();
		System.out.println(pd.enddisk());
	}

	@Test
	public void endram() {
		ProcessDao pd = new ProcessDao();
		System.out.println(pd.endram());
	}
}
