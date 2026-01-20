package com.mastermarisa.solmaiddream.data;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.utils.AttributeHandler;
import com.mastermarisa.solmaiddream.utils.FoodNutritionManager;
import com.mastermarisa.solmaiddream.utils.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.INBTSerializable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class FoodList implements INBTSerializable<CompoundTag> {
    private Set<String> foods = new HashSet<String>();
    private List<String> currentWishes = new ArrayList<>();
    private int wishesAchieved = 0;
    private int wishesCycleCount = 0;
    private int wishesAchievedInCycle = 0;
    private double totalFoodValue = 0d;
    private int reachedMilestone = -1;

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        ModUtils.writeStringSet(tag,"foodlist",foods);
        ModUtils.writeStringList(tag,"currentWishes",currentWishes);
        tag.putInt("wish_count",wishesAchieved);
        tag.putInt("wish_cycle",wishesCycleCount);
        tag.putInt("wish_count_cycle",wishesAchievedInCycle);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        foods = ModUtils.readStringSet(tag, "foodlist");
        currentWishes = ModUtils.readStringList(tag,"currentWishes");
        wishesAchieved = tag.getInt("wish_count");
        wishesCycleCount = tag.getInt("wish_cycle");
        wishesAchievedInCycle = tag.getInt("wish_count_cycle");
    }

    public Set<String> getFoods() { return this.foods; }
    public void setFoods(Set<String> foods) { this.foods = foods; }
    public double getTotalFoodValue() { return this.totalFoodValue; }
    public void setTotalFoodValue(double totalFoodValue) { this.totalFoodValue = totalFoodValue; }
    public int getReachedMilestone() { return this.reachedMilestone; }
    public void setReachedMilestone(int reachedMilestone) { this.reachedMilestone = reachedMilestone; }
    public int getWishesAchieved() { return wishesAchieved; }
    public void setWishesAchieved(int wishesAchieved) { this.wishesAchieved = wishesAchieved; }
    public int getWishesCycleCount() { return wishesCycleCount; }
    public void setWishesCycleCount(int wishesCycleCount) { this.wishesCycleCount = wishesCycleCount; }
    public int getWishesAchievedInCycle() { return wishesAchievedInCycle; }
    public void setWishesAchievedInCycle(int wishesAchievedInCycle) { this.wishesAchievedInCycle = wishesAchievedInCycle; }
    public List<String> getCurrentWishes() { return currentWishes; }
    public void setCurrentWishes(List<String> currentWishes) { this.currentWishes = currentWishes; }

    public void init(){
        foods = foods.stream().filter((Predicate<? super String>) ModUtils::canDecodeItem).collect(Collectors.toSet());
    }

    public List<Item> getFoodItems(){
        return foods.stream().map(ModUtils::decodeItem).toList();
    }

    public void refreshTotalFoodValue(){
        totalFoodValue = 0;
        for (String str : foods){
            totalFoodValue += FoodNutritionManager.getFoodValue(ModUtils.decodeItem(str));
        }
    }

    public void refreshReachedMilestone(){
        List<? extends Integer> milestones = ModServerConfig.getMilestones();
        boolean flag = false;
        for (int i = 0;i < milestones.size();i++){
            if (getTotalFoodValue() < milestones.get(i)){
                setReachedMilestone(i - 1);
                flag = true;
                break;
            }
        }

        if (!flag){
            setReachedMilestone(milestones.size() - 1);
        }
    }

    public void addFoodByMaid(ItemStack stack, EntityMaid maid){
        if (FoodNutritionManager.filter(stack,maid)){
            int pre = foods.size();
            foods.add(ModUtils.encodeItem(stack.getItem()));
            if (foods.size() != pre) tryUpdateMilestone(stack,maid);
        }
    }

    public void tryUpdateMilestone(ItemStack stack, EntityMaid maid){
        totalFoodValue += FoodNutritionManager.getFoodValue(stack);
        List<? extends Integer> milestones = ModServerConfig.getMilestones();
        if (getReachedMilestone() + 1 < milestones.size() && getTotalFoodValue() >= milestones.get(getReachedMilestone() + 1)){
            this.reachedMilestone++;
            AttributeHandler.updateModifiers(maid);
            int favor = ModServerConfig.getFavorabilityMilestoneReached();
            maid.getFavorabilityManager().add(favor);
            LivingEntity player = maid.getOwner();
            if (player instanceof ServerPlayer serverPlayer){
                serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("真是独特的味道...啊，谢谢主人~").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                if (favor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("好感度+" + favor + "!").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int HP = ModServerConfig.getHPPerMilestone();
                if (HP != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("生命值+" + HP + "!").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int armor = ModServerConfig.getArmorPerMilestone();
                if (armor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("护甲值+" + armor + "!").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int armor_toughness = ModServerConfig.getArmorToughnessPerMilestone();
                if (armor_toughness != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("护甲韧性值+" + armor_toughness + "!").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int attack_damage = ModServerConfig.getAttackDamagePerMilestone();
                if (attack_damage != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.literal("攻击伤害+" + attack_damage + "!").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
            }
        }
    }

    public boolean isFoodEaten(ItemStack stack){
        return foods.contains(ModUtils.encodeItem(stack.getItem()));
    }
}
