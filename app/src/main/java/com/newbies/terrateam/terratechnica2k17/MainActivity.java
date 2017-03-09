package com.newbies.terrateam.terratechnica2k17;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.newbies.terrateam.terratechnica2k17.NavDrawer.AboutUs;
import com.newbies.terrateam.terratechnica2k17.NavDrawer.FAQData;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CircleFragment.Communicator {

    private Toolbar toolbar;

    private ActionBarDrawerToggle toggle;
    View header;
    private FirebaseAuth mAuth;
    private SignInButton mGoogleBtn;
    private static final String TAG = "MAIN_ACTIVITY";
    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    Button button;

    TextView navname,navEmail;
    ImageView navImage;

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "Raleway-Regular.ttf");
        fontChanger.replaceFonts((ViewGroup)this.findViewById(android.R.id.content));

        manager = getFragmentManager();
//        transaction = manager.beginTransaction();

        manager.beginTransaction().replace(R.id.mainContainer, new CircleFragment()).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().hide();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.inflateHeaderView(R.layout.nav_header_main);
        navname = (TextView) header.findViewById(R.id.navName);
        navEmail = (TextView) header.findViewById(R.id.navEmail);
        navImage= (ImageView) header.findViewById(R.id.navImage);
        mGoogleBtn = (SignInButton) header.findViewById(R.id.googleBtn);
        button = (Button) header.findViewById(R.id.signin_logout);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        if(sharedPreferences.contains("name"))
        {
            mGoogleBtn.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
            navname.setText(sharedPreferences.getString("name","user"));
            navEmail.setText(sharedPreferences.getString("email","email"));
            Picasso.with(this)
                    .load(sharedPreferences.getString("url",""))
                    .into(navImage);
        }


        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        signOut();

                    }
                }
        );


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    //startActivity(new Intent(MainActivity.this, AccountActivity.class));
                }
            }
        };
        mAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(MainActivity.this, "You Got an Error", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

     void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // ...
                        mGoogleBtn.setVisibility(View.VISIBLE);
                        button.setVisibility(View.GONE);
                        navname.setText("user");
                        navEmail.setText("email");
                        Picasso.with(getBaseContext())
                                .load(R.drawable.notification)
                                .into(navImage);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        sharedPreferences.edit()
                                .clear()
                                .commit();
                    }
                });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("Manojit","Hello      "+result.isSuccess());
            if (result.isSuccess()) {
                mGoogleBtn.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount acct = result.getSignInAccount();
                //Take details from here and store it in the database
                Log.e("Manojit","Hello      "+acct.getDisplayName());
                navname.setText(acct.getDisplayName());
                navEmail.setText(acct.getEmail());
                Picasso.with(this)
                        .load(acct.getPhotoUrl())
                        .into(navImage);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                sharedPreferences.edit()
                        .putString("name",acct.getDisplayName())
                        .putString("email",acct.getEmail())
                        .putString("url",acct.getPhotoUrl().toString())
                        .commit();
                //Log.e("Manojit",acct.getPhotoUrl().toString()+"");
                //firebaseAuthWithGoogle(acct);
            } else {

                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        transaction = manager.beginTransaction();

        if (id == R.id.nav_home) {
            transaction.replace(R.id.mainContainer, new CircleFragment()).commit();
        }
        else if (id == R.id.nav_about_us) {
            transaction.replace(R.id.mainContainer, new AboutUs()).addToBackStack(null).commit();
            getSupportActionBar().setTitle("About Us");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        else if (id == R.id.nav_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "TerraTechnica'17");

            // ToDO: Put the app link here ...
            share.putExtra(Intent.EXTRA_TEXT, "com.newbies.terrateam.terratechnica2k17");
            startActivity(share);
        }
        else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            // TODO: Keep the link for playstore account here ...
            intent.setData(Uri.parse("market://details?id=com.newbies.terrateam.terratechnica2k17"));  // Eg:  market://details?id=com.example.android ...
            startActivity(intent);
        }
        else if(id == R.id.nav_faq) {
            transaction.replace(R.id.mainContainer, new FAQData()).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void openNavDrawer() {
        drawer.openDrawer(GravityCompat.START);
    }
}