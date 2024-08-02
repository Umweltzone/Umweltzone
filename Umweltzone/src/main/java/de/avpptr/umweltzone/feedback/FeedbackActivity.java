/*
 *  Copyright (C) 2024  Tobias Preuss
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.avpptr.umweltzone.feedback;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.base.BaseActivity;
import de.avpptr.umweltzone.utils.ViewHelper;

public class FeedbackActivity extends BaseActivity {

    public FeedbackActivity() {
        super(R.layout.activity_feedback);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewHelper.setupTextViewSimple(this, R.id.buildVersion, "v." + BuildConfig.VERSION_NAME);

        ViewHelper.setupTextViewExtended(this, R.id.app_info_contact_email,
                R.string.appinfo_contact_email,
                R.string.appinfo_contact_email);

        ViewHelper.setupTextViewExtended(this, R.id.app_info_rating,
                R.string.appinfo_rating_url_title,
                R.string.appinfo_rating_url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
