package com.example.rishikeshwar.finalapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
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


public class Menu1 extends Fragment
{


    public class DataNote
    {
        String hour;
        String subject;
        String faculty;

        public DataNote(String hour, String subject, String faculty)
        {
            this.hour = hour;
            this.subject = subject;
            this.faculty = faculty;
        }

        public String getHour()
        {
            return hour;
        }

        public String getSubject()
        {
            return subject;
        }

        public String getFaculty()
        {
            return faculty;
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

        View view = inflater.inflate(R.layout.fragment_menu_1, container, false);

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
            database.getReference().child("TimeTableFaculty").child(currEmail.split("@")[0]).child(currentDay).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    for (DataSnapshot booksSnapshot : dataSnapshot.getChildren()) {
                        String key = booksSnapshot.getKey();
                        Object value = booksSnapshot.getValue();
                        data.add(
                                new DataNote
                                        (
                                                "Hour: " + String.valueOf(i + 1),
                                                value.toString(),
                                                ""
                                        ));
                        i++;
                    }
                    mListadapter = new ListAdapter(data);
                    mRecyclerView.setAdapter(mListadapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } else {
            database.getReference().child("TimeTable").child("CSEA").child(currentDay).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i = 0;
                    for (DataSnapshot booksSnapshot : dataSnapshot.getChildren()) {
                        String key = booksSnapshot.getKey();
                        Object value = booksSnapshot.getValue();
                        data.add(
                                new DataNote
                                        (
                                                "Hour: " + String.valueOf(i + 1),
                                                value.toString(),
                                                "Faculty: " + value.toString()
                                        ));
                        i++;
                    }

                    database2.getReference().child("FacultyIncharge").addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (int i = 0; i < 8; i++) {
                                Object k = (DataNote) data.get(i);
                                data.set(i, new DataNote(((DataNote) k).hour,
                                        "Subject: " + ((DataNote) k).subject,
                                        "Faculty: " + dataSnapshot.child(((DataNote) k).subject).getValue()));
                            }
                        /*for (DataSnapshot user : dataSnapshot.getChildren()) {
                            for (DataSnapshot booksSnapshot : user.getChildren()) {
                                String key = booksSnapshot.getKey();
                                Object value = booksSnapshot.getValue();

                                Object k = (DataNote) data.get(i);
                                data.set(i++, new DataNote(((DataNote) k).hour,
                                        ((DataNote) k).subject,
                                        "Faculty: " + database3.getReference().child("FacultyIncharge").child(((DataNote) k).subject).getV));
                            }
                        }*/

                            mListadapter = new ListAdapter(data);
                            mRecyclerView.setAdapter(mListadapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });


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
            TextView textViewHour;
            TextView textViewSubject;
            TextView textViewFaculty;
            TextView textViewHourTime;

            public ViewHolder(View itemView)
            {
                super(itemView);
                //itemView.setBackgroundColor(Color.parseColor("#ffff80"));
                this.textViewHour = (TextView) itemView.findViewById(R.id.textViewHour);
                this.textViewSubject = (TextView) itemView.findViewById(R.id.textViewSubject);
                this.textViewFaculty = (TextView) itemView.findViewById(R.id.textViewFaculty);
                this.textViewHourTime = (TextView) itemView.findViewById(R.id.textViewHourTime);
            }
        }

        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position)
        {
            String[] hourTime = {"Time: 8:10 - 9:10", "Time: 9:10 - 10:10", "Time: 10:35 - 11:35", "Time: 11:35 - 12:35", "Time: 1:30 - 2:30", "Time: 2:30 - 3:30", "Time: 3:30 - 4:30", "Time: 4:30 - 5:30"};
            holder.textViewHour.setText(dataList.get(position).getHour());
            holder.textViewSubject.setText(dataList.get(position).getSubject());
            holder.textViewFaculty.setText(dataList.get(position).getFaculty());
            holder.textViewHourTime.setText(hourTime[position]);

            if(dataList.get(position).getSubject().equalsIgnoreCase("Subject: LIBRARY")) {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffff80"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#80ff00"));
            }


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

