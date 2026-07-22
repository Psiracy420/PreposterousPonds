package com.psiracy.preposterousponds.datagen;

import com.psiracy.preposterousponds.block.ModBlocks;
import com.psiracy.preposterousponds.block.custom.WaterLilyCropBlock;
import com.psiracy.preposterousponds.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.client.rendering.v1.FabricModel;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.world.level.block.CropBlock;

public class ModModelProvider extends FabricModelProvider
{
    public ModModelProvider(FabricPackOutput output)
    {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators)
    {
        blockModelGenerators.createCropBlock(ModBlocks.WATER_LILY_CROP, WaterLilyCropBlock.AGE, 0, 1, 2);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators)
    {
        itemModelGenerators.generateFlatItem(ModItems.LILY_PETAL, ModelTemplates.FLAT_ITEM);
    }
}
