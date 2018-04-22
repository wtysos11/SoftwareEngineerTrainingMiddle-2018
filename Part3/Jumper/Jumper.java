import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Rock;
import java.awt.Color;

public class Jumper extends Actor
{
    private int steps;
    private int sideLength;
    private static int maxMove = 2;
    private boolean moving;
    /**
     * use two parameter to describe the jumping message.
     * @param moveNum how many jump the Jumper do in this move (most is 2 now)
     */
    private int moveNum;
    /**
     * Constructs a Jumper that traces a square of a given side length
     * @param length the side length
     */
    public Jumper(int length)
    {
        steps = 0;
        sideLength = length;
        moveNum = 0;
        moving = false;
        setColor(Color.RED);
    }

    /**
     * Use jump
     * If the jumper can jump, it jump
     * If the jumper can't jump, it rotate to another direction.
     */
    public void act()
    {   
        if(moving == false)
        {
            if(steps<sideLength && canJump())
            {
                jump();
                steps+=2;
            }
            else if(steps >= sideLength)
            {
                turn();
                steps=0;
            }
            /*
                special situation zero:
                if can't jump but can move,let it move
            */
            else if(!canJump())
            {
                if(canMove())
                {
                    moving = true;
                    move();
                    steps++;
                    moveNum=1;
                }
                else
                {
                    turn();
                    steps=0;
                }
            }
        }
        else
        {
            if(steps<sideLength && canMove() && moveNum<maxMove)
            {
                move();
                steps++;
                moveNum++;
            }
            /*
                special situation one:
                move two steps, should change to jump
                to avoid hesitate, if it can jump, then let it jump first.
            */
            else if(moveNum==maxMove)
            {
                moving = false;
                if(canJump())
                {
                    jump();
                    steps+=2;
                }
            }
            /*
                specail situation two:
                if can't move but can jump, then change to jump
            */
            else if(!canMove()&&canJump())
            {
                moving = false;
                jump();
                steps+=2;
            }
            else
            {
                turn();
                steps=0;
            }
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
        {
    		return;
        }
        /*
            only consider about far
            if far is empty, then jump to far
            if far is not empty,then switch to move
        */
    	Location loc = getLocation();
    	Location next = loc.getAdjacentLocation(getDirection());
    	Location far = next.getAdjacentLocation(getDirection());
        if(gr.isValid(far)&&!judgeRock(gr,far))
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
        {
    		return false;
        }
    	Location loc = getLocation();
    	Location next = loc.getAdjacentLocation(getDirection());
    	Location far = next.getAdjacentLocation(getDirection());
    	/*
    	 * if next is the edge, or if next has rock and two cells away is the edge, get method can get null but still can't move
    	 * */
    	if(!gr.isValid(far)||(judgeRock(gr,far)))
        {
    		return false;
        }
        Actor neighbor = gr.get(far);
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
    /**
     * Tests whether this bug can move forward into a location that is empty or
     * contains a flower.
     * @return true if this bug can move.
     */
    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (!gr.isValid(next))
            return false;
        Actor neighbor = gr.get(next);
        return (neighbor == null);
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
    public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return;
        Location loc = getLocation();
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
    }
}
