package zorail.rohan.com.myprofiler.profiledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.FireBaseAuthService;
import zorail.rohan.com.myprofiler.data.database.FirebaseDatabaseService;
import zorail.rohan.com.myprofiler.profilepage.ProfilePageActivity;

/**
 * Created by zorail on 17-May-17.
 */

public class ProfileDetailFragment extends Fragment implements ProfileDetailContract.View {

    private EditText bioInput, interestsInput;
    private ImageButton back, done;
    private ProfileDetailContract.Presenter presenter;

    public ProfileDetailFragment(){
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(presenter==null)
            presenter = new ProfileDetailPresenter(FireBaseAuthService.getInstance(), FirebaseDatabaseService.getInstance(),this, SchedulerProvider.getInstance());
        presenter.subscribe();
    }

    public static ProfileDetailFragment newInstance(){return new ProfileDetailFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_profile_detail, container, false);
            bioInput = (EditText)v.findViewById(R.id.edt_profile_bio_input);
            interestsInput = (EditText)v.findViewById(R.id.edt_profile_interests_input);

            back = (ImageButton)v.findViewById(R.id.imb_profile_detail_back);
            back.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    presenter.onBackButtonClick();
                }
            });

            done = (ImageButton)v.findViewById(R.id.imb_profile_detail_done);
            done.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View view) {
                    presenter.onDoneButtonClick();
                }
            });

            return v;
    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setBioText(String bio) {
        bioInput.setText(bio);
    }

    @Override
    public void setInterestsText(String interests) {
        interestsInput.setText(interests);
    }

    @Override
    public String getInterests() {
        return interestsInput.getText().toString();
    }

    @Override
    public String getBio() {
        return bioInput.getText().toString();
    }

    @Override
    public void startProfilePageActivity() {
        Intent i = new Intent(getActivity(), ProfilePageActivity.class);
        startActivity(i);
    }

    @Override
    public void setPresenter(ProfileDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void makeToast(@StringRes int message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
