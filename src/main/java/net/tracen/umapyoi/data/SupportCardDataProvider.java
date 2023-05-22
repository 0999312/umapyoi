package net.tracen.umapyoi.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;

import cn.mcmod_mmf.mmlib.utils.DataGenUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.tags.TagManager;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.tracen.umapyoi.Umapyoi;
import net.tracen.umapyoi.registry.SupportCardRegistry;
import net.tracen.umapyoi.registry.training.card.SupportCard;

public class SupportCardDataProvider implements DataProvider {

    protected final DataGenerator generator;
    protected final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
    protected final Map<ResourceLocation, SupportCard> datas = Maps.newLinkedHashMap();
    protected final String modId;
    protected final ExistingFileHelper existingFileHelper;
    private final ExistingFileHelper.IResourceType resourceType;

    public SupportCardDataProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        this(generator, existingFileHelper, Umapyoi.MODID);
    }

    public SupportCardDataProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, String modId) {
        this.generator = generator;
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json",
                TagManager.getTagDir(SupportCard.REGISTRY_KEY));
    }

    public void addDatas() {
        for (Supplier<SupportCard> data : SupportCardRegistry.SUPPORT_CARD.getEntries()) {
            this.addData(data);
        }
    }


    public void addData(Supplier<SupportCard> data) {
        this.addData(data, data.get().getRegistryName());
    }
    
    public void addData(Supplier<SupportCard> data, ResourceLocation name) {
        this.datas.computeIfAbsent(name, loc -> {
            existingFileHelper.trackGenerated(loc, resourceType);
            return data.get();
        });
    }

    @Override
    public void run(HashCache cache) throws IOException {
        this.datas.clear();
        this.addDatas();
        final Path outputFolder = generator.getOutputFolder();
        this.datas.forEach((loc, data) -> {
            String pathString = String.join("/", PackType.SERVER_DATA.getDirectory(), loc.getNamespace(),
                    SupportCard.REGISTRY_KEY.location().getNamespace(), SupportCard.REGISTRY_KEY.location().getPath(),
                    loc.getPath() + ".json");
            Path path = outputFolder.resolve(pathString);

            SupportCard.CODEC.encodeStart(ops, data)
                    .resultOrPartial(msg -> Umapyoi.getLogger().error("Failed to encode {}: {}", path, msg)) // Log
                                                                                                             // error on
                                                                                                             // encode
                                                                                                             // failure.
                    .ifPresent(json -> // Output to file on encode success.
            {
                        try {
                            DataProvider.save(DataGenUtil.DATA_GSON, cache, json, path);
                        } catch (IOException e) // The throws can't deal with this exception, because we're inside the
                                                // ifPresent.
                        {
                            Umapyoi.getLogger().error("Failed to save " + pathString, e);
                        }
                    });
        });

    }

    @Override
    public String getName() {
        return String.format("%s provider for %s", "supplier card", modId);
    }

}
