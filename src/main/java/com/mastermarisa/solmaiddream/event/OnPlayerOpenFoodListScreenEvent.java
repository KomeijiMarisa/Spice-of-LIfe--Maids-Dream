package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.registry.InitItems;
import com.mastermarisa.solmaiddream.render.ui.FoodListScreen;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//@OnlyIn(Dist.CLIENT)
//@EventBusSubscriber(modid = SOLMaidDream.MOD_ID,value = Dist.CLIENT)
public class OnPlayerOpenFoodListScreenEvent {
    @SubscribeEvent
    public static void onPlayerOpenFoodListScreenEvent(PlayerInteractEvent.EntityInteract event){
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        if(entity instanceof EntityMaid maid){
            if(player.getMainHandItem().is(InitItems.FOOD_BOOK.get())){
                if (event.getLevel().isClientSide){
                    FoodListScreen.open(player,maid);
                }
                event.setCanceled(true);
            }
        }
    }
}
