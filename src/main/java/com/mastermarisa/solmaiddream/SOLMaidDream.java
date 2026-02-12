package com.mastermarisa.solmaiddream;

import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.event.*;
import com.mastermarisa.solmaiddream.registry.CommonRegistry;
import com.mastermarisa.solmaiddream.registry.InitItems;
import com.mastermarisa.solmaiddream.utils.DayChangeListener;
import com.mastermarisa.solmaiddream.utils.MaidTracker;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

@Mod(SOLMaidDream.MOD_ID)
public class SOLMaidDream {
    public static final String MOD_ID = "solmaiddream";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public SOLMaidDream(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(CommonRegistry::onCommonSetup);
        modEventBus.addListener(CommonRegistry::onClientSetup);

        InitItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(OnMaidFoodEatenEvent.class);
        MinecraftForge.EVENT_BUS.register(OnMaidJoinWorldEvent.class);
        MinecraftForge.EVENT_BUS.register(DayChangeListener.class);
        MinecraftForge.EVENT_BUS.register(OnDayChangedEvent.class);
        MinecraftForge.EVENT_BUS.register(MaidTracker.class);
        MinecraftForge.EVENT_BUS.register(OnPlayerInteractMaidEvent.class);
        MinecraftForge.EVENT_BUS.register(OnPlayerOpenFoodListScreenEvent.class);
        if (OnAddJadeInfoEvent.JADE_LOADED){
            MinecraftForge.EVENT_BUS.register(OnAddJadeInfoEvent.class);
        }

        context.registerConfig(ModConfig.Type.SERVER, ModServerConfig.SPEC);
    }
}
