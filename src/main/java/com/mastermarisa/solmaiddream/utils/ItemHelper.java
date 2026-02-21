package com.mastermarisa.solmaiddream.utils;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID, value = Dist.CLIENT)
public class ItemHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemHelper.class);

    @SubscribeEvent
    public static void onPlayerJoinWorld(ClientPlayerNetworkEvent.LoggingIn event){
        Minecraft mc = Minecraft.getInstance();
        ClientPacketListener connection = mc.getConnection();

        if (connection != null) {
            HolderLookup.Provider provider = connection.registryAccess();
            List<Item> foodList = new ArrayList<>();
            provider.lookup(Registries.ITEM).ifPresent(itemLookup -> {
                itemLookup.listElements().forEach(itemHolder -> {
                    Item item = itemHolder.value();
                    if (item.getFoodProperties(new ItemStack(item),null) != null){
                        foodList.add(item);
                    }
                });
            });
            allFoods = foodList.stream().map(ItemStack::new).filter(s -> FilterHelper.filter(s, null)).map(ItemStack::getItem).toList();
        } else {
             LOGGER.warn("客户端玩家登录时，网络连接尚未完全建立。");
        }
    }

    public static List<Item> allFoods = new ArrayList<>();
}
