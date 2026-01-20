package com.mastermarisa.solmaiddream.utils;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.data.ModAttachmentTypes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;
import java.util.Objects;


public class AttributeHandler {
    private static final ResourceLocation HEALTH_MODIFIER_ID = SOLMaidDream.resourceLocation("health_boost");
    private static final ResourceLocation ARMOR_MODIFIER_ID = SOLMaidDream.resourceLocation("armor");
    private static final ResourceLocation ARMOR_TOUGHNESS_MODIFIER_ID = SOLMaidDream.resourceLocation("armor_toughness");
    private static final ResourceLocation ATTACK_DAMAGE_MODIFIER_ID = SOLMaidDream.resourceLocation("attack_damage");

    private static AttributeInstance getAttribute(EntityMaid maid, Holder<Attribute> attribute) {
        return (AttributeInstance) Objects.requireNonNull(maid.getAttribute(attribute));
    }

    @Nullable
    private static AttributeModifier getModifier(EntityMaid maid, Holder<Attribute> attribute, ResourceLocation ID) {
        return getAttribute(maid,attribute).getModifier(ID);
    }

    public static void updateArmorToughnessModifier(EntityMaid maid){
        double totalArmorToughnessDamageModifier = (maid.getData(ModAttachmentTypes.FOOD_LIST).getReachedMilestone() + 1) * ModServerConfig.getArmorToughnessPerMilestone();
        totalArmorToughnessDamageModifier = applyWishBuff(totalArmorToughnessDamageModifier,maid);
        if (!maid.level().isClientSide()){
            AttributeModifier modifier = new AttributeModifier(ARMOR_TOUGHNESS_MODIFIER_ID,totalArmorToughnessDamageModifier,AttributeModifier.Operation.ADD_VALUE);
            AttributeInstance attribute = getAttribute(maid,Attributes.ARMOR_TOUGHNESS);
            attribute.removeModifier(modifier);
            attribute.addPermanentModifier(modifier);
        }
    }

    public static void updateFoodAttackDamageModifier(EntityMaid maid){
        double totalAttackDamageModifier = (maid.getData(ModAttachmentTypes.FOOD_LIST).getReachedMilestone() + 1) * ModServerConfig.getAttackDamagePerMilestone();
        totalAttackDamageModifier = applyWishBuff(totalAttackDamageModifier,maid);
        if (!maid.level().isClientSide()){
            AttributeModifier modifier = new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,totalAttackDamageModifier,AttributeModifier.Operation.ADD_VALUE);
            AttributeInstance attribute = getAttribute(maid,Attributes.ATTACK_DAMAGE);
            attribute.removeModifier(modifier);
            attribute.addPermanentModifier(modifier);
        }
    }

    public static void updateFoodArmorModifier(EntityMaid maid){
        double totalArmorModifier = (maid.getData(ModAttachmentTypes.FOOD_LIST).getReachedMilestone() + 1) * ModServerConfig.getArmorPerMilestone();
        totalArmorModifier = applyWishBuff(totalArmorModifier,maid);
        if (!maid.level().isClientSide()){
            AttributeModifier modifier = new AttributeModifier(ARMOR_MODIFIER_ID,totalArmorModifier,AttributeModifier.Operation.ADD_VALUE);
            AttributeInstance attribute = getAttribute(maid,Attributes.ARMOR);
            attribute.removeModifier(modifier);
            attribute.addPermanentModifier(modifier);
        }
    }

    public static void updateFoodHPModifier(EntityMaid maid){
        double totalHealthModifier = (maid.getData(ModAttachmentTypes.FOOD_LIST).getReachedMilestone() + 1) * ModServerConfig.getHPPerMilestone();
        totalHealthModifier = applyWishBuff(totalHealthModifier,maid);
        if (!maid.level().isClientSide()){
            AttributeModifier modifier = new AttributeModifier(HEALTH_MODIFIER_ID,totalHealthModifier,AttributeModifier.Operation.ADD_VALUE);
            updateHealthModifier(maid,modifier);
        }
    }

    private static double applyWishBuff(double value, EntityMaid maid){
        return Math.ceil(value * ((ModServerConfig.getWishBuffAddValuePercent() / 100d) * MaidWishHandler.getWishesAchieved(maid) + 1));
    }

    private static void updateHealthModifier(EntityMaid maid, AttributeModifier modifier) {
        float oldMax = maid.getMaxHealth();
        AttributeInstance attribute = getAttribute(maid,Attributes.MAX_HEALTH);
        attribute.removeModifier(modifier);
        attribute.addPermanentModifier(modifier);
        float newHealth = maid.getHealth() * maid.getMaxHealth() / oldMax;

        maid.setHealth(newHealth);
    }

    public static void updateModifiers(EntityMaid maid){
        updateFoodHPModifier(maid);
        updateFoodArmorModifier(maid);
        updateArmorToughnessModifier(maid);
        updateFoodAttackDamageModifier(maid);
    }
}
