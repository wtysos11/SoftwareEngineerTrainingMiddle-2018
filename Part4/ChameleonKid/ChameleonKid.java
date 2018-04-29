
import info.gridworld.grid.Grid;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;

import java.util.ArrayList;
import java.awt.Color;

/*
 * this class get the color of its front or back. And the color will darken if there's none.
 * Very like CrabCritter and therefore many codes are similar.
 * */
public class ChameleonKid extends ChameleonCritter
{
	//darken factor just like other
	private static final double DARKEN_FACTOR = 0.15;

    /**
     * A Chameleonkid gets the actors in the two location immediately in front and back to get their color
     * @return a list of actors occupying these locations
     */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs =
            { Location.AHEAD, Location.HALF_CIRCLE };//front and back.
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null)
            {
                actors.add(a);
            }
        }

        return actors;
    }
    
    /**
     * Finds the valid adjacent locations of this critter in different
     * directions.
     * @param directions - an array of directions (which are relative to the
     * current direction)
     * @return a set of valid locations that are neighbors of the current
     * location in the given directions
     */
    public ArrayList<Location> getLocationsInDirections(int[] directions)
    {
        ArrayList<Location> locs = new ArrayList<Location>();
        Grid gr = getGrid();
        Location loc = getLocation();
    
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
	
    /**
     * Randomly selects a neighbor from front or back and changes this critter's color to be the
     * same as that neighbor's. If there are no neighbors, the color will darken.
     */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        if (n == 0)
        {
        	Color c=getColor();
        	int red = (int)(c.getRed()*(1-DARKEN_FACTOR));
        	int green = (int)(c.getGreen()*(1-DARKEN_FACTOR));
        	int blue = (int)(c.getBlue()*(1-DARKEN_FACTOR));
        	
        	setColor(new Color(red,green,blue));
        }
        else
        {
            int r = (int) (Math.random() * n);
            Actor other = actors.get(r);
            setColor(other.getColor());
        }
    }
    
}
