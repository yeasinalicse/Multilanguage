package com.yeasin.multilanguage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv_select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG","main = "+MultiLanguageUtil.getInstance().getLanguageType());
        MultiLanguageUtil.getInstance().setConfiguration(this);
        setContentView(R.layout.activity_main);
        Button btnOther = findViewById(R.id.btn_other);
        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, OtherPageActivity.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        int selectedLanguage = 0;
        switch (id){
            case R.id.action_language_english:
                selectedLanguage = LanguageType.LANGUAGE_EN;
                break;
            case R.id.action_bangla:
                selectedLanguage = LanguageType.LANGUAGE_BN;
                break;
            case R.id.action_chinese:
                selectedLanguage = LanguageType.LANGUAGE_CHINESE;
                break;

        }
        MultiLanguageUtil.getInstance().updateLanguage(this,selectedLanguage);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        return super.onOptionsItemSelected(item);
    }

}
