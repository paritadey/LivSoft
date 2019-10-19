package in.android.livsoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button login;
    private String TAG = Login.class.getSimpleName();
    ConstraintLayout layoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        final Animation animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        layoutLogin = findViewById(R.id.layout_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.log_in_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validEmail = checkEmail(email.getText().toString());
                boolean validPassword = checkPassword(password.getText().toString());
                Log.d(TAG, "Email and Password check value:" + validEmail + "\t" + validPassword);
                if (validEmail != true || validPassword != true) {
                    if (validEmail != true && validPassword != true) {
                        email.startAnimation(animShake);
                        password.startAnimation(animShake);
                        alertDialog("LivSoft", "please enter Email and Password");
                    } else if (validEmail != true) {
                        email.startAnimation(animShake);
                        alertDialog("LivSoft", "It is not a valid Email Id");
                    } else if (validPassword != true) {
                        password.startAnimation(animShake);
                        alertDialog("LivSoft", "Password should contain atleast 1 caps, 1 lower, 1 number and 1 special character");
                    }
                } else {
                    if (email.getText().toString().trim().equalsIgnoreCase("paritasampa95@gmail.com") &&
                            password.getText().toString().trim().equalsIgnoreCase("Abcdef123#")) {
                        startActivity(new Intent(Login.this, AddUsers.class));
                        finish();
                    } else {
                        alertDialog("LivSoft", "Please check your credentials");
                    }
                }
            }
        });
    }

    private boolean checkEmail(String email) {
        Log.d(TAG, "Email id:" + email);
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkPassword(String password) {
        Log.d(TAG, "Password:" + password + "\t" + password.length());
        String expression = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private void alertDialog(String title, String message) {
        final CustomAlertDialog dialog = new CustomAlertDialog(this,
                R.style.WideDialog, title, message);
        dialog.show();
        dialog.onPositiveButton(view -> {
            dialog.dismiss();
        });
    }
}
