package factories;

import com.example.agarioclientsae.worldElements.Food;
import com.example.agarioclientsae.worldElements.World;
import javafx.scene.Group;

public class FactoryFood implements Factory<Food> {


    @Override
    public Food create(Group group, double weight) {
        World world = World.getInstance();
        Food food = new Food(group, weight);
        world.addEntity(food);


        return food;
    }
}
