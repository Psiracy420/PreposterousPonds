package com.psiracy.preposterousponds.entity.custom;

import com.psiracy.preposterousponds.entity.boss.prince.PrinceOfThePondEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class GooseEntity extends Animal
{
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public GooseEntity(EntityType<? extends Animal> type, Level level)
    {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick()
    {
        super.tick();

        //anim
        if (this.level().isClientSide())
        {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates()
    {
        if (this.idleAnimationTimeout <= 0 && !this.walkAnimation.isMoving())
        {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.tickCount);
        }
        else
        {
            if (this.walkAnimation.isMoving())
            {
                this.idleAnimationState.stop();
            }

            --this.idleAnimationTimeout;
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack)
    {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner)
    {
        return null;
    }
}
