package tz.co.aim.demo.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tz.co.aim.demo.R;
import tz.co.aim.demo.model.User;
import tz.co.aim.demo.sql.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextUsername;
    EditText editTextPassword;


    TextInputLayout textInputLayoutEmail;
    TextInputLayout textInputLayoutUsername;
    TextInputLayout textInputLayoutPassword;

    Button buttonRegister;
    DatabaseHelper databaseHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);


        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);


        buttonRegister = findViewById(R.id.buttonRegister);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    String UserName = editTextUsername.getText().toString();
                    String Password = editTextPassword.getText().toString();
                    String Email = editTextEmail.getText().toString();

                    if (!databaseHelper.isEmailExists(Email)) {


                        databaseHelper.addUser(new User(null, UserName, Email, Password));
                        Snackbar.make(buttonRegister, "User Created Successfully! Please login", Snackbar.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, Snackbar.LENGTH_LONG);

                    } else {
                        Snackbar.make(buttonRegister, "User Already Exists With The same Email", Snackbar.LENGTH_LONG).show();
                    }


                }
            }
        });

    }
    public boolean validate(){
        boolean valid = false;
        String UserName = editTextUsername.getText().toString();
        String Email = editTextEmail.getText().toString();
        String Password = editTextPassword.getText().toString();


//        Validating Username
        if (UserName.isEmpty()){
            valid = false;
            editTextUsername.setError("Please enter a valid Username");
        }else {
            editTextUsername.setError(null);
        }

//        Validating Email
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            valid = false;
            editTextEmail.setError("Please enter a valid email");
        }else {
            valid = true;
            editTextEmail.setError(null);
        }


//        Validating Password
        if (Password.isEmpty()){
            valid = false;
            editTextPassword.setError("Invalid Password");
        }else {
            if (Password.length()==4){
                valid = true;
                editTextPassword.setError(null);
            }else {
                valid = false;
                editTextPassword.setError("Your password is too long or too short");
            }

        }

        return valid;



    }




}
