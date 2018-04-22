import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.actor.Critter;
import info.gridworld.actor.Rock;
import info.gridworld.actor.Flower;

public class UnboundedGridRunner
{
	/*
		use ActorWorld's addGridClass function to add the grid.
	*/
	public static void main(String[] args)
	{
		ActorWorld world = new ActorWorld();
		world.addGridClass("UnboundedGrid2");
		world.add(new Location(2,2),new Critter());
		world.show();
	}
}