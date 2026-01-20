package com.mastermarisa.solmaiddream.utils;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.model.data.ModelData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModUtils {
    public static final boolean IRIS_LOADED = ModList.get().isLoaded("iris");

    public static void writeStringSet(CompoundTag tag, String key, Set<String> stringSet) {
        ListTag listTag = new ListTag();
        for (String str : stringSet) {
            listTag.add(StringTag.valueOf(str));
        }
        tag.put(key, listTag);
    }

    public static Set<String> readStringSet(CompoundTag tag, String key) {
        Set<String> stringSet = new HashSet<>();
        if (tag.contains(key, Tag.TAG_LIST)) {
            ListTag listTag = tag.getList(key, Tag.TAG_STRING);
            for (int i = 0; i < listTag.size(); i++) {
                stringSet.add(listTag.getString(i));
            }
        }
        return stringSet;
    }

    public static void writeStringList(CompoundTag tag, String key, List<String> stringList){
        ListTag listTag = new ListTag();
        for (String str : stringList) {
            listTag.add(StringTag.valueOf(str));
        }
        tag.put(key, listTag);
    }

    public static List<String> readStringList(CompoundTag tag, String key) {
        List<String> stringList = new ArrayList<>();
        if (tag.contains(key, Tag.TAG_LIST)) {
            ListTag listTag = tag.getList(key, Tag.TAG_STRING);
            for (int i = 0; i < listTag.size(); i++) {
                stringList.add(listTag.getString(i));
            }
        }
        return stringList;
    }

    public static String encodeItem(Item item){
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }

    public static Item decodeItem(String encoded){
        ResourceLocation name = ResourceLocation.tryParse(encoded);
        return BuiltInRegistries.ITEM.get(name);
    }

    public static boolean canDecodeItem(String encoded){
        ResourceLocation name = ResourceLocation.tryParse(encoded);
        return BuiltInRegistries.ITEM.get(name) != null;
    }

    public static ResourceLocation getItemID(String encoded){
        return ResourceLocation.tryParse(encoded);
    }

    public static ResourceLocation getItemTextureLocation(ItemStack stack) {
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "textures/item/" + itemId.getPath() + ".png");
    }

    public static ResourceLocation getItemTextureLocation(ResourceLocation ID) {
        return ResourceLocation.fromNamespaceAndPath(ID.getNamespace(), "textures/item/" + ID.getPath() + ".png");
    }

    public static ResourceLocation getItemTextureLocation(String encoded){
        return getItemTextureLocation(getItemID(encoded));
    }

    public static List<BakedQuad> unshade(List<BakedQuad> src) {
        return src.stream().map(q ->
                // 重新构造时把 shade 置为 false：
                new BakedQuad(q.getVertices().clone(), q.getTintIndex(), q.getDirection(), q.getSprite(), false)
        ).toList();
    }

    public static BakedModel unshade(BakedModel bakedModel){
        List<BakedQuad> quads = bakedModel.getQuads(null, null, RandomSource.create(42L), ModelData.EMPTY, null);
        quads = unshade(quads);

        return bakedModel;
    }
}
