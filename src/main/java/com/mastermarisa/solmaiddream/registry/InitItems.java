package com.mastermarisa.solmaiddream.registry;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class InitItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SOLMaidDream.MOD_ID);

    public static final DeferredItem<Item> FOOD_BOOK = ITEMS.register("food_book",FoodBookItem::new);
}
