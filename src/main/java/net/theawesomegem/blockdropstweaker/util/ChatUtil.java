package net.theawesomegem.blockdropstweaker.util;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.theawesomegem.blockdropstweaker.networking.PrimaryPacketHandler;
import net.theawesomegem.blockdropstweaker.networking.packet.PacketTextCopyC;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 * Credit to CraftTweaker for some of the helper methods.
 */
public class ChatUtil
{
    public static final String copyCommandBase = "/bd copy ";

    public static final ITextComponent EMPTY_TEXTMESSAGE = new TextComponentString("");


    public static void sendMessageWithCopy(EntityPlayer player, String holeMessage, String copyMessage)
    {
        player.sendMessage(getCopyMessage(holeMessage, copyMessage));
    }

    static void copyCommandRun(ICommandSender sender, String[] args)
    {

        StringBuilder message = new StringBuilder();

        for (int i = 0; i < args.length; i++)
        {
            message.append(args[i]);
            if (i != args.length - 1)
                message.append(" ");
        }

        if (sender.getCommandSenderEntity() instanceof EntityPlayer)
        {
            copyStringPlayer((EntityPlayer) sender.getCommandSenderEntity(), message.toString());

            sender.sendMessage(new TextComponentString(message.toString() + " was copied. Press Ctrl+V to paste."));
        }
        else
        {
            sender.sendMessage(new TextComponentString("Only a player can execute this command."));
        }
    }

    static void copyStringPlayer(EntityPlayer player, String s)
    {
        if (player instanceof EntityPlayerMP)
        {
            PrimaryPacketHandler.INSTANCE.sendTo(new PacketTextCopyC(s), (EntityPlayerMP) player);
        }
    }

    public static ITextComponent getClickableCommandText(String message, String command, boolean runDirectly)
    {

        Style style = new Style();
        ClickEvent click = new ClickEvent(runDirectly ? ClickEvent.Action.RUN_COMMAND : ClickEvent.Action.SUGGEST_COMMAND, command);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to execute [\u00A76" + command + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getClickableBrowserLinkText(String message, String url)
    {

        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, url);
        style.setClickEvent(click);
        style.setColor(TextFormatting.AQUA);
        style.setUnderlined(true);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to Open [\u00A76" + url + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static List<String> getOreDictOfItem(ItemStack stack)
    {
        if(stack.isEmpty())
            return new ArrayList<>();

        int[] ids = OreDictionary.getOreIDs(stack);
        List<String> names = new ArrayList<>();

        for (int id : ids)
        {
            names.add(OreDictionary.getOreName(id));
        }

        return names;
    }


    public static RayTraceResult getPlayerLookat(EntityPlayer player, double range)
    {
        Vec3d eyes = player.getPositionEyes(1.0F);

        return player.getEntityWorld().rayTraceBlocks(eyes, eyes.add(player.getLookVec().scale(range)));
    }

    public static ITextComponent getFileOpenText(String message, String filepath)
    {

        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_FILE, filepath);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to open [\u00A76" + filepath + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static ITextComponent getNormalMessage(String message)
    {
        return new TextComponentString(message);
    }

    public static ITextComponent getCopyMessage(String message, String copyMessage)
    {
        Style style = new Style();
        ClickEvent click = new ClickEvent(ClickEvent.Action.RUN_COMMAND, copyCommandBase + copyMessage);
        style.setClickEvent(click);

        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy [\u00A76" + copyMessage + "\u00A7r]"));
        style.setHoverEvent(hoverEvent);

        return new TextComponentString(message).setStyle(style);
    }

    public static String getItemStackID(Item item)
    {
        if(item == null)
            return "EMPTY";

        return item.getRegistryName().toString();
    }

    public static void copyMessage(EntityPlayer player, String message)
    {
        if (player instanceof EntityPlayerMP)
            PrimaryPacketHandler.INSTANCE.sendTo(new PacketTextCopyC(message), (EntityPlayerMP) player);
    }
}
