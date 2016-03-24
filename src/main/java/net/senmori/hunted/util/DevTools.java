package net.senmori.hunted.util;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_9_R1.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class DevTools {
	
	public static ItemStack getDevBook() {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		List<IChatBaseComponent> pages;
		BookMeta bm = (BookMeta)book.getItemMeta();
		try {
			pages = (List<IChatBaseComponent>) CraftMetaBook.class.getDeclaredField("pages").get(bm);
		} catch(ReflectiveOperationException e) {
			e.printStackTrace();
			return book;
		}
		bm.setAuthor("Supreme Overlord Senmori");
		bm.setTitle("Dev Book");
		
		// give armor 
		String armorCommand = "/ht give armor";
		TextComponent armorMessage = new TextComponent(" Armor-");
		armorMessage.setColor(ChatColor.BLUE);
		armorMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, armorCommand));
		armorMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to give random armor").create()));
		
		// give weapon
		String weaponCommand = "/ht give weapon";
		TextComponent weaponMessage = new TextComponent(" Weapon-");
		weaponMessage.setColor(ChatColor.RED);
		weaponMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, weaponCommand));
		weaponMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to give a random weapon").create()));
		
		//give potion
		String potionCommand = "/ht give potion";
		TextComponent potionMessage = new TextComponent(" Potion");
		potionMessage.setColor(ChatColor.DARK_PURPLE);
		potionMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, potionCommand));
		potionMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to give a random potion").create()));
		
		armorMessage.addExtra(weaponMessage);
		weaponMessage.addExtra(potionMessage);
		
		// add page
		String pageText = ComponentSerializer.toString(armorMessage);
		IChatBaseComponent page = ChatSerializer.a(pageText);
		pages.add(page);
		
		book.setItemMeta(bm);
		return book;
	}
}
