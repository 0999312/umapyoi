package net.tracen.umapyoi.utils;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public class Codecs {
    public static <K, V, M extends Map<K, V>> StreamCodec<ByteBuf, M> streamUnboundedMap(
            StreamCodec<ByteBuf, K> keyCodec,
            StreamCodec<ByteBuf, V> valueCodec,
            Supplier<M> constructor
    ) {
        return new StreamCodec<>() {
            @Override
            public M decode(ByteBuf byteBuf) {
                M returnVal = constructor.get();
                int length = byteBuf.readInt();
                for (int i = 0; i < length; i++) {
                    K keyVal = keyCodec.decode(byteBuf);
                    V valueVal = valueCodec.decode(byteBuf);
                    returnVal.put(keyVal, valueVal);
                }
                return returnVal;
            }

            @Override
            public void encode(ByteBuf o, M kvMap) {
                o.writeInt(kvMap.size());
                for (Map.Entry<K, V> entry: kvMap.entrySet()) {
                    keyCodec.encode(o, entry.getKey());
                    valueCodec.encode(o, entry.getValue());
                }
            }
        };
    }

    public static <K, V> StreamCodec<ByteBuf, Map<K, V>> streamUnboundedMap(
            StreamCodec<ByteBuf, K> keyCodec,
            StreamCodec<ByteBuf, V> valueCodec
    ) {
        return streamUnboundedMap(keyCodec, valueCodec, HashMap::new);
    }
}
