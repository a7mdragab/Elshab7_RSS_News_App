package elshab7.engineering.elshab7_rss_news_app.Adapters;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.browser.customtabs.CustomTabsCallback;
import androidx.browser.customtabs.CustomTabsClient;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.browser.customtabs.CustomTabsServiceConnection;
import androidx.browser.customtabs.CustomTabsSession;
import androidx.core.content.ContextCompat;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.listeners.GroupExpandCollapseListener;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.HashMap;
import java.util.List;

import elshab7.engineering.elshab7_rss_news_app.Models.MyArticleModel;
import elshab7.engineering.elshab7_rss_news_app.Models.ProviderModel;
import elshab7.engineering.elshab7_rss_news_app.R;
import elshab7.engineering.elshab7_rss_news_app.ViewHolders.ArticleViewHolder;
import elshab7.engineering.elshab7_rss_news_app.ViewHolders.ProviderViewHolder;

public class RSS_Adapter extends ExpandableRecyclerViewAdapter<ProviderViewHolder, ArticleViewHolder> {
    private final Context mContext;
    private CustomTabsClient mClient;
    private final HashMap<String,Boolean> mState=new HashMap<>();

    public RSS_Adapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        mContext=context;

        this.setOnGroupExpandCollapseListener(new GroupExpandCollapseListener() {
            @Override
            public void onGroupExpanded(ExpandableGroup group) {
                mState.put(group.getTitle(),true);
            }

            @Override
            public void onGroupCollapsed(ExpandableGroup group) {
                mState.put(group.getTitle(),false);
            }
        });

        CustomTabsServiceConnection mConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                mClient = customTabsClient;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mClient = null;
            }
        };

        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome", mConnection);
    }

    @Override
    public ProviderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_provider, parent, false);
        return new ProviderViewHolder(view);
    }

    @Override
    public ArticleViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ArticleViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final MyArticleModel article = ((ProviderModel) group).getItems().get(childIndex);

        holder.setArticleTitle(article.getTitle());

        holder.setArticlePubDate(article.getPubDate());

        holder.setArticleAvatar(mContext,article.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("hhhhhClick","here");
                loadCustomTabs(article.getLink());
            }
        });


    }


    @Override
    public void onBindGroupViewHolder(ProviderViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setProviderName(group.getTitle());
        if(!mState.containsKey(group.getTitle()))mState.put(group.getTitle(),false);

        if(mState.get(group.getTitle())) holder.expand();
        else holder.collapse();
    }


    private void loadCustomTabs(String url) {
        CustomTabsIntent.Builder mBuilder = new CustomTabsIntent.Builder(getSession());
        mBuilder.setToolbarColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        mBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(mContext.getResources(),
                R.mipmap.ic_arrow_back_white_24dp));

        mBuilder.setStartAnimations(mContext, R.anim.slide_in_right, R.anim.slide_out_left);
        mBuilder.setExitAnimations(mContext, R.anim.slide_in_left, R.anim.slide_out_right);
        CustomTabsIntent mIntent = mBuilder.build();
        mIntent.launchUrl(mContext, Uri.parse(url));
    }

    private CustomTabsSession getSession() {
        return mClient.newSession(new CustomTabsCallback() {
            @Override
            public void onNavigationEvent(int navigationEvent, Bundle extras) {
                super.onNavigationEvent(navigationEvent, extras);
            }
        });
    }

}
