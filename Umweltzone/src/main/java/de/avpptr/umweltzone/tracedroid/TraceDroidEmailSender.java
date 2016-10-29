/*
 *  Copyright (C) 2016  ligi, Tobias Preuss
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

package de.avpptr.umweltzone.tracedroid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import org.ligi.tracedroid.TraceDroid;
import org.ligi.tracedroid.collecting.TraceDroidMetaInfo;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.utils.IntentHelper;
import de.avpptr.umweltzone.utils.SnackBarHelper;

// This class supports translation and configuration via XML files.
// The original TraceDroidEmailSender class is available here:
// https://github.com/ligi/tracedroid
public abstract class TraceDroidEmailSender {

    public static boolean sendStackTraces(@NonNull final Activity context) {
        if (TraceDroid.getStackTraceFiles().length < 1) {
            return false;
        }

        final String emailAddress = context.getString(
                R.string.config_trace_droid_email_address);
        final String dialogTitle = context.getString(
                R.string.trace_droid_dialog_title);
        final String dialogMessage = context.getString(
                R.string.trace_droid_dialog_message);
        final String buttonTitleSend = context.getString(
                R.string.trace_droid_button_title_send);
        final String buttonTitleLater = context.getString(
                R.string.trace_droid_button_title_later);
        final String buttonTitleNo = context.getString(
                R.string.trace_droid_button_title_no);
        final String sendMail = context.getString(
                R.string.trace_droid_app_chooser_title);
        final int maximumStackTracesCount = context.getResources().getInteger(
                R.integer.config_trace_droid_maximum_stack_traces_count);

        new AlertDialog.Builder(context)
                .setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setPositiveButton(buttonTitleSend, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final Intent emailIntent = getEmailIntent(
                                context, emailAddress, maximumStackTracesCount);
                        if (emailIntent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(Intent.createChooser(emailIntent, sendMail));
                            TraceDroid.deleteStacktraceFiles();
                        } else {
                            SnackBarHelper.showError(context, R.id.map,
                                    R.string.trace_droid_no_email_app);
                        }
                    }
                })
                .setNegativeButton(buttonTitleNo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        TraceDroid.deleteStacktraceFiles();
                    }
                })
                .setNeutralButton(buttonTitleLater, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Nothing to do here
                    }
                })
                .show();
        return true;
    }

    @NonNull
    private static Intent getEmailIntent(
            @NonNull Activity context,
            @NonNull final String recipient,
            int maximumStackTracesCount) {
        String[] recipients = {recipient};
        String subject = "[TraceDroid Report] " + TraceDroidMetaInfo.getAppPackageName();
        String message = TraceDroid.getStackTraceText(maximumStackTracesCount);
        return IntentHelper.getSendEmailIntent(context, recipients, null, subject, message);
    }

}
