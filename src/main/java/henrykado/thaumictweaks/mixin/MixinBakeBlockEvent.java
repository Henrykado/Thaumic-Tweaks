package henrykado.thaumictweaks.mixin;

import henrykado.thaumictweaks.ThaumicTweaks;
import net.minecraftforge.client.event.ModelBakeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.proxies.ProxyBlock;

@Mixin(ProxyBlock.BakeBlockEventHandler.class)
public class MixinBakeBlockEvent {
    @Inject(method = "onModelBakeEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onModelBakeEventInject(ModelBakeEvent event, CallbackInfo ci)
    {
        ci.cancel();
    }
}
