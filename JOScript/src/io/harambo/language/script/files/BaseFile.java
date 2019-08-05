package io.harambo.language.script.files;

import java.io.File;

import io.harambo.language.script.JScript;

public class BaseFile {
	
	private JScript jScript;
	private File file;
	
	public BaseFile(JScript jScript, File f) {
		this.jScript = jScript;
		this.file = f;
	}
	
	public JScript getScript() {
		return this.jScript;
	}
	
	public File getFile() {
		return this.file;
	}

}
