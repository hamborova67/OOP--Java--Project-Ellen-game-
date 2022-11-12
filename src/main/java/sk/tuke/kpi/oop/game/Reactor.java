package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean running;
    private Light light;
    private Computer comp;
    private Set<EnergyConsumer> devices;
    private EnergyConsumer energyConsumer;
    private Animation normalAnimation;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.running = false;
        this.light = light;
        this.comp = comp;

        devices = new HashSet<>();
        this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        turnOff();

    }
    public int getTemperature(){
        return this.temperature;
    }
    public int getDamage(){
        return this.damage;
    }
    public void increaseTemperature(int increment){
            if(!running){
                return;
            }
            this.temperature =  this.temperature + increment;

            if(getTemperature()>=2000){
                this.damage =   ((this.temperature-2000) / 40) ;
            }
            if(getDamage()>=33 ){
                if(getDamage()>=66 ){
                   this.temperature = this.temperature + increment+(increment/2);
                   this.damage =   ((this.temperature-2000) / 40) ;
                }else{
                    this.temperature = this.temperature + (increment*2);
                    this.damage =   ((this.temperature-2000) / 40) ;
                }
            }
            if(getDamage()>100 && getTemperature()>6000){
                this.damage = 100;
                this.temperature = 6000;
                turnOff();


            }

        updateAnimation();
        }
        public void  decreaseTemperature(int decrement) {

            if (getDamage() >= 50) {
                this.temperature = this.temperature - (decrement / 2);
                if (getDamage() >= 100) {
                    this.temperature = getTemperature();
                }

            } else {
                this.temperature = this.temperature - decrement;
            }

            updateAnimation();
        }

        public void updateAnimation(){
                if(getTemperature()>=4000){
                    this.normalAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
                    setAnimation(normalAnimation);
                }
                if(getTemperature()>=6000){
                    this.normalAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
                    setAnimation(normalAnimation);
                }
                if(getTemperature()<=4000){
                    this.normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
                    setAnimation(normalAnimation);
                }


}       @Override
        public boolean repair (){

            if(getDamage()>=50){
                this.damage=this.damage-50;

            }else{
                this.damage=0;

            }
            // Math.max(0,30);Math.max(0,-30);

            return true;
        }
        @Override
        public void turnOn(){
            running=true;
        }
        @Override
        public void turnOff(){
            running=false;
            this.temperature=getTemperature();
            updateAnimation();

        }
         @Override
        public boolean isOn(){
            return running;
        }


        public void addLight(Light light){
            this.addDevice(light);

        }
        public void removeLight(Light light){
            this.removeDevice(light);


        }

        public void addDevice(EnergyConsumer energyConsumer){
        if( energyConsumer != null){
            this.devices.add(energyConsumer);
            energyConsumer.setPowered(true);
        }


        }
        public void removeDevice(EnergyConsumer energyConsumer){
            energyConsumer.setPowered(false);
            this.devices.remove(energyConsumer);

        }
    @Override
        public void addedToScene(@NotNull Scene scene) {
            super.addedToScene(scene);
            new PerpetualReactorHeating(1).scheduleFor(this);
        }

        public boolean extinguish(FireExtinguisher fireExtinguisher){
            if(fireExtinguisher==null){
                return false;
            }
            if(this.getDamage()==100 && this.getTemperature()>4000){
                this.temperature = 4000;
                this.normalAnimation = new Animation("sprites/reactor_extinguished.png");
                setAnimation(this.normalAnimation);
                fireExtinguisher.useWith();
                return true;
            }
            fireExtinguisher.useWith();
            return false;


        }



}
