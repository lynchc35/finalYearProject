package com.example.a116311691_fyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//JobListingAdapter.java - adjusted
//START

public class ApplicantInterviewListAdapter extends RecyclerView.Adapter<ApplicantInterviewListAdapter.InterviewListViewHolder>{


    private Context mCtx;
    private List<InterviewListModel> interviewListModelList;

    public ApplicantInterviewListAdapter(Context mCtx, List<InterviewListModel> interviewListModelList) {
        this.mCtx = mCtx;
        this.interviewListModelList = interviewListModelList;
    }

    @NonNull
    @Override
    public ApplicantInterviewListAdapter.InterviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.applicant_interviews, null);
        return new ApplicantInterviewListAdapter.InterviewListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicantInterviewListAdapter.InterviewListViewHolder holder, int position) {

        InterviewListModel interview = interviewListModelList.get(position);
        holder.tvRoleTitle.setText(interview.getRoleTitle());
        //holder.tvTime.setText(interview.getTime());
        //OrgInterviewSchedule.java
        //START
        String dbTime = interview.getTime();
        String[] seperated = dbTime.split(" ");
        String date = seperated[0];
        String time = seperated[1];

        String[] timeSep = time.split(":");
        String hourSep = timeSep[0];
        String minuteSep = timeSep[1];

        String timeDisplay = date + " " + hourSep + ":" + minuteSep;
        holder.tvTime.setText(timeDisplay);
        //END

        holder.tvLocation.setText(interview.getLocation());
        holder.tvOutcome.setText(interview.getOutcome());
        if (interview.getAdditional().equals("")){
            holder.tvAdditional.setText("n/a");
        } else {
            holder.tvAdditional.setText(interview.getAdditional());
        }

        if (interview.getFeedback().equals("")){
            holder.tvFeedback.setText("n/a");
        } else {
            holder.tvFeedback.setText(interview.getFeedback());
        }
    }

    @Override
    public int getItemCount() {
        return interviewListModelList.size();
    }

    class InterviewListViewHolder extends RecyclerView.ViewHolder{

        TextView tvRoleTitle, tvTime, tvLocation, tvAdditional, tvFeedback, tvOutcome;

        public InterviewListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRoleTitle = itemView.findViewById(R.id.tvRoleTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvAdditional = itemView.findViewById(R.id.tvAdditional);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            tvOutcome = itemView.findViewById(R.id.tvOutcome);
        }
    }
    //END
}
