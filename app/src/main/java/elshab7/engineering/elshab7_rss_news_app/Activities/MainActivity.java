package elshab7.engineering.elshab7_rss_news_app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.prof.rssparser.Article;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import elshab7.engineering.elshab7_rss_news_app.Adapters.RSS_Adapter;
import elshab7.engineering.elshab7_rss_news_app.Models.MyArticleModel;
import elshab7.engineering.elshab7_rss_news_app.Models.ProviderModel;
import elshab7.engineering.elshab7_rss_news_app.R;
import elshab7.engineering.elshab7_rss_news_app.Utils.SharedPrefUtil;

import static elshab7.engineering.elshab7_rss_news_app.Utils.Common.getDomainName;
import static elshab7.engineering.elshab7_rss_news_app.Utils.Common.isConnectedToInternet;

public class MainActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private RSS_Adapter mRssAdapter;
    private List<String> mNewsProviders;
    private SharedPrefUtil mSharedPrefUtil;
    private List<ProviderModel> mDbList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int cnt=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPrefUtil=new SharedPrefUtil(this);
        mNewsProviders=new ArrayList<>(mSharedPrefUtil.getCurrentProvidersList());
        mDbList =new ArrayList<>();

        mSwipeRefreshLayout =findViewById(R.id.swipe_layout);


        mRssAdapter=new RSS_Adapter(this, mDbList);

        mRecyclerView=findViewById(R.id.rss_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        //fetchFeed();

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRssAdapter);


        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        mSwipeRefreshLayout.canChildScrollUp();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                synchronizeData();
                updateFeed();
            }
        });

        //for(String url:mNewsProviders)
        //new ProcessInBackground2().execute(url);

        //for(String url:mNewsProviders)
        //    new ReadRss2().execute(url);

        //new ProcessInBackground3().execute();

        //new ReadRss2().execute(mNewsProviders.get(1));
        //


        //tasks=new ArrayList<>();

        //new testTask().execute();

        updateFeed();

        //    tasks.add((MyJsonTask) new MyJsonTask().execute(url));

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!mNewsProviders.equals(mSharedPrefUtil.getCurrentProvidersList())) {
            synchronizeData();
            updateFeed();
        }
    }

    private void updateFeed() {
        Snackbar.make(mSwipeRefreshLayout, "Updating content...", Snackbar.LENGTH_SHORT).show();
        mDbList=mSharedPrefUtil.getCurrentDbList();
        mSwipeRefreshLayout.setRefreshing(true);
        cnt=0;
        if(isConnectedToInternet(MainActivity.this)){
            mRecyclerView.setAdapter(null);
            mRecyclerView.removeAllViews();
            mRecyclerView.invalidate();
            for(String url:mNewsProviders)
                fetchFeed(url);
        }else{
            mRssAdapter = new RSS_Adapter(MainActivity.this, mDbList);
            mRecyclerView.setAdapter(mRssAdapter);
            mRssAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
            Snackbar.make(mSwipeRefreshLayout, "Not connected to the internet...", Snackbar.LENGTH_SHORT).show();
        }
    }


    private void fetchFeed(final String link) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                final Parser parser = new Parser();
                parser.onFinish(new OnTaskCompleted() {

                    //what to do when the parsing is done
                    @Override
                    public void onTaskCompleted(@NotNull List<Article> list) {
                        SaveCurrentDB(new ProviderModel(
                                getDomainName(link),
                                link,
                                getList(list)));
                        //Log.e("hhhhhhhhhh","Cnt="+(cnt+1)+" And Size="+(mNewsProviders.size()-1));

                        check();

                    }

                    //what to do in case of error
                    @Override
                    public void onError(@NotNull Exception e) {
                        check();
                        e.printStackTrace();
                        //parser.execute(link);
                    }

                    private void check() {
                        if(++cnt==mNewsProviders.size()-1){
                            cnt=0;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mSwipeRefreshLayout.setRefreshing(false);
                                    //mRecyclerView.setAdapter(null);
                                    mRecyclerView.removeAllViews();
                                    mRecyclerView.removeAllViewsInLayout();
                                    mRecyclerView.invalidate();
                                    //Log.e("hhhhhhhhhhhh","1=");
                                    if(mDbList!=null){
                                        if(mDbList.size()<1){
                                            Snackbar.make(mSwipeRefreshLayout, "No data to show", Snackbar.LENGTH_LONG).show();
                                        }
                                        //Log.e("hhhhhhhhhhhh","2=");
                                        mRssAdapter = new RSS_Adapter(MainActivity.this, mDbList);
                                        mRecyclerView.setAdapter(mRssAdapter);
                                        mRssAdapter.notifyDataSetChanged();
                                        mRecyclerView.invalidate();
                                        Snackbar.make(mSwipeRefreshLayout, "Data updated", Snackbar.LENGTH_SHORT).show();
                                    }else{
                                        Snackbar.make(mSwipeRefreshLayout, "Fetching data error", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }
                });
                parser.execute(link);
            }
        });
    }

    private void SaveCurrentDB(ProviderModel providerModelNew) {
        boolean isNew=true;

        for(int I=0;I<mDbList.size();I++){
            if(TextUtils.equals(mDbList.get(I).getLink(),providerModelNew.getLink())) {
                mDbList.set(I, providerModelNew);
                isNew = false;
                break;
            }
        }
        if(isNew){
            mDbList.add(providerModelNew);
        }

        synchronizeData();

        if(mSharedPrefUtil.getCurrentDbList()!=null&&mSharedPrefUtil.getCurrentDbList().size()<1) {
            mSharedPrefUtil.setCurrentDbList(mDbList);
            recreate();
            return;
        }

        mSharedPrefUtil.setCurrentDbList(mDbList);
    }

    private void synchronizeData() {
        mNewsProviders=mSharedPrefUtil.getCurrentProvidersList();
        for(int I=0;I<mDbList.size();I++){
            boolean isFound=false;
            int index=0;
            for(int J=0;J<mNewsProviders.size();J++){
                if(TextUtils.equals(mNewsProviders.get(J),mDbList.get(I).getLink())) {
                    isFound=true;
                    index=I;
                    //Log.e("hhhhhhhhhhh","Found: "+mDbList.get(I).getLink());
                    break;
                }
            }
            if(!isFound){
                mDbList.remove(index);
                recreate();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mRssAdapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRssAdapter.onRestoreInstanceState(savedInstanceState);
    }

    private List<MyArticleModel> getList(List<Article> articles) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        List<MyArticleModel> list=new ArrayList<>();
        for(Article article : articles){
            list.add(new MyArticleModel(article.getTitle(),article.getLink(),article.getImage(),sdf.format(article.getPubDate())));
        }
        return list;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_Settings:
                Intent intent=new Intent(MainActivity.this,Settingsctivity.class);
                startActivity(intent);
                return true;
            case R.id.action_Close:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private long lastTBackPressTime = 0;

    @Override
    public void onBackPressed() {
        if (lastTBackPressTime < System.currentTimeMillis() - 4000) {
            Toast.makeText(this, "Press back again to close the application", Toast.LENGTH_LONG).show();
            lastTBackPressTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }
}
