package com.psiracy.preposterousponds.entity.boss.prince;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Objects;

public class PrinceOfThePondEntity extends Monster
{
    public enum PrinceAttacks
    {
        NONE,
        SLAM,
        WING_FLAP,
        LASER,
        BIG_HONK,
        DASH
    }

    //anim
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState slamAnimationState = new AnimationState();
    public final AnimationState honkAnimationState = new AnimationState();
    public final AnimationState runAnimationState = new AnimationState();
    public final AnimationState idleToLaserAnimationState = new AnimationState();
    public final AnimationState laserAnimationState = new AnimationState();
    public final AnimationState wingFlapAnimationState = new AnimationState();

    //attacks
    private PrinceAttacks lastAttack = PrinceAttacks.NONE;
    private int attackTicks = 0;
    private boolean isLasering = false;

    private final ServerBossEvent bossEvent =
            new ServerBossEvent(this.uuid, Component.literal("The Prince of the Pond"), BossEvent.BossBarColor.BLUE, BossEvent.BossBarOverlay.NOTCHED_12);

    private static final EntityDataAccessor<Integer> ATTACK = SynchedEntityData.defineId(PrinceOfThePondEntity.class, EntityDataSerializers.INT);

    //boolean hasParts = false;
   // private final PrinceOfThePondPart[] parts;
  //  public final PrinceOfThePondPart head;
  //  public final PrinceOfThePondPart neck;

    public PrinceOfThePondEntity(EntityType<? extends Monster> type, Level level)
    {
        super(type, level);

        /*
        head = new PrinceOfThePondPart(this, "head", 1.5f,10.5f);
        neck = new PrinceOfThePondPart(this, "neck", 2f,10f);
        parts = new PrinceOfThePondPart[]{this.head, this.neck};
        */
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        super.defineSynchedData(builder);
        builder.define(ATTACK, PrinceAttacks.NONE.ordinal());
    }

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(3, new PrinceAttackGoal(this,8,32));
       // this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 6f));
      //  this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public boolean isPushedByFluid()
    {
        return false;
    }

    @Override
    public boolean isSensitiveToWater()
    {
        return false;
    }

    @Override
    public float getWaterSlowDown()
    {
        return 1.0F;
    }

    private void setupAnimationStates()
    {
        if (this.idleAnimationTimeout <= 0 && !this.walkAnimation.isMoving() && this.getCurrentAttack() == PrinceAttacks.NONE)
        {
            this.idleAnimationTimeout = 40;
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

        if(this.getCurrentAttack() == PrinceAttacks.NONE)
        {
            this.slamAnimationState.stop();
            this.honkAnimationState.stop();
            this.idleToLaserAnimationState.stop();
            this.laserAnimationState.stop();
            this.wingFlapAnimationState.stop();

            if (!this.idleAnimationState.isStarted())
            {
                this.idleAnimationTimeout = 40;
                this.idleAnimationState.start(this.tickCount);
            }
        }

        if (this.getCurrentAttack() != lastAttack)
        {
            this.startAttackAnim();
            lastAttack = this.getCurrentAttack();
        }
    }

    @Override
    public void tick()
    {
        super.tick();
        //parts
        //updateParts();

        //anim
        if (this.level().isClientSide())
        {
            this.setupAnimationStates();
        }
    }

    public static AttributeSupplier.Builder createPrinceOfThePondAttributes()
    {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 750.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.MOVEMENT_EFFICIENCY, 1.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5)
                .add(Attributes.ATTACK_DAMAGE, 45.0)
                .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    //boss stuff
    @Override
    public void startSeenByPlayer(ServerPlayer player)
    {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player)
    {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    public void aiStep()
    {
        super.aiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    //attack
    public PrinceAttacks getCurrentAttack()
    {
        return PrinceAttacks.values()[this.entityData.get(ATTACK)];
    }

    public void setCurrentAttack(PrinceAttacks attack)
    {
        this.entityData.set(ATTACK, attack.ordinal());
        attackTicks = 0;
    }

    public int getAttackTicks()
    {
        return attackTicks;
    }

    public void setLasering(boolean lasering)
    {
        this.isLasering = lasering;
    }

    public boolean getLasering()
    {
        return this.isLasering;
    }

    public void incrementAttackTicks()
    {
        attackTicks++;
    }

    public void startAttackAnim()
    {
        this.idleAnimationState.stop();

        if (this.getCurrentAttack() != PrinceAttacks.NONE)
        {
            if (Objects.requireNonNull(this.getCurrentAttack()) == PrinceAttacks.SLAM)
            {
                this.slamAnimationState.start(this.tickCount);
            }

            if (Objects.requireNonNull(this.getCurrentAttack()) == PrinceAttacks.DASH)
            {
                this.honkAnimationState.start(this.tickCount);
            }

            if (Objects.requireNonNull(this.getCurrentAttack()) == PrinceAttacks.WING_FLAP)
            {
                this.wingFlapAnimationState.start(this.tickCount);
            }

            if (Objects.requireNonNull(this.getCurrentAttack()) == PrinceAttacks.LASER)
            {
               if (this.getLasering())
               {
                   this.idleToLaserAnimationState.stop();
                   this.laserAnimationState.start(this.tickCount);
               }
               else
               {
                   this.idleToLaserAnimationState.start(this.tickCount);
               }
            }
        }
    }

    /*
    @Override
    public void remove(RemovalReason reason)
    {
        for (PrinceOfThePondPart part : parts)
        {
            if (part != null)
            {
                part.discard();
            }
        }

        super.remove(reason);
    }


    //part
    private void updateParts()
    {
        if (!hasParts && !this.level().isClientSide())
        {
            for (PrinceOfThePondPart part : parts)
            {
                ((ServerLevel)this.level()).addWithUUID(part);
            }
            hasParts = true;
        }

        if(head != null && head.isAlive())
        {
            Vec3 offset = this.getOffset(-3, 5, -3);
            this.tickPart(head, offset.x, offset.y, offset.z);
        }
        if(neck != null && neck.isAlive())
        {
            Vec3 offset = this.getOffset(-1f, 3, -1f);
            this.tickPart(neck, offset.x, offset.y, offset.z);
        }
    }

    private Vec3 getOffset(final double x, final double y, final double z)
    {
        float yaw = this.getYHeadRot() * ((float)Math.PI / 180F);

        double dx = Math.sin(yaw);
        double dz = -Math.cos(yaw);

        return new Vec3(dx * x, y, dz * z);
    }

    private void tickPart(final PrinceOfThePondPart part, final double x, final double y, final double z) {
        part.setPos(this.getX() + x, this.getY() + y, this.getZ() + z);
    }


    @Override
    public void recreateFromPacket(final ClientboundAddEntityPacket packet)
    {
        super.recreateFromPacket(packet);
        for (int i = 0; i < this.parts.length; i++)
        {
            this.parts[i].setId(i + packet.getId() + 1);
        }
    }
     */
}

