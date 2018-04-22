import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;

import java.awt.Color;

public class DancingBugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        int[] process = new int[]{1,5,4,3,2};
        DancingBug alice = new DancingBug(process,4);
        alice.setColor(Color.ORANGE);
        world.add(new Location(3, 3), alice);
        world.show();
    }
}