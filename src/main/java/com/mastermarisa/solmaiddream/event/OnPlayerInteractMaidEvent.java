package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.api.task.meal.MaidMealType;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.ChatBubbleManager;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.IChatBubbleData;
import com.github.tartaricacid.touhoulittlemaid.entity.chatbubble.implement.ImageChatBubbleData;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.github.tartaricacid.touhoulittlemaid.network.NetworkHandler;
import com.github.tartaricacid.touhoulittlemaid.network.message.SpawnParticleMessage;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.registry.InitItems;
import com.mastermarisa.solmaiddream.render.ui.FoodListScreen;
import com.mastermarisa.solmaiddream.utils.MaidWishHandler;
import com.mastermarisa.solmaiddream.utils.ModUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnPlayerInteractMaidEvent {
    @SubscribeEvent
    public static void onPlayerInteractMaidEvent(PlayerInteractEvent.EntityInteract event){
        Player player = event.getEntity();
        Entity entity = event.getTarget();

        if(entity instanceof EntityMaid maid){
            if(!player.getMainHandItem().isEmpty()){
                ItemStack stack = player.getMainHandItem();
                FoodProperties foodProperties = stack.getFoodProperties(maid);
                if (foodProperties != null && !maid.isUsingItem()){
                    InteractionHand hand;
                    if (maid.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()){
                        hand = InteractionHand.MAIN_HAND;
                    } else if (maid.getItemInHand(InteractionHand.OFF_HAND).isEmpty()){
                        hand = InteractionHand.OFF_HAND;
                    } else {
                        return;
                    }
                    ItemStack food = stack.split(1);
                    maid.setItemInHand(hand,food);
                    maid.getMaidBauble().fireEvent((b, s) -> {
                        b.onMaidEat(maid, s, food, MaidMealType.HEAL_MEAL);
                        return false;
                    });
                    maid.startUsingItem(hand);
                    int nutrition = foodProperties.getNutrition();
                    float saturation = foodProperties.getSaturationModifier();
                    float total = (float)nutrition + saturation;
                    if ((float)maid.getRandom().nextInt(5) < total) {
                        float healCount = Math.max(total / 5.0F, 1.0F);
                        maid.heal(healCount);
                        NetworkHandler.sendToNearby(maid, new SpawnParticleMessage(maid.getId(), SpawnParticleMessage.Type.HEAL, stack.getUseDuration()));
                    }
                    event.setCanceled(true);
                } else if (stack.is(Items.BOWL)){
                    MaidWishHandler.tryRenderWishes(maid);
                    event.setCanceled(true);
                }
            }
        }
    }
}
