package com.example.agarioclientsae;

public class EatPastilleAI implements IStrategyAI{

    @Override
    public double[] move(World world, Enemy enemy) {

            enemy.setClosestEntityDistance(enemy.distanceTo(world.getPlayers().get(0).getPosition()));
            enemy.setClosestEntity(world.getPlayers().get(0));

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
