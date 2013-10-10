package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;

import java.util.List;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.adapters.FaqsAdapter;
import de.avpptr.umweltzone.models.Faq;
import de.avpptr.umweltzone.utils.ContentProvider;

public class FaqActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ExpandableListView faqsList = (ExpandableListView) findViewById(R.id.faq_list);
        List<Faq> faqs = ContentProvider.getFaqs(this);
        faqsList.setAdapter(new FaqsAdapter(this, faqs));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.faqs, menu);
        return true;
    }

}
