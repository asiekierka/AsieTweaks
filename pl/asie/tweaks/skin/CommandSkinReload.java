package pl.asie.tweaks.skin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.DamageSource;

public class CommandSkinReload extends CommandBase
{
    public String getCommandName()
    {
        return "skinreload";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.skinreload.usage";
    }

    public void processCommand(ICommandSender sender, String[] args)
    {
    	if(args.length > 0) {
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		DataOutputStream data = new DataOutputStream(baos);
    		try {
    			data.writeByte((byte)1);
    			data.writeUTF(args[0]);
    			PacketDispatcher.sendPacketToAllPlayers(new Packet250CustomPayload("AsieTweaks", baos.toByteArray()));
    		} catch(Exception e) { e.printStackTrace(); }
    	} else throw new WrongUsageException(getCommandUsage(sender), new Object[0]);
    }
}
