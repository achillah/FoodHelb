package com.helb.foodhelb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterDoB, editTextRegisterMobile,
    editTextRegisterPwd, editTextRegisterconfirmPwd;

    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private DatePickerDialog picker;
    private static final String TAG="RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");

        Toast.makeText(this, "You can register now", Toast.LENGTH_SHORT).show();


        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDoB = findViewById(R.id.editText_register_dob);
        editTextRegisterMobile = findViewById(R.id.editText_register_mobile);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
        editTextRegisterconfirmPwd = findViewById(R.id.editText_register_confirm_password);
        progressBar = findViewById(R.id.progressBar);

        //RadioButton for Gender

        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();


        //Settings up Datepicker on EditText
        editTextRegisterDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date picker Dialog

                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        editTextRegisterDoB.setText(dayOfMonth + "/"+ (month +1)+ "/" + year);
                    }
                }, year, month, day);
                picker.show();

            }
        });

        Button buttonRegister = findViewById(R.id.btnRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                //Obtain the entered data

                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String mobile = editTextRegisterMobile.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textConfirmPwd = editTextRegisterconfirmPwd.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textFullName))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullName.setError("Full name is Required");
                    editTextRegisterFullName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Email is Required");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullName.setError("Valid email is Required");
                    editTextRegisterFullName.requestFocus();
                } else if (TextUtils.isEmpty(textDoB))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your dob", Toast.LENGTH_SHORT).show();
                    editTextRegisterDoB.setError("DoB is Required");
                    editTextRegisterDoB.requestFocus();
                }else if (radioGroupRegisterGender.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                }else if (TextUtils.isEmpty(mobile))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your dob", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile is Required");
                    editTextRegisterMobile.requestFocus();
                }else if (mobile.length() != 10)
                {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your mobile", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile No. should be 10 digits");
                    editTextRegisterMobile.requestFocus();
                }else if (TextUtils.isEmpty(textPwd))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password is Required");
                    editTextRegisterPwd.requestFocus();
                }else if (textPwd.length()<6)
                {
                    Toast.makeText(RegisterActivity.this, "Please should be at least 6 digits", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password to weak");
                    editTextRegisterPwd.requestFocus();
                }else if (TextUtils.isEmpty(textConfirmPwd))
                {
                    Toast.makeText(RegisterActivity.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    editTextRegisterconfirmPwd.setError("Password Confirmation is Required");
                    editTextRegisterconfirmPwd.requestFocus();
                }else if (!textPwd.equals(textConfirmPwd))
                {
                    Toast.makeText(RegisterActivity.this, "Please same password", Toast.LENGTH_SHORT).show();
                    editTextRegisterconfirmPwd.setError("Password Confirmation is Required");
                    editTextRegisterconfirmPwd.requestFocus();

                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterconfirmPwd.clearComposingText();
                }else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textEmail, textDoB, textGender, mobile, textPwd);

                }

            }
        });

    }

    private void registerUser(String textFullName, String textEmail, String textDoB, String textGender, String mobile, String textPwd) {

        FirebaseAuth auth = FirebaseAuth.getInstance();


        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    FirebaseUser firebaseUser = auth.getCurrentUser();

                    //Update Display Name of User
                    UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textFullName).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    //Enter User Data into the firebase
                    ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDoB, textGender, mobile);

                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                //send Verification Email
                                //firebaseUser.sendEmailVerification();

                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                  /*  //Open User Profile after successful registration
                    Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish();*/

                            }else {
                                Toast.makeText(RegisterActivity.this, "User registered failed", Toast.LENGTH_SHORT).show();
                            }

                            progressBar.setVisibility(View.GONE);

                        }
                    });

                } else {
                    try {
                        throw task.getException();
                    } catch(FirebaseAuthWeakPasswordException e){
                        editTextRegisterPwd.setError("Your password is too weak");
                        editTextRegisterPwd.requestFocus();
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        editTextRegisterPwd.setError("Your email is invalid");
                        editTextRegisterPwd.requestFocus();
                    } catch (FirebaseAuthUserCollisionException e){
                        editTextRegisterPwd.setError("User is already register with this email");
                        editTextRegisterPwd.requestFocus();
                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegisterActivity.this, e.getMessage() , Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }

            }
        });


    }
}