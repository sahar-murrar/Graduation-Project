package com.project.carty.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.project.carty.Models.User;
import com.project.carty.R;
import com.squareup.picasso.Picasso;

public class ProfileActivity  extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private TextView profileNameTextView, profilePasswordTextView, profilePhonenoTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView profilePicImageView;
    private FirebaseStorage firebaseStorage;
    private TextView textViewemailname;
    private EditText editTextName;
   // private List<User> mUploads;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        profilePicImageView = findViewById(R.id.profile_pic_imageView);
        profileNameTextView = findViewById(R.id.profile_name_textView);
        profilePasswordTextView = findViewById(R.id.profile_Password_textView);
        profilePhonenoTextView = findViewById(R.id.profile_phoneno_textView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
       // mDatabaseRef = FirebaseDatabase.getInstance().getReference("Fruits and Vegetables");

        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile Info");
        StorageReference storageReference = firebaseStorage.getReference();


        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
        storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                // ".fit().centerInside()" fits the entire image into the specified area.
                // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                Picasso.get().load(uri).fit().centerInside().into(profilePicImageView);
            }
        });
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                profileNameTextView.setText(userProfile.getName());
                profilePasswordTextView.setText(userProfile.getPassword());
                profilePhonenoTextView.setText(userProfile.getPhone());
                textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
                textViewemailname.setText(user.getEmail());
            }
            @Override
            public void onCancelled( DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void buttonClickedEditName(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_name, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Name Edit");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = etUsername.getText().toString();
                String password = profilePasswordTextView.getText().toString();
                String phoneno =  profilePhonenoTextView.getText().toString();
                String email =  textViewemailname.getText().toString();

                User userinformation = new User(name,email,phoneno,password);
                DatabaseReference userInfoRef = FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("User Info");

                databaseReference.child("Users").child(firebaseAuth.getUid()).child("Profile Info").setValue(userinformation);
                userInfoRef.child("fullname").setValue(name);
                Toast.makeText(ProfileActivity.this, " Name Edited Successfully", Toast.LENGTH_SHORT).show();


                // databaseReference.child(user.getUid()).setValue(userinformation);
               // databaseReference.setValue(userinformation);
               // databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Profile Info");
                etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public void buttonClickedEditSurname(final View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_password, null);
        final EditText etUserPassword = alertLayout.findViewById(R.id.et_userpassword);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Password Edit");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String password = etUserPassword.getText().toString();
                if (TextUtils.isEmpty(password)) {
                    etUserPassword.setError("Enter the Password!");
                    return;
                } else {

                    String name = profileNameTextView.getText().toString();
                    String passwordu = etUserPassword.getText().toString();
                    String phoneno =  profilePhonenoTextView.getText().toString();
                    String email =  textViewemailname.getText().toString();
                    User userinformation = new User(name,email,phoneno,passwordu);
                    databaseReference.child("Users").child(firebaseAuth.getUid()).child("Profile Info").setValue(userinformation);
                    Toast.makeText(ProfileActivity.this, " password Edited Successfully", Toast.LENGTH_SHORT).show();

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    user.updatePassword(passwordu);
                    //  FirebaseUser user = firebaseAuth.getCurrentUser();
                 //   databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userinformation);
                    // databaseReference.child(user.getUid()).setValue(userinformation);
                    etUserPassword.onEditorAction(EditorInfo.IME_ACTION_DONE);

                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
    public void buttonClickedEditPhoneNo(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_phoneno, null);
        final EditText etUserPhoneno = alertLayout.findViewById(R.id.et_userPhoneno);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Phone Edit");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = profileNameTextView.getText().toString();
                String passwordu = profilePasswordTextView.getText().toString();
                String phoneno =  etUserPhoneno.getText().toString();
                String email =  textViewemailname.getText().toString();

                User userinformation = new User(name,email,phoneno,passwordu);
                databaseReference.child("Users").child(firebaseAuth.getUid()).child("Profile Info").setValue(userinformation);
                DatabaseReference userInfoRef = FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Information").child("User Info");
                userInfoRef.child("phone").setValue(phoneno);
                Toast.makeText(ProfileActivity.this, " phone Edited Successfully", Toast.LENGTH_SHORT).show();

                // databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Users").setValue(userinformation);

                // databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Users").child("Profile Info").setValue(userinformation);
               //  databaseReference.child(user.getUid()).setValue(userinformation);
               // databaseReference.child(user.getUid()).setValue(userinformation);
                etUserPhoneno.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void navigateLogOut(View v){
        FirebaseAuth.getInstance().signOut();
        Intent inent = new Intent(this, LoginActivity.class);
        startActivity(inent);
    }
}