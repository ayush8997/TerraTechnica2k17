package delhi.android.nit.com.terratechnica;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Manojit Paul on 3/17/2017.
 */

public class Dialog extends DialogFragment {

//    Context context;
//
//    public Dialog(Context context) {
//        this.context =context;
//    }

    callback callback;
    EditText college, mobile;
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return LayoutInflater.from(getActivity()).inflate(R.layout.dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCancelable(false);
        college = (EditText) view.findViewById(R.id.dCollege);
        mobile = (EditText) view.findViewById(R.id.dMobile);
        button = (Button) view.findViewById(R.id.dButton);
        callback = (Dialog.callback) getActivity();
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (college.getText().toString().length() > 0 && mobile.getText().toString().length() == 10) {
                            getDialog().dismiss();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("college", college.getText().toString());
                            editor.putString("mobile", mobile.getText().toString());
                            editor.commit();
                            if (!getDialog().isShowing())
                                callback.register();
                        } else {
                            Toast.makeText(getActivity(), "Provide with Genuine Data", Toast.LENGTH_SHORT).show();
                            college.setText("");
                            mobile.setText("");
                        }
                    }
                }
        );
    }


    public interface callback {
        void register();
    }
}
