package com.example.agarioclientsae;

public class EatPastilleAI implements IStrategyAI{

    @Override
    public double[] move(Enemy enemy) {

            World world = World.getInstance();



            enemy.setClosestEntityDistance(enemy.distanceTo(world.getPlayer().getPosition()));
            enemy.setClosestEntity(world.getPlayer());

            world.root.getChildren().forEach(entity ->{
                switch (entity) {
                    case MoveableBody each:
                        if (each.equals(enemy)){
                            if (enemy.distanceTo(each.getPosition()) < enemy.getClosestEntityDistance()) {
                                enemy.setClosestEntityDistance(enemy.distanceTo(each.getPosition()));
                                enemy.setClosestEntity(each);

                            }
                        }

                    default : break;
                }

            });



            return enemy.getClosestEntity().getPosition();


        }

    public void move(){

    }
}
