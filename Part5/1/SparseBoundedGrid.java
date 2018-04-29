import info.gridworld.grid.*;
import java.util.*;

public class SparseBoundedGrid<E> extends AbstractGrid<E>
{
   private ArrayList<LinkedList<OccupantInCol<E>>> sparseArray; // the array storing the grid elements
   private int rowNum;
   private int colNum;
    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public SparseBoundedGrid(int rows, int cols)
    {
        if (rows <= 0)
        {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (cols <= 0)
        {
            throw new IllegalArgumentException("cols <= 0");
        }
        rowNum = rows;
        colNum = cols;
        sparseArray = new ArrayList<LinkedList<OccupantInCol<E>>>();
        //LinkedList's init
        for(int i=0;i<rowNum;i++)
        {
            LinkedList<OccupantInCol<E>> list = new LinkedList<OccupantInCol<E>>();
            sparseArray.add(list);
        }
    }

    public int getNumRows()
    {
        return rowNum;
    }

    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return colNum;
    }

    public boolean isValid(Location loc)
    {
        //the range of the grid is limit
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> occupiedLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
            LinkedList<OccupantInCol<E>> list = sparseArray.get(r);
            for(int i=0;i<list.size();i++)
            {
                OccupantInCol<E> object = list.get(i);
                occupiedLocations.add(new Location(r,object.getCol()));
            }
        }

        return occupiedLocations;
    }

    public E get(Location loc)
    {
        //throw error if loc is not in the grid
        if (!isValid(loc))
        {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }


        LinkedList<OccupantInCol<E>> list = sparseArray.get(loc.getRow());
        for(int i=0;i<list.size();i++)
        {
            OccupantInCol<E> object = list.get(i);
            if(object.getCol()==loc.getCol())
            {
                return object.getObject();
            }
        }
        return null;
    }

/*
    put means occupant can get the element in loc, and put one obj into it.
    return value is the element in the location.
    It will use obj to replace the elemetn in that location, and create one if there's none.
*/
    public E put(Location loc, E obj)
    {
        //throw error if location is not in the grid or obj is empty
        if (!isValid(loc))
        {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (obj == null)
        {
            throw new NullPointerException("obj == null");
        }

        // Add the object to the grid.
        E oldOccupant = get(loc);
        OccupantInCol<E> object;
        LinkedList<OccupantInCol<E>> list = sparseArray.get(loc.getRow());
        for(int i=0;i<list.size();i++)
        {
            object = list.get(i);
            if(object.getCol()==loc.getCol())
            {
                object.setObject(obj);
                return oldOccupant;
            }
        }

//insert if not exists
        object = new OccupantInCol<E>(obj,loc.getCol());
        list.add(object);
        return oldOccupant;
    }
/*
    function remove remove the actor in that location.
    the return value of that function is the actor. 
*/
    public E remove(Location loc)
    {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        
        // Remove the object from the grid.
        E oldOccupant = get(loc);
        OccupantInCol<E> object;
        LinkedList<OccupantInCol<E>> list = sparseArray.get(loc.getRow());
        for(int i=0;i<list.size();i++)
        {
            object = list.get(i);
            if(object.getCol()==loc.getCol())
            {
                list.remove(i);
                // may be multiple objects in the same list.
            }
        }
        return oldOccupant;
    }
}
