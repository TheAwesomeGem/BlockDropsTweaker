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
public class DropAddCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "add";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.add";
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
            player.sendMessage(ChatUtil.getNormalMessage("Select a block first using '/bd block select'"));

            return;
        }

        String itemID = args[0];
        String metadataStr = args[1];

        int metadata;

        try
        {
            metadata = Integer.parseInt(metadataStr);
        }
        catch (NumberFormatException e)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Metadata can only be a whole number."));

            return;
        }

        Item item = Item.getByNameOrId(itemID);

        if(item == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("The item with that id does not exist."));

            return;
        }

        DropData dropData = new DropData();
        dropData.id = itemID;
        dropData.metadata = metadata;

        blockDropData.dropdatalist.add(dropData);
        playerData.setSelectedDrop(dropData.id + "," + dropData.metadata);

        player.sendMessage(ChatUtil.getNormalMessage("Added drop '" + itemID + "' with metadata '" + metadataStr + "'"));
        player.sendMessage(ChatUtil.getNormalMessage("Use '/bd drop set' to edit the drop info now."));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabListItems = new ArrayList<>();

        if(args.length == 1)
        {
            tabListItems.addAll(getListOfStringsMatchingLastWord(args, Item.REGISTRY.getKeys()));
        }

        return tabListItems;
    }
}
