package com.mastermarisa.solmaiddream.render.ui;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.config.ModServerConfig;
import com.mastermarisa.solmaiddream.data.FoodList;
import com.mastermarisa.solmaiddream.render.ui.element.*;
import com.mastermarisa.solmaiddream.utils.FoodNutritionManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TotalViewPage extends Page{
    private final UIContainerVertical UIInfosContainer;

    public TotalViewPage(Rectangle frame, String header, FoodList foodList,int totalFoodCount){
        super(frame,header);

        List<UIElement> infos = new ArrayList<>();
        foodList.refreshTotalFoodValue();
        foodList.refreshReachedMilestone();

        List<UIElement> foodInfo = new ArrayList<>();
        foodInfo.add(new UIImage(FoodListScreen.hungerImage));
        foodInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.food_eaten").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIFoodInfo = UIContainerHorizontal.wrap(foodInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIFoodInfo.setWidth(107);
        infos.add(UIFoodInfo);
        infos.add(new UILabel(foodList.getFoods().size() + "/" + totalFoodCount,FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> hpInfo = new ArrayList<>();
        hpInfo.add(new UIImage(FoodListScreen.heartImage));
        hpInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.hp_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIHPInfo = UIContainerHorizontal.wrap(hpInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIHPInfo.setWidth(107);
        infos.add(UIHPInfo);
        infos.add(new UILabel((foodList.getReachedMilestone() + 1) * ModServerConfig.HP_PER_MILESTONE.get() + "/" + ModServerConfig.getMilestones().size() * ModServerConfig.HP_PER_MILESTONE.get(),FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> armorInfo = new ArrayList<>();
        armorInfo.add(new UIImage(FoodListScreen.armorImage));
        armorInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.armor_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIArmorInfo = UIContainerHorizontal.wrap(armorInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIArmorInfo.setWidth(107);
        infos.add(UIArmorInfo);
        infos.add(new UILabel((foodList.getReachedMilestone() + 1) * ModServerConfig.ARMOR_PER_MILESTONE.get() + "/" + ModServerConfig.getMilestones().size() * ModServerConfig.ARMOR_PER_MILESTONE.get(),FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> armorToughnessInfo = new ArrayList<>();
        armorToughnessInfo.add(new UIImage(FoodListScreen.armorToughnessImage));
        armorToughnessInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.armor_toughness_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIArmorToughnessInfo = UIContainerHorizontal.wrap(armorToughnessInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIArmorToughnessInfo.setWidth(107);
        infos.add(UIArmorToughnessInfo);
        infos.add(new UILabel((foodList.getReachedMilestone() + 1) * ModServerConfig.ARMOR_TOUGHNESS_PER_MILESTONE.get() + "/" + ModServerConfig.getMilestones().size() * ModServerConfig.ARMOR_TOUGHNESS_PER_MILESTONE.get(),FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> attackDamageInfo = new ArrayList<>();
        attackDamageInfo.add(new UIImage(FoodListScreen.attackDamageImage));
        attackDamageInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.attack_damage_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIAttackDamageInfo = UIContainerHorizontal.wrap(attackDamageInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIAttackDamageInfo.setWidth(107);
        infos.add(UIAttackDamageInfo);
        infos.add(new UILabel((foodList.getReachedMilestone() + 1) * ModServerConfig.getAttackDamagePerMilestone() + "/" + ModServerConfig.getMilestones().size() * ModServerConfig.getAttackDamagePerMilestone(),FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        UIInfosContainer = UIContainerVertical.wrap(infos,3,0,UIContainerVertical.ElementAlignment.UP);

        this.children = List.of(bg,title,underLine,UIInfosContainer);
    }

    public void onResize(){
        super.onResize();

        UIInfosContainer.setCenterX(FoodListScreen.getScreenCenterX());
        UIInfosContainer.setMinY(title.getMaxY() + 7);
    }

    public void onSwitchedTo(){
        onResize();
    }
}
