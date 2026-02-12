package com.mastermarisa.solmaiddream.data;

import com.github.tartaricacid.touhoulittlemaid.entity.data.inner.AttackListData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.capability.FoodListCapability;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.utils.AttributeHandler;
import com.mastermarisa.solmaiddream.utils.FoodNutritionManager;
import com.mastermarisa.solmaiddream.utils.ModUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
public class FoodList {
//    public static final Codec<FoodList> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            Codec.STRING.listOf().xmap(HashSet::new, ArrayList::new)
//                    .fieldOf("foods").forGetter(FoodList::getFoods),
//            Codec.STRING.listOf()
//                    .fieldOf("currentWishes").forGetter(FoodList::getCurrentWishes),
//            Codec.INT.fieldOf("wishesAchieved").forGetter(FoodList::getWishesAchieved),
//            Codec.INT.fieldOf("wishesCycleCount").forGetter(FoodList::getWishesCycleCount),
//            Codec.INT.fieldOf("wishesAchievedInCycle").forGetter(FoodList::getWishesAchievedInCycle)
//    ).apply(instance, FoodList::new));
//
//    public FoodList() {
//
//    }

//    public FoodList(Set<String> preferredFoods, List<String> currentWishes, int wishesAchieved, int wishesCycleCount, int wishesAchievedInCycle) {
//        this.foods = new HashSet<>(preferredFoods);
//        this.currentWishes = new ArrayList<>(currentWishes);
//        this.wishesAchieved = wishesAchieved;
//        this.wishesCycleCount = wishesCycleCount;
//        this.wishesAchievedInCycle = wishesAchievedInCycle;
//    }

    /**
     * 食用过的食物
     */
    private Set<String> foods = new HashSet<String>();
    /**
     * 当前愿望
     */
    private List<String> currentWishes = new ArrayList<>();
    /**
     * 遂愿数量
     */
    private int wishesAchieved = 0;
    /**
     * 愿望值结算次数
     */
    private int wishesCycleCount = 0;
    /**
     * 一个周期里愿望完成次数
     */
    private int wishesAchievedInCycle = 0;

    /**
     * 可以触发属性增加的食物总数
     */
    private double totalFoodValue = 0d;
    /**
     * 完成的里程碑数量
     */
    private int reachedMilestone = -1;

    /**
     * 获取食用过的食物
     * @return 食用过的食物
     */
    public Set<String> getFoods() { return this.foods; }

    /**
     * 设置食用过的食物
     * @param foods 食用过的食物
     */
    public void setFoods(Set<String> foods) { this.foods = foods; }

    /**
     * 获取可以触发属性增加的食物总数
     * @return 食物总数
     */
    public double getTotalFoodValue() { return this.totalFoodValue; }
    /**
     * 设置可以触发属性增加的食物总数
     * @param totalFoodValue 食物总数
     */
    public void setTotalFoodValue(double totalFoodValue) { this.totalFoodValue = totalFoodValue; }

    /**
     * 获取完成的里程碑数量
     * @return 里程碑数量
     */
    public int getReachedMilestone() { return this.reachedMilestone; }

    /**
     * 设置完成的里程碑数量
     * @param reachedMilestone 里程碑数量
     */
    public void setReachedMilestone(int reachedMilestone) { this.reachedMilestone = reachedMilestone; }

    /**
     * 获取遂愿数量
     * @return 遂愿数量
     */
    public int getWishesAchieved() { return wishesAchieved; }

    /**
     * 设置遂愿数量
     * @param wishesAchieved 遂愿数量
     */
    public void setWishesAchieved(int wishesAchieved) { this.wishesAchieved = wishesAchieved; }

    /**
     * 获取愿望值结算次数
     * @return 愿望值结算次数
     */
    public int getWishesCycleCount() { return wishesCycleCount; }

    /**
     * 设置愿望值结算次数
     * @param wishesCycleCount 愿望值结算次数
     */
    public void setWishesCycleCount(int wishesCycleCount) { this.wishesCycleCount = wishesCycleCount; }

    /**
     * 获取一个周期里愿望完成次数
     * @return 愿望完成次数
     */
    public int getWishesAchievedInCycle() { return wishesAchievedInCycle; }
    /**
     * 设置一个周期里愿望完成次数
     * @param wishesAchievedInCycle 愿望完成次数
     */
    public void setWishesAchievedInCycle(int wishesAchievedInCycle) { this.wishesAchievedInCycle = wishesAchievedInCycle; }

    /**
     * 获取当前愿望
     * @return 愿望
     */
    public List<String> getCurrentWishes() { return currentWishes; }

    /**
     * 设置当前愿望
     * @param currentWishes 愿望
     */
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

    /**
     * 刷新里程碑
     */
    public void refreshReachedMilestone(){
        List<? extends Integer> milestones = ModServerConfig.getMilestones();
        boolean flag = false;
        for (int i = 0;i < milestones.size();i++){
            if (getTotalFoodValue() < milestones.get(i).intValue()){
                setReachedMilestone(i - 1);
                flag = true;
                break;
            }
        }

        if (!flag){
            setReachedMilestone(milestones.size() - 1);
        }
    }

    /**
     * 向食物列表添加食物
     * <p>
     * 你仍然需要 {@code ModAttachmentTypes.setFoodList(maid, foodList);} 对数据进行同步
     * @param stack 物品对象
     * @param maid 女仆对象
     */
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
                serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.milestone.reached").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                if (favor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.milestone.favorability", favor).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int HP = ModServerConfig.getHPPerMilestone();
                if (HP != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.milestone.hp", HP).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int armor = ModServerConfig.getArmorPerMilestone();
                if (armor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.milestone.armor", armor).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int armor_toughness = ModServerConfig.getArmorToughnessPerMilestone();
                if (armor_toughness != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.milestone.armor_toughness", armor_toughness).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                int attack_damage = ModServerConfig.getAttackDamagePerMilestone();
                if (attack_damage != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.milestone.attack_damage", attack_damage).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
            }
        }
    }

    public boolean isFoodEaten(ItemStack stack){
        return foods.contains(ModUtils.encodeItem(stack.getItem()));
    }

    public void markDirty(EntityMaid maid) {
        FoodListCapability.sync(maid);
    }
}
