import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private boolean begin;
    private int process;
    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
    	begin = false;
        steps = 0;
        process = 0;
        sideLength = length;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
    	if(begin == false)
    	{
    		begin = true;
    		setDirection(Location.EAST);
    	}
    	
    	if(steps < sideLength && canMove())
    	{
    		move();
    		steps++;
    	}
    	else
    	{
    		if(canMove() == false || process > 2)
    		{
    			return;
    		}
    		
    		if(process == 0)
    		{
    			process = 1;
    			steps = 0;
    			setDirection(getDirection()+Location.RIGHT + Location.HALF_RIGHT);
    		}
    		else if(process == 1)
    		{
    			process = 2;
    			steps = 0;
    			setDirection(getDirection()+Location.LEFT+Location.HALF_LEFT);
    		}
    		else
    		{
    			process = 3;
    			return;
    		}
    	}
    	
    }
}
