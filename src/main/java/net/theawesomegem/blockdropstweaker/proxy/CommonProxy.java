package net.theawesomegem.blockdropstweaker.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.capability.CapabilityHandler;
import net.theawesomegem.blockdropstweaker.common.capability.player.IPlayerData;
import net.theawesomegem.blockdropstweaker.common.capability.player.PlayerData;
import net.theawesomegem.blockdropstweaker.common.command.BlockDropsCommandManager;
import net.theawesomegem.blockdropstweaker.common.event.EventHandlerCommon;
import net.theawesomegem.blockdropstweaker.networking.PrimaryPacketHandler;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;


/**
 * Created by TheAwesomeGem on 6/18/2017.
 */

/**
 * Change Log:
 *
 *
 */

public class CommonProxy
{
    public static Logger Logger = null;

    public void onPreInit(FMLPreInitializationEvent e)
    {
        //Register Items/Blocks/Entities
        Logger = e.getModLog();

        ConfigurationHandler.preInit(e.getModConfigurationDirectory());
        PrimaryPacketHandler.registerPackets();
    }

    public void onInit(FMLInitializationEvent e)
    {
        registerCapabilities();
        registerEvents();
    }

    public void onPostInit(FMLPostInitializationEvent e)
    {

    }

    public void onServerStarting(FMLServerStartingEvent e)
    {
        BlockDropsCommandManager.init(e);
    }

    protected void registerCapabilities()
    {
        CapabilityManager.INSTANCE.register(IPlayerData.class, new IStorage<IPlayerData>()
        {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side)
            {
                return new NBTTagCompound();
            }

            @Override
            public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbt)
            {

            }

        }, PlayerData::new);
    }

    protected void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandlerCommon());
    }

    public IThreadListener getListener(MessageContext ctx)
    {
        return (WorldServer) ctx.getServerHandler().player.world;
    }

    public EntityPlayer getPlayer(MessageContext ctx)
    {
        return ctx.getServerHandler().player;
    }

    public boolean hasPermission(EntityPlayer player)
    {
        return player.canUseCommand(2, "gamemode");
    }
}
