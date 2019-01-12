package elshab7.engineering.elshab7_rss_news_app.ViewHolders;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import elshab7.engineering.elshab7_rss_news_app.R;

public class ProviderViewHolder extends GroupViewHolder {

    private final TextView providerNameTxtView;

    public ProviderViewHolder(View itemView) {
        super(itemView);
        providerNameTxtView =itemView.findViewById(R.id.providerNameTxt);
    }

    public void setProviderName(String msg) {
        providerNameTxtView.setText(msg);
    }

    @Override
    public void expand() {
        super.expand();
        itemView.setBackgroundColor(Color.parseColor("#D81B60"));   //Accent
    }

    @Override
    public void collapse() {
        super.collapse();
        itemView.setBackgroundColor(Color.parseColor("#4472C4"));   //Blue
    }
}
