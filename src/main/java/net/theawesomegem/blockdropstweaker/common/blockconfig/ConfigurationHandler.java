package net.theawesomegem.blockdropstweaker.common.blockconfig;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.theawesomegem.blockdropstweaker.ModInfo;
import net.theawesomegem.blockdropstweaker.proxy.CommonProxy;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by TheAwesomeGem on 1/5/2018.
 */
public class ConfigurationHandler
{
    public static Map<String, BlockDropData> blockDropMap = new HashMap<>();

    private static File modFolder = null;
    private static File blockConfigFile = null;

    private static JsonElement blockJson = null;

    public static void preInit(File file)
    {
        modFolder = new File(file, ModInfo.MOD_ID);

        if(!modFolder.exists() || !modFolder.isDirectory())
        {
            if(!modFolder.mkdir())
            {
                CommonProxy.Logger.log(Level.FATAL, "Could not create the folder for configuration.");
            }
        }

        if(modFolder == null || !modFolder.exists())
            return;

        blockConfigFile = new File(modFolder, "blockdrops.json");

        if(!blockConfigFile.exists())
            createConfig();

        blockJson = new JsonObject();

        loadConfig();
    }

    private static void createConfig()
    {
        try
        {
            if(!blockConfigFile.createNewFile())
            {
                CommonProxy.Logger.log(Level.FATAL, "Could not create the block config file.");

                return;
            }

            saveConfig();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void loadConfig()
    {
        if(blockConfigFile == null || !blockConfigFile.exists())
        {
            CommonProxy.Logger.log(Level.FATAL, "Could not load the block config file. File does not exist.");

            return;
        }

        try
        {
            String blockDropsString = Files.toString(blockConfigFile, Charset.defaultCharset());
            blockJson = new JsonParser().parse(blockDropsString);

            loadBlockDrops();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void saveConfig()
    {
        if(blockConfigFile == null || !blockConfigFile.exists())
        {
            CommonProxy.Logger.log(Level.FATAL, "Could not save the block config file. File does not exist.");

            return;
        }

        try
        {
            //String blockDropsString = Files.toString(blockConfigFile, Charset.defaultCharset());
            //blockJson = new JsonParser().parse(blockDropsString);
            String blockDropsString = saveBlockDrops();
            boolean writeable = blockConfigFile.setWritable(true);

            if(!writeable)
            {
                CommonProxy.Logger.log(Level.FATAL,"Cannot write/save the block config file. Insufficient permission.");

                return;
            }

            PrintWriter writer = new PrintWriter(blockConfigFile);

            writer.write(blockDropsString);

            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void loadBlockDrops()
    {
        JsonObject blockDropDataJson = blockJson.getAsJsonObject();

        Gson gson = new Gson();

        blockDropMap.clear();

        for(Entry<String, JsonElement> blockDataEntry : blockDropDataJson.entrySet())
        {
            JsonElement blockData = blockDataEntry.getValue();
            BlockDropData blockDropData = gson.fromJson(blockData, BlockDropData.class);
            blockDropMap.put(blockDataEntry.getKey(), blockDropData);
        }

    }


    private static String saveBlockDrops()
    {
        JsonObject blockDropDataJson = new JsonObject();
        Gson gson = new Gson();

        for(Entry<String, BlockDropData> blockDataEntry : blockDropMap.entrySet())
        {
            JsonElement element = gson.toJsonTree(blockDataEntry.getValue());

            blockDropDataJson.add(blockDataEntry.getKey(), element);
        }

        return blockDropDataJson.toString();
    }

    public static FortuneQuantityData getQuantityData(int min, int max)
    {
        FortuneQuantityData fortuneQuantityData = new FortuneQuantityData();
        fortuneQuantityData.minquantity = min;
        fortuneQuantityData.maxquantity = max;

        return fortuneQuantityData;
    }

    public static BlockDropData getBlockDropData(Block block, String blockID, int metadata)
    {
        BlockDropData blockDropData = blockDropMap.get(blockID + "," + metadata);

        if(blockDropData == null)
            blockDropData = blockDropMap.get(blockID + ",*");

        if(blockDropData == null)
        {
            ItemStack blockItemStack = new ItemStack(Item.getItemFromBlock(block), 1, metadata);

            if(blockItemStack.isEmpty())
                return null;

            int[] blockOreIds = OreDictionary.getOreIDs(blockItemStack);

            for (int oreId : blockOreIds)
            {
                String oreName = OreDictionary.getOreName(oreId);

                blockDropData = blockDropMap.get("ore:" + oreName + ",*");
            }
        }

        return blockDropData;
    }
}
