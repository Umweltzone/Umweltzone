/*
 *  Copyright (C) 2019  Tobias Preuss
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

import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.analytics.TrackingPoint;
import de.avpptr.umweltzone.base.BaseActivity;
import de.avpptr.umweltzone.models.Faq;
import de.avpptr.umweltzone.utils.ContentProvider;

public class FaqActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ExpandableListView faqsList = findViewById(R.id.faq_list);
        List<Faq> faqs = ContentProvider.getFaqs(this);
        faqsList.setAdapter(new FaqsAdapter(this, faqs));

        faqsList.setOnGroupClickListener((parent, view, groupPosition, id) -> {
            if (!parent.isGroupExpanded(groupPosition)) {
                FaqsAdapter faqsAdapter = (FaqsAdapter) parent.getExpandableListAdapter();
                if (faqsAdapter != null) {
                    String itemDescription = faqsAdapter.getFaqDescription(groupPosition);
                    mTracking.track(TrackingPoint.FaqItemClick, itemDescription);
                }
            }
            return false;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.faqs, menu);
        return true;
    }

}
