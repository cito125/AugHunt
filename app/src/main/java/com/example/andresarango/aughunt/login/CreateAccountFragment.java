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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccountFragment extends Fragment {
    private View rootView;
    private EditText emailEtv;
    private EditText passEtv;
    private Button registerBtn;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_acount_create, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEtv = (EditText) view.findViewById(R.id.etv_create_email);
        passEtv = (EditText) view.findViewById(R.id.etv_create_password);
        registerBtn = (Button) view.findViewById(R.id.btn_register_account);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String email = emailEtv.getText().toString().trim();
        String pass = passEtv.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(rootView.getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(rootView.getContext(), "Register success!", Toast.LENGTH_SHORT).show();

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
}
