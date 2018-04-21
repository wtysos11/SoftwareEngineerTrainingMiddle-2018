import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;
public class ChameleonKid extends ChameleonCritter 
{
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n == 0)
        {
            getDark();
            return;
        }
        Grid<Actor> gr = getGrid();
        Location loc = getLocation();
        Location front = loc.getAdjacentLocation(Location.NORTH);
        Location back = loc.getAdjacentLocation(Location.SOUTH);
        if(gr.get(front)!=null)
        {
        	setColor(gr.get(front).getColor());
        }
        else if(gr.get(back)!=null)
        {
        	setColor(gr.get(back).getColor());
        }
        else
        {
        	getDark();
        }
    }

}
