import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

public class DancingBug extends Bug
{
    private int steps;
    private int sideLength;
    private int [] store;
    private int process;
    private boolean rotate;
    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public DancingBug(int[] cache,int length)
    {
    	sideLength = length;
    	if(cache!=null)
    	{
    		store = new int[cache.length];
    		System.arraycopy(cache, 0, store, 0, cache.length);
    		process = 0;
    		rotate = false;
    	}
    	else
    	{
    		process = -1;
    	}
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
    	if(process!=-1 && rotate == false)
    	{
    		setDirection(getDirection()+store[process]*Location.HALF_RIGHT);
    		rotate = true;
    		process ++;
    		if(process == store.length)
    		{
    			process = 0;
    		}
    	}

        if (steps < sideLength && canMove())
        {
            move();
            steps++;
        }
        else
        {
        	rotate = false;
            steps = 0;
        }
    }
}
