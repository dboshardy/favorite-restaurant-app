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

import java.io.Serializable;
import java.util.ArrayList;

public class RestoFragment extends DialogFragment {

    private ArrayList<Resto> mRestos;
    private AbsListView mRestoList;
    private RestoListAdapter<Resto> myAdapter;
    private Resto mResto;

    // TODO: Rename and change types of parameters
    public static RestoFragment newInstance(ArrayList<Resto> restos) {
        RestoFragment fragment = new RestoFragment();
        Bundle args = new Bundle();
        args.putSerializable(Resto.RESTO_LIST, restos);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestoFragment() {
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mRestos = (ArrayList<Resto>) getArguments().getSerializable(Resto.RESTO_LIST);
        View v = getActivity().getLayoutInflater().inflate(R.layout.resto_list_fragment, null);
        mRestoList = (AbsListView) v.findViewById(R.id.resto_list);
        myAdapter = new RestoListAdapter<Resto>(mRestos);
        mRestoList.setAdapter(myAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a Restaurant").setView(v);

        return builder.create();
    }


    public class RestoListAdapter<Resto extends Serializable> extends ArrayAdapter<Resto> {
        private final ArrayList<Resto> mRestos;

        public RestoListAdapter(ArrayList<Resto> restos) {
            super(getActivity(), android.R.layout.simple_list_item_1, (java.util.List<Resto>) restos);
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
            final TextView textView = (TextView) convertView.findViewById(R.id.resto_list_item);
            textView.setText(resto.toString());
            textView.setTextColor(getActivity().getApplicationContext().getResources().getColor(R.color.black));
            textView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    sendResult(position);
                    dismiss();
                }
            });


            return convertView;
        }

        public void sendResult(int position) {
            Intent i = new Intent();
            i.putExtra("position", position);
            getTargetFragment().onActivityResult(getTargetRequestCode(), 5, i);

        }

    }
}
