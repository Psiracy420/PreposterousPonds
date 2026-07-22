package com.psiracy.preposterousponds.world;

import com.psiracy.preposterousponds.PreposterousPonds;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModWorldGen
{
    public static final ResourceKey<NoiseGeneratorSettings> POND_ISLANDS =
            ResourceKey.create(Registries.NOISE_SETTINGS, Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "pond_islands"));
}
