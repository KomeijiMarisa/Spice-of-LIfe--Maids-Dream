package com.mastermarisa.solmaiddream.utils;

import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleManager;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.implement.ImageChatBubbleData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.render.MaidWishChatBubbleData;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class MaidWishHandler {
    public static void onDayChanged(List<EntityMaid> maids){
        for (EntityMaid maid : maids){
            handleExistWish(maid);
            tryGenerateWish(maid);
        }
    }

    public static void tryGenerateWish(EntityMaid maid){
        FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
        if (foodList.getFoods().size() >= ModServerConfig.getMinimalFoodTypesToGenerateWishes()){
            List<String> wishes = getCurrentWishes(maid);
            List<String> foods = new ArrayList<>(foodList.getFoods().stream().toList());
            Collections.shuffle(foods);
            wishes.clear();
            for (int i = 0;i < 3;i++){
                wishes.add(foods.get(i));
            }
            foodList.setCurrentWishes(wishes);
            maid.setData(ModAttachmentTypes.FOOD_LIST,foodList);
        }
    }

    public static void handleExistWish(EntityMaid maid){
        FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
        if (foodList.getFoods().size() < ModServerConfig.getMinimalFoodTypesToGenerateWishes()) return;
        foodList.setWishesCycleCount(foodList.getWishesCycleCount() + 1);
        if(foodList.getWishesCycleCount() >= 3){
            foodList.setWishesCycleCount(0);
            handleCycle(maid,foodList);
        }
        maid.setData(ModAttachmentTypes.FOOD_LIST,foodList);
    }

    public static List<String> getCurrentWishes(EntityMaid maid){
        FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);

        return foodList.getCurrentWishes();
    }

    private static void handleCycle(EntityMaid maid,FoodList foodList){
        switch (foodList.getWishesAchievedInCycle()){
            case 0:
                foodList.setWishesAchieved(0);
                break;
            case 1:
                foodList.setWishesAchieved(foodList.getWishesAchieved() / 2);
                break;
            case 2:
                foodList.setWishesAchieved(foodList.getWishesAchieved() + 1);
                break;
            case 3:
                foodList.setWishesAchieved(foodList.getWishesAchieved() + 2);
                break;
        }
        trySendHandleCycleResult(maid,foodList.getWishesAchievedInCycle());
        foodList.setWishesAchievedInCycle(0);
        AttributeHandler.updateModifiers(maid);
        tryUpdateMaxWishBuff(maid,foodList);
    }

    private static void trySendHandleCycleResult(EntityMaid maid,int achieved){
        LivingEntity player = maid.getOwner();
        if (player instanceof ServerPlayer serverPlayer){
            switch (achieved){
                case 0:
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.to_zero_chat").withStyle(ChatFormatting.DARK_AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.to_zero").withStyle(ChatFormatting.DARK_AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                    break;
                case 1:
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.reduce_by_half_chat").withStyle(ChatFormatting.AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.reduce_by_half").withStyle(ChatFormatting.DARK_AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                    break;
                case 2:
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_one_chat").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_one").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    break;
                case 3:
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_two_chat").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_two").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    break;
            }
        }
    }

    private static void tryUpdateMaxWishBuff(EntityMaid maid,FoodList foodList){
        MaidInfo info = maid.getData(ModAttachmentTypes.MAID_INFO);
        info.maxWishBuffCount = Math.max(info.maxWishBuffCount,foodList.getWishesAchieved());
        maid.setData(ModAttachmentTypes.MAID_INFO,info);
    }

    public static boolean isInWishes(EntityMaid maid, ItemStack stack){
        String id = ModUtils.encodeItem(stack.getItem());
        return getCurrentWishes(maid).contains(id);
    }

    public static void addWishesAchieved(EntityMaid maid){
        FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
        foodList.setWishesAchievedInCycle(foodList.getWishesAchievedInCycle() + 1);
        getCurrentWishes(maid).clear();
        maid.setData(ModAttachmentTypes.FOOD_LIST,foodList);
    }

    public static int getWishesAchieved(EntityMaid maid){
        FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
        return foodList.getWishesAchieved();
    }

    public static void tryRenderWishes(EntityMaid maid){
        if (!maid.level().isClientSide()) return;
        if (getCurrentWishes(maid).isEmpty()) return;
        ChatBubbleManager bubbleManager = maid.getChatBubbleManager();
        boolean empty = bubbleManager.getChatBubbleDataCollection().isEmpty();
        if (!empty) return;

        bubbleManager.addChatBubble(new MaidWishChatBubbleData(getCurrentWishes(maid).stream().map(ResourceLocation::parse).toList()));
    }
}
