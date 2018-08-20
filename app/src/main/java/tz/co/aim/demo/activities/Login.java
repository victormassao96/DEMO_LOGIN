package tz.co.aim.demo.activities;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tz.co.aim.demo.R;
import tz.co.aim.demo.model.User;
import tz.co.aim.demo.sql.DatabaseHelper;

public class Login extends AppCompatActivity {



    EditText editTextUsername;
    EditText editTextPassword;



    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutPassword;


    Button buttonLogin;


    DatabaseHelper databaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        databaseHelper = new DatabaseHelper(this);


        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        textInputLayoutEmail = (TextInputLayout)findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout)findViewById(R.id.textInputLayoutPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(validate()){
                    String Email = editTextUsername.getText().toString();
                    String Password = editTextPassword.getText().toString();


                    User currentUser = databaseHelper.Authenticate(new User(null, null,Email,Password));


                    if(currentUser != null){
                        Toast.makeText(Login.this,"Logged In",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(Login.this,"Failed to Login",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    public boolean validate(){
        boolean valid = false;

        String Email = editTextUsername.getText().toString();
        String Password = editTextPassword.getText().toString();


        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            valid = false;

            editTextUsername.setError("Please Enter a valid email");
        }else {
            valid = true;
            editTextUsername.setError(null);
        }

        if (Password.isEmpty()){
            valid = false;
            editTextPassword.setError("Please Enter a valid password");
        }else {
            if (Password.length()==4){
                valid = true;
                editTextPassword.setError(null);
            }else {
                valid=false;
                editTextPassword.setError("Password is too long or is too short");
            }
        }

        return  valid;


    }
}
