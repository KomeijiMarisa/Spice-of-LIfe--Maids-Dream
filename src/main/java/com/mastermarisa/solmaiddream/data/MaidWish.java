package com.mastermarisa.solmaiddream.data;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.init.InitConfig;
import com.mastermarisa.solmaiddream.utils.AttributeUtils;
import com.mastermarisa.solmaiddream.utils.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MaidWish implements INBTSerializable<CompoundTag> {
    private List<String> wishes = new ArrayList<>();
    /// 0 -> 未使用
    /// 1 -> 当日槽位
    /// 2 -> 已实现
    /// 3 -> 未实现
    private byte[] cycle = new byte[3];
    public int fulfillment;
    public int maxFulfillment;
    public int totalAchieved;
    public long existed;

    public void onDayChanged(EntityMaid maid) {
        if (maid.getOwner() == null) return;
        FoodRecord foodRecord = maid.getData(FoodRecord.TYPE);
        if (foodRecord.size() < Math.max(InitConfig.TYPES_FOR_WISH(), ItemHelper.allFoods.size() / 10)) return;
        existed++;
        for (int i = 0;i < cycle.length;i++)
            if (cycle[i] == 1) cycle[i] = 3;

        int index = getFirstSlot(cycle, 0);
        if (index != -1) cycle[index] = 1;
        else handleCycle(maid);

        wishes.clear();
        List<String> food = foodRecord.toList();
        Collections.shuffle(food);
        wishes.addAll(food.subList(0,3));
    }

    public void handleCycle(EntityMaid maid) {
        int achieced = 0;
        for (int i = 0;i < cycle.length;i++) {
            if (cycle[i] == 2) achieced++;
            cycle[i] = 0;
        }

        if (maid.getOwner() instanceof ServerPlayer player) {
            switch (achieced) {
                case 0 -> {
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.to_zero_chat").withStyle(ChatFormatting.DARK_AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.to_zero").withStyle(ChatFormatting.DARK_AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                }
                case 1 -> {
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.reduce_by_half_chat").withStyle(ChatFormatting.AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.reduce_by_half").withStyle(ChatFormatting.DARK_AQUA)),false, ChatType.bind(ChatType.CHAT, maid));
                }
                case 2 -> {
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_one_chat").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_one").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                }
                case 3 -> {
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_two_chat").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                    player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.wish_handler.add_two").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
                }
            }
        }

        switch (achieced) {
            case 0 -> fulfillment = 0;
            case 1 -> fulfillment /= 2;
            case 2 -> {
                fulfillment ++;
                if (fulfillment > maxFulfillment) maxFulfillment = fulfillment;
            }
            case 3 -> {
                fulfillment += 2;
                if (fulfillment > maxFulfillment) maxFulfillment = fulfillment;
            }
        }

        cycle[0] = 1;
        AttributeUtils.updateAttributes(maid);
    }

    public int getCycleNum() {
        int index = getFirstSlot(cycle, 0);
        if (index != -1) {
            return index - 1;
        } else return 2;
    }

    public void achieve(EntityMaid maid) {
        if (wishes.isEmpty()) return;
        wishes.clear();
        int index = getFirstSlot(cycle, 1);
        if (index != -1) cycle[index] = 2;
        totalAchieved++;

        if(maid.getOwner() instanceof ServerPlayer serverPlayer){
            int favor = InitConfig.FAVORABILITY_WISH();
            serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.event.daily_wish_chat").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
            if (favor != 0) serverPlayer.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("chat.solmaiddream.event.daily_wish",favor).withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, maid));
        }
    }

    public boolean contains(String wish) { return wishes.contains(wish); }

    private int getFirstSlot(byte[] array, int value) {
        for (int i = 0;i < array.length;i++)
            if (array[i] == value) return i;
        return -1;
    }

    public List<String> toList() { return wishes.stream().toList(); }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        ListTag listTag = new ListTag();
        wishes.forEach(s -> listTag.add(StringTag.valueOf(s)));
        tag.put("wishes", listTag);
        tag.putByteArray("cycle", cycle);
        tag.putInt("fulfillment", fulfillment);
        tag.putInt("totalAchieved", totalAchieved);
        tag.putInt("maxFulfillment", maxFulfillment);
        tag.putLong("existed", existed);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        if (tag.contains("wishes")) {
            wishes.clear();
            ListTag listTag = tag.getList("wishes", Tag.TAG_STRING);
            listTag.forEach(t -> wishes.add(t.getAsString()));
            cycle = tag.getByteArray("cycle");
            fulfillment = tag.getInt("fulfillment");
            totalAchieved = tag.getInt("totalAchieved");
            maxFulfillment = tag.getInt("maxFulfillment");
            existed = tag.getLong("existed");
        }
    }

    public static class Syncer implements AttachmentSyncHandler<MaidWish> {
        @Override
        public void write(RegistryFriendlyByteBuf registryFriendlyByteBuf, MaidWish maidWish, boolean b) {
            registryFriendlyByteBuf.writeCollection(maidWish.wishes, FriendlyByteBuf::writeUtf);
            registryFriendlyByteBuf.writeByteArray(maidWish.cycle);
            registryFriendlyByteBuf.writeInt(maidWish.fulfillment);
            registryFriendlyByteBuf.writeInt(maidWish.totalAchieved);
            registryFriendlyByteBuf.writeInt(maidWish.maxFulfillment);
            registryFriendlyByteBuf.writeLong(maidWish.existed);
        }

        @Override
        public @Nullable MaidWish read(IAttachmentHolder iAttachmentHolder, RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable MaidWish maidWish) {
            if (maidWish == null) maidWish = new MaidWish();
            maidWish.wishes = registryFriendlyByteBuf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf);
            maidWish.cycle = registryFriendlyByteBuf.readByteArray(3);
            maidWish.fulfillment = registryFriendlyByteBuf.readInt();
            maidWish.totalAchieved = registryFriendlyByteBuf.readInt();
            maidWish.maxFulfillment = registryFriendlyByteBuf.readInt();
            maidWish.existed = registryFriendlyByteBuf.readLong();
            return maidWish;
        }
    }

    public static final AttachmentType<MaidWish> TYPE = AttachmentType.serializable(MaidWish::new).sync(new Syncer()).build();
}
