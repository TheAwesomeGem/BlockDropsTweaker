package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.DropTraitAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropTraitClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropTraitRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class DropTraitCommand extends BlockDropsTreeBaseCommand
{
    public DropTraitCommand()
    {
        addSubcommand(new DropTraitAddCommand());
        addSubcommand(new DropTraitRemoveCommand());
        addSubcommand(new DropTraitClearCommand());
    }

    @Override
    public String getName()
    {
        return "trait";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.trait";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);

        BlockDropData blockDropData = (playerData.getSelectedBlock() == null ? null : ConfigurationHandler.blockDropMap.get(playerData.getSelectedBlock()));

        if(blockDropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("The block associated with the drop does not exist."));

            return;
        }

        DropData dropData = DropData.getDropData(blockDropData, playerData.getSelectedDrop());

        if(dropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a drop first using '/bd drop select'"));

            return;
        }

        player.sendMessage(ChatUtil.getNormalMessage("-----Traits-----"));

        for(String trait : dropData.traitList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(trait));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}
