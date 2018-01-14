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
public class BlockAddCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "add";
    }

    @Override
    protected String getCommandPath()
    {
        return "block.add";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if(args.length < 2)
        {
            throw new WrongUsageException(this.getUsage(player));
        }

        IPlayerData playerData = player.getCapability(PlayerDataCapabilityProvider.PLAYERDATA_CAP, null);
        String blockID = args[0];
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

        String blockInfo = blockID + "," + metadataStr;

        if(ConfigurationHandler.blockDropMap.containsKey(blockInfo))
        {
            player.sendMessage(ChatUtil.getNormalMessage("Drops for that block already exists."));

            return;
        }

        ConfigurationHandler.blockDropMap.put(blockInfo, new BlockDropData());
        playerData.setSelectedBlock(blockInfo);

        player.sendMessage(ChatUtil.getNormalMessage("Added block '" + blockID + "' with metadata '" + metadataStr + "'"));
        player.sendMessage(ChatUtil.getNormalMessage("Use '/bd block set' to edit the block drops now."));
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
