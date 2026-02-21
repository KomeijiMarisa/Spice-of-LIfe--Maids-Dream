package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.api.event.DayChangedEvent;
import com.mastermarisa.solmaiddream.data.MaidWish;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = SOLMaidDream.MOD_ID)
public class MaidTracker {
    public static final List<EntityMaid> maids = new ArrayList<>();

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            maids.add((EntityMaid) event.getEntity());
        }
    }

    @SubscribeEvent
    public static void onEntityLeave(EntityLeaveLevelEvent event) {
        if (!event.getLevel().isClientSide() && event.getEntity().getType() == EntityMaid.TYPE) {
            EntityMaid maid = (EntityMaid) event.getEntity();
            maids.remove(maid);
        }
    }

    @SubscribeEvent
    public static void onDayChanged(DayChangedEvent event) {
        maids.forEach(maid -> {
            MaidWish wish = maid.getData(MaidWish.TYPE);
            wish.onDayChanged(maid);
            maid.setData(MaidWish.TYPE, wish);
        });
    }
}
