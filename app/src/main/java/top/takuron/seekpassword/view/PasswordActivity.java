package top.takuron.seekpassword.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import top.takuron.seekpassword.R;

public class PasswordActivity extends AppCompatActivity {

    private Intent i;
    private EditText et;
    private long mPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et = (EditText)findViewById(R.id.text_encrypted_password);
        i = getIntent();
        et.setText(i.getStringExtra("password"));



    }

    public void onBackbutton(View v){
        Intent intent = new Intent();
        intent.setClass(PasswordActivity.this, MainActivity.class);
        startActivity(intent);
        this.finish();
        overridePendingTransition(R.anim.in_from_left,R.anim.out_to_right);
    }

    public void onCopybutton(View v){
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, et.getText().toString());
        clipboard.setPrimaryClip(clipData);

        Snackbar.make(v, R.string.copy_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();
        if((mNowTime - mPressedTime) > 2000){
            Toast.makeText(this,R.string.exit_message , Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        }
        else{
            this.finish();
            System.exit(0);
        }
    }
}
