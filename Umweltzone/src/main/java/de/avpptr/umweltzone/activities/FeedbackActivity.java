package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;

public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        TextView buildVersionTextView = (TextView) findViewById(R.id.buildVersion);
        buildVersionTextView.setText("v." + BuildConfig.BUILD_VERSION);

        Button userVoiceButton = (Button) findViewById(R.id.appInfoButtonUserVoice);
        userVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTracking.track(TrackingPoint.UserVoiceClick);
            }
        });

        TextView contactEmailTextView = (TextView) findViewById(R.id.appInfoContactEmail);
        contactEmailTextView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTracking.track(TrackingPoint.SupportMailClick);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
