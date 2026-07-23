package com.psiracy.preposterousponds.world.structure;

import com.psiracy.preposterousponds.PreposterousPonds;
import com.psiracy.preposterousponds.entity.ModEntities;
import com.psiracy.preposterousponds.entity.boss.prince.PrinceOfThePondEntity;
import com.psiracy.preposterousponds.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public final class PrinceArenaStructure {
    private static final Identifier STRUCTURE = Identifier.fromNamespaceAndPath( PreposterousPonds.MOD_ID, "prince_arena" );
    private static final int WATER_SURFACE_Y = 64;

    private static final BlockPos PLACED_MARKER = new BlockPos(0, 2, 0);

    private PrinceArenaStructure()
    {
    }

    public static void register()
    {
        ServerLevelEvents.LOAD.register((server, level) ->
        {
            if (!level.dimension().equals(ModDimensions.PONDDIM_LEVEL_KEY)) {
                return;
            }

            placeOnce(level);
        });
    }

    private static void placeOnce(ServerLevel level)
    {
        if (level.getBlockState(PLACED_MARKER).is(Blocks.LODESTONE))
        {
            return;
        }

        var templateOptional = level
                .getStructureManager()
                .get(STRUCTURE);

        if (templateOptional.isEmpty())
        {
            PreposterousPonds.LOGGER.error("Could not find structure template {}. Expected file: " + "data/{}/structure/{}.nbt", STRUCTURE, STRUCTURE.getNamespace(), STRUCTURE.getPath());
            return;
        }

        StructureTemplate template = templateOptional.get();
        Vec3i size = template.getSize();
        BlockPos placementPos = new BlockPos(-(size.getX() / 2), WATER_SURFACE_Y, -(size.getZ() / 2));

        loadRequiredChunks(level, placementPos, size);
        StructurePlaceSettings settings = new StructurePlaceSettings();
        boolean placed = template.placeInWorld(level, placementPos, placementPos, settings, level.getRandom(), 2);

        if (!placed)
        {
            PreposterousPonds.LOGGER.error("Failed to place {} at {}", STRUCTURE, placementPos);
            return;
        }

        spawnBoss(level);

        level.setBlockAndUpdate(PLACED_MARKER, Blocks.LODESTONE.defaultBlockState());
        PreposterousPonds.LOGGER.info("Placed {} at {} with template size {}", STRUCTURE, placementPos, size);
    }

    private static void loadRequiredChunks(ServerLevel level, BlockPos placementPos, Vec3i size)
    {
        int minimumChunkX = placementPos.getX() >> 4;
        int minimumChunkZ = placementPos.getZ() >> 4;
        int maximumChunkX = (placementPos.getX() + size.getX() - 1) >> 4;
        int maximumChunkZ = (placementPos.getZ() + size.getZ() - 1) >> 4;

        for (int chunkX = minimumChunkX; chunkX <= maximumChunkX; chunkX++)
        {
            for (int chunkZ = minimumChunkZ; chunkZ <= maximumChunkZ; chunkZ++)
            {
                level.getChunk(chunkX, chunkZ);
            }
        }
    }

    private static void spawnBoss(ServerLevel level)
    {
        BlockPos bossPos = new BlockPos(0, 66, 0);
        PrinceOfThePondEntity boss = new PrinceOfThePondEntity(ModEntities.PRINCE, level);
        boss.setPos(bossPos.getX(), bossPos.getY(), bossPos.getZ());
        boss.setPersistenceRequired();
        boolean spawned = level.addFreshEntity(boss);

        if (!spawned)
        {
            PreposterousPonds.LOGGER.error("Failed to spawn Prince of the Pond at {}", bossPos);
            return;
        }

        PreposterousPonds.LOGGER.info("Spawned Prince of the Pond at {}", bossPos);
    }
}