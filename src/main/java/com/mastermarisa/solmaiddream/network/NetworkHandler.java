package com.mastermarisa.solmaiddream.network;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static byte CHANNEL_MESSAGE_ID = 1;
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(SOLMaidDream.MOD_ID, "main"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(FoodListSyncPacket.class, CHANNEL_MESSAGE_ID++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(FoodListSyncPacket::encode)
                .decoder(FoodListSyncPacket::new)
                .consumerMainThread(FoodListSyncPacket::handle)
                .add();

        INSTANCE.messageBuilder(MaidInfoSyncPacket.class, CHANNEL_MESSAGE_ID++, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(MaidInfoSyncPacket::encode)
                .decoder(MaidInfoSyncPacket::new)
                .consumerMainThread(MaidInfoSyncPacket::handle)
                .add();
    }
}
