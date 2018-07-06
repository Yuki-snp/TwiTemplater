package jp.ac.titech.itpro.sdl.twitemplater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FinalCopyActivity extends AppCompatActivity {
    private InputMethodManager inputMethodManager;
    private LinearLayout mainLayout;
    private EditText editText;
    private TextView textCount;
    private final int MAX_LENGTH = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //読込フェーズ
        Intent intent = getIntent();
        String FinalCopy = intent.getStringExtra("FinalCopy");
        Log.d("INPUT FINALCOPY",FinalCopy);

        Button edit_button = findViewById(R.id.edit_button);
        edit_button.setText("ツイート");
        //今度は2つもボタンがいらない
        Button edit_button2 = findViewById(R.id.edit_button2);
        ViewGroup p = (ViewGroup) edit_button2.getParent();
        p.removeView(edit_button2);
        editText = findViewById(R.id.editText);
        editText.setText(FinalCopy);
        textCount = findViewById(R.id.textCount);
        textCount.setText(Integer.toString(FinalCopy.length()) + "/" + MAX_LENGTH);
        if(FinalCopy.length()>MAX_LENGTH)textCount.setTextColor(Color.RED);

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
                    Uri uri = Uri.parse(tweeting(str));
                    Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                    startActivity(intent);
                }
            }
        });

        //画面全体のレイアウト
        mainLayout = findViewById(R.id.mainLayout);
        //キーボード表示を制御するためのオブジェクト
        inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private String tweeting(String EditedTemplete) {
        String strTweet = "";
        String strMessage = EditedTemplete;
        //String strHashTag = "#TwiTemplater";
        //String strUrl = "http://かくかくしかじか.hatenablog.com/";

        try {
            strTweet = "http://twitter.com/intent/tweet?text="
                    + URLEncoder.encode(strMessage, "UTF-8");
            //+ "+"
            //+ URLEncoder.encode(strHashTag, "UTF-8")
            //+ "&url="
            //+ URLEncoder.encode(strUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return strTweet;
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
