package henrykado.thaumictweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import henrykado.thaumictweaks.TT_Config;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.common.blocks.world.ore.ShardType;
import thaumcraft.common.world.ThaumcraftWorldGenerator;

import java.util.Random;

@Mixin(ThaumcraftWorldGenerator.class)
public class MixinThaumcraftWorldGenerator {
    @Inject(method = "generateOres", at = @At(value = "INVOKE", target = "Lthaumcraft/common/blocks/world/ore/ShardType;byMetadata(I)Lthaumcraft/common/blocks/world/ore/ShardType;", shift = At.Shift.BEFORE), cancellable = true, remap = false)
    public void dimensionBlacklistInject(World world, Random random, int chunkX, int chunkZ, boolean newGen, CallbackInfo ci, @Local Biome bgb, @Local(ordinal = 9) int md, @Local(ordinal = 8) int randPosY)
    {
        if (
                ((TT_Config.DIMENSION.enableBlacklist && MixinThaumcraftWorldGenerator.isWhitelisted(TT_Config.DIMENSION.blacklist, world.provider.getDimension()))
                || (TT_Config.DIMENSION.enableSpecificCrystalBlacklist && MixinThaumcraftWorldGenerator.specificCrystalDimensionBlacklist(world.provider.getDimension(), md)))

                || ((TT_Config.BIOME.enableBlacklist && MixinThaumcraftWorldGenerator.isWhitelisted(TT_Config.BIOME.crystalsBlacklist, Biome.getIdForBiome(bgb)))
                || (TT_Config.BIOME.enableSpecificCrystalBlacklist && MixinThaumcraftWorldGenerator.specificCrystalDimensionBlacklist(Biome.getIdForBiome(bgb), md)))

                || ((randPosY > TT_Config.crystalMaxY && TT_Config.crystalMaxY > -1)
                || randPosY < TT_Config.crystalMinY)
        )
        {
            ci.cancel();
        }
    }


    private static boolean isWhitelisted(String[] blacklist, int identifier)
    {
        if (blacklist.length > 0)
        {
            boolean isWhitelist = blacklist[0].equals("*");
            for (int i = (isWhitelist ? 1 : 0); i < blacklist.length; i++)
            {
                if (identifier == Integer.parseInt(blacklist[i])) {
                    return !isWhitelist;
                }
            }

            return isWhitelist;
        }
        return false;
    }

    private static boolean specificCrystalDimensionBlacklist(int dimensionID, int md)
    {
        switch (ShardType.byMetadata(md))
        {
            case AIR:
                if (isWhitelisted(TT_Config.DIMENSION.airCrystalBlacklist, dimensionID))
                    return false;
                break;
            case EARTH:
                if (isWhitelisted(TT_Config.DIMENSION.earthCrystalBlacklist, dimensionID))
                    return false;
                break;
            case ENTROPY:
                if (isWhitelisted(TT_Config.DIMENSION.entropyCrystalBlacklist, dimensionID))
                    return false;
                break;
            case FIRE:
                if (isWhitelisted(TT_Config.DIMENSION.fireCrystalBlacklist, dimensionID))
                    return false;
                break;
            case ORDER:
                if (isWhitelisted(TT_Config.DIMENSION.orderCrystalBlacklist, dimensionID))
                    return false;
                break;
            default: // WATER
                if (isWhitelisted(TT_Config.DIMENSION.waterCrystalBlacklist, dimensionID))
                    return false;
                break;
        }
        return true;
    }

    private static boolean specificCrystalBiomeBlacklist(int biomeID, int md)
    {
        switch (ShardType.byMetadata(md))
        {
            case AIR:
                if (isWhitelisted(TT_Config.BIOME.airCrystalBlacklist, biomeID))
                    return false;
                break;
            case EARTH:
                if (isWhitelisted(TT_Config.BIOME.earthCrystalBlacklist, biomeID))
                    return false;
                break;
            case ENTROPY:
                if (isWhitelisted(TT_Config.BIOME.entropyCrystalBlacklist, biomeID))
                    return false;
                break;
            case FIRE:
                if (isWhitelisted(TT_Config.BIOME.fireCrystalBlacklist, biomeID))
                    return false;
                break;
            case ORDER:
                if (isWhitelisted(TT_Config.BIOME.orderCrystalBlacklist, biomeID))
                    return false;
                break;
            default: // WATER
                if (isWhitelisted(TT_Config.BIOME.waterCrystalBlacklist, biomeID))
                    return false;
                break;
        }
        return true;
    }
}
