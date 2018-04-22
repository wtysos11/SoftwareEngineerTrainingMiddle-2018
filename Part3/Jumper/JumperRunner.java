
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.actor.Rock;
import java.awt.Color;

/**
 * This class runs a world that contains box bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class JumperRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        Jumper alice = new Jumper(6);
        alice.setColor(Color.ORANGE);
        world.add(new Location(7, 8), alice);
        world.add(new Location(6, 8), new Rock());
        world.add(new Location(4, 0), new Rock());
        world.add(new Location(5, 0), new Rock());
        world.add(new Location(9, 0), new Rock());
        world.add(new Location(5, 9), new Rock());
        world.add(new Location(8, 9), new Rock());
        world.add(new Location(2,0),new Jumper(4));
        
        world.show();
    }
}