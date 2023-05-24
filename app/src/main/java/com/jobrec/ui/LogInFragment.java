package com.jobrec.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.jobrec.R;
import com.jobrec.app.CandidateMainActivity;
import com.jobrec.app.EmployerMainActivity;
import com.jobrec.controllers.CandidateController;
import com.jobrec.controllers.ManagerController;
import com.jobrec.databinding.FragmentLogInBinding;
import com.jobrec.db.UsersDAO;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Manager;
import com.jobrec.domain.UserType;
import com.jobrec.utils.Callback;

public class LogInFragment extends Fragment {
    private FragmentLogInBinding binding;
    private UsersDAO database;
    private FirebaseAuth mAuth;

    private CandidateController candidateController;
    private ManagerController managerController;

    private UserType userType = UserType.ADMIN;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        Log.e("AMALIA", "1");
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            // TODO: open main activity
//        }
//    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Log.e("AMALIA", "2");
        candidateController = CandidateController.getInstance();
        managerController = ManagerController.getInstance();
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpRadioButtons();

        binding.questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LogInFragment.this)
                        .navigate(R.id.action_LogIn_Fragment_to_SignUp_Fragment);
            }
        });

        binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailFieldEditText.getText().toString();
                String password = binding.passwordFieldEditText.getText().toString();
                binding.progressBar.setVisibility(View.VISIBLE);

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getContext(), "Please complete all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                binding.progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    if(userType == UserType.CANDIDATE) {
                                        candidateController.isUserRegistered(email, new Callback() {
                                            @Override
                                            public void onCallbackReceived(Object obj) {
                                                if (((Candidate) obj).getUserType() == UserType.CANDIDATE) {
                                                    candidateController.updateCurrentUser((Candidate) obj);
                                                    startMainPageForActivity(CandidateMainActivity.class);
                                                } else {
                                                    Toast.makeText(getContext(), "Your account is not a candidate account", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else if(userType == UserType.MANAGER) {
                                        managerController.isUserRegistered(email, new Callback() {
                                            @Override
                                            public void onCallbackReceived(Object obj) {
                                                if(((Manager) obj).getUserType() == UserType.MANAGER) {
                                                    managerController.updateCurrentUser((Manager) obj);
                                                    startMainPageForActivity(EmployerMainActivity.class);
                                                } else {
                                                    Toast.makeText(getContext(), "Your account is not a manager account", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startMainPageForUser() {
        switch (userType) {
            case MANAGER: {
//                startMainPageForActivity();
            }
        }
    }

    private void startMainPageForActivity(Class destinationClass) {
        Intent intent = new Intent(getActivity(), destinationClass);
        startActivity(intent);
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
}
