/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P4.twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

	/*
	 * TODO: your testing strategies for these methods should go here. See the
	 * ic03-testing exercise for examples of what a testing strategy comment
	 * looks like. Make sure you have partitions.
	 */

	private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
	private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

	/*
	 * TODO which infers that Ernie follows Bert if Ernie @-mentions Bert.
	 */
	private static final Tweet tweet1 = new Tweet(1, "Ernie", "@Bert lululu", d1);
	private static final Tweet tweet2 = new Tweet(2, "Bert", "Freedom!!!!", d2);
	private static final Tweet tweet3 = new Tweet(3, "Ernie", "lululu Bert lululu", d1);

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	@Test
	public void testGuessFollowsGraphEmpty() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());

		assertTrue("expected empty graph", followsGraph.isEmpty());
	}

	@Test
	public void testGuessFollowGraphWithOneAt() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2));

		assertFalse("expected not empty", followsGraph.isEmpty());
		assertTrue("Bert follows no one", followsGraph.get("Bert").isEmpty());
		assertTrue("Ernie follows Bert", followsGraph.get("Ernie").contains("Bert"));
	}

	@Test
	public void testGuessFollowGraphWithNoMention() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet3));

		assertFalse("expected empty graph", followsGraph.isEmpty());
		assertTrue("Ernie follows no one", followsGraph.get("Ernie").isEmpty());
		assertTrue("Bert follows no one", followsGraph.get("Bert").isEmpty());
	}

	@Test
	public void testInfluencersEmpty() {
		Map<String, Set<String>> followsGraph = new HashMap<>();
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertTrue("expected empty list", influencers.isEmpty());
	}

	@Test
	public void testInfluencersWithOne() {
		Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3));
		List<String> influencers = SocialNetwork.influencers(followsGraph);

		assertFalse(influencers.isEmpty());
		assertTrue("Only Bert has followers", influencers.contains("Bert"));
	}

	/*
	 * Warning: all the tests you write here must be runnable against any
	 * SocialNetwork class that follows the spec. It will be run against several
	 * staff implementations of SocialNetwork, which will be done by overwriting
	 * (temporarily) your version of SocialNetwork with the staff's version. DO
	 * NOT strengthen the spec of SocialNetwork or its methods.
	 * 
	 * In particular, your test cases must not call helper methods of your own
	 * that you have put in SocialNetwork, because that means you're testing a
	 * stronger spec than SocialNetwork says. If you need such helper methods,
	 * define them in a different class. If you only need them in this test
	 * class, then keep them in this test class.
	 */

}
