package com.mastermarisa.solmaiddream.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Objects;

public class ModServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> MILESTONES = BUILDER
            .translation("config.solmaiddream.server.milestones")
            .defineList("milestones",List.of(5,10,15,20,25,30,35,40,45,50),()-> 0,(e)-> e instanceof Integer);

    public static final ModConfigSpec.IntValue FAVORABILITY_INCREASED_DAYLY_WISH_ACHIEVED = BUILDER
            .translation("")
            .defineInRange("favorabilityIncreasedDailyWishAchieved",2,0,1000);

    public static final ModConfigSpec.IntValue FAVORABILITY_INCREASED_MILESTONE_REACHED = BUILDER
            .translation("config.solmaiddream.server.favorability_per_milestone")
            .defineInRange("favorabilityIncreasedMilestoneReached",10,0,1000);

    public static final ModConfigSpec.IntValue WISH_BUFF_ADD_VALUE_PERCENT = BUILDER
            .translation("")
            .defineInRange("wishBuffAddValuePercent",10,0,1000);

    public static final ModConfigSpec.IntValue HP_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.hearts_per_milestone")
            .defineInRange("hpPerMilestone",2,0,1000);

    public static final ModConfigSpec.IntValue ARMOR_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.armor_per_milestone")
            .defineInRange("armorPerMilestone",1,0,1000);

    public static final ModConfigSpec.IntValue ATTACK_DAMAGE_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.attack_damage_per_milestone")
            .defineInRange("attackDamagePerMilestone",1,0,1000);

    public static final ModConfigSpec.IntValue ARMOR_TOUGHNESS_PER_MILESTONE = BUILDER
            .translation("config.solmaiddream.server.armor_toughness_per_milestone")
            .defineInRange("armorToughnessPerMilestone",1,0,1000);


    public static final ModConfigSpec.IntValue MINIMAL_HUNGER_VALUE = BUILDER
            .translation("")
            .defineInRange("minimalHungerValue",4,0,1000);

    public static final ModConfigSpec.IntValue MINIMAL_NUTRITION = BUILDER
            .translation("")
            .defineInRange("minimalNutrition",6,0,1000);

    public static final ModConfigSpec.IntValue MINIMAL_FOOD_TYPES_TO_GENERATE_WISHES = BUILDER
            .translation("")
            .defineInRange("minimalFoodTypesToGenerateWishes",20,3,1000);


    public static final ModConfigSpec.ConfigValue<List<? extends String>> FOOD_BLACKLIST = BUILDER
            .translation("")
            .defineList("foodBlacklist",List.of(""),()-> "",(e)-> e instanceof String);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> FOOD_WHITELIST = BUILDER
            .translation("")
            .defineList("foodWhitelist",List.of(""),()-> "",(e)-> e instanceof String);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> MOD_BLACKLIST = BUILDER
            .translation("")
            .defineList("modBlacklist",List.of(""),()-> "",(e)-> e instanceof String);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static List<? extends Integer> getMilestones(){ return MILESTONES.get(); }

    public static int getMinimalHungerValue(){ return MINIMAL_HUNGER_VALUE.getAsInt(); }

    public static int getMinimalNutrition(){ return MINIMAL_NUTRITION.getAsInt(); }

    public static int getMinimalFoodTypesToGenerateWishes() { return MINIMAL_FOOD_TYPES_TO_GENERATE_WISHES.getAsInt(); }

    public static List<? extends String> getFoodBlacklist(){ return FOOD_BLACKLIST.get(); }

    public static List<? extends String> getFoodWhitelist(){ return FOOD_WHITELIST.get(); }

    public static List<? extends String> getModBlacklist(){ return MOD_BLACKLIST.get(); }

    public static int getHPPerMilestone() { return HP_PER_MILESTONE.getAsInt(); }

    public static int getArmorPerMilestone() { return ARMOR_PER_MILESTONE.getAsInt(); }

    public static int getArmorToughnessPerMilestone() { return ARMOR_TOUGHNESS_PER_MILESTONE.getAsInt(); }

    public static int getAttackDamagePerMilestone() { return ATTACK_DAMAGE_PER_MILESTONE.getAsInt(); }

    public static int getWishBuffAddValuePercent() { return WISH_BUFF_ADD_VALUE_PERCENT.getAsInt(); }

    public static int getFavorabilityDailyWishAchieved() { return FAVORABILITY_INCREASED_DAYLY_WISH_ACHIEVED.getAsInt(); }

    public static int getFavorabilityMilestoneReached() { return FAVORABILITY_INCREASED_MILESTONE_REACHED.getAsInt(); }
}
