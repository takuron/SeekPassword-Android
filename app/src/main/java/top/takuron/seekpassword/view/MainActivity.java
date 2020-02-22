package top.takuron.seekpassword.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import top.takuron.seekpassword.R;
import top.takuron.seekpassword.presenter.MainPresenter;
import top.takuron.seekpassword.presenter.OnSeekpasswordlinstener;

public class MainActivity extends AppCompatActivity {

    private Button generate_button;
    private LinearLayout fatherview;
    private CheckBox cb_remove_punctuation;
    private TextInputEditText et_distinguish_code;
    private TextInputEditText et_memory_password;
    private long mPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generate_button = (Button)findViewById(R.id.generate_button);
        fatherview = (LinearLayout)findViewById(R.id.father);

        cb_remove_punctuation = (CheckBox)findViewById(R.id.is_remove_punctuation);
        et_distinguish_code = (TextInputEditText) findViewById(R.id.text_distinguish_code);
        et_memory_password = (TextInputEditText) findViewById(R.id.text_memory_password);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();
         if((mNowTime - mPressedTime) > 2000){
             Toast.makeText(this, R.string.exit_message, Toast.LENGTH_SHORT).show();
             mPressedTime = mNowTime;
         }
         else{
             this.finish();
             System.exit(0);
         }
    }

    public void onHelp(View v){
        dialogHelp();
    }

    private void dialogHelp(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.help_dialog_title)
                .setMessage(R.string.help_dialog_message)
                .setCancelable(true)
                .setPositiveButton(R.string.button_back,null)
                .setNeutralButton(R.string.button_article,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse("https://sspai.com/post/55403");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
        dialog.show();
    }

    public void gengratePassword (View v){
        boolean isgo = true;
        if(et_distinguish_code.getText().toString().equals("")){
            et_distinguish_code.setError(getResources().getString(R.string.no_text));
            isgo = false;
        }
        if(et_memory_password.getText().toString().equals("")){
            et_memory_password.setError(getResources().getString(R.string.no_text));
            isgo = false;
        }
        if(isgo){
            MainPresenter.seekpassword(fatherview,
                    et_memory_password.getText().toString(),
                    et_distinguish_code.getText().toString(),
                    cb_remove_punctuation.isChecked(),new OnSeekpasswordlinstener(){

                        @Override
                        public void onReturn(String returns) {
                            Intent intent = new Intent();
                            intent.putExtra("password",returns);
                            intent.setClass(MainActivity.this, PasswordActivity.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                            MainActivity.this.finish();
                        }
                    });
        }
    }
}
