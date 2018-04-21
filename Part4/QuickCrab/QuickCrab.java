import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class QuickCrab extends CrabCritter
{
	public QuickCrab()
	{
		setColor(Color.RED);
	}
    public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid<Actor> gr = getGrid();
        Location loc = getLocation();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc))
                locs.add(neighborLoc);
            else
            	continue;
            //the second step
            Location nextLoc = neighborLoc.getAdjacentLocation(getDirection() + d);
                if (gr.isValid(nextLoc))
                locs.add(nextLoc);
        }
        return locs;
    }    
}