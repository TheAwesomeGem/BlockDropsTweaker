package net.theawesomegem.blockdropstweaker.common.command.add;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class DropToolAddCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "add";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.tool.add";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if(args.length < 2)
        {
            throw new WrongUsageException(this.getUsage(player));
        }

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

        String itemID = args[0];
        String metadataStr = args[1];

        if(!metadataStr.equals("*"))
        {
            int metadata;

            try
            {
                metadata = Integer.parseInt(metadataStr);
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(ChatUtil.getNormalMessage("Metadata can either be '*' for the wildcard or a number '0-99999'."));

                return;
            }
        }

        Item toolItem = Item.getByNameOrId(itemID);

        if(toolItem == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("That item does not exist."));

            return;
        }

        String toolInfo = itemID + "," + metadataStr;

        if(dropData.tools.contains(toolInfo))
        {
            player.sendMessage(ChatUtil.getNormalMessage("That tool already exists."));

            return;
        }

        dropData.tools.add(toolInfo);

        player.sendMessage(ChatUtil.getNormalMessage("Added Tool '" + toolInfo + "'"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        if(args.length == 1)
        {
            tabList.addAll(getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()));
        }

        return tabList;
    }
}
