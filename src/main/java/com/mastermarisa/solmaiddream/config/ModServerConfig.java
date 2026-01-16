package com.mastermarisa.solmaiddream.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Objects;

public class ModServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> MILESTONES = BUILDER
            .translation("")
            .defineList("milestones",List.of(5,10,15,20,25,30,35,40,45,50),()-> 0,(e)-> e instanceof Integer);

    public static final ModConfigSpec.IntValue FAVORABILITY_INCREASED_DAYLY_WISH_ACHIEVED = BUILDER
            .translation("")
            .defineInRange("favorabilityIncreasedDailyWishAchieved",2,0,1000);

    public static final ModConfigSpec.IntValue FAVORABILITY_INCREASED_MILESTONE_REACHED = BUILDER
            .translation("")
            .defineInRange("favorabilityIncreasedMilestoneReached",10,0,1000);

    public static final ModConfigSpec.IntValue WISH_BUFF_ADD_VALUE_PERCENT = BUILDER
            .translation("")
            .defineInRange("wishBuffAddValuePercent",10,0,1000);

    public static final ModConfigSpec.IntValue HP_PER_MILESTONE = BUILDER
            .translation("")
            .defineInRange("hpPerMilestone",2,0,1000);

    public static final ModConfigSpec.IntValue ARMOR_PER_MILESTONE = BUILDER
            .translation("")
            .defineInRange("armorPerMilestone",1,0,1000);

    public static final ModConfigSpec.IntValue ATTACK_DAMAGE_PER_MILESTONE = BUILDER
            .translation("")
            .defineInRange("attackDamagePerMilestone",1,0,1000);

    public static final ModConfigSpec.IntValue ARMOR_TOUGHNESS_PER_MILESTONE = BUILDER
            .translation("")
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

    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue ITEMS_PER_LINE = CLIENT_BUILDER
            .translation("config.solmaid.client.item_per_line")
            .defineInRange("itemsPerLine", 10, 1, 1000);

    public static final ModConfigSpec CLIENT_SPEC = CLIENT_BUILDER.build();

    public static List<? extends Integer> getMilestones(){ return MILESTONES.get(); }

    public static int getMinimalHungerValue(){ return MINIMAL_HUNGER_VALUE.getAsInt(); }

    public static int getMinimalNutrition(){ return MINIMAL_NUTRITION.getAsInt(); }

    public static List<? extends String> getFoodBlacklist(){ return FOOD_BLACKLIST.get(); }

    public static List<? extends String> getFoodWhitelist(){ return FOOD_WHITELIST.get(); }

    public static List<? extends String> getModBlacklist(){ return MOD_BLACKLIST.get(); }
}
