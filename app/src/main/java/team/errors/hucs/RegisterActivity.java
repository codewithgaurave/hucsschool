package team.errors.hucs;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    MaterialButton reg_btn;
    TextInputLayout emailLayout, passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        reg_btn = findViewById(R.id.reg_btn);

        // Initialize TextInputLayouts
        emailLayout = findViewById(R.id.reg_Email);
        passwordLayout = findViewById(R.id.reg_Password);

        // Apply bold styling
        setupBoldStyling();

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    etEmail.setError("Email required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    etPassword.setError("Password required");
                    return;
                }

                // TODO: Backend integration (Firebase / API)
                if (email.equals("test@gmail.com") && password.equals("123456")) {
                    Toast.makeText(RegisterActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupBoldStyling() {
        // Setup Email field styling
        if (emailLayout != null) {
            makeBoldTextInputLayout(emailLayout, "Enter Email", etEmail);
        }

        // Setup Password field styling
        if (passwordLayout != null) {
            makeBoldTextInputLayout(passwordLayout, "Enter Password", etPassword);
        }
    }

    private void makeBoldTextInputLayout(TextInputLayout textInputLayout, String hintText, TextInputEditText editText) {
        if (textInputLayout == null || editText == null) return;

        try {
            // Set same color for hint and text
            int customColor = Color.parseColor("#3A6073");
            ColorStateList hintColorStateList = ColorStateList.valueOf(customColor);
            textInputLayout.setHintTextColor(hintColorStateList);
            textInputLayout.setDefaultHintTextColor(hintColorStateList);

            // Bold hint with SpannableString
            SpannableString boldHint = new SpannableString(hintText);
            boldHint.setSpan(new StyleSpan(Typeface.BOLD), 0, boldHint.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                boldHint.setSpan(new TypefaceSpan("sans-serif-medium"), 0, boldHint.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            textInputLayout.setHint(boldHint);

            // Style the EditText (same color as hint)
            Typeface boldTypeface = Typeface.create("sans-serif-medium", Typeface.BOLD);
            editText.setTypeface(boldTypeface);
            editText.setTextColor(customColor);       // 👈 input text color same as hint
            editText.setHintTextColor(customColor);   // 👈 hint text color same

            // Refresh
            textInputLayout.refreshDrawableState();
            textInputLayout.invalidate();

        } catch (Exception e) {
            e.printStackTrace();
            setupFallbackStyling(textInputLayout, hintText, editText);
        }
    }


    // Fallback method if main method fails
    private void setupFallbackStyling(TextInputLayout textInputLayout, String hintText, TextInputEditText editText) {
        try {
            // Simple approach
            textInputLayout.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#3A6073")));

            // Make hint bold using simple method
            SpannableString hint = new SpannableString(hintText);
            hint.setSpan(new StyleSpan(Typeface.BOLD), 0, hint.length(), 0);
            textInputLayout.setHint(hint);

            // Make EditText text bold
            editText.setTypeface(editText.getTypeface(), Typeface.BOLD);
            editText.setTextColor(Color.BLACK);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Alternative method - call this in onCreate after setContentView
    private void setupDelayedStyling() {
        // Run after layout is fully rendered
        findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                setupBoldStyling();
            }
        });
    }

    // Static utility method for reuse in other activities
    public static void applyBoldStylingToTextInput(TextInputLayout textInputLayout,
                                                   String hintText,
                                                   TextInputEditText editText) {
        if (textInputLayout == null || editText == null) return;

        try {
            // Set colors
            ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#3A6073"));
            textInputLayout.setHintTextColor(colorStateList);
            textInputLayout.setDefaultHintTextColor(colorStateList);

            // Bold hint
            SpannableString boldHint = new SpannableString(hintText);
            boldHint.setSpan(new StyleSpan(Typeface.BOLD), 0, hintText.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textInputLayout.setHint(boldHint);

            // Bold EditText
            editText.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
            editText.setTextColor(Color.BLACK);
            editText.setHintTextColor(Color.parseColor("#3A6073"));

            // Refresh
            textInputLayout.refreshDrawableState();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBoldStyling();
    }
}