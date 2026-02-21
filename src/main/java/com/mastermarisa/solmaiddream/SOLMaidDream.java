package com.mastermarisa.solmaiddream;

import com.mastermarisa.solmaiddream.init.InitCompats;
import com.mastermarisa.solmaiddream.init.InitConfig;
import com.mastermarisa.solmaiddream.init.InitEntities;
import com.mastermarisa.solmaiddream.init.InitItems;
import net.minecraft.resources.ResourceLocation;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(SOLMaidDream.MOD_ID)
public class SOLMaidDream {
    public static final String MOD_ID = "solmaiddream";

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public SOLMaidDream(IEventBus modEventBus, ModContainer modContainer) {
        InitEntities.register(modEventBus);
        InitItems.register(modEventBus);
        InitConfig.register(modContainer);
        InitCompats.register();
    }
}
