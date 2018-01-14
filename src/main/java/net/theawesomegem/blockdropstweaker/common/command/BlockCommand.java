package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraft.command.CommandException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockAddCommand;
import net.theawesomegem.blockdropstweaker.common.command.add.BlockDupeCommand;
import net.theawesomegem.blockdropstweaker.common.command.get.BlockGetCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockClearCommand;
import net.theawesomegem.blockdropstweaker.common.command.remove.BlockRemoveCommand;
import net.theawesomegem.blockdropstweaker.common.command.set.BlockSetCommand;
import net.theawesomegem.blockdropstweaker.common.command.select.BlockSelectCommand;
import net.theawesomegem.blockdropstweaker.util.ChatUtil;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class BlockCommand extends BlockDropsTreeBaseCommand
{
    public BlockCommand()
    {
        this.addSubcommand(new BlockSelectCommand());
        this.addSubcommand(new BlockSetCommand());
        this.addSubcommand(new BlockGetCommand());
        this.addSubcommand(new BlockAddCommand());
        this.addSubcommand(new BlockRemoveCommand());
        this.addSubcommand(new BlockClearCommand());
        this.addSubcommand(new BlockDupeCommand());
        this.addSubcommand(new BlockToolCommand());
        this.addSubcommand(new BlockBiomeCommand());
        this.addSubcommand(new BlockGamestageCommand());
        this.addSubcommand(new BlockModifierCommand());
        this.addSubcommand(new BlockTraitCommand());
        this.addSubcommand(new BlockEnchantmentCommand());
        this.addSubcommand(new BlockNbtCommand());
    }

    @Override
    public String getName()
    {
        return "block";
    }

    @Override
    protected String getCommandPath()
    {
        return "block";
    }

    @Override
    protected void onCommand(MinecraftServer server, EntityPlayer player, String[] args) throws CommandException
    {
        player.sendMessage(ChatUtil.getNormalMessage("-----Blocks-----"));

        for(String blockInfo : ConfigurationHandler.blockDropMap.keySet())
        {
            player.sendMessage(ChatUtil.getClickableCommandText(blockInfo, "/bd block select " + blockInfo, true));
        }

        player.sendMessage(ChatUtil.getNormalMessage("----------------"));
    }
}
