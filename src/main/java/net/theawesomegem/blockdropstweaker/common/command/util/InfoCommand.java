package net.theawesomegem.blockdropstweaker.common.command.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class InfoCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "info";
    }

    @Override
    protected String getCommandPath()
    {
        return "info";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        Item item = null;
        boolean oreDictionary = args.length >= 1 && args[0].equalsIgnoreCase("ore");

        ItemStack itemStack = player.getHeldItemMainhand();

        if(itemStack.isEmpty())
        {
            RayTraceResult ray = ChatUtil.getPlayerLookat(player, 4.0);

            if (ray != null)
            {
                IBlockState blockState = player.world.getBlockState(ray.getBlockPos());

                if(!blockState.getMaterial().equals(Material.AIR))
                {
                    item = Item.getItemFromBlock(blockState.getBlock());
                }
            }
        }
        else
        {
            item = itemStack.getItem();
        }

        if(item == null)
        {
            player.sendMessage(ChatUtil.getNormalMessage("Could not find the item/block."));

            return;
        }

        ChatUtil.copyMessage(player, ChatUtil.getItemStackID(item));
        player.sendMessage(ChatUtil.EMPTY_TEXTMESSAGE);
        player.sendMessage(ChatUtil.getNormalMessage("'" + ChatUtil.getItemStackID(item) + "' has been copied."));

        if(oreDictionary)
        {
            List<String> oreDictEntries = ChatUtil.getOreDictOfItem(new ItemStack(item));

            player.sendMessage(ChatUtil.getNormalMessage("Ore Dictionary:"));
            player.sendMessage(ChatUtil.getNormalMessage("======="));

            for(String oreDict : oreDictEntries)
            {
                String oreName = "ore:" + oreDict;

                player.sendMessage(ChatUtil.getCopyMessage(oreName, oreName));
            }

            player.sendMessage(ChatUtil.getNormalMessage("======="));
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        if(args.length == 1)
        {
            tabList.add("ore");
        }

        return getListOfStringsMatchingLastWord(args, tabList);
    }
}

