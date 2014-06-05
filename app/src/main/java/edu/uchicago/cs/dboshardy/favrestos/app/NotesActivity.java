package edu.uchicago.cs.dboshardy.favrestos.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


public class NotesActivity extends ActionBarActivity {
    Resto mResto;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getActionBar().setTitle(getString(R.string.edit_notes_title));
        mResto = (Resto) getIntent().getSerializableExtra(Resto.RESTO);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NoteFragment(mResto))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class NoteFragment extends Fragment {
        TextView mHeaderText;
        EditText mNotes;
        Button mCancelButton;
        Button mSaveButton;
        Resto mFragmentResto;

        public NoteFragment() {
        }

        public NoteFragment(Resto resto) {
            mFragmentResto = resto;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
            mHeaderText = (TextView) rootView.findViewById(R.id.notes_header);
            mHeaderText.setText("Notes for: " + mFragmentResto.getName() + " in " + mFragmentResto.getCity());
            mNotes = (EditText) rootView.findViewById(R.id.edit_note);
            if (!mFragmentResto.getNotes().equals("") && mFragmentResto != null) {
                mNotes.setText(mFragmentResto.getNotes());

            }
            mNotes.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    mFragmentResto.setNotes(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
            mCancelButton = (Button) rootView.findViewById(R.id.cancel_note_button);
            mCancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
            mSaveButton = (Button) rootView.findViewById(R.id.save_note_button);
            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendResult();
                    finish();
                }
            });

            return rootView;
        }

        private void sendResult() {
            Intent i = new Intent();
            i.putExtra(Resto.RESTO, mFragmentResto);
            if (getParent() == null) {
                setResult(RESULT_OK, i);
            } else {
                getParent().setResult(RESULT_OK, i);
            }
        }
    }
}
