package com.psiracy.preposterousponds.entity.boss.prince.client;

import com.psiracy.preposterousponds.PreposterousPonds;
import com.psiracy.preposterousponds.entity.client.ModModelLayerLocations;
import com.psiracy.preposterousponds.entity.boss.prince.PrinceOfThePondEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class PrinceOfThePondRenderer extends MobRenderer<PrinceOfThePondEntity, PrinceOfThePondRenderState,PrinceOfThePondModel>
{
    public PrinceOfThePondRenderer(EntityRendererProvider.Context context)
    {
        super(context, new PrinceOfThePondModel(context.bakeLayer(ModModelLayerLocations.PRINCE)), 2f);
    }

    @Override
    public Identifier getTextureLocation(PrinceOfThePondRenderState state)
    {
        return Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "textures/entity/prince/prince.png");
    }

    @Override
    public PrinceOfThePondRenderState createRenderState()
    {
        return new PrinceOfThePondRenderState();
    }

    @Override
    public void extractRenderState(PrinceOfThePondEntity entity, PrinceOfThePondRenderState state, float partialTicks)
    {
        super.extractRenderState(entity, state, partialTicks);

        state.idleAnimationState.copyFrom(entity.idleAnimationState);
        state.slamAnimationState.copyFrom(entity.slamAnimationState);
        state.honkAnimationState.copyFrom(entity.honkAnimationState);
        state.runAnimationState.copyFrom(entity.runAnimationState);
        state.idletolaserAnimationState.copyFrom(entity.idleToLaserAnimationState);
        state.laserAnimationState.copyFrom(entity.laserAnimationState);
        state.wingFlapAnimationState.copyFrom(entity.wingFlapAnimationState);
    }
}
