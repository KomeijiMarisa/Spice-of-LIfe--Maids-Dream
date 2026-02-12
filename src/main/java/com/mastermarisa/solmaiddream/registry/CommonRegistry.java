package com.mastermarisa.solmaiddream.registry;

import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleRegister;
import com.mastermarisa.solmaiddream.event.OnStartingEvent;
import com.mastermarisa.solmaiddream.network.NetworkHandler;
import com.mastermarisa.solmaiddream.render.MaidWishChatBubbleData;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;

public final class CommonRegistry {
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(CommonRegistry::init);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MinecraftForge.EVENT_BUS.register(OnStartingEvent.class);
        });
    }

    public static void init(){
        NetworkHandler.register();
    }

    @SubscribeEvent
    public static void addItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept((ItemLike) InitItems.FOOD_BOOK.get());
        }
    }
}
