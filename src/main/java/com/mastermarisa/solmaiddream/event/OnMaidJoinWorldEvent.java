package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.utils.AttributeHandler;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public class OnMaidJoinWorldEvent {
    @SubscribeEvent
    public static void onMaidJoinWorld(EntityJoinLevelEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof EntityMaid maid){
            FoodList foodList = maid.getData(ModAttachmentTypes.FOOD_LIST);
            foodList.init();
            foodList.refreshTotalFoodValue();
            foodList.refreshReachedMilestone();
            maid.setData(ModAttachmentTypes.FOOD_LIST,foodList);
            AttributeHandler.updateModifiers(maid);
        }
    }
}
