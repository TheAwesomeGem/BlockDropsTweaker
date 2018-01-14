package net.theawesomegem.blockdropstweaker.networking.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by TheAwesomeGem on 1/8/2018.
 */
public class PacketTextCopyC implements IMessage, IMessageHandler<PacketTextCopyC, IMessage>
{

    private String data;

    public PacketTextCopyC()
    {
    }

    public PacketTextCopyC(String data)
    {
        this.data = data;
    }

    public String getData()
    {
        return data;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        data = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(PacketTextCopyC message, MessageContext ctx)
    {
        Minecraft.getMinecraft().addScheduledTask(() -> handle(message, ctx));
        return null;
    }

    private void handle(PacketTextCopyC message, MessageContext ctx)
    {
        if (Desktop.isDesktopSupported())
        {
            StringSelection stringSelection = new StringSelection(message.getData());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }
}