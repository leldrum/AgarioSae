package com.example.client.views;

import com.example.client.app.HelloApplication;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class EntitiesView extends Group {

    private static HashMap<Entity, Pair<Circle, Label>> entityGraphicsMap = new HashMap<>();

    public EntitiesView(ArrayList<Entity> entities) {
        entities.forEach(this::addEntityGraphics);
        HelloApplication.root.getChildren().add(this);
    }

    /**
     * Crée les éléments (Circle et Label) pour une entité et les ajoute à la vue et au HashMap.
     */
    public void addEntityGraphics(Entity entity) {
        Circle circle = new Circle(10 * Math.sqrt(entity.getWeight()),
                Color.rgb(entity.getR(), entity.getG(), entity.getB(), 0.99));
        circle.setCenterX(entity.getX());
        circle.setCenterY(entity.getY());

        Label label = new Label(String.valueOf(entity.getWeight()));
        label.setTextFill(Color.WHITE);
        label.layoutXProperty().bind(circle.centerXProperty().subtract(label.widthProperty().divide(2)));
        label.layoutYProperty().bind(circle.centerYProperty().subtract(label.heightProperty().divide(2)));

        // Ajouter le Circle et le Label au HashMap pour cette entité
        entityGraphicsMap.put(entity, new Pair<>(circle, label));

        // Ajouter le Circle et le Label au Group pour qu'ils soient affichés
        getChildren().addAll(circle, label);
    }

    /**
     * Mets à jour l'affichage des entités et supprime celles qui ont été marquées pour suppression.
     */
    public void updateView() {
        System.out.println("Nombre d'éléments à supprimer : " + WorldModel.getInstance().getQueuedObjectsForDeletion().size());

        // Supprimer graphiquement les entités marquées pour la suppression
        WorldModel.getInstance().getQueuedObjectsForDeletion().forEach(entityToDelete -> {
            Pair<Circle, Label> graphics = entityGraphicsMap.remove(entityToDelete);
            if (graphics != null) {
                // Supprime le cercle et le label de l'affichage
                getChildren().removeAll(graphics.getKey(), graphics.getValue());
            }
        });

        // Nettoyer les objets supprimés de "queuedObjectsForDeletion"
        freeQueuedObjects();

        // Mettre à jour la position des cercles restants
        entityGraphicsMap.forEach((entity, graphics) -> {
            Circle circle = graphics.getKey();
            circle.setCenterX(entity.getX());
            circle.setCenterY(entity.getY());
        });
    }

    /**
     * Nettoie les entités supprimées de "queuedObjectsForDeletion".
     */
    public void freeQueuedObjects() {
        WorldModel.getInstance().clearQueudObjects();
    }

    /**
     * Obtient le Circle graphique associé à une entité donnée.
     */
    public static Circle getSprite(Entity entity) {
        Pair<Circle, Label> graphics = entityGraphicsMap.get(entity);
        return (graphics != null) ? graphics.getKey() : null;
    }

    public HashMap<Entity, Pair<Circle, Label>> getEntityGraphicsMap() {
        return entityGraphicsMap;
    }
}