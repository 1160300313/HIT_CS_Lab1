package P3;

import java.util.*;
import java.util.ArrayList;

public class FriendshipGraph {
	private static int[][] graph = new int[999][999];
	private static int members;
	{
		members = 0;
	}

	private static List<String> personList = new ArrayList<>();

	/**
	 * Add a person into the person list.
	 * 
	 * @param person
	 *            the name of the person
	 *
	 */
	public static void addVertex(Person person) {
		String name = person.getName();
		for (String temp : personList) {
			if (temp.equals(name)) {
				System.out.println("Already exist!");
				return;
			}
		}
		personList.add(name);
		System.out.println(personList.get(members));
		System.out.println(personList.size());
		System.out.println(personList.indexOf(name));
		members++;
	}

	/**
	 * 
	 * @param srcPerson
	 * @param dstPerson
	 */
	public static void addEdge(Person srcPerson, Person dstPerson) {
		int findex = personList.indexOf(srcPerson.getName());
		System.out.println(findex);
		int sindex = personList.indexOf(dstPerson.getName());
		System.out.println(sindex);
		graph[findex][sindex] = 1;
	}

	/**
	 * 
	 * @param srcPerson
	 * @param dstPerson
	 * @return the distance between two persons x (an integer which above 0) if
	 *         they're connected, otherwise return -1
	 */
	public static int getDistance(Person srcPerson, Person dstPerson) {
		return -1;
	}

	public static void main(String[] args) {
		FriendshipGraph g = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		g.addVertex(rachel);
		g.addVertex(ross);
		g.addEdge(ross, rachel);
	}
}
