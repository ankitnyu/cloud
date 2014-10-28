import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
 
public class tweet {
    public static void main(String[] args) throws Exception 
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("xxxx")
        .setOAuthConsumerSecret("xxxx")
         .setOAuthAccessToken("xxxx")
         .setOAuthAccessTokenSecret("xxxx");
         //.setUseSSL(true);
        PrintWriter tweetWriter = null;
         
        try
        {
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
         
            //enter the query to search for tweets   
            Query query = new Query("#NarenderModi");
      
            //setting the language of tweets to be extracted   
            query.lang("en");
 
            //number of tweets in a page
            query.setCount(100);
         
            //result contains the extracted tweets
            QueryResult result = twitter.search(query);
            System.out.println(result.getRateLimitStatus());
            //storing the tweets in the current page to happy.txt
            tweetWriter = new PrintWriter(new File("happy.txt"));
         
            do{
                List<Status> tweets = result.getTweets();
                //ResponseList<Status> a = twitter.getUserTimeline(new Paging(1,5));
                System.out.println("Number of tweets:"+ tweets.size());
                for (Status tweet : tweets ) 
                {
                    tweetWriter.print(tweet.getUser().getScreenName()+":");
                    tweetWriter.print(tweet.getText()); 
                    tweetWriter.println();
                    tweetWriter.flush();
                }
 
         
                //get tweets in the next page if any
                query = result.nextQuery();
                if(query != null)
                {
                    result = twitter.search(query);
                }
            }while(query != null);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(tweetWriter != null )
            {
                tweetWriter.close();
            }
        }    
    }
}
