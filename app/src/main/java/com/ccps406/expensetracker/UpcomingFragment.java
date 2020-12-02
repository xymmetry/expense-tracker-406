package com.ccps406.expensetracker;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpcomingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpcomingFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView mTest, mDescription, mDate;
    ArrayList<Double> amounts = new ArrayList<>();
    ArrayList<String> descriptions = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> types = new ArrayList<>();
    public final String wantedFormat = "MM/dd/yyyy";

    Calendar newDate = Calendar.getInstance();

    FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    CollectionReference upcomingRef = mStore.collection("users").document(user.getUid()).collection("Upcoming");
    DocumentReference docRef;

    private String mParam1;
    private String mParam2;

    //functions below

    public void amountGet() {
        upcomingRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        amounts.add(Math.round(document.getDouble("amount")*100)/100.0);
                        descriptions.add(document.getString("description"));
                        dates.add(convertStr(document.getDate("nextDate")));
                        types.add(document.getString("type"));
                        mTest.setText("");
                        recyclerMethod();
                    }
                }
                else {
                    mTest.setText("No Upcoming Transaction");
                }
            }
        });
    }

    public String convertStr(Date date)  {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat(wantedFormat); //Changed
        return formatter.format(date);
    }


    private void recyclerMethod(){
        RecyclerView rView = getView().findViewById(R.id.upcoming_view);
        RecyclerViewAdpater adapter = new RecyclerViewAdpater(amounts, descriptions, dates, types);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    // end of Functions line


    public UpcomingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View upView = inflater.inflate(R.layout.activity_upcoming, container, false);
        //RecyclerView rView = (RecyclerView) upView.findViewById(R.id.upcoming_view);
        //rView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //RecyclerViewAdpater adapter = new RecyclerViewAdpater(amounts, descriptions, dates, types);
        //rView.setAdapter(adapter);
        //return upView;
        return inflater.inflate(R.layout.activity_upcoming, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTest = getView().findViewById(R.id.empty_title_u);
        amountGet();
    }
}