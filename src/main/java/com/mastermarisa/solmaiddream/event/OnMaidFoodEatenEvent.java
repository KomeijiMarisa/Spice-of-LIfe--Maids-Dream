package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.utils.MaidWishHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

public class OnMaidFoodEatenEvent {
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event){
        LivingEntity entity = event.getEntity();
        if(entity instanceof EntityMaid maid){
            FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
            foodList.addFoodByMaid(event.getItem(),maid);
            maid.setData(ModAttachmentTypes.FOOD_LIST,foodList);
            if (MaidWishHandler.isInWishes(maid,event.getItem())){
                MaidWishHandler.addWishesAchieved(maid);
                LivingEntity player = maid.getOwner();
                int favor = ModServerConfig.FAVORABILITY_INCREASED_DAYLY_WISH_ACHIEVED.getAsInt();
                maid.getFavorabilityManager().add(favor);
                if(player instanceof ServerPlayer serverPlayer){
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("谢谢主人！我好高兴~").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    if (favor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("好感度+" + favor + "!").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                }
            }
        }
    }
}
