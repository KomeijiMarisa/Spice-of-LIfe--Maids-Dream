package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.init.InitItems;
import com.mastermarisa.solmaiddream.render.ui.FoodListScreen;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID)
public class OnOpenScreen {
    @SubscribeEvent
    public static void onPlayerInteractMaidEvent(PlayerInteractEvent.EntityInteract event){
        Player player = event.getEntity();
        if(event.getTarget() instanceof EntityMaid maid){
            if(player.getMainHandItem().is(InitItems.FOOD_BOOK.get())){
                if (event.getLevel().isClientSide)
                    FoodListScreen.open(player, maid);
                event.setCanceled(true);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
