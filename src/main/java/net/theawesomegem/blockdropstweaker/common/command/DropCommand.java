package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.add.DropAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.add.DropDupeCommand;
import net.theawesomegem.blockdropstweaker.common.command.get.DropGetCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.DropRemoveCommand;
import net.theawesomegem.blockdropstweaker.common.command.select.DropSelectCommand;
import net.theawesomegem.blockdropstweaker.common.command.set.DropSetCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class DropCommand extends BlockDropsTreeBaseCommand
{
    public DropCommand()
    {
        this.addSubcommand(new DropSelectCommand());
        this.addSubcommand(new DropSetCommand());
        this.addSubcommand(new DropGetCommand());
        this.addSubcommand(new DropAddCommand());
        this.addSubcommand(new DropRemoveCommand());
        this.addSubcommand(new DropClearCommand());
        this.addSubcommand(new DropDupeCommand());
        this.addSubcommand(new DropToolCommand());
        this.addSubcommand(new DropBiomeCommand());
        this.addSubcommand(new DropGamestageCommand());
        this.addSubcommand(new DropModifierCommand());
        this.addSubcommand(new DropTraitCommand());
        this.addSubcommand(new DropEnchantmentCommand());
        this.addSubcommand(new DropNbtCommand());
    }

    @Override
    public String getName()
    {
        return "drop";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop";
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

        player.sendMessage(ChatUtil.getNormalMessage("-----Drops-----"));

        for(DropData dropData : blockDropData.dropdatalist)
        {
            String dropInfo = dropData.id + "," + dropData.metadata;

            player.sendMessage(ChatUtil.getClickableCommandText(dropInfo, "/bd drop select " + dropInfo, true));
        }

        player.sendMessage(ChatUtil.getNormalMessage("---------------"));
    }
}
