/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

	/**
	 * Get the time period spanned by tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return a minimum-length time interval that contains the timestamp of
	 *         every tweet in the list.
	 */
	public static Timespan getTimespan(List<Tweet> tweets) {
		Instant start = tweets.get(0).getTimestamp();
		Instant end = tweets.get(0).getTimestamp();
		for (Tweet tmp : tweets) {
			Instant tmpIns = tmp.getTimestamp();
			if (tmpIns.isBefore(start))
				start = tmpIns;
			if (tmpIns.isAfter(end))
				end = tmpIns;
		}
		Timespan timespan = new Timespan(start, end);
		return timespan;
	}

	/**
	 * Get usernames mentioned in a list of tweets.
	 * 
	 * @param tweets
	 *            list of tweets with distinct ids, not modified by this method.
	 * @return the set of usernames who are mentioned in the text of the tweets.
	 *         A username-mention is "@" followed by a Twitter username (as
	 *         defined by Tweet.getAuthor()'s spec). The username-mention cannot
	 *         be immediately preceded or followed by any character valid in a
	 *         Twitter username. For this reason, an email address like
	 *         bitdiddle@mit.edu does NOT contain a mention of the username mit.
	 *         Twitter usernames are case-insensitive, and the returned set may
	 *         include a username at most once.
	 */
	public static Set<String> getMentionedUsers(List<Tweet> tweets) {
		Set<String> mentionedUsers = new HashSet<>();
		for (Tweet tmpTweet : tweets) {
			String tmpText = tmpTweet.getText();
			String name;
			String[] tmpTextSplit = tmpText.split("@");
			// 按照正文被@分割后的数量判断是否存在@行为
			int atNum = tmpTextSplit.length - 1;
			if (atNum >= 1) {
				int i;
				for (i = 1; i <= atNum; i++) {
					String thisString = tmpTextSplit[i];
					int j;
					int thisLength = thisString.length();
					char jj = ' ';
					// 遇到第一个非法字符跳出循环
					for (j = 0; j < thisLength; j++) {
						jj = thisString.charAt(j);
						if (!((jj >= 'a' && jj <= 'z') | (jj >= 'A' && jj <= 'Z') | (jj >= '0' && jj <= '9')
								| (jj == '-') | (jj == '_')))
							break;
					}
					// 合理情况：到达字符串尾或非.的非法字符
					if (j == thisString.length() || thisString.charAt(j) != '.') {
						name = thisString.substring(0, j);
						mentionedUsers.add(name);
					}
				}
			}
		}
		return mentionedUsers;
	}

}
