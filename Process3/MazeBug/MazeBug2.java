
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
/*
 * ʹ��һ��ջ����branch���ƣ�����¼ÿһ���ڵ�ѡ��ʱ�ķ�����Ϣ
 * ���˵��������߲�ͨ����ǰ������Ϣ��Ӧ�ĸ��ʼ�1������ǰ������Ϣ����Ϊ����״̬
 * ����ڷ���״̬��Ҫ�������أ����ջ����ȡ��صķ�����Ϣ�������Ӧ���ʼ�1����������
 * 
 * ·��ѡ������ȳ̶��ܵ�������ʵ�Ӱ�졣
 * */
public class MazeBug2 extends Bug
{
	
	public int stepLength;//�Ѿ��߹��Ĳ���
	private boolean[][] isVisit;//�Ƿ���ʹ�
	
	//���й���������ջ�Ĳ���Ӧ��ͳһ��act������ֱ�ӹ����ķ����н��У��Ա�֤����ͳһ��
	Stack<Location> cache;//ջ�������洢��ǰ���Ѿ��߹���λ��
	Stack<Stack<Location>> branch;//��������DFSջ��ǰ�漸�����Ϣ
	Stack<Integer> influence;//�������ѡ����Ϣ����ѡֵ��ǰ0��1��2��3
	int[] probability;//1ά4Ԫ�����飬������������ϵĸ���
	int curDir;
	
	Location next;//store the next location to go
	Location last;//��һ��λ��
	boolean judgeEnd;//�ж��Ƿ��ߵ��յ�
	
	public MazeBug2()
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
		//So outside the MazeBug will probably go to origin.
		/*
		Location currentLoc = getLocation();
		cache.push(currentLoc);
		isVisit[currentLoc.getRow()][currentLoc.getCol()]=true;*/
		influence = new Stack<Integer>();
		probability = new int[4];
		for(int i=0;i<4;i++)
		{
			probability[i]=1;
		}
		curDir=3;
		probability[curDir]++;
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
    	//printAll();
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
    	try
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
            else if(list.size()>=2)
            {
            	branch.push(cache);
            	cache = new Stack<Location>();
            }
            
            cache.add(getLocation());
            
            int random = (int)(Math.random()*(list.size()-1));
            if(list.size()>=2)
            {
            	next = getNextLocation(list);
            }
            else
            {
            	next = list.get(0);
            }
            last = getLocation();
            
            //�ڷ�֧�������£��Ը��ʽ���������
            if(list.size()>1)
            {
                try
                {
                	//��ѡ�����Ӧ�ĸ�����ӣ���ѹ��
                    int direction = loc.getDirectionToward(next);
                    influence.push(curDir);
                    probability[changeDirection2int(direction)]++;
                    curDir=changeDirection2int(direction);//���µ�ǰ���
                }
                catch(Exception e)
                {
                	e.printStackTrace();
                }
            }	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return true;
    }
    
    /* 
     * getNextLocation����������һ��list������һ��λ��
     * ʹ������ͶӰ��˼��ȥ����Ч���úܶࡣ
     * */
    public Location getNextLocation(ArrayList<Location> list) throws Exception
    {
    	
    	int front=0,back=0,left=0,right=0;
    	int resultDirection;
    	//������������ʵ��Ȩֵ��û���õ���Ϊ0.
    	Location currentLoc = getLocation();
    	for(Location loc : list)
    	{
    		int dir = currentLoc.getDirectionToward(loc);
    		//System.out.println("Available direction: "+dir);
    		switch(dir)
    		{
    			case Location.NORTH:
    			{
    				front = probability[0];
    				break;
    			}
    			case Location.SOUTH:
    			{
    				back = probability[1];
    				break;
    			}
    			case Location.WEST:
    			{
    				left = probability[2];
    				break;
    			}
    			case Location.EAST:
    			{
    				right = probability[3];
    				break;
    			}
    		}
    	}
    	//printAll();
    	
    	int random = (int)(Math.random()*(left+right+front+back));
    	//System.out.println(random+" total:"+(left+right+front));
    	if(random<front)
    	{
    		resultDirection = Location.NORTH;
    	}
    	else if(random<front+back)
    	{
    		resultDirection = Location.SOUTH;
    	}
    	else if(random<front+back+left)
    	{
    		resultDirection = Location.WEST;
    	}
    	else
    	{
    		resultDirection = Location.EAST;
    	}
    	
    	for(Location loc : list)
    	{
    		if(resultDirection == currentLoc.getDirectionToward(loc))
    		{
    			return loc;
    		}
    	}
    	
    	throw new Exception("Error Calculation in function getNextLocation.");
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
    		
    		//influenceջ��branch���Ӧ
    		probability[curDir]--;
    		curDir = influence.pop();
    	}
    }
    
    private int changeDirection2int(int dir) throws Exception
    {
    	if(dir == Location.AHEAD)
    	{
    		return 0;
    	}
    	else if(dir == Location.HALF_CIRCLE)
    	{
    		return 1;
    	}
    	else if(dir == Location.LEFT || dir == Location.WEST)
    	{
    		return 2;
    	}
    	else if(dir == Location.RIGHT)
    	{
    		return 3;
    	}
    	else
    	{
    		throw new Exception("illegal direction");
    	}
    }
    
    private void printAll()
    {
    	System.out.println("In function printAll.");
    	for(int i=0;i<4;i++)
    	{
    		System.out.println(i+"�ĸ���Ϊ: "+probability[i]);
    	}
    }
}