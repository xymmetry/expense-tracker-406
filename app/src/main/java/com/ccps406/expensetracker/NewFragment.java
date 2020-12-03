package com.ccps406.expensetracker;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewFragment extends Fragment implements View.OnClickListener{      // added implement for OnClickListener

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // parameters
    private String mParam1;
    private String mParam2;

    private static final String TAG = "Onclick addExpense" ;
    private static final String TAG2 = "Onclick addIncome" ;
    private static final String EXPENSE = "Expense";
    private static final String INCOME = "Income";

    EditText mAmount, mDescription, mRepeatDays, mEndDate, mOccurrences, mStartDate;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch mRecurring;
    Button mIncomeBtn, mExpenseBtn;
    RadioButton mOccurrenceBtn, mDateBtn;
    FirebaseAuth mAuth;
    String userID;
    Date tDate = new Date();
    FirebaseFirestore mStore;
    TransactionModel model;
    TransactionRecurringModel rByOccurrence;
    TransactionEndByDateModel rEndByDate;
    TransactionValidation validate = new TransactionValidation();
    DocumentReference documentReference, historyRef ,upcomingRef;
    String amountStr, occurrencesStr, description, startDateStr, endDateStr;
    double amount;
    int days, occurrences;
    Date endDate, startDate, nextDate;
    boolean isRecurring, isEndByOccurrences, isEndbyDate;
    LinearLayout mRadioButtonLayout, mRepeatEveryLayout;
    DocumentReference financeInfo;
    Spinner spinner;


    public NewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewFragment newInstance(String param1, String param2) {
        NewFragment fragment = new NewFragment();
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
        // Inflate the layout for this fragment

        View viewTransaction = inflater.inflate(R.layout.activity_transaction, container, false); // added to fix crashes
        viewTransaction.findViewById(R.id.radioButton2).setOnClickListener(this);
        viewTransaction.findViewById(R.id.radioButton3).setOnClickListener(this);


       // return inflater.inflate(R.layout.activity_transaction, container, false); //changed R.layout.fragment_new to activity_transactions
        return viewTransaction;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        financeInfo = mStore.collection("users").document(userID)
                .collection("Profile").document("Financial");
        mStore = FirebaseFirestore.getInstance();

        mAmount = getView().findViewById(R.id.amount_in);
        mDescription = getView().findViewById(R.id.description_input);
        //mRepeatDays = getView().findViewById(R.id.repeat_days);
        mEndDate = getView().findViewById(R.id.date_end);
        mOccurrences = getView().findViewById(R.id.endBy);
        mStartDate = getView().findViewById(R.id.start_date);
        mRadioButtonLayout = getView().findViewById(R.id.radio_button_layouts);
        mRepeatEveryLayout = getView().findViewById(R.id.repeat_every_layout);

        mRecurring = getView().findViewById(R.id.recurring_switch);
        mOccurrenceBtn = getView().findViewById(R.id.radioButton2);
        mDateBtn = getView().findViewById(R.id.radioButton3);

        mExpenseBtn = getView().findViewById(R.id.add_expense);
        mIncomeBtn = getView().findViewById(R.id.add_Income);

        spinner = getView().findViewById(R.id.timeframe_select);


        // functions

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.time_frame, android.R.layout.simple_spinner_dropdown_item); //changed from this to getActivity()
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){ days = 7; }
                else if (position == 1){ days = 14; }
                else{ days = 30; }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                days = 7;
            }
        });

        mIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)  {
                try {
                    addTo(INCOME);
                } catch (ParseException e) {
                    Log.d(TAG2, "Caught Exception");
                }
            }
        });

        mExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v)  {
                try {
                    addTo(EXPENSE);
                } catch (ParseException e) {
                    Log.d(TAG, "Caught Exception");
                }
            }
        });

        mRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mRecurring.isChecked()){

                    mOccurrenceBtn.setChecked(true);
                    mRadioButtonLayout.setVisibility(View.VISIBLE);
                    mRepeatEveryLayout.setVisibility(View.VISIBLE);
                    isRecurring = true;
                    isEndbyDate =false;
                    isEndByOccurrences =true;
                }
                else {
                    isRecurring = false;
                    mRadioButtonLayout.setVisibility(View.INVISIBLE);
                    mRepeatEveryLayout.setVisibility(View.INVISIBLE);

                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTo(String type) throws ParseException {
        documentReference = mStore.collection("users").document(userID).collection(type).document();
        String docId = documentReference.getId();
        amountStr = mAmount.getText().toString().trim();
        description = mDescription.getText().toString().trim();
        if(TextUtils.isEmpty(amountStr)){ mAmount.setError("Amount Required");return; }
        if(!validate.validDesLen(description)){ mDescription.setError("Character Limit 19"); return; }
        if(!validate.isDouble(amountStr)){ mAmount.setError("Enter Valid Number"); return; }
        if(Double.parseDouble(amountStr) > 10000){mAmount.setError("Maximum Amount 10000.00");return;}
        amount = Double.parseDouble(amountStr);
        amount = Math.round(amount*100)/100.0;
        Calendar today = Calendar.getInstance();
        if(!isRecurring){
            model = new TransactionModel(amount, description, tDate, type);
            if (type.equals(INCOME)) { addToIncome(model); }
            else { addToExpenses(model); }
            addToHistory(model,docId);
        }
        else{
            startDateStr = mStartDate.getText().toString();
            if(TextUtils.isEmpty(startDateStr)){mStartDate.setError("Required Field");return;}
            if(!validate.isValidDate(startDateStr)){mStartDate.setError("MM/DD/YYYY Format required");return;}
            startDate =new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA).parse(startDateStr);
            today.setTime(startDate);
            LocalDate todayDate = LocalDate.now();
            DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String formatted = todayDate.format(formattedDate);
            Date ttDate = new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA).parse(formatted);

            if(startDate.before(ttDate) && !startDate.equals(ttDate)){mStartDate.setError("Current or Future Date allowed");return;}
            if(isEndByOccurrences) {
                occurrencesStr = mOccurrences.getText().toString();
                if (TextUtils.isEmpty(occurrencesStr)) { mOccurrences.setError("Required Field");return; }
                if (!validate.isInt(occurrencesStr)) { mOccurrences.setError("Enter Valid Number");return; }
                occurrences = Integer.parseInt(occurrencesStr);
                if(startDate.after(ttDate)){ nextDate = startDate; }
                else { nextDate = calculateNextDate(startDate, days); }
                rByOccurrence = new TransactionRecurringModel(amount, description, days, occurrences, startDate, type, nextDate);
                if (type.equals(INCOME)) { addToIncome(rByOccurrence); }
                else { addToExpenses(rByOccurrence); }
                if (startDate.after(ttDate)) { addToUpcoming(rByOccurrence,docId); }
                else{ addToHistory(rByOccurrence,docId); }
            }
            else{
                endDateStr = mEndDate.getText().toString();
                if(TextUtils.isEmpty(endDateStr)){mEndDate.setError("Empty Field"); return; }
                if(!validate.isValidDate(endDateStr)){mEndDate.setError("MM/DD/YYYY Format Required"); return;}
                endDate =new SimpleDateFormat("MM/dd/yyyy", Locale.CANADA).parse(endDateStr);
                if(endDate.before(tDate) || startDate.equals(endDate) || endDate.before(startDate)){mEndDate.setError("End Date before Start date");return;}
                if(startDate.after(ttDate)){ nextDate = startDate; }
                else { nextDate = calculateNextDate(startDate, days); }
                if(nextDate.before(endDate)){nextDate = endDate; }
                rEndByDate = new TransactionEndByDateModel(amount, description, days, endDate, startDate, type, nextDate);
                if (type.equals(INCOME)) { addToIncome(rEndByDate); }
                else { addToExpenses(rEndByDate); }
                if (startDate.after(ttDate)) { addToUpcoming(rEndByDate,docId); }
                if(startDate.equals(ttDate)){
                    addToUpcoming(rEndByDate,docId);
                    addToHistory(rEndByDate,docId);
                }
            }
        }
        updateTotals(amount, type);
    }

    public void updateTotals(double amount, String type){
        financeInfo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot =  task.getResult();
                    double currentAmount=0;
                    if(type.equals(EXPENSE)){
                        assert documentSnapshot != null;
                        currentAmount = documentSnapshot.getDouble("eTotal");
                    }
                    else{
                        assert documentSnapshot != null;
                        currentAmount = documentSnapshot.getDouble("iTotal");
                    }
                    double newAmount = currentAmount+amount;
                    updateDocument(newAmount, type);
                }
            }
        });
    }

    public void updateDocument(double newAmount, String type){
        if (type.equals(EXPENSE)){
            financeInfo.update("eTotal", newAmount).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG2, "Updated Amount eTotal");
                }
            });
        }else{
            financeInfo.update("iTotal", newAmount).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG2, "Updated Amount iTotal");
                }
            });
        }
    }

    public void addToHistory(TransactionModel regModel, String docId){
        historyRef = mStore.collection("users").document(userID).collection("History").document(docId);
        historyRef.set(regModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to History (Non-Recurring)");
            }
        });
    }
    public void addToExpenses(TransactionModel regModel){
        documentReference.set(regModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Added to Expense (Recurring EndDate)");
            }
        });
    }
    public void addToIncome(TransactionModel regModel){
        documentReference.set(regModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to Income (Non-Recurring)");
            }
        });
    }

    public void addToHistory(TransactionRecurringModel byOccurrence, String docId){
        historyRef = mStore.collection("users").document(userID).collection("History").document(docId);
        historyRef.set(byOccurrence).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to History (Non-Recurring)");
            }
        });
    }
    public void addToExpenses(TransactionRecurringModel byOccurrence){
        documentReference.set(byOccurrence).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Added to Expense (Recurring EndDate)");
            }
        });
    }
    public void addToIncome(TransactionRecurringModel byOccurrence){
        documentReference.set(byOccurrence).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to Income (Non-Recurring)");
            }
        });
    }
    public void addToUpcoming(TransactionRecurringModel byOccurrence, String docId){
        upcomingRef = mStore.collection("users").document(userID).collection("Upcoming").document(docId);
        upcomingRef.set(byOccurrence).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to Upcoming (Recurring, Occurrence)");
            }
        });
    }

    public void addToHistory(TransactionEndByDateModel byEndDate,String docId){
        historyRef = mStore.collection("users").document(userID).collection("History").document(docId);
        historyRef.set(byEndDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to History (Non-Recurring)");
            }
        });

    }
    public void addToExpenses(TransactionEndByDateModel byEndDate){
        documentReference.set(byEndDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "Added to Expense (Recurring EndDate)");
            }
        });
    }
    public void addToIncome(TransactionEndByDateModel byEndDate){
        documentReference.set(byEndDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to Income (Non-Recurring)");
            }
        });
    }

    public void addToUpcoming(TransactionEndByDateModel byEndDate,String docId){
        upcomingRef = mStore.collection("users").document(userID).collection("Upcoming").document(docId);
        upcomingRef.set(byEndDate).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG2, "Added to Upcoming (Recurring, Occurrence)");
            }
        });
    }

    public Date calculateNextDate(Date sDate, int timeFrame) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTime(sDate);
        newDate.add(Calendar.DATE, timeFrame);
        Date finalDate = newDate.getTime();
        return finalDate;
    }

    public String formatDate(Date date)  {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy"); //Changed
        return formatter.format(date);
    }

/*    public void OnRadioButtonClick(View v){
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.radioButton2:
                if (checked)
                    isEndbyDate = false;
                isEndByOccurrences = true;
                mOccurrences.setEnabled(true);
                mOccurrences.setVisibility(View.VISIBLE);
                mEndDate.setVisibility(View.INVISIBLE);
                mEndDate.setText(null);
                break;
            case R.id.radioButton3:
                if (checked)
                    isEndbyDate = true;
                isEndByOccurrences =false;
                mOccurrences.setText(null);
                mOccurrences.setVisibility(View.INVISIBLE);
                mEndDate.setVisibility(View.VISIBLE);
                break;
        }
    }*/
    @Override
    public void onClick(View v){
        boolean checked = ((RadioButton) v).isChecked();

        switch(v.getId()) {
            case R.id.radioButton2:
                if (checked)
                    isEndbyDate = false;
                isEndByOccurrences = true;
                mOccurrences.setEnabled(true);
                mOccurrences.setVisibility(View.VISIBLE);
                mEndDate.setVisibility(View.INVISIBLE);
                mEndDate.setText(null);
                break;
            case R.id.radioButton3:
                if (checked)
                    isEndbyDate = true;
                isEndByOccurrences =false;
                mOccurrences.setText(null);
                mOccurrences.setVisibility(View.INVISIBLE);
                mEndDate.setVisibility(View.VISIBLE);
                break;
            }
    }










}