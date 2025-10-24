package net.valerio.mccourse.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class Hamburger extends Item {

    // Costruttore SEMPLICE senza parametri Properties
    public Hamburger() {
        super(new Item.Properties()
                .tab(net.minecraft.world.item.CreativeModeTab.TAB_FOOD)
                .stacksTo(16)
                .food(new FoodProperties.Builder()
                        .nutrition(10)
                        .saturationMod(1.5f)
                        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300, 1), 1.0f)
                        .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 100, 0), 0.8f)
                        .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 200, 0), 0.3f)
                        .alwaysEat()
                        .build()
                ));
    }
}