package io.harambo.language.script;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import io.harambo.JScriptCore;
import io.harambo.JScriptCore.ME;
import io.harambo.language.info.ScriptInformation;
import io.harambo.language.info.ScriptInformation.DATA;
import io.harambo.language.script.files.BaseFile;
import io.harambo.language.script.files.CommandFile;
import io.harambo.language.script.files.EventFile;
import io.harambo.language.script.files.VariableFile;

public class JScript {
	
	private HashMap<TYPE, ArrayList<BaseFile>> regis;
	private ScriptInformation information;
	
	private boolean state;
	
	public JScript(ScriptInformation information) {
		this.information = information;
		this.regis = new HashMap<>();
		this.state = false;
		
		if(!new File(information.getPath()).exists()) {
			JScriptCore.sendConsoleMessage(ME.ERROR, "(§a" + information.getResults().get(DATA.NAME) + "§7) - "
					+ "Script-Directory does not exist.");
			return;
		}
		
		Arrays.asList(TYPE.values()).forEach(cc -> {
			this.regis.put(cc, new ArrayList<>());
		});
		
		Arrays.asList(new File(information.getPath()).listFiles()).forEach(files -> {
			if(!files.isDirectory()) {
				if(files.getName().contains(".jsc")) {
					CommandFile cmd = new CommandFile(this, files);
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader(files));
						
						String m;
						Integer line = 0;
						
						while((m = reader.readLine()) != null) {
							line = line + 1;
							
							if(m.isEmpty()) {
								continue;
							}
							
							m = m.trim();
							cmd.line2line(m, line);
							
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if(cmd.getState()) {
						this.regis.get(TYPE.COMMAND).add(cmd);
						JScriptCore.sendConsoleMessage(ME.INFO, "The file §b" + files.getName() + "§7 was successfully parsed!");
					}
				}else if(files.getName().contains(".jse")) {
					EventFile cmd = new EventFile(this, files);
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader(files));
						
						String m;
						Integer line = 0;
						
						while((m = reader.readLine()) != null) {
							line = line + 1;
							
							if(m.isEmpty()) {
								continue;
							}
							
							m = m.trim();
							cmd.line2line(m, line);
							
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if(cmd.getState()) {
						this.regis.get(TYPE.EVENT).add(cmd);
						JScriptCore.sendConsoleMessage(ME.INFO, "The file §b" + files.getName() + "§7 was successfully parsed!");
					}
				}else if(files.getName().contains(".jsv")) {
					VariableFile cmd = new VariableFile(this, files);
					BufferedReader reader;
					try {
						reader = new BufferedReader(new FileReader(files));
						
						String m;
						Integer line = 0;
						
						while((m = reader.readLine()) != null) {
							line = line + 1;
							
							if(m.isEmpty()) {
								continue;
							}
							
							m = m.trim();
							cmd.line2line(m, line);
							
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if(cmd.getState()) {
						this.regis.get(TYPE.VAR).add(cmd);
						JScriptCore.sendConsoleMessage(ME.INFO, "The file §b" + files.getName() + "§7 was successfully parsed!");
					}
				}else {
					JScriptCore.sendConsoleMessage(ME.ERROR, "(§a" + files.getName() + "§7) - Datas could not be registered. Wrong prefix?");
				}
			}
		});
		
		this.state = true;
	}
	
	public HashMap<TYPE, ArrayList<BaseFile>> getFileRegistry() {
		return this.regis;
	}
	
	public ScriptInformation getInformation() {
		return this.information;
	}
	
	public boolean getState() {
		return this.state;
	}
	
	public enum TYPE {
		COMMAND,
		EVENT,
		VAR
	}

}
