package com.mastermarisa.solmaiddream.compat.jade;

import com.github.tartaricacid.touhoulittlemaid.api.event.AddJadeInfoEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.data.FoodRecord;
import com.mastermarisa.solmaiddream.data.MaidWish;
import com.mastermarisa.solmaiddream.utils.FilterHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import snownee.jade.api.ITooltip;

public class OnAddJadeInfo {
    @SubscribeEvent
    public static void onAddJadeInfoEvent(AddJadeInfoEvent event){
        EntityMaid maid = event.getMaid();
        ITooltip tooltip = event.getTooltip();
        FoodRecord foodRecord = maid.getData(FoodRecord.TYPE);
        tooltip.add(Component.translatable("jade.solmaiddream.tooltip.eaten_food_count").append(String.valueOf(foodRecord.size())).withStyle(ChatFormatting.AQUA));
        Player player = (Player) event.getMaid().getOwner();
        if(player == null) return;
        ItemStack stack = player.getMainHandItem();
        if(stack.getItem().getFoodProperties(stack,player) != null){
            if (FilterHelper.filter(stack, maid)) {
                if(foodRecord.isFoodEaten(stack)){
                    tooltip.add(Component.translatable("jade.solmaiddream.tooltip.eaten_food").withStyle(ChatFormatting.GRAY));
                } else {
                    tooltip.add(Component.translatable("jade.solmaiddream.tooltip.untried_food").withStyle(ChatFormatting.AQUA));
                }
            } else {
                tooltip.add(Component.literal(FilterHelper.getInvalidReason(stack,maid)).withStyle(ChatFormatting.GRAY));
            }
        }
        MaidWish wish = maid.getData(MaidWish.TYPE);
        tooltip.add(Component.translatable("jade.solmaiddream.tooltip.wish_value").append(String.valueOf(wish.fulfillment)));
        tooltip.add(Component.translatable("jade.solmaiddream.tooltip.wish_value_cycle").append(wish.getCycleNum() + "/3"));
    }
}
