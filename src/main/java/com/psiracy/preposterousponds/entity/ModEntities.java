package com.psiracy.preposterousponds.entity;

import com.psiracy.preposterousponds.PreposterousPonds;
import com.psiracy.preposterousponds.entity.boss.prince.PrinceOfThePondEntity;
import com.psiracy.preposterousponds.entity.custom.GooseEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities
{
    //keys
    public static final ResourceKey<EntityType<?>> PRINCE_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "prince_of_the_pond"));

    public static final ResourceKey<EntityType<?>> GOOSE_KEY = ResourceKey.create(Registries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "goose"));

    //register
    public static final EntityType<PrinceOfThePondEntity> PRINCE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "prince_of_the_pond"),
            EntityType.Builder.of(PrinceOfThePondEntity::new, MobCategory.MONSTER)
                    .sized(6,6)
                    .clientTrackingRange(10)
                    .eyeHeight(5.5f)
                    .build(PRINCE_KEY));

    public static final EntityType<GooseEntity> GOOSE = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "goose"),
            EntityType.Builder.of(GooseEntity::new, MobCategory.CREATURE)
                    .sized(.5f,1.25f)
                    .eyeHeight(1.25f)
                    .build(GOOSE_KEY));

    public static void registerModEntities()
    {
        PreposterousPonds.LOGGER.info("Registering Mod Entities");
    }
}
