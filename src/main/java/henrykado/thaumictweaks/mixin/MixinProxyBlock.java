package henrykado.thaumictweaks.mixin;

import henrykado.thaumictweaks.TT_Config;
import henrykado.thaumictweaks.ThaumicTweaks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.client.model.ModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.blocks.world.ore.ShardType;
import thaumcraft.proxies.ProxyBlock;

@Mixin(ProxyBlock.class)
public class MixinProxyBlock {
    @Redirect(method = "setupBlocksClient", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/model/ModelLoader;setCustomStateMapper(Lnet/minecraft/block/Block;Lnet/minecraft/client/renderer/block/statemap/IStateMapper;)V"), remap = false)
    private static void redirectCustomStateMapper(Block block, IStateMapper mapper)
    {
        if (TT_Config.useCustomCrystalModel) return;
        ModelLoader.setCustomStateMapper(block, mapper);
    }
}
