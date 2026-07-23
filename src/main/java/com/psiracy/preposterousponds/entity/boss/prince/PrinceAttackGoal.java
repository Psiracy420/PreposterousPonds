package com.psiracy.preposterousponds.entity.boss.prince;

import com.psiracy.preposterousponds.PreposterousPonds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class PrinceAttackGoal extends Goal
{
    private final PrinceOfThePondEntity prince;
    private LivingEntity target;
    private int attackCooldown = 0;
    private final RandomSource random;
    private int melleeAttackRange;
    private int rangedAttackRange;
    private int repathDelay;

    private Vec3 dashDirection = Vec3.ZERO;
    private Vec3 dashTarget = Vec3.ZERO;
    private boolean dashing = false;
    private boolean shouldmove = true;
    private boolean half = false;
    private boolean halfend = false;
    private boolean panic = false;

    private Vec3 laserDirection = Vec3.ZERO;
    private float laserCooldown = 0;

    private static final int SLAM_WINDUP = 3;
    private static final int SLAM_END = 10;

    private static final int DASH_WINDUP = 20;
    private static final int DASH_END = 32;
    private static final float DASH_KNOCKBACK_POWER = 3.5f;

    private static final int LASER_WINDUP = 7;
    private static final int LASER_DURATION = 80;
    private static final float LASER_TURN_SPEED = 0.2F;
    private static final float LASER_LENGTH = 40f;
    private static final float LASER_RADIUS = 0.75f;
    private static final float LASER_DOT_DMG = 16.25f;

    private static final int WING_WINDUP = 5;
    private static final int WING_END = 18;
    private static final int WING_ARROW_AMOUNT = 12;
    private static final float WING_CONE = 35f;
    private static final float WING_OFFSET = 3.5f;
    private static final float WING_ARROW_VELOCITY = 1.6f;


    public PrinceAttackGoal(PrinceOfThePondEntity prince, int meleeRange, int rangedRange)
    {
        this.prince = prince;
        this.random = prince.getRandom();
        this.melleeAttackRange = meleeRange;
        this.rangedAttackRange = rangedRange;

        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse()
    {
        target = prince.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse()
    {
        target = prince.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public void stop()
    {
        finishAttack();
        super.stop();
    }

    @Override
    public void tick()
    {
        target = prince.getTarget();

        if (target == null)
            return;

        if (prince.getCurrentAttack() != PrinceOfThePondEntity.PrinceAttacks.NONE)
        {
            prince.getNavigation().stop();
            runCurrentAttack();
            return;
        }

        if(!this.dashing)
        {
            this.prince.getLookControl().setLookAt(target);
        }

        PreposterousPonds.LOGGER.info("dis:"+this.prince.distanceTo(target) );
        if (this.prince.distanceTo(target) > 4.1f && this.shouldmove && !this.dashing)
        {
            this.prince.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.2);
        }

        //break blocks
        AABB box = prince.getBoundingBox().inflate(.1f);

        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX, box.maxY, box.maxZ);

        for (BlockPos pos : BlockPos.betweenClosed(min, max))
        {
            if (pos.getY() < prince.getBlockY())
                continue;

            BlockState state = prince.level().getBlockState(pos);

            if (!state.isAir() && !state.is(Blocks.REINFORCED_DEEPSLATE))
            {
                prince.level().destroyBlock(pos, true);
            }
        }

        if (attackCooldown > 0)
            attackCooldown--;


        if (attackCooldown <= 0)
        {
            if (prince.getHealth() <= prince.getMaxHealth() / 10)
            {
                this.panic = true;
            }
            else if (prince.getHealth() <= prince.getMaxHealth() / 2)
            {
                this.half = true;
            }

            chooseAttack();
        }
    }

    //attack
    private void chooseAttack()
    {
        double distance = prince.distanceTo(target);
        PrinceOfThePondEntity.PrinceAttacks attack;

        if (distance < this.melleeAttackRange)
        {
            int r = random.nextInt(100);

            if (r < 80)
                attack = PrinceOfThePondEntity.PrinceAttacks.SLAM;
            else if (r < 90)
                attack = PrinceOfThePondEntity.PrinceAttacks.WING_FLAP;
            else
                attack = PrinceOfThePondEntity.PrinceAttacks.LASER;
        }
        else if (distance < this.rangedAttackRange)
        {
            int r = random.nextInt(100);

            if (r < 50)
                attack = PrinceOfThePondEntity.PrinceAttacks.DASH;
            else if (r < 80)
                attack = PrinceOfThePondEntity.PrinceAttacks.LASER;
            else
                attack = PrinceOfThePondEntity.PrinceAttacks.WING_FLAP;
        }
        else
        {
            attack = PrinceOfThePondEntity.PrinceAttacks.NONE;
        }

        if (this.half && !this.halfend)
        {
            attack = PrinceOfThePondEntity.PrinceAttacks.LASER;
            this.halfend = true;
        }

        this.shouldmove = false;
        prince.setCurrentAttack(attack);
    }

    private void runCurrentAttack()
    {
        prince.incrementAttackTicks();

        switch (prince.getCurrentAttack())
        {
            case SLAM -> tickSlam();
            case DASH -> tickDash();
            case WING_FLAP -> tickWingFlap();
            case LASER -> tickLaser();
            case BIG_HONK ->
            {
                PreposterousPonds.LOGGER.info("big honk");
                finishAttack();
            }
            default -> {}
        }
    }

    private void finishAttack()
    {
        shouldmove = true;
        prince.setCurrentAttack(PrinceOfThePondEntity.PrinceAttacks.NONE);
        //SET ATTACK COOLDOWN HERE
        if (!panic)
        {
            attackCooldown = 20 + random.nextInt(40);
        }
        else
        {
            attackCooldown = 5 + random.nextInt(20);
        }
    }

    //slam
    private void tickSlam()
    {
        if (prince.getAttackTicks() == SLAM_WINDUP)
        {
            if (target != null && prince.distanceTo(target) < this.melleeAttackRange)
            {
                if (!prince.level().isClientSide())
                {
                    prince.doHurtTarget((ServerLevel) prince.level(), this.target);
                }
            }
        }

        if (prince.getAttackTicks() >= SLAM_END)
        {
            finishAttack();
        }
    }

    //dash
    private void tickDash()
    {
        if (prince.getAttackTicks() < DASH_WINDUP)
        {
            prince.getNavigation().stop();
            prince.getLookControl().setLookAt(target, 30.0F, 30.0F);
            return;
        }

        if (prince.getAttackTicks() == DASH_WINDUP)
        {
            Vec3 direction = target.position()
                    .subtract(prince.position())
                    .normalize();

            dashTarget = target.position();

            startDash(direction);
        }

        if (prince.getAttackTicks() >= DASH_WINDUP && prince.getAttackTicks() <= DASH_END)
        {
            //make the prince look at the dash direction
            prince.getLookControl().setLookAt(this.dashTarget);
            float yaw = (float)(Math.atan2(dashDirection.z, dashDirection.x) * (180F / Math.PI)) - 90F;

            prince.setYRot(yaw);
            prince.setYBodyRot(yaw);
            prince.setYHeadRot(yaw);

            prince.setDeltaMovement(this.dashDirection.scale(1.8));

            for (LivingEntity entity : prince.level().getEntitiesOfClass(LivingEntity.class, prince.getBoundingBox().inflate(0.5), e -> e != prince))
            {
                if (entity instanceof Player player && player.isBlocking())
                {
                    ItemStack shield = player.getUseItem();
                    if (shield.is(Items.SHIELD))
                    {
                        shield.hurtAndBreak(25, player, player.getUsedItemHand());
                        player.stopUsingItem();
                    }
                }
                prince.doHurtTarget((ServerLevel) prince.level(), entity);
                Vec3 knockback = entity.position().subtract(prince.position()).normalize().scale(2.0);
                entity.setDeltaMovement(knockback.x * this.DASH_KNOCKBACK_POWER, 1.2, knockback.z * this.DASH_KNOCKBACK_POWER);
            }
        }

        if (prince.getAttackTicks() >= DASH_END + 1)
        {
            this.stopDash();
            prince.setDeltaMovement(Vec3.ZERO);
        }
    }

    public void startDash(Vec3 direction)
    {
        dashDirection = direction.normalize();
        dashing = true;
    }

    public void stopDash()
    {
        dashing = false;
        dashDirection = Vec3.ZERO;
        finishAttack();
    }

    //laser
    public void tickLaser()
    {
        float laserTime  = this.half ? LASER_DURATION * 1.5f : LASER_DURATION;

        if (prince.getAttackTicks() < LASER_WINDUP)
        {
            prince.setLasering(false);
            prince.getLookControl().setLookAt(target, 30, 30);
        }

        if (prince.getAttackTicks() == LASER_WINDUP)
        {
            laserDirection = target.position()
                    .add(0, target.getBbHeight() * 0.5, 0)
                    .subtract(getMouthPosition())
                    .normalize();
        }

        if (prince.getAttackTicks() > LASER_WINDUP)
        {
            prince.setLasering(true);
            prince.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 5, 2,true,false));

            //rotate
            Vec3 desired = target.position()
                    .add(0, prince.getEyeHeight() - 4.5, 0)
                    .subtract(getMouthPosition())
                    .normalize();

            laserDirection = laserDirection.lerp(desired, LASER_TURN_SPEED).normalize();

            float yaw = (float)Math.toDegrees(Math.atan2(laserDirection.z, laserDirection.x)) - 90;

            prince.setYRot(yaw);
            prince.setYBodyRot(yaw);
            prince.setYHeadRot(yaw);

            this.laserPewPew();
        }

        if (prince.getAttackTicks() >= LASER_WINDUP + laserTime)
        {
            prince.removeEffect(MobEffects.RESISTANCE);
            prince.setLasering(false);
            this.finishAttack();
        }
    }

    private Vec3 getMouthPosition()
    {
        return prince.position()
                .add(0, prince.getEyeHeight() - 3, 0)
                .add(prince.getForward().scale(4));
    }

    private void laserPewPew()
    {
        //raycast
        Vec3 start = getMouthPosition();
        Vec3 end = start.add(laserDirection.scale(LASER_LENGTH));

        BlockHitResult hit = prince.level().clip(
                new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, prince));

        end = hit.getLocation();

        //damage
        if (this.laserCooldown == 0)
        {
            for (double d = 0; d < start.distanceTo(end); d += 0.5)
            {
                Vec3 point = start.add(laserDirection.scale(d));

                //do dmg
                AABB box = new AABB(point, point).inflate(LASER_RADIUS);

                for (LivingEntity entity : prince.level().getEntitiesOfClass(LivingEntity.class, box, e -> e != prince))
                {
                    //no prince.doHurtTarget we need to set custom damage
                    entity.hurtServer((ServerLevel) prince.level(), this.prince.getWeaponItem().getDamageSource(this.prince), LASER_DOT_DMG);

                    //vaporize the shield if the player is blocking
                    if (entity instanceof Player player && player.isBlocking())
                    {
                        ItemStack shield = player.getUseItem();
                        if (shield.is(Items.SHIELD))
                        {
                            shield.hurtAndBreak(5, player, player.getUsedItemHand());
                        }
                    }

                    this.laserCooldown = 5;
                }

                //break blocks
                BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
                BlockPos max = BlockPos.containing(box.maxX, box.maxY, box.maxZ);

                for (BlockPos pos : BlockPos.betweenClosed(min, max))
                {
                    if (pos.getY() < target.getBlockY())
                        continue;

                    BlockState state = prince.level().getBlockState(pos);

                    if (!state.isAir() && state.getDestroySpeed(prince.level(), pos) >= 0 && state.getDestroySpeed(prince.level(), pos) < 30)
                    {
                        prince.level().destroyBlock(pos, true);
                    }
                }
            }
        }
        else
        {
            this.laserCooldown--;
        }

        //pretty particles :)
        //also not even gonna lie fam I yoinked this piece of code
        ServerLevel serverLevel = (ServerLevel) prince.level();

        Vec3 forward = laserDirection.normalize();

        Vec3 right = forward.cross(new Vec3(0, 1, 0));

        if (right.lengthSqr() < 1.0E-6)
        {
            right = new Vec3(1, 0, 0);
        }

        right = right.normalize();

        Vec3 up = right.cross(forward).normalize();

        double beamLength = start.distanceTo(end);

        double time = prince.tickCount * 0.2;


        for (double d = 0; d < beamLength; d += 0.08)
        {
            Vec3 point = start.add(forward.scale(d));


            // ==========================
            // Soft glowing center
            // ==========================

            if (((int)(d * 10) + prince.tickCount) % 4 == 0)
            {
                serverLevel.sendParticles(
                        ParticleTypes.END_ROD,
                        point.x,
                        point.y,
                        point.z,
                        1,
                        0.01,
                        0.01,
                        0.01,
                        0
                );
            }


            // ==========================
            // Inner bubble spiral
            // ==========================

            double innerAngle = time + d * 1.2;

            Vec3 innerOffset =
                    right.scale(Math.cos(innerAngle) * 0.18)
                            .add(up.scale(Math.sin(innerAngle) * 0.18));

            Vec3 innerPoint = point.add(innerOffset);


            serverLevel.sendParticles(
                    ParticleTypes.BUBBLE,
                    innerPoint.x,
                    innerPoint.y,
                    innerPoint.z,
                    3,
                    0,
                    0,
                    0,
                    0
            );


            // ==========================
            // Outer bubble spiral
            // ==========================

            double outerAngle = -time + d * 0.7;

            Vec3 outerOffset =
                    right.scale(Math.cos(outerAngle) * 0.4)
                            .add(up.scale(Math.sin(outerAngle) * 0.4));

            Vec3 outerPoint = point.add(outerOffset);


            serverLevel.sendParticles(
                    ParticleTypes.BUBBLE,
                    outerPoint.x,
                    outerPoint.y,
                    outerPoint.z,
                    2,
                    0,
                    0,
                    0,
                    0
            );


            // ==========================
            // Occasional water spray
            // ==========================

            if ((prince.tickCount + (int)d) % 12 == 0)
            {
                serverLevel.sendParticles(
                        ParticleTypes.SPLASH,
                        outerPoint.x,
                        outerPoint.y,
                        outerPoint.z,
                        1,
                        0.05,
                        0.05,
                        0.05,
                        0.02
                );
            }
        }
    }

    //wing flap
    public void tickWingFlap()
    {
        if (prince.getAttackTicks() < WING_WINDUP)
        {
            prince.getLookControl().setLookAt(target, 30, 30);
        }

        if (prince.getAttackTicks() == LASER_WINDUP)
        {
            fireWing(true);
            fireWing(false);
        }

        if (prince.getAttackTicks() > WING_END)
        {
            finishAttack();
        }
    }

    private void fireWing(boolean left)
    {
        Vec3 forward = prince.getForward();
        Vec3 right = new Vec3(-forward.z, 0, forward.x);

        double sideOffset = left ? -WING_OFFSET : WING_OFFSET;

        Vec3 spawn = prince.getEyePosition()
                .add(0, -3.75, 0)
                .add(right.scale(sideOffset));

        for (int i = 0; i < WING_ARROW_AMOUNT; i++)
        {
            Arrow arrow = new Arrow(prince.level(), prince, new ItemStack(Items.ARROW), null);
            arrow.setPos(spawn.x, spawn.y, spawn.z);

            float angle = (this.random.nextFloat() * WING_CONE - WING_CONE / 2F) + (left ? -15 : 15);
            float height = this.random.nextFloat() * 20F - 10F;

            Vec3 direction = forward
                    .yRot((float) Math.toRadians(angle))
                    .xRot((float)Math.toRadians(height))
                    .normalize();
            direction = direction
                    .add(0, 0.08, 0)
                    .normalize();

            arrow.shoot(direction.x, direction.y, direction.z, WING_ARROW_VELOCITY, 0.0F);
            arrow.setBaseDamage(3.0);
            arrow.pickup = AbstractArrow.Pickup.DISALLOWED;
            prince.level().addFreshEntity(arrow);
        }
    }
}