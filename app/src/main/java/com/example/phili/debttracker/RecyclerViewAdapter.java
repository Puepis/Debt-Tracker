package com.example.phili.debttracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import java.text.NumberFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * This class represents the adapter that binds account data to the RecyclerView layout.
 *
 * @see android.support.v7.widget.RecyclerView.Adapter
 *
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    // Declare instance variables
    private Context context;
    private ArrayList<Account> data;
    private SharedPreferences sharedPreferences;

    // Constants
    private final String PREF_ACCOUNTS_PAYABLE = "Accounts Payable List";
    private final String PREF_ACCOUNTS_RECEIVABLE = "Accounts Receivable List";
    private final int PAYABLE_ACCOUNT = 0;

    /**
     * This is the parameterized constructor.
     */
    public RecyclerViewAdapter(Context context, ArrayList<Account> data) {
        this.context = context;
        this.data = data;
    }

    /**
     * This method creates the ViewHolder that contains the inflated account view.
     *
     * @see View
     * @see android.support.v7.widget.RecyclerView.ViewHolder
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.account_item, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    /**
     * This method binds the account data to the ViewHolder.
     *
     */
    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        // Locale-specific formatting
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        String label;
        if (data.get(i).getAccountType() == PAYABLE_ACCOUNT) {
            label = "is owed: ";
        }
        else {
            label = "owes you: ";
        }

        myViewHolder.nameTextView.setText(data.get(i).getName());
        myViewHolder.amountTextView.setText(label + currencyFormat.format(data.get(i).getAmount()));
        myViewHolder.personImage.setImageResource(data.get(i).getPhoto());

        Account infoData = data.get(i);
        myViewHolder.deleteImage.setOnClickListener(new OnDeleteClickListener(infoData));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * This method removes an item from the list.
     */
    private void deleteItem(Account infoData) {
        //Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
        int position = data.indexOf(infoData);
        data.remove(position);

        // Notify the adapter
        notifyItemRemoved(position);

        // Updated the stored data
        updateSharedPreferences(infoData, data);
    }

    /**
     * This method updates the data stored on device using SharedPreferences.
     */
    private void updateSharedPreferences(Account infoData, ArrayList<Account> data) {
        sharedPreferences = context.getSharedPreferences("Shared Preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        if (infoData.getAccountType() == PAYABLE_ACCOUNT) {
            editor.putString(PREF_ACCOUNTS_PAYABLE, json);
        }
        else {
            editor.putString(PREF_ACCOUNTS_RECEIVABLE, json);
        }
        editor.apply();
    }

    /**
     * This inner class holds the View for one item in the list.
     *
     * @see RecyclerView.ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        // Views
        private TextView nameTextView;
        private TextView amountTextView;
        private ImageView personImage;
        private ImageView deleteImage;

        // Constructor
        public MyViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.accountName);
            amountTextView = itemView.findViewById(R.id.dollarAmount);
            personImage = itemView.findViewById(R.id.accountImage);
            deleteImage = itemView.findViewById(R.id.deleteIcon);

            nameTextView.setTextColor(ContextCompat.getColor(context, R.color.titleColor));
        }
    }

    /**
     * This inner class serves as the event handler for when the user
     * clicks the delete item in the RecyclerView.
     */
    class OnDeleteClickListener implements View.OnClickListener {

        Account infoData;

        public OnDeleteClickListener(Account infoData) {
            this.infoData = infoData;
        }

        @Override
        public void onClick(View v) {
            deleteItem(infoData);

            // Display remove message
            Snackbar.make(v, R.string.removed_message, Snackbar.LENGTH_SHORT).show();

        }
    }
}
