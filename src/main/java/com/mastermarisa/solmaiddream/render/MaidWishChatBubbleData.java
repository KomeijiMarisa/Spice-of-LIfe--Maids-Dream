package com.mastermarisa.solmaiddream.render;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.implement.EmojiChatBubbleData;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.utils.ModUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class MaidWishChatBubbleData implements IChatBubbleData {
    public static final ResourceLocation ID = SOLMaidDream.resourceLocation("wish");
    private final ResourceLocation bg;
    private final List<ResourceLocation> foods;

    @OnlyIn(Dist.CLIENT)
    private IChatBubbleRenderer renderer;

    public MaidWishChatBubbleData(List<ResourceLocation> foods){
        this.foods = foods;
        this.bg = TYPE_2;
    }

    public int existTick() {
        return 100;
    }

    public ResourceLocation id() {
        return ID;
    }

    @OnlyIn(Dist.CLIENT)
    public IChatBubbleRenderer getRenderer(IChatBubbleRenderer.Position position) {
        if (this.renderer == null) {
            this.renderer = new MaidWishChatBubbleRenderer(16,16,this.bg,this.foods);
        }

        return this.renderer;
    }

    public static class MaidWishChatSerializer implements IChatBubbleData.ChatSerializer {
        public IChatBubbleData readFromBuff(FriendlyByteBuf buf) {
            List<ResourceLocation> foods = new ArrayList<>();
            for (int i = 0;i < 3;i++){
                foods.add(buf.readResourceLocation());
            }
            return new MaidWishChatBubbleData(foods);
        }

        public void writeToBuff(FriendlyByteBuf buf, IChatBubbleData data) {
            MaidWishChatBubbleData wishChat = (MaidWishChatBubbleData)data;
            for (ResourceLocation resourceLocation : wishChat.foods){
                buf.writeResourceLocation(resourceLocation);
            }
        }
    }
}
