package com.mastermarisa.solmaiddream.utils;

import com.mastermarisa.solmaiddream.init.InitConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FilterHelper {
    private static boolean initialized;
    private static final List<Item> whitelistItems;
    private static final List<TagKey<Item>> whitelistTags;
    private static final List<Item> blacklistItems;
    private static final List<TagKey<Item>> blacklistTags;

    public static boolean filter(ItemStack itemStack, LivingEntity entity) {
        init();
        if (isInWhiteList(itemStack)) return true;
        if (isInBlacklist(itemStack)) return false;

        FoodProperties foodProperties = itemStack.getFoodProperties(entity);
        if (foodProperties == null) return false;

        int nutrition = foodProperties.nutrition();
        float saturation = foodProperties.saturation();
        if (nutrition < InitConfig.HUNGER_VALUE()) return false;
        if (nutrition + saturation < InitConfig.SATURATION()) return false;

        return !containsHarmfulEffect(foodProperties);
    }

    public static boolean containsHarmfulEffect(FoodProperties foodProperties){
        if (foodProperties == null) return false;
        return foodProperties.effects().stream().anyMatch(e -> e.effect().getEffect().value().getCategory().equals(MobEffectCategory.HARMFUL));
    }

    private static boolean isInWhiteList(ItemStack itemStack) {
        return InitConfig.ENABLE_WHITELIST() && (whitelistItems.contains(itemStack.getItem()) || whitelistTags.stream().anyMatch(itemStack::is));
    }

    private static boolean isInBlacklist(ItemStack itemStack) {
        return InitConfig.ENABLE_BLACKLIST() && (blacklistItems.contains(itemStack.getItem()) || blacklistTags.stream().anyMatch(itemStack::is));
    }

    public static String getInvalidReason(ItemStack stack, LivingEntity entity){
        if (isInBlacklist(stack)) return Component.translatable("jade.solmaiddream.tooltip.blacklist").getString();

        FoodProperties foodProperties = stack.getFoodProperties(entity);
        int nutrition = foodProperties.nutrition();
        float saturation = foodProperties.saturation();
        if (nutrition < InitConfig.HUNGER_VALUE())
            return Component.translatable("jade.solmaiddream.tooltip.insufficient_hunger").getString();
        if (nutrition + saturation < InitConfig.SATURATION())
            return Component.translatable("jade.solmaiddream.tooltip.insufficient_nutrition").getString();

        if (containsHarmfulEffect(foodProperties))
            return Component.translatable("jade.solmaiddream.tooltip.harmful_effect").getString();

        return Component.translatable("jade.solmaiddream.tooltip.debug").getString();
    }

    private static void init() {
        if (initialized) return;
        for (String str : InitConfig.WHITELIST()) {
            if (isTagLocation(str)) {
                ResourceLocation location = ResourceLocation.tryParse(str.substring(1));
                if (location == null) continue;
                whitelistTags.add(TagKey.create(Registries.ITEM, location));
            } else {
                ResourceLocation location = ResourceLocation.tryParse(str);
                if (location == null) continue;
                whitelistItems.add(BuiltInRegistries.ITEM.get(location));
            }
        }
        for (String str : InitConfig.BLACKLIST()) {
            if (isTagLocation(str)) {
                ResourceLocation location = ResourceLocation.tryParse(str.substring(1));
                if (location == null) continue;
                blacklistTags.add(TagKey.create(Registries.ITEM, location));
            } else {
                ResourceLocation location = ResourceLocation.tryParse(str);
                if (location == null) continue;
                blacklistItems.add(BuiltInRegistries.ITEM.get(location));
            }
        }
        initialized = true;
    }

    private static boolean isTagLocation(String location) {
        return location.startsWith("#");
    }

    static {
        whitelistItems = new ArrayList<>();
        whitelistTags = new ArrayList<>();
        blacklistItems = new ArrayList<>();
        blacklistTags = new ArrayList<>();
    }
}
