package zorail.rohan.com.myprofiler.photodetail;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import zorail.rohan.com.myprofiler.ActivityUtils;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.photogallery.PhotoGalleryActivity;


public class PhotoDetailActivity extends AppCompatActivity {

    private static final String PHOTO_DETAIL_FRAGMENT = "PHOTO_DETAIL_FRAGMENT";
    private static final String EXTRA_PHOTO_URL = "EXTRA_PHOTO_URL";

    private FragmentManager manager;
    private String photoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        photoURL = getIntent().getStringExtra(EXTRA_PHOTO_URL);

        if (photoURL == null){
            startPhotoGalleryActivity();
        }

        manager = this.getFragmentManager();

        //set up fragment
        PhotoDetailFragment fragment = (PhotoDetailFragment)
                manager.findFragmentByTag(PHOTO_DETAIL_FRAGMENT);

        if (fragment == null){
            fragment = PhotoDetailFragment.newInstance(photoURL);
        }

        ActivityUtils.addFragmentToActivity(manager,
                fragment,
                R.id.cont_photo_detail_fragment,
                PHOTO_DETAIL_FRAGMENT
        );

    }


    private void startPhotoGalleryActivity(){
        Intent i = new Intent(this, PhotoGalleryActivity.class);
        startActivity(i);
    }


}
