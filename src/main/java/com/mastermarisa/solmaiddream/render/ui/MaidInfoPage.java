package com.mastermarisa.solmaiddream.render.ui;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.mastermarisa.solmaiddream.data.FoodRecord;
import com.mastermarisa.solmaiddream.data.MaidWish;
import com.mastermarisa.solmaiddream.render.ui.element.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MaidInfoPage extends Page{
    public static final ImageData ExistTimeIcon;
    public static final ImageData PicnicBasketIcon;
    private final UIContainerVertical fullUIContainer;

    static {
        ExistTimeIcon = new ImageData(ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID,"textures/item/model_switcher.png"),
                new Rectangle(0,0,16,16),16,16,16,16);
        PicnicBasketIcon = new ImageData(ResourceLocation.fromNamespaceAndPath(TouhouLittleMaid.MOD_ID,"textures/item/picnic_basket.png"),
                new Rectangle(0,0,16,16),16,16,16,16);
    }

    public MaidInfoPage(Rectangle frame, String header, FoodRecord foodList, MaidWish maidInfo, long tickCount){
        super(frame,header);
        List<UIElement> elements = new ArrayList<>();

        UILabel UIExistTimeTitle = new UILabel(Component.translatable("gui.solmaiddream.maid_info.exist_time").getString(),FoodListScreen.lessBlack);
        UIExistTimeTitle.alignment = UILabel.TextAlignment.LEFT;
        UIImage UIExistTimeIcon = new UIImage(ExistTimeIcon);
        UIExistTimeIcon.setHeight(14);
        UIContainerHorizontal UIExistTimeContainer = UIContainerHorizontal.wrap(List.of(UIExistTimeIcon,UIExistTimeTitle),4,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIExistTimeContainer.setWidth(107);

        elements.add(UIExistTimeContainer);

        UILabel UIExistTime = new UILabel(maidInfo.existed + Component.translatable("gui.solmaiddream.maid_info.day").getString(),FoodListScreen.lessBlack);
        UIExistTime.alignment = UILabel.TextAlignment.CENTER;
        UIExistTime.setWidth(107);
        UIExistTime.setHeight(UIExistTime.getHeight() + 1);

        elements.add(UIExistTime);
        elements.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));


        UILabel UIWishCountTitle = new UILabel(Component.translatable("gui.solmaiddream.maid_info.wish_achieved").getString(),FoodListScreen.lessBlack);
        UIWishCountTitle.alignment = UILabel.TextAlignment.LEFT;
        UIImage UIWishCountIcon = new UIImage(FoodListScreen.potatoChipImage);
        UIWishCountIcon.setHeight(14);
        UIContainerHorizontal UIWishCountContainer = UIContainerHorizontal.wrap(List.of(UIWishCountIcon,UIWishCountTitle),4,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIWishCountContainer.setWidth(107);

        elements.add(UIWishCountContainer);

        UILabel UIWishCount = new UILabel(String.valueOf(maidInfo.totalAchieved),FoodListScreen.lessBlack);
        UIWishCount.alignment = UILabel.TextAlignment.CENTER;
        UIWishCount.setWidth(107);
        UIWishCount.setHeight(UIWishCount.getHeight() + 1);

        elements.add(UIWishCount);
        elements.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));


        UILabel UIMaxWishBuffTitle = new UILabel(Component.translatable("gui.solmaiddream.maid_info.max_wish_value").getString(),FoodListScreen.lessBlack);
        UIMaxWishBuffTitle.alignment = UILabel.TextAlignment.LEFT;
        UIImage UIMaxWishBuffIcon = new UIImage(FoodListScreen.appleStickImage);
        UIMaxWishBuffIcon.setHeight(14);
        UIContainerHorizontal UIMaxWishBuffContainer = UIContainerHorizontal.wrap(List.of(UIMaxWishBuffIcon,UIMaxWishBuffTitle),4,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIMaxWishBuffContainer.setWidth(107);

        elements.add(UIMaxWishBuffContainer);

        UILabel UIMaxWishBuff = new UILabel(String.valueOf(maidInfo.maxFulfillment),FoodListScreen.lessBlack);
        UIMaxWishBuff.alignment = UILabel.TextAlignment.CENTER;
        UIMaxWishBuff.setWidth(107);
        UIMaxWishBuff.setHeight(UIMaxWishBuff.getHeight() + 1);

        elements.add(UIMaxWishBuff);
        elements.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));


        UILabel UIWishBuffTitle = new UILabel(Component.translatable("gui.solmaiddream.maid_info.wish_value").getString(),FoodListScreen.lessBlack);
        UIWishBuffTitle.alignment = UILabel.TextAlignment.LEFT;
        UIImage UIWishBuffIcon = new UIImage(FoodListScreen.watermelonImage);
        UIWishBuffIcon.setHeight(14);
        UIContainerHorizontal UIWishBuffContainer = UIContainerHorizontal.wrap(List.of(UIWishBuffIcon,UIWishBuffTitle),4,0,UIContainerHorizontal.ElementAlignment.LEFT);
        UIWishBuffContainer.setWidth(107);

        elements.add(UIWishBuffContainer);

        UILabel UIWishBuff = new UILabel(String.valueOf(maidInfo.fulfillment),FoodListScreen.lessBlack);
        UIWishBuff.alignment = UILabel.TextAlignment.CENTER;
        UIWishBuff.setWidth(107);
        UIWishBuff.setHeight(UIWishBuff.getHeight() + 1);
        UILabel UIWishBuffPercent = new UILabel("(" + Component.translatable("gui.solmaiddream.maid_info.attributes_increased").getString() + maidInfo.fulfillment * 10 + "%)",FoodListScreen.lessBlack);
        UIWishBuffPercent.alignment = UILabel.TextAlignment.CENTER;
        UIWishBuffPercent.setWidth(107);
        UIWishBuffPercent.setHeight(UIWishBuffPercent.getHeight() + 1);

        elements.add(UIWishBuff);
        elements.add(UIWishBuffPercent);
        elements.add(UIBox.horizontalLine(title.getMinX(),title.getMaxX(),0,FoodListScreen.leastBlack));

        fullUIContainer = UIContainerVertical.wrap(elements,2,0,UIContainerVertical.ElementAlignment.UP);

        this.children = List.of(bg,title,underLine,fullUIContainer);
    }

    public void onResize(){
        super.onResize();

        fullUIContainer.setCenterX(FoodListScreen.getScreenCenterX());
        fullUIContainer.setMinY(underLine.getMaxY() + 1);
    }

    public void onSwitchedTo(){
        onResize();
    }
}
