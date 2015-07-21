package net.senmori.hunted.lib;

import org.bukkit.Bukkit;

public class AttributeModifier
{
	public enum AttributeType {
		ATTACK_DAMAGE("generic.attackDamage"),
		FOLLOW_RANGE("generic.followRange"),
		KNOCKBACK_RESISTANCE("generic.knockbackResistance"),
		MOVEMENT_SPEED("generic.movementSpeed"),
		MAX_HEALTH("generic.maxHealth");
		
		public String name;
		AttributeType(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
	}
	
	
	
	private static String version = "";
	private static Class<?> nmsItemStack;
	
	private static Class<?> craftItemStack;
	
	private static Class<?> nbtTagCompound;
	private static Class<?> nbtTagList;
	private static Class<?> nbtTagEnd;
	private static Class<?> nbtTagString;
	private static Class<?> nbtTagLong;
	private static Class<?> nbtTagInt;
	private static Class<?> nbtTagDouble;
	private static Class<?> nbtTagFloat;
	private static Class<?> nbtTagByte;
	private static Class<?> nbtTagShort;
	private static Class<?> nbtTagByteArray;
	private static Class<?> nbtTagIntArray;
	
	static {
			try {
				String packageName = Bukkit.getServer().getClass().getPackage().getName();
				version = packageName.substring(packageName.lastIndexOf('.')+1);
				

	            nmsItemStack = Class.forName(getNMSItemStackClass());

	            craftItemStack = Class.forName(getCraftItemStackClass());
	            
	           nbtTagCompound = Class.forName(getNBTCompoundClass());
	           nbtTagList = Class.forName(getNBTListClass());
	           nbtTagEnd = Class.forName(getNBTEndClass());
	           nbtTagString = Class.forName(getNBTStringClass());
	           nbtTagLong = Class.forName(getNBTLongClass());
	           nbtTagInt = Class.forName(getNBTIntClass());
	           nbtTagDouble = Class.forName(getNBTDoubleClass());
	           nbtTagFloat = Class.forName(getNBTFloatClass());
	           nbtTagByte = Class.forName(getNBTByteClass());
	           nbtTagShort = Class.forName(getNBTShortClass());
	           nbtTagByteArray = Class.forName(getNBTByteArrayClass());
	           nbtTagIntArray = Class.forName(getNBTIntArrayClass());
	            

            } 
			catch (ClassNotFoundException e) {
	            e.printStackTrace();
            }
	}
	
	/* ####################
	 *  Set variable classes
	 * ####################
	 */
	public static String getNMSItemStackClass() {
		return "net.minecraft.server." + version + ".ItemStack";
	}
	
	private static String getCraftItemStackClass() {
		return "org.bukkit.craftbukkit." + version + ".inventory.CraftItemStack";
	}
	
	private static String getNBTByteClass() {
		return "net.minecraft.server." + version + ".NBTTagByte";
	}
	
	private static String getNBTByteArrayClass() {
		return "net.minecraft.server." + version + ".NBTTagByteArray";
	}
	
	private static String getNBTCompoundClass() {
		return "net.minecraft.server." + version + ".NBTTagCompound";
	}
	
	private static String getNBTDoubleClass() {
		return "net.minecraft.server." + version + ".NBTTagDouble";
	}
	
	private static String getNBTEndClass() {
		return "net.minecraft.server." + version + ".NBTTagEnd";
	}
	
	private static String getNBTFloatClass() {
		return "net.minecraft.server." + version + ".NBTTagFloat";
	}
	
	private static String getNBTIntClass() {
		return "net.minecraft.server." + version + ".NBTTagInt";
	}
	
	private static String getNBTIntArrayClass() {
		return "net.minecraft.server." + version + ".NBTTagIntArray";
	}
	
	private static String getNBTListClass() {
		return "net.minecraft.server." + version + ".NBTTagList";
	}
	
	private static String getNBTLongClass() {
		return "net.minecraft.server." + version + ".NBTTagLong";
	}
	
	private static String getNBTShortClass() {
		return "net.minecraft.server." + version + ".NBTTagShort";
	}
	
	private static String getNBTStringClass() {
		return "net.minecraft.server." + version + ".NBTTagString";
	}
	
}
