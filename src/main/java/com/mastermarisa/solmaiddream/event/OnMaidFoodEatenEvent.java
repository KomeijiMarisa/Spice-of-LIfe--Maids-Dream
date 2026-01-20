package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.MaidInfo;
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
                int favor = ModServerConfig.getFavorabilityDailyWishAchieved();
                maid.getFavorabilityManager().add(favor);
                MaidInfo info = maid.getData(ModAttachmentTypes.MAID_INFO);
                info.achievedWishCount ++;
                maid.setData(ModAttachmentTypes.MAID_INFO,info);
                if(player instanceof ServerPlayer serverPlayer){
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.event.daily_wish_chat").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    if (favor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.event.daily_wish",favor).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                }
            }
        }
    }
}
