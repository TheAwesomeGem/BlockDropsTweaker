package net.theawesomegem.blockdropstweaker.common.command.add;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.theawesomegem.blockdropstweaker.Primary;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerDataCapabilityProvider;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsBaseCommand;
import net.theawesomegem.blockdropstweaker.hook.TConstructHook;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;
import slimeknights.tconstruct.library.modifiers.IModifier;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/14/2018.
 */
public class DropModifierAddCommand extends BlockDropsBaseCommand
{
    @Override
    public String getName()
    {
        return "add";
    }

    @Override
    protected String getCommandPath()
    {
        return "drop.modifier.add";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        if(args.length < 1)
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

        String modifier = args[0];

        if(!Primary.Instance.isTConstructLoaded())
        {
            player.sendMessage(ChatUtil.getNormalMessage("Tinker Construct is not loaded."));

            return;
        }

        if(!TConstructHook.hasModifier(modifier))
        {
            player.sendMessage(ChatUtil.getNormalMessage("That modifier does not exist."));

            return;
        }

        if(dropData.modifierList.contains(modifier))
        {
            player.sendMessage(ChatUtil.getNormalMessage("That modifier already exists."));

            return;
        }

        dropData.modifierList.add(modifier);

        player.sendMessage(ChatUtil.getNormalMessage("Added Modifier '" + modifier + "'"));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
        List<String> tabList = new ArrayList<>();

        if(args.length == 1)
        {
            if (Primary.Instance.isTConstructLoaded())
            {
                List<String> modifierList = new ArrayList<>();

                for(IModifier modifier : TConstructHook.getAllModifiers())
                {
                    modifierList.add(modifier.getIdentifier());
                }

                tabList.addAll(getListOfStringsMatchingLastWord(args, modifierList));
            }
        }

        return tabList;
    }
}
