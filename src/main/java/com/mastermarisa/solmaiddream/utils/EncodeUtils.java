package com.mastermarisa.solmaiddream.utils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentSkipListSet;

public class EncodeUtils {
    public static void writeStringSet(CompoundTag tag, ConcurrentSkipListSet<String> set, String key) {
        ListTag listTag = new ListTag();
        set.forEach(s -> listTag.add(StringTag.valueOf(s)));
        tag.put(key, listTag);
    }

    public static ConcurrentSkipListSet<String> readStringSet(CompoundTag tag, String key) {
        if (tag.contains(key)) {
            ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>();
            ListTag listTag = tag.getList(key, Tag.TAG_STRING);
            listTag.forEach(t -> set.add(t.getAsString()));
            return set;
        } else return new ConcurrentSkipListSet<>();
    }

    public static ResourceLocation encode(Item item){
        return BuiltInRegistries.ITEM.getKey(item);
    }

    public static ResourceLocation encode(ItemStack stack){
        return BuiltInRegistries.ITEM.getKey(stack.getItem());
    }

    @Nullable
    public static Item decode(String key){ return BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(key)); }

    @Nullable
    public static Item decode(ResourceLocation key){
        return BuiltInRegistries.ITEM.get(key);
    }
}
