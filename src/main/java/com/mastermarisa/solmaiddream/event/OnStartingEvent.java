package com.mastermarisa.solmaiddream.event;

import com.mastermarisa.solmaiddream.utils.FoodNutritionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.HolderLookup;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnStartingEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnStartingEvent.class);
//    @SubscribeEvent
//    public static void onServerStartingEvent(ServerStartingEvent event) {
//        MinecraftServer server = event.getServer();
//        HolderLookup.Provider provider = server.registryAccess();
//
//        FoodNutritionManager.setFoodsByComponent(provider);
//    }

//    @SubscribeEvent
//    public static void onClientPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
//        if (event.getEntity() == Minecraft.getInstance().player) {
//            HolderLookup.Provider lookupProvider = Minecraft.getInstance().getConnection().registryAccess();
//
//            FoodNutritionManager.setFoodsByComponent(lookupProvider);
//        }
//    }
    @SubscribeEvent
    public static void onClientPlayerLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener connection = mc.getConnection();

        if (connection != null) {
            HolderLookup.Provider lookupProvider = connection.registryAccess();
            FoodNutritionManager.setFoodsByComponent(lookupProvider);
        } else {
            LOGGER.warn("客户端玩家登录时，网络连接尚未完全建立。");
        }
    }
}
