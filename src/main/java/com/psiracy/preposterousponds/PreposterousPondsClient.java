package com.psiracy.preposterousponds;

import com.psiracy.preposterousponds.entity.ModEntities;
import com.psiracy.preposterousponds.entity.client.ModModelLayerLocations;
import com.psiracy.preposterousponds.entity.boss.prince.client.PrinceOfThePondModel;
import com.psiracy.preposterousponds.entity.boss.prince.client.PrinceOfThePondRenderer;
import com.psiracy.preposterousponds.entity.client.goose.GooseModel;
import com.psiracy.preposterousponds.entity.client.goose.GooseRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class PreposterousPondsClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        //prince
        ModelLayerRegistry.registerModelLayer(ModModelLayerLocations.PRINCE, PrinceOfThePondModel::createBodyLayer);
        EntityRenderers.register(ModEntities.PRINCE, PrinceOfThePondRenderer::new);

        //goose
        ModelLayerRegistry.registerModelLayer(ModModelLayerLocations.GOOSE, GooseModel::createBodyLayer);
        EntityRenderers.register(ModEntities.GOOSE, GooseRenderer::new);
    }
}
