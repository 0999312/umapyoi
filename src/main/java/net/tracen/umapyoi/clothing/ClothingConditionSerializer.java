package net.tracen.umapyoi.clothing;

import com.google.gson.JsonObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class ClothingConditionSerializer<T extends ClothingCondition>
        implements IForgeRegistryEntry<ClothingConditionSerializer<?>> {

    public abstract T read(ResourceLocation location, JsonObject object);

    public abstract JsonObject write(JsonObject object, T instance);

    public JsonObject getJson(T value) {
        JsonObject json = new JsonObject();
        this.write(json, value);
        json.addProperty("type", this.getRegistryName().toString());
        return json;
    }

    private ResourceLocation registryName = null;

    public final ClothingConditionSerializer<T> setRegistryName(String name) {
        if (getRegistryName() != null)
            throw new IllegalStateException(
                    String.format("Attempted to set registry name with existing registry name! New: {}, Old: {}.", name,
                            getRegistryName()));

        this.registryName = GameData.checkPrefix(name, true);
        return this;
    }

    // Helpers
    @Override
    public final ClothingConditionSerializer<T> setRegistryName(ResourceLocation name) {
        return setRegistryName(name.toString());
    }

    public final ClothingConditionSerializer<T> setRegistryName(String modID, String name) {
        return setRegistryName(modID + ":" + name);
    }

    @Override
    public final ResourceLocation getRegistryName() {
        return registryName;
    }

    /**
     * Used by Forge's registry system.
     */
    @Override
    public final Class<ClothingConditionSerializer<?>> getRegistryType() {
        return castClass(ClothingConditionSerializer.class);
    }

    @SuppressWarnings("unchecked") // Need this wrapper, because generics
    private static <G> Class<G> castClass(Class<?> cls) {
        return (Class<G>) cls;
    }
}
