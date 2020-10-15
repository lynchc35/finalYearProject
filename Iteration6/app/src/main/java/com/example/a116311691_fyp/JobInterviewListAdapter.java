package com.example.a116311691_fyp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//JobListingAdapter.java - adjusted
//START

public class JobInterviewListAdapter extends RecyclerView.Adapter<JobInterviewListAdapter.InterviewListViewHolder>{


    private Context mCtx;
    private List<InterviewListModel> interviewListModelList;

    public JobInterviewListAdapter(Context mCtx, List<InterviewListModel> interviewListModelList) {
        this.mCtx = mCtx;
        this.interviewListModelList = interviewListModelList;
    }

    @NonNull
    @Override
    public JobInterviewListAdapter.InterviewListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.job_interviews_list, null);
        return new JobInterviewListAdapter.InterviewListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobInterviewListAdapter.InterviewListViewHolder holder, int position) {

        InterviewListModel interview = interviewListModelList.get(position);
        holder.tvApplicantName.setText(interview.getApplicantName());
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
        /*
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
        holder.tvOutcome.setText(interview.getOutcome());


         */
    }

    @Override
    public int getItemCount() {
        return interviewListModelList.size();
    }

    class InterviewListViewHolder extends RecyclerView.ViewHolder{

        TextView tvApplicantName, tvTime, tvLocation;
                //, tvAdditional, tvFeedback, tvOutcome;

        public InterviewListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvApplicantName = itemView.findViewById(R.id.tvApplicantName);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            /*
            tvAdditional = itemView.findViewById(R.id.tvAdditional);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            tvOutcome = itemView.findViewById(R.id.tvOutcome);

             */
        }
    }
    //END
}
