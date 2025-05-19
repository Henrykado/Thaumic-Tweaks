package henrykado.thaumictweaks;

import java.util.Map;

import fermiumbooter.FermiumRegistryAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class TT_Plugin implements IFMLLoadingPlugin {
	public TT_Plugin() {
		MixinBootstrap.init();
		//False for Vanilla/Coremod mixins, true for regular mod mixins
		FermiumRegistryAPI.enqueueMixin(true, "mixins.thaumictweaks.json");
	}

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
}
