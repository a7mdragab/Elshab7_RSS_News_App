package elshab7.engineering.elshab7_rss_news_app.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import elshab7.engineering.elshab7_rss_news_app.R;

public class AddNewSiteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_site);
    }

    public void PerformAdding(View view) {
        finish();
    }

    public void CancelAdding(View view) {
        finish();
    }
}
