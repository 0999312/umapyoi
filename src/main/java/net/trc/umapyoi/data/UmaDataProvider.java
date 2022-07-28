package net.trc.umapyoi.data;

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
import net.trc.umapyoi.Umapyoi;
import net.trc.umapyoi.registry.UmaData;
import net.trc.umapyoi.registry.UmaDataRegistry;

public class UmaDataProvider implements DataProvider {

    protected final DataGenerator generator;
    protected final RegistryOps<JsonElement> ops = RegistryOps.create(JsonOps.INSTANCE, RegistryAccess.BUILTIN.get());
    protected final Map<ResourceLocation, UmaData> datas = Maps.newLinkedHashMap();
    protected final String modId;
    protected final ExistingFileHelper existingFileHelper;
    private final ExistingFileHelper.IResourceType resourceType;
    public UmaDataProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, String modId) {
        this.generator = generator;
        this.modId = modId;
        this.existingFileHelper = existingFileHelper;
        this.resourceType = new ExistingFileHelper.ResourceType(PackType.SERVER_DATA, ".json", TagManager.getTagDir(UmaData.REGISTRY_KEY));
    }
    
    public void addDatas() {
        this.addData(UmaDataRegistry.GOLD_SHIP);
        this.addData(UmaDataRegistry.TOKAI_TEIO);
        this.addData(UmaDataRegistry.SPECIAL_WEEK);
        this.addData(UmaDataRegistry.OGURI_CAP);
        this.addData(UmaDataRegistry.SAKURA_CHIYONO_O);
        this.addData(UmaDataRegistry.OGURI_CAP_XMAS);
        this.addData(UmaDataRegistry.AGNUS_TACHYON);
    }
    
    public void addData(Supplier<UmaData> data) {
        this.datas.computeIfAbsent(data.get().getRegistryName(), loc->{
            existingFileHelper.trackGenerated(loc, resourceType);
            return data.get();
        });
    }
    
    @Override
    public void run(HashCache cache) throws IOException {
        this.datas.clear();
        this.addDatas();
        final Path outputFolder = generator.getOutputFolder();
        this.datas.forEach( (loc, data) -> {
            String pathString = String.join("/", PackType.SERVER_DATA.getDirectory(), loc.getNamespace(), UmaData.REGISTRY_KEY.location().getNamespace(), UmaData.REGISTRY_KEY.location().getPath(), loc.getPath()+".json");
            Path path = outputFolder.resolve(pathString);
            
            UmaData.CODEC.encodeStart(ops, data)
            .resultOrPartial(msg -> Umapyoi.getLogger().error("Failed to encode {}: {}", path, msg)) // Log error on encode failure.
            .ifPresent(json -> // Output to file on encode success.
            {
                try
                {
                    DataProvider.save(DataGenUtil.DATA_GSON, cache, json, path);
                }
                catch (IOException e) // The throws can't deal with this exception, because we're inside the ifPresent.
                {
                    Umapyoi.getLogger().error("Failed to save " + pathString, e);
                }
            });
        });
        
    }

    @Override
    public String getName() {
        return String.format("%s provider for %s", "uma musume data", modId);
    }

}
