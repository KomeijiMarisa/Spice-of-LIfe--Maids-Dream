package com.mastermarisa.solmaiddream.capability;

import com.github.tartaricacid.touhoulittlemaid.entity.data.inner.AttackListData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.network.FoodListSyncPacket;
import com.mastermarisa.solmaiddream.network.NetworkHandler;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class FoodListCapability {
    public static final Capability<FoodList> FOOD_LIST = CapabilityManager.get(new CapabilityToken<>() {});

    public static void sync(EntityMaid maid) {
        if (!maid.level().isClientSide()) {
            maid.getCapability(FOOD_LIST).ifPresent(foodList -> {
                NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> maid), new FoodListSyncPacket(maid.getId(), foodList));
            });
        }
    }

    public static void sync(EntityMaid maid, ServerPlayer player) {
        if (!maid.level().isClientSide()) {
            maid.getCapability(FOOD_LIST).ifPresent(foodList -> {
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new FoodListSyncPacket(maid.getId(), foodList));
            });
        }
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private final FoodList foodList = new FoodList();
        private final LazyOptional<FoodList> lazyOptional = LazyOptional.of(() -> foodList);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == FOOD_LIST) {
                return lazyOptional.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            foodList.getFoods().forEach(s -> tag.putString("food_" + new ArrayList<>(foodList.getFoods()).indexOf(s), s));
            tag.putInt("food_count", foodList.getFoods().size());
            for (int i = 0; i < foodList.getCurrentWishes().size(); i++) {
                tag.putString("wish_" + i, foodList.getCurrentWishes().get(i));
            }
            tag.putInt("wish_count", foodList.getCurrentWishes().size());
            tag.putInt("wishes_achieved", foodList.getWishesAchieved());
            tag.putInt("wishes_cycle", foodList.getWishesCycleCount());
            tag.putInt("wishes_achieved_in_cycle", foodList.getWishesAchievedInCycle());
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            foodList.getFoods().clear();
            int foodCount = tag.getInt("food_count");
            for (int i = 0; i < foodCount; i++) {
                foodList.getFoods().add(tag.getString("food_" + i));
            }
            foodList.getCurrentWishes().clear();
            int wishCount = tag.getInt("wish_count");
            for (int i = 0; i < wishCount; i++) {
                foodList.getCurrentWishes().add(tag.getString("wish_" + i));
            }
            foodList.setWishesAchieved(tag.getInt("wishes_achieved"));
            foodList.setWishesCycleCount(tag.getInt("wishes_cycle"));
            foodList.setWishesAchievedInCycle(tag.getInt("wishes_achieved_in_cycle"));
        }
    }
}
