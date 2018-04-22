import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.util.ArrayList;
import java.awt.Color;

public class BlusterCritter extends Critter 
{
    private static final double COLOR_FACTOR = 0.20;
    private static final int COLORLMIT = 255;
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
    a loop that check all 24 cells aournd location to find whether it is available.
    ps: notice not to include itself
    if available, add to the list and return.
*/
        for(int i=-2;i<=2;i++)
        {
            for(int j=-2;j<=2;j++)
            {
                Location newLoc = new Location(loc.getRow()+i,loc.getCol()+j);
                /*
                    if location is not valid, or the location is origin, then it can't be a neighbor
                */
                if(!gr.isValid(newLoc)||(i==0&&j==0))
                {
                    continue;
                }

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
        /*
            make rgb's value low can make them dark.
            But make rgb's value high seems not make them bright.
        */
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
            /*
                if rgb's value is greater than 255,then there will be an error. The color won't chagne.
            */
            if(red>COLORLMIT)
            {
                red=COLORLMIT;
            }
            if(green>COLORLMIT)
            {
                green=COLORLMIT;
            }
            if(blue>COLORLMIT)
            {
                blue=COLORLMIT;
            }
            setColor(new Color(red,green,blue));
        }
    }

}
