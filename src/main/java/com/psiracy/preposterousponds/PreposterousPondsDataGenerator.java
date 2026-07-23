package com.psiracy.preposterousponds;

import com.psiracy.preposterousponds.datagen.*;
import com.psiracy.preposterousponds.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class PreposterousPondsDataGenerator implements DataGeneratorEntrypoint
{
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator)
	{
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		//pack.addProvider(ModRecipeProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModRegistryDataProvider::new);
	}

	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder)
	{
		registryBuilder.add(Registries.LEVEL_STEM, ModDimensions::bootstrapStem);
		registryBuilder.add(Registries.DIMENSION_TYPE, ModDimensions::bootstrapType);
	}
}
