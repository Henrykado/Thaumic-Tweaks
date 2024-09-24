package henrykado.thaumictweaks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	/*@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {}

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {}

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {}*/
}
