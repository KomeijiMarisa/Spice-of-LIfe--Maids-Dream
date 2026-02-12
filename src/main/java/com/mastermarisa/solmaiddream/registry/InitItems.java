package com.mastermarisa.solmaiddream.registry;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class InitItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SOLMaidDream.MOD_ID);

    public static final RegistryObject<Item> FOOD_BOOK = ITEMS.register("food_book", FoodBookItem::new);
}
