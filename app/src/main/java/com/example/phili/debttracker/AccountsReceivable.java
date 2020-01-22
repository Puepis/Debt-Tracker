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
 * This is a Fragment subclass that represents the accounts receivable tab. It is linked to the
 * XML file named fragment_accounts_payable.xml. It contains a RecyclerView that displays
 * all of the accounts in an organized list.
 *
 * @see Fragment
 * @see RecyclerView
 * @see View
 */
public class AccountsReceivable extends Fragment {

    // Instance variables
    private View v;
    private RecyclerView recyclerView;
    private ArrayList<Account> receivableAccounts = new ArrayList<>();

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
        // Inflate view from respective XML file
        v = inflater.inflate(R.layout.fragment_accounts_receivable, container, false);

        // Setup RecyclerView and RecyclerViewAdapter
        recyclerView = v.findViewById(R.id.receivableRecyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),
                receivableAccounts);
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
        receivableAccounts.add(newAccount);
    }

    /**
     * Mutator method for accounts list. Returns no values.
     *
     * @param accounts (ArrayList<Account>)
     */
    public void setReceivableAccounts(ArrayList<Account> accounts) {
        receivableAccounts = accounts;
    }

    /**
     * Accessor method for accounts list.
     *
     * @return ArrayList<Account> - All of the accounts.
     */
    public ArrayList<Account> getReceivableAccounts() {
        return receivableAccounts;
    }

    /**
     * Returns number of receivable accounts.
     *
     * @return int - Number of accounts
     */
    public int getSize() {
        return receivableAccounts.size();
    }
}
