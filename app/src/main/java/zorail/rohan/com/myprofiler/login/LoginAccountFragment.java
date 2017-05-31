package zorail.rohan.com.myprofiler.login;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import javax.inject.Inject;


import zorail.rohan.com.myprofiler.MyApp;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.createaccount.CreateAccountActivity;
import zorail.rohan.com.myprofiler.profilepage.ProfilePageActivity;

/**
 * Created by zorail on 16-May-17.
 */

public class LoginAccountFragment extends Fragment implements LoginAccountContract.View {

    private Button login, register;
    private TextView emailLabel, passwordLabel;
    private EditText emailInput, passwordInput;
    private ProgressBar progressBar;
    private View contentContainer;
    @Inject
    LoginAccountPresenter presenter;

    public LoginAccountFragment(){}

    public static LoginAccountFragment newInstance(){ return new LoginAccountFragment();}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLoginComponent.builder()
                .netComponent(((MyApp)getActivity().getApplication()).getComponent())
                .loginModule(new LoginModule(this,getActivity().getApplicationContext()))
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);


        contentContainer = v.findViewById(R.id.cont_login_fragment_content);
        progressBar = (ProgressBar) v.findViewById(R.id.pro_login_loading);

        emailInput = (EditText) v.findViewById(R.id.edt_login_email);
        passwordInput = (EditText) v.findViewById(R.id.edt_login_password);

        emailLabel = (TextView) v.findViewById(R.id.lbl_login_email_sub);
        passwordLabel = (TextView) v.findViewById(R.id.lbl_login_password_sub);

        login = (Button) v.findViewById(R.id.btn_login);
        register = (Button) v.findViewById(R.id.btn_create_account);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreateClick();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogInClick();
            }
        });

        setUpListeners();
        emailInput.requestFocus();
        return v;
    }

    public void setUpListeners() {
        emailInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View label, boolean hasFocus) {
                if (hasFocus) {
                    emailLabel.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorAccent)
                    );
                } else {
                    emailLabel.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), android.R.color.white)
                    );
                }
            }
        });

        passwordInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View label, boolean hasFocus) {
                if (hasFocus) {
                    passwordLabel.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorAccent)
                    );
                } else {
                    passwordLabel.setTextColor(
                            ContextCompat.getColor(getActivity().getApplicationContext(), android.R.color.white)
                    );
                }
            }
        });
    }

    @Override
    public void setPresenter(LoginAccountContract.Presenter presenter) {
        this.presenter =(LoginAccountPresenter) presenter;
    }

    @Override
    public void makeToast(@StringRes int stringId) {
        Toast.makeText(getActivity().getApplicationContext(), getString(stringId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getEmail() {
        return emailInput.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordInput.getText().toString();
    }

    @Override
    public void startProfileActivity() {
        Intent i = new Intent(getActivity(), ProfilePageActivity.class);
        startActivity(i);
    }

    @Override
    public void startCreateAccountActivity() {
        Intent i = new Intent(getActivity(), CreateAccountActivity.class);
        startActivity(i);
    }


    @Override
    public void showProgressIndicator(boolean show) {
        if (show){
            progressBar.setVisibility(View.VISIBLE);
            contentContainer.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            contentContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
