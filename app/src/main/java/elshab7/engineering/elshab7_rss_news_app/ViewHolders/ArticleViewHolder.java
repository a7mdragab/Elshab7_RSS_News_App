package elshab7.engineering.elshab7_rss_news_app.ViewHolders;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.stfalcon.frescoimageviewer.ImageViewer;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.Collections;

import elshab7.engineering.elshab7_rss_news_app.R;

public class ArticleViewHolder extends ChildViewHolder {

    private final TextView articleTitle;
    private final TextView articlePubDate;
    private final SimpleDraweeView articleImageView;

    public ArticleViewHolder(View itemView) {
        super(itemView);
        articleTitle=itemView.findViewById(R.id.articleTitleTxt);
        articlePubDate=itemView.findViewById(R.id.articlePubDateTxt);
        articleImageView=itemView.findViewById(R.id.articleImageView);
    }

    public void setArticleTitle(String msg) {
        articleTitle.setText(msg);
    }

    public void setArticlePubDate(String msg) {
        articlePubDate.setText(msg);
    }

    public void setArticleAvatar(final Context context,final String Avatar) {
        if(Avatar==null|| TextUtils.isEmpty(Avatar)||(Avatar.toLowerCase().contains("logo"))){
            articleImageView.setVisibility(View.GONE);
            return;
        }
        articleImageView.setImageURI(Avatar);
        articleImageView.setVisibility(View.VISIBLE);
        articleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mShowEnlargedImageView(context,Avatar);
                try {
                    //noinspection unchecked
                    new ImageViewer.Builder(context, Collections.singletonList(Avatar))
                            .setStartPosition(0)
                            .show();
                }catch (Exception ignored){}
            }
        });
    }
}
