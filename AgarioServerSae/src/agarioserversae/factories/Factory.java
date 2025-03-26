package agarioserversae.factories;

import javafx.scene.Group;

public interface Factory<Entity> {

    Entity create(Group group, double weight);
}
