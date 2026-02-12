package com.mastermarisa.solmaiddream.network;

import com.mastermarisa.solmaiddream.capability.FoodListCapability;
import com.mastermarisa.solmaiddream.data.FoodList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Supplier;

public class FoodListSyncPacket {
    private final int entityId;
    private final HashSet<String> foods;
    private final ArrayList<String> currentWishes;
    private final int wishesAchieved;
    private final int wishesCycleCount;
    private final int wishesAchievedInCycle;
    private final double totalFoodValue;
    private final int reachedMilestone;

    public FoodListSyncPacket(int entityId, FoodList foodList) {
        this.entityId = entityId;
        this.foods = new HashSet<>(foodList.getFoods());
        this.currentWishes = new ArrayList<>(foodList.getCurrentWishes());
        this.wishesAchieved = foodList.getWishesAchieved();
        this.wishesCycleCount = foodList.getWishesCycleCount();
        this.wishesAchievedInCycle = foodList.getWishesAchievedInCycle();
        this.totalFoodValue = foodList.getTotalFoodValue();
        this.reachedMilestone = foodList.getReachedMilestone();
    }

    public FoodListSyncPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.foods = new HashSet<>(buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf));
        this.currentWishes = buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf);
        this.wishesAchieved = buf.readInt();
        this.wishesCycleCount = buf.readInt();
        this.wishesAchievedInCycle = buf.readInt();
        this.totalFoodValue = buf.readDouble();
        this.reachedMilestone = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeCollection(this.foods, FriendlyByteBuf::writeUtf);
        buf.writeCollection(this.currentWishes, FriendlyByteBuf::writeUtf);
        buf.writeInt(this.wishesAchieved);
        buf.writeInt(this.wishesCycleCount);
        buf.writeInt(this.wishesAchievedInCycle);
        buf.writeDouble(this.totalFoodValue);
        buf.writeInt(this.reachedMilestone);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> handleClient());
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private void handleClient() {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity != null) {
            entity.getCapability(FoodListCapability.FOOD_LIST).ifPresent(foodList -> {
                foodList.setFoods(foods);
                foodList.setCurrentWishes(currentWishes);
                foodList.setWishesAchieved(wishesAchieved);
                foodList.setWishesCycleCount(wishesCycleCount);
                foodList.setWishesAchievedInCycle(wishesAchievedInCycle);
                foodList.setTotalFoodValue(totalFoodValue);
                foodList.setReachedMilestone(reachedMilestone);
            });
        }
    }
}
