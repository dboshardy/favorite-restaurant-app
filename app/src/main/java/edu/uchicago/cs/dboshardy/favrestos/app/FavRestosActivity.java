package edu.uchicago.cs.dboshardy.favrestos.app;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.apress.gerber.reminders.app.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import edu.uchicago.cs.dboshardy.favrestos.app.db.FavRestosDbAdapter;
import edu.uchicago.cs.dboshardy.favrestos.app.db.FavRestosSimpleCursorAdapter;


public class FavRestosActivity extends ActionBarActivity {

    private ListView mListView;
    private FavRestosDbAdapter mDbAdapter;
    private FavRestosSimpleCursorAdapter mCursorAdapter;
    private ShowcaseView mShowcaseView;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favrestos_layout);

        mListView = (ListView) findViewById(R.id.favrestos_list_view);
        mListView.setDivider(null);

        mShowcaseView = new ShowcaseView.Builder(this)
                //TODO: Add the viewgroups needed to show off what to do.
                .setTarget(new ViewTarget(findViewById(R.id.button)))
                .setOnClickListener(this)
                .build();
        mDbAdapter = new FavRestosDbAdapter(this);
        mDbAdapter.open();

        if (savedInstanceState == null) {
            //Clean all data
            mDbAdapter.deleteAllFavRestos();
            //Add some data
            mDbAdapter.insertSomeFavRestos();
        }

        Cursor cursor = mDbAdapter.fetchAllFavRestos();

        //from columns defined in the db
        String[] from = new String[]{
                FavRestosDbAdapter.KEY_CONTENT
        };

        //to the ids of views in the layout
        int[] to = new int[]{
                R.id.row_text
        };

        mCursorAdapter = new FavRestosSimpleCursorAdapter(
                //context
                FavRestosActivity.this,
                //the layout of the row
                R.layout.favrestos_row,
                //cursor
                cursor,
                //from columns defined in the db
                from,
                //to the ids of views in the layout
                to,
                //flag - not used
                0);


        //the cursorAdapter (controller) is now updating the listView (view) with data from the db (model)
        mListView.setAdapter(mCursorAdapter);

        //when we click an individual item in the listview
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FavRestosActivity.this);
                ListView modeList = new ListView(FavRestosActivity.this);
                String[] stringArray = new String[] { "Edit FavResto", "Delete FavResto" };
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(FavRestosActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
                modeList.setAdapter(modeAdapter);
                builder.setView(modeList);
                final Dialog dialog = builder.create();
                dialog.show();
                modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        //edit reminder
                        if (position == 0){

                            int nId = getIdFromPosition(masterListPosition);
                            FavResto reminder = mDbAdapter.fetchFavRestoById(nId);
                            fireCustomDialog(reminder);

                            //delete reminder
                        } else {

                            mDbAdapter.deleteFavRestoById(getIdFromPosition(masterListPosition));
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllFavRestos());

                        }
                        dialog.dismiss();
                    }
                });


            }
        });

        //contextual action mode set-up
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.cam_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.menu_item_delete_reminder:
                            for (int nC = mCursorAdapter.getCount() - 1; nC >= 0; nC--) {
                                if (mListView.isItemChecked(nC)) {

                                    mDbAdapter.deleteFavRestoById(getIdFromPosition(nC));

                                }
                            }
                            mode.finish();
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllFavRestos());
                            return true;

                    }

                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });

        }
    }



    private int getIdFromPosition(int nPosition){
        Cursor cursor = mDbAdapter.fetchAllFavRestos();
        cursor.move(nPosition);
        return cursor.getInt(FavRestosDbAdapter.KEY_ID_INDEX);
    }


    private void fireCustomDialog(final FavResto reminder){


        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom);


        TextView textView = (TextView) dialog.findViewById(R.id.custom_title);
        final EditText editCustom = (EditText) dialog.findViewById(R.id.custom_edit_reminder);
        Button buttonCustom = (Button) dialog.findViewById(R.id.custom_button_commit);
        final CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.custom_check_box);
        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.custom_root_layout);

        //this is for an edit
        if (reminder != null){
            textView.setText("Edit FavResto");
            checkBox.setChecked(reminder.getImportant() == 1);
            editCustom.setText(reminder.getContent());
            linearLayout.setBackgroundColor(getResources().getColor(R.color.blue));
        }

        buttonCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strCustom = editCustom.getText().toString();
                //this is for edit reminder
                if (reminder != null) {

                    FavResto reminderEdited = new FavResto(reminder.getId(),strCustom, checkBox.isChecked() ? 1 : 0 );
                    mDbAdapter.updateFavResto(reminderEdited);

                    //this is for new reminder
                } else {
                    mDbAdapter.createFavResto( strCustom, checkBox.isChecked());
                }
                mCursorAdapter.changeCursor(mDbAdapter.fetchAllFavRestos());
                dialog.dismiss();
            }
        });

        Button buttonCancel = (Button) dialog.findViewById(R.id.custom_button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        dialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reminders_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_new:
                //create new FavResto
                fireCustomDialog(null);
                return true;

            case R.id.action_exit:
                finish();
                return true;

            default:
                return false;


        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDbAdapter.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDbAdapter.open();
    }
}
