package net.theawesomegem.blockdropstweaker.common.blockconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TheAwesomeGem on 1/5/2018.
 */
public class DropData
{
    public String id;
    public int metadata;
    public boolean toolsBlacklist = true;
    public List<String> tools = new ArrayList<>();
    public Map<Integer, Integer> fortunechancemap = new HashMap<Integer, Integer>()
    {{
        put(0, 100);
        put(1, 100);
        put(2, 100);
        put(3, 100);
    }};
    public Map<Integer, FortuneQuantityData> fortunequantitymap = new HashMap<Integer, FortuneQuantityData>()
    {{
        put(0, new FortuneQuantityData());
        put(1, new FortuneQuantityData());
        put(2, new FortuneQuantityData());
        put(3, new FortuneQuantityData());
    }};
    public boolean biomeBlacklist = true;
    public List<String> biomes = new ArrayList<>();
    public int minYLevel = 0;
    public int maxYLevel = 256;
    public int minExp = 0;
    public int maxExp = 0;
    public boolean gamestageBlacklist = true;
    public boolean gamestageAll = true;
    public List<String> gameStageList = new ArrayList<>();
    public boolean modifierBlacklist = true;
    public List<String> modifierList = new ArrayList<>();
    public boolean traitBlacklist = true;
    public List<String> traitList = new ArrayList<>();
    public boolean enchantmentBlacklist = true;
    public List<String> enchantmentList = new ArrayList<>();
    public boolean nbtBlacklist = true;
    public List<String> nbtList = new ArrayList<>();


    public DropData duplicate()
    {
        DropData dropData = new DropData();

        dropData.toolsBlacklist = this.toolsBlacklist;
        dropData.tools = new ArrayList<>(this.tools);
        dropData.fortunechancemap = new HashMap<>(this.fortunechancemap);
        dropData.fortunequantitymap = new HashMap<>(this.fortunequantitymap);
        dropData.minYLevel = this.minYLevel;
        dropData.maxYLevel = this.maxYLevel;
        dropData.minExp = this.minExp;
        dropData.maxExp = this.maxExp;
        dropData.biomeBlacklist = this.biomeBlacklist;
        dropData.biomes = new ArrayList<>(this.biomes);
        dropData.gamestageBlacklist = this.gamestageBlacklist;
        dropData.gamestageAll = this.gamestageAll;
        dropData.gameStageList = new ArrayList<>(this.gameStageList);
        dropData.modifierBlacklist = this.modifierBlacklist;
        dropData.modifierList = new ArrayList<>(this.modifierList);
        dropData.traitBlacklist = this.traitBlacklist;
        dropData.traitList = new ArrayList<>(this.traitList);
        dropData.enchantmentBlacklist = this.enchantmentBlacklist;
        dropData.enchantmentList = new ArrayList<>(this.enchantmentList);
        dropData.nbtBlacklist = this.nbtBlacklist;
        dropData.nbtList = new ArrayList<>(this.nbtList);

        return dropData;
    }

    public static boolean hasDropData(BlockDropData blockDropData, String itemID, int metadata)
    {
        if(itemID == null)
            return false;

        if(blockDropData.dropdatalist.size() <= 0)
            return false;

        for(DropData dropData : blockDropData.dropdatalist)
        {
            if(dropData.id.equals(itemID) && dropData.metadata == metadata)
                return true;
        }

        return false;
    }

    public static DropData getDropData(BlockDropData blockDropData, String itemData)
    {
        if(itemData == null)
            return null;

        String[] itemArgs = itemData.split(",");

        if(itemArgs.length < 2)
            return null;

        String itemID = itemArgs[0];
        String metadataStr = itemArgs[1];

        int metadata;

        try
        {
            metadata = Integer.parseInt(metadataStr);
        }
        catch (NumberFormatException e)
        {
            return null;
        }

        for(DropData dropData : blockDropData.dropdatalist)
        {
            if(dropData.id.equals(itemID) && dropData.metadata == metadata)
                return dropData;
        }

        return null;
    }
}
