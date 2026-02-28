package net.tracen.umapyoi.compat.epicFight.adapter;

import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.compat.epicFight.UmaModelTransformer;

public interface IArmatureOverride {
    boolean umapyoi$getDoReplaceMesh();
    void umapyoi$setDoReplaceMesh(boolean setval);
    UmaModelTransformer.UmaMesh umapyoi$getMeshReplace();
    void umapyoi$setMeshReplace(UmaModelTransformer.UmaMesh setval);
    void umapyoi$setTexture(ResourceLocation location);
    void umapyoi$setEmissiveTexture(ResourceLocation location);
    ResourceLocation umapyoi$getTexture();
    ResourceLocation umapyoi$getEmissiveTexture();
}
