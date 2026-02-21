package com.mastermarisa.solmaiddream.data;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.init.InitConfig;
import com.mastermarisa.solmaiddream.utils.AttributeUtils;
import com.mastermarisa.solmaiddream.utils.EncodeUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collector;

public class FoodRecord implements INBTSerializable<CompoundTag> {
    private ConcurrentSkipListSet<String> recorded = new ConcurrentSkipListSet<>();
    public int reached = -1;

    public boolean add(String id, EntityMaid maid) {
        if (recorded.add(id)) {
            tryUpdateMilestone(maid);
            return true;
        }
        return false;
    }

    public int size() { return recorded.size(); }

    public void tryUpdateMilestone(EntityMaid maid) {
        List<? extends Integer> milestones = InitConfig.MILESTONES();
        if (reached >= milestones.size() - 1) return;
        if (recorded.size() >= milestones.get(reached + 1)) {
            reached++;
            AttributeUtils.updateAttributes(maid);
            maid.getFavorabilityManager().add(InitConfig.FAVORABILITY());
            sendMessages(maid);
        }
    }

    public void sendMessages(EntityMaid maid) {
        if (maid.getOwner() instanceof ServerPlayer player) {
            player.sendChatMessage(
                    new OutgoingChatMessage.Disguised(
                            Component.translatable("message.solmaiddream.milestone_achieved").withStyle(ChatFormatting.GOLD)
                    ),
                    false,
                    ChatType.bind(ChatType.CHAT, maid)
            );

            if(InitConfig.HP() != 0){
                player.sendChatMessage(
                        new OutgoingChatMessage.Disguised(
                                Component.literal("+").append(Component.translatable("message.solmaiddream.hearts",InitConfig.HP())).withStyle(ChatFormatting.GOLD)
                        ),
                        false,
                        ChatType.bind(ChatType.CHAT, maid)
                );
            }

            if(InitConfig.ARMOR() != 0){
                player.sendChatMessage(
                        new OutgoingChatMessage.Disguised(
                                Component.literal("+").append(Component.translatable("message.solmaiddream.armor",InitConfig.ARMOR())).withStyle(ChatFormatting.GOLD)
                        ),
                        false,
                        ChatType.bind(ChatType.CHAT, maid)
                );
            }

            if(InitConfig.ARMOR_TOUGHNESS() != 0) {
                player.sendChatMessage(
                        new OutgoingChatMessage.Disguised(
                                Component.literal("+").append(Component.translatable("message.solmaiddream.armor_toughness",InitConfig.ARMOR_TOUGHNESS())).withStyle(ChatFormatting.GOLD)
                        ),
                        false,
                        ChatType.bind(ChatType.CHAT, maid)
                );
            }

            if(InitConfig.ATTACK_DAMAGE() != 0){
                player.sendChatMessage(
                        new OutgoingChatMessage.Disguised(
                                Component.literal("+").append(Component.translatable("message.solmaiddream.attack_damage",InitConfig.ATTACK_DAMAGE())).withStyle(ChatFormatting.GOLD)
                        ),
                        false,
                        ChatType.bind(ChatType.CHAT, maid)
                );
            }

            if (InitConfig.FAVORABILITY() != 0) {
                player.sendChatMessage(
                        new OutgoingChatMessage.Disguised(
                                Component.translatable("message.solmaiddream.favorability_increased",InitConfig.FAVORABILITY()).withStyle(ChatFormatting.GOLD)
                        ),
                        false,
                        ChatType.bind(ChatType.CHAT, maid)
                );
            }

            if(reached == InitConfig.MILESTONES().size() - 1){
                player.sendChatMessage(new OutgoingChatMessage.Disguised(Component.translatable("message.solmaiddream.finished").withStyle(ChatFormatting.GOLD)),false, ChatType.bind(ChatType.CHAT, player));
            }
        }
    }

    public void resolveAvailability(EntityMaid maid) {
        recorded = recorded.stream().
                dropWhile(str -> EncodeUtils.decode(str) == null).
                collect(Collector.of(
                        ConcurrentSkipListSet::new,
                        ConcurrentSkipListSet::add,
                        (l,l1) -> {
                            ConcurrentSkipListSet<String> set = new ConcurrentSkipListSet<>(l);
                            set.addAll(l1);
                            return set;
                        }
                ));
        reached = resolveReached();
        AttributeUtils.updateAttributes(maid);
    }

    public int resolveReached() {
        List<? extends Integer> milestones = InitConfig.MILESTONES();
        int r = Integer.MIN_VALUE;
        for (int i = 0;i < milestones.size();i++)
            if (milestones.get(i) > recorded.size()) {
                r = i - 1;
                break;
            }
        if (r == Integer.MIN_VALUE) r = milestones.size() - 1;
        return r;
    }

    public boolean isFoodEaten(ItemStack itemStack) {
        return recorded.contains(EncodeUtils.encode(itemStack).toString());
    }

    public List<Item> toItems() {
        return recorded.stream().map(EncodeUtils::decode).filter(Objects::nonNull).toList();
    }

    public List<String> toList() { return new ArrayList<>(recorded); }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        EncodeUtils.writeStringSet(tag, recorded, "recorded");
        tag.putInt("reached", reached);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        recorded = EncodeUtils.readStringSet(tag, "recorded");
        reached = tag.getInt("reached");
    }

    public static class Syncer implements AttachmentSyncHandler<FoodRecord> {
        @Override
        public void write(RegistryFriendlyByteBuf registryFriendlyByteBuf, FoodRecord foodRecord, boolean b) {
            registryFriendlyByteBuf.writeCollection(foodRecord.recorded, FriendlyByteBuf::writeUtf);
            registryFriendlyByteBuf.writeInt(foodRecord.reached);
        }

        @Override
        public @Nullable FoodRecord read(IAttachmentHolder iAttachmentHolder, RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable FoodRecord foodRecord) {
            if (foodRecord == null) foodRecord = new FoodRecord();
            foodRecord.recorded = new ConcurrentSkipListSet<>(new ArrayList<>(registryFriendlyByteBuf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf)));
            foodRecord.reached = registryFriendlyByteBuf.readInt();
            return foodRecord;
        }
    }

    public static final AttachmentType<FoodRecord> TYPE = AttachmentType.serializable(FoodRecord::new).sync(new Syncer()).build();
}
