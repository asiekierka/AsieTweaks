package pl.asie.tweaks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import pl.asie.tweaks.skin.SkinHandler;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class NetworkHandler implements IPacketHandler, IConnectionHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		DataInputStream packetData = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			int commandType = packetData.readUnsignedByte();
			System.out.println("Received packet, command " + commandType);
			switch(commandType) {
				case 1: { // Reload player skin
					String playerName = packetData.readUTF();
					SkinHandler.invalidate(playerName, true);
				} break;
				case 2: { // Set skin/cape URLs
					String skinURL = packetData.readUTF();
					String capeURL = packetData.readUTF();
					AsieTweaks.proxy.setSkin(skinURL, capeURL);
					AsieTweaks.proxy.invalidateAll();
				} break;
			}
		}
		catch(Exception e) { e.printStackTrace(); }
	}

    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler,
                    INetworkManager manager) {
            // Send skin and cape URLs
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(baos);
		try {
			data.writeByte((byte)2);
			data.writeUTF(AsieTweaks.skinURL);
			data.writeUTF(AsieTweaks.capeURL);
			PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload("AsieTweaks", baos.toByteArray()), player);
		} catch(Exception e) { e.printStackTrace(); }
    }

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) {
		// TODO Auto-generated method stub
		
	}

}
