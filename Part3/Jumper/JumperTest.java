import static org.junit.Assert.*;
import org.junit.Test;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import info.gridworld.actor.Rock;
import java.awt.Color;

public class JumperTest {
	@Test
	public void testRockMove()
	{
        ActorWorld world = new ActorWorld();
        Jumper alice = new Jumper(6);
        alice.setColor(Color.ORANGE);
        world.add(new Location(7, 8), alice);
        world.add(new Location(6,8),new Rock());
        alice.act();
        assertEquals(alice.getLocation(),new Location(5,8));
	}
	
	@Test
	public void testRockNotMove()
	{
        ActorWorld world = new ActorWorld();
        Jumper alice = new Jumper(6);
        alice.setColor(Color.ORANGE);
        world.add(new Location(1, 0), alice);
        world.add(new Location(0,0),new Rock());
        alice.act();
        assertEquals(alice.getLocation(),new Location(1,0));
	}
	@Test
	public void testEmptyNotMove()
	{
        ActorWorld world = new ActorWorld();
        Jumper alice = new Jumper(6);
        alice.setColor(Color.ORANGE);
        world.add(new Location(0, 0), alice);
        alice.act();
        assertEquals(alice.getLocation(),new Location(0,0));
	}
	@Test
	public void testEmptyMove()
	{
        ActorWorld world = new ActorWorld();
        Jumper alice = new Jumper(6);
        alice.setColor(Color.ORANGE);
        world.add(new Location(2, 0), alice);
        alice.act();
        assertEquals(alice.getLocation(),new Location(0,0));
	}
	@Test
	public void testEdgeMove()
	{
        ActorWorld world = new ActorWorld();
        Jumper alice = new Jumper(6);
        alice.setColor(Color.ORANGE);
        world.add(new Location(0, 0), alice);
        alice.act();
        alice.act();
        alice.act();
        assertEquals(alice.getLocation(),new Location(0,2));
	}
}
