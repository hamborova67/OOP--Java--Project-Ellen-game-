package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor implements Switchable {

    private Switchable switchable;
    private boolean switch01;
    public PowerSwitch(Switchable switchable){

        this.switchable =  switchable;
        Animation controllerAnimation = new Animation("sprites/switch.png",16,16);
        setAnimation(controllerAnimation);

    }

    public Switchable getDevice(){
        return this.switchable;
    }

    public void switchOn(){
            if(switchable == null){
                return;
            }
            switchable.turnOn();
            getAnimation().setTint(Color.WHITE);
    }

    public void switchOff(){
            if(switchable == null){
                return;
            }
            switchable.turnOff();
            getAnimation().setTint(Color.GRAY);

    }

    /*
    public void toggle(){
        if(switchable.isOn() == true ){
            switchable.turnOn();
            return;
        }
        switchable.turnOff();
    }
     */

    public void turnOn(){
        this.switch01 = true;
    }
    public void turnOff(){
        this.switch01 = false;
    }
    public boolean isOn(){
        return this.switch01;
    }







}
