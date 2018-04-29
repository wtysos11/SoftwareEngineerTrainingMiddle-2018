import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

public class KingCrab extends CrabCritter
{
	public KingCrab()
	{
		setColor(Color.YELLOW);
	}

    /*
        override the method processActors from class Critter
        If actor can move away from KingCrab, it move.
        Otherwise, remove it.
    */
    public void processActors(ArrayList<Actor> actors)
    {
        Grid gr = getGrid();
        Location loc = getLocation();

        for (Actor a : actors)
        {
            if (!(a instanceof Critter))
            {
                Location eleLoc = a.getLocation();
                int direction = countDirection(loc,eleLoc);
                Location aimLoc = eleLoc.getAdjacentLocation(direction);
                
                //judge whether can move. If can't move, then remove this.
                if (gr.isValid(aimLoc)) 
                {
                    a.moveTo(aimLoc);
                } 
                else 
                {
                    a.removeSelfFromGrid();
                }
            }
        }
    }
    protected int countDirection(Location origin,Location dest)
    {
        int rowNum = dest.getRow()-origin.getRow();
        int colNum = dest.getCol()-origin.getCol();
        //on the top
        if(rowNum == -1)
        {
            if(colNum == -1)
            {
                return Location.NORTHWEST;
            }
            else if(colNum == 0)
            {
                return Location.NORTH;
            }
            else
            {
                return Location.NORTHEAST;
            }
        }
        //on that line
        else if(rowNum == 0)
        {
            if(colNum == -1)
            {
                return Location.WEST;
            }
            else if(colNum == 1)
            {
                return Location.EAST;
            }

        }
        //on the bottom
        else
        {
            if(colNum == -1)
            {
                return Location.SOUTHWEST;
            }
            else if(colNum == 0)
            {
                return Location.SOUTH;
            }
            else
            {
                return Location.SOUTHEAST;
            }           
        }
        return Location.NORTH;
    }
}