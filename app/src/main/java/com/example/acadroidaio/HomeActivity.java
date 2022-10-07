package com.example.acadroidaio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.acadroidaio.fragment.AboutusFragment;
import com.example.acadroidaio.fragment.VideoMainPage;
import com.example.acadroidaio.fragment.ContactusFragment;
import com.example.acadroidaio.fragment.FactsMainPage;
import com.example.acadroidaio.fragment.HomeFragment;
import com.example.acadroidaio.fragment.ProfileFragment;
import com.example.acadroidaio.fragment.QueriesMainFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ERROR";
    DrawerLayout drawerLayout;
    NavigationView navView;
    Toolbar toolbar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String currentUserID = mAuth.getCurrentUser().getUid();
    DocumentReference usersRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView header_profile_image;
    TextView navHeaderName, navHeaderEmail;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dialog = new ProgressDialog(this);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");

        usersRef = db.collection("Students").document(currentUserID);

        navView = findViewById(R.id.navView);
        View headerView = navView.inflateHeaderView(R.layout.nav_header);
        header_profile_image = headerView.findViewById(R.id.header_profile_image);
        navHeaderName = headerView.findViewById(R.id.navHeaderName);
        navHeaderEmail = headerView.findViewById(R.id.navHeaderEmail);

        usersRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                if (snapshot.exists()) {
                    String fname = snapshot.getString("FName");
                    String lname = snapshot.getString("LName");
                    String email = snapshot.getString("Email");
                    navHeaderName.setText(fname + " " + lname);
                    navHeaderEmail.setText(email);

                } else {
                    Toast.makeText(getApplicationContext(), "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        navView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        navView.setCheckedItem(R.id.navHome);
        //setFactsMainFragment();
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack("fragmentA")
                    .replace(R.id.container, new HomeFragment(), "fragmentA")
                    .commit();
        }

        /*FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d(TAG, token);
                        //Toast.makeText(HomeActivity.this, token, Toast.LENGTH_SHORT).show();
                        usersRef.update("token",token);
                    }
                });*/

    }

    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            // User is signed in
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navHome:
                setHomeFragment();
                break;
            case R.id.navQuery:
                setQueryMainFragment();
                break;
            case R.id.navFacts:
                setFactsMainFragment();
                break;
            case R.id.navVideos:
                setVideoPageFragment();
                break;
            case R.id.navProfile:
                setProfilePageFragment();
                break;
            case R.id.navContactUs:
                setContactFragment();
                break;
            case R.id.navAboutUs:
                setAboutFragment();
                break;
            case R.id.navLogout:
                mAuth.signOut();
                sendUserToLogin();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(getApplicationContext(), AuthActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    void setHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
        getSupportActionBar().setTitle("Home");
        setAnimation();
    }

    private void setQueryMainFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new QueriesMainFragment()).commit();
        getSupportActionBar().setTitle("Query");
        setAnimation();
    }

    void setFactsMainFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new FactsMainPage()).commit();
        getSupportActionBar().setTitle("Facts");
        setAnimation();
    }

    void setVideoPageFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new VideoMainPage()).commit();
        getSupportActionBar().setTitle("Videos");
        setAnimation();
    }

    void setProfilePageFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
        getSupportActionBar().setTitle("Profile");
        setAnimation();
    }

    void setContactFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ContactusFragment()).commit();
        getSupportActionBar().setTitle("Contact Us");
        setAnimation();
    }

    void setAboutFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AboutusFragment()).commit();
        getSupportActionBar().setTitle("About Us");
        setAnimation();
    }

    private void setAnimation() {
        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .repeat(0)
                .playOn(findViewById(R.id.container));

    }

    public void replaceFragment(Fragment fragment, String tag) {
        //Get current fragment placed in container
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        //Prevent adding same fragment on top
        if (currentFragment.getClass() == fragment.getClass()) {
            return;
        }
        //If fragment is already on stack, we can pop back stack to prevent stack infinite growth
        if (getSupportFragmentManager().findFragmentByTag(tag) != null) {
            getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        //Otherwise, just replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(R.id.container, fragment, tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        int fragmentsInStack = getSupportFragmentManager().getBackStackEntryCount();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentsInStack > 1) {
            // If we have more than one fragment, pop back stack
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().show();
        } else if (fragmentsInStack == 1) {
            // Finish activity, if only one fragment left, to prevent leaving empty screen
            finish();
        } else {
            super.onBackPressed();
        }
    }

    public void setDrawer_Locked() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void setDrawer_UnLocked() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }
}