package com.mastermarisa.solmaiddream.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.Objects;

public class ModServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> MILESTONES = BUILDER
            .translation("config.solmaiddream.server.milestones")
            .defineList("milestones",List.of(5,10,15,20,25,30,35,40,45,50),(e)-> e instanceof Integer);

    public static final ForgeConfigSpec.IntValue FAVORABILITY_INCREASED_DAYLY_WISH_ACHIEVED = BUILDER
            .translation("config.solmaiddream.server.favorability_per_daily_wish")
            .defineInRange("favorabilityIncreasedDailyWishAchieved",2,0,1000);

    public static final ForgeConfigSpec.IntValue FAVORABILITY_INCREASED_MILESTONE_REACHED = BUILDER
            .translation("config.solmaiddream.server.favorability_per_milestone")
            .defineInRange("favorabilityIncreasedMilestoneReached",10,0,1000);

    public static final ForgeConfigSpec.IntValue WISH_BUFF_ADD_VALUE_PERCENT = BUILDER
            .translation("config.solmaiddream.server.wish_buff_percent")
            .defineInRange("wishBuffAddValuePercent",10,0,1000);

    public static final ForgeConfigSpec.IntValue HP_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.hearts_per_milestone")
            .defineInRange("hpPerMilestone",2,0,1000);

    public static final ForgeConfigSpec.IntValue ARMOR_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.armor_per_milestone")
            .defineInRange("armorPerMilestone",1,0,1000);

    public static final ForgeConfigSpec.IntValue ATTACK_DAMAGE_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.attack_damage_per_milestone")
            .defineInRange("attackDamagePerMilestone",1,0,1000);

    public static final ForgeConfigSpec.IntValue ARMOR_TOUGHNESS_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.armor_toughness_per_milestone")
            .defineInRange("armorToughnessPerMilestone",1,0,1000);


    public static final ForgeConfigSpec.IntValue MINIMAL_HUNGER_VALUE = BUILDER
            .translation("config.solmaiddream.server.minimal_hunger_value")
            .defineInRange("minimalHungerValue",4,0,1000);

    public static final ForgeConfigSpec.IntValue MINIMAL_NUTRITION = BUILDER
            .translation("config.solmaiddream.server.minimal_nutrition")
            .defineInRange("minimalNutrition",6,0,1000);

    public static final ForgeConfigSpec.IntValue MINIMAL_FOOD_TYPES_TO_GENERATE_WISHES = BUILDER
            .translation("config.solmaiddream.server.minimal_food_types_for_wishes")
            .defineInRange("minimalFoodTypesToGenerateWishes",20,3,1000);


    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> FOOD_BLACKLIST = BUILDER
            .translation("config.solmaiddream.server.food_blacklist")
            .defineList("foodBlacklist",List.of(""),(e)-> e instanceof String);

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> FOOD_WHITELIST = BUILDER
            .translation("config.solmaiddream.server.food_whitelist")
            .defineList("foodWhitelist",List.of(""),(e)-> e instanceof String);

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> MOD_BLACKLIST = BUILDER
            .translation("config.solmaiddream.server.mod_blacklist")
            .defineList("modBlacklist",List.of(""),(e)-> e instanceof String);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    /**
     * 获取触发里程碑所需的食物数量
     * @return 食物数量
     */
    public static List<? extends Integer> getMilestones(){ return MILESTONES.get(); }

    /**
     * 获取可以触发属性加成的食物最低饥饿值
     * @return 饥饿值阈值
     */
    public static int getMinimalHungerValue(){ return MINIMAL_HUNGER_VALUE.get(); }
    /**
     * 获取可以触发属性加成的食物最低饱食度
     * @return 饱食度阈值
     */
    public static int getMinimalNutrition(){ return MINIMAL_NUTRITION.get(); }

    /**
     * 获取女仆生成愿望需要的食物
     * @return 女仆生成愿望需要的食物
     */
    public static int getMinimalFoodTypesToGenerateWishes() { return MINIMAL_FOOD_TYPES_TO_GENERATE_WISHES.get(); }

    /**
     * 获取食物黑名单列表
     * @return 食物黑名单列表
     */
    public static List<? extends String> getFoodBlacklist(){ return FOOD_BLACKLIST.get(); }
    /**
     * 获取食物白名单列表
     * @return 食物白名单列表
     */
    public static List<? extends String> getFoodWhitelist(){ return FOOD_WHITELIST.get(); }
    /**
     * 获取MOD黑名单列表
     * @return MOD黑名单列表
     */
    public static List<? extends String> getModBlacklist(){ return MOD_BLACKLIST.get(); }

    /**
     * 获取完成里程碑后女仆获得的血量加成
     * @return 血量加成
     */
    public static int getHPPerMilestone() { return HP_PER_MILESTONE.get(); }
    /**
     * 获取完成里程碑后女仆获得的护甲值加成
     * @return 护甲值加成
     */
    public static int getArmorPerMilestone() { return ARMOR_PER_MILESTONE.get(); }
    /**
     * 获取完成里程碑后女仆获得的护甲韧性加成
     * @return 护甲韧性加成
     */
    public static int getArmorToughnessPerMilestone() { return ARMOR_TOUGHNESS_PER_MILESTONE.get(); }
    /**
     * 获取完成里程碑后女仆获得的攻击力加成
     * @return 攻击力加成
     */
    public static int getAttackDamagePerMilestone() { return ATTACK_DAMAGE_PER_MILESTONE.get(); }

    /**
     * 获取全属性增加百分比
     * 完成当天的所有女仆愿望后，女仆会获得全属性加成
     * @return 全属性增加百分比
     */
    public static int getWishBuffAddValuePercent() { return WISH_BUFF_ADD_VALUE_PERCENT.get(); }

    /**
     * 获取完成愿望的好感度增加量
     * @return 好感度增加量
     */
    public static int getFavorabilityDailyWishAchieved() { return FAVORABILITY_INCREASED_DAYLY_WISH_ACHIEVED.get(); }
    /**
     * 获取完成食物里程碑的好感度增加量
     * @return 好感度增加量
     */
    public static int getFavorabilityMilestoneReached() { return FAVORABILITY_INCREASED_MILESTONE_REACHED.get(); }
}
