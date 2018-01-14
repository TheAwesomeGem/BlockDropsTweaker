package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.DropModifierAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropModifierClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropModifierRemoveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class DropModifierCommand extends BlockDropsTreeBaseCommand
{
    public DropModifierCommand()
    {
        addSubcommand(new DropModifierAddCommand());
        addSubcommand(new DropModifierRemoveCommand());
        addSubcommand(new DropModifierClearCommand());
    }

    @Override
    public String getName()
    {
        return "modifier";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.modifier";
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Modifiers-----"));

        for(String modifier : dropData.modifierList)
        {
            player.sendMessage(ChatUtil.getNormalMessage(modifier));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}