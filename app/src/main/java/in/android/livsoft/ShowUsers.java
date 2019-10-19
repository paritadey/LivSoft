package in.android.livsoft;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowUsers extends AppCompatActivity {
    List<UserList> userLists;
    SQLiteDatabase mDatabase;
    ListView listViewUsers;
    ShowUsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_users);
        initView();
    }

    private void initView() {
        listViewUsers = (ListView) findViewById(R.id.listViewUser);
        userLists = new ArrayList<>();
        mDatabase = openOrCreateDatabase(AddUsers.DATABASE_NAME, MODE_PRIVATE, null);
        showUsersFromDatabase();
    }

    private void showUsersFromDatabase() {
        Cursor cursorUser = mDatabase.rawQuery("SELECT * FROM users", null);
        if (cursorUser.moveToFirst()) {
            do {
                userLists.add(new UserList(
                        cursorUser.getString(1),
                        cursorUser.getString(2),
                        cursorUser.getString(3)
                ));
            } while (cursorUser.moveToNext());
        }
        cursorUser.close();
        adapter = new ShowUsersAdapter(this, R.layout.user_list, userLists, mDatabase);
        listViewUsers.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
