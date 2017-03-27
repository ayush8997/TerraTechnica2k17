package delhi.android.nit.com.terratechnica.FAQSection;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import delhi.android.nit.com.terratechnica.R;

/**
 * Created by Directioner on 2/11/2017.
 */

public class FAQAdapter extends ArrayAdapter<FAQ> {

    private Context context;
    private List<FAQ> faqData;

    private TextView ques;
    private TextView ans;

    public FAQAdapter(Context context, int resource, List<FAQ> objects) {
        super(context, resource, objects);

        this.context = context;
        faqData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        FAQ data = faqData.get(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.faq_cardview, parent, false);
        }

        Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),  "fonts/JosefinSans-Regular.ttf");


        ques = (TextView) convertView.findViewById(R.id.ques);
        ques.setTypeface(custom_font);

        ans = (TextView) convertView.findViewById(R.id.ans);
        ans.setTypeface(custom_font);

        String question = data.getQues();
        String answer = data.getAns();

        ques.setText(question);
        ans.setText(answer);

        return convertView;
    }
}