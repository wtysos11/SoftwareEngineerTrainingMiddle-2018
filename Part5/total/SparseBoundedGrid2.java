import info.gridworld.grid.*;
import java.util.*;

public class SparseBoundedGrid2<E> extends AbstractGrid<E>
{
   private HashMap<Location,E> sparseMap; // the array storing the grid elements
   private int rowNum;
   private int colNum;
    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public SparseBoundedGrid2(int rows, int cols)
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
        sparseMap = new HashMap<Location,E>();
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
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> occupiedLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for(Location loc: sparseMap.keySet())
        {
            occupiedLocations.add(loc);
        }

        return occupiedLocations;
    }
/*
function get use method's get method.
return value is the element in the refer location.
*/
    public E get(Location loc)
    {
        if (!isValid(loc))
        {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }


        return sparseMap.get(loc);
    }

/*
    put means occupant can get the element in loc, and put one obj into it.
*/
    public E put(Location loc, E obj)
    {
        if (!isValid(loc))
        {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (obj == null)
        {
            throw new NullPointerException("obj == null");
        }

        return sparseMap.put(loc,obj);
    }
/*
remove function use map's remove function to remove.
*/
    public E remove(Location loc)
    {
        if (!isValid(loc))
        {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        
        // Remove the object from the grid.
        return sparseMap.remove(loc);
    }
}
