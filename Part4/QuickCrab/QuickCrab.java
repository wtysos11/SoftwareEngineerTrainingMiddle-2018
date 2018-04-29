
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class QuickCrab extends CrabCritter{
    /**
     * @return list of empty locations that are two spaces to its right or left.
     */
    public ArrayList<Location> getMoveLocations()
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location currentLoc = getLocation();

        //judge the left
        Location loc1 = currentLoc.getAdjacentLocation(getDirection() + Location.LEFT);
        if (gr.isValid(loc1) && gr.get(loc1) == null)
        {
            int[] dirs1 = { Location.LEFT };
            for (Location loc : getLocationsInOtherDirections(dirs1, loc1))
            {
            	if(gr.isValid(loc))
            	{
            		if (getGrid().get(loc) == null)
            		{
                        locs.add(loc);	
            		}
            	}
            }
        }

        //judge the right
        Location loc2 = currentLoc.getAdjacentLocation(getDirection() + Location.RIGHT);
        if (gr.isValid(loc2) && gr.get(loc2) == null)
        {
            int[] dirs2 = { Location.RIGHT };
            for (Location loc : getLocationsInOtherDirections(dirs2, loc2))
            {
            	if(gr.isValid(loc))
            	{
            		if (getGrid().get(loc) == null)
            		{
                        locs.add(loc);	
            		}
            	}
            }
        }

        return locs;
    }

    /**
     * Similar as those in other file like CrabCritter.java
     * Finds the valid adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are neighbors of the current
     * location in the given directions
     */
    public ArrayList<Location> getLocationsInOtherDirections(int[] directions,Location loc)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
    
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(getDirection() + d);
            if (gr.isValid(neighborLoc))
            {
                locs.add(neighborLoc);
            }

        }
        return locs;
    }      
}
