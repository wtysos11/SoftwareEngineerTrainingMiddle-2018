import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;
public class RockHound extends Critter 
{
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n == 0)
        {
            return;
        }
        Grid<Actor> gr = getGrid();
        for(Actor actor : actors)
        {
            Location loc = actor.getLocation();
            if(actor.getClass().getName()=="info.gridworld.actor.Rock")
            {
                actor.removeSelfFromGrid();
            }
        }
    }

}
