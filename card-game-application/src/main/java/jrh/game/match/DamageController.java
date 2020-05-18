package jrh.game.match;

import jrh.game.event.impl.PlayerTookDamage;

public class DamageController {

    private final Match match;

    public DamageController(Match match) {
        this.match = match;
    }

    public void damage(Player player, int amount) {
        player.damage(amount);
        match.getEventBus().dispatch(new PlayerTookDamage(player));
    }
}
