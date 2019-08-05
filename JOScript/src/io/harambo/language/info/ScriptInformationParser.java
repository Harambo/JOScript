package io.harambo.language.info;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.Bukkit;

import io.harambo.JScriptCore;
import io.harambo.JScriptCore.ME;

public class ScriptInformationParser {
	
	private File file;
	private BufferedReader reader2;
	
	private String path;
	private boolean state;
	private ArrayList<String> result;
	
	public ScriptInformationParser(File file) {
		this.file = file;
		this.result = new ArrayList<>();
		state = false;
	}
	
	public ScriptInformationParser parse() throws IOException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		FileReader reader = new FileReader(file);
		reader2 = new BufferedReader(reader);
		
		String m;
		Integer x = 0;
		Integer line = 0;
		
		ArrayList<String> names = new ArrayList<>();
		Arrays.asList(ScriptInformation.DATA.values()).forEach(c -> names.add(c.toString()));
	
		while((m = reader2.readLine()) != null) {
			
			line = line + 1;
			
			if(m.isEmpty()) {
				continue;
			}
			
			m = m.trim();
			
			if(m.startsWith("*!INFORMATION") && m.endsWith("*")) {
				if(x == 0) {
					this.path = m.trim().replace("*!INFORMATION ", "").replace("*", "");
					
					if(this.path.trim().startsWith("path=")) {
						x = 1;
						this.path = this.path.replace("path=", "");
						continue;
					}
					
					JScriptCore.sendConsoleMessage(ME.ERROR, "(§a" + file.getName() + "§7) - Could not read path => LINE: §c" + line);
					return this;
				}
				
				JScriptCore.sendConsoleMessage(ME.ERROR, "(§a" + file.getName() + "§7) - Unnecessary code => LINE: §c" + line);
				return this;
			}
			
			if(x == 1) {
				if(m.contains(":")) {
					if(names.contains(m.split(":")[0].toUpperCase().trim())) {
						String attribute = m.split(":")[1].trim();
				
						if(attribute.length() >= 3) {
							result.add(m);
							names.remove(m.split(":")[0].trim().toUpperCase());
							if(names.isEmpty()) x = 2;
							continue;
						}
		
						JScriptCore.sendConsoleMessage(ME.ERROR, "(§a" + file.getName() + "§7) - The " +
						m.split(":")[0].toUpperCase() + " size must be over 3 => LINE: §c" + line);
						return this;
					}
					
					JScriptCore.sendConsoleMessage(ME.ERROR, "(§a" + file.getName() + "§7) - " +
							m.split(":")[0].toUpperCase() + " does not exist => LINE: §c" + line);
					return this;
				}
				
				JScriptCore.sendConsoleMessage(ME.ERROR, "(§a" + file.getName() + "§7) - Unnecessary code => LINE: §c" + line);
				return this;
			}
		}
		
		reader.close();
		reader2.close();
		
		if(names.isEmpty()) {
			result.forEach(ff -> {
				if(ff.startsWith("NAME")) {
					Bukkit.getConsoleSender().sendMessage("  ");
					JScriptCore.sendConsoleMessage(ME.INFO, "The information file of §b" + ff.split(":")[1].trim() + "§7 was successfully parsed!");
					state = true;
				}
			});
			return this;
		}
		
		AtomicReference<String> msString = new AtomicReference<>( "(§a" + file.getName() + "§7) - Datas not found  => ");
		names.forEach(c -> msString.set(msString + c + " "));
		JScriptCore.sendConsoleMessage(ME.ERROR, msString.get());
		return this;
	}

	
	
	public ScriptInformation getFileInfo() {
		return new ScriptInformation(this);
	}
	
	public File getFile() {
		return this.file;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public ArrayList<String> getResult() {
		return this.result;
	}
	
	public Boolean getState() {
		return this.state;
	}
}
