package zorail.rohan.com.myprofiler.photodetail;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import zorail.rohan.com.myprofiler.MyApp;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.Util.SchedulerProvider;
import zorail.rohan.com.myprofiler.data.FireBaseAuthService;
import zorail.rohan.com.myprofiler.data.database.FirebaseDatabaseService;
import zorail.rohan.com.myprofiler.photogallery.PhotoGalleryActivity;
import zorail.rohan.com.myprofiler.profilepage.ProfilePageActivity;

/**
 * Created by zorail on 17-May-17.
 */

public class PhotoDetailFragment extends Fragment implements PhotoDetailContract.View {

    private static final String PHOTO_URL = "PHOTO_URL";
    @Inject
    PhotoDetailPresenter presenter;
    private ImageButton back, done;
    private ImageView photo;
    private ProgressBar progressBar;
    private String photoURL;

    public PhotoDetailFragment()
    {
    }

    public static PhotoDetailFragment newInstance(String photoURL) {
        PhotoDetailFragment f = new PhotoDetailFragment();
        Bundle args = new Bundle();
        args.putString(PHOTO_URL, photoURL);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
        if (getArguments() != null){
            this.photoURL = getArguments().getString(PHOTO_URL);
        }
        DaggerPhotoDetailComponent.builder().netComponent(((MyApp)getActivity().getApplication()).getComponent())
                .photoDetailModule(new PhotoDetailModule(this))
                .build().inject(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.subscribe();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_detail, container, false);
        back = (ImageButton) v.findViewById(R.id.imb_photo_detail_back);
        done = (ImageButton) v.findViewById(R.id.imb_photo_detail_done);
        photo = (ImageView) v.findViewById(R.id.imv_photo_detail);

        progressBar = (ProgressBar) v.findViewById(R.id.pro_photo_loading);

        back.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onBackButtonPress();
            }
        });

        done.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onDoneButtonPress();
            }
        });

        return v;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if(presenter==null)
//            presenter = new PhotoDetailPresenter(FireBaseAuthService.getInstance(), FirebaseDatabaseService.getInstance(),this, SchedulerProvider.getInstance());
//        presenter.subscribe();
//    }

    @Override
    public void makeToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setBitmap() {
        Picasso.with(getActivity())
                .load(photoURL)
                .fit()
                .into(photo, new Callback() {
                    @Override
                    public void onSuccess() {
                        presenter.onImageLoaded();
                    }

                    @Override
                    public void onError() {
                        presenter.onImageLoadFailure();
                    }
                });
    }

    @Override
    public void startProfilePageActivity() {
        Intent i = new Intent(getActivity(), ProfilePageActivity.class);
        startActivity(i);
    }

    @Override
    public void startPhotoGalleryActivity() {
        Intent i = new Intent(getActivity(), PhotoGalleryActivity.class);
        startActivity(i);
    }

    @Override
    public void makeToast(@StringRes int message) {
        Toast.makeText(getActivity().getApplicationContext(), getString(message), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setPresenter(PhotoDetailContract.Presenter presenter) {
        this.presenter = (PhotoDetailPresenter) presenter;
    }

    @Override
    public void showProgressIndicator(boolean show) {
        if (show) {
            progressBar.setVisibility(android.view.View.VISIBLE);
            photo.setVisibility(android.view.View.INVISIBLE);
        } else {
            progressBar.setVisibility(android.view.View.INVISIBLE);
            photo.setVisibility(android.view.View.VISIBLE);
        }
    }

    @Override
    public String getPhotoURL() {
        return this.photoURL;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
