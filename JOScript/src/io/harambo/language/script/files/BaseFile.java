package io.harambo.language.script.files;

import java.io.File;

import io.harambo.language.script.JScript;

public abstract class BaseFile {
	
	private JScript jScript;
	private File file;
	private boolean state;
	
	public BaseFile(JScript jScript, File f) {
		this.jScript = jScript;
		this.file = f;
		this.state = false;
	}
	
	public JScript getScript() {
		return this.jScript;
	}
	
	public File getFile() {
		return this.file;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public abstract void line2line(String line, Integer linenum);

}
