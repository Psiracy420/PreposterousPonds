package com.psiracy.preposterousponds.item;

import com.psiracy.preposterousponds.PreposterousPonds;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModCreativeModeTabs
{
    public static final CreativeModeTab PREPOSTEROUS_PONDS_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "preposterous_ponds"),
            FabricCreativeModeTab.builder().icon(() -> new ItemStack(Items.FEATHER))
                    .title(Component.translatable("creativetab.preposterousponds.preposterous_ponds_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.LILY_SEED);
                    }).build());

    public static void registerCreativeModeTabs()
    {
        PreposterousPonds.LOGGER.info("Registering Creative Mode Tabs for " + PreposterousPonds.MOD_ID);
    }
}
