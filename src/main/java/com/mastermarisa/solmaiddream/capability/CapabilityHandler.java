package com.mastermarisa.solmaiddream.capability;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod.EventBusSubscriber(modid = SOLMaidDream.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CapabilityHandler.class);
    public static final ResourceLocation FOOD_LIST_CAP = ResourceLocation.fromNamespaceAndPath(SOLMaidDream.MOD_ID, "food_list");
    public static final ResourceLocation MAID_INFO_CAP = ResourceLocation.fromNamespaceAndPath(SOLMaidDream.MOD_ID, "maid_info");

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(FoodList.class);
        event.register(MaidInfo.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityMaid) {
            event.addCapability(FOOD_LIST_CAP, new FoodListCapability.Provider());
            event.addCapability(MAID_INFO_CAP, new MaidInfoCapability.Provider());
        }
    }

    @SubscribeEvent
    public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
        if (!event.getEntity().level().isClientSide() && event.getTarget() instanceof EntityMaid maid) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            // 当玩家开始追踪实体时同步，之后的更改会自动同步到真正追踪该实体的玩家
            // 在对应类的markDirty方法下
            FoodListCapability.sync(maid, player);
            MaidInfoCapability.sync(maid, player);
        }
    }
}
