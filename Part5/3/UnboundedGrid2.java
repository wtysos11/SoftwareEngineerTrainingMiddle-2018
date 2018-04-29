
import info.gridworld.grid.*;
import java.util.ArrayList;
import java.util.*;

public class UnboundedGrid2<E> extends AbstractGrid<E>
{
    //multiple dimension array can't be a E type. 
    //It is better to declare it as type Object and transfer it to E
    private Object[][] occupantArray;
    private int ARRAY_LIMIT = 16;
    private int currentLimit;
    /**
     * Constructs an empty unbounded grid.
     */
    public UnboundedGrid2()
    {
        currentLimit = ARRAY_LIMIT;
        occupantArray = new Object[16][16];
    }

//like otehr unbounded grid, the rows and cols are all -1.
    public int getNumRows()
    {
        return -1;
    }

    public int getNumCols()
    {
        return -1;
    }
/*
  only non-negative parameter is permited
*/
    public boolean isValid(Location loc)
    {
        if(loc.getRow()>=0 && loc.getCol()>=0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

/*
use ArrayList to store it.
*/
    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> a = new ArrayList<Location>();
        for(int i=0;i<currentLimit;i++)
        {
            for(int j=0;j<currentLimit;j++)
            {
                Location loc = new Location(i,j);
                if(get(loc)!=null)
                {
                    a.add(loc);
                }
            }
        }
        return a;
    }

    public E get(Location loc)
    {
        if (loc == null)
		{
            throw new NullPointerException("loc == null");
		}
		if(!isValid(loc))
		{
			throw new IllegalArgumentException("Location is not valid. There should be all positive coordinate.");
		}
		if(loc.getRow()>=currentLimit || loc.getCol()>=currentLimit)
		{
			return null;
		}
        return (E) occupantArray[loc.getRow()][loc.getCol()];
    }

/*
The most important part. If there's something out of range, then multiple it.
*/
    public E put(Location loc, E obj)
    {
        if (loc == null)
        {
            throw new NullPointerException("loc == null");
        }
        if (obj == null)
        {
            throw new NullPointerException("obj == null");
        }
        
        int rowNum = loc.getRow();
        int colNum = loc.getCol();
        int newsize = currentLimit;
        while(rowNum>newsize - 1 || colNum > newsize - 1)
        {
            newsize*=2;
        }
        if(newsize!=currentLimit)
        {
            extendGrid(newsize);
        }
        E oldOccupant = get(loc);
        occupantArray[rowNum][colNum]=obj;
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (loc == null)
        {
            throw new NullPointerException("loc == null");
        }
        int rowNum = loc.getRow();
        int colNum = loc.getCol();

        E oldOccupant = get(loc);
        occupantArray[rowNum][colNum]=null;
        return oldOccupant;
    }

    public void extendGrid(int newSize)
    {
        Object[][] newCopy = new Object[newSize][newSize];
        for(int i=0;i<currentLimit;i++)
        {
            System.arraycopy(occupantArray[i],0,newCopy[i],0,currentLimit);
        }

        occupantArray = newCopy;
        currentLimit = newSize;
    }

}
