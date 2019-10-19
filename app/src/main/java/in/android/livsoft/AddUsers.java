package in.android.livsoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddUsers extends AppCompatActivity implements View.OnClickListener {
    private String TAG = AddUsers.class.getSimpleName();
    private List<UserList> userLists;
    public static final String DATABASE_NAME = "user_db";
    SQLiteDatabase mDatabase;
    String userName, userMobile, userGender;
    ListView listViewUsers;
    EditText firstName, lastName, mobileNumber;
    RadioGroup radioGroup;
    Button submit, showList;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);
        initView();
    }

    private void initView() {
        firstName = findViewById(R.id.c1);
        lastName = findViewById(R.id.c2);
        mobileNumber = findViewById(R.id.c3);
        radioGroup = findViewById(R.id.c4);
        submit = (Button) findViewById(R.id.c45701c6c74e4315b4c3fd9);
        submit.setOnClickListener(this);
        showList = findViewById(R.id.c5);
        showList.setOnClickListener(this);
        listViewUsers = (ListView) findViewById(R.id.listViewUser);
        userLists = new ArrayList<>();
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createUserTable();
    }

    private void createUserTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS users (\n" +
                        "    id INTEGER NOT NULL CONSTRAINT user_pk PRIMARY KEY AUTOINCREMENT,\n" +
                        "    name varchar(200) NOT NULL,\n" +
                        "    mobile varchar(200) NOT NULL,\n" +
                        "    gender varchar(200) NOT NULL\n" +
                        ");"
        );
    }

    private void alertDialog(String title, String message) {
        final CustomAlertDialog dialog = new CustomAlertDialog(this,
                R.style.WideDialog, title, message);
        dialog.show();
        dialog.onPositiveButton(view -> {
            dialog.dismiss();
            clearField();
            startActivity(new Intent(AddUsers.this, ShowUsers.class));
        });
    }

    private void addUser(String name, String mobile, String gender) {
        if (!name.isEmpty() && !mobile.isEmpty() && !gender.isEmpty()) {
            String insertSQL = "INSERT INTO users \n" +
                    "(name, mobile, gender)\n" +
                    "VALUES \n" +
                    "(?, ?, ?);";

            mDatabase.execSQL(insertSQL, new String[]{name, mobile, gender});
            alertDialog("LivSoft", "User is added successfully.");
        } else {
            Log.d(TAG, "Error in Data entry");
            Toast.makeText(getApplicationContext(), "Please Check your data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.c45701c6c74e4315b4c3fd9:
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                userGender = radioButton.getText().toString();
                Log.d(TAG, "User details:" + firstName.getText().toString() + "\t" + lastName.getText().toString() + "\t"
                        + mobileNumber.getText().toString() + "\t" + userGender);
                userName = firstName.getText().toString().trim() + " " + lastName.getText().toString().trim();
                userMobile = mobileNumber.getText().toString().trim();
                addUser(userName, userMobile, userGender);
                break;
            case R.id.c5:
                startActivity(new Intent(AddUsers.this, ShowUsers.class));
                break;
            default:
                break;
        }
    }

    private void clearField() {
        firstName.setText("");
        lastName.setText("");
        mobileNumber.setText("");
        radioGroup.clearCheck();
    }

}
