package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLivingEvent;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID,value = Dist.CLIENT)
public class EntityRenderHandler {

    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Post<LivingEntity, ? extends EntityModel<LivingEntity>> event) {
//        LivingEntity entity = event.getEntity();
//        PoseStack poseStack = event.getPoseStack();
//        MultiBufferSource bufferSource = event.getMultiBufferSource();
//        int packedLight = event.getPackedLight();
//        float partialTick = event.getPartialTick();
//
//        // 检查是否为目标实体
//        if (!(entity instanceof EntityMaid)) return; // 示例：只渲染玩家
//
//        // 在实体上方渲染物品
//        renderItemAboveHead((EntityMaid)entity, poseStack, bufferSource, packedLight, partialTick);
    }

    private static void renderItemAboveHead(EntityMaid maid, PoseStack poseStack,
                                            MultiBufferSource bufferSource,
                                            int packedLight, float partialTick) {

        ItemStack itemStack = maid.getMainHandItem();
        if (itemStack.isEmpty()) return;

        poseStack.pushPose();

        // 调整到头顶位置
        float height = maid.getBbHeight() + 0.5F;
        poseStack.translate(0.0D, height, 0.0D);

        // 让物品随着时间旋转
//        float rotation = (player.tickCount + partialTick) / 20.0F * 180.0F;
//        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation));

        // 上下浮动效果
        float bounce = Mth.sin((maid.tickCount + partialTick) / 10.0F) * 0.1F;
        poseStack.translate(0.0D, bounce, 0.0D);

        // 获取渲染器和模型
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel bakedModel = itemRenderer.getModel(
                itemStack,
                maid.level(),
                maid,
                maid.getId()
        );

        // 调整大小
        float scale = 0.25F;
        poseStack.scale(scale, scale, scale);

        // 渲染物品
        itemRenderer.render(
                itemStack,
                ItemDisplayContext.FIXED, // 固定显示上下文
                false,
                poseStack,
                bufferSource,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                bakedModel
        );

        poseStack.popPose();
    }
}
