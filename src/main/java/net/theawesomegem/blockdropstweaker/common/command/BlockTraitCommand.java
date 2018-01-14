package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockTraitAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockTraitClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockTraitRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class BlockTraitCommand extends BlockDropsTreeBaseCommand
{
    public BlockTraitCommand()
    {
        addSubcommand(new BlockTraitAddCommand());
        addSubcommand(new BlockTraitRemoveCommand());
        addSubcommand(new BlockTraitClearCommand());
    }

    @Override
    public String getName()
    {
        return "trait";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.trait";
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Traits-----"));

        for(String trait : blockDropData.traitList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(trait));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}
