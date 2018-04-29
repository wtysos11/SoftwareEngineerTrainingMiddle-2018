import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

public class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private int process;
	private boolean goon;
    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
		goon = true;
        steps = 0;
        process = 0;
        sideLength = length;
		setDirection(Location.EAST);
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
    	
    	if(steps < sideLength && canMove() && goon==true)
    	{
    		move();
    		steps++;
    	}
    	else if(goon == true)
    	{
			//如果没有走到指定步数就不能移动，或者超过过程
    		if(process>2 || (!canMove()&&steps<sideLength))
    		{
				goon = false;
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
