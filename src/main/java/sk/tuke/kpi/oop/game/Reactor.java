package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean running;
    private Set<EnergyConsumer> devices;
    private EnergyConsumer energyConsumer;

    private Animation normalAnimation, normalAnimation_hot, normalAnimation_broken, normalAnimation_on;

    public Reactor(){
        this.temperature = 0;
        this.damage = 0;
        this.running = false;


        devices = new HashSet<>();
        this.normalAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.normalAnimation_broken = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        this.normalAnimation_hot = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        this.normalAnimation_on = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
         setAnimation(normalAnimation);


    }
    public int getTemperature(){
        return this.temperature;
    }
    public int getDamage(){
        return this.damage;
    }
    public void increaseTemperature(int increment){

            if(!running || increment<=0){
                return;
            }
        if(damage >=100){
            turnOff();
            return;
        }

        if(getDamage()<33){
                    temperature= temperature + increment;
                }else{
                    if(getDamage()<=66){
                        temperature= temperature + (int)Math.ceil(increment*1.5);
                    }else {
                        temperature= temperature + (2*increment);
                    }
                }

        if(getTemperature()>=2000){
                    if(getTemperature()>=6000){
                        damage = 100;
                    }else {
                        damage =   ((temperature-2000) / 40) ;
                    }
                }
            damage=(int) Math.floor(damage);
             if(damage >=100){
                turnOff();
            }
            updateAnimation();

        }
        public void  decreaseTemperature(int decrement) {
            if(damage>=100 ||!isOn() || decrement<=0 || temperature<=0){
                return;
            }
            if (getDamage() >= 50 && getDamage()<100) {
                this.temperature = this.temperature - (decrement / 2);
            }else{
                this.temperature = this.temperature - decrement;
            }
            updateAnimation();
        }

        public void updateAnimation(){
            if(getDamage()>=100){
                setAnimation(normalAnimation_broken);

            }
            if(isOn()){
                if(getTemperature()>4000 && getDamage()<100){
                    setAnimation(normalAnimation_hot);
                }

                if(getTemperature()<=4000){
                    setAnimation(normalAnimation_on);

                }

            }else{
                if(getDamage()<100 ){
                    setAnimation(normalAnimation);

                }
            }




}       @Override
        public boolean repair () {

            if(getDamage()==0){
                return false;
            }
             if (getDamage() < 50){

                this.damage = 0;

             }
            if (getDamage() >= 50) {
                this.damage = this.damage - 50;
            }
            temperature = (damage*40)+2000;
            return true;

    }
        @Override
        public void turnOn(){

            if(damage >= 100) {
                running= false;
                return;
            }
            running=true;
            updateAnimation();
            for (EnergyConsumer energyConsumer : this.devices)
            {
                energyConsumer.setPowered(true);

            }

        }
        @Override
        public void turnOff(){
            running=false;
            this.temperature=getTemperature();
            updateAnimation();
                for (EnergyConsumer energyConsumer : this.devices)
                {
                    energyConsumer.setPowered(false);
                }
        }
         @Override
        public boolean isOn(){
            return running;
        }

        public void addDevice(EnergyConsumer energyConsumer){
        this.energyConsumer =energyConsumer;
        if( energyConsumer != null){
            this.devices.add(energyConsumer);
            energyConsumer.setPowered(running);

        }
        }
        public void removeDevice(@NotNull EnergyConsumer energyConsumer){
            energyConsumer.setPowered(false);
            this.devices.remove(energyConsumer);

        }
    @Override
        public void addedToScene(@NotNull Scene scene) {
            super.addedToScene(scene);
            new PerpetualReactorHeating(1).scheduleFor(this);
        }

        public boolean extinguish(){

            if(this.getDamage()>=100 && this.getTemperature()>4000){
                this.temperature = 4000;
                this.normalAnimation = new Animation("sprites/reactor_extinguished.png");
                setAnimation(this.normalAnimation);
                return true;
            }

            return false;


        }


    public EnergyConsumer getEnergyConsumer() {
        return energyConsumer;
    }
}
