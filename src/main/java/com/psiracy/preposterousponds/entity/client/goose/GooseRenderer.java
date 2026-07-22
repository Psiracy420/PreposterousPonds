package com.psiracy.preposterousponds.entity.client.goose;

import com.psiracy.preposterousponds.PreposterousPonds;

import com.psiracy.preposterousponds.entity.client.ModModelLayerLocations;
import com.psiracy.preposterousponds.entity.custom.GooseEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class GooseRenderer extends MobRenderer<GooseEntity, GooseRenderState, GooseModel>
{
    public GooseRenderer(EntityRendererProvider.Context context)
    {
        super(context, new GooseModel(context.bakeLayer(ModModelLayerLocations.GOOSE)), .5f);
    }

    @Override
    public Identifier getTextureLocation(GooseRenderState state)
    {
        return Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "textures/entity/goose.png");
    }

    @Override
    public GooseRenderState createRenderState()
    {
        return new GooseRenderState();
    }

    @Override
    public void extractRenderState(GooseEntity entity, GooseRenderState state, float partialTicks)
    {
        super.extractRenderState(entity, state, partialTicks);
        state.idleAnimationState.copyFrom(entity.idleAnimationState);
    }
}
