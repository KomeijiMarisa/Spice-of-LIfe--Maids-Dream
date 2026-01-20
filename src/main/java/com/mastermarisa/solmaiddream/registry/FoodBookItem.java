package com.mastermarisa.solmaiddream.registry;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.render.ui.FoodListScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FoodBookItem extends Item {
    public FoodBookItem(){
        super(new Item.Properties()
                .component(DataComponents.ITEM_NAME,
                        Component.translatable("item.solmaiddream.food_book")
                                .withStyle(ChatFormatting.GOLD)));
    }

    @Override
    public int getDefaultMaxStackSize() {
        return 1;
    }
}
