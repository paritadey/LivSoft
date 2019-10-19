package in.android.livsoft;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ShowUsersAdapter extends ArrayAdapter<UserList> {

    Context mCtx;
    int listLayoutRes;
    List<UserList> userLists;
    SQLiteDatabase mDatabase;

    public ShowUsersAdapter(Context mCtx, int listLayoutRes, List<UserList> userLists, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, userLists);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.userLists = userLists;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final UserList userList = userLists.get(position);


        TextView userMobile = view.findViewById(R.id.user_mobile);
        TextView userName = view.findViewById(R.id.user_name);
        TextView userGender = view.findViewById(R.id.user_gender);

        userName.setText(userList.getName());
        userMobile.setText(userList.getMobile());
        userGender.setText(userList.getGender());
        return view;
    }


    private void reloadEmployeesFromDatabase() {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM employees", null);
        if (cursorEmployees.moveToFirst()) {
            userLists.clear();
            do {
                userLists.add(new UserList(
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3)
                ));
            } while (cursorEmployees.moveToNext());
        }
        cursorEmployees.close();
        notifyDataSetChanged();
    }

}
