package sk.tuke.kpi.oop.game.characters;
import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

import java.util.List;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed {
    private Animation player;
    private Animation player_died;
    private int energy;

    private int ammo;
    private Backpack ruksak= new Backpack("Ripley's backpack",10);

    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);
    private Health health;
    private int speed;
    private Firearm weapon;
    public Ripley(){
        super("Ellen");
        player_died = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
        player = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(player);
        player.pause();
        health = new Health(100,100);
        setAmmo(400);
        speed=1;
        weapon = new Gun(100,100);
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    public void startedMoving(Direction direction){
        this.player.setRotation(direction.getAngle());
        this.player.play();

    }
    public void stoppedMoving(){
        this.player.pause();
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        this.addedToScene(scene);

    }

    @Override
    public Backpack getBackpack() {
        return ruksak;
    }
    public void showRipleyState(){
        int windowHeight = getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        getScene().getGame().getOverlay().drawText("| Energy: "+this.getEnergy()+" | Ammo: "+this.getAmmo(), 100 , yTextPos);
    }

    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return this.weapon;
    }
    @Override
    public void setFirearm(Firearm weapon) {
        this.weapon =weapon;
    }
    public void restInPeace(){
        this.stoppedMoving();
        if(health.getValue()<=0){
            this.setAnimation(player_died);
            if(getScene()==null){
                return;
            }
            getScene().getMessageBus().publish(RIPLEY_DIED,this);
        }
    }

    public void decreaseHealth(){
        if(health.getValue()>0){
            new Loop<>(
                new ActionSequence<>(
                    new Invoke<>(() -> { if (health.getValue() <= 0) {
                            restInPeace();
                        }
                    else {this.getHealth().drain(1);}
                    }),
                    new Wait<>(2)
                )
            ).scheduleFor(this);

        }
    }

}