package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleManager;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.MaidWish;
import com.mastermarisa.solmaiddream.init.InitItems;
import com.mastermarisa.solmaiddream.render.MaidWishChatBubbleData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID)
public class OnCheckWishes {
    @SubscribeEvent
    public static void onInteractMaid(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        if (event.getTarget() instanceof EntityMaid maid) {
            if (!event.getItemStack().is(Items.BOWL)) return;

            MaidWish wish = maid.getData(MaidWish.TYPE);
            if (wish.toList().isEmpty()) return;

            ChatBubbleManager bubbleManager = maid.getChatBubbleManager();
            if (!bubbleManager.getChatBubbleDataCollection().isEmpty()) return;

            if (player.level().isClientSide()) {
                bubbleManager.addChatBubble(new MaidWishChatBubbleData(wish.toList().stream().map(ResourceLocation::parse).toList()));
            }

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
