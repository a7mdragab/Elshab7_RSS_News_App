package elshab7.engineering.elshab7_rss_news_app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import elshab7.engineering.elshab7_rss_news_app.Models.ProviderModel;

import static elshab7.engineering.elshab7_rss_news_app.Utils.Common.getArticlesListFromGson;
import static elshab7.engineering.elshab7_rss_news_app.Utils.Common.getGsonFromArticlesList;
import static elshab7.engineering.elshab7_rss_news_app.Utils.Common.getGsonFromStringList;
import static elshab7.engineering.elshab7_rss_news_app.Utils.Common.getStringListFromGson;


public class SharedPrefUtil {

    private static final String APP_PREFS = "elshab7_RSS_preferences";

    private final SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPrefUtil(Context context) {
        Context mContext = context;
        this.mSharedPreferences = mContext.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public void setCurrentProvidersList(List<String> list) {
        mEditor = mSharedPreferences.edit();
        mEditor.putString("ProvidersList", getGsonFromStringList(list));
        mEditor.apply();
    }
    public List<String> getCurrentProvidersList() {
        List<String> list=new ArrayList<>();
        list.add("http://feeds.feedburner.com/Mobilecrunch");
        list.add("http://feeds.washingtonpost.com/rss/rss_soccer-insider");
        list.add("https://www.androidauthority.com/feed");

        String x=mSharedPreferences.getString("ProvidersList","");
        if(TextUtils.isEmpty(x))
            return list;
        else
            return getStringListFromGson(x);
    }

    public void setCurrentDbList(List<ProviderModel> list){
        mEditor = mSharedPreferences.edit();
        mEditor.putString("CurrentDbList", getGsonFromArticlesList(list));
        mEditor.apply();
    }

    public List<ProviderModel> getCurrentDbList(){
        String x=mSharedPreferences.getString("CurrentDbList","");
        if(!TextUtils.isEmpty(x))
            return getArticlesListFromGson(x);
        else
            return new ArrayList<>();
    }

    public void clearSharedPreferences(){
        mEditor = mSharedPreferences.edit();
        mEditor.remove("ProvidersList");
        mEditor.remove("CurrentDbList");
        mEditor.apply();
    }

}
