package com.mastermarisa.solmaiddream.event;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.utils.FilterHelper;
import com.mastermarisa.solmaiddream.utils.ItemHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID)
public class OnServerStart {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        HolderLookup.Provider provider = event.getServer().registryAccess();
        List<Item> foodList = new ArrayList<>();
        provider.lookup(Registries.ITEM).ifPresent(itemLookup -> {
            itemLookup.listElements().forEach(itemHolder -> {
                Item item = itemHolder.value();
                if (item.getFoodProperties(new ItemStack(item),null) != null){
                    foodList.add(item);
                }
            });
        });
        ItemHelper.allFoods = foodList.stream().map(ItemStack::new).filter(s -> FilterHelper.filter(s, null)).map(ItemStack::getItem).toList();
    }
}
