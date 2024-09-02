package henrykado.thaumictweaks;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = ThaumicTweaks.MODID)
@Config(modid = ThaumicTweaks.MODID, category = "general")
public class TT_Config {
	@Name("Use a different Vis Crystal model")
	@Comment("Enables a vanilla styled vis crystal model based on the item's texture \n"
			+ "Also, enabling this allows resource packs to modify the models/textures of the crystals \n"
			+ "Also (x2), in case you're using Ore Stages, enabling this (should) fix crystals being un-hideable \n"
			+ "Needs to be enabled in both server and client for it to work properly")
	public static boolean useCustomCrystalModel = false;
	
	@RequiresMcRestart
	@Name("Crystal ore density override")
	@Comment("A separate ore density value for crystals, \n"
			+ "if set to -1 it will use the general ore density value, the one set in Thaumcraft's config. \n")
	public static int crystalOreDensity = -1;

	
	@Name("Vis Crystals Dimension Blacklists")
	public static final Generation GENERATION = new Generation();
	
	public static class Generation
	{
		@Name("Enable dimension generation blacklist")
		public boolean enableDimensionBlacklist = false;
		
		@Comment("List of dimensions where crystals won't generate (if blacklist), \n"
				+ "or the only dimensions it will generate (if whitelist, to make it a whitelist simply make the first entry an asterisk [*])")
		@Name("Dimension generation blacklist")
		public String[] crystalsDimensionBlacklist = {
				"4"
		};
		
		@Name("Enable (specific types) dimension generation blacklist")
		public boolean enableSpecificCrystalBlacklist = false;
		
		@Name("Air Crystal dimension generation blacklist")
		public String[] airCrystalDimensionBlacklist = {};
		
		@Name("Fire Crystal dimension generation blacklist")
		public String[] fireCrystalDimensionBlacklist = {};
		
		@Name("Earth Crystal dimension generation blacklist")
		public String[] earthCrystalDimensionBlacklist = {};
		
		@Name("Water Crystal dimension generation blacklist")
		public String[] waterCrystalDimensionBlacklist = {};
		
		@Name("Order (White) dimension generation blacklist")
		public String[] orderCrystalDimensionBlacklist = {};
		
		@Name("Entropy (Black) dimension generation blacklist")
		public String[] entropyCrystalDimensionBlacklist = {};
	}
	
	@SubscribeEvent
	public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if (event.getModID().equals(ThaumicTweaks.MODID))
		{
			ConfigManager.sync(ThaumicTweaks.MODID, Config.Type.INSTANCE);
		}
	}
}
