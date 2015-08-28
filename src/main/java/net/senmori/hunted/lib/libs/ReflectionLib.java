package net.senmori.hunted.lib.libs;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.senmori.hunted.util.ReflectionUtils;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ReflectionLib
{
	/** Get CraftPlayer class */
	public static Class<?> getCraftPlayerClass() {
		return ReflectionUtils.getRefClass("{cb}.entity.CraftPlayer").getRealClass();
	}
	
	/** Get NMS class by name */
	public static Class<?> getNMSClass(String name) {
		return ReflectionUtils.getRefClass("{nms}." + name).getRealClass();
	}
	
	/** Get CraftBukkit class by name */
	public static Class<?> getCraftClass(String name) {
		return ReflectionUtils.getRefClass("{cb}." + name).getRealClass();
	}
	
	/* #####################
	 *  Player methods
	 * #####################
	 */
	/** Cast {@link Player} to {@link CraftPlayer} */
	public static Object castToCraftPlayer(Player player) {
		return getCraftPlayerClass().cast(player);
	}
	
	/** Cast {@link Player} to {@link EntityPlayer} */
	public static Object castToNMSEntityPlayer(Player player)  {
		try {
	        return castToCraftPlayer(player).getClass().getMethod("getHandle").invoke(castToCraftPlayer(player));
        } 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
	        e.printStackTrace();
        }
		return null;
	}
	
	/* #################
	 *  Entity Methods
	 * #################
	 */
	/** Cast {@link Entity} to CB Entity */
	public static org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity getCraftEntity(Entity entity) {
		return (org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity)getCraftClass("CraftEntity").cast(entity);
	}
	
	/** Cast {@link Entity} to NMS Entity */
	public static net.minecraft.server.v1_8_R3.Entity getNMSEntity(Entity entity) {
		// try to get the handle instead of casting directly to nms Entity class
		try {
	        return (net.minecraft.server.v1_8_R3.Entity)getNMSClass("Entity").getDeclaredMethod("getHandle").invoke(getNMSClass("Entity").cast(getCraftEntity(entity)));
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
	        e.printStackTrace();
        }
		return (net.minecraft.server.v1_8_R3.Entity)getNMSClass("Entity").cast(getCraftEntity(entity));
	}
}
