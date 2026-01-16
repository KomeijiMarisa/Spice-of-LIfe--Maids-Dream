package com.mastermarisa.solmaiddream.registry;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleRegister;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.implement.*;
import com.google.common.collect.ImmutableMap;
import com.mastermarisa.solmaiddream.render.MaidWishChatBubbleData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

public final class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(CommonRegistry::init);
    }

    public static void init(){
        ChatBubbleRegister register = new ChatBubbleRegister();
        register.register(MaidWishChatBubbleData.ID, new MaidWishChatBubbleData.MaidWishChatSerializer());
    }
}
