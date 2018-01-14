package net.theawesomegem.blockdropstweaker.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

/**
 * Created by TheAwesomeGem on 1/9/2018.
 */
public class BiomeUtil
{
    public static Biome getBiome(String id)
    {
        Biome biome = Biome.REGISTRY.getObject(new ResourceLocation(id));

        return biome;
    }
}
