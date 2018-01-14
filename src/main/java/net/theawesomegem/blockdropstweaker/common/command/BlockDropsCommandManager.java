package net.theawesomegem.blockdropstweaker.common.command;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * Created by TheAwesomeGem on 1/6/2018.
 */
public class BlockDropsCommandManager
{
    public static void init(FMLServerStartingEvent e)
    {
        e.registerServerCommand(new BlockDropsCommand());
    }
}
