package sk.tuke.kpi.oop.game.items;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Energy extends AbstractActor implements Usable<Alive>{

    public Energy(){
        Animation energy = new Animation("sprites/energy.png",16,16);
        setAnimation(energy);
    }
    @Override
    public void useWith(Alive actor) {
        if(actor==null){
            return;
        }
        if(getScene()==null){
            return;
        }
        if(actor.getHealth().getValue()>=100){
            return;
        }

        actor.getHealth().restore();
        getScene().removeActor(this);
            //System.out.println("ahoj"+actor.getEnergy());


    }
    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
