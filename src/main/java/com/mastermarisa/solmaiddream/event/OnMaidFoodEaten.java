package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.FoodRecord;
import com.mastermarisa.solmaiddream.data.MaidWish;
import com.mastermarisa.solmaiddream.utils.EncodeUtils;
import com.mastermarisa.solmaiddream.utils.FilterHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID)
public class OnMaidFoodEaten {
    @SubscribeEvent
    public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity().level().isClientSide()) return;

        if (event.getEntity() instanceof EntityMaid maid) {
            if (!FilterHelper.filter(event.getItem(), maid)) return;
            String encoded = EncodeUtils.encode(event.getItem()).toString();
            FoodRecord record = maid.getData(FoodRecord.TYPE);
            if (record.add(encoded, maid))
                maid.setData(FoodRecord.TYPE, record);
            MaidWish wish = maid.getData(MaidWish.TYPE);
            if (wish.contains(encoded)) {
                wish.achieve(maid);
                maid.setData(MaidWish.TYPE, wish);
            }
        }
    }
}
