package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.AddJadeInfoEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.utils.MaidWishHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import snownee.jade.api.ITooltip;

public class OnAddJadeInfoEvent {
    public static final boolean JADE_LOADED = ModList.get().isLoaded("jade");

    @SubscribeEvent
    public static void onAddJadeInfoEvent(AddJadeInfoEvent event){
        EntityMaid maid = event.getMaid();
        ITooltip tooltip = event.getTooltip();
        FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
        tooltip.add(Component.literal("吃过的食物：" + foodList.getFoods().size()).withStyle(ChatFormatting.AQUA));
        Player player = (Player) event.getMaid().getOwner();
        if(player == null) return;
        ItemStack stack = player.getMainHandItem();
        if(stack.getItem().getFoodProperties(stack,player) != null){
            if(foodList.isFoodEaten(stack)){
                tooltip.add(Component.literal("已食用：用于提升属性").withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.literal("还没尝过呢! 她可能会喜欢的，你觉得呢？").withStyle(ChatFormatting.AQUA));
            }
        }
        tooltip.add(Component.literal("愿望值：" + MaidWishHandler.getWishesAchieved(maid)));
        tooltip.add(Component.literal("愿望值结算周期：" + foodList.getWishesCycleCount() + "/" + 3));
    }
}
