package elshab7.engineering.elshab7_rss_news_app.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import elshab7.engineering.elshab7_rss_news_app.R;
import elshab7.engineering.elshab7_rss_news_app.Utils.SharedPrefUtil;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    private final List<String> mList;
    private final Context mContext;
    private final SharedPrefUtil mSharedPrefUtil;

    public LinkAdapter(Context context){
        mContext=context;
        mSharedPrefUtil=new SharedPrefUtil(context);
        mList=mSharedPrefUtil.getCurrentProvidersList();
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.row_add_link, parent, false);

        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LinkViewHolder holder, final int position) {
        holder.setLink(mList.get(position));

        ImageButton dotsMenu=holder.itemView.findViewById(R.id.LinkDotsMenu);
        dotsMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(holder.itemView,"Provider: "+mList.get(position),Snackbar.LENGTH_SHORT).show();

                PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        // TODO Auto-generated method stub
                        switch (item.getItemId()) {
                            case R.id.Link_Menu_Edit:
                                holder.linkTxt.setEnabled(true);

                                final Button editDoneBtn=holder.itemView.findViewById(R.id.AddLinkBtn);
                                editDoneBtn.setVisibility(View.VISIBLE);

                                editDoneBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mList.set(position,holder.linkTxt.getText().toString().trim());
                                        mSharedPrefUtil.setCurrentProvidersList(mList);
                                        notifyDataSetChanged();
                                        holder.linkTxt.setEnabled(false);
                                        editDoneBtn.setVisibility(View.GONE);
                                    }
                                });
                                return true;
                            case R.id.Link_Menu_Delete:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                                alertDialogBuilder.setCancelable(true);
                                alertDialogBuilder.setMessage(R.string.LinkDelete);
                                alertDialogBuilder.setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                    mList.remove(position);
                                                    notifyDataSetChanged();
                                                    mSharedPrefUtil.setCurrentProvidersList(mList);
                                            }
                                        });
                                alertDialogBuilder.setNegativeButton("No", null);
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.inflate(R.menu.link_menu);
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LinkViewHolder extends RecyclerView.ViewHolder {

        final TextInputEditText linkTxt;


        LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            linkTxt=itemView.findViewById(R.id.AddRssLinkTxtView);
        }

        void setLink(String link) {
            linkTxt.setText(link);
            linkTxt.setEnabled(false);
        }
    }

}
