 package net.valerio.mccourse.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Classe che rappresenta un piccone personalizzato per il mod
 * Estende la classe PickaxeItem di Minecraft per ereditare tutte le funzionalità base dei picconi
 */
public class ModPickaxeItem extends PickaxeItem {

    /**
     * Costruttore per il piccone personalizzato
     * @param tier Il materiale del piccone (definisce durabilità, velocità, etc.)
     * @param attackDamage Il danno base dell'attacco
     * @param attackSpeed La velocità di attacco
     * @param properties Le proprietà aggiuntive dell'item
     */
    public ModPickaxeItem(Tier tier, int attackDamage, float attackSpeed, Properties properties) {
        // Chiama il costruttore della classe padre (PickaxeItem)
        super(tier, attackDamage, attackSpeed, properties);
    }
}