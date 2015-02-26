package edu.ucsd.cse124;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;


public class TwitterHandler implements Twitter.Iface {
    protected Map<String,Account> accounts;
    protected Random rnd;
    public TwitterHandler() {
        this.rnd = new Random();
        this.accounts = new HashMap<String, Account>();
    }


    @Override
    public void ping() {
    }

    @Override
    public void createUser(String handle) throws AlreadyExistsException
    {
        if(accounts.containsKey(handle)){
            throw new AlreadyExistsException(handle);
        }
        accounts.put(handle, new Account(handle));
    }

    @Override
    public void subscribe(String handle, String theirhandle)
        throws NoSuchUserException
    {
        if(!accounts.containsKey(handle)) {
            throw new NoSuchUserException(handle);
        }

        if (!accounts.containsKey(theirhandle)) {
            throw new NoSuchUserException(theirhandle);
        }

        Account target = accounts.get(handle);
        if(!target.check_subscribed_accounts(handle)){
            target.add_to_subscribed_accounts(handle);
        }


    }

    @Override
    public void unsubscribe(String handle, String theirhandle)
        throws NoSuchUserException
    {
        if(!accounts.containsKey(handle)) {
            throw new NoSuchUserException(handle);
        }

        if (!accounts.containsKey(theirhandle)) {
            throw new NoSuchUserException(theirhandle);
        }

        Account target = accounts.get(handle);
        if(target.check_subscribed_accounts(handle)){
            target.remove_to_subscribed_accounts(handle);
        }
    }

    @Override
    public void post(String handle, String tweetString)
        throws NoSuchUserException, TweetTooLongException
    {
        if (tweetString.length() > 140) {
            throw new TweetTooLongException();
        }

        if(!accounts.containsKey(handle)){
            throw new NoSuchUserException(handle);
        }

        Account target = accounts.get(handle);

        Tweet new_tweet = new Tweet(
            (long)rnd.nextInt(Integer.MAX_VALUE), 
            System.currentTimeMillis() / 1000, 
            0,
            tweetString
        );

        target.push_to_tweet_list(new_tweet);
    }

    @Override
    public List<Tweet> readTweetsByUser(String handle, int howmany)
        throws NoSuchUserException
    {
        return null;
    }

    @Override
    public List<Tweet> readTweetsBySubscription(String handle, int howmany)
        throws NoSuchUserException
    {
        return null;
    }

    @Override
    public void star(String handle, long tweetId) throws
        NoSuchUserException, NoSuchTweetException
    {
    }
}
