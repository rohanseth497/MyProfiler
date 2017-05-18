package zorail.rohan.com.myprofiler.photogallery;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import zorail.rohan.com.myprofiler.ActivityUtils;
import zorail.rohan.com.myprofiler.R;
import zorail.rohan.com.myprofiler.profilepage.ProfilePageActivity;

/**
 * Created by zorail on 17-May-17.
 */

public class PhotoGalleryActivity extends AppCompatActivity {

    private static final String GALLERY_FRAGMENT = "GALLERY_FRAGMENT";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        manager = this.getFragmentManager();

        ActivityCompat.requestPermissions(PhotoGalleryActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpPhotoGalleryComponent();
                } else {
                    Toast.makeText(this,
                            R.string.error_permission_denied,
                            Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(this, ProfilePageActivity.class);
                    startActivity(i);
                }
                return;
            }
        }
    }

    public void setUpPhotoGalleryComponent (){
        PhotoGalleryFragment fragment = (PhotoGalleryFragment)
                manager.findFragmentByTag(GALLERY_FRAGMENT);

        if (fragment == null){
            fragment = PhotoGalleryFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(manager,
                fragment,
                R.id.cont_photo_gallery_fragment,
                GALLERY_FRAGMENT
        );
    }

}
