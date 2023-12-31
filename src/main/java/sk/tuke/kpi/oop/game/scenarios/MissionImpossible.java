package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class MissionImpossible implements SceneListener {
    private Ripley ripley = new Ripley();
    public static class Factory implements ActorFactory{


        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            Energy energy = new Energy();
            Ripley ellen = new Ripley();
            return energy;
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {

        scene.addActor(ripley,120,55);
        MovableController mc = new MovableController(ripley);
        scene.getInput().registerListener(mc);
        KeeperController kc = new KeeperController(ripley);
        scene.getInput().registerListener(kc);

        Door door = new Door();
        scene.addActor(door,100,65);

        //KeeperController kc = new KeeperController(ripley);
        //scene.getInput().registerListener(kc);

       // float x=180;
        //Direction directionx = directionx.getAngle(x);
       // System.out.println("DIR "+directionx);



    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        SceneListener.super.sceneUpdating(scene);
        ripley.showRipleyState();
        scene.follow(ripley);
    }
}

