package net.senmori.hunted.util;

import net.minecraft.server.v1_12_R1.ChatComponentText;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class HuntedUtil {

    public static void actionBar(Player player, String message) {
        IChatBaseComponent cbc = new ChatComponentText(message);
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
        ((CraftPlayer)player ).getHandle().playerConnection.sendPacket(ppoc);
    }

    public static int getInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Throwable var3) {
            return defaultValue;
        }
    }

    public static int floor(double value) {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }

    public static int clampAngle(int angle) {
        angle = angle % 360;
        if (angle >= 180) {
            angle -= 360;
        }
        if (angle < -180) {
            angle += 360;
        }
        return angle;
    }
}
