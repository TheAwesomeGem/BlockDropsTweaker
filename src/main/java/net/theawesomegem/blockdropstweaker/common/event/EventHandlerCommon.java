package net.theawesomegem.blockdropstweaker.common.event;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.theawesomegem.blockdropstweaker.common.blockconfig.BlockDropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.ConfigurationHandler;
import net.theawesomegem.blockdropstweaker.common.blockconfig.DropData;
import net.theawesomegem.blockdropstweaker.common.blockconfig.FortuneQuantityData;
import net.theawesomegem.blockdropstweaker.hook.GameStageHook;
import net.theawesomegem.blockdropstweaker.hook.TConstructHook;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TheAwesomeGem on 12/21/2017.
 */
public class EventHandlerCommon
{
    @SubscribeEvent
    public void onHarvestBlock(HarvestDropsEvent e)
    {
        World world = e.getWorld();

        if(world.isRemote)
            return;

        ItemStack toolUsed = ItemStack.EMPTY;

        if(e.getHarvester() != null && e.getHarvester().isEntityAlive())
        {
            EntityPlayer player = e.getHarvester();
            ItemStack itemStack = player.getHeldItemMainhand();

            if(!itemStack.isEmpty())
                toolUsed = itemStack.copy();
        }

        IBlockState blockState = e.getState();
        Block block = blockState.getBlock();
        String blockID = block.getRegistryName().toString();
        int metadata = block.getMetaFromState(blockState);
        Random random = world.rand;

        BlockDropData blockDropData = ConfigurationHandler.getBlockDropData(block, blockID, metadata);

        if(blockDropData == null)
            return;

        if(!GameStageHook.isGameStage(e.getHarvester(), blockDropData))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        if(!TConstructHook.isModifier(toolUsed, blockDropData))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        if(!TConstructHook.isTrait(toolUsed, blockDropData))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        if(!hasEnchantment(toolUsed, blockDropData))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        if(!hasNBT(toolUsed, blockDropData))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        int yPos = e.getPos().getY();

        if(!(yPos >= blockDropData.minYLevel && yPos <= blockDropData.maxYLevel))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        if(!isToolAllowed(toolUsed, blockDropData))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        Biome biome = world.getBiome(e.getPos());
        String biomeID = biome.getRegistryName().toString();

        if(!isBiomeAllowed(biomeID, blockDropData))
        {
            /* TODO: Uncomment this if it gets to be a problem.
            if(blockDropData.replace)
                e.getDrops().clear();*/

            return;
        }

        int fortuneLevel = e.getFortuneLevel();
        int chance = blockDropData.fortunechance.get(fortuneLevel);

        List<ItemStack> dropList = new ArrayList<>();
        ItemStack silkTouchItem = ItemStack.EMPTY;

        if(!blockDropData.replace)
        {
            dropList.addAll(e.getDrops());
        }

        if (e.isSilkTouching() && blockDropData.allowsilktouch)
        {
            silkTouchItem = getSilkTouchDrop(block, blockState);
        }

        ItemStack itemToRemove = ItemStack.EMPTY;

        for(ItemStack itemStack : dropList)
        {
            if(itemStack.isItemEqual(silkTouchItem))
                itemToRemove = itemStack;
        }

        if(!itemToRemove.isEmpty())
            dropList.remove(itemToRemove);

        if(!silkTouchItem.isEmpty())
            dropList.add(silkTouchItem);

        e.getDrops().clear();

        dropList.addAll(getDrops(random, blockDropData, fortuneLevel, toolUsed, yPos, biomeID, e.getHarvester()));

        int perc = MathHelper.getInt(random, 0, 100);

        if(chance >= perc)
        {
            e.getDrops().addAll(dropList);
        }
        else if(blockDropData.dropsilktouchalways)
        {
            if (!silkTouchItem.isEmpty())
            {
                e.getDrops().add(silkTouchItem);
            }
        }
    }

    public static boolean hasNBT(ItemStack toolItem, DropData dropData)
    {
        if(dropData.nbtList.isEmpty() && dropData.nbtBlacklist)
            return true;

        if(toolItem.isEmpty())
            return dropData.nbtBlacklist;

        if(!toolItem.hasTagCompound())
            return dropData.nbtBlacklist;

        boolean nbtExist = false;

        for(String nbt : dropData.nbtList)
        {
            if(toolItem.getTagCompound().hasKey(nbt))
            {
                nbtExist = true;

                break;
            }
        }

        if(dropData.nbtBlacklist && nbtExist)
            return false;

        if((!dropData.nbtBlacklist) && (!nbtExist))
            return false;

        return true;
    }

    public static boolean hasNBT(ItemStack toolItem, BlockDropData blockDropData)
    {
        if(blockDropData.nbtList.isEmpty() && blockDropData.nbtBlacklist)
            return true;

        if(toolItem.isEmpty())
            return blockDropData.nbtBlacklist;

        if(!toolItem.hasTagCompound())
            return blockDropData.nbtBlacklist;

        boolean nbtExist = false;

        for(String nbt : blockDropData.nbtList)
        {
            if(toolItem.getTagCompound().hasKey(nbt))
            {
                nbtExist = true;

                break;
            }
        }

        if(blockDropData.nbtBlacklist && nbtExist)
            return false;

        if((!blockDropData.nbtBlacklist) && (!nbtExist))
            return false;

        return true;
    }

    private boolean hasEnchantment(ItemStack toolItem, DropData dropData)
    {
        if(dropData.enchantmentList.isEmpty() && dropData.enchantmentBlacklist)
            return true;

        if(toolItem.isEmpty())
            return dropData.enchantmentBlacklist;

        if(!toolItem.isItemEnchanted())
            return dropData.enchantmentBlacklist;

        boolean enchantmentExist = false;

        for(String enchantment : dropData.enchantmentList)
        {
            Enchantment enchantmentObj = Enchantment.getEnchantmentByLocation(enchantment);

            if(enchantmentObj == null)
                continue;

            if (EnchantmentHelper.getEnchantmentLevel(enchantmentObj, toolItem) > 0)
            {
                enchantmentExist = true;

                break;
            }
        }

        if(dropData.enchantmentBlacklist && enchantmentExist)
            return false;

        if((!dropData.enchantmentBlacklist) && (!enchantmentExist))
            return false;

        return true;
    }

    private boolean hasEnchantment(ItemStack toolItem, BlockDropData blockDropData)
    {
        if(blockDropData.enchantmentList.isEmpty() && blockDropData.enchantmentBlacklist)
            return true;

        if(toolItem.isEmpty())
            return blockDropData.enchantmentBlacklist;

        if(!toolItem.isItemEnchanted())
            return blockDropData.enchantmentBlacklist;

        boolean enchantmentExist = false;

        for(String enchantment : blockDropData.enchantmentList)
        {
            Enchantment enchantmentObj = Enchantment.getEnchantmentByLocation(enchantment);

            if(enchantmentObj == null)
                continue;

            if (EnchantmentHelper.getEnchantmentLevel(enchantmentObj, toolItem) > 0)
            {
                enchantmentExist = true;

                break;
            }
        }

        if(blockDropData.enchantmentBlacklist && enchantmentExist)
            return false;

        if((!blockDropData.enchantmentBlacklist) && (!enchantmentExist))
            return false;

        return true;
    }

    private boolean isToolAllowed(ItemStack toolItem, BlockDropData blockDropData)
    {
        for(String minedWith : blockDropData.tools)
        {
            if(blockDropData.toolsBlacklist)
            {
                return !isUsingItem(toolItem, minedWith);
            }
            else
            {
                return isUsingItem(toolItem, minedWith);
            }
        }

        return blockDropData.toolsBlacklist;
    }

    private boolean isBiomeAllowed(String biomeID, BlockDropData blockDropData)
    {
        if(blockDropData.biomeBlacklist)
        {
            return !blockDropData.biomes.contains(biomeID);
        }
        else
        {
            return blockDropData.biomes.contains(biomeID);
        }
    }

    private List<ItemStack> getDrops(Random random, BlockDropData blockDropData, int fortune, ItemStack heldItem, int yLevel, String biomeID, EntityPlayer harvester)
    {
        List<ItemStack> drops = new ArrayList<>();

        if(blockDropData.dropdatalist.isEmpty())
            return new ArrayList<>();

        for(DropData dropData : blockDropData.dropdatalist)
        {
            Item item = Item.getByNameOrId(dropData.id);

            if(item == null)
                continue;

            FortuneQuantityData fortuneQuantityData = dropData.fortunequantitymap.get(fortune);

            if(fortuneQuantityData == null)
                continue;

            int quantity = MathHelper.getInt(random, fortuneQuantityData.minquantity, fortuneQuantityData.maxquantity);
            int chance = dropData.fortunechancemap.get(fortune);
            int perc = MathHelper.getInt(random, 1, 100);

            if(chance < perc)
                continue;

            if(!(yLevel >= dropData.minYLevel && yLevel <= dropData.maxYLevel))
                continue;

            if(!isToolAllowed(heldItem, dropData))
                continue;

            if(!isBiomeAllowed(biomeID, dropData))
                continue;

            if(!GameStageHook.isGameStage(harvester, dropData))
                continue;

            if(!TConstructHook.isModifier(heldItem, dropData))
                continue;

            if(!TConstructHook.isTrait(heldItem, dropData))
                continue;

            if(!hasEnchantment(heldItem, dropData))
                continue;

            if(!hasNBT(heldItem, blockDropData))
                continue;

            drops.add(new ItemStack(item, quantity, dropData.metadata));
        }

        return drops;
    }

    private boolean isToolAllowed(ItemStack toolItem, DropData dropData)
    {
        for(String minedWith : dropData.tools)
        {
            if(dropData.toolsBlacklist)
            {
                return !isUsingItem(toolItem, minedWith);
            }
            else
            {
                return isUsingItem(toolItem, minedWith);
            }
        }

        return dropData.toolsBlacklist;
    }

    private boolean isBiomeAllowed(String biomeID, DropData dropData)
    {
        if(dropData.biomeBlacklist)
        {
            return !dropData.biomes.contains(biomeID);
        }
        else
        {
            return dropData.biomes.contains(biomeID);
        }
    }

    private ItemStack getSilkTouchDrop(Block block, IBlockState state)
    {
        Item item = Item.getItemFromBlock(block);
        int i = 0;

        if (item.getHasSubtypes())
        {
            i = block.getMetaFromState(state);
        }

        return new ItemStack(item, 1, i);
    }

    private boolean isUsingItem(ItemStack heldItem, String itemData)
    {
        if(heldItem.isEmpty())
            return false;

        String[] itemArray = itemData.split(",");
        String itemID = itemArray[0];
        String metadata = itemArray[1];

        Item item = Item.getByNameOrId(itemID);

        if (item == null)
            return false;

        ItemStack itemStack;

        if (metadata.equals("*"))
        {
            itemStack = new ItemStack(item, heldItem.getCount());

            return !itemStack.isEmpty() && itemStack.getItem().equals(heldItem.getItem());
        }
        else
        {
            itemStack = new ItemStack(item, heldItem.getCount(), Integer.parseInt(metadata));

            return ItemStack.areItemsEqual(heldItem, itemStack);
        }
    }
}
