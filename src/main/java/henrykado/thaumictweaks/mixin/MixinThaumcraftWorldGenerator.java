package henrykado.thaumictweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import henrykado.thaumictweaks.TT_Config;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.common.blocks.world.ore.ShardType;
import thaumcraft.common.world.ThaumcraftWorldGenerator;

import java.util.Random;

@Mixin(ThaumcraftWorldGenerator.class)
public class MixinThaumcraftWorldGenerator {
    @ModifyVariable(method = "generateOres", at = @At("STORE"), ordinal = 9, remap = false,
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Ljava/lang/Math;round(F)I"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldProvider;getDimension()V")
            ))
    int modifyMaxCrystals(int maxCrystals)
    {
        return TT_Config.crystalOreDensity > -1 ? Math.round(64.0F * ((float)TT_Config.crystalOreDensity / 100)) : maxCrystals;
    }

    @ModifyVariable(method = "generateOres", at = @At("STORE"), ordinal = 14, remap = false)
    int modifyRandPosY(int randPosY)
    {
        return TT_Config.crystalMaxY > -1 ? Math.min(TT_Config.crystalMaxY, randPosY) : randPosY;
    }

    @Inject(method = "generateOres", at = @At(value = "INVOKE", target = "Lthaumcraft/common/blocks/world/ore/ShardType;byMetadata(I)Lthaumcraft/common/blocks/world/ore/ShardType;", shift = At.Shift.BEFORE), cancellable = true, remap = false)
    public void dimensionBlacklistInject(World world, Random random, int chunkX, int chunkZ, boolean newGen, CallbackInfo ci, @Local(ordinal = 16) int md)
    {
        if ((TT_Config.GENERATION.enableDimensionBlacklist && MixinThaumcraftWorldGenerator.crystalBlacklist(world))
                || (TT_Config.GENERATION.enableSpecificCrystalBlacklist && MixinThaumcraftWorldGenerator.specificCrystalBlacklist(world, md))) {
            ci.cancel();
        }
    }


    private static boolean crystalBlacklist(World world)
    {
        if (isInvalidDimension(TT_Config.GENERATION.crystalsDimensionBlacklist, world))
        {
            return false;
        }
        return true;
    }

    private static boolean specificCrystalBlacklist(World world, int md)
    {
        switch (ShardType.byMetadata(md))
        {
            case AIR:
                if (isInvalidDimension(TT_Config.GENERATION.airCrystalDimensionBlacklist, world))
                    return false;
                break;
            case EARTH:
                if (isInvalidDimension(TT_Config.GENERATION.earthCrystalDimensionBlacklist, world))
                    return false;
                break;
            case ENTROPY:
                if (isInvalidDimension(TT_Config.GENERATION.entropyCrystalDimensionBlacklist, world))
                    return false;
                break;
            case FIRE:
                if (isInvalidDimension(TT_Config.GENERATION.fireCrystalDimensionBlacklist, world))
                    return false;
                break;
            case ORDER:
                if (isInvalidDimension(TT_Config.GENERATION.orderCrystalDimensionBlacklist, world))
                    return false;
                break;
            default: // WATER
                if (isInvalidDimension(TT_Config.GENERATION.waterCrystalDimensionBlacklist, world))
                    return false;
                break;
        }
        return true;
    }


    private static boolean isInvalidDimension(String[] blacklist, World world)
    {
        if (blacklist.length > 0)
        {
            boolean isWhitelist = blacklist[0] == "*";
            for (int i = (isWhitelist ? 1 : 0); i < blacklist.length; i++)
            {
                if (!isWhitelist)
                {
                    if (world.provider.getDimension() == Integer.valueOf(blacklist[i]))
                        return true;
                }
                else
                {
                    if (world.provider.getDimension() != Integer.valueOf(blacklist[i]))
                        return true;
                }
            }
        }
        return false;
    }
}
