/*** the jumper class
 * @author wty
 * 
 * */

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;

import java.awt.Color;

public class Jumper extends Actor
{
	public Jumper()
	{
		setColor(Color.RED);
	}
	
	public Jumper(Color jumpColor)
	{
		setColor(jumpColor);
	}
	
	public void act()
	{
		/*
		 * jump when can jump.
		 * Else move when can move.
		 * Else turn .
		 * */
		if(canJump())
		{
			jump();
		}
		else if(canMove())
		{
			move();
		}
		else
		{
			turn();
		}
	}
	
	public boolean canJump()
    {
    	/*
    	 * judge whether to jump.
    	 * Location is valid.
    	 * Location that is two steps shouldn't be a rock or other things except for flower.
    	 * 
    	 * */
    	Grid<Actor> gr = getGrid();
    	if(gr == null)
        {
    		return false;
        }
    	/*
    	 * use gridworld's form to judge whether a location can jump
    	 * */
    	Location loc = getLocation();
    	Location next = loc.getAdjacentLocation(getDirection());
    	if(!gr.isValid(next))
    	{
    		return false;
    	}
    	
    	Location far = next.getAdjacentLocation(getDirection());
    	if(!gr.isValid(far))
    	{
    		return false;
    	}
    	
    	Actor neighbor = gr.get(far);
    	return (neighbor==null)||(neighbor instanceof Flower);
    }
	
    public void jump()
    {
    	Grid<Actor> gr = getGrid();
    	if(gr == null)
        {
    		throw new IllegalStateException("gr is NULL, please call canJump before calling jump method.");
        }
    	/*
    	 * use gridworld's form to judge whether a location can jump
    	 * */
    	Location loc = getLocation();
    	Location next = loc.getAdjacentLocation(getDirection());
    	if(!gr.isValid(next))
    	{
    		throw new IllegalStateException("next is out of grid, please call canJump before calling jump method.");
    	}
    	
    	Location far = next.getAdjacentLocation(getDirection());
    	if(!gr.isValid(far))
    	{
    		throw new IllegalStateException("far is out of grid, please call canJump before calling jump method.");
    	}

        moveTo(far);

    }
    
    /*
     * the code is from the Bug.java because they act the same.
     * */
    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
        {
            return false;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
        {
            return false;
        }
        Actor neighbor = gr.get(next);
        return (neighbor == null) || (neighbor instanceof Flower);
    }
    
    /*
     * move method also look similar to Bug.java's except the flower.
     * */
    public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
        {
            return;
        }
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
        {
            moveTo(next);
        }
        else
        {
            removeSelfFromGrid();
        }
    }
    
    /*
     * turn 45 degrees, just like this in Bug.java
     * */
    public void turn()
    {
    	setDirection(getDirection() + Location.HALF_RIGHT);
    }
}