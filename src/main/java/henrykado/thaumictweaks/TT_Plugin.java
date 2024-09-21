package henrykado.thaumictweaks;

import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import zone.rong.mixinbooter.ILateMixinLoader;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class TT_Plugin implements IFMLLoadingPlugin, ILateMixinLoader {
	@Override
	public String[] getASMTransformerClass() {
		return null; //new String[] {"henrykado.thaumictweaks.asm.TT_ClassTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

	@Override
	public List<String> getMixinConfigs() {
		return ImmutableList.of("mixins.thaumictweaks.json");
	}
}
