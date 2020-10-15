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

public class ApplicantApplicationsAdapter extends RecyclerView.Adapter<ApplicantApplicationsAdapter.ApplicantApplicationsViewHolder>{

    private Context mCtx;
    private List<ApplicantApplicationsModel> applicationsList;

    public ApplicantApplicationsAdapter(Context mCtx, List<ApplicantApplicationsModel> applicationsList) {
        this.mCtx = mCtx;
        this.applicationsList = applicationsList;
    }

    @NonNull
    @Override
    public ApplicantApplicationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.applicant_applications, null);
        return new ApplicantApplicationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantApplicationsViewHolder holder, int position) {

        ApplicantApplicationsModel application1 = applicationsList.get(position);
        holder.tvOrgName.setText(application1.getOrgName());
        holder.tvRoleTitle.setText(application1.getRoleTitle());
        holder.tvAdditional.setText(application1.getAdditional());

    }

    @Override
    public int getItemCount() {
        return applicationsList.size();
    }

    class ApplicantApplicationsViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrgName, tvRoleTitle, tvAdditional;

        public ApplicantApplicationsViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrgName = itemView.findViewById(R.id.tvOrgName);
            tvRoleTitle = itemView.findViewById(R.id.tvRoleTitle);
            tvAdditional = itemView.findViewById(R.id.tvAdditional);
        }
    }
}

//END
