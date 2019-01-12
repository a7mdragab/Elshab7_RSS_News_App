package elshab7.engineering.elshab7_rss_news_app.Models;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ProviderModel extends ExpandableGroup<MyArticleModel> {
    private String mLink;


    public ProviderModel(String title,String link, List<MyArticleModel> items) {
        super(title, items);
        mLink=link;
    }

    public ProviderModel(String title, List<MyArticleModel> items) {
        super(title, items);
    }

    public String getLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

}
