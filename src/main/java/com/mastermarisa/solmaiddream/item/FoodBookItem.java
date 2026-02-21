package com.mastermarisa.solmaiddream.item;

import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FoodBookItem extends Item {
    public FoodBookItem(){
        super(new Item.Properties().component(DataComponents.ITEM_NAME, Component.translatable("item.solmaiddream.food_book").withStyle(ChatFormatting.GOLD)));
    }

    @Override
    public int getDefaultMaxStackSize() {
        return 1;
    }
}