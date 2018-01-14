package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockNbtAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockNbtClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockNbtRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class BlockNbtCommand extends BlockDropsTreeBaseCommand
{
    public BlockNbtCommand()
    {
        addSubcommand(new BlockNbtAddCommand());
        addSubcommand(new BlockNbtRemoveCommand());
        addSubcommand(new BlockNbtClearCommand());
    }

    @Override
    public String getName()
    {
        return "nbt";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.nbt";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);

        BlockDropData blockDropData = (playerData.getSelectedBlock() == null ? null : ConfigurationHandler.blockDropMap.get(playerData.getSelectedBlock()));

        if(blockDropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a block first using '/bd block select'"));

            return;
        }

        player.sendMessage(ChatUtil.getNormalMessage("-----NBT-----"));

        for(String nbt : blockDropData.nbtList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(nbt));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}
