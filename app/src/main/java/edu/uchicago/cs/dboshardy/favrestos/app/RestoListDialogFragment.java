package edu.uchicago.cs.dboshardy.favrestos.app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uchicago.cs.dboshardy.favRestos.app.R;


public class RestoListDialogFragment extends DialogFragment {

    private AbsListView mLanguageList;
    private LanguageListAdapter<String> myAdapter;
    private ArrayList<Resto> mRestos;

    public static RestoListDialogFragment newInstance(ArrayList<Resto> restos) {
        RestoListDialogFragment fragment = new RestoListDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(FavResto.RESTO_LIST, restos);
        fragment.setArguments(args);
        return fragment;
    }
    public RestoListDialogFragment() {
        // Required empty public constructor
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mRestos = (ArrayList<Resto>) getArguments().getSerializable(FavResto.RESTO_LIST);
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_language_list,null);
        mLanguageList = (AbsListView) v.findViewById(R.id.language_list);
        myAdapter = new LanguageListAdapter<String>(mA,mLanguageType);
        mLanguageList.setAdapter(myAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a Currency").setView(v);

        return builder.create();
    }



    private class LanguageListAdapter<Resto> extends ArrayAdapter<Resto>{
        public LanguageListAdapter(ArrayList<String> languages,String languageType){
            super(getActivity(),android.R.layout.simple_list_item_1,languages);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.resto_list_item, null);
            }

            final String language = getItem(position);

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
}
