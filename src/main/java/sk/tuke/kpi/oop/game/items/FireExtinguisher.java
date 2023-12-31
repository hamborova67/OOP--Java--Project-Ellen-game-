package sk.tuke.kpi.oop.game.items;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;



public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible{
    private int remainingUses;

    public FireExtinguisher(){
        this(1);

    }
    public FireExtinguisher(int remainingUses){
        super(1);
        Animation extinguisherAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(extinguisherAnimation);
        this.remainingUses = remainingUses;

    }
    public int getRemainingUses(){
        return remainingUses;
    }
    @Override
    public void useWith(Reactor reactor){
        if(reactor==null){
            return;
        }
        if(!reactor.extinguish()){
            return;
        }
        if(this.remainingUses<=0){
            getScene().removeActor(this);
            return;
        }
        reactor.extinguish();
        this.remainingUses--;
        if(this.remainingUses<=0){
            getScene().removeActor(this);

        }
    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return null;
    }


}
