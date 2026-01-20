package com.mastermarisa.solmaiddream;

import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.event.*;
import com.mastermarisa.solmaiddream.registry.CommonRegistry;
import com.mastermarisa.solmaiddream.registry.InitItems;
import com.mastermarisa.solmaiddream.utils.DayChangeListener;
import com.mastermarisa.solmaiddream.utils.MaidTracker;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;

@Mod(SOLMaidDream.MOD_ID)
public class SOLMaidDream {
    public static final String MOD_ID = "solmaiddream";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public SOLMaidDream(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        ModAttachmentTypes.ATTACHMENT_TYPES.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);

        modEventBus.register(CommonRegistry.class);

        NeoForge.EVENT_BUS.register(OnMaidFoodEatenEvent.class);
        NeoForge.EVENT_BUS.register(OnMaidJoinWorldEvent.class);
        NeoForge.EVENT_BUS.register(DayChangeListener.class);
        NeoForge.EVENT_BUS.register(OnDayChangedEvent.class);
        NeoForge.EVENT_BUS.register(MaidTracker.class);
        NeoForge.EVENT_BUS.register(OnPlayerInteractMaidEvent.class);
        NeoForge.EVENT_BUS.register(OnServerStartingEvent.class);
        NeoForge.EVENT_BUS.register(OnPlayerOpenFoodListScreenEvent.class);
        if (OnAddJadeInfoEvent.JADE_LOADED){
            NeoForge.EVENT_BUS.register(OnAddJadeInfoEvent.class);
        }

        modContainer.registerConfig(ModConfig.Type.SERVER, ModServerConfig.SPEC);
    }
}
