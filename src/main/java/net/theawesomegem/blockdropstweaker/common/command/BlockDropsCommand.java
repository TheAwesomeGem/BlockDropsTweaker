package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.util.CopyCommand;
import net.theawesomegem.blockdropstweaker.common.command.util.InfoCommand;
import net.theawesomegem.blockdropstweaker.common.command.util.ReloadCommand;
import net.theawesomegem.blockdropstweaker.common.command.util.SaveCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/6/2018.
 */
public class BlockDropsCommand extends BlockDropsTreeBaseCommand
{
    public BlockDropsCommand()
    {
        this.addSubcommand(new BlockCommand());
        this.addSubcommand(new DropCommand());
        this.addSubcommand(new InfoCommand());
        this.addSubcommand(new CopyCommand());
        this.addSubcommand(new ReloadCommand());
        this.addSubcommand(new SaveCommand());
    }

    @Override
    public String getName()
    {
        return "blockdrops";
    }

    @Override
    protected String getCommandPath()
    {
        return "blockdrops";
    }

    @Override
    public List<String> getAliases()
    {
        return new ArrayList<String>()
        {{
            add("bd");
            add("bdrops");
            add("bdrop");
        }};
    }

    @Override
    public void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);
        BlockDropData blockDropData = (playerData.getSelectedBlock() == null ? null : ConfigurationHandler.blockDropMap.get(playerData.getSelectedBlock()));
        DropData dropData;

        if(blockDropData == null)
        {
            playerData.setSelectedBlock(null);
        }
        else
        {
            dropData = (playerData.getSelectedDrop() == null ? null : DropData.getDropData(blockDropData, playerData.getSelectedDrop()));

            if(dropData == null)
                playerData.setSelectedDrop(null);
        }

        player.sendMessage(ChatUtil.getNormalMessage("Current Selected Block:" + (playerData.getSelectedBlock() == null ? "None" : playerData.getSelectedBlock()) ));
        player.sendMessage(ChatUtil.getNormalMessage("Current Selected Drop:" + (playerData.getSelectedDrop() == null ? "None" : playerData.getSelectedDrop()) ));
    }
}
