package zorail.rohan.com.myprofiler.profilesettings;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.util.Log;
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
import zorail.rohan.com.myprofiler.login.LoginAccountActivity;

/**
 * Created by zorail on 16-May-17.
 */

public class ProfileSettingsFragment extends Fragment implements ProfileSettingsContract.View {

    @Inject
    ProfileSettingsPresenter presenter;
    private Button deleteAccount;
    private ProgressBar progressBar;
    private EditText passwordInput;
    private TextView cardTitle, cardSubTitle;
    private CardView authCard;

    public ProfileSettingsFragment() {
        // Required empty public constructor
    }

    public static ProfileSettingsFragment newInstance() {
       return new ProfileSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        ProfileSettingsComponent.Builder builder = (ProfileSettingsComponent.Builder)((MyApp)getActivity().getApplication()).getComponent().subcomponentBuilders().get(ProfileSettingsComponent.Builder.class).get();
        builder.profileSettingsModule(new ProfileSettingsModule(this)).build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.subscribe();
    }
    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if(presenter==null)
//            presenter = new ProfileSettingsPresenter(FireBaseAuthService.getInstance(), FirebaseDatabaseService.getInstance(),this, SchedulerProvider.getInstance());
//        presenter.subscribe();
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_settings, container, false);
        progressBar = (ProgressBar) v.findViewById(R.id.pro_settings_loading);

        authCard = (CardView) v.findViewById(R.id.card_settings_auth);
        passwordInput = (EditText) v.findViewById(R.id.edt_settings_card_input);

        cardTitle = (TextView)v.findViewById(R.id.lbl_settings_card_heading);

        cardSubTitle = (TextView)v.findViewById(R.id.lbl_settings_card_sub);


        Button cardProceed = (Button)v.findViewById(R.id.btn_settings_card_proceed);
        cardProceed.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onDeleteAccountConfirmed(passwordInput.getText().toString());
            }
        });
        Button cardCancel = (Button)v.findViewById(R.id.btn_settings_card_cancel);
        cardCancel.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                showAuthCard(false);
            }
        });

        deleteAccount = (Button) v.findViewById(R.id.btn_settings_delete_account);
        deleteAccount.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onDeleteAccountPress();
            }
        });

        return v;
    }

    @Override
    public void setPresenter(ProfileSettingsContract.Presenter presenter) {
        this.presenter  = (ProfileSettingsPresenter) presenter;
    }

    @Override
    public void makeToast(@StringRes int stringId) {
        Toast.makeText(getActivity(), stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void makeToast(String message) {

        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startLogInActivity() {
        Intent i = new Intent(getActivity(), LoginAccountActivity.class);
        startActivity(i);
    }

    @Override
    public void showAuthCard(boolean show) {
        if (show) {
            authCard.setVisibility(android.view.View.VISIBLE);
            authCard.setClickable(true);

            cardTitle.setText(R.string.prompt_delete_user);
            cardSubTitle.setText(R.string.prompt_enter_password);

            Log.d("PROFILE_SETTINGS", Integer.toString(cardSubTitle.getVisibility()));

            progressBar.setVisibility(android.view.View.INVISIBLE);
        } else {
            authCard.setVisibility(android.view.View.INVISIBLE);
            authCard.setClickable(false);
        }
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (show) {
            progressBar.setVisibility(android.view.View.VISIBLE);

        } else {
            progressBar.setVisibility(android.view.View.INVISIBLE);
        }
    }
    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }
}
