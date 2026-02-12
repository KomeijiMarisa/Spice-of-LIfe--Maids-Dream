package com.mastermarisa.solmaiddream.network;

import com.mastermarisa.solmaiddream.capability.MaidInfoCapability;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MaidInfoSyncPacket {
    private final int entityId;
    private final long existTime;
    private final int achievedWishCount;
    private final int maxWishBuffCount;

    public MaidInfoSyncPacket(int entityId, MaidInfo maidInfo) {
        this.entityId = entityId;
        this.existTime = maidInfo.existTime;
        this.achievedWishCount = maidInfo.achievedWishCount;
        this.maxWishBuffCount = maidInfo.maxWishBuffCount;
    }

    public MaidInfoSyncPacket(FriendlyByteBuf buf) {
        this.entityId = buf.readInt();
        this.existTime = buf.readLong();
        this.achievedWishCount = buf.readInt();
        this.maxWishBuffCount = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeLong(this.existTime);
        buf.writeInt(this.achievedWishCount);
        buf.writeInt(this.maxWishBuffCount);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> handleClient());
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private void handleClient() {
        Entity entity = Minecraft.getInstance().level.getEntity(entityId);
        if (entity != null) {
            entity.getCapability(MaidInfoCapability.MAID_INFO).ifPresent(maidInfo -> {
                maidInfo.existTime = existTime;
                maidInfo.achievedWishCount = achievedWishCount;
                maidInfo.maxWishBuffCount = maxWishBuffCount;
            });
        }
    }
}
