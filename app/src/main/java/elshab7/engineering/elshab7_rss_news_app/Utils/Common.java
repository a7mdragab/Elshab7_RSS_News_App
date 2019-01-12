package elshab7.engineering.elshab7_rss_news_app.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import elshab7.engineering.elshab7_rss_news_app.Models.ProviderModel;

public class Common {


    public static List<ProviderModel> getArticlesListFromGson(String string) {
        Type type = new TypeToken<List<ProviderModel>>() {}.getType();
        return new Gson().fromJson(string, type);
    }

    public static String getGsonFromStringList(List<String> List) {
        return new Gson().toJson(List);
    }

    public static List<String> getStringListFromGson(String string) {
        Type type = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(string, type);
    }

    public static String getGsonFromArticlesList(List<ProviderModel> List) {
        return new Gson().toJson(List);
    }

    /*private class checkUrl extends AsyncTask<String,Void,Boolean> {
        HttpURLConnection connection;
        int code = 0;
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                connection = (HttpURLConnection) new URL(strings[0]).openConnection();
                if (connection != null) {
                    connection.setRequestMethod("GET");
                    code = connection.getResponseCode();
                    connection.disconnect();
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return code==200;
        }
    }
    private boolean check(final String image) {

        try {
            return new checkUrl().execute(image).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    private int findLastIndex(String image) {
        List<String> Types=new ArrayList<>();
        Types.add(".jpg");
        Types.add(".png");
        Types.add(".jpeg");
        Types.add(".gif");
        Types.add(".webp");
        int Temp=0;
        int index=Integer.MAX_VALUE;
        for(int I=0;I<Types.size();I++) {
            if (image.contains(Types.get(I))){
                if(image.indexOf(Types.get(I))<index) {
                    index = image.indexOf(Types.get(I));
                    Temp=I;
                }
            }
        }
        return index+(Types.get(Temp).length());
    }
    */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static ImageViewer.OnImageChangeListener getImageChangeListener(final TextView currentPage, final int totalPage) {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                currentPage.setText(position+1 + "/" + totalPage);
            }
        };
    }

    public static ImageViewer.OnDismissListener getDismissListener(final View FV) {
        return new ImageViewer.OnDismissListener() {
            @Override
            public void onDismiss() {
                ((ViewGroup)FV.getParent()).removeView(FV);
            }
        };
    }

    public static String getDomainName(String url) {
        try {
            URI uri = new URI(url);
            String domain = uri.getHost();
            return domain.startsWith("www.") ? domain.substring(4) : domain;
        }catch (Exception e){}
        return "Provider";
    }
}
