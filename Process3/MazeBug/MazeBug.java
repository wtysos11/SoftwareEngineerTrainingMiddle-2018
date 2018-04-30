
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
public class MazeBug extends Bug
{
	public int stepLength;//�Ѿ��߹��Ĳ���
	private boolean[][] isVisit;//�Ƿ���ʹ�
	
	//���й���������ջ�Ĳ���Ӧ��ͳһ��act������ֱ�ӹ����ķ����н��У��Ա�֤����ͳһ��
	Stack<Location> cache;//ջ�������洢��ǰ���Ѿ��߹���λ��
	Stack<Stack<Location>> branch;//��������DFSջ��ǰ�漸�����Ϣ
	
	Location next;//store the next location to go
	Location last;//��һ��λ��
	boolean judgeEnd;//�ж��Ƿ��ߵ��յ�
	
	public MazeBug()
	{
		int length = 100;
		isVisit = new boolean[length][length];
		for(int i=0;i<length;i++)
		{
			for(int j=0;j<length;j++)
			{
				isVisit[i][j]=false;
			}
		}
		
		stepLength = 0;
		cache = new Stack<Location>();
		branch = new Stack<Stack<Location>>();
		judgeEnd = false;
		
		//gridworld is not the newest and location is null at the beginning.
		/*
		Location currentLoc = getLocation();
		cache.push(currentLoc);
		isVisit[currentLoc.getRow()][currentLoc.getCol()]=true;*/
	}
	
    /**
     * Move if one can move along the DFS tree.
     * otherwise, turn back to the last branch of the DFS tree.
     */
    public void act()
    {
    	//System.out.println();
    	//System.out.println("Current location: "+getLocation().toString());
    	boolean willmove = canMove();
    	//System.out.println("canMove:"+willmove);
    	if(judgeEnd == true)
    	{
    		System.out.println("The total step is "+stepLength);
    	}
    	else if (willmove)
        {
            move();
            stepLength++;
        }
        else
        {
            turnBack();	
            stepLength++;
        }

    }
	
    /**
     * the method that move the MazeBug
     * judge canMove in method act, so that move method don't need to concern much about this.
     * Very similar to the same method in Bug.java.
     */
    public void move()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
        {
            return;
        }

        Location loc = getLocation();
//next should be decided in canMove method.
        if (gr.isValid(next))
        {
        	setDirection(loc.getDirectionToward(next));//��������
        	isVisit[next.getRow()][next.getCol()] = true;
        	//cache.push(getLocation());//������move���������޸�cache����Ϊ��turnBack�л����move������
            moveTo(next);
        }
        else
        {
            removeSelfFromGrid();
        }
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
    }

    /* This function use origin location to find the next location.
     * Requirement:
     * 	1.can go
     *  2.haven't been gone
     * 
     * @param loc is the element's origin location 
     * 
     * return a list of location that is valid for loc.
     * 
     * refer: getLocationInDirection() method of CrabCritter.java. They are very similar
     * */
    private ArrayList<Location> getValidLocation(Location loc)
    {
    	ArrayList<Location> validList = new ArrayList<Location>();
        Grid<Actor> gr = getGrid();
        int[] directions = {Location.AHEAD, Location.RIGHT, Location.LEFT, Location.HALF_CIRCLE};
        
        for (int d : directions)
        {
            Location neighborLoc = loc.getAdjacentLocation(d);
            
            /*
             * The requirement for location to add in the validList:
             * 1.It should return true with gr.isValid(neighborLoc)
             * 2.The place shouldn't have a rock or other bug
             * 		in other word, the place should only have flower or null.
             * 3.That place shouldn't be arrived before.
             * */
            if (gr.isValid(neighborLoc))
            {
            	Actor actor = gr.get(neighborLoc);
            	if((actor==null||actor instanceof Flower) && !isVisit[neighborLoc.getRow()][neighborLoc.getCol()])
            	{
            		validList.add(neighborLoc);
            	}
            	//judge whether to end.
            	else if(actor instanceof Rock)
            	{
            		if(actor.getColor()==Color.RED)
            		{
            			judgeEnd = true;
            		}
            	}
            }
        }
    	return validList;
    }
    
    /**
     * Judge whether the mazeBug can move.
     * Most important is if there's a way to go.
     * Need to override the method to look for adjacentLocation, maybe I should write a new method.
     * @return true if this bug can move. return false if the bug can't move.
     */
    public boolean canMove()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null)
            return false;
        Location loc = getLocation();
        ArrayList<Location> list = getValidLocation(loc);
        
        if(list.size()==0)
        {
        	return false;
        }
        //���ڶ���һ����֧
        else if(list.size()>1)
        {
        	branch.push(cache);
        	cache = new Stack<Location>();
        }
        
        cache.add(getLocation());
        
        int random = (int)(Math.random()*(list.size()-1));
        next = list.get(random);
        last = getLocation();
        
        return true;
    }
    
    /*
     * ����գ��ߵ�DFS������һ������һ���ֽ�㡣
     * */
    public void turnBack()
    {
    	if(!cache.empty())
    	{
    		next = cache.pop();
    		move();
    	}
    	
    	if(cache.empty())//��ǰջ�������
    	{
    		cache = branch.pop();    		
    	}
    }
}