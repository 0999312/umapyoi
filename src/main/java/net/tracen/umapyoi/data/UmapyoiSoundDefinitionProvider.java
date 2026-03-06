package net.tracen.umapyoi.data;

import net.minecraft.Util;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.tracen.umapyoi.Umapyoi;

import static net.tracen.umapyoi.registry.SoundRegistry.SOUNDS;

public class UmapyoiSoundDefinitionProvider extends SoundDefinitionsProvider {
    public UmapyoiSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Umapyoi.MODID, helper);
    }

    @Override
    public void registerSounds() {
        SOUNDS.getEntries().forEach(
                soundEventRegistryObj -> {
                    add(soundEventRegistryObj::get,
                            definition()
                                    .subtitle(Util.makeDescriptionId("sound", soundEventRegistryObj.getId()))
                                    .with(sound(soundEventRegistryObj.getId(), SoundDefinition.SoundType.SOUND))
                    );
                }
        );
    }
}
