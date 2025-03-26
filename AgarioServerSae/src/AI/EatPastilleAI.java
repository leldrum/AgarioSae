package AI;

import com.example.agarioclientsae.worldElements.Enemy;
import com.example.agarioclientsae.worldElements.World;
import com.example.agarioclientsae.worldElements.Food;
import javafx.scene.Node;

public class EatPastilleAI implements IStrategyAI {

    @Override
    public double[] move(Enemy enemy) {

        World world = World.getInstance();
        // Initialiser la distance minimale à une valeur élevée
        double minDistance = Double.MAX_VALUE;
        Food closestFood = null;

        // Parcourir tous les nœuds de la scène pour trouver les pastilles de nourriture
        for (Node node : world.root.getChildren()) {
            if (node instanceof Food) {
                Food food = (Food) node;
                double distance = enemy.distanceTo(food.getPosition());

                // Vérifier si cette pastille est plus proche que la précédente
                if (distance < minDistance) {
                    minDistance = distance;
                    closestFood = food;
                }
            }
        }

        // Si une pastille de nourriture a été trouvée, calculer le vecteur de déplacement vers elle
        if (closestFood != null) {
            double[] foodPosition = closestFood.getPosition();
            return new double[]{foodPosition[0], foodPosition[1]};
        }

        // Si aucune nourriture n'est trouvée, rester immobile
        return new double[]{enemy.getPosition()[0], enemy.getPosition()[1]};
    }


    public void move(){

    }
}
