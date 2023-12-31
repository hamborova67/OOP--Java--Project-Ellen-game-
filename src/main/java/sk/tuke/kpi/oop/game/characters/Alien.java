package sk.tuke.kpi.oop.game.characters;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.List;

public class Alien extends AbstractActor implements Alive, Enemy, Movable {
    private int speed;
    private Health health;
    private Behaviour<? super Alien> behaviour;
    public Alien(){
        Animation alien = new Animation("sprites/alien.png",32,32,0.1f);
        setAnimation(alien);
        speed=4;
        health =  new Health(100,100);
        if(getScene()!=null){
            health.onExhaustion(() -> getScene().removeActor(this));
        }
    }
    public Alien(int healthValue, Behaviour<? super Alien> behaviour){
        Animation alien = new Animation("sprites/alien.png",32,32,0.1f);
        setAnimation(alien);
        speed=4;
        this.behaviour=behaviour;
        health = new Health(healthValue,100);
        if(getScene()!=null){
            health.onExhaustion(() -> getScene().removeActor(this));
        }
    }


    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (behaviour != null) {
            behaviour.setUp(this);
        }

        new Loop<>(
            new ActionSequence<>(
                new Invoke<>(this::drain),
                new Wait<>(0.3f)
            )).scheduleFor(this);

    }

    public void drain() {
        if (getScene() == null) {
            return;
        }
        List<Actor> aliveActorsList;
        aliveActorsList = getScene().getActors();

        for (Actor aliveActor : aliveActorsList) {
            if (aliveActor instanceof Alive && !(aliveActor instanceof Enemy) && this.intersects(aliveActor)) {
                ((Alive) aliveActor).getHealth().drain(3);
                new ActionSequence<>(
                    new Invoke<>(this::drain),
                    new Wait<>(1)

                ).scheduleFor(this);
            }

        }
    }
}




