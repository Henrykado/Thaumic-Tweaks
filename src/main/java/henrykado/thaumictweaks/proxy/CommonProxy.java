package henrykado.thaumictweaks.proxy;

import henrykado.thaumictweaks.NewBlockCrystal;
import henrykado.thaumictweaks.TT_Config;
import henrykado.thaumictweaks.ThaumicTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import thaumcraft.Thaumcraft;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.world.ore.ShardType;
import thaumcraft.common.tiles.crafting.TileResearchTable;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    }

    public void postInit(FMLPostInitializationEvent e) {
    }
    
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
    	if (!TT_Config.useCustomCrystalModel) return;
    	BlocksTC.crystalAir = registerBlock(new NewBlockCrystal("crystal_aer", Aspect.AIR));
        BlocksTC.crystalFire = registerBlock(new NewBlockCrystal("crystal_ignis", Aspect.FIRE));
        BlocksTC.crystalWater = registerBlock(new NewBlockCrystal("crystal_aqua", Aspect.WATER));
        BlocksTC.crystalEarth = registerBlock(new NewBlockCrystal("crystal_terra", Aspect.EARTH));
        BlocksTC.crystalOrder = registerBlock(new NewBlockCrystal("crystal_ordo", Aspect.ORDER));
        BlocksTC.crystalEntropy = registerBlock(new NewBlockCrystal("crystal_perditio", Aspect.ENTROPY));
        BlocksTC.crystalTaint = registerBlock(new NewBlockCrystal("crystal_vitium", Aspect.FLUX));
        ShardType.AIR.setOre(BlocksTC.crystalAir);
        ShardType.FIRE.setOre(BlocksTC.crystalFire);
        ShardType.WATER.setOre(BlocksTC.crystalWater);
        ShardType.EARTH.setOre(BlocksTC.crystalEarth);
        ShardType.ORDER.setOre(BlocksTC.crystalOrder);
        ShardType.ENTROPY.setOre(BlocksTC.crystalEntropy);
        ShardType.FLUX.setOre(BlocksTC.crystalTaint);
    }
    
    private static Block registerBlock(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(item);
        Thaumcraft.proxy.registerModel(item);
        return block;
    }
}
