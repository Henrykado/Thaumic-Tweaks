package henrykado.thaumictweaks;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.world.ore.BlockCrystal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import henrykado.thaumictweaks.proxy.CommonProxy;

@Mod(
	modid = ThaumicTweaks.MODID,
	name = ThaumicTweaks.NAME,
	version = ThaumicTweaks.VERSION,
	dependencies = "required-after:thaumcraft"
)
public class ThaumicTweaks {
	public static final String MODID = "thaumictweaks";
	public static final String NAME = "Thaumic Tweaks";
	public static final String VERSION = "1.0";
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	
	@SidedProxy(clientSide = "henrykado.thaumictweaks.proxy.ClientProxy", serverSide = "henrykado.thaumictweaks.proxy.ServerProxy")
    public static CommonProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
        
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
