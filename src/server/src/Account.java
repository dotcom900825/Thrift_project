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

    public synchronized void favoriting_tweet(long tweet_id){
      Tweet target = tweet_lists.get(tweet_id);
      int num = target.getNumStars();
      target.setNumStars(num + 1);
    }

    public ArrayList<Tweet> getTweetArrayList(){
      return tweet_arraylist;
    }

    public String handle;
    private ArrayList<String> following_accounts;
    private Map<Long,Tweet> tweet_lists;
    private ArrayList<Tweet> tweet_arraylist;
}
