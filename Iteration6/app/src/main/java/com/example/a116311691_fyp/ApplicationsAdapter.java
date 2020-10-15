package com.example.a116311691_fyp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//https://www.simplifiedcoding.net/android-recyclerview-cardview-tutorial/
//START

public class ApplicationsAdapter extends RecyclerView.Adapter<ApplicationsAdapter.ApplicationsViewHolder>{

    private Context mCtx;
    private List<ApplicationModel> applicationModelList;

    public ApplicationsAdapter(Context mCtx, List<ApplicationModel> applicationModelList) {
        this.mCtx = mCtx;
        this.applicationModelList = applicationModelList;
    }

    @NonNull
    @Override
    public ApplicationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_applications, null);
        return new ApplicationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationsViewHolder holder, int position) {

        ApplicationModel application = applicationModelList.get(position);
        holder.tvApplicantName.setText(application.getApplicantName());
        holder.tvAdditional.setText(application.getAdditional());


    }

    @Override
    public int getItemCount() {
        return applicationModelList.size();
    }

    class ApplicationsViewHolder extends RecyclerView.ViewHolder{

        TextView tvApplicantName, tvAdditional;

        public ApplicationsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvApplicantName = itemView.findViewById(R.id.tvApplicantName);
            tvAdditional = itemView.findViewById(R.id.tvAdditional);
        }
    }
}

//END
