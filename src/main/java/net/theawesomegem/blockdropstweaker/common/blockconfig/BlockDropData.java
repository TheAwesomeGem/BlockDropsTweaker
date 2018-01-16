package net.theawesomegem.blockdropstweaker.common.blockconfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TheAwesomeGem on 1/5/2018.
 */
public class BlockDropData
{
    public boolean replace = true;
    public boolean allowsilktouch = true;
    public boolean dropsilktouchalways = true;
    public boolean toolsBlacklist = true;
    public List<String> tools = new ArrayList<>();
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
    public Map<Integer, Integer> fortunechance = new HashMap<Integer, Integer>()
    {{
        put(0, 100);
        put(1, 100);
        put(2, 100);
        put(3, 100);
    }};
    public List<DropData> dropdatalist = new ArrayList<>();

    public BlockDropData duplicate()
    {
        BlockDropData blockDropData = new BlockDropData();

        blockDropData.replace = this.replace;
        blockDropData.allowsilktouch = this.allowsilktouch;
        blockDropData.dropsilktouchalways = this.dropsilktouchalways;
        blockDropData.toolsBlacklist = this.toolsBlacklist;
        blockDropData.tools = new ArrayList<>(this.tools);
        blockDropData.fortunechance = new HashMap<>(this.fortunechance);
        blockDropData.dropdatalist = new ArrayList<>(this.dropdatalist);
        blockDropData.minYLevel = this.minYLevel;
        blockDropData.maxYLevel = this.maxYLevel;
        blockDropData.minExp = this.minExp;
        blockDropData.maxExp = this.maxExp;
        blockDropData.biomeBlacklist = this.biomeBlacklist;
        blockDropData.biomes = new ArrayList<>(this.biomes);
        blockDropData.gamestageBlacklist = this.gamestageBlacklist;
        blockDropData.gamestageAll = this.gamestageAll;
        blockDropData.gameStageList = new ArrayList<>(this.gameStageList);
        blockDropData.modifierBlacklist = this.modifierBlacklist;
        blockDropData.modifierList = new ArrayList<>(this.modifierList);
        blockDropData.traitBlacklist = this.traitBlacklist;
        blockDropData.traitList = new ArrayList<>(this.traitList);
        blockDropData.enchantmentBlacklist = this.enchantmentBlacklist;
        blockDropData.enchantmentList = new ArrayList<>(this.enchantmentList);
        blockDropData.nbtBlacklist = this.nbtBlacklist;
        blockDropData.nbtList = new ArrayList<>(this.nbtList);

        return blockDropData;
    }
}
