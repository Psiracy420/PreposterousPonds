package com.psiracy.preposterousponds.entity.client;

import com.ibm.icu.text.Normalizer;
import com.psiracy.preposterousponds.PreposterousPonds;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

public class ModModelLayerLocations
{
    public static final ModelLayerLocation PRINCE =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "prince_of_the_pond"), "main");
    public static final ModelLayerLocation GOOSE =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath(PreposterousPonds.MOD_ID, "goose"), "main");
}
