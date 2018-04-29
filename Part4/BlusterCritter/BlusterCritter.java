
import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class BlusterCritter extends Critter{

	//factor is the factor that influence the color
    private static final double COLOR_FACTOR = 0.15;
    //to aviod magic number, the max and min of rgb num
    private static final int MAX_RGB = 255;
    private static final int MIN_RGB = 0;
    
    private int courage;
    
    public BlusterCritter(int c)
    {
    	courage = c;
    }
    
    public int getCourage()
    {
    	return courage;
    }

    /**
     * A BlusterCritter gets the actors within two steps of its current location.
     * @return a list of actors occupying these locations
     */
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        Grid<Actor> gr = getGrid();
        Location originLoc = getLocation();
        int x1,x2,y1,y2;
        
        x1 = originLoc.getRow() - 2;
        x2 = originLoc.getRow() + 2;
        y1 = originLoc.getCol() - 2;
        y2 = originLoc.getCol() + 2;
        
        //legalize
        if(x1<0)
        {
        	x1 = 0;
        }
        if(y1<0)
        {
        	y1 = 0;
        }
        if(x2>=gr.getNumRows())
        {
        	x2 = gr.getNumRows()-1;
        }
        if(y2>=gr.getNumCols())
        {
        	y2 = gr.getNumCols()-1;
        }
        
        //search for all actor in the range of x1,x2 y1,y2 and add it to the list.
        for(int i=x1;i<=x2;i++)
        {
        	for(int j=y1;j<=y2;j++)
        	{
        		Location loc = new Location(i,j);
        		Actor a = gr.get(loc);
        		if(a!=null)
        		{
        			actors.add(a);
        		}
        	}
        }

        return actors;
    }
    
    /*
     * process actors.
     * If the actor's number is lower than courage, the color will be brighten.
     * Else, the color will darken.
     * */
    public void processActors(ArrayList<Actor> actors)
    {
        int n = actors.size();
        Color c = getColor();
        if (n >= courage)
        {
            int red = (int) (c.getRed() * (1 - COLOR_FACTOR));
            int green = (int) (c.getGreen() * (1 - COLOR_FACTOR));
            int blue = (int) (c.getBlue() * (1 - COLOR_FACTOR));
            if (red < MIN_RGB)
            {
                red = MIN_RGB;
            }
            if (green < MIN_RGB)
            {
                green = MIN_RGB;
            }
            if (blue < MIN_RGB)
            {
                blue = MIN_RGB;
            }

            setColor(new Color(red, green, blue));
        } else {
            int red = (int) (c.getRed() * (1 + COLOR_FACTOR));
            int green = (int) (c.getGreen() * (1 + COLOR_FACTOR));
            int blue = (int) (c.getBlue() * (1 + COLOR_FACTOR));
            if (red > MAX_RGB)
            {
                red = MAX_RGB;
            }
            if (green > MAX_RGB)
            {
                green = MAX_RGB;
            }
            if (blue > MAX_RGB)
            {
                blue = MAX_RGB;
            }
            setColor(new Color(red, green, blue));
        }
    } 
}
