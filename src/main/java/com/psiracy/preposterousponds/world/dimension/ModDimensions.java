package com.psiracy.preposterousponds.world.dimension;

import com.psiracy.preposterousponds.PreposterousPonds;
import com.psiracy.preposterousponds.world.ModWorldGen;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.DimensionTypes;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.*;
import net.minecraft.world.clock.WorldClocks;
import net.minecraft.world.level.CardinalLighting;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

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
        var dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        var noiseGen = context.lookup(Registries.NOISE_SETTINGS);

        NoiseBasedChunkGenerator fixedBiome = new NoiseBasedChunkGenerator
                (
                        new FixedBiomeSource(biomes.getOrThrow(Biomes.BEACH)),
                        noiseGen.getOrThrow(ModWorldGen.POND_ISLANDS)
                );

        LevelStem stem = new LevelStem(dimensionTypes.getOrThrow(ModDimensions.PONDDIM_TYPE_KEY), fixedBiome);
        context.register(PONDDIM_KEY, stem);
    }
}
