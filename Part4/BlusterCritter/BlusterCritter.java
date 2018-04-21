import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;
import java.awt.Color;

public class BlusterCritter extends Critter 
{
    private static final double COLOR_FACTOR = 0.20;
    private int _count;
    public BlusterCritter(int count)
    {
        _count = count;
    }
    /*
    override Critter's method.
    it get neighbors from 24 cells that 2 steps away from origin location. Which means isValid() is true and the grid.get()!=null
    */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors=new ArrayList<Actor>();
        Grid<Actor> gr=getGrid();

        Location loc = getLocation();
        /*
        Location left,right,up,bottom;
        int leftm=0,rightm=0,upm=0,bottomm=0;
        //calculate range of valid search.
        left = loc.getAdjacentLocation(Location.WEST);
        if(gr.isValid(left))
        {
            leftm=-1;
        }
        left = left.getAdjacentLocation(Location.WEST);
        if(gr.isValid(left))
        {
            leftm=-2;
        }

        right = loc.getAdjacentLocation(Location.EAST);
        if(gr.isValid(right))
        {
            rightm=1;
        }
        right = right.getAdjacentLocation(Location.EAST);
        if(gr.isValid(right))
        {
            rightm=2;
        }

        up = loc.getAdjacentLocation(Location.NORTH);
        if(gr.isValid(up))
        {
            upm=-1;
        }
        up = up.getAdjacentLocation(Location.NORTH);
        if(gr.isValid(up))
        {
            upm=-2;
        }

        bottom = loc.getAdjacentLocation(Location.SOUTH);
        if(gr.isValid(bottom))
        {
            bottomm=1;
        }
        bottom = bottom.getAdjacentLocation(Location.SOUTH);
        if(gr.isValid(bottom))
        {
            bottomm=2;
        }*/

        for(int i=-2;i<=2;i++)
        {
            for(int j=-2;j<=2;j++)
            {
                Location newLoc = new Location(loc.getRow()+i,loc.getCol()+j);
                /*
                    if location is not valid, or the location is origin, then it can't be a neighbor
                */
                if(!gr.isValid(newLoc)||(i==0&&j==0))
                    continue;

                if(gr.get(newLoc)!=null)
                {
                    actors.add(gr.get(newLoc));
                }
            }
        }
        return actors;
    }

    /*
    override the act method. When there are few critters of neighbor than c, the color get brighter. Otherwise, get darken.
    */
    public void act()
    {
        ArrayList<Actor> actors = getActors();
        if(actors.size()>=_count)
        {
            Color c=getColor();
            int red = (int)(c.getRed()*(1-COLOR_FACTOR ));
            int green = (int)(c.getGreen()*(1-COLOR_FACTOR ));
            int blue = (int)(c.getBlue()*(1-COLOR_FACTOR ));
            setColor(new Color(red,green,blue));
        }
        else
        {
            Color c=getColor();
            int red = (int)(c.getRed()*(1+COLOR_FACTOR ));
            int green = (int)(c.getGreen()*(1+COLOR_FACTOR ));
            int blue = (int)(c.getBlue()*(1+COLOR_FACTOR ));
            if(red>255)
                red=255;
            if(green>255)
                green=255;
            if(blue>255)
                blue=255;
            setColor(new Color(red,green,blue));
        }
    }

}
