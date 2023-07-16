package nl.vinstaal0.Dungeonrealms;

import minecade.dungeonrealms.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class BungeeConnector implements PluginMessageListener {

    public void onEnable() {
        Main.getPlugin().getServer().getMessenger().registerOutgoingPluginChannel(Main.getPlugin(), "BungeeCord");
        Main.getPlugin().getServer().getMessenger().registerIncomingPluginChannel(Main.getPlugin(), "BungeeCord", this);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        try{
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
            String subchannel = in.readUTF();
            if (subchannel.equals("playerchannel")) {
                short len = in.readShort();
                byte[] data = new byte[len];
                in.readFully(data);

                //bytearray to string
                String s = new String(data);

                System.out.println(s);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void sendPlayerToShard(String subchannel, String target, String s) {
        try{
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);

            out.writeUTF("Forward");
            out.writeUTF(target);
            out.writeUTF(subchannel);
            byte[] data = s.getBytes();
            out.writeShort(data.length);
            out.write(data);

            Player p = Bukkit.getPlayer("someplayer");

            p.sendPluginMessage(Main.getPlugin(), "BungeeCord", b.toByteArray());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
