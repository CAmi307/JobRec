package com.jobrec.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jobrec.R;
import com.jobrec.app.CandidateMainActivity;
import com.jobrec.app.EmployerMainActivity;
import com.jobrec.controllers.CandidateController;
import com.jobrec.controllers.ManagerController;
import com.jobrec.databinding.FragmentSignUpBinding;
import com.jobrec.domain.UserType;

public class SignUpFragment extends Fragment {
    private UserType userType = UserType.ADMIN;
    private FragmentSignUpBinding binding;
    private FirebaseAuth mAuth;

    private CandidateController candidateController = CandidateController.getInstance();
    private ManagerController managerController = ManagerController.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // TODO: open main activity
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRadioButtons();

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = binding.emailFieldEditText.getText().toString();
                password = binding.passwordFieldEditText.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || userType.equals(UserType.ADMIN)) {
                    Toast.makeText(getContext(), "Please complete all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
//
//                if(userType == UserType.CANDIDATE && !candidateController.isUserRegistered(email)) {
//                    Toast.makeText(getContext(), "This candidate account already exists", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(userType == UserType.EMPLOYER && !managerController.isUserRegistered(email)){
//                    Toast.makeText(getContext(), "This manager account already exists", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(userType == UserType.ADMIN) {

                }
                if(userType == UserType.UNKNOWN) {
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                binding.progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    if(userType == UserType.CANDIDATE) {
                                        candidateController.createNewCandidate(email);
                                        startMainPageForActivity(CandidateMainActivity.class);
                                    } else if(userType == UserType.MANAGER) {
                                        managerController.createNewManager(email);
                                        startMainPageForActivity(EmployerMainActivity.class);
                                    } else if(userType == UserType.ADMIN) {
                                        //TODO: do admin stuff
                                    }
                                    Toast.makeText(getContext(),"Authentication Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Authentication failed: " + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        binding.questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpFragment.this)
                        .navigate(R.id.action_SignUp_Fragment_to_LogIn_Fragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpRadioButtons() {
        binding.radioCandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                if (checked) {
                    userType = UserType.CANDIDATE;
                }
            }
        });

        binding.radioEmployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();
                if (checked) {
                    userType = UserType.MANAGER;
                }
            }
        });
    }

    private void startMainPageForActivity(Class destinationClass) {
        Intent intent = new Intent(getActivity(), destinationClass);
        startActivity(intent);
    }
}
