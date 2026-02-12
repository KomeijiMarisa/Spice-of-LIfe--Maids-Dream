package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.api.event.AddJadeInfoEvent;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.utils.FoodNutritionManager;
import com.mastermarisa.solmaiddream.utils.MaidWishHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import snownee.jade.api.ITooltip;

public class OnAddJadeInfoEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnAddJadeInfoEvent.class);
    public static final boolean JADE_LOADED = ModList.get().isLoaded("jade");

    @SubscribeEvent
    public static void onAddJadeInfoEvent(AddJadeInfoEvent event){
        EntityMaid maid = event.getMaid();
        ITooltip tooltip = event.getTooltip();
        FoodList foodList = ModAttachmentTypes.getFoodList(maid);
        tooltip.add(Component.translatable("jade.solmaiddream.tooltip.eaten_food_count").append(String.valueOf(foodList.getFoods().size())).withStyle(ChatFormatting.AQUA));
        Player player = (Player) event.getMaid().getOwner();
        if(player == null) return;
        ItemStack stack = player.getMainHandItem();
        if(stack.getItem().getFoodProperties(stack,player) != null){
            if (FoodNutritionManager.filter(stack,maid)){
                if(foodList.isFoodEaten(stack)){
                    tooltip.add(Component.translatable("jade.solmaiddream.tooltip.eaten_food").withStyle(ChatFormatting.GRAY));
                } else {
                    tooltip.add(Component.translatable("jade.solmaiddream.tooltip.untried_food").withStyle(ChatFormatting.AQUA));
                }
            } else {
                tooltip.add(Component.literal(FoodNutritionManager.getUncountableReason(stack,maid)).withStyle(ChatFormatting.GRAY));
            }
        }
        tooltip.add(Component.translatable("jade.solmaiddream.tooltip.wish_value").append(String.valueOf(MaidWishHandler.getWishesAchieved(maid))));
        tooltip.add(Component.translatable("jade.solmaiddream.tooltip.wish_value_cycle").append(foodList.getWishesCycleCount() + "/" + 3));
    }
}
