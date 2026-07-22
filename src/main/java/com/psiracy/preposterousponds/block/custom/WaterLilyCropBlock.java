package com.psiracy.preposterousponds.block.custom;

import com.psiracy.preposterousponds.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;

public class WaterLilyCropBlock extends CropBlock
{
    public static final int MAX_AGE = 2;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

    public WaterLilyCropBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId()
    {
        return ModItems.LILY_SEED;
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockState stateBelow = level.getBlockState(pos.below());

        if(stateBelow.is(Blocks.WATER))
        {
            BlockPos posBelow = pos.below();

            for (Direction dir : Direction.Plane.HORIZONTAL)
            {
                BlockState blockState = level.getBlockState(posBelow.relative(dir));
                FluidState fluidState = level.getFluidState(posBelow.relative(dir));
                if(fluidState.is(FluidTags.WATER) || blockState.is(Blocks.FROSTED_ICE))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(AGE);
    }
}
