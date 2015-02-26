package edu.ucsd.cse124;

public class Tweet {

    public Tweet(long tweet_id, long posted, int num_stars, String tweet_content) {
      this.tweetId = tweet_id;
      this.tweetString = tweet_content;
      this.numStars = num_stars;
      this.posted = posted;
    }

    public void liked(){
      this.numStars += 1;
    }

    public long tweetId;
    public String tweetString;
    public int numStars;
    public long posted;
}
