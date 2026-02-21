package com.mastermarisa.solmaiddream.render.ui;

import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import com.mastermarisa.solmaiddream.SOLMaidDream;
import com.mastermarisa.solmaiddream.data.FoodRecord;
import com.mastermarisa.solmaiddream.data.MaidWish;
import com.mastermarisa.solmaiddream.render.ui.element.*;
import com.mastermarisa.solmaiddream.utils.FilterHelper;
import com.mastermarisa.solmaiddream.utils.ItemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FoodListScreen extends Screen implements PageFlipButton.Pageable{
    private static final int itemSize = 16;
    private static final int startY = 40;
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Font font;
    static final Color fullBlack;
    static final Color lessBlack;
    static final Color leastBlack;

    public static final ImageData bookImage;
    public static final ImageData heartImage;
    public static final ImageData hungerImage;
    public static final ImageData armorImage;
    public static final ImageData armorToughnessImage;
    public static final ImageData potatoChipImage;
    public static final ImageData watermelonImage;
    public static final ImageData appleStickImage;
    public static final ImageData attackDamageImage;

    private final Player player;
    private final EntityMaid maid;
    private final FoodRecord foodList;
    private final MaidWish maidInfo;
    private final List<Item> allFoodList;

    private final List<Page> pages = new ArrayList<>();
    private Page curPage;

    private PageFlipButton prePageButton;
    private PageFlipButton nextPageButton;

    private UILabel UIPageNumber;

    static {
        font = mc.font;
        fullBlack = Color.BLACK;
        lessBlack = new Color(0, 0, 0, 128);
        leastBlack = new Color(0, 0, 0, 64);
        hungerImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/food_book.png"),new Rectangle(19,227,9,9),9,9,400,256);
        bookImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/food_book.png"),new Rectangle(1,1,147,182),147,182,400,256);
        heartImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/food_book.png"),new Rectangle(3,227,9,9),9,9,400,256);
        armorImage = new ImageData(ResourceLocation.fromNamespaceAndPath("minecraft","textures/gui/sprites/hud/armor_full.png"),new Rectangle(0,0,9,9),9,9,9,9);
        armorToughnessImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/armor_toughness.png"),new Rectangle(0,0,9,9),9,9,9,9);
        potatoChipImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/food_book.png"),new Rectangle(128,240,16,16),16,16,400,256);
        watermelonImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/food_book.png"),new Rectangle(96,240,16,16),16,16,400,256);
        appleStickImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/food_book.png"),new Rectangle(112,240,16,16),16,16,400,256);
        attackDamageImage = new ImageData(SOLMaidDream.resourceLocation("textures/gui/icons.png"),new Rectangle(3,3,9,9),9,9,128,64);
    }

    private void initPages(){
        TotalViewPage totalViewPage = new TotalViewPage(new Rectangle(0,0,147,182),Component.translatable("gui.solmaiddream.title.total_view").getString(),foodList, allFoodList.size());
        curPage = totalViewPage;
        pages.add(totalViewPage);
        pages.add(new MaidInfoPage(new Rectangle(0,0,147,182),Component.translatable("gui.solmaiddream.title.about_maid").getString(),foodList,maidInfo,maid.tickCount));

        List<Item> foods = foodList.toItems();
        List<FoodViewPage> eaten = generatePagesFromItemStacks(Component.translatable("gui.solmaiddream.title.tasted_food").getString(),foods.stream().map(ItemStack::new).toList());
        pages.addAll(eaten);
        List<FoodViewPage> notEaten = generatePagesFromItemStacks(Component.translatable("gui.solmaiddream.title.untried_food").getString(),allFoodList.stream().filter((o)-> !foods.contains(o)).map(ItemStack::new).toList());
        pages.addAll(notEaten);

        curPage.setCenterX(getScreenCenterX());
        curPage.setCenterY(getScreenCenterY());
        curPage.onSwitchedTo();
    }

    private void initButtons(){
        if (prePageButton != null) this.removeWidget(prePageButton);
        if (nextPageButton != null) this.removeWidget(nextPageButton);

        prePageButton = this.addRenderableWidget(new PageFlipButton(curPage.bg.getMinX() + 20,curPage.bg.getMaxY() - 25,this,-1));
        nextPageButton = this.addRenderableWidget(new PageFlipButton(curPage.bg.getMaxX() - 37,curPage.bg.getMaxY() - 25,this,1));
        updateButtonVisibility();
    }

    public FoodListScreen(Player player,EntityMaid maid){
        super(Component.empty());
        this.player = player;
        this.maid = maid;
        this.foodList = maid.getData(FoodRecord.TYPE);
        this.maidInfo = maid.getData(MaidWish.TYPE);
        allFoodList = ItemHelper.allFoods;

        initPages();
        initButtons();

        UIPageNumber = new UILabel("1",lessBlack);
        resetUIPageNumber();
    }

    public static void open(Player player,EntityMaid maid) {
        Minecraft.getInstance().setScreen(new FoodListScreen(player,maid));
    }

    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);

        if (curPage != null) UIElement.render(graphics,curPage,mouseX,mouseY);
        UIElement.render(graphics,UIPageNumber,mouseX,mouseY);

        prePageButton.render(graphics,mouseX,mouseY,partialTicks);
        nextPageButton.render(graphics,mouseX,mouseY,partialTicks);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY > 0) {
            switchToPage(getCurrentPageNumber() - 1);
        } else if (scrollY < 0) {
            switchToPage(getCurrentPageNumber() + 1);
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        super.resize(minecraft, width, height);

        if (curPage != null){
            curPage.setCenterX(getScreenCenterX());
            curPage.setCenterY(getScreenCenterY());
            curPage.onResize();
        }
        initButtons();
        resetUIPageNumber();
    }

    public void resetUIPageNumber(){
        UIPageNumber.setWidth(107);
        UIPageNumber.setCenterX(getScreenCenterX());
        UIPageNumber.setMaxY(curPage.bg.getMaxY() - 15);
    }

    public List<FoodViewPage> generatePagesFromItemStacks(String header,List<ItemStack> itemStacks){
        List<FoodViewPage> foodViewPages = new ArrayList<>();
        for (int i = 0;i < (itemStacks.size() / FoodViewPage.itemsPerPage + 1);i++){
            if ((i + 1) * FoodViewPage.itemsPerPage <= itemStacks.size()){
                foodViewPages.add(new FoodViewPage(new Rectangle(0,0,147,182),header,itemStacks.subList(i * FoodViewPage.itemsPerPage,(i + 1) * FoodViewPage.itemsPerPage)));
            } else {
                foodViewPages.add(new FoodViewPage(new Rectangle(0,0,147,182),header,itemStacks.subList(i * FoodViewPage.itemsPerPage,itemStacks.size())));
            }
        }

        return foodViewPages;
    }

    public static int getScreenCenterX(){
        return mc.getWindow().getGuiScaledWidth() / 2;
    }

    public static int getScreenCenterY(){
        return mc.getWindow().getGuiScaledHeight() / 2;
    }

    private void updateButtonVisibility() {
        this.prePageButton.updateState();
        this.nextPageButton.updateState();
    }

    public void switchToPage(int pageNumber){
        if (isWithinRange(pageNumber)){
            curPage = pages.get(pageNumber);
            curPage.onSwitchedTo();
            UIPageNumber = new UILabel(String.valueOf(pageNumber + 1),lessBlack);
            resetUIPageNumber();
            updateButtonVisibility();
        }
    }

    public int getCurrentPageNumber() {
        return pages.indexOf(curPage);
    }

    public boolean isWithinRange(int pageNumber) {
        return pageNumber >= 0 && pageNumber < this.pages.size();
    }
}
