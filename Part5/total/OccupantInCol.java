/*
a very easy base class, contains store element and col index
*/
public class OccupantInCol<E>
{
	private E occupant;
	private int col;
	public OccupantInCol(E object,int _col)
	{
		occupant = object;
		col = _col;
	}
	public void setObject(E o)
	{
		occupant = o;
	}
	public E getObject()
	{
		return occupant;
	}
	public void setCol(int _col)
	{
		col  = _col;
	}
	public int getCol()
	{
		return col;
	}
}