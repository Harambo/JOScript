package io.harambo.language.script;

import java.awt.Event;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;

import io.harambo.JScriptCore;
import io.harambo.JScriptCore.ME;
import io.harambo.language.info.ScriptInformation;
import io.harambo.language.info.ScriptInformation.DATA;
import io.harambo.language.script.files.BaseFile;
import io.harambo.language.script.files.CommandFile;
import io.harambo.language.script.files.EventFile;

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
					this.regis.get(TYPE.COMMAND).add(new CommandFile(this, files));
					JScriptCore.sendConsoleMessage(ME.INFO, "The file §b" + files.getName() + "§7 was successfully parsed!");
				}else if(files.getName().contains(".jse")) {
					this.regis.get(TYPE.EVENT).add(new EventFile(this, files));
					JScriptCore.sendConsoleMessage(ME.INFO, "The file §b" + files.getName() + "§7 was successfully parsed!");
				}else if(files.getName().contains(".jsv")) {
					this.regis.get(TYPE.VAR).add(new EventFile(this, files));
					JScriptCore.sendConsoleMessage(ME.INFO, "The file §b" + files.getName() + "§7 was successfully parsed!");
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
