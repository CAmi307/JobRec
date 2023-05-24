package com.jobrec.adapters;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jobrec.R;
import com.jobrec.db.UsersDAO;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Manager;

import java.util.ArrayList;

public class CandidatesPageAdapter extends RecyclerView.Adapter<CandidatesPageAdapter.CandidateViewHolder> {
    public ArrayList<Candidate> candidates;
    private String jobId;

    public CandidatesPageAdapter(ArrayList<Candidate> candidates, String jobId) {
        this.candidates = candidates;
        this.jobId = jobId;
    }

    public class CandidateViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView currentJobTitle;
        private TextView age;
        private ImageView picture;
        private ImageButton favouriteBtn;

        public CandidateViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.candidat_name_text);
            this.currentJobTitle = itemView.findViewById(R.id.current_tile_text);
            this.age = itemView.findViewById(R.id.age_text);
            this.picture = itemView.findViewById(R.id.candidat_image);
            this.favouriteBtn = itemView.findViewById(R.id.favourite_btn);
        }
    }

    @NonNull
    @Override
    public CandidateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.candidat_item, parent, false);

        return new CandidateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateViewHolder holder, int position) {
        if(candidates.get(position).getAge()!= null) {
            int age = candidates.get(position).getAge();
            holder.age.setText(String.valueOf(age));
        }

        if(candidates.get(position).getFirstName()!=null) {
            String name = candidates.get(position).getFirstName() + " " + candidates.get(position).getLastName();
            holder.name.setText(name);
        }

        if(candidates.get(position).getCurrentJob()!=null) {
            String currentJobTitle = candidates.get(position).getCurrentJob();
            holder.currentJobTitle.setText(currentJobTitle);
        }

        if(candidates.get(position).getPicture()!=null) {
            String imageUrl = candidates.get(position).getPicture();
            //TODO: ADD IMG
        }

        Dialog mDialog = new Dialog(holder.itemView.getContext());
        mDialog.setContentView(R.layout.apply_popup);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setCanceledOnTouchOutside(true);
        TextView question = mDialog.findViewById(R.id.question_text);
        question.setText("Accep");
        Button buttonYes = mDialog.findViewById(R.id.yes_btn);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersDAO.getInstance().unapplyForJob(jobId, candidates.get(position).getId());
                candidates.remove(candidates.get(position));
                notifyItemChanged(holder.getAdapterPosition());
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
                mDialog.show();
            }
        });

        holder.favouriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (candidates.get(position).isFavourite()) {
                    holder.favouriteBtn.setImageResource(R.drawable.ic_empty_star);
                    candidates.get(position).setFavourite(false);
                } else {
                    holder.favouriteBtn.setImageResource(R.drawable.ic_full_star);
                    candidates.get(position).setFavourite(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return candidates.size();
    }
}
