package com.psiracy.preposterousponds.block.custom;

import com.jcraft.jorbis.Block;
import com.jcraft.jorbis.DspState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.portal.TeleportTransition;
import org.jspecify.annotations.Nullable;

public class PondPortalBlock extends Block implements Portal
{
    public PondPortalBlock(DspState vd)
    {
        super(vd);
    }

    @Override
    public @Nullable TeleportTransition getPortalDestination(ServerLevel currentLevel, Entity entity, BlockPos portalEntryPos)
    {
        return null;
    }
}
