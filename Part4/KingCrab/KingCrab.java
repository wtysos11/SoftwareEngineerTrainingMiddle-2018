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
        System.out.println("In function processActors");
        Grid<Actor> gr=getGrid();
        Location loc = getLocation();
        System.out.println("location is "+loc.toString());
        for(Actor a : actors)
        {
            System.out.println(a.toString());
            /*
                make a line that points from KingCrab to a, then check whether actors can go to the cell on that line step 1 from actor. If not, remove
            */
            Location aLoc = a.getLocation();
            Location next = aLoc.getAdjacentLocation(countDirection(loc,aLoc));/*
            System.out.println("actor location: "+aLoc.toString());
            System.out.println("next location: "+next.toString());
            System.out.println("direction:"+countDirection(loc,aLoc));*/
            if(gr.isValid(next))
            {
                a.moveTo(next);
            }
            else
            {
                a.removeSelfFromGrid();
            }
        }
    }
    
    protected int countDirection(Location origin,Location dest)
    {
        int rowNum = dest.getRow()-origin.getRow();
        int colNum = dest.getCol()-origin.getCol();
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