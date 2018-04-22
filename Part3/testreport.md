# testreport for Part3 Jumper
## statement
### 1. jump over rock
description: This test is about whether the jumper can jump over the rock and move to the correction location.
```
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
```
### 2.test rock not move
description: When there is a rock near and edge two cells from the jumper, the jumper shouldn't move.
```
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
```
### 3.test empty not move
description: When the jumper is at the corner and has edge near, it shouldn't mvoe.
```
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
```
### 4.test empty move
description: When the jumper has nothing near, it should move.
```
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
```
### 5.test edge move
description: When a jumper is near the edge, it should act like a BoxBug.
```
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
```