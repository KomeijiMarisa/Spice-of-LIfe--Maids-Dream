package com.mastermarisa.solmaiddream;

import com.github.tartaricacid.touhoulittlemaid.api.ILittleMaid;
import com.github.tartaricacid.touhoulittlemaid.api.LittleMaidExtension;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleRegister;
import com.mastermarisa.solmaiddream.render.MaidWishChatBubbleData;

@LittleMaidExtension
public class MaidPlugin implements ILittleMaid {
    @Override
    public void registerChatBubble(ChatBubbleRegister register) {
        register.register(MaidWishChatBubbleData.ID, new MaidWishChatBubbleData.MaidWishChatSerializer());
    }
}
