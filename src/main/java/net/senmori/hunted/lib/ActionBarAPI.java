package net.senmori.hunted.lib;

/*
* Copyright (c) 2014 hamzaxx
*
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
* documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR
* OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
*/

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
* @author hamzaxx
*/
public class ActionBarAPI
{

    private static String version = "";

    private static Method getHandle;
    private static Method sendPacket;
    private static Field playerConnection;
    private static Class<?> nmsChatSerializer;
    private static Constructor chatConstructor;

    static
    {
        try
        {
            version = Bukkit.getServer().getClass().getPackage().getName().split( "\\." )[ 3 ];

            Class<?> packetType = Class.forName( getPacketPlayOutChat() );

            Class<?> typeCraftPlayer = Class.forName( getCraftPlayerClasspath() );

            Class<?> typeNMSPlayer = Class.forName( getNMSPlayerClasspath() );

            Class<?> typePlayerConnection = Class.forName( getPlayerConnectionClasspath() );

            nmsChatSerializer = Class.forName( getChatSerializerClasspath() );

            Class<?> nmsIChatBaseComponent = Class.forName( getIChatBaseComponentClasspath() );

            getHandle = typeCraftPlayer.getMethod( "getHandle" );

            playerConnection = typeNMSPlayer.getField( "playerConnection" );

            sendPacket = typePlayerConnection.getMethod( "sendPacket", Class.forName( getPacketClasspath() ) );

            if ( version.startsWith( "v1_8" ) )
            {
                chatConstructor = packetType.getConstructor( nmsIChatBaseComponent, byte.class );
            } else
            {
                chatConstructor = packetType.getConstructor( nmsIChatBaseComponent, int.class );
            }

        } catch ( ClassNotFoundException | NoSuchMethodException |
                SecurityException | NoSuchFieldException ex )
        {
            Bukkit.getLogger().severe( ex.getMessage() );
        }
    }


    public static void send(Player receivingPacket, String msg)
    {
        try
        {

            Object serialized = nmsChatSerializer.getMethod( "a", String.class ).invoke( null, "{\"text\": \"" + msg + "\"}" );

            Object packet;
            if ( version.startsWith( "v1_8" ) )
            {
                packet = chatConstructor.newInstance( serialized, ( byte ) 2 );
            } else
            {
                packet = chatConstructor.newInstance( serialized, 2 );
            }

            Object player = getHandle.invoke( receivingPacket );

            Object connection = playerConnection.get( player );

            sendPacket.invoke( connection, packet );
        } catch ( NoSuchMethodException | SecurityException | IllegalAccessException |
                IllegalArgumentException | InvocationTargetException | InstantiationException ex )
        {
            Bukkit.getLogger().severe( ex.getMessage() );
        }
    }

    private static String getCraftPlayerClasspath()
    {
        return "org.bukkit.craftbukkit." + version + ".entity.CraftPlayer";
    }

    private static String getPlayerConnectionClasspath()
    {
        return "net.minecraft.server." + version + ".PlayerConnection";
    }

    private static String getNMSPlayerClasspath()
    {
        return "net.minecraft.server." + version + ".EntityPlayer";
    }

    private static String getPacketClasspath()
    {
        return "net.minecraft.server." + version + ".Packet";
    }

    private static String getIChatBaseComponentClasspath()
    {
        return "net.minecraft.server." + version + ".IChatBaseComponent";
    }

    private static String getChatSerializerClasspath()
    {
        if ( version.equals( "v1_8_R3" ) || version.equals( "v1_8_R2" ) )
        {
            return "net.minecraft.server." + version + ".IChatBaseComponent$ChatSerializer";
        }
        return "net.minecraft.server." + version + ".ChatSerializer";
    }

    private static String getPacketPlayOutChat()
    {
        return "net.minecraft.server." + version + ".PacketPlayOutChat";
    }
}