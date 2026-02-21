package com.mastermarisa.solmaiddream.render.ui;

import com.mastermarisa.solmaiddream.data.FoodRecord;
import com.mastermarisa.solmaiddream.init.InitConfig;
import com.mastermarisa.solmaiddream.render.ui.element.*;
import net.minecraft.network.chat.Component;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TotalViewPage extends Page{
    private final UIContainerVertical UIInfosContainer;

    public TotalViewPage(Rectangle frame, String header, FoodRecord foodList, int totalFoodCount){
        super(frame,header);

        List<UIElement> infos = new ArrayList<>();

        List<UIElement> foodInfo = new ArrayList<>();
        foodInfo.add(new UIImage(FoodListScreen.hungerImage));
        foodInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.food_eaten").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIFoodInfo = UIContainerHorizontal.wrap(foodInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIFoodInfo.setWidth(107);
        infos.add(UIFoodInfo);
        infos.add(new UILabel(foodList.size() + "/" + totalFoodCount,FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> hpInfo = new ArrayList<>();
        hpInfo.add(new UIImage(FoodListScreen.heartImage));
        hpInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.hp_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIHPInfo = UIContainerHorizontal.wrap(hpInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIHPInfo.setWidth(107);
        infos.add(UIHPInfo);
        infos.add(new UILabel((foodList.reached + 1) * InitConfig.HP() + "/" + InitConfig.MILESTONES().size() * InitConfig.HP(),FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> armorInfo = new ArrayList<>();
        armorInfo.add(new UIImage(FoodListScreen.armorImage));
        armorInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.armor_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIArmorInfo = UIContainerHorizontal.wrap(armorInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIArmorInfo.setWidth(107);
        infos.add(UIArmorInfo);
        infos.add(new UILabel((foodList.reached + 1) * InitConfig.ARMOR() + "/" + InitConfig.MILESTONES().size() * InitConfig.ARMOR(),FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> armorToughnessInfo = new ArrayList<>();
        armorToughnessInfo.add(new UIImage(FoodListScreen.armorToughnessImage));
        armorToughnessInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.armor_toughness_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIArmorToughnessInfo = UIContainerHorizontal.wrap(armorToughnessInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIArmorToughnessInfo.setWidth(107);
        infos.add(UIArmorToughnessInfo);
        infos.add(new UILabel((foodList.reached + 1) * InitConfig.ARMOR_TOUGHNESS() + "/" +InitConfig.MILESTONES().size() * InitConfig.ARMOR_TOUGHNESS(),FoodListScreen.lessBlack));
        infos.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        List<UIElement> attackDamageInfo = new ArrayList<>();
        attackDamageInfo.add(new UIImage(FoodListScreen.attackDamageImage));
        attackDamageInfo.add(new UILabel(Component.translatable("gui.solmaiddream.total_view.attack_damage_gained").getString(),FoodListScreen.lessBlack));
        UIContainerHorizontal UIAttackDamageInfo = UIContainerHorizontal.wrap(attackDamageInfo,9,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIAttackDamageInfo.setWidth(107);
        infos.add(UIAttackDamageInfo);
        infos.add(new UILabel((foodList.reached + 1) * InitConfig.ATTACK_DAMAGE() + "/" + InitConfig.MILESTONES().size() * InitConfig.ATTACK_DAMAGE(),FoodListScreen.lessBlack));
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
