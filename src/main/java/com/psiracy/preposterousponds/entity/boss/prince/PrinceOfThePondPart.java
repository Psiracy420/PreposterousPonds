package com.psiracy.preposterousponds.entity.boss.prince;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

public class PrinceOfThePondPart extends Entity
{
    public final PrinceOfThePondEntity parentMob;
    public final String name;
    private final EntityDimensions size;

    public PrinceOfThePondPart(final PrinceOfThePondEntity parentMob, final String name, final float w, final float h)
    {
        super(parentMob.getType(), parentMob.level());
        this.size = EntityDimensions.scalable(w, h);
        this.refreshDimensions();
        this.parentMob = parentMob;
        this.name = name;
    }

    @Override
    protected void defineSynchedData(final SynchedEntityData.Builder entityData)
    {
    }

    @Override
    protected void readAdditionalSaveData(final ValueInput input)
    {
    }

    @Override
    protected void addAdditionalSaveData(final ValueOutput output)
    {
    }

    @Override
    public boolean isPickable()
    {
        return true;
    }

    @Override
    public @Nullable ItemStack getPickResult()
    {
        return this.parentMob.getPickResult();
    }

    @Override public boolean hurtServer(ServerLevel level, DamageSource source, float damage)
    {
        if (this.parentMob == null) return false; return this.parentMob.hurtServer(level, source, damage);
    }

    @Override
    public boolean is(final Entity other)
    {
        return this == other || this.parentMob == other;
    }

    @Override
    public EntityDimensions getDimensions(final Pose pose)
    {
        return this.size;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity)
    {
        return null;
    }

    @Override
    public boolean shouldBeSaved()
    {
        return false;
    }
}
