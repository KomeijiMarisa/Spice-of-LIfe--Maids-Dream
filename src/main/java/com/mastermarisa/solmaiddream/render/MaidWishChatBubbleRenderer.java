package com.mastermarisa.solmaiddream.render;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.EntityGraphics;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

import static net.minecraft.client.model.TridentModel.TEXTURE;

public class MaidWishChatBubbleRenderer implements IChatBubbleRenderer {
    private final int width;
    private final int height;
    private final ResourceLocation bg;
    private final List<ResourceLocation> foods;

    public MaidWishChatBubbleRenderer(int width,int height,ResourceLocation bg,List<ResourceLocation> foods){
        this.width = width;
        this.height = height;
        this.bg = bg;
        this.foods = foods;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() { return (this.width + 5) * 3 + 5; }

    public void render(EntityMaidRenderer renderer, EntityGraphics graphics) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (int i = 0;i < 3;i++){
            int x1 = (i) * (width + 5) + 5;
            graphics.blit(foods.get(i),x1,0,0,0,this.width,this.height,this.width,this.height);
        }
    }

    public ResourceLocation getBackgroundTexture() {
        return this.bg;
    }
}
