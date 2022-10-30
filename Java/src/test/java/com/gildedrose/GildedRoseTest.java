package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {


    Item[] items = new Item[] {
        new Item("+5 Dexterity Vest", 10, 20), //
        new Item("Aged Brie", 2, 0), //
        new Item("Elixir of the Mongoose", 5, 7), //
        new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
        new Item("Sulfuras, Hand of Ragnaros", -1, 80),
        new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
        new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
        new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),

        // Conjured has been fixed
        new Item("Conjured Mana Cake", 3, 6) };

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    //Once the sell by date has passed, Quality degrades twice as fast
    //The Quality of an item is never negative
    @Test
    void dexterityVest() {
        Item[] items = new Item[]{new Item("+5 Dexterity Vest", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9,app.items[0].sellIn);

        assertEquals(19,app.items[0].quality);
        app.updateQuality();
        assertEquals(8,app.items[0].sellIn);

        assertEquals(18,app.items[0].quality);
    }
    //"Aged Brie" actually increases in Quality the older it gets
    @Test
    void agedBrieIncreasesInQualityTheOlder() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1,app.items[0].sellIn);

        assertEquals(1,app.items[0].quality);
        app.updateQuality();
        assertEquals(0,app.items[0].sellIn);
        assertEquals(2,app.items[0].quality);

    }

    @Test
    void qualityNeverMoreThan50() {
        Item[] items = new Item[]{new Item("Aged Brie", 2, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(1,app.items[0].sellIn);
        assertEquals(50,app.items[0].quality);


    }

    @Test
    void givenRegularItemExpectQualityCanNotDropUnderZero() {
        Item[] items = new Item[]{new Item("Elixir of the Mongoose", 1, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0,app.items[0].sellIn);
        assertEquals(0,app.items[0].quality);

    }
    //"Sulfuras", being a legendary item, never has to be sold or decreases in Quality
    @Test
    void sulfurasLegendaryItem() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 0, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0,app.items[0].sellIn);
        assertEquals(80,app.items[0].quality);

    }

    //"Sulfuras", being a legendary item, never has to be sold or decreases in Quality
    @Test
    void qualityAndSellInDataNeverChanges2() {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", -1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1,app.items[0].sellIn);
        assertEquals(80,app.items[0].quality);

    }

//    "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
//    Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
//    Quality drops to 0 after the concert
    @Test
    void backStageQualityIncreases() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(14,app.items[0].sellIn);
        assertEquals(21,app.items[0].quality);
    }

    //Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    //The Quality of an item is never more than 50
    @Test
    void backStageQualityIncreasesBy2() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9,app.items[0].sellIn);
        assertEquals(50,app.items[0].quality);
    }

    //Quality increases by 3 when there are 5 days or less but Quality drops to 0 after the concert
    @Test
    void backStageQualityIncreasesBy3() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 5, 30) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4,app.items[0].sellIn);
        assertEquals(33,app.items[0].quality);
    }
    //"Conjured" items degrade in Quality twice as fast as normal items
    @Test
    void conjuredItemsDegradeInQualityTwice() {
        Item[] items = new Item[] { new Item("Conjured Mana Cake", 3, 6)};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(2,app.items[0].sellIn);
        assertEquals(4,app.items[0].quality);
    }
}
