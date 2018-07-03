package jp.ac.titech.itpro.sdl.twitemplater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private InputMethodManager inputMethodManager;
    private ConstraintLayout mainLayout;
    private EditText editText;
    private TextView textCount;
    private final int MAX_LENGTH = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button edit_button = findViewById(R.id.edit_button);
        Button edit_button2 = findViewById(R.id.edit_button2);
        editText = findViewById(R.id.editText);
        textCount = findViewById(R.id.textCount);
        textCount.setText("0/" + MAX_LENGTH);

        editText.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                int textColor = Color.GRAY;

                // 入力文字数の表示
                int txtLength = s.length();
                textCount.setText(Integer.toString(txtLength) + "/" + MAX_LENGTH);

                // 指定文字数オーバーで文字色を赤くする
                if (txtLength > MAX_LENGTH) {
                    textColor = Color.RED;
                }
                textCount.setTextColor(textColor);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if(str.trim().length() > 0 && str.length() <= MAX_LENGTH) {
                    Intent intent = new Intent(getApplication(), LineModeActivity.class);
                    intent.putExtra("Template", str);
                    startActivity(intent);
                }
            }
        });

        edit_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if(str.trim().length() > 0 && str.length() <= MAX_LENGTH) {
                    Intent intent = new Intent(getApplication(), LineModeActivity.class);
                    intent.putExtra("Template", str);
                    intent.putExtra("filled", true);
                    startActivity(intent);
                }
            }
        });

        //画面全体のレイアウト
        mainLayout = findViewById(R.id.mainLayout);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * EditText編集時に背景をタップしたらキーボードを閉じるようにするタッチイベントの処理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //キーボードを隠す
        inputMethodManager.hideSoftInputFromWindow(mainLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //背景にフォーカスを移す
        mainLayout.requestFocus();

        return false;
    }
}
