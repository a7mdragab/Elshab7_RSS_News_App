package elshab7.engineering.elshab7_rss_news_app.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import elshab7.engineering.elshab7_rss_news_app.Adapters.LinkAdapter;
import elshab7.engineering.elshab7_rss_news_app.R;
import elshab7.engineering.elshab7_rss_news_app.Utils.SharedPrefUtil;

public class Settingsctivity extends AppCompatActivity {

    private RecyclerView mProvidersRecyclerView =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsctivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settingsctivity.this);
                    LayoutInflater inflater = LayoutInflater.from(Settingsctivity.this);
                    @SuppressLint("InflateParams") View mView2 = inflater.inflate(R.layout.dialog_add_link, null);

                    final TextView mMsg = mView2.findViewById(R.id.AddRssLinkDialog);
                    Button mAddBtn = mView2.findViewById(R.id.AddLinkBtn);
                    mBuilder.setView(mView2);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(false);
                    mAddBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final String msg = mMsg.getText().toString().trim();
                            if (TextUtils.isEmpty(msg)) {
                                Toast.makeText(Settingsctivity.this, "Empty Link", Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPrefUtil sharedPrefUtil=new SharedPrefUtil(Settingsctivity.this);
                                List<String> list=sharedPrefUtil.getCurrentProvidersList();
                                list.add(msg);
                                sharedPrefUtil.setCurrentProvidersList(list);
                                dialog.cancel();
                                recreate();
                            }
                        }
                    });
            }
        });

        mProvidersRecyclerView = findViewById(R.id.ProvidersRecyclerView);
        mProvidersRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mProvidersRecyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();

        LinkAdapter mLinksAdapter = new LinkAdapter(this);
        mProvidersRecyclerView.setAdapter(mLinksAdapter);
    }
}
