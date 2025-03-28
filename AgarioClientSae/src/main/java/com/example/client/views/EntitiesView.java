package com.example.client.views;

import com.example.client.app.HelloApplication;
import com.example.libraries.models.worldElements.Entity;
import com.example.libraries.models.worldElements.WorldModel;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

public class EntitiesView extends Group {

    private ArrayList<Circle> circles = new ArrayList<>();

    private ArrayList<Label> labels = new ArrayList<>();

    private ArrayList<Entity> list = new ArrayList<>();


    public EntitiesView(ArrayList<Entity> entities) {
        list.addAll(entities);
        entityView();
        HelloApplication.root.getChildren().add(this);
    }

    public void entityView() {
        int cp = 0;
        System.out.println(list.size());
        for (Entity entity : list) {
            //System.out.println(entity.getClass().getSimpleName());

            circles.add(new Circle(10 * Math.sqrt(entity.getWeight()), Color.rgb(entity.getR(), entity.getG(), entity.getB(), 0.99)));
            circles.get(cp).setCenterX(entity.getX());
            circles.get(cp).setCenterY(entity.getY());


            labels.add(new Label("Entity"));
            labels.get(cp).setTextFill(Color.WHITE);
            labels.get(cp).layoutXProperty().bind(circles.get(cp).centerXProperty().subtract(labels.get(cp).widthProperty().divide(2)));
            labels.get(cp).layoutYProperty().bind(circles.get(cp).centerYProperty().subtract(labels.get(cp).heightProperty().divide(2)));

            getChildren().addAll(circles.get(cp), labels.get(cp));
            cp++;
        }
    }

    public void updateView() {
        System.out.println("Nombre d'éléments à supprimer : " + WorldModel.getInstance().getQueuedObjectsForDeletion().size());
        ArrayList<Entity> toRemove = new ArrayList<>(); // Liste temporaire pour les entités à supprimer

        WorldModel.getInstance().getQueuedObjectsForDeletion().forEach(entityToDelete -> {
            //System.out.println("Entité introuvable dans la liste : " + entityToDelete);

            int indexToRemove = list.indexOf(entityToDelete); // Trouve l'index de l'entité à supprimer
            if (indexToRemove != -1) { // Si elle existe dans la liste
                // Supprimer les éléments graphiques (cercle et label) du Group
                Circle circleToRemove = circles.get(indexToRemove);
                Label labelToRemove = labels.get(indexToRemove);

                // Supprime les éléments graphiques du groupe
                getChildren().remove(circleToRemove);
                getChildren().remove(labelToRemove);

                // Supprime les éléments graphiques des listes respectives
                circles.remove(indexToRemove);
                labels.remove(indexToRemove);

                // Supprimer l'entité de la liste logique
                list.remove(indexToRemove);
                //System.out.println("Entité supprimée graphiquement et logiquement.");
            }

            else {

            }
            // Ajoute l'entité à la liste temporaire de nettoyage
            toRemove.add(entityToDelete);
        });

        // Nettoyer les objets de "queuedObjectsForDeletion"
        freeQueuedObjects();
        //System.out.println("Liste queuedObjectsForDeletion nettoyée.");
    }

    public void freeQueuedObjects() {
        HelloApplication.root.getChildren().removeAll(WorldModel.getInstance().getQueuedObjectsForDeletion());
        WorldModel.getInstance().clearQueudObjects();
    }
}
