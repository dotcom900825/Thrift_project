package edu.ucsd.cse124;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class Account {

    public Account(String handle) {
        this.handle = handle;
        this.following_accounts = new ArrayList<String>();
        this.tweet_lists = new HashMap<Long, Tweet>();
        this.tweet_arraylist = new ArrayList<Tweet>();
        this.tweet_like = new HashMap<Long, ArrayList<String>>();
    }

    public synchronized void add_to_subscribed_accounts(String handle){
      this.following_accounts.add(handle);
    }

    public synchronized void remove_to_subscribed_accounts(String handle){
      this.following_accounts.remove(handle);
    }


    public ArrayList<String> get_subscribed_accounts(){
      return following_accounts;
    }

    public boolean check_subscribed_accounts(String handle){
      return following_accounts.contains(handle);
    }

    public synchronized void push_to_tweet_list(Tweet tweet){
      tweet_lists.put(tweet.tweetId, tweet);
      tweet_arraylist.add(tweet);
    }

    public synchronized boolean favoriting_tweet(long tweet_id, String handle){
      if(tweet_like.get(tweet_id).contains(handle)){
        return false;
      }

      if(!tweet_lists.contains(tweet_id)){
        return false;
      }

      Tweet target = tweet_lists.get(tweet_id);
      int num = target.getNumStars();
      target.setNumStars(num + 1);
      tweet_like.get(tweet_id).push(handle);

      return true;
    }

    public ArrayList<Tweet> getTweetArrayList(){
      return tweet_arraylist;
    }

    public String handle;
    private Map<Long, ArrayList<String>> tweet_like;
    private ArrayList<String> following_accounts;
    private Map<Long,Tweet> tweet_lists;
    private ArrayList<Tweet> tweet_arraylist;
}
