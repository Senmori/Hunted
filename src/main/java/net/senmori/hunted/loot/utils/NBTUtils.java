package net.senmori.hunted.loot.utils;

import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import me.dpohvar.powernbt.nbt.NBTTagList;
import me.dpohvar.powernbt.nbt.NBTTagString;
import net.senmori.hunted.Hunted;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Created by Senmori on 4/14/2016.
 */
public class NBTUtils {

    private static NBTManager manager = Hunted.getInstance().nbtManager;

    public static ItemStack addAttribute(AttributeModifier modifier, ItemStack stack, Set<EquipmentSlot> validSlots) {

        NBTCompound root = manager.read(stack);
        NBTTagList modifiers = new NBTTagList();

        NBTCompound tag = new NBTCompound();
        tag.put("AttributeName", modifier.getName());
        tag.put("Name", modifier.getName());
        tag.put("Amount", modifier.getAmount());
        tag.put("Operation", modifier.getOperation().ordinal());
        tag.put("UUIDLeast", modifier.getUniqueId().getLeastSignificantBits());
        tag.put("UUIDMost", modifier.getUniqueId().getMostSignificantBits());

        NBTTagList slotList = new NBTTagList();
        for (EquipmentSlot s : validSlots) {
            slotList.add(new NBTTagString(s.name()));
        }
        tag.put("Slot", slotList);
        modifiers.addValue(tag);
        root.put("AttributeModifiers", modifiers);
        manager.write(stack, root);
        return stack;
    }


    /**
     * Set the NBT data of the given itemstack.<br>
     *
     * @param nbtString must be in JSON format.
     * @param stack
     * @param nbtString
     * @return
     */
    public static ItemStack setNBT(ItemStack stack, String nbtString) {
        return stack;
    }
}
