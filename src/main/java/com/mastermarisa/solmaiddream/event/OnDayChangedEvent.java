package com.mastermarisa.solmaiddream.event;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.MaidInfo;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import com.mastermarisa.solmaiddream.utils.MaidTracker;
import com.mastermarisa.solmaiddream.utils.MaidWishHandler;
import net.neoforged.bus.api.SubscribeEvent;

public class OnDayChangedEvent {
    @SubscribeEvent
    public static void onDayChangedEvent(DayChangedEvent event){
        MaidWishHandler.onDayChanged(MaidTracker.getMaids());
        for (EntityMaid maid : MaidTracker.getMaids()){
            MaidInfo info = maid.getData(ModAttachmentTypes.MAID_INFO);
            info.existTime++;
            maid.setData(ModAttachmentTypes.MAID_INFO,info);
        }
    }
}
