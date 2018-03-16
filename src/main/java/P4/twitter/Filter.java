/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

	/**
	 * Find tweets written by a particular user.
	 * 
	 * @param tweets
	 *            a list of tweets with distinct ids, not modified by this
	 *            method.
	 * @param username
	 *            Twitter username, required to be a valid Twitter username as
	 *            defined by Tweet.getAuthor()'s spec.
	 * @return all and only the tweets in the list whose author is username, in
	 *         the same order as in the input list.
	 */
	public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
		List<Tweet> userTweets = new ArrayList<>();
		for (Tweet tmp : tweets)
			if (tmp.getAuthor().equals(username))
				userTweets.add(tmp);
		return userTweets;
	}

	/**
	 * Find tweets that were sent during a particular timespan.
	 * 
	 * @param tweets
	 *            a list of tweets with distinct ids, not modified by this
	 *            method.
	 * @param timespan
	 *            timespan
	 * @return all and only the tweets in the list that were sent during the
	 *         timespan, in the same order as in the input list.
	 */
	public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
		List<Tweet> inTimeTweets = new ArrayList<>();
		for (Tweet tmp : tweets)
			if (tmp.getTimestamp().isAfter(timespan.getStart()) && tmp.getTimestamp().isBefore(timespan.getEnd()))
				inTimeTweets.add(tmp);
		return inTimeTweets;
	}

	/**
	 * Find tweets that contain certain words.
	 * 
	 * @param tweets
	 *            a list of tweets with distinct ids, not modified by this
	 *            method.
	 * @param words
	 *            a list of words to search for in the tweets. A word is a
	 *            nonempty sequence of nonspace characters.
	 * @return all and only the tweets in the list such that the tweet text
	 *         (when represented as a sequence of nonempty words bounded by
	 *         space characters and the ends of the string) includes *at least
	 *         one* of the words found in the words list. Word comparison is not
	 *         case-sensitive, so "Obama" is the same as "obama". The returned
	 *         tweets are in the same order as in the input list.
	 */
	public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
		List<Tweet> containTweets = new ArrayList<>();
		for (Tweet tmpTweet : tweets) {
			String tmpText = tmpTweet.getText().toUpperCase();
			for (String tmpWord : words) {
				// 由于匹配不区分大小写，这里将全部文字转换为大写再进行匹配
				String upTmp = tmpWord.toUpperCase();
				/*
				 * 情况1：关键词出现在句首，后有空格 情况2：关键词出现在句中，前后有空格 情况3：关键词出现在句尾，前有空格
				 * 情况4：关键词出现在句中任意部分
				 */
				String begPos = upTmp + " ";
				String midPos = " " + upTmp + " ";
				String endPos = " " + upTmp;
				if (tmpText.startsWith(begPos) | tmpText.contains(midPos) | tmpText.endsWith(endPos)
						| tmpText.contains(upTmp)) {
					containTweets.add(tmpTweet);
					break;
				}
			}
		}
		return containTweets;
	}

}
