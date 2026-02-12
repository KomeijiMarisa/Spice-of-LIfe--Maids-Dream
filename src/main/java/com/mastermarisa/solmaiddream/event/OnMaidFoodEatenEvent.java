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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnMaidFoodEatenEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnMaidFoodEatenEvent.class);
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event){
        LivingEntity entity = event.getEntity();
        if(entity instanceof EntityMaid maid){
            FoodList foodList = ModAttachmentTypes.getFoodList(maid);
            foodList.addFoodByMaid(event.getItem(), maid);
            ModAttachmentTypes.setFoodList(maid, foodList);
            if (MaidWishHandler.isInWishes(maid,event.getItem())){
                MaidWishHandler.addWishesAchieved(maid);
                LivingEntity player = maid.getOwner();
                int favor = ModServerConfig.getFavorabilityDailyWishAchieved();
                maid.getFavorabilityManager().add(favor);
                MaidInfo info = ModAttachmentTypes.getMaidInfo(maid);
                info.achievedWishCount ++;
                ModAttachmentTypes.setMaidInfo(maid, info);
                if(player instanceof ServerPlayer serverPlayer){
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.event.daily_wish_chat").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    if (favor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.event.daily_wish",favor).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                }
            }
        }
    }
}
