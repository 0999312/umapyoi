package net.tracen.umapyoi.compat.epicFight.mixin;

import net.minecraft.resources.ResourceLocation;
import net.tracen.umapyoi.compat.epicFight.UmaModelTransformer;
import net.tracen.umapyoi.compat.epicFight.adapter.IArmatureOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import yesman.epicfight.api.model.Armature;

@Mixin(value = Armature.class, remap = false)
public class ArmatureMixin implements IArmatureOverride {
    @Unique
    private boolean umapyoi$doReplaceMesh;

    @Unique
    private UmaModelTransformer.UmaMesh umapyoi$meshReplace;

    @Unique
    private ResourceLocation umapyoi$texture;

    @Unique
    private ResourceLocation umapyoi$emissiveTexture;

    @Unique
    public boolean umapyoi$getDoReplaceMesh() {
        return umapyoi$doReplaceMesh;
    };

    @Unique
    public void umapyoi$setDoReplaceMesh(boolean setval) {this.umapyoi$doReplaceMesh = setval;}

    @Unique
    public UmaModelTransformer.UmaMesh umapyoi$getMeshReplace() {
        return umapyoi$meshReplace;
    };

    @Unique
    public void umapyoi$setMeshReplace(UmaModelTransformer.UmaMesh setval) {this.umapyoi$meshReplace = setval;}

    @Override
    public void umapyoi$setTexture(ResourceLocation location) {
        this.umapyoi$texture = location;
    }

    @Override
    public void umapyoi$setEmissiveTexture(ResourceLocation location) {
        this.umapyoi$emissiveTexture = location;
    }

    @Override
    public ResourceLocation umapyoi$getTexture() {
        return umapyoi$texture;
    }

    @Override
    public ResourceLocation umapyoi$getEmissiveTexture() {
        return umapyoi$emissiveTexture;
    }
}
