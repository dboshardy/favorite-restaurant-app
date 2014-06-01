package edu.uchicago.cs.dboshardy.favrestos.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uchicago.cs.dboshardy.favRestos.app.R;

import edu.uchicago.cs.dboshardy.favrestos.app.dummy.DummyContent;

public class RestoFragment extends DialogFragment {

    // TODO: Rename and change types of parameters
    public static RestoFragment newInstance(ArrayList<Resto> restos) {
        RestoFragment fragment = new RestoFragment();
        Bundle args = new Bundle();
        args.putSerializable(FavResto.RESTO_LIST, restos);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) { }

        // TODO: Change Adapter to display your content
        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));
    }


    private class LanguageListAdapter<Resto> extends ArrayAdapter<Resto>{
        private final ArrayList<Resto> mRestos;

        public LanguageListAdapter(ArrayList<Resto> restos){
            super(getActivity(),R.layout.resto_list_item, (java.util.List<Resto>) restos);
            mRestos = restos;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.resto_list_item, null);
            }

            final Resto resto = getItem(position);

            final TextView textView = (TextView) convertView.findViewById(R.id.language_item);
            textView.setText((CharSequence) language);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mLanguageType.equals(HOME_LANGUAGE)) {
                        mCurrencyConverter.setHomeCurrency((java.lang.String) language);
                    }
                    else if(mLanguageType.equals(FOREIGN_LANGUAGE)){
                        mCurrencyConverter.setForeignCurrency((java.lang.String) language);
                    }
                    sendResult();
                    dismiss();
                }
            });


            return convertView;
        }
        public void sendResult() {
            Intent i = new Intent();
            getTargetFragment().onActivityResult(getTargetRequestCode(), 0, i);
        }

}
