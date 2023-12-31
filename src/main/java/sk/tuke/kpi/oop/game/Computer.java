package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {

    private boolean flow;
    public Computer(){
        this.flow = false;
        Animation animationComputer = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animationComputer);
    }
    public int add(int a, int b){
        if(!flow){
            return 0;
        }
        return a + b;
    }
    public float add(float a, float b){
        if(!flow){
            return 0;
        }
        return a + b;
    }
    public int sub(int a, int b){
        if(!flow){
            return 0;
        }
        return a - b;
    }
    public float sub(float a, float b){
        if(!flow){
            return 0;
        }
        return a - b;
    }



    public void setPowered(boolean flow){
        this.flow = flow;
    }
}
