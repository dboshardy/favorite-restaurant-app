package edu.uchicago.cs.dboshardy.favrestos.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RestoFragment extends DialogFragment {

    private ArrayList<Resto> mRestos;
    private AbsListView mRestoList;
    private RestoListAdapter<Resto> myAdapter;

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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mRestos = (ArrayList<Resto>) getArguments().getSerializable(FavResto.RESTO_LIST);
        View v = getActivity().getLayoutInflater().inflate(R.layout.resto_list_fragment, null);
        mRestoList = (AbsListView) v.findViewById(R.id.resto_list);
        myAdapter = new RestoListAdapter<Resto>(mRestos);
        mRestoList.setAdapter(myAdapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a Restaurant").setView(v);

        return builder.create();
    }


    private class RestoListAdapter<Resto> extends ArrayAdapter<Resto> {
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
           // textView.setText((CharSequence) resto.getDisplayName());
            textView.setOnClickListener(new View.OnClickListener() {
                //TODO: Define what happens on click
                @Override
                public void onClick(View v) {
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
