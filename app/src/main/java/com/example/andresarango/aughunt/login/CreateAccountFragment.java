package com.example.andresarango.aughunt.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountFragment extends Fragment {
    private View rootView;
    @BindView(R.id.tv_create_profile_name) EditText profileNameEtv;
    @BindView(R.id.etv_create_email) EditText emailEtv;
    @BindView(R.id.etv_create_password) EditText passEtv;
    @BindView(R.id.btn_register_account) Button registerBtn;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_acount_create, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String name = profileNameEtv.getText().toString().trim();
        String email = emailEtv.getText().toString().trim();
        String pass = passEtv.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(rootView.getContext(), "Invalid fields", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(rootView.getContext(), "Register success!", Toast.LENGTH_SHORT).show();

                    storeUserInFirebaseDatabase(name);

                    // Go back to login fragment
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.activity_start, new LoginFragment())
                            .commit();

                } else {
                    Toast.makeText(rootView.getContext(), "Register failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void storeUserInFirebaseDatabase(String name) {
        String userId = auth.getCurrentUser().getUid();
        rootRef.child("users").child(userId).setValue(new User(userId, name));
    }
}
