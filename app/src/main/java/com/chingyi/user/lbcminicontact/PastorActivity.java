package com.chingyi.user.lbcminicontact;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PastorActivity extends AppCompatActivity {

    private RecyclerView mRecycleList;

    private DatabaseReference mUserDatabase;
    private AlertDialog.Builder build;
    FirebaseRecyclerAdapter<Users,PastorActivity.UserViewHolder> recyclerAdapter;
    private MaterialSearchView pastor_MaterialSearchView;
    private Button phoneBTN;
    private Button phoneBTN1;
    private TextView mDiagPhone1;

    private Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        pastor_MaterialSearchView.setMenuItem(item);
        return  true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pastor);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("offices");
        mUserDatabase.keepSynced(true);


        toolbar = (Toolbar) findViewById(R.id.pastor_searchToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pastor & Office Contacts");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        pastor_MaterialSearchView = (MaterialSearchView)findViewById(R.id.pastor_materialsearchView);

        pastor_MaterialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                firebaseSearch();

            }
        });

        pastor_MaterialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText != null && !newText.isEmpty()) {

                    firebaseSearch(newText);

                }
                else {
                    firebaseSearch();
                }
                return true;
            }

        });









        mRecycleList=(RecyclerView)findViewById(R.id.pastor_mSearchListView);
        mRecycleList.setHasFixedSize(true);
        mRecycleList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {

        super.onStart();
        recyclerAdapter = new FirebaseRecyclerAdapter<Users, PastorActivity.UserViewHolder>(
                Users.class,
                R.layout.deatail_contact_view,
                PastorActivity.UserViewHolder.class,
                mUserDatabase
        ) {
            @Override
            protected void populateViewHolder(PastorActivity.UserViewHolder viewHolder, final Users model, int position) {
                final String pos = getRef(position).getKey();
                viewHolder.setDetails(PastorActivity.this,model.getName(),model.getPhone(),model.getImage());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mUserDatabase.child(pos).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("phone1")){
                                    dialogHelper(model.getName(), model.getPhone(),model.getPhone1());
                                }
                                else {

                                    dialogHelper(model.getName(), model.getPhone());

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }
        };

        mRecycleList.setAdapter(recyclerAdapter);
    }
    public void dialogHelper(String name, final String phone)  {


        //new Dialog Box

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PastorActivity.this);
        View mDiaView = getLayoutInflater().inflate(R.layout.second_new_dialog_layout, null);

        TextView mTextView = (TextView) mDiaView.findViewById(R.id.title_view);
        TextView mDiagName = (TextView) mDiaView.findViewById(R.id.dialog_new_name);
        TextView mDiagPhone = (TextView) mDiaView.findViewById(R.id.dialog_phone_view);
        //mDiagPhone1 = (TextView) mDiaView.findViewById(R.id.dialog_phone_view1);

        phoneBTN = (Button) mDiaView.findViewById(R.id.dialog_phone_button);
        // phoneBTN1 = (Button)mDiaView.findViewById(R.id.dialog_phone_button1);



        mDiagName.setText(name);
        mDiagPhone.setText(phone);
        //mDiagPhone1.setText(phone1);




        //ImageView mCallBtn = (ImageView) mDiaView.findViewById(R.id.imageButton);




        phoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + phone + ""));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);

                            return;
                        }

                        startActivity(i);
                    }


                }
            }
        });


        mBuilder.setView(mDiaView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    public void dialogHelper(String name, final String phone, final String phone1)  {


        //new Dialog Box

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(PastorActivity.this);
        View mDiaView = getLayoutInflater().inflate(R.layout.new_dialog_layout, null);

        TextView mTextView = (TextView) mDiaView.findViewById(R.id.title_view);
        TextView mDiagName = (TextView) mDiaView.findViewById(R.id.dialog_new_name);
        TextView mDiagPhone = (TextView) mDiaView.findViewById(R.id.dialog_phone_view);
        mDiagPhone1 = (TextView) mDiaView.findViewById(R.id.dialog_phone_view1);

        phoneBTN = (Button) mDiaView.findViewById(R.id.dialog_phone_button);
        phoneBTN1 = (Button)mDiaView.findViewById(R.id.dialog_phone_button1);



        mDiagName.setText(name);
        mDiagPhone.setText(phone);
        mDiagPhone1.setText(phone1);




        // ImageView mCallBtn = (ImageView) mDiaView.findViewById(R.id.imageButton);




        phoneBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + phone + ""));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);

                            return;
                        }

                        startActivity(i);
                    }


                }
            }
        });
        phoneBTN1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + phone1 + ""));

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 10);

                            return;
                        }

                        startActivity(i);
                    }


                }
            }
        });


        mBuilder.setView(mDiaView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }
    public void firebaseSearch(){

        recyclerAdapter = new FirebaseRecyclerAdapter<Users, PastorActivity.UserViewHolder>(
                Users.class,
                R.layout.deatail_contact_view,
                PastorActivity.UserViewHolder.class,
                mUserDatabase
        ) {
            @Override
            protected void populateViewHolder(PastorActivity.UserViewHolder viewHolder, final Users model, int position) {
                final String pos = getRef(position).getKey();
                viewHolder.setDetails(PastorActivity.this,model.getName(),model.getPhone(),model.getImage());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mUserDatabase.child(pos).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("phone1")){
                                    dialogHelper(model.getName(), model.getPhone(),model.getPhone1());
                                }
                                else {

                                    dialogHelper(model.getName(), model.getPhone());

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }
        };

        mRecycleList.setAdapter(recyclerAdapter);

    }

    public void firebaseSearch(String searchText1) {
        String searchText=searchText1.toLowerCase().toString();

        // Toast.makeText(ChungTelActivity.this,"Searching Plezz",Toast.LENGTH_LONG).show();
        // String lowerString = searchText.toLowerCase();
        //String upperString = searchText.toUpperCase();
        Query firebaseSearchQuery = mUserDatabase.orderByChild("search_name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<Users,PastorActivity.UserViewHolder>recyclerAdapter1 = new FirebaseRecyclerAdapter<Users, PastorActivity.UserViewHolder>(
                Users.class,
                R.layout.deatail_contact_view,
                PastorActivity.UserViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(PastorActivity.UserViewHolder viewHolder, final Users model, int position) {
                final String pos = getRef(position).getKey();
                viewHolder.setDetails(PastorActivity.this,model.getName(),model.getPhone(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mUserDatabase.child(pos).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("phone1")){
                                    dialogHelper(model.getName(), model.getPhone(),model.getPhone1());
                                }
                                else {

                                    dialogHelper(model.getName(), model.getPhone());

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                });
            }
        };

        mRecycleList.setAdapter(recyclerAdapter1);

    }

    public static  class  UserViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public UserViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setDetails(Context context, String userName, String userPhone, final String userImage) {

            TextView user_name = (TextView) mView.findViewById(R.id.nameView);
            TextView user_phone = (TextView) mView.findViewById(R.id.phoneView);
            final ImageView user_image = (ImageView) mView.findViewById(R.id.imageView);

            user_name.setText(userName);
            user_phone.setText(userPhone);
            // Picasso.get().load(userImage).placeholder(R.drawable.contact).into(user_image);
            //Glide.with(context).load(userImage).into(user_image);

            Picasso.get().load(userImage).placeholder(R.drawable.contact).networkPolicy(NetworkPolicy.OFFLINE).into(user_image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Picasso.get().load(userImage).placeholder(R.drawable.contact).into(user_image);
                }
            });

        }
    }



}
