package com.mastermarisa.solmaiddream.init.registry;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.init.InitItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID)
public class CommonRegistry {
    @SubscribeEvent
    public static void addItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES){
            event.accept(InitItems.FOOD_BOOK.get());
        }
    }
}
