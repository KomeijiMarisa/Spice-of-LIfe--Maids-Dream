package com.mastermarisa.solmaiddream.registry;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.render.ui.FoodListScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FoodBookItem extends Item {
    public FoodBookItem() {
        super(new Properties());
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        setCustomName(pStack);
    }

    private static void setCustomName(ItemStack stack) {
        // 获取或创建一个 NBT Tag
        CompoundTag displayTag = stack.getOrCreateTagElement("display");

        // 设置自定义名称
        // Component 必须先转换为 JSON 字符串才能存入 NBT
        String nameJson = Component.Serializer.toJson(
                Component.translatable("item.solmaiddream.food_book")
                        .withStyle(ChatFormatting.GOLD)
        );
        displayTag.putString("Name", nameJson);
    }
}
