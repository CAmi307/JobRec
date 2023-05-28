package com.jobrec.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.jobrec.databinding.ActivityCompleteProfileBinding;
import com.jobrec.db.UsersDAO;
import com.jobrec.domain.Candidate;
import com.jobrec.utils.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {

    private ActivityCompleteProfileBinding binding;
    private UsersDAO usersDAO;
    private String selectedDegree;
    private String selectedWorkingHours;
    private String selectedWorkingType;
    ArrayList<String> degrees = new ArrayList<>(Arrays.asList("PRIMARY_SCHOOL", "HIGH_SCHOOL", "LICENSE", "MASTER", "DOCTOR"));
    ArrayList<String> types = new ArrayList<>(Arrays.asList("FULL_REMOTE", "HYBRID", "PHYSICAL"));
    ArrayList<String> hours = new ArrayList<>(Arrays.asList("FOUR_HOURS", "SIX_HOURS", "EIGHT_HOURS"));


    private AdapterView.OnItemSelectedListener degreeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            selectedDegree = degrees.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            /* no-op */
        }
    };

    private AdapterView.OnItemSelectedListener workingTypeListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            selectedWorkingType = types.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            /* no-op */
        }
    };

    private AdapterView.OnItemSelectedListener workingHoursListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            selectedWorkingHours = hours.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            /* no-op */
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("AMALIA", "created");
        super.onCreate(savedInstanceState);

        usersDAO = UsersDAO.getInstance();
        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> degreeAdapter = new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                degrees
        );

        usersDAO.getUser(
                FirebaseAuth.getInstance().getCurrentUser().getEmail().toString(),
                new Callback() {
                    @Override
                    public void onCallbackReceived(Object obj) {
                        Candidate candidate = (Candidate) obj;
                        if (candidate.getFirstName() != null) {
                            binding.firstName.setText(candidate.getFirstName());
                        }
                        if (candidate.getLastName() != null) {
                            binding.lastName.setText(candidate.getLastName());
                        }
                        if (candidate.getCity() != null) {
                            binding.city.setText(candidate.getCity());
                        }
                    }
                }
        );

        binding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<>();
                if (selectedDegree != null) {
                    map.put("lastDegree", selectedDegree);
                }
                if (selectedWorkingHours != null) {
                    map.put("workingHours", selectedWorkingHours);
                }
                if (selectedWorkingType != null) {
                    map.put("workingType", selectedWorkingType);
                }
                if (binding.firstName.getText() != null) {
                    String firstName = binding.firstName.getText().toString();
                    map.put("firstName", firstName);
                }
                if (binding.lastName.getText() != null) {
                    String lastName = binding.lastName.getText().toString();
                    map.put("lastName", lastName);
                }
                if (binding.city.getText() != null) {
                    String city = binding.city.getText().toString();
                    map.put("city", city);
                }

                for (Object pair : map.values()) {
                    Log.e("AMALIA", pair.toString());
                }
                usersDAO.updateCurrentUserData(map);

                onBackPressed();
            }
        });
    }
}
