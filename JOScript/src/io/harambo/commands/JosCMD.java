package io.harambo.commands;

import java.util.concurrent.atomic.AtomicReference;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.harambo.JScriptCore;
import io.harambo.language.info.ScriptInformation.DATA;

public class JosCMD implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender cs, Command cc, String ll, String[] aa) {
		
		if(!(cs instanceof Player)) {
			AtomicReference<String> m = new AtomicReference<>("§fScripts (§a" + JScriptCore.getScripts().size() + "§f): ");
			
			JScriptCore.getScripts().forEach(script -> {
				m.set(m + "§a" + script.getInformation().getResults().get(DATA.NAME) + " ");
			});
			
			Bukkit.getConsoleSender().sendMessage(m.get());
			return false;
		}
		
		Player p = (Player) cs;
		Location loc = p.getLocation();
		
		if(aa.length == 0) {
			p.playSound(loc, Sound.NOTE_BASS, 1, 1);
			
            AtomicReference<String> m = new AtomicReference<>("§fScripts (§a" + JScriptCore.getScripts().size() + "§f): ");
			
			JScriptCore.getScripts().forEach(script -> {
				m.set(m + "§a" + script.getInformation().getResults().get(DATA.NAME) + " ");
			});
			
			p.sendMessage(m.get());
			return false;
		}
		return false;
	}

}
