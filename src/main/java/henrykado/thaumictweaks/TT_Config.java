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
	
	/*@RequiresMcRestart
	@Name("Crystal ore density override")
	@Comment("Sets a separate ore density value exclusively for crystals, \n"
			+ "if set to -1 it will use the general ore density value, the one set in Thaumcraft's config. \n")
	public static int crystalOreDensity = -1;*/

	@Comment("The minimum y value that a crystal can generate in")
	@Name("Minimum generation height")
	public static int crystalMinY = 5;

	@Comment("The maximum y value that a crystal can generate in")
	@Name("Maximum generation height")
	public static int crystalMaxY = -1;

	@Name("Vis Crystal dimension blacklists")
	public static final DimensionBlacklists DIMENSION = new DimensionBlacklists();

	@Name("Vis Crystal biome blacklists")
	public static final BiomeBlacklists BIOME = new BiomeBlacklists();

	public static class DimensionBlacklists
	{
		@Name("Enable dimension blacklist")
		public boolean _enableBlacklist = false;

		@Comment("List of dimensions where crystals won't generate (if blacklist), \n"
				+ "or the only dimensions it will generate (if whitelist, to make it a whitelist simply make the first entry an asterisk [*]) \n"
				+ "P.S. Use dimension IDs: The Nether -> -1, The End -> 1, The Aether -> 4, etc")
		@Name("Dimension blacklist")
		public String[] blacklist = {
				"1"
		};

		@Name("Enable dimension blacklist (specific crystals)")
		public boolean _enableSpecificCrystalBlacklist = false;

		@Name("Air Crystal dimension blacklist")
		public String[] airCrystalBlacklist = {};

		@Name("Fire Crystal dimension blacklist")
		public String[] fireCrystalBlacklist = {};

		@Name("Earth Crystal dimension blacklist")
		public String[] earthCrystalBlacklist = {};

		@Name("Water Crystal dimension blacklist")
		public String[] waterCrystalBlacklist = {};

		@Name("Order (White) dimension blacklist")
		public String[] orderCrystalBlacklist = {};

		@Name("Entropy (Black) dimension blacklist")
		public String[] entropyCrystalBlacklist = {};
	}

	public static class BiomeBlacklists
	{
		@Name("Enable biome blacklist")
		public boolean _enableBlacklist = false;

		@Comment("List of biomes where crystals won't generate (if blacklist), \n"
				+ "or the only dimensions it will generate (if whitelist, to make it a whitelist simply make the first entry an asterisk [*]) \n"
				+ "P.S. Use biome IDs: Plains -> 1, Desert -> 4, The Aether -> 4, etc")
		@Name("Biome blacklist")
		public String[] crystalsBlacklist = {
				"1"
		};

		@Name("Enable biome blacklist (specific crystals)")
		public boolean _enableSpecificCrystalBlacklist = false;

		@Name("Air Crystal biome blacklist")
		public String[] airCrystalBlacklist = {};

		@Name("Fire Crystal biome blacklist")
		public String[] fireCrystalBlacklist = {};

		@Name("Earth Crystal biome blacklist")
		public String[] earthCrystalBlacklist = {};

		@Name("Water Crystal biome blacklist")
		public String[] waterCrystalBlacklist = {};

		@Name("Order (White) biome blacklist")
		public String[] orderCrystalBlacklist = {};

		@Name("Entropy (Black) biome blacklist")
		public String[] entropyCrystalBlacklist = {};
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
