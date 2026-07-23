package com.psiracy.preposterousponds.world.structure;

import com.psiracy.preposterousponds.PreposterousPonds;
import com.psiracy.preposterousponds.world.dimension.ModDimensions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLevelEvents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.phys.Vec3;

public class PrinceArenaStructure
{
    private static final BlockPos STRUCTURE_POS = new BlockPos(0, 64, 0);
    private static final Identifier STRUCTURE = Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "prince_arena");
    private PrinceArenaStructure()
    {
    }

    public static void register()
    {
        ServerLevelEvents.LOAD.register((server, level) ->
        {
            if (!level.dimension().equals(ModDimensions.PONDDIM_LEVEL_KEY))
            {
                return;
            }

            level.getChunk(0, 0);
            CommandSourceStack source = server
                    .createCommandSourceStack()
                    .withLevel(level)
                    .withPosition(Vec3.atLowerCornerOf(STRUCTURE_POS))
                    .withPermission(PermissionSet.ALL_PERMISSIONS);

            server.getCommands().performPrefixedCommand(source, "place structure " + STRUCTURE + " 0 64 0");
            PreposterousPonds.LOGGER.info("Attempted to place prince_arena at 0, 64, 0");
        });
    }
}
