package com.example.andresarango.aughunt.login;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.andresarango.aughunt.R;
import com.example.andresarango.aughunt.homescreen.HomeScreenActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment{
    private View rootView;
    private EditText emailEtv;
    private EditText passEtv;
    private Button registerBtn;
    private TextView createAccountTv;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEtv = (EditText) view.findViewById(R.id.etv_login_email);
        passEtv = (EditText) view.findViewById(R.id.etv_login_password);
        registerBtn = (Button) view.findViewById(R.id.btn_login_account);
        createAccountTv = (TextView) view.findViewById(R.id.tv_create_account);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        createAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.activity_start, new CreateAccountFragment())
                        .commit();
            }
        });

    }

    private void loginUser() {
        String email = emailEtv.getText().toString().trim();
        String pass = passEtv.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(rootView.getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(rootView.getContext(), "Sign in success!", Toast.LENGTH_SHORT).show();

                    // Go to home screen activity
                    Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(rootView.getContext(), "Sign in failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
