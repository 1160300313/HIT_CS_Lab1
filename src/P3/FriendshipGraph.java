package P3;

import java.util.*;
import java.util.ArrayList;

public class FriendshipGraph {
	public final int inf = 9999;
	private int[][] graph = new int[inf][inf];
	private int members;
	{
		members = 0;
		for (int i = 0; i < inf; i++)
			for (int j = 0; j < inf; j++)
				graph[i][j] = inf;
	}

	public int getMembers() {
		return this.members;
	}

	public int[][] getGraph() {
		return this.graph;
	}

	private List<String> personList = new ArrayList<>();

	/**
	 * Add a person into the person list.
	 * 
	 * @param person
	 *            the name of the person
	 *
	 */
	public void addVertex(Person person) {
		String name = person.getName();
		for (String temp : personList) {
			if (temp.equals(name)) {
				System.out.println("Already exist!");
				return;
			}
		}
		personList.add(name);
		// System.out.println(personList.get(members));
		// System.out.println(personList.size());
		// System.out.println(personList.indexOf(name));
		members++;
	}

	/**
	 * 
	 * @param srcPerson
	 * @param dstPerson
	 */
	public void addEdge(Person srcPerson, Person dstPerson) {
		int findex = personList.indexOf(srcPerson.getName());
		// System.out.println(findex);
		int sindex = personList.indexOf(dstPerson.getName());
		// System.out.println(sindex);
		graph[findex][sindex] = 1;
	}

	/**
	 * Use Dijkstra бнбн
	 * 
	 * @param srcPerson
	 * @param dstPerson
	 * @return the distance between two persons x (an integer which above 0) if
	 *         they're connected, otherwise return -1
	 */
	public int getDistance(Person srcPerson, Person dstPerson) {
		int srcIndex = personList.indexOf(srcPerson.getName());
		int dstIndex = personList.indexOf(dstPerson.getName());
		if (srcIndex == -1 || dstIndex == -1) {// Both persons do not exist
			System.out.println("No such person(s)!");
			return -1;
		}
		if (srcIndex == dstIndex)
			return 0;

		// Dijkstra
		int i, j, k;
		int[] usedVertex = new int[inf];// 1:not used 0:used
		for (i = 0; i < members; i++)
			usedVertex[i] = 1;
		usedVertex[srcIndex] = 0;
		int[] distances = new int[inf];// array stored all distances
		for (i = 0; i < members; i++)
			distances[i] = graph[srcIndex][i];
		for (i = 0; i < members - 1; i++) {
			int[] via = new int[inf];
			for (j = 0, k = 0; j < members; j++) {
				if (usedVertex[j] == 1) {
					via[k] = j;
					k++;
				}
			}
			int minDistance = inf;
			int temp = srcIndex;// Temporary variable
			for (j = 0; j < k; j++) {
				int nowDistance = distances[via[j]];
				if (nowDistance < minDistance) {
					minDistance = nowDistance;
					temp = via[j];
				}
			}
			usedVertex[temp] = 0;
			for (j = 0; j < members; j++) {
				if (usedVertex[j] == 1)
					distances[j] = Math.min(distances[j], distances[temp] + graph[temp][j]);
			}
		}
		/*
		 * for (i = 0; i < members; i++) System.out.print(distances[i] + "\t");
		 * System.out.println();
		 */
		int distance = distances[dstIndex];
		if (distance == inf)
			return -1;
		return distance;
	}

	public static void main(String[] args) {
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
		 * members; j++) System.out.print(g[i][j] + "\t"); System.out.println();
		 * }
		 */
		System.out.println(graph.getDistance(rachel, ross));// should print 1
		System.out.println(graph.getDistance(rachel, ben));// should print 2
		System.out.println(graph.getDistance(rachel, rachel));// should print 0
		System.out.println(graph.getDistance(rachel, kramer));// should print -1
	}
}
