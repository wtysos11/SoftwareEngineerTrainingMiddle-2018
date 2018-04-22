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
            //if there's no actor, let it return.
        }
        /*
        * remove all the rock using tag to identify
        */
        for(Actor actor : actors)
        {
            //use actor's class name to judge.
            //! String's compare must use equals() method rather than ==
            if(actor.getClass().getName().equals("info.gridworld.actor.Rock"))
            {
                actor.removeSelfFromGrid();
            }
        }
    }

}
