package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.utils.MaidTracker;
import com.mastermarisa.solmaiddream.utils.MaidWishHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnDayChangedEvent {
    @SubscribeEvent
    public static void onDayChangedEvent(DayChangedEvent event){
        MaidWishHandler.onDayChanged(MaidTracker.getMaids());
        for (EntityMaid maid : MaidTracker.getMaids()){
            MaidInfo info = ModAttachmentTypes.getMaidInfo(maid);
            info.existTime++;
            ModAttachmentTypes.setMaidInfo(maid, info);
        }
    }
}
