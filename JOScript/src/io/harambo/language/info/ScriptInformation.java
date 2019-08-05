package io.harambo.language.info;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;

public class ScriptInformation {
	
	private ScriptInformationParser fileInfoParser;
	private HashMap<DATA, String> regis;
	private String path;
	
	
	public ScriptInformation(ScriptInformationParser parser) {
		this.fileInfoParser = parser;
		this.regis = new HashMap<>();
		
		try {
			parser.parse();
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException
				| IOException e1) {
			e1.printStackTrace();
		}
		
		Arrays.asList(DATA.values()).forEach(dataa -> {
			parser.getResult().forEach(attr -> {
				if(attr.startsWith(dataa.toString())) {
					regis.put(dataa, attr.split(":")[1]);
				}
			});
		});
		
		this.path = parser.getPath();
		
		if(fileInfoParser.getState()) {
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage("§8§m=============================");
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage("§7NAME §8=> §a" +regis.get(DATA.NAME));
			Bukkit.getConsoleSender().sendMessage("§7AUTHOR §8=> §a" +regis.get(DATA.AUTHOR));
			Bukkit.getConsoleSender().sendMessage("§7VERSION §8=> §a" +regis.get(DATA.VERSION));
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage("§7PATH: §8=> §b" + path);
			Bukkit.getConsoleSender().sendMessage(" ");
			Bukkit.getConsoleSender().sendMessage("§8§m=============================");
		}
	}
	
	public ScriptInformationParser getFileInfoParser() {
		return this.fileInfoParser;
	}
	
	public HashMap<DATA, String> getResults() {
		return this.regis;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public static enum DATA {
		NAME,
		WEBSITE,
		VERSION,
		AUTHOR
	}

}
