package com.psiracy.preposterousponds.block;

import com.psiracy.preposterousponds.PreposterousPonds;
import com.psiracy.preposterousponds.block.custom.WaterLilyCropBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class ModBlocks
{
    public static final Block WATER_LILY_CROP = registerBlockWithoutBlockItem("water_lily_crop",
            properties -> new WaterLilyCropBlock(properties.randomTicks().instabreak()
                    .sound(SoundType.CROP).noCollision().pushReaction(PushReaction.DESTROY)));

    private static Block registerBlockWithoutBlockItem(String name, Function<BlockBehaviour.Properties, Block> function)
    {
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, name), function.apply(BlockBehaviour.Properties.of()
                .setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, name)))));
    }

    public static void registerModBlocks()
    {
        PreposterousPonds.LOGGER.info("Registering Mod Blocks for " + PreposterousPonds.MOD_ID);
    }
}
