package com.mastermarisa.solmaiddream.event;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.utils.MaidTracker;
import com.mastermarisa.solmaiddream.utils.MaidWishHandler;
import net.neoforged.bus.api.SubscribeEvent;

public class OnDayChangedEvent {
    @SubscribeEvent
    public static void onDayChangedEvent(DayChangedEvent event){
        SOLMaidDream.LOGGER.debug("新的一天！当前为Day：" + String.valueOf(event.getNewDay()));
        MaidWishHandler.onDayChanged(MaidTracker.getMaids());
    }
}
