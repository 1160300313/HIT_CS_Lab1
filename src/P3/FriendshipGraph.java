package P3;

import java.util.HashMap;

public class FriendshipGraph {
	private int[][] graph;
	private int members;
	{
		members = 0;
	}
	private HashMap<String, Integer> personList = new HashMap();

	/**
	 * 
	 * @param person
	 */
	private static void addVertex(Person person) {

	}

	/**
	 * 
	 * @param srcPerson
	 * @param dstPerson
	 */
	private static void addEdge(Person srcPerson, Person dstPerson) {

	}

	/**
	 * 
	 * @param srcPerson
	 * @param dstPerson
	 * @return the distance between two persons x (an integer which above 0) if
	 *         they're connected, otherwise return -1
	 */
	private static int getDistance(Person srcPerson, Person dstPerson) {
		return -1;
	}
}
