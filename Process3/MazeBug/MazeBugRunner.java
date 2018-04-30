//package info.gridworld.maze;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.grid.UnboundedGrid;
import info.gridworld.actor.Rock;
import java.awt.Color;

/**
 * This class runs a world that contains maze bugs. <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class MazeBugRunner
{
    public static void main(String[] args)
    {
        //UnboundedGrid ugr=new UnboundedGrid();
        ActorWorld world = new ActorWorld(); 
        world.add(new Location(0,0), new Rock());
        world.add(new Location(0,1), new Rock());
        world.add(new Location(0,2), new Rock());
        world.add(new Location(0,3), new Rock());
        world.add(new Location(0,4), new Rock());
        world.add(new Location(0,5), new Rock());
        world.add(new Location(1,0), new Rock());
        world.add(new Location(1,5), new Rock());
        world.add(new Location(2,0), new Rock());
        world.add(new Location(2,1), new Rock());
        world.add(new Location(2,3), new Rock());
        world.add(new Location(2,4), new Rock());
        world.add(new Location(3,0), new Rock());
        world.add(new Location(4,1), new Rock());
        world.add(new Location(3,5), new Rock(Color.RED));
        world.add(new Location(4,3), new Rock());
        world.add(new Location(4,4), new Rock());
        world.add(new Location(5,2), new Rock());
        world.add(new Location(1,1), new BasicMazeBug());
        //world.add(new Location(2,2), new MazeBug2());
        world.show();
    }
}