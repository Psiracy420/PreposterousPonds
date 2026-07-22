package com.psiracy.preposterousponds.entity.boss.prince.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PrinceOfThePondRenderState extends LivingEntityRenderState
{
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState slamAnimationState = new AnimationState();
    public final AnimationState honkAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState idletolaserAnimationState = new AnimationState();
    public final AnimationState laserAnimationState = new AnimationState();
    public final AnimationState wingFlapAnimationState = new AnimationState();
}
