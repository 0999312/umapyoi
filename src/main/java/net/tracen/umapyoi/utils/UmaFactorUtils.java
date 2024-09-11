package net.tracen.umapyoi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import net.tracen.umapyoi.registry.UmaFactorRegistry;
import net.tracen.umapyoi.registry.factors.FactorData;
import net.tracen.umapyoi.registry.factors.UmaFactorStack;

public class UmaFactorUtils {
    public static List<FactorData> serializeData(List<UmaFactorStack> factors) {
    	List<FactorData> result = new ArrayList<FactorData>();

        factors.forEach(factor->result.add(new FactorData(UmaFactorRegistry.REGISTRY.getKey(factor.getFactor()),
        		factor.getLevel(), Optional.ofNullable(factor.getTag()))));
        return result;
    }

    public static List<UmaFactorStack> deserializeData(List<FactorData> datas) {
        List<UmaFactorStack> list = Lists.newArrayList();

        datas.forEach(data->list.add(new UmaFactorStack(UmaFactorRegistry.REGISTRY.get(data.id()), data.level(), data.tag())));

        return list;
    }
}
