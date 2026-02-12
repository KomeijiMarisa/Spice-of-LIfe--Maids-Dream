package com.mastermarisa.solmaiddream.render;

import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.EntityMaidRenderer;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.EntityGraphics;
import com.github.tartaricacid.touhoulittlemaid.client.renderer.entity.chatbubble.IChatBubbleRenderer;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.utils.ModUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.joml.*;

import java.util.List;

public class MaidWishChatBubbleRenderer implements IChatBubbleRenderer {
    private final int width;
    private final int height;
    private final ResourceLocation bg;
    private final List<ItemStack> foods;
    public static final ResourceLocation texture = SOLMaidDream.resourceLocation("textures/gui/food_book.png");

    public MaidWishChatBubbleRenderer(int width,int height,ResourceLocation bg,List<ResourceLocation> foods){
        this.width = width;
        this.height = height;
        this.bg = bg;
        this.foods = foods.stream().map((o)-> new ItemStack(BuiltInRegistries.ITEM.get(o))).toList();
    }

//    public int getHeight() {
//        return this.height + 24;
//    }
//
//    public int getWidth() { return (this.width + 5) * 3 + 5; }

//    public int getHeight() { return 57; }
//
//    public int getWidth() { return 58; }

    public int getHeight() { return 0; }

    public int getWidth() { return 0; }

    public void render(EntityMaidRenderer renderer, EntityGraphics graphics) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        Minecraft mc = Minecraft.getInstance();
        ItemRenderer itemRenderer = mc.getItemRenderer();
        PoseStack poseStack = graphics.getPoseStack();
        MultiBufferSource buffer = mc.renderBuffers().bufferSource();
//        int startX = 168;int width = 231;
//        int startY = 0;int height = 226;
        int packedLight = graphics.getPackedLight();

        int xOffset = 34;//29
        int yOffset = 40;//57

        graphics.blit(texture, -xOffset, -yOffset,57,58,168,0,231,226,400,256);

        if (isGui3D(foods.get(0),itemRenderer)) RenderItemStackUnLit3D(foods.get(0),itemRenderer,buffer,poseStack,11 - xOffset,17 - yOffset,12,packedLight);
        else RenderItemStackUnLit2D(foods.get(0),itemRenderer,buffer,poseStack,11 - xOffset,17 - yOffset,12,packedLight);//22,35

        if (isGui3D(foods.get(1),itemRenderer)) RenderItemStackUnLit3D(foods.get(1),itemRenderer,buffer,poseStack,28 - xOffset,13 - yOffset,12,packedLight);
        else RenderItemStackUnLit2D(foods.get(1),itemRenderer,buffer,poseStack,28 - xOffset,13 - yOffset,12,packedLight);//56,24

        if (isGui3D(foods.get(2),itemRenderer)) RenderItemStackUnLit3D(foods.get(2),itemRenderer,buffer,poseStack,46 - xOffset,14 - yOffset,12,packedLight);
        else RenderItemStackUnLit2D(foods.get(2),itemRenderer,buffer,poseStack,46 - xOffset,14 - yOffset,12,packedLight);//93,29
    }

    private Vector3f vec3ToVector3f(Vec3 vec3){
        return new Vector3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    private boolean isGui3D(ItemStack itemStack,ItemRenderer itemRenderer){
        return itemRenderer.getModel(itemStack,null,null,0).isGui3d();
    }

    private void RenderItemStackUnLit2D(ItemStack itemStack,ItemRenderer itemRenderer,MultiBufferSource buffer,PoseStack poseStack,int x,int y,float scale,int packedLight){
        boolean shader = false;
        if (ModUtils.OCULUS_LOADED){
            shader = ShaderStateHelper.shaderEnabled();
        }

        Vector3f shaderLightDirections$1 = null;
        Vector3f shaderLightDirections$2 = null;
        if (!shader){
            if (buffer instanceof MultiBufferSource.BufferSource bufferSource)
                bufferSource.endBatch();
            shaderLightDirections$1 = new Vector3f(RenderSystem.shaderLightDirections[0]);
            shaderLightDirections$2 = new Vector3f(RenderSystem.shaderLightDirections[1]);
        }

        poseStack.pushPose();

        poseStack.translate(x,y,-0.1f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180));
        poseStack.scale(scale,scale,0.1f);

        if (!shader){
            Vector3f vec = poseStack.last().pose().transformDirection(new Vector3f(0, 0, -1)).normalize();
            RenderSystem.setShaderLights(vec, vec);
            packedLight = LightTexture.FULL_BRIGHT;
        }

        itemRenderer.renderStatic(
                itemStack,
                ItemDisplayContext.FIXED,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                null,
                0
        );

        poseStack.popPose();

        if (!shader){
            if (buffer instanceof MultiBufferSource.BufferSource bufferSource)
                bufferSource.endBatch();
            RenderSystem.shaderLightDirections[0] = shaderLightDirections$1;
            RenderSystem.shaderLightDirections[1] = shaderLightDirections$2;
        }
    }

    private void RenderItemStackUnLit3D(ItemStack itemStack,ItemRenderer itemRenderer,MultiBufferSource buffer,PoseStack poseStack,int x,int y,float scale,int packedLight){
        boolean shader = false;
        if (ModUtils.OCULUS_LOADED){
            shader = ShaderStateHelper.shaderEnabled();
        }

        Vector3f shaderLightDirections$1 = null;
        Vector3f shaderLightDirections$2 = null;
        if (!shader){
            if (buffer instanceof MultiBufferSource.BufferSource bufferSource)
                bufferSource.endBatch();
            shaderLightDirections$1 = new Vector3f(RenderSystem.shaderLightDirections[0]);
            shaderLightDirections$2 = new Vector3f(RenderSystem.shaderLightDirections[1]);
        }

        BakedModel bakedModel = itemRenderer.getModel(itemStack,null,null,0);
        poseStack.pushPose();

        poseStack.translate(x,y,-0.1f);
//        poseStack.translate((x + 8),(y + 8),-0.1f);
        poseStack.scale(scale,-scale,-scale);
        poseStack.scale(1.0f, 1.0f, 0.01f);

        if (!shader){
            Vector3f vec = poseStack.last().pose().transformDirection(new Vector3f(0,0,1)).normalize();
            RenderSystem.setShaderLights(vec, vec);
            packedLight = LightTexture.FULL_BRIGHT;
        }

        itemRenderer.render(itemStack,ItemDisplayContext.GUI,false,poseStack,buffer,packedLight,OverlayTexture.NO_OVERLAY,bakedModel);

        poseStack.popPose();

        if (!shader){
            if (buffer instanceof MultiBufferSource.BufferSource bufferSource)
                bufferSource.endBatch();
            RenderSystem.shaderLightDirections[0] = shaderLightDirections$1;
            RenderSystem.shaderLightDirections[1] = shaderLightDirections$2;
        }
    }

    public ResourceLocation getBackgroundTexture() {
        return this.bg;
    }
}
