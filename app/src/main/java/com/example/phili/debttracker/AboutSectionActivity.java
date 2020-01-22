package com.example.phili.debttracker;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This activity displays information about the application and how to use it. It extends
 * AppCompatActivity.
 *
 * @see AppCompatActivity
 */
public class AboutSectionActivity extends AppCompatActivity {

    private TextView aboutSectionTitleTextView;
    private TextView descriptionSubtitleTextView;
    private TextView descriptionTextView;
    private TextView usageSubtitleTextView;
    private TextView usageTextView;
    private TextView creatorTextView;
    /**
     * This method is called when the activity is launched.
     *
     * @param savedInstanceState (Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_section);

        aboutSectionTitleTextView = findViewById(R.id.aboutSectionTitleTextView);
        descriptionSubtitleTextView = findViewById(R.id.descriptionSubtitleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        usageSubtitleTextView = findViewById(R.id.usageSubtitleTextView);
        usageTextView = findViewById(R.id.usageTextView);
        creatorTextView = findViewById(R.id.creatorTextView);

        // Title colour
        aboutSectionTitleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.titleColor));
        descriptionSubtitleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.titleColor));
        usageSubtitleTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.titleColor));
        creatorTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.titleColor));

        // Text colour
        descriptionTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor));
        usageTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.textColor));
    }
}
