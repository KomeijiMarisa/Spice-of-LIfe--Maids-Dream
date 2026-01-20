package com.mastermarisa.solmaiddream.data;

import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, SOLMaidDream.MOD_ID);

    public static final Supplier<AttachmentType<FoodList>> FOOD_LIST = ATTACHMENT_TYPES.register("foodlist", ()-> AttachmentType.serializable(FoodList::new).sync(new FoodListSyncHandler()).build());
    public static final Supplier<AttachmentType<MaidInfo>> MAID_INFO = ATTACHMENT_TYPES.register("maidinfo", ()-> MaidInfo.TYPE);
}
