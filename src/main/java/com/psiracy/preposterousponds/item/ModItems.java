package com.psiracy.preposterousponds.item;

import com.psiracy.preposterousponds.PreposterousPonds;
import com.psiracy.preposterousponds.block.custom.WaterLilyCropBlock;
import com.psiracy.preposterousponds.item.custom.WaterLilyCropBlockItem;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import com.psiracy.preposterousponds.block.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Consumer;
import java.util.function.Function;

public class ModItems
{
    public static final Item LILY_SEED = registerItem("lily_seed",
            properties -> new WaterLilyCropBlockItem(ModBlocks.WATER_LILY_CROP, properties.useItemDescriptionPrefix()));

    public static final Item LILY_PETAL = registerItem("lily_petal", Item::new);

    private static Item registerItem(String name, Function<Item.Properties, Item> function)
    {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, name)))));
    }

    public static void registerModItems()
    {
        PreposterousPonds.LOGGER.info("Registering Mod Items for " + PreposterousPonds.MOD_ID);
    }
}
