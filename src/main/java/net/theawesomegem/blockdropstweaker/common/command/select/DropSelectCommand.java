package net.theawesomegem.blockdropstweaker.common.command.select;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/6/2018.
 */
public class DropSelectCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "select";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.select";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if (args.length < 1)
        {
            throw new WrongUsageException(this.getUsage(player));
        }

        String itemData = args[0];
        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);
        String blockData = playerData.getSelectedBlock();
        BlockDropData blockDropData = ConfigurationHandler.blockDropMap.get(blockData);

        if (blockData == null || blockDropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Select a block first using '/bd block select'"));

            return;
        }

        DropData dropData = DropData.getDropData(blockDropData, itemData);

        if (dropData == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("The drop does not exist. Please add it first to configure."));

            return;
        }

        playerData.setSelectedDrop(itemData);

        player.sendMessage(ChatUtil.getNormalMessage("Selected Drop:" + itemData + " for block:" + blockData));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        if (!(sender instanceof EntityPlayer))
            return tabList;

        EntityPlayer player = (EntityPlayer) sender;
        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);
        String blockData = playerData.getSelectedBlock();

        if (blockData != null)
        {
            BlockDropData blockDropData = ConfigurationHandler.blockDropMap.get(blockData);

            for (DropData dropData : blockDropData.dropdatalist)
                tabList.add(dropData.id + "," + dropData.metadata);
        }

        return getListOfStringsMatchingLastWord(args, tabList);
    }
}
