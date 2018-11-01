package com.example.rishikeshwar.finalapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.security.auth.Subject;


public class Menu2 extends Fragment
{


    public class DataNote
    {
        String subject1;
        String subject2;
        String subject3;
        String subject4;
        String subject5;
        String subject6;
        String subject7;
        String subject8;

        public DataNote(String subject1, String subject2, String subject3, String subject4, String subject5, String subject6, String subject7, String subject8)
        {
            this.subject1 = subject1;
            this.subject2 = subject2;
            this.subject3 = subject3;
            this.subject4 = subject4;
            this.subject5 = subject5;
            this.subject6 = subject6;
            this.subject7 = subject7;
            this.subject8 = subject8;
        }

        public String getSubject1()
        {
            return subject1;
        }
        public String getSubject2()
        {
            return subject2;
        }
        public String getSubject3()
        {
            return subject3;
        }
        public String getSubject4()
        {
            return subject4;
        }
        public String getSubject5()
        {
            return subject5;
        }
        public String getSubject6()
        {
            return subject6;
        }
        public String getSubject7()
        {
            return subject7;
        }
        public String getSubject8()
        {
            return subject8;
        }
    }

    private TextView mTextViewEmpty;
    private ProgressBar mProgressBarLoading;
    private ImageView mImageViewEmpty;
    private RecyclerView mRecyclerView;
    private ListAdapter mListadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_menu_2, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mTextViewEmpty = (TextView)view.findViewById(R.id.textViewEmpty);
        mImageViewEmpty = (ImageView)view.findViewById(R.id.imageViewEmpty);
        mProgressBarLoading = (ProgressBar)view.findViewById(R.id.progressBarLoading);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        final ArrayList data = new ArrayList<DataNote>();



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        final FirebaseDatabase database3 = FirebaseDatabase.getInstance();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        Log.d("checking", currentDay);
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String currEmail = currentFirebaseUser.getEmail();

        if(currEmail.equals("rishi1@gmail.com")) {
            database.getReference().child("TimeTableFaculty").child(currEmail.split("@")[0]).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        String[] s1 = new String[8];
                        int i = 0;
                        for (DataSnapshot booksSnapshot : user.getChildren()) {
                            String key = booksSnapshot.getKey();
                            Object value = booksSnapshot.getValue();
                            s1[i] = value.toString();
                            i++;
                        }
                        data.add(
                                new DataNote
                                        (
                                                s1[0],
                                                s1[1],
                                                s1[2],
                                                s1[3],
                                                s1[4],
                                                s1[5],
                                                s1[6],
                                                s1[7]
                                        ));
                    }
                    mListadapter = new ListAdapter(data);
                    mRecyclerView.setAdapter(mListadapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } else {

            database.getReference().child("TimeTable").child("CSEA").addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        String[] s1 = new String[8];
                        int i = 0;
                        for (DataSnapshot booksSnapshot : user.getChildren()) {
                            String key = booksSnapshot.getKey();
                            Object value = booksSnapshot.getValue();
                            s1[i] = value.toString();
                            i++;
                        }
                        data.add(
                                new DataNote
                                        (
                                                s1[0],
                                                s1[1],
                                                s1[2],
                                                s1[3],
                                                s1[4],
                                                s1[5],
                                                s1[6],
                                                s1[7]
                                        ));
                    }
                    mListadapter = new ListAdapter(data);
                    mRecyclerView.setAdapter(mListadapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        }


        return view;
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
    {
        private ArrayList<DataNote> dataList;

        public ListAdapter(ArrayList<DataNote> data)
        {
            this.dataList = data;
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            TextView textViewSubject;
            TextView textViewSubject2;
            TextView textViewSubject3;
            TextView textViewSubject4;
            TextView textViewSubject5;
            TextView textViewSubject6;
            TextView textViewSubject7;
            TextView textViewSubject8;

            public ViewHolder(View itemView)
            {
                super(itemView);
                //itemView.setBackgroundColor(Color.parseColor("#ffff80"));
                this.textViewSubject = (TextView) itemView.findViewById(R.id.textViewSubject);
                this.textViewSubject2 = (TextView) itemView.findViewById(R.id.textViewSubject2);
                this.textViewSubject3 = (TextView) itemView.findViewById(R.id.textViewSubject3);
                this.textViewSubject4 = (TextView) itemView.findViewById(R.id.textViewSubject4);
                this.textViewSubject5 = (TextView) itemView.findViewById(R.id.textViewSubject5);
                this.textViewSubject6 = (TextView) itemView.findViewById(R.id.textViewSubject6);
                this.textViewSubject7 = (TextView) itemView.findViewById(R.id.textViewSubject7);
                this.textViewSubject8 = (TextView) itemView.findViewById(R.id.textViewSubject8);
            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item2, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            String[] hourTime = {"Time: 8:10 - 9:10", "Time: 9:10 - 10:10", "Time: 10:35 - 11:35", "Time: 11:35 - 12:35", "Time: 1:30 - 2:30", "Time: 2:30 - 3:30", "Time: 3:30 - 4:30", "Time: 4:30 - 5:30"};

            holder.textViewSubject.setText(dataList.get(position).getSubject1());
            holder.textViewSubject2.setText(dataList.get(position).getSubject2());
            holder.textViewSubject3.setText(dataList.get(position).getSubject3());
            holder.textViewSubject4.setText(dataList.get(position).getSubject4());
            holder.textViewSubject5.setText(dataList.get(position).getSubject5());
            holder.textViewSubject6.setText(dataList.get(position).getSubject6());
            holder.textViewSubject7.setText(dataList.get(position).getSubject7());
            holder.textViewSubject8.setText(dataList.get(position).getSubject8());

            ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams)
                    holder.itemView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;

            holder.itemView.setBackgroundColor(Color.parseColor("#80ff00"));
            /*if(dataList.get(position).getSubject().equalsIgnoreCase("Subject: LIBRARY")) {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffff80"));
            } else {
            }*/


            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    Toast.makeText(getActivity(), "Item " + position + " is clicked.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return dataList.size();
        }
    }
}

