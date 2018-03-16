/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even
 * exist as a key in the map; this is true even if A is followed by other people
 * in the network. Twitter usernames are not case sensitive, so "ernie" is the
 * same as "ERNie". A username should appear at most once as a key in the map or
 * in any given map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

	/**
	 * Guess who might follow whom, from evidence found in tweets.
	 * 
	 * @param tweets
	 *            a list of tweets providing the evidence, not modified by this
	 *            method.
	 * @return a social network (as defined above) in which Ernie follows Bert
	 *         if and only if there is evidence for it in the given list of
	 *         tweets. One kind of evidence that Ernie follows Bert is if Ernie
	 * @-mentions Bert in a tweet. This must be implemented. Other kinds of
	 *            evidence may be used at the implementor's discretion. All the
	 *            Twitter usernames in the returned social network must be
	 *            either authors or @-mentions in the list of tweets.
	 */
	public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
		Map<String, Set<String>> gFollowGraph = new HashMap<>();
		/*
		 * 遍历tweets，将全部合法@行为（仅判断用户名格式，不判断用户是否存在）加入该 tweet作者的关注中
		 */
		for (Tweet tmpTweet : tweets) {
			String tmpAuthor = tmpTweet.getAuthor();
			Set<String> follows = new HashSet<>();
			boolean authorExist = gFollowGraph.containsKey(tmpAuthor);
			if (authorExist)
				follows = gFollowGraph.get(tmpAuthor);
			/*
			 * 将tweet正文按照@分割以判断是否存在
			 */
			String[] tmpTextSplitAt = tmpTweet.getText().split("@");
			int atNum = tmpTextSplitAt.length - 1;
			if (atNum > 0) {
				int i = 1;
				for (; i <= atNum; i++) {
					String thisString = tmpTextSplitAt[i];
					int j;
					for (j = 0; j < thisString.length(); j++) {
						char jj = thisString.charAt(j);
						// @后第一个非法字符停止
						if (!((jj >= 'a' && jj <= 'z') | (jj >= 'A' && jj <= 'Z') | (jj >= '0' && jj <= '9')
								| (jj == '-') | (jj == '_')))
							break;
					}
					/*
					 * 情况1：无非法字符，j遍历至字符串尾 情况2：非法字符为非'.'的其他字符
					 */
					if (j == thisString.length() || thisString.charAt(j) != '.') {
						String name = thisString.substring(0, j);
						follows.add(name);
					}
				}
			}
			// 添加至map
			if (authorExist) {
				gFollowGraph.replace(tmpAuthor, follows);
			} else {
				gFollowGraph.put(tmpAuthor, follows);
			}

		}
		return gFollowGraph;
	}

	/**
	 * Find the people in a social network who have the greatest influence, in
	 * the sense that they have the most followers.
	 * 
	 * @param followsGraph
	 *            a social network (as defined above)
	 * @return a list of all distinct Twitter usernames in followsGraph, in
	 *         descending order of follower count.
	 */
	public static List<String> influencers(Map<String, Set<String>> followsGraph) {
		List<String> influencers = new ArrayList<>();

		HashMap<String, Integer> followerStatus = new HashMap<>();
		/*
		 * 遍历followsGraph中所有人的关注列表，将被关注的人与其关注者人数建立哈希表
		 * 如果不存在则创建条目，存在则将关注人数+1后替换原条目
		 */
		for (Map.Entry<String, Set<String>> tmpEntry : followsGraph.entrySet()) {
			Set<String> follows = tmpEntry.getValue();
			if (!follows.isEmpty()) {
				for (String username : follows) {
					if (followerStatus.containsKey(username)) {
						followerStatus.replace(username, followerStatus.get(username) + 1);
					} else {
						followerStatus.put(username, 1);
					}
				}
			}
		}
		/*
		 * 将全部有关注者的用户的关注数提取出来，按降序排序后存入数组intFluNums
		 */
		int infNum = followerStatus.size();
		Object[] influenceNums = followerStatus.values().toArray();
		int[] intFluNums = new int[infNum];
		for (int i = 0; i < infNum; i++)
			intFluNums[i] = (int) influenceNums[i];
		for (int i = 0; i < infNum; i++)
			for (int j = i + 1; j < infNum; j++) {
				if (intFluNums[i] < intFluNums[j]) {
					int t = intFluNums[i];
					intFluNums[i] = intFluNums[j];
					intFluNums[j] = t;
				}
			}
		/*
		 * 按照上面排序的关注者人数顺序，从followerStatus中依次提取用户名，存入influencers并返回
		 */
		for (int i = 0; i < infNum; i++) {
			int max = intFluNums[i];
			for (Map.Entry<String, Integer> tmpEntry : followerStatus.entrySet()) {
				if (tmpEntry.getValue() == max) {
					influencers.add(tmpEntry.getKey());
					followerStatus.remove(tmpEntry.getKey());
					break;
				}
			}
		}
		return influencers;
	}

}
