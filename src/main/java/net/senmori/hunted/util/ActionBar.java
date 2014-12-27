package net.senmori.hunted.util;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar 
{
	  public static void sendAnnouncement(Player p, String msg)
	  {
	    String s = ChatColor.translateAlternateColorCodes('&', msg);

	    @SuppressWarnings("deprecation")
		int online = Bukkit.getServer().getOnlinePlayers().length;
	    s = s.replace("%player%", p.getName()).replace("%online%", Integer.toString(online));    
	    IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s + "\"}");    
	    PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);	    
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
	  }
}
