package com.mastermarisa.solmaiddream.utils;

import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.event.OnAddJadeInfoEvent;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FoodNutritionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FoodNutritionManager.class);
    public static boolean filterByBlacklist(ItemStack stack, LivingEntity entity){
        //Food Blacklist
        if (ModServerConfig.getFoodBlacklist().contains(ModUtils.encodeItem(stack.getItem()))) return false;

        //Whether it is food
        FoodProperties foodProperties = stack.getFoodProperties(entity);
        if (foodProperties == null) return false;

        //Hunger Value and Nutrition
        int hunger = ModServerConfig.getMinimalHungerValue();
        int nutrition = ModServerConfig.getMinimalNutrition();
        if (foodProperties.getNutrition() < hunger) return false;
        if (foodProperties.getSaturationModifier() + foodProperties.getNutrition() < nutrition) return false;

        //Harmful Effect
        if (containsHarmfulEffect(foodProperties)) return false;

        return true;
    }

    public static String getUncountableReason(ItemStack stack, LivingEntity entity){
        if (ModServerConfig.getFoodBlacklist().contains(ModUtils.encodeItem(stack.getItem())))
            return Component.translatable("jade.solmaiddream.tooltip.blacklist").getString();

        FoodProperties foodProperties = stack.getFoodProperties(entity);
        int hunger = ModServerConfig.getMinimalHungerValue();
        int nutrition = ModServerConfig.getMinimalNutrition();
        if (foodProperties.getNutrition() < hunger)
            return Component.translatable("jade.solmaiddream.tooltip.insufficient_hunger").getString();
        if (foodProperties.getSaturationModifier() + foodProperties.getNutrition() < nutrition)
            return Component.translatable("jade.solmaiddream.tooltip.insufficient_nutrition").getString();

        if (containsHarmfulEffect(foodProperties))
            return Component.translatable("jade.solmaiddream.tooltip.harmful_effect").getString();

        return Component.translatable("jade.solmaiddream.tooltip.debug").getString();
    }

    public static boolean filterByWhitelist(ItemStack stack){
        return ModServerConfig.getFoodWhitelist().contains(ModUtils.encodeItem(stack.getItem()));
    }

    public static boolean filter(ItemStack stack,LivingEntity entity){
        return filterByWhitelist(stack) || filterByBlacklist(stack,entity);
    }

    public static boolean containsHarmfulEffect(FoodProperties foodProperties){
        if (foodProperties == null) return false;

        for (Pair<MobEffectInstance, Float> possibleEffect : foodProperties.getEffects()){
            if (possibleEffect.getFirst().getEffect().getCategory() == MobEffectCategory.HARMFUL){
                return true;
            }
        }

        return false;
    }

    public static double getFoodValue(ItemStack stack){
        return 1.0d;
    }

    public static double getFoodValue(Item item){
        return 1.0d;
    }

    public static void setFoodsByComponent(HolderLookup.Provider lookupProvider) {
        List<Item> foodList = new ArrayList<>();
        lookupProvider.lookup(Registries.ITEM).ifPresent(itemLookup -> {
            itemLookup.listElements().forEach(itemHolder -> {
                Item item = itemHolder.value();
                if (item.getFoodProperties(new ItemStack(item),null) != null){
                    foodList.add(item);
                }
            });
        });
        allFoods = foodList;
        allFoods = allFoods.stream().map(ItemStack::new).filter((o)-> filter(o,null)).map(ItemStack::getItem).toList();
    }

    public static List<Item> getFoodsByComponent(){
        return allFoods;
    }

    public static List<Item> allFoods;
}
