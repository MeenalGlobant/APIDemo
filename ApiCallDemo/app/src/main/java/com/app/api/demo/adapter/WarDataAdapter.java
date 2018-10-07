package com.app.api.demo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.api.demo.R;
import com.app.api.demo.model.ModelWarDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WarDataAdapter extends
        RecyclerView.Adapter<WarDataAdapter.ViewHolderWarDetails> {

    // declaring variables
    private Context context;
    private List<ModelWarDetails> listWarDetails;

    //constructor
    public WarDataAdapter(Context context, List<ModelWarDetails> listWarDetails) {
        this.context = context;
        this.listWarDetails = listWarDetails;
    }

    @Override
    public ViewHolderWarDetails onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_war_details, parent, false);
        ViewHolderWarDetails viewHolderWarDetails = new ViewHolderWarDetails(view);

        return viewHolderWarDetails;
    }

    @Override
    public void onBindViewHolder(ViewHolderWarDetails holder, int position) {

        //setting data on each row of list according to position.

        holder.txtName.setText(listWarDetails.get(position).getName());
        holder.txtLocation.setText(listWarDetails.get(position).getLocation());
        holder.txtAttackerKing.setText(listWarDetails.get(position).getAttacker_king());
        holder.txtDefenderKing.setText(listWarDetails.get(position).getDefender_king());
    }

    //returns list size

    @Override
    public int getItemCount() {
        return listWarDetails.size();
    }

    class ViewHolderWarDetails extends RecyclerView.ViewHolder {

        // declaring variables
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtLocation)
        TextView txtLocation;
        @BindView(R.id.txtAttackerKing)
        TextView txtAttackerKing;
        @BindView(R.id.txtDefenderKing)
        TextView txtDefenderKing;
        @BindView(R.id.layoutCard)
        CardView layoutCard;

        ViewHolderWarDetails(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}
