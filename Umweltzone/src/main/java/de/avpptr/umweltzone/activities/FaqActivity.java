package de.avpptr.umweltzone.activities;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ExpandableListView;

import de.avpptr.umweltzone.R;
import de.avpptr.umweltzone.adapters.ExpandableListAdapter;
import de.avpptr.umweltzone.utils.FaqDataProvider;

public class FaqActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.faq_list);

        FaqDataProvider faqDataProvider = new FaqDataProvider();

        expListView.setAdapter(new ExpandableListAdapter(this,
                faqDataProvider.getFaqQuestions(),
                faqDataProvider.getFaqAnswers()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        return true;
    }

}
