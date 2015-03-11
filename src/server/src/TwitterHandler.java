package edu.ucsd.cse124;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;

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
    public synchronized void createUser(String handle) throws AlreadyExistsException
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
        if(!target.check_subscribed_accounts(theirhandle)){
            target.add_to_subscribed_accounts(theirhandle);
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
        if(target.check_subscribed_accounts(theirhandle)){
            target.remove_to_subscribed_accounts(theirhandle);
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
            handle,
            System.currentTimeMillis(),
            0,
            tweetString
        );

        target.push_to_tweet_list(new_tweet);
    }

    @Override
    public List<Tweet> readTweetsByUser(String handle, int howmany)
        throws NoSuchUserException
    {
        if(!accounts.containsKey(handle)){
            throw new NoSuchUserException(handle);
        }
        Account target = accounts.get(handle);
        ArrayList<Tweet> alist = target.getTweetArrayList();
        List<Tweet>  output = new ArrayList<Tweet>(); 

        //collect the last <howmany> tweets from the list in reverse order
        for(int i = alist.size()-1; i >= 0 && i >= (alist.size() - howmany); --i){
            output.add(alist.get(i));
        }
        return output;
    }

    //self-defined comparator for Tweet class
    public static Comparator<Tweet> timestanpComparator = new Comparator<Tweet>(){
        @Override
        public int compare(Tweet c1, Tweet c2) {
            return (int) (c1.posted - c2.posted);
        }
    };

    @Override
    public List<Tweet> readTweetsBySubscription(String handle, int howmany)
        throws NoSuchUserException
    {   
        if(!accounts.containsKey(handle)){
            throw new NoSuchUserException(handle);
        }
        Account target = accounts.get(handle);
        ArrayList<String> tarlist = target.get_subscribed_accounts();

        //matain a minimum heap to keep tracking on the latest <howmany> tweets
        PriorityQueue<Tweet> priorityQ = new PriorityQueue<Tweet>(howmany, timestanpComparator);

        LinkedList<Tweet>  output = new LinkedList<Tweet>(); 
        ArrayList<Tweet> alist;

        Tweet tw;
        int count = 0;

        //traverse user's following_list
        for(int j = 0; j < tarlist.size(); ++j){
            alist = accounts.get(tarlist.get(j)).getTweetArrayList();
            //Only process the last <howmany> entries in each list
            for(int i = alist.size()-1; i >= 0 && i >= (alist.size()-howmany); --i){
                tw = alist.get(i);
                if(count < howmany){//If the heap size is smaller than <howmany>, add it.
                    count++;
                    priorityQ.add(tw);
                }
                //If the heap size equals <howmany>, then compare the new node with the top node.
                //If the new node is greater than the top node, pop out the top node and
                //add the new node into the heap.
                else{
                    if( timestanpComparator.compare(tw, priorityQ.peek()) > 0 ){
                        priorityQ.poll();
                        priorityQ.add(tw);
                    }
                }
            }
        }
        //collect the output list.
        for(int i = priorityQ.size() - 1; i >= 0; --i){
            Tweet temp = priorityQ.poll();
            System.out.println(temp);

            output.addFirst(temp);            
        }

        return output;
    }

    @Override
    public void star(String handle, long tweetId) throws
        NoSuchUserException, NoSuchTweetException
    {
        if(!accounts.containsKey(handle)){
            throw new NoSuchUserException(handle);
        }

        Account target = accounts.get(handle);
        target.favoriting_tweet(tweetId);

    }
}
