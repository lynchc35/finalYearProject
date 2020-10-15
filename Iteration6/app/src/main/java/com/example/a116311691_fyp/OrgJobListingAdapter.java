package com.example.a116311691_fyp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//START
//https://www.youtube.com/watch?v=a4o9zFfyIM4 - adjusted code to fit my values
public class OrgJobListingAdapter extends RecyclerView.Adapter<OrgJobListingAdapter.JobListingViewHolder>{

    private Context mCtx;
    private List<OrgJobListingsModel> jobListingsList;

    public OrgJobListingAdapter(Context mCtx, List<OrgJobListingsModel> jobListingsList) {
        this.mCtx = mCtx;
        this.jobListingsList = jobListingsList;
    }

    @NonNull
    @Override
    public JobListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.org_job_list_layout, null);
        return new JobListingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobListingViewHolder holder, int position) {

        OrgJobListingsModel jobListing = jobListingsList.get(position);
        holder.tvRoleTitle.setText(jobListing.getRoleTitle());
        holder.tvLocation.setText(jobListing.getLocation());
        holder.tvRequirements.setText(jobListing.getRoleRequirements());
        holder.tvRoleDuties.setText(jobListing.getRoleDuties());
        String salary = "€" + jobListing.getSalary();
        holder.tvSalary.setText(salary);
    }

    @Override
    public int getItemCount() {
        return jobListingsList.size();
    }

    class JobListingViewHolder extends RecyclerView.ViewHolder{

        TextView tvRoleTitle, tvLocation, tvRequirements, tvRoleDuties, tvSalary;

        public JobListingViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRoleTitle = itemView.findViewById(R.id.tvRoleTitle);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvRequirements = itemView.findViewById(R.id.tvRequirements);
            tvRoleDuties = itemView.findViewById(R.id.tvRoleDuties);
            tvSalary = itemView.findViewById(R.id.tvSalary);
        }
    }
}

//END
