package com.psiracy.preposterousponds.world.dimension;

import com.psiracy.preposterousponds.PreposterousPonds;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.*;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;

import java.util.List;
import java.util.Optional;

public class ModDimensions
{
    public static final ResourceKey<LevelStem> PONDDIM_KEY = ResourceKey.create(Registries.LEVEL_STEM,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "ponddim"));
    public static final ResourceKey<Level> PONDDIM_LEVEL_KEY = ResourceKey.create(Registries.DIMENSION,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "ponddim"));
    public static final ResourceKey<DimensionType> PONDDIM_TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "ponddim_type"));

    public static void bootstrapType(BootstrapContext<DimensionType> context)
    {
        var timelines = context.lookup(Registries.TIMELINE);
        var clocks = context.lookup(Registries.WORLD_CLOCK);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        EnvironmentAttributeMap pondAttributes = EnvironmentAttributeMap.builder()
                .set(EnvironmentAttributes.FOG_COLOR, -7342096)
                .set(EnvironmentAttributes.SKY_COLOR, -14687274)
                .set(EnvironmentAttributes.AMBIENT_LIGHT_COLOR, -16119286)
                .set(EnvironmentAttributes.CLOUD_COLOR, ARGB.white(0.8F))
                .set(EnvironmentAttributes.CLOUD_HEIGHT, 192.33F)
                .set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.OVERWORLD)
                .set(EnvironmentAttributes.BED_RULE, BedRule.EXPLODES)
                .set(EnvironmentAttributes.RESPAWN_ANCHOR_WORKS, false)
                .set(EnvironmentAttributes.NETHER_PORTAL_SPAWNS_PIGLINS, true)
                .set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS)
                .build();


        context.register(PONDDIM_TYPE_KEY, new DimensionType
                (
                        true,
                        true,
                        false,
                        false,
                        2.0,
                        0,
                        256,
                        256,
                        blocks.getOrThrow(BlockTags.INFINIBURN_OVERWORLD),
                        0.0F,
                        new DimensionType.MonsterSettings(UniformInt.of(0, 7), 0),
                        DimensionType.Skybox.OVERWORLD,
                        CardinalLighting.Type.DEFAULT,
                        pondAttributes,
                        timelines.getOrThrow(TimelineTags.IN_OVERWORLD),
                        Optional.of(clocks.getOrThrow(WorldClocks.OVERWORLD))
                ));
    }

    public static void bootstrapStem(BootstrapContext<LevelStem> context)
    {
        var biomes = context.lookup(Registries.BIOME);
        var blocks = context.lookup(Registries.BLOCK);
        var features = context.lookup(Registries.PLACED_FEATURE);
        var structure = context.lookup(Registries.STRUCTURE_SET);
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);

        FlatLevelGeneratorSettings oceanSettings =
                new FlatLevelGeneratorSettings(Optional.empty(), biomes.getOrThrow(Biomes.DEEP_OCEAN), List.of());

        oceanSettings.getLayersInfo().add(
                new FlatLayerInfo(1, Blocks.BEDROCK)
        );

        oceanSettings.getLayersInfo().add(
                new FlatLayerInfo(44, Blocks.STONE)
        );

        oceanSettings.getLayersInfo().add(
                new FlatLayerInfo(3, Blocks.GRAVEL)
        );

        oceanSettings.getLayersInfo().add(
                new FlatLayerInfo(16, Blocks.WATER)
        );

        oceanSettings.updateLayers();
        FlatLevelSource oceanGenerator = new FlatLevelSource(oceanSettings);

        LevelStem stem = new LevelStem(dimensionTypes.getOrThrow(ModDimensions.PONDDIM_TYPE_KEY), oceanGenerator);
        context.register(PONDDIM_KEY, stem);
    }
}
