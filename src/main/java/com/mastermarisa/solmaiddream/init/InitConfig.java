package com.mastermarisa.solmaiddream.init;

import com.google.common.collect.Lists;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class InitConfig {
    public static List<? extends Integer> MILESTONES() { return Server.MILESTONES.get(); }

    public static boolean ENABLE_WHITELIST() { return Server.ENABLE_WHITELIST.getAsBoolean(); }

    public static List<? extends String> WHITELIST() { return Server.WHITELIST.get(); }

    public static boolean ENABLE_BLACKLIST() { return Server.ENABLE_BLACKLIST.getAsBoolean(); }

    public static List<? extends String> BLACKLIST() { return Server.BLACKLIST.get(); }

    public static int HUNGER_VALUE() { return Server.HUNGER_VALUE.getAsInt(); }

    public static double SATURATION() { return Server.SATURATION.getAsDouble(); }

    public static int TYPES_FOR_WISH() { return Server.TYPES_FOR_WISH.getAsInt(); }

    public static int HP() { return Server.HP.getAsInt(); }

    public static int ARMOR() { return Server.ARMOR.getAsInt(); }

    public static int ARMOR_TOUGHNESS() { return Server.ARMOR_TOUGHNESS.getAsInt(); }

    public static int ATTACK_DAMAGE() { return Server.ATTACK_DAMAGE.getAsInt(); }

    public static int FAVORABILITY() { return Server.FAVORABILITY.getAsInt(); }

    public static int FAVORABILITY_WISH() { return Server.FAVORABILITY_WISH.getAsInt(); }

    private static class Server {
        private static final ModConfigSpec.Builder BUILDER;
        private static final ModConfigSpec SPEC;
        public static final ModConfigSpec.ConfigValue<List<? extends Integer>> MILESTONES;
        public static final ModConfigSpec.BooleanValue ENABLE_WHITELIST;
        public static final ModConfigSpec.ConfigValue<List<? extends String>> WHITELIST;
        public static final ModConfigSpec.BooleanValue ENABLE_BLACKLIST;
        public static final ModConfigSpec.ConfigValue<List<? extends String>> BLACKLIST;
        public static final ModConfigSpec.IntValue HUNGER_VALUE;
        public static final ModConfigSpec.DoubleValue SATURATION;
        public static final ModConfigSpec.IntValue TYPES_FOR_WISH;
        public static final ModConfigSpec.IntValue HP;
        public static final ModConfigSpec.IntValue ARMOR;
        public static final ModConfigSpec.IntValue ARMOR_TOUGHNESS;
        public static final ModConfigSpec.IntValue ATTACK_DAMAGE;
        public static final ModConfigSpec.IntValue FAVORABILITY;
        public static final ModConfigSpec.IntValue FAVORABILITY_WISH;

        public static void register(ModContainer modContainer) {
            modContainer.registerConfig(ModConfig.Type.SERVER, SPEC);
        }

        static {
            BUILDER = new ModConfigSpec.Builder();

            MILESTONES = BUILDER
                    .translation("config.solmaiddream.server.milestones")
                    .defineList("milestones", Lists.newArrayList(5,10,15,20,25,30,35,40,45,50), () -> 0, (e) -> e instanceof Integer);

            ENABLE_WHITELIST = BUILDER
                    .translation("config.solmaiddream.server.enable_whitelist")
                    .define("enable_whitelist", true);

            WHITELIST = BUILDER
                    .translation("config.solmaiddream.server.whitelist")
                    .defineList("whitelist", Lists.newArrayList(), () -> "", (e) -> e instanceof String);

            ENABLE_BLACKLIST = BUILDER
                    .translation("config.solmaiddream.server.enable_blacklist")
                    .define("enable_blacklist", true);

            BLACKLIST = BUILDER
                    .translation("config.solmaiddream.server.blacklist")
                    .defineList("blacklist", Lists.newArrayList("minecraft:rotten_flesh"), () -> "", (e) -> e instanceof String);

            HUNGER_VALUE = BUILDER.translation("config.solmaiddream.server.minimal_hunger_value")
                    .defineInRange("minimal_hunger_value", 4, 0, 20);

            SATURATION = BUILDER.translation("config.solmaiddream.server.minimal_saturation")
                    .defineInRange("minimal_saturation", 6D, 0D, 20D);

            TYPES_FOR_WISH = BUILDER.translation("config.solmaiddream.server.minimal_types_to_generate_wishes")
                    .defineInRange("minimal_types_to_generate_wishes", 20, 3,1000);

            HP = BUILDER.translation("config.solmaiddream.server.hearts_per_milestone")
                    .defineInRange("hp_per_mileStone", 4, 0, 1000);

            ARMOR = BUILDER.translation("config.solmaiddream.server.armor_per_milestone")
                    .defineInRange("armor_per_milestone", 1, 0, 1000);

            ARMOR_TOUGHNESS = BUILDER
                    .translation("config.solmaiddream.server.armor_toughness_per_milestone")
                    .defineInRange("armor_toughness_per_milestone",1,0,1000);

            ATTACK_DAMAGE = BUILDER
                    .translation("config.solmaiddream.server.attack_damage_per_milestone")
                    .defineInRange("attack_damage_per_milestone", 1, 0, 1000);

            FAVORABILITY = BUILDER
                    .translation("config.solmaiddream.server.favorability_per_milestone")
                    .defineInRange("favorability_per_milestone", 24, 0, 1000);

            FAVORABILITY_WISH = BUILDER
                    .translation("")
                    .defineInRange("favorability_wish_achieved", 4, 0, 1000);

            SPEC = BUILDER.build();
        }
    }

    public static void register(ModContainer modContainer) {
        Server.register(modContainer);
    }
}
