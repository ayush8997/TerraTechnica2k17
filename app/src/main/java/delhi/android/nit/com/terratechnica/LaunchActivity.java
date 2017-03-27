package delhi.android.nit.com.terratechnica;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;

import delhi.android.nit.com.terratechnica.About_Us.About_Us;
import delhi.android.nit.com.terratechnica.Contact.Contact_Us;
import delhi.android.nit.com.terratechnica.FAQSection.FAQData;
import delhi.android.nit.com.terratechnica.InstaGram.insta;
import delhi.android.nit.com.terratechnica.Maps.Maps;
import delhi.android.nit.com.terratechnica.Sponsor.Sponsor;

public class LaunchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener, Dialog.callback {

    Button signinButton;
    View header;
    FrameLayout contentContainer;
    TextView navname, navEmail;
    ImageView navImage;
    Button logoutButton;
    GoogleApiClient mGoogleApiClient;
    int RC_SIGN_IN = 10298;
    boolean first = false;
    ImageView launchBG, navBG;
    DrawerLayout drawer;
    String name;
    Uri uri;
    String email;
    String gender = "unknown";
    String phone, college;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("FirstTime", MODE_PRIVATE);
        if (sharedPreferences.contains("first")) {
            //Log.e("Manojit","If    "+first);
            first = sharedPreferences.getBoolean("first", false);
        } else {
            //Log.e("Manojit","Else     "+first);
            first = false;       }
        if (first) {
            setContentView(R.layout.login_template);
            callbackManager = CallbackManager.Factory.create();
            //info = (TextView)findViewById(R.id.info);
            loginButton = (LoginButton) findViewById(R.id.login_button);

            //fb callback intercept ...
            loginButton.setReadPermissions(Arrays.asList(
                    "public_profile", "email", "user_birthday"));
            //fb callback intercpt
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    accessToken = loginResult.getAccessToken();
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                            Profile.setCurrentProfile(currentProfile);

//                        Log.e("Manojit", "" + currentProfile);
                            if (Profile.getCurrentProfile() != null)
                                layoutUpdaterLogin(accessToken, Profile.getCurrentProfile());
                            else {

                            }
                            profileTracker.stopTracking();
                        }

                    };
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        layoutUpdaterLogin(accessToken, profile);
                    } else {
                        profileTracker.startTracking();
                    }


                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException e) {
                }
            });

            if (Profile.getCurrentProfile() == null) {

            }

            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    AccessToken.setCurrentAccessToken(currentAccessToken);
                    if (currentAccessToken != oldAccessToken) {
                        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPreferences2.edit();
                        editor.clear();
                        editor.commit();
                        if(!first){
                            navname.setText("user");
                            navEmail.setText("user@email.com");
                            Picasso.with(LaunchActivity.this)
                                    .load(R.drawable.flipkart)
                                    .into(navImage);
                        }
                    }
                }
            };

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        } else {


            setContentView(R.layout.activity_launch);
            launchBG = (ImageView) findViewById(R.id.launchBG);
            Glide.with(this)
                    .load(R.drawable.bg2)
                    .centerCrop()
                    .crossFade()
                    .into(launchBG);

            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            contentContainer = (FrameLayout) findViewById(R.id.contentContainer);

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            header = navigationView.inflateHeaderView(R.layout.nav_header_launch);
            navname = (TextView) header.findViewById(R.id.navName);
            navEmail = (TextView) header.findViewById(R.id.navEmail);
            navImage = (ImageView) header.findViewById(R.id.navImage);
            logoutButton = (Button) header.findViewById(R.id.logoutButton);
            logoutButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            signinButton.setVisibility(View.VISIBLE);
                                            logoutButton.setVisibility(View.GONE);
                                            SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                            SharedPreferences.Editor editor = sharedPreferences2.edit();
                                            editor.clear();
                                            editor.commit();
                                            navname.setText("user");
                                            navEmail.setText("user@email.com");
                                            Picasso.with(LaunchActivity.this)
                                                    .load(R.drawable.flipkart)
                                                    .into(navImage);
                                        }
                                    });

                        }
                    }
            );
            SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//            Log.e("Manojit", "" + sharedPreferences1.contains("name"));
            if (sharedPreferences1.contains("name")) {
//                Log.e("Manojit", "" + sharedPreferences1.getString("name", ""));
                navname.setText(sharedPreferences1.getString("name", ""));
                navEmail.setText(sharedPreferences1.getString("email", ""));
                Picasso.with(this)
                        .load(sharedPreferences1.getString("uri", ""))
                        .into(navImage);
            }
            navBG = (ImageView) header.findViewById(R.id.navBG);
            Picasso.with(this)
                    .load(R.drawable.headers)
                    .into(navBG);
            //fb thing

            callbackManager = CallbackManager.Factory.create();
            //info = (TextView)findViewById(R.id.info);
            loginButton = (LoginButton) header.findViewById(R.id.login_button);
            loginButton.setReadPermissions(Arrays.asList(
                    "public_profile", "email", "user_birthday"));
            //fb callback intercpt
            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {

                    accessToken = loginResult.getAccessToken();
                    profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                            Profile.setCurrentProfile(currentProfile);

//                        Log.e("Manojit", "" + currentProfile);
                            if (Profile.getCurrentProfile() != null)
                                layoutUpdater(accessToken, Profile.getCurrentProfile());
                            else {

                            }
                            profileTracker.stopTracking();
                        }

                    };
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        layoutUpdater(accessToken, profile);
                    } else {
                        profileTracker.startTracking();
                    }


                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException e) {
                }
            });

            if (Profile.getCurrentProfile() == null) {

            }

            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                    AccessToken.setCurrentAccessToken(currentAccessToken);
                    if (currentAccessToken != oldAccessToken) {
                        SharedPreferences sharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor editor = sharedPreferences2.edit();
                        editor.clear();
                        editor.commit();
                        navname.setText("user");
                        navEmail.setText("user@email.com");
                        Picasso.with(LaunchActivity.this)
                                .load(R.drawable.flipkart)
                                .into(navImage);
                    }
                }
            };


            //google login

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                    .requestEmail()
                    .build();

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .addApi(Plus.API)
                    .build();

            signinButton = (Button) header.findViewById(R.id.signinButton);
            signinButton.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                            startActivityForResult(signInIntent, RC_SIGN_IN);

                        }
                    }
            );


            final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
            bottomBar.setDefaultTab(R.id.tab_about_us);
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_about_us) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        if (getSupportFragmentManager().findFragmentById(R.id.contentContainer) != null) {
                            fragmentTransaction.remove(getSupportFragmentManager().findFragmentById(R.id.contentContainer));
                        }
                        fragmentTransaction.add(R.id.contentContainer, new About_Us());
                        fragmentTransaction.commit();
                    } else if (tabId == R.id.tab_contact) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        if (getSupportFragmentManager().findFragmentById(R.id.contentContainer) != null) {
                            fragmentTransaction.remove(getSupportFragmentManager().findFragmentById(R.id.contentContainer));
                        }
                        fragmentTransaction.add(R.id.contentContainer, new Contact_Us());
                        fragmentTransaction.commit();

                    } else if (tabId == R.id.tab_events) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        if (getSupportFragmentManager().findFragmentById(R.id.contentContainer) != null) {
                            fragmentTransaction.remove(getSupportFragmentManager().findFragmentById(R.id.contentContainer));
                        }
                        fragmentTransaction.add(R.id.contentContainer, new Events());
                        fragmentTransaction.commit();
                    } else if (tabId == R.id.tab_instagram) {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        if (getSupportFragmentManager().findFragmentById(R.id.contentContainer) != null) {
                            fragmentTransaction.remove(getSupportFragmentManager().findFragmentById(R.id.contentContainer));
                        }
                        fragmentTransaction.add(R.id.contentContainer, new Sponsor());
                        fragmentTransaction.commit();
                    }

                }
            });
        }

    }

//    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//    Fragment container = getSupportFragmentManager().findFragmentById(R.id.container);
//
//    if (container != null) {
//        transaction.remove(container);
//    }
//
//    Fragment fragment = new Sponsor();
//    transaction.replace(R.id.container, fragment)
//            .addToBackStack(null)
//    .commit();

    public void layoutUpdater(AccessToken accessToken, Profile profile) {
//        Log.e("Manojit", "" + profile.getName());
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        name = profile.getName();
        navname.setText("" + name);
        uri = profile.getProfilePictureUri(400, 400);
        Picasso.with(LaunchActivity.this)
                .load(uri)
                .into(navImage);
        editor.putString("name", name);
        editor.putString("uri", uri.toString());

        GraphRequest graphRequest = GraphRequest.newMeRequest(
                accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
//                            Log.e("Manojit", "" + object.toString());
                            email = object.getString("email");
                            //String birthday = object.getString("birthday");
                            gender = object.getString("gender");
                            editor.putString("email", email);
                            editor.putString("gender", gender);
                            editor.commit();
                            Dialog dialog = new Dialog();
                            dialog.show(getFragmentManager(), "");
                            navEmail.setText(email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();

    }

    public void layoutUpdaterLogin(AccessToken accessToken, Profile profile){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();
//        name = profile.getName();
//        navname.setText("" + name);
        uri = profile.getProfilePictureUri(400, 400);
//        Picasso.with(LaunchActivity.this)
//                .load(uri)
//                .into(navImage);
        editor.putString("name", name);
        editor.putString("uri", uri.toString());

        GraphRequest graphRequest = GraphRequest.newMeRequest(
                accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        try {
//                            Log.e("Manojit", "" + object.toString());
                            email = object.getString("email");
                            //String birthday = object.getString("birthday");
                            gender = object.getString("gender");
                            editor.putString("email", email);
                            editor.putString("gender", gender);
                            editor.commit();
                            Dialog dialog = new Dialog();
                            dialog.show(getFragmentManager(), "");
//                            navEmail.setText(email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    public void navigate(View v) {
        if (!drawer.isDrawerOpen(Gravity.LEFT))
            drawer.openDrawer(Gravity.LEFT);
        else
            drawer.closeDrawer(Gravity.LEFT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //profileTracker.startTracking();
        accessTokenTracker.startTracking();
    }

    public void login(View v) {
        SharedPreferences.Editor editor = getSharedPreferences("FirstTime", MODE_PRIVATE).edit();
        editor.putBoolean("first", false);
        editor.commit();
        startActivity(new Intent(this, LaunchActivity.class));
        finish();
    }

    public void gmailSignin(View v) {
        SharedPreferences.Editor editor = getSharedPreferences("FirstTime", MODE_PRIVATE).edit();
        editor.putBoolean("first", false);
        editor.commit();
        startActivity(new Intent(this, LaunchActivity.class));
        finish();
    }

    public void skip(View v) {
        SharedPreferences.Editor editor = getSharedPreferences("FirstTime", MODE_PRIVATE).edit();
        editor.putBoolean("first", false);
        editor.commit();
        startActivity(new Intent(this, LaunchActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            signinButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            String gende = "";
            // Signed in successfully, show authenticated UI.
            Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
//            Log.e("Manojit", "" + person);
            try {

                JSONObject jsonObject = new JSONObject(person.toString());

                if (person != null) {
                    gende = jsonObject.getString("gender");
//                    Log.e("Manojit", gende + " hello   " + person);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            GoogleSignInAccount acct = result.getSignInAccount();
            navname.setText(acct.getDisplayName());
            navEmail.setText(acct.getEmail());
            Picasso.with(this)
                    .load(acct.getPhotoUrl())
                    .into(navImage);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            name = acct.getDisplayName();
            email = acct.getEmail();
            gender = "unknown";
            if(!gende.equals(""))
                gender = "" + gende;
            editor.putString("gender", gender);
            editor.putString("name", acct.getDisplayName());
            editor.putString("email", acct.getEmail());
            editor.putString("uri", acct.getPhotoUrl().toString());
            editor.commit();
            Dialog dialog = new Dialog();
            dialog.show(getFragmentManager(), "google");
        } else {
        }
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
        getMenuInflater().inflate(R.menu.launch, menu);
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

        if(id == R.id.homes){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment container = getSupportFragmentManager().findFragmentById(R.id.container);

            if (container != null) {
                transaction.remove(container);
            }
            transaction.commit();
        }

        if (id == R.id.locate_us) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment container = getSupportFragmentManager().findFragmentById(R.id.container);

            if (container != null) {
                transaction.remove(container);
            }
            transaction.commit();
            Intent intent = new Intent(LaunchActivity.this, Maps.class);
            startActivity(intent);

        } else if (id == R.id.faq) {
            // Handle the camera action

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment container = getSupportFragmentManager().findFragmentById(R.id.container);

            if (container != null) {
                transaction.remove(container);
            }

            Fragment fragment = new FAQData();
            transaction.replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_gallery) {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment container = getSupportFragmentManager().findFragmentById(R.id.container);

            if (container != null) {
                transaction.remove(container);
            }

            Fragment fragment = new insta();
            transaction.replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_share) {

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_SUBJECT, "TerraTechnica'17");

            // ToDO: Put the app link here ...
            share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?" +
                    "id=com.newbies.terrateam.terratechnica2k17");
            startActivity(share);

        } else if (id == R.id.nav_feed) {
            Intent intent = new Intent(Intent.ACTION_VIEW);

            // TODO: Keep the link for playstore account here ...
            intent.setData(Uri.parse("market://details?id=com.newbies.terrateam.terratechnica2k17"));
            // Eg:  market://details?id=com.example.android ...
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void register() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        phone = sharedPreferences.getString("mobile", "");
        college = sharedPreferences.getString("college", "");
        new Background(LaunchActivity.this).execute(name, phone, gender, email, college);
    }

    private class Background extends AsyncTask<String, Void, String> {

        String registration_link = "http://insigniathefest.com/manojit/terra_register.php";
        Context context;
        AlertDialog.Builder builder;
        Activity activity;
        ProgressDialog progressDialog;

        public Background(Context context) {
            this.context = context;
            activity = (Activity) context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                progressDialog = new ProgressDialog(activity);
                progressDialog.setTitle("Working!!");
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(registration_link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                OutputStream outputStream = connection.getOutputStream();
//                for (int i = 0; i < params.length; i++) {
//                    Log.e("Manojit", params[i] + "\n");
//                }
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = "";
                data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&" +
                        URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" +
                        URLEncoder.encode("gendre", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(params[3], "UTF-8") + "&" +
                        URLEncoder.encode("college", "UTF-8") + "=" + URLEncoder.encode(params[4], "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                connection.disconnect();
                //Log.e("Manoit",stringBuilder.toString()+"");
                Thread.sleep(3000);
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            if (s != null)
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                    JSONObject JO = jsonArray.getJSONObject(0);
                    String code = JO.getString("code");
                    String message = JO.getString("message");

                    if (code.equals("true")) {
//                        Log.e("Manojit", "" + code);
                        builder = new AlertDialog.Builder(context);
                        builder.setTitle("Successful!!");
                        builder.setMessage(message);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                if(first) {
                                    SharedPreferences.Editor editor = getSharedPreferences("FirstTime", MODE_PRIVATE).edit();
                                    editor.putBoolean("first", false);
                                    editor.commit();
                                    startActivity(new Intent(LaunchActivity.this, LaunchActivity.class));
                                    finish();
                                }

                            }
                        });
                        builder.setCancelable(false);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    } else {
                        builder = new AlertDialog.Builder(context);
                        builder.setTitle("UnSuccessful!!");
                        builder.setMessage(message);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                if(first) {
                                    SharedPreferences.Editor editor = getSharedPreferences("FirstTime", MODE_PRIVATE).edit();
                                    editor.putBoolean("first", false);
                                    editor.commit();
                                    startActivity(new Intent(LaunchActivity.this, LaunchActivity.class));
                                    finish();
                                }

                            }
                        });
                        builder.setCancelable(false);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
    }

}
