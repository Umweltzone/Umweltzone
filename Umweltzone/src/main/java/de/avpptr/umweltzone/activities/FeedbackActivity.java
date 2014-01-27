package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.utils.StringHelper;

public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        TextView buildVersionTextView = (TextView) findViewById(R.id.buildVersion);
        buildVersionTextView.setText("v." + getBuildVersionName());

        Button userVoiceButton = (Button) findViewById(R.id.appInfoButtonUserVoice);
        userVoiceButton.setText(
                StringHelper.spannedLinkForString(this, R.string.appinfo_uservoice_url),
                TextView.BufferType.SPANNABLE);
        userVoiceButton.setMovementMethod(LinkMovementMethod.getInstance());
        userVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTracking.track(TrackingPoint.UserVoiceClick);
            }
        });

        TextView contactEmailTextView = (TextView) findViewById(R.id.appInfoContactEmail);
        contactEmailTextView.setText(
                StringHelper.spannedLinkForString(this, R.string.appinfo_contact_email),
                TextView.BufferType.SPANNABLE);
        contactEmailTextView.setMovementMethod(LinkMovementMethod.getInstance());
        contactEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTracking.track(TrackingPoint.SupportMailClick);
            }
        });

        Button ratingButton = (Button) findViewById(R.id.appInfoButtonRating);
        ratingButton.setText(
                StringHelper.spannedLinkForString(this, R.string.appinfo_rating_url),
                TextView.BufferType.SPANNABLE);
        ratingButton.setMovementMethod(LinkMovementMethod.getInstance());
        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTracking.track(TrackingPoint.RatingClick);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
