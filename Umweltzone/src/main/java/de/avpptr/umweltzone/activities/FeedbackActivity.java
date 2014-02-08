package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.view.Menu;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.utils.ViewHelper;

public class FeedbackActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ViewHelper.setupTextViewSimple(this, R.id.buildVersion, "v." + getBuildVersionName());

        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonUserVoice,
                R.string.appinfo_uservoice_url, TrackingPoint.UserVoiceClick, null);

        ViewHelper.setupTextViewExtended(this, R.id.appInfoContactEmail,
                R.string.appinfo_contact_email, TrackingPoint.SupportMailClick, null);

        ViewHelper.setupTextViewExtended(this, R.id.appInfoButtonRating,
                R.string.appinfo_rating_url, TrackingPoint.RatingClick, null);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
