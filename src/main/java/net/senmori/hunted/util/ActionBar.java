package net.senmori.hunted.util;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar 
{
	  public static void sendUpdate(Player p, String msg)
	  {
	    String s = ChatColor.translateAlternateColorCodes('&', msg);

	    IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + s + "\"}");    
	    PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);	    
	    ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
	  }
	  
		// Bitshift function, for stuff
		// shift 'n', 'k' positions
	  public static boolean isBit(int n, int k)
	  {
		return ((n >> k) & 1) == 1;
	  }
}
