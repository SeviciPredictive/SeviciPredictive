package com.bignerdranch.android.sevici;

import android.app.ListActivity;
import android.os.Bundle;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.Fabric;

public class TwitterActivity extends ListActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "F71b0X3hXreT7Z7sooGPgwPXx";
    private static final String TWITTER_SECRET = "m8KCP60UJpNoV70pnyplK5ctvDNlUB7lutHAVf3yhJzm1PKP5L";
   // private TwitterLoginButton loginButton;


   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
               // TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
               // String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
               // Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
                String UserName = result.data.getUserName();
                Toast.makeText(MainActivity.this, UserName, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


    }*/
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
       Fabric.with(this, new Twitter(authConfig));
       setContentView(R.layout.activity_twitter);


       final SearchTimeline searchTimeline = new SearchTimeline.Builder()
               .query("#Sevici")
               .build();


       final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
               .setTimeline(searchTimeline)
               .build();

       setListAdapter(adapter);



       final UserTimeline userTimeline = new UserTimeline.Builder()
               .screenName("Ayto_Sevilla ")
               .build();
       final TweetTimelineListAdapter adapter1 = new TweetTimelineListAdapter.Builder(this)
               .setTimeline(userTimeline)
               .build();
       setListAdapter(adapter1);

   }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }*/

}
