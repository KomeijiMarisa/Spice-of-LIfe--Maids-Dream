package com.mastermarisa.solmaiddream.data;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.capability.FoodListCapability;
import com.mastermarisa.solmaiddream.capability.MaidInfoCapability;
import net.minecraftforge.common.util.LazyOptional;

public class ModAttachmentTypes {
    public static FoodList getFoodList(EntityMaid maid) {
        LazyOptional<FoodList> cap = maid.getCapability(FoodListCapability.FOOD_LIST);
        return cap.orElseThrow(() -> new IllegalStateException("女仆不存在 FoodList 能力" + maid.getName()));
    }

    /**
     * 设置并同步 FoodList
     * @param maid 女仆对象
     * @param foodList FoodList
     */
    public static void setFoodList(EntityMaid maid, FoodList foodList) {
        LazyOptional<FoodList> cap = maid.getCapability(FoodListCapability.FOOD_LIST);
        cap.ifPresent(f -> {
            f.setFoods(foodList.getFoods());
            f.setCurrentWishes(foodList.getCurrentWishes());
            f.setWishesAchieved(foodList.getWishesAchieved());
            f.setWishesCycleCount(foodList.getWishesCycleCount());
            f.setWishesAchievedInCycle(foodList.getWishesAchievedInCycle());
            f.setTotalFoodValue(foodList.getTotalFoodValue());
            f.setReachedMilestone(foodList.getReachedMilestone());
            f.markDirty(maid);
        });
    }

    public static MaidInfo getMaidInfo(EntityMaid maid) {
        LazyOptional<MaidInfo> cap = maid.getCapability(MaidInfoCapability.MAID_INFO);
        return cap.orElse(new MaidInfo());
    }

    public static void setMaidInfo(EntityMaid maid, MaidInfo maidInfo) {
        LazyOptional<MaidInfo> cap = maid.getCapability(MaidInfoCapability.MAID_INFO);
        cap.ifPresent(m -> {
            m.existTime = maidInfo.existTime;
            m.achievedWishCount = maidInfo.achievedWishCount;
            m.maxWishBuffCount = maidInfo.maxWishBuffCount;
            m.markDirty(maid);
        });
    }
}
