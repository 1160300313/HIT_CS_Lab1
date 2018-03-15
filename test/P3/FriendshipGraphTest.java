package P3;

import org.junit.Test;
import static org.junit.Assert.*;

public class FriendshipGraphTest {
	@Test
	public void friendGraphTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		/*
		 * int i, j; int members = graph.getMembers(); int[][] g =
		 * graph.getGraph(); for (i = 0; i < members; i++) { for (j = 0; j <
		 * members; j++) System.out.print(g[i][j]); System.out.println(); }
		 */
		assertEquals(1, graph.getDistance(rachel, ross), 0.001);
		assertEquals(2, graph.getDistance(rachel, ben), 0.001);
		assertEquals(0, graph.getDistance(rachel, rachel), 0.001);
		assertEquals(-1, graph.getDistance(rachel, kramer), 0.001);
	}

	@Test
	public void testFriendGraphWithOneWay() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		// graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);

		assertEquals(1, graph.getDistance(ross, rachel));
		assertEquals(-1, graph.getDistance(rachel, ross));
		assertEquals(-1, graph.getDistance(rachel, ben));
		assertEquals(2, graph.getDistance(ben, rachel));
	}

	@Test
	public void testFriendGraphWithSameName() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Rachel");
		graph.addVertex(rachel);
		graph.addVertex(ross);// should exit here

		// graph.addVertex(ben);
		// graph.addVertex(kramer);
		// graph.addEdge(rachel, ross);
		// graph.addEdge(ross, rachel);
		// graph.addEdge(ross, ben);
		// graph.addEdge(ben, ross);

		assertEquals(1, graph.getMembers());
	}
}
