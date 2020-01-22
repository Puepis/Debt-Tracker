package com.example.phili.debttracker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This class represents the main activity. It is launched when the app is launched and extends
 * AppCompatActivity. It displays all of the accounts that the user has stored using Fragments,
 * ViewPager, and TabLayout. Through the main activity, the user can create a new account and
 * navigate to the "About" section, where information about the app is displayed.
 *
 * The onResume() and onPause() methods are not overwritten (intentionally) because the necessary
 * data is already stored on the device using SharedPreferences.
 *
 * @see AppCompatActivity
 * @see AccountsPayable
 * @see AccountsReceivable
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    // Hosts section contents
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private SharedPreferences sharedPreferences;

    // Fragments
    AccountsPayable accountsPayable = new AccountsPayable();
    AccountsReceivable accountsReceivable = new AccountsReceivable();

    // Constants
    private final int PAYABLE_ACCOUNT = 0;
    private final int RECEIVABLE_ACCOUNT = 1;
    private final int CREATE_NEW_ACCOUNT = 1;
    private final String PREF_ACCOUNTS_PAYABLE = "Accounts Payable List";
    private final String PREF_ACCOUNTS_RECEIVABLE = "Accounts Receivable List";


    /**
     * This method is called when the Activity is launched. It instantiates the necessary
     * widgets and adapters.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);

        // Create the adapter that will return each fragment
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add fragments
        adapter.addFragment(accountsPayable, "Accounts Payable" );
        adapter.addFragment(accountsReceivable, "Accounts Receivable");

        // Set up the ViewPager with the ViewPagerAdapter.
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        // Floating action button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new FloatingClickListener());

        // Load stored data
        loadData();
    }

    /**
     * This method inflates the menu and adds the menu items to the action bar.
     *
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * This method is called when an option is selected in the options menu from the action bar.
     *
     * @param item
     * @return boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Menu options
        switch (id) {
            case R.id.action_about:
                // Display app information
                startActivity(new Intent(MainActivity.this, AboutSectionActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method is called when NewAccountActivity is finished. The data is retrieved and a
     * new account is created and saved.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CREATE_NEW_ACCOUNT) {
            if (resultCode == RESULT_OK) {

                // Create new account
                String accountName = data.getStringExtra("Account Name");
                double amount = data.getDoubleExtra("Amount", 0.00);
                int accountType = data.getIntExtra("Account Type", 0);
                Account newAccount = new Account(accountName, amount,
                        accountType, R.drawable.ic_person_outline);

                modifyAccounts(newAccount);

                // Save data
                saveData(accountsPayable.getPayableAccounts(), accountsReceivable.getReceivableAccounts());
            }
        }
    }

    /**
     * This method takes the newly created account and adds it to the respective account list. It
     * returns no values.
     *
     * @param newAccount
     */
    private void modifyAccounts(Account newAccount) {

        ArrayList<Account> payableAccounts = accountsPayable.getPayableAccounts();
        ArrayList<Account> receivableAccounts = accountsReceivable.getReceivableAccounts();

        // Add new account to the respective list
        switch(newAccount.getAccountType()) {
            case PAYABLE_ACCOUNT:
                if (!isDuplicateFound(payableAccounts, receivableAccounts, newAccount)) {
                    accountsPayable.addAccount(newAccount);
                }

                break;
            case RECEIVABLE_ACCOUNT:
                if (!isDuplicateFound(receivableAccounts, payableAccounts, newAccount)) {
                    accountsReceivable.addAccount(newAccount);
                }
                break;
        }
    }

    /**
     * This method is used by the modifyAccounts() method to determine if the newly created account
     * contains the same name as another account within the application that already exists. It
     * combines the accounts appropriately.
     *
     * @param primaryAccounts
     * @param otherAccounts
     * @param newAccount
     * @return boolean - Indicating whether a duplicate was found
     */
    private boolean isDuplicateFound(ArrayList<Account> primaryAccounts, ArrayList<Account> otherAccounts, Account newAccount) {
        for (Account account: primaryAccounts) {
            // If duplicate found within same type accounts
            if (account.getName().toLowerCase().equals(newAccount.getName().toLowerCase())) {
                account.addAmount(newAccount.getAmount());
                return true;
            }
        }

        // Check other set of accounts
        for (Account account: otherAccounts) {

            // Matching accounts from other fragment
            if (account.getName().toLowerCase().equals(newAccount.getName().toLowerCase())) {

                // Calculate difference between two diff account types
                double difference = account.getAmount() - newAccount.getAmount();

                // New account won't be created
                if (difference > 0) {
                    account.addAmount(newAccount.getAmount() * -1);
                    return true;
                }

                // Account in other category is removed
                else if (difference < 0) {
                    newAccount.addAmount(account.getAmount() * -1);
                    otherAccounts.remove(account);
                    return false;
                }

                // Both accounts are removed
                else {
                    otherAccounts.remove(account);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method refreshes the fragments. It accepts no parameters and returns no values.
     */
    private void refresh() {
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.detach(accountsPayable).attach(accountsPayable);
        transaction.detach(accountsReceivable).attach(accountsReceivable).commit();
    }

    /**
     * This method saves all the items in both fragments using SharedPreferences.
     */
    private void saveData(ArrayList<Account> accountsPayableList, ArrayList<Account> accountsReceivableList) {

        sharedPreferences = getSharedPreferences("Shared Preferences",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonPayable = gson.toJson(accountsPayableList);
        String jsonReceivable = gson.toJson(accountsReceivableList);
        editor.putString(PREF_ACCOUNTS_PAYABLE, jsonPayable);
        editor.putString(PREF_ACCOUNTS_RECEIVABLE, jsonReceivable);
        editor.apply();

        // Refresh both fragments
        refresh();
    }

    /**
     * This helper method loads the data stored on the device. It is called when the app is
     * launched. It accepts no parameters and returns no values.
     */
    private void loadData() {

        sharedPreferences = getSharedPreferences("Shared Preferences", MODE_PRIVATE);

        // Use GSON and JSON to customize shared preferences
        Gson gson = new Gson();
        String jsonPayable = sharedPreferences.getString(PREF_ACCOUNTS_PAYABLE, null);
        String jsonReceivable = sharedPreferences.getString(PREF_ACCOUNTS_RECEIVABLE, null);
        Type type = new TypeToken<ArrayList<Account>>(){}.getType();
        ArrayList<Account> payableAccounts = gson.fromJson(jsonPayable, type);
        ArrayList<Account> receivableAccounts = gson.fromJson(jsonReceivable, type);

        // Empty accounts
        if (payableAccounts == null) {
            payableAccounts = new ArrayList<>();
        }
        if (receivableAccounts == null) {
            receivableAccounts = new ArrayList<>();
        }

        // Change the respective fragments
        accountsPayable.setPayableAccounts(payableAccounts);
        accountsReceivable.setReceivableAccounts(receivableAccounts);
    }

    /**
     * This inner class represents the event handler for the FloatingActionButton. When clicked,
     * NewAccountActivity is launched, and data is retrieved.
     *
     * @see android.view.View.OnClickListener
     *
     */
    class FloatingClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            // Begin new activity
            Intent intent = new Intent(MainActivity.this, NewAccountActivity.class);
            startActivityForResult(intent, CREATE_NEW_ACCOUNT);
        }
    }
}
