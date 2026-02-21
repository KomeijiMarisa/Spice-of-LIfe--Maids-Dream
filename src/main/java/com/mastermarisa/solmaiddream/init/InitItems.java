package com.mastermarisa.solmaiddream.init;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.item.FoodBookItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SOLMaidDream.MOD_ID);

    public static final DeferredItem<Item> FOOD_BOOK = ITEMS.register("food_book", FoodBookItem::new);

    public static void register(IEventBus mod) {
        ITEMS.register(mod);
    }
}
