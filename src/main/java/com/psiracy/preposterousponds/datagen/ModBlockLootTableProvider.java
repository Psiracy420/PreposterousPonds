package com.psiracy.preposterousponds.datagen;

import com.psiracy.preposterousponds.block.ModBlocks;
import com.psiracy.preposterousponds.block.custom.WaterLilyCropBlock;
import com.psiracy.preposterousponds.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider
{
    public ModBlockLootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture)
    {
        super(packOutput, registriesFuture);
    }

    @Override
    public void generate()
    {
        add(ModBlocks.WATER_LILY_CROP, createCropDrops(ModBlocks.WATER_LILY_CROP, ModItems.LILY_SEED, ModItems.LILY_PETAL,
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WATER_LILY_CROP)
                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WaterLilyCropBlock.AGE, WaterLilyCropBlock.MAX_AGE))));
    }
}
