package com.jobrec.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jobrec.domain.Candidate;
import com.jobrec.domain.Manager;
import com.jobrec.domain.User;
import com.jobrec.mappers.Mappers;
import com.jobrec.utils.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UsersDAO {

    private FirebaseAuth mAuth;
    private FirebaseFirestore database;

    private static UsersDAO instance = null;
    private static FirebaseUser currentUser;


    private UsersDAO() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    public static UsersDAO getInstance() {
        if (instance == null) {
            instance = new UsersDAO();
        }
        return instance;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        database.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Candidate user = Mappers.mapToCandidate(documentSnapshot);
                        users.add(user);
                    }
                }
            }
        });

        return users;
    }

    public void getJobsForManager(Callback callback) {
        String managerId = Manager.getInstance().getId();
        database.collection("Managers").document(managerId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<DocumentReference> jobs = (ArrayList<DocumentReference>) task.getResult().getData().get("jobs");

                    callback.onCallbackReceived(jobs);
                }
            }
        });
    }

    public void addNewCandidate(String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        database.collection("Users").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                          @Override
                                          public void onSuccess(DocumentReference documentReference) {
                                              Log.d("DB", "new user added");
                                          }
                                      }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DB", "error adding new user");
                    }
                });
    }

    public void getUser(String email, Callback callback) {
        database.collection("Users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().getDocuments().isEmpty()) {
                        DocumentSnapshot docRef = task.getResult().getDocuments().get(0);
                        callback.onCallbackReceived(Mappers.mapToCandidate(docRef));
                    } else {
                        Log.e("AMALIA", "no user from db");
                    }
                } else {
                    Log.e("AMALIA", "Failed");
                }
            }
        });
    }

    public void getManager(String email, Callback callback) {
        database.collection("Managers").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().getDocuments().isEmpty()) {
                        DocumentSnapshot docRef = task.getResult().getDocuments().get(0);
                        callback.onCallbackReceived(Mappers.mapToManager(docRef));
                    } else {
                        Log.e("AMALIA", "no manager from db");
                    }
                } else {
                    Log.e("AMALIA", "Failed");
                }
            }
        });
    }

    public void addNewManager(String email) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        database.collection("Managers").add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                          @Override
                                          public void onSuccess(DocumentReference documentReference) {
                                              Log.d("DB", "new manager added");
                                          }
                                      }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("DB", "error adding new manager");
                    }
                });
    }

    public void updateManager(Manager manager) {

    }

    public void getAllJobs(Callback callback) {
        database.collection("Jobs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    callback.onCallbackReceived(task.getResult().getDocuments());
                }
            }
        });
    }

    public void setJobFavouriteState(String id, boolean state) {
        database.collection("Jobs").document(id).update("isFavourite", state);
    }

    public void getApplications(Callback callback) {
        String userId = Candidate.getInstance().getId();
        database.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<DocumentReference> applicationsList = (ArrayList<DocumentReference>) task.getResult().getData().get("applications");
                    callback.onCallbackReceived(applicationsList);
                }
            }
        });
    }

    public void getApplicantsForJob(String jobId, Callback callback) {
        database.collection("Jobs").document(jobId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<DocumentReference> applicantsList = (ArrayList<DocumentReference>) task.getResult().getData().get("applicants");
                    for (DocumentReference applicantRef : applicantsList) {
                        applicantRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    callback.onCallbackReceived(task.getResult());
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void applyForJob(String jobId) {
        String userId = Candidate.getInstance().getId();

        //Get current user reference
        database.collection("Users").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> firstTask) {
                if (firstTask.isSuccessful()) {
                    DocumentReference userRef = firstTask.getResult().getReference();
                    ArrayList<DocumentReference> applicationsList = (ArrayList<DocumentReference>) firstTask.getResult().getData().get("applications");
                    database.collection("Jobs").document(jobId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentReference jobRef = task.getResult().getReference();
                                Candidate.getInstance().addApplication(jobRef);
                                ArrayList<DocumentReference> applicantsList = (ArrayList<DocumentReference>) task.getResult().getData().get("applicants");
                                applicantsList.add(userRef);
                                jobRef.update("applicants", applicantsList);
                                //Update the list of applications for user
                                applicationsList.add(jobRef);
                                database.collection("Users").document(userId).update("applications", applicationsList);
                            }
                        }
                    });
                }
            }
        });
    }

    public void updateCurrentUserData(Map<String, Object> newUserData) {
        String userId = Candidate.getInstance().getId();
        database.collection("Users").document(userId).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            Log.e("AMALIA", "here");
                            DocumentReference userRef = task.getResult().getReference();
                            userRef.update(newUserData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.e("AMALIA", "succes");
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("AMALIA", e.toString());
                                }
                            });
                        }
                    }
                }
        );
    }

    public void unapplyForJob(String jobId, String candidateId) {
        database.collection("Users").document(candidateId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> firstTask) {
                if (firstTask.isSuccessful()) {
                    DocumentReference userRef = firstTask.getResult().getReference();
                    ArrayList<DocumentReference> applicationsList = (ArrayList<DocumentReference>) firstTask.getResult().getData().get("applications");

                    database.collection("Jobs").document(jobId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentReference jobRef = task.getResult().getReference();
                                ArrayList<DocumentReference> applicantsList = (ArrayList<DocumentReference>) task.getResult().getData().get("applicants");

                                applicantsList.remove(userRef);
                                jobRef.update("applicants", applicantsList);

                                applicationsList.remove(jobRef);
                                userRef.update("applications", applicationsList);
                            }
                        }
                    });
                }
            }
        });
    }
}
