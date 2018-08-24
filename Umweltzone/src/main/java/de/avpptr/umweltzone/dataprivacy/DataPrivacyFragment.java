/*
 *  Copyright (C) 2018  Tobias Preuss
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

package de.avpptr.umweltzone.dataprivacy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import de.avpptr.umweltzone.BuildConfig;
import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.Umweltzone;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.base.BaseFragment;
import de.avpptr.umweltzone.prefs.PreferencesHelper;
import de.avpptr.umweltzone.utils.ViewHelper;

public class DataPrivacyFragment extends BaseFragment {

    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DATA_PRIVACY_FRAGMENT_TAG";

    private OnDataPrivacyFinishListener onDataPrivacyFinishListener;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_data_privacy;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView dataPrivacyContentView = (TextView) view.findViewById(R.id.data_privacy_content);
        Spanned dataPrivacyContentSpanned = Html.fromHtml(getString(R.string.data_privacy_content));
        dataPrivacyContentView.setText(dataPrivacyContentSpanned, TextView.BufferType.SPANNABLE);

        ViewHelper.setupTextViewExtended(getActivity(), R.id.data_privacy_statement,
                R.string.data_privacy_statement_title_de,
                R.string.data_privacy_statement_link_de,
                TrackingPoint.DataPrivacyModalItemClick, "data_privacy_statement_de");

        Button confirmButton = (Button) view.findViewById(R.id.data_privacy_confirm);
        confirmButton.setOnClickListener(button -> onButtonClick(button.getContext(), true));
        Button declineButton = (Button) view.findViewById(R.id.data_privacy_decline);
        declineButton.setOnClickListener(button -> onButtonClick(button.getContext(), false));
    }

    private void onButtonClick(Context context, boolean isEnabled) {
        Umweltzone application = (Umweltzone) context.getApplicationContext();
        PreferencesHelper preferencesHelper = application.getPreferencesHelper();
        String suffix = isEnabled ? "enabled" : "disabled";
        mTracking.track(TrackingPoint.DataPrivacyModalItemClick, "google_analytics_" + suffix);
        preferencesHelper.storeGoogleAnalyticsIsEnabled(isEnabled);
        application.initTracking();
        preferencesHelper.storeDataPrivacyModalWasShown(true);
        closeScreen();
    }

    private void closeScreen() {
        if (onDataPrivacyFinishListener != null) {
            onDataPrivacyFinishListener.onFinish();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDataPrivacyFinishListener) {
            onDataPrivacyFinishListener = (OnDataPrivacyFinishListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnDataPrivacyFinishListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDataPrivacyFinishListener = null;
    }

    public interface OnDataPrivacyFinishListener {
        void onFinish();
    }

}
