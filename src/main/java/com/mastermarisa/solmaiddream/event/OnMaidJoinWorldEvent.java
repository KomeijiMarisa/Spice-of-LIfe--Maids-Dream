package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.utils.AttributeHandler;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

public class OnMaidJoinWorldEvent {
    /**
     * 当女仆在世界生成
     * 对数据进行初始化
     * @param event 事件
     */
    @SubscribeEvent
    public static void onMaidJoinWorld(EntityJoinLevelEvent event){
        Entity entity = event.getEntity();
        if(entity instanceof EntityMaid maid){
            FoodList foodList = ModAttachmentTypes.getFoodList(maid);
            foodList.init();
            foodList.refreshTotalFoodValue();
            foodList.refreshReachedMilestone();
            ModAttachmentTypes.setFoodList(maid, foodList);
            AttributeHandler.updateModifiers(maid);
        }
    }
}
