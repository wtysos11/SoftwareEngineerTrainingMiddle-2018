
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
 * 使用一个栈（与branch类似）来记录每一个节点选择时的方向信息
 * 如果说这个方向走不通，则当前方向信息对应的概率减1，将当前方向信息定义为返回状态
 * 如果在返回状态需要继续返回，则从栈中提取相关的方向信息，将其对应概率减1，继续返回
 * 
 * 路径选择的优先程度受到这个概率的影响。
 * */
public class MazeBug2 extends Bug
{
	
	public int stepLength;//已经走过的步数
	private boolean[][] isVisit;//是否访问过
	
	//所有关于这两个栈的操作应该统一在act及与其直接关联的方法中进行，以保证数据统一。
	Stack<Location> cache;//栈，用来存储当前层已经走过的位置
	Stack<Stack<Location>> branch;//用来保存DFS栈中前面几层的信息
	Stack<Integer> influence;//保存结点的选择信息，可选值：前0后1左2右3
	int[] probability;//1维4元素数组，保存各个方向上的概率
	int curDir;
	
	Location next;//store the next location to go
	Location last;//上一个位置
	boolean judgeEnd;//判断是否走到终点
	
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
        	setDirection(loc.getDirectionToward(next));//调整方向
        	isVisit[next.getRow()][next.getCol()] = true;
        	//cache.push(getLocation());//不能在move方法里面修改cache，因为在turnBack中会调用move方法。
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
            //存在多余一个分支
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
            
            //在分支点的情况下，对概率进行修正。
            if(list.size()>1)
            {
                try
                {
                	//所选方向对应的概率相加，并压入
                    int direction = loc.getDirectionToward(next);
                    influence.push(curDir);
                    probability[changeDirection2int(direction)]++;
                    curDir=changeDirection2int(direction);//更新当前情况
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
     * getNextLocation方法，接受一个list，返回一个位置
     * 使用数轴投影的思想去做，效果好很多。
     * */
    public Location getNextLocation(ArrayList<Location> list) throws Exception
    {
    	
    	int front=0,back=0,left=0,right=0;
    	int resultDirection;
    	//计算各个方向的实际权值，没有用到即为0.
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
     * 逐步清空，走到DFS树的上一层的最后一个分界点。
     * */
    public void turnBack()
    {
    	if(!cache.empty())
    	{
    		next = cache.pop();
    		move();
    	}
    	
    	if(cache.empty())//当前栈被清空了
    	{
    		cache = branch.pop();    	
    		
    		//influence栈与branch相对应
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
    		System.out.println(i+"的概率为: "+probability[i]);
    	}
    }
}