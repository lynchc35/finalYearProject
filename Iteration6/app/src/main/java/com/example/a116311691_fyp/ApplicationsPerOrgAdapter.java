package com.example.a116311691_fyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ApplicationsPerOrgAdapter extends RecyclerView.Adapter<ApplicationsPerOrgAdapter.ApplicationsPerOrgViewHolder> {


    private Context mCtx;
    private List<ApplicationsPerOrgModel> applicationsList;

    public ApplicationsPerOrgAdapter(Context mCtx, List<ApplicationsPerOrgModel> applicationsList) {
        this.mCtx = mCtx;
        this.applicationsList = applicationsList;
    }

    @NonNull
    @Override
    public ApplicationsPerOrgAdapter.ApplicationsPerOrgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.applications_per_org, null);
        return new ApplicationsPerOrgAdapter.ApplicationsPerOrgViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationsPerOrgAdapter.ApplicationsPerOrgViewHolder holder, int position) {

        ApplicationsPerOrgModel application1 = applicationsList.get(position);
        holder.tvApplicantName.setText(application1.getApplicantName());
        holder.tvRole.setText(application1.getRoleTitle());
        holder.tvAdditional.setText(application1.getAdditional());

    }

    @Override
    public int getItemCount() {
        return applicationsList.size();
    }

    class ApplicationsPerOrgViewHolder extends RecyclerView.ViewHolder{

        TextView tvApplicantName, tvRole, tvAdditional;

        public ApplicationsPerOrgViewHolder(@NonNull View itemView) {
            super(itemView);

            tvApplicantName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvAdditional = itemView.findViewById(R.id.tvAdditional);
        }
    }
}
