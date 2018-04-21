import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Rock;
import java.awt.Color;

public class Jumper extends Actor
{
    private int steps;
    private int sideLength;
    private static int maxJump = 2;
    /**
     * use two parameter to describe the jumping message.
     * @param jumpnum how many jump the Jumper do in this move (most is 2 now)
     */
    private int jumpnum;
    /**
     * Constructs a Jumper that traces a square of a given side length
     * @param length the side length
     */
    public Jumper(int length)
    {
        steps = 0;
        sideLength = length;
        jumpnum = 0;
        setColor(Color.RED);
    }

    /**
     * Use jump
     * If the jumper can jump, it jump
     * If the jumper can't jump, it rotate to another direction.
     */
    public void act()
    {   
    	if(jumpnum == maxJump)
    	{
    		jumpnum = 0;
    		steps ++;
    	}
        if(steps<sideLength && canJump())
        {
        	jump();
        	jumpnum++;
        }
        else
        {
        	turn();
        	steps = 0;
        }
    }
    
    private boolean judgeRock(Grid<Actor> gr,Location loc)
    {
    	Actor ele = gr.get(loc);
    	return (ele instanceof Rock);
    	
    }
    
    private void jump()
    {
    	Grid<Actor> gr = getGrid();
    	if(gr == null)
    		return;
    	Location loc = getLocation();
    	Location next = loc.getAdjacentLocation(getDirection());
    	Location far = next.getAdjacentLocation(getDirection());
    	if(gr.isValid(next)&&!judgeRock(gr,next))
    	{
    		moveTo(next);
    	}
    	else if((!gr.isValid(next)||judgeRock(gr,next))&& gr.isValid(far) && !judgeRock(gr,far))
    	{
    		moveTo(far);
    	}
    	else
    	{
    		removeSelfFromGrid();
    	}/*
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location far = next.getAdjacentLocation(getDirection());
        if (gr.isValid(far))
            moveTo(far);
        else
            removeSelfFromGrid();*/
    }
    
    private void turn()
    {
    	setDirection(getDirection()+Location.HALF_RIGHT);
    }
    
    private boolean canJump()
    {
    	Grid<Actor> gr = getGrid();
    	if(gr == null)
    		return false;
    	Location loc = getLocation();
    	Location next = loc.getAdjacentLocation(getDirection());
    	Location far = next.getAdjacentLocation(getDirection());
    	/*
    	 * if next is the edge, or if next has rock and two cells away is the edge, get method can get null but still can't move
    	 * */
    	if(!gr.isValid(next)||(judgeRock(gr,next)&&!gr.isValid(far)))
    		return false;
    	Actor neighbor;
    	if(gr.isValid(next) && !judgeRock(gr, next))
    	{
    		neighbor = gr.get(next);
    	}
    	else
    	{
    		neighbor = gr.get(far);
    	}
    	return (neighbor == null);
    	/*
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        Location far = next.getAdjacentLocation(getDirection());
        if (!gr.isValid(far))
            return false;
        Actor neighbor = gr.get(far);
        return (neighbor == null);*/
    }
}