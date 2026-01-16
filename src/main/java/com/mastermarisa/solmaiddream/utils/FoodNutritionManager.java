package com.mastermarisa.solmaiddream.utils;

import com.mastermarisa.solmaiddream.config.ModServerConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FoodNutritionManager {
    public static boolean filterByBlacklist(ItemStack stack, LivingEntity entity){
        //Food Blacklist
        if (ModServerConfig.getFoodBlacklist().contains(ModUtils.encodeItem(stack.getItem()))) return false;

        //Whether it is food
        FoodProperties foodProperties = stack.getFoodProperties(entity);
        if (foodProperties == null) return false;

        //Hunger Value and Nutrition
        int hunger = ModServerConfig.getMinimalHungerValue();
        int nutrition = ModServerConfig.getMinimalNutrition();
        if (foodProperties.nutrition() < hunger) return false;
        if (foodProperties.saturation() + foodProperties.nutrition() < nutrition) return false;

        //Harmful Effect
        if (containsHarmfulEffect(foodProperties)) return false;

        return true;
    }

    public static boolean filterByWhitelist(ItemStack stack){
        return ModServerConfig.getFoodWhitelist().contains(ModUtils.encodeItem(stack.getItem()));
    }

    public static boolean filter(ItemStack stack,LivingEntity entity){
        return filterByWhitelist(stack) || filterByBlacklist(stack,entity);
    }

    public static boolean containsHarmfulEffect(FoodProperties foodProperties){
        if (foodProperties == null) return false;

        for (FoodProperties.PossibleEffect possibleEffect : foodProperties.effects()){
            if (possibleEffect.effect().getEffect().value().getCategory() == MobEffectCategory.HARMFUL){
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
}
