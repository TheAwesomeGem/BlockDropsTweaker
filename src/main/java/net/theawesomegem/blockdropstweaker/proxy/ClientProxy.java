package net.theawesomegem.blockdropstweaker.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.theawesomegem.blockdropstweaker.client.event.KeyBindingHandler;
import net.theawesomegem.blockdropstweaker.client.keybind.KeybindManager;

/**
 * Created by TheAwesomeGem on 6/18/2017.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void onPreInit(FMLPreInitializationEvent e)
    {
        super.onPreInit(e);
    }

    @Override
    public void onInit(FMLInitializationEvent e)
    {
        super.onInit(e);

        KeybindManager.load();
    }

    @Override
    protected void registerEvents()
    {
        super.registerEvents();

        MinecraftForge.EVENT_BUS.register(new KeyBindingHandler());
    }

    @Override
    public IThreadListener getListener(MessageContext ctx)
    {
        return ctx.side == Side.CLIENT ? Minecraft.getMinecraft() : super.getListener(ctx);
    }

    @Override
    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.side == Side.CLIENT ? Minecraft.getMinecraft().player : super.getPlayer(ctx);
    }
}
