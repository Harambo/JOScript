package io.harambo;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import io.harambo.language.info.ScriptInformation;
import io.harambo.language.info.ScriptInformationParser;

public class JScript extends JavaPlugin {
	
	private static JScript jScript;
	private static ArrayList<ScriptInformation> info;
	
	@Override public void onEnable() {
	
		File f= new File("plugins/JOScript/scripts");
		File[] files = null;
		
		if(!f.exists()) {
			sendConsoleMessage(ME.INFO, "The directory §ascript §7does not exist. Creating...");
			f.mkdirs();
		} else {
			f= new File("plugins/JOScript");
			files = f.listFiles();
		
		}
		
		jScript = this;
		info = new ArrayList<>();
	
		
		if(files.length > 0) {
			List<File> filesa = Arrays.asList(files);
			
			filesa.forEach(file -> {
				if(!file.isDirectory()) {
					if(file.getName().split(Pattern.quote("."))[1].equalsIgnoreCase("info")) {
						ScriptInformation infdo = new ScriptInformation(new ScriptInformationParser(file));
						if(infdo.getFileInfoParser().getState()) info.add(infdo);
					}
				}
			});
		} else {
			sendConsoleMessage(ME.INFO, "No scripts were found!");
		}
		
		
		registerCommands();
		registerEvents(this.getServer().getPluginManager());
	}
	
	@Override public void onDisable() {
		
	}
	
	private void registerCommands() {
		
	}
	
	private void registerEvents(PluginManager pm) {
		
	}
	
	public static void sendConsoleMessage(ME type, String msg) {
		Bukkit.getConsoleSender().sendMessage("§8| " + type.getColor() + "§l" + type.toString() + " §8=> §7" + msg);
	};
	
	public static JScript getInstance() {
		return jScript;
	}
	
	public static enum ME {
		
		DEBUG("§a"),
		INFO("§e"),
		ERROR("§c");
		
		private String color;
		
		ME(String co) {
			this.color = co;
		}
		
		public String getColor() {
			return color;
		}
	}
	
	public ArrayList<ScriptInformation> getInfoList() {
		return info;
	}

}
