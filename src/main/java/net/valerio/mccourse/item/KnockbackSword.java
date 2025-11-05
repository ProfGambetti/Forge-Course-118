package net.valerio.mccourse.item;
// Importa tutte le classi necessarie da Minecraft
// LivingEntity: tutte le entità viventi (giocatori, mob, etc.)
import net.minecraft.world.entity.LivingEntity;
// Player: il giocatore specificamente
import net.minecraft.world.entity.player.Player;
// ItemStack: rappresenta un oggetto nell'inventario (con dati, danni, etc.)
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
// SwordItem: la classe base per tutte le spade in Minecraft
import net.minecraft.world.item.SwordItem;
// Tiers: i materiali delle tools (legno, pietra, ferro, diamante, etc.)
import net.minecraft.world.item.Tiers;
// Vec3: vettore 3D per calcolare direzione e movimento
import net.minecraft.world.phys.Vec3;
public class KnockbackSword extends SwordItem {
    public  KnockbackSword(){
        // 'super' chiama il costruttore della classe padre (SwordItem)
        super (

                Tiers.DIAMOND,  // Materiale: usa le statistiche del diamante (danno, durabilità)
                3,              // Attack damage bonus: danno aggiuntivo alla base della spada
                -2.4F,          // Attack speed: velocità di attacco (negativo = più lento)
                new Properties()
                        .stacksTo(1)
                        .tab(CreativeModeTab.TAB_COMBAT) // Proprietà: può stare in stack solo di 1 (come tutte le spade)

        );


    }
    // METODO OVERRIDE - Sovrascriviamo il comportamento quando la spada colpisce un nemico
    // Questo metodo viene chiamato automaticamente ogni volta che la spada colpisce un'entità
    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Prima chiamiamo il metodo originale della spada per applicare il danno normale
        // 'super.hurtEnemy' fa tutto quello che farebbe una spada normale (danno, suoni, etc.)
        boolean result = super.hurtEnemy(stack, target, attacker);

        // ORA AGGIUNGIAMO IL NOSTRO COMPORTAMENTO PERSONALIZZATO
        // Controlliamo se:
        // 1. Chi attacca è un Player (non un mob che usa la spada)
        // 2. Non siamo sul client (per evitare duplicati in multiplayer)
        if (attacker instanceof Player player ) {
            // Ottiene il vettore di direzione dello sguardo del giocatore
            // Se il giocatore guarda a destra, il vettore avrà x positivo, etc.
            Vec3 lookVec = player.getLookAngle();

            // Forza del knockback - puoi modificare questo valore
            double knockbackStrength = 3.5; // 2.5 è abbastanza forte ma non eccessivo

            // Imposta il movimento del bersaglio (il nemico colpito)
            target.setDeltaMovement(
                    lookVec.x * knockbackStrength, // Movimento orizzontale (avanti/indietro)
                    lookVec.y * knockbackStrength + 2.5, // Movimento verticale + piccolo sollevamento extra
                    lookVec.z * knockbackStrength  // Movimento orizzontale (destra/sinistra)
            );

            // QUESTA RIGA È FONDAMENTALE:
            // Dice al gioco che il movimento è stato modificato forzatamente
            // Senza questo, il movimento potrebbe essere ignorato o sovrascritto
            target.hurtMarked = true;

            // OPZIONALE: Messaggio di debug che appare in chat
            // player.sendSystemMessage(Component.literal("§6Spada respingente attivata!"));
        }

        // Restituisce il risultato del metodo originale
        // Di solito è 'true' se il colpo è andato a buon fine
        return result;
    }
}
