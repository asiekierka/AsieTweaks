package pl.asie.tweaks;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class CommandBlockInfo extends CommandBase
{
	private static String getItemIdentifierFor(Item item) {
		UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(item);
		if(uid == null) {
			return "Minecraft|" + item.getUnlocalizedName();
		} else return uid.modId + "|" + uid.name;
	}
	
	@Override
    public String getCommandName()
    {
        return "blockinfo";
    }

	@Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return "commands.asietweaks.blockinfo.usage";
    }
	
	@Override
    public int getRequiredPermissionLevel()
    {
        return 2;
    }

	@Override
    public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        EntityPlayerMP player = getCommandSenderAsPlayer(par1ICommandSender);
        ItemStack stack = player.inventory.getStackInSlot(player.inventory.currentItem);
        ChatMessageComponent chat = new ChatMessageComponent();
        chat.addText("Unique identifier: " + getItemIdentifierFor(stack.getItem()));
        player.sendChatToPlayer(chat);
    }
}
