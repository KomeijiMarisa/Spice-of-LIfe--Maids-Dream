package com.mastermarisa.solmaiddream.capability;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import com.mastermarisa.solmaiddream.network.MaidInfoSyncPacket;
import com.mastermarisa.solmaiddream.network.NetworkHandler;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MaidInfoCapability {
    public static final Capability<MaidInfo> MAID_INFO = CapabilityManager.get(new CapabilityToken<>() {});

    public static void sync(EntityMaid maid) {
        if (!maid.level().isClientSide()) {
            maid.getCapability(MAID_INFO).ifPresent(maidInfo -> {
                NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> maid), new MaidInfoSyncPacket(maid.getId(), maidInfo));
            });
        }
    }

    public static void sync(EntityMaid maid, ServerPlayer player) {
        if (!maid.level().isClientSide()) {
            maid.getCapability(MAID_INFO).ifPresent(maidInfo -> {
                NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new MaidInfoSyncPacket(maid.getId(), maidInfo));
            });
        }
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private final MaidInfo maidInfo = new MaidInfo();
        private final LazyOptional<MaidInfo> lazyOptional = LazyOptional.of(() -> maidInfo);

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == MAID_INFO) {
                return lazyOptional.cast();
            }
            return LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putLong("existTime", maidInfo.existTime);
            tag.putInt("achievedWishCount", maidInfo.achievedWishCount);
            tag.putInt("maxWishBuffCount", maidInfo.maxWishBuffCount);
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            maidInfo.existTime = tag.getLong("existTime");
            maidInfo.achievedWishCount = tag.getInt("achievedWishCount");
            maidInfo.maxWishBuffCount = tag.getInt("maxWishBuffCount");
        }
    }
}
