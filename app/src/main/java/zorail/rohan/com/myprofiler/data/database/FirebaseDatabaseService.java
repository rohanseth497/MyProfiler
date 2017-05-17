package zorail.rohan.com.myprofiler.data.database;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;


/**
 * Created by Ryan on 25/01/2017.
 */

public class FirebaseDatabaseService implements DataBaseSource {

    private static final String USER_PROFILES = "user_profiles";

    private FirebaseDatabaseService() {

    }

    public static DataBaseSource getInstance() {
        return new FirebaseDatabaseService();
    }

    @Override
    public Completable createProfile(final Profile profile) {
        return Completable.create(
                new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(final CompletableEmitter e) throws Exception {
                        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        final DatabaseReference idRef = rootRef.child(USER_PROFILES).child(profile.getUid());
                        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (!snapshot.exists()) {
                                    idRef.setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                e.onComplete();
                                            } else {
                                                e.onError(task.getException());
                                            }
                                        }
                                    });
                                } else {
                                    e.onComplete();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("FIREBASE", databaseError.toString());
                            }

                        });
                    }
                }
        );
    }

    @Override
    public Maybe<Profile> getProfile(final String uid) {
        return Maybe.create(
                new MaybeOnSubscribe<Profile>() {
                    @Override
                    public void subscribe(final MaybeEmitter e) throws Exception {
                        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference idRef = rootRef.child(USER_PROFILES).child(uid);
                        idRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            //does this check node for activeUser exists?
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    //setUpProfilePageComponent(
                                    Profile profile = snapshot.getValue(Profile.class);
                                    e.onSuccess(profile);
                                } else {
                                    e.onComplete();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("FIREBASE", databaseError.toString());
                            }
                        });
                    }
                }
        );
    }

    @Override
    public Completable deleteProfile(final String uid) {
        return Completable.create(
                new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(final CompletableEmitter e) throws Exception {
                        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        rootRef.child(USER_PROFILES)
                                .child(uid)
                                .setValue(null)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            e.onComplete();
                                        } else {
                                            e.onError(task.getException());
                                        }
                                    }
                                });
                    }

                });
    }

    @Override
    public Completable updateProfile(final Profile profile) {
        return Completable.create(
                new CompletableOnSubscribe() {
                    @Override
                    public void subscribe(final CompletableEmitter e) throws Exception {
                        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        rootRef.child(USER_PROFILES)
                                .child(profile.getUid())
                                .setValue(profile)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            e.onComplete();
                                        } else {
                                            e.onError(task.getException());
                                        }
                                    }
                                });
                    }

                });
    }


    @Override
    public Completable uploadImage(final Profile profile) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter e) throws Exception {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl("gs://myprofiler-75995.appspot.com").child(profile.getUid());
                UploadTask task = storageRef.putFile(Uri.parse(profile.getPhotoURL()));
                task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        e.onComplete();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception f) {
                        e.onError(f);
                    }
                });
            }
        });

    }


    @Override
    public void setReturnFail(boolean bool) {
        //only for testing purposes
    }

    @Override
    public void setReturnEmpty(boolean bool) {
        //only for testing purposes

    }

}
