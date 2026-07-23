package com.psiracy.preposterousponds;

import com.psiracy.preposterousponds.block.ModBlocks;
import com.psiracy.preposterousponds.entity.ModEntities;
import com.psiracy.preposterousponds.entity.boss.prince.PrinceOfThePondEntity;
import com.psiracy.preposterousponds.entity.custom.GooseEntity;
import com.psiracy.preposterousponds.item.ModCreativeModeTabs;
import com.psiracy.preposterousponds.item.ModItems;
import com.psiracy.preposterousponds.world.structure.PrinceArenaStructure;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PreposterousPonds implements ModInitializer
{
	public static final String MOD_ID = "preposterous-ponds";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{
		ModCreativeModeTabs.registerCreativeModeTabs();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

		ModEntities.registerModEntities();

		PrinceArenaStructure.register();

		FabricDefaultAttributeRegistry.register(ModEntities.PRINCE, PrinceOfThePondEntity.createPrinceOfThePondAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.GOOSE, GooseEntity.createAttributes());
	}

	public static Identifier id(String path)
	{
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
