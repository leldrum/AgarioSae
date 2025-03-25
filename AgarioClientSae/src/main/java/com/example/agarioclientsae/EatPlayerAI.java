package com.example.agarioclientsae;

public class EatPlayerAI implements IStrategyAI{

    public double[] move(World world, Enemy enemy){
        //move player towards the mouse position

        enemy.setClosestEntityDistance(enemy.distanceTo(world.getPlayers().get(0).getPosition()));
        enemy.setClosestEntity(world.getPlayers().get(0));

        world.root.getChildren().forEach(entity ->{
            switch (entity) {
                case MoveableBody each:
                    if (!each.equals(enemy)){
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

    @Override
    public void move() {

    }

}
