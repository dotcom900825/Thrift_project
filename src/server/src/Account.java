package edu.ucsd.cse124;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class Account {

    public Account(String handle) {
        this.handle = handle;
        this.following_accounts = new ArrayList();
        this.tweet_lists = new HashMap<Long, Tweet>();
    }

    public void add_to_subscribed_accounts(String handle){
      this.following_accounts.add(handle);
    }

    public void remove_to_subscribed_accounts(String handle){
      this.following_accounts.remove(handle);
    }


    public ArrayList get_subscribed_accounts(){
      return following_accounts;
    }

    public boolean check_subscribed_accounts(String handle){
      return following_accounts.contains(handle);
    }

    public void push_to_tweet_list(Tweet tweet){
      tweet_lists.put(tweet.tweetId, tweet);
    }

    public String handle;
    private ArrayList following_accounts;
    private Map<Long,Tweet> tweet_lists;
}
