package elshab7.engineering.elshab7_rss_news_app.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyArticleModel implements Parcelable {

    private String title;
    private String link;
    private String img;
    private String pubDate;

    private MyArticleModel(Parcel in) {
        try {
            img=(in.readString());
            link=(in.readString());
            title=(in.readString());

            Locale.setDefault(Locale.getDefault());
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

            pubDate =(in.readString());

        }catch (Exception e){
            Log.e("MyArticleModel",e.getMessage());
        }
    }
    public MyArticleModel(){}

    public MyArticleModel(String title,String link, String img, String pubDate) {
        this.title=(title);
        this.link=(link);
        this.img=(img);
        this.pubDate =(pubDate);

        Locale.setDefault(Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(link);
        dest.writeString(title);

        Locale.setDefault(Locale.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        dest.writeString(sdf.format(pubDate));
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyArticleModel> CREATOR = new Creator<MyArticleModel>() {
        @Override
        public MyArticleModel createFromParcel(Parcel in) {
            return new MyArticleModel(in);
        }

        @Override
        public MyArticleModel[] newArray(int size) {
            return new MyArticleModel[size];
        }
    };

    @NonNull
    @Override
    public String toString() {
        return "Article/ Title: "+title
                +"\nLink: "+link
                +"\nImage: "+img;
    }
}
