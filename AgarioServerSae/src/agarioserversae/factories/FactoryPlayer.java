package agarioserversae.factories;

import agarioserversae.worldElements.World;
import agarioserversae.player.Player;
import javafx.scene.Group;

public class FactoryPlayer implements Factory<Player> {

    @Override
    public Player create(Group group, double weight) {
        World world = World.getInstance();
        Player p = new Player(group, weight);
        world.addEntity(p);
        return p;
    }

}
