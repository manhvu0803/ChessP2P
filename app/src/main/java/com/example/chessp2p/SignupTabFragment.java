package com.example.chessp2p;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SignupTabFragment extends Fragment {
    EditText id;
    EditText password;
    EditText repeatPassword;
    Button register;
    float v = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        id = root.findViewById(R.id.id);
        password = root.findViewById(R.id.password);
        repeatPassword = root.findViewById(R.id.repeat_password);
        register = root.findViewById(R.id.reg_button);

        id.setTranslationX(800);
        password.setTranslationX(800);
        repeatPassword.setTranslationX(800);
        register.setTranslationX(800);

        id.setAlpha(v);
        password.setAlpha(v);
        repeatPassword.setAlpha(v);
        register.setAlpha(v);

        id.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        repeatPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        register.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        return root;
    }
}
