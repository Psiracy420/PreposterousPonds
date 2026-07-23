package com.psiracy.preposterousponds.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.concurrent.CompletableFuture;

public class ModRegistryDataProvider extends FabricDynamicRegistryProvider
{
    public ModRegistryDataProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries)
    {
        entries.addAll(provider.lookupOrThrow(Registries.DIMENSION_TYPE));
        entries.addAll(provider.lookupOrThrow(Registries.LEVEL_STEM));
    }

    @Override
    public String getName()
    {
        return "Preposterous Dynamic Registry";
    }
}
