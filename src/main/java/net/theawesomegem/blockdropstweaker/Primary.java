package net.theawesomegem.blockdropstweaker;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.theawesomegem.blockdropstweaker.proxy.CommonProxy;

@Mod(modid = ModInfo.MOD_ID, version = ModInfo.VERSION, name = ModInfo.MOD_NAME)
public class Primary
{
    private boolean gameStagesLoaded, tConstructLoaded = false;

    public boolean isGameStagesLoaded()
    {
        return gameStagesLoaded;
    }

    public boolean isTConstructLoaded()
    {
        return tConstructLoaded;
    }

    @Instance(ModInfo.MOD_ID)
    public static Primary Instance;

    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.SERVER_PROXY)
    public static CommonProxy proxy;

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent e)
    {
        proxy.onPreInit(e);
    }

    @EventHandler
    public void onInit(FMLInitializationEvent e)
    {
        proxy.onInit(e);
    }

    @EventHandler
    public void onPostInit(FMLPostInitializationEvent e)
    {
        proxy.onPostInit(e);

        gameStagesLoaded = Loader.isModLoaded("gamestages");
        tConstructLoaded = Loader.isModLoaded("tconstruct");
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent e)
    {
        proxy.onServerStarting(e);
    }
}
