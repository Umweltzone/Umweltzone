/*
 *  Copyright (C) 2023  Tobias Preuss
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

package de.avpptr.umweltzone.faqs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.core.content.ContextCompat;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.models.Faq;
import de.avpptr.umweltzone.utils.ViewHelper;

class FaqsAdapter extends BaseExpandableListAdapter {

    private final Context mContext;

    private final List<Faq> mFaqs;

    FaqsAdapter(Context context, List<Faq> faqs) {
        mContext = context;
        mFaqs = faqs;
    }

    @Override
    public int getGroupCount() {
        return mFaqs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mFaqs.get(groupPosition).question;
    }

    @Override
    public Object getChild(final int groupPosition, int childPosition) {
        final String faqAnswer = mFaqs.get(groupPosition).answer;
        return new FaqAnswer() {{
            text = faqAnswer;
        }};
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private View getNewView(int resourceId) {
        LayoutInflater inflater = ContextCompat.getSystemService(mContext, LayoutInflater.class);
        return inflater.inflate(resourceId, null);
    }

    @Override
    public View getGroupView(
            int groupPosition,
            boolean isExpanded,
            View convertView,
            ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            convertView = getNewView(R.layout.faq_list_group);
        }

        ViewHelper.setupTextViewSimple(convertView, R.id.faq_question, headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(
            int groupPosition,
            int childPosition,
            boolean isLastChild,
            View convertView,
            ViewGroup parent) {
        final FaqAnswer faqAnswer = (FaqAnswer) getChild(groupPosition, childPosition);

        if (convertView == null) {
            convertView = getNewView(R.layout.faq_list_item);
        }
        final String childText = faqAnswer.text;
        ViewHelper.setupTextViewSimple(convertView, R.id.faq_answer, childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class FaqAnswer {

        String text;

    }

}
