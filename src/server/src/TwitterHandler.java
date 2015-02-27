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
        System.out.println("step 11");
        Account target = accounts.get(handle);
        System.out.println("step 22");
        ArrayList<Tweet> alist = target.getTweetArrayList();
        System.out.println("step 33");
        List<Tweet>  output = new ArrayList<Tweet>(); 
        System.out.println("step 44");
        for(int i=alist.size()-1; i>=0 && i>=(alist.size()-howmany); --i){
            System.out.println("step "+i);
            output.add(alist.get(i));
        }
        System.out.println("step final");
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
        PriorityQueue<Tweet> priorityQ = new PriorityQueue<Tweet>(howmany, timestanpComparator);
        LinkedList<Tweet>  output = new LinkedList<Tweet>(); 
        ArrayList<Tweet> alist;
        Tweet tw;
        int count = 0;
        for(int j=0; j<tarlist.size(); ++j){
            alist = accounts.get(tarlist.get(j)).getTweetArrayList();
            for(int i=alist.size()-1; i>=0 && i>=(alist.size()-howmany); --i){
                tw = alist.get(i);
                if(count<howmany)
                    priorityQ.add(tw);
                else{
                    if( timestanpComparator.compare(tw,priorityQ.peek())>0 ){
                        priorityQ.poll();
                        priorityQ.add(tw);
                    }
                }
            }
        }
        for(int i=0; i<priorityQ.size(); ++i)
            output.addFirst(priorityQ.poll());
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
