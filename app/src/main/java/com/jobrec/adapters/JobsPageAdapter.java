package com.jobrec.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.jobrec.R;
import com.jobrec.controllers.CandidateController;
import com.jobrec.db.UsersDAO;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Job;
import com.jobrec.domain.Manager;
import com.jobrec.domain.User;
import com.jobrec.utils.Callback;

import java.util.ArrayList;

public class JobsPageAdapter extends RecyclerView.Adapter<JobsPageAdapter.JobViewHolder> {
    public ArrayList<Job> jobs;
    private Context context;
    private NavController navController = null;

    public JobsPageAdapter(ArrayList<Job> jobs, Context context) {
        this.jobs = jobs;
        this.context = context;
    }

    public JobsPageAdapter(ArrayList<Job> jobs, NavController navController, Context context) {
        this.jobs = jobs;
        this.context = context;
        this.navController = navController;
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        private TextView jobTitle;
        private TextView companyName;
        private TextView shortDescription;

        private ImageButton favouriteBtn;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.e("AMALIA","constructor view holder");

            jobTitle = itemView.findViewById(R.id.job_title_text);
            companyName = itemView.findViewById(R.id.company_name_text);
            shortDescription = itemView.findViewById(R.id.job_description_text);
            favouriteBtn = itemView.findViewById(R.id.favourite_btn);
        }
    }


    @NonNull
    @Override
    public JobsPageAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("AMALIA","inflate view holder");

        View itemView = LayoutInflater
                .from(this.context)
                .inflate(R.layout.job_item, parent, false);

        return new JobViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull JobsPageAdapter.JobViewHolder holder, int pos) {
        Log.e("AMALIA","bind view holder");

        int position = holder.getAdapterPosition();
        String jobTitle = jobs.get(position).getJobTitle();
        holder.jobTitle.setText(jobTitle);

        String companyName = jobs.get(position).getCompanyName();
        holder.companyName.setText(companyName);

        String shortDescription = jobs.get(position).getShortDescription();
        holder.shortDescription.setText(shortDescription);

        Dialog mDialog = new Dialog(holder.itemView.getContext());
        mDialog.setContentView(R.layout.apply_popup);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(true);
        Button buttonYes = mDialog.findViewById(R.id.yes_btn);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersDAO.getInstance().applyForJob(jobs.get(position).getId());
//                UsersDAO.getInstance().(jobs.get(position).getIdManager())
                mDialog.dismiss();
            }
        });
        Button buttonNo = mDialog.findViewById(R.id.no_btn);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.cancel();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Manager.isSingleton) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("job", jobs.get(position));
                    if(navController != null) {
                        navController.navigate(R.id.action_jobs_page_to_candidates, bundle);
                    }
                }else if(Candidate.isSingleton) {
                    mDialog.show();
                }
            }
        });

        if (jobs.get(position).isFavourite()) {
            holder.favouriteBtn.setImageResource(R.drawable.ic_full_star);
        } else {
            holder.favouriteBtn.setImageResource(R.drawable.ic_empty_star);
        }
        holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (jobs.get(position).isFavourite()) {
                    holder.favouriteBtn.setImageResource(R.drawable.ic_empty_star);
                    jobs.get(position).setFavourite(false);
                    UsersDAO.getInstance().setJobFavouriteState(jobs.get(position).getId(), false);
                } else {
                    holder.favouriteBtn.setImageResource(R.drawable.ic_full_star);
                    jobs.get(position).setFavourite(true);
                    UsersDAO.getInstance().setJobFavouriteState(jobs.get(position).getId(), true);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }
}
