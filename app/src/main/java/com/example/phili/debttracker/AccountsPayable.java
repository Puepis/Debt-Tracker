package com.example.phili.debttracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * This is a Fragment subclass that represents the accounts payable tab. It is linked to the
 * XML file named fragment_accounts_payable.xml. It contains a RecyclerView that displays
 * all of the accounts in an organized list.
 *
 * @see Fragment
 * @see RecyclerView
 * @see View
 */
public class AccountsPayable extends Fragment {

    // Instance variables
    private View v;
    private RecyclerView recyclerView;
    private ArrayList<Account> payableAccounts = new ArrayList<>();

    /**
     * This method creates the view for the class and returns it.
     *
     * @param inflater (LayoutInflater)
     * @param container (ViewGroup)
     * @param savedInstanceState (Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Toast.makeText(getActivity(), payableAccounts.toString(), Toast.LENGTH_LONG).show();
        // Inflate view from respective XML file
        v = inflater.inflate(R.layout.fragment_accounts_payable, container, false);

        // Setup RecyclerView and RecyclerViewAdapter
        recyclerView = v.findViewById(R.id.payableRecyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),
                payableAccounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return v;
    }

    /**
     * This method is called when the fragment is launched.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Adds an account to the list. It returns no values.
     *
     * @param newAccount (Account)
     */
    public void addAccount(Account newAccount) {
        payableAccounts.add(newAccount);
    }

    /**
     * Mutator method for accounts list. Returns no values.
     *
     * @param accounts (ArrayList<Account>)
     */
    public void setPayableAccounts(ArrayList<Account> accounts) {
        payableAccounts = accounts;
    }

    /**
     * Accessor method for accounts list.
     *
     * @return ArrayList<Account> - All of the accounts.
     */
    public ArrayList<Account> getPayableAccounts() {
        return payableAccounts;
    }

    /**
     * Returns number of payable accounts.
     *
     * @return int - Number of accounts
     */
    public int getSize() {
        return payableAccounts.size();
    }
}
