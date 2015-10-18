package com.t2t.examples.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 文件集合
 * 
 * @author ypf
 */
public class FileList {

	private List<File> list = new ArrayList<File>();

	public int size() {
		return list.size();
	}

	public File get(int i) {
		return list.get(i);
	}

	public FileList add(File file) {
		list.add(file);
		return this;
	}
}
