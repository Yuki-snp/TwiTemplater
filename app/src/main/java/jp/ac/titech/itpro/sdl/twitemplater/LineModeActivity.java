package jp.ac.titech.itpro.sdl.twitemplater;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class LineModeActivity extends AppCompatActivity {

    private String Template;
    private boolean filled;
    //private TextView textSum;
    //private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linemode);

        //読込フェーズ
        Intent intent = getIntent();
        Template = intent.getStringExtra("Template");
        filled = intent.getBooleanExtra("filled",false);
        Log.d("INPUT TEMPLATE",Template);

        //準備フェーズ
        ListView listView = findViewById(R.id.container);

        final ArrayList<TwiContext> list = new ArrayList<>();
        MyAdapter adapter = new MyAdapter(LineModeActivity.this);
        adapter.setNounList(list);
        listView.setAdapter(adapter);

        //テンプレートの分解フェーズ
        String[] template_split_one = Template.split("\n");
        String[] stopwords = {" ","　","！","!","？","?",".","．","。"};
        ArrayList<Map<String, String>> template_split_two = new ArrayList<Map<String, String>>();
        String temp = "";
        for(String S : template_split_one){
            for(int i=0;i<S.length()-1;i++){
                String s = S.substring(i,i+1);
                String next = S.substring(i+1,i+2);
                temp += s;
                if(Arrays.asList(stopwords).contains(s) && !Arrays.asList(stopwords).contains(next)) {
                    list.add(setTwiContext(temp,filled,true));
                    adapter.notifyDataSetChanged();
                    temp = "";
                }
            }
            if(S.length()>0) {
                temp += S.substring(S.length() - 1);
                list.add(setTwiContext(temp,filled,false));
                adapter.notifyDataSetChanged();
            }
            temp = "";
        }

        /**
        textSum = findViewById(R.id.textSum);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String EditedTemplate = "";
                for(TwiContext twicontext : list){
                    //選択されてない文は無視する
                    if(twicontext.getSelected()) {
                        String edited = twicontext.getEdit();
                        //記述がなければ原文を入れる
                        EditedTemplate += (!edited.isEmpty()) ? edited : twicontext.getAnswer();
                        //連結がOFFの場合は改行を入れる
                        if (!twicontext.getisConnect()) EditedTemplate += "\n";
                    }
                }
                textSum.setText(Integer.toString(EditedTemplate.length()) + "/140");
                }
            });
         */

        //清書モード
        Button final_button = findViewById(R.id.final_button);
        final_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EditedTemplate = "";
                for(TwiContext twicontext : list){
                    //選択されてない文は無視する
                    if(twicontext.getSelected()) {
                        String edited = twicontext.getEdit();
                        //記述がなければ原文を入れる
                        EditedTemplate += (!edited.isEmpty()) ? edited : twicontext.getAnswer();
                        //連結がOFFの場合は改行を入れる
                        if (!twicontext.getisConnect()) EditedTemplate += "\n";
                    }
                }
                //ここを清書フェーズに書き換えても良さそう
                if(EditedTemplate.trim().length() > 0) {
                    Intent intent = new Intent(getApplication(), FinalCopyActivity.class);
                    intent.putExtra("FinalCopy", EditedTemplate);
                    startActivity(intent);
                }
            }
        });

        //清書いらねえツイートいくぜ！
        Button tweet_button = findViewById(R.id.Tweet_button);
        tweet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EditedTemplate = "";
                for(TwiContext twicontext : list){
                    //選択されてない文は無視する
                    if(twicontext.getSelected()) {
                        String edited = twicontext.getEdit();
                        //記述がなければ原文を入れる
                        EditedTemplate += (!edited.isEmpty()) ? edited : twicontext.getAnswer();
                        //連結がOFFの場合は改行を入れる
                        if (!twicontext.getisConnect()) EditedTemplate += "\n";
                    }
                }
                //ここを清書フェーズに書き換えても良さそう
                Uri uri = Uri.parse(tweeting(EditedTemplate));
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
                /**
                setContentView(R.layout.web);
                webView = findViewById(R.id.web_view);

                // JavaScriptを有効化
                webView.getSettings().setJavaScriptEnabled(true);
                // Web Storage を有効化
                webView.getSettings().setDomStorageEnabled(true);
                // Hardware Acceleration ON
                getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

                webView.loadUrl(tweeting(EditedTemplate));
                */
            }
        });
    }

    private TwiContext setTwiContext(String temp,boolean filled,boolean isConnect){
        TwiContext twicontext = new TwiContext();
        twicontext.setAnswer(temp);
        if(filled)twicontext.setEdit(temp);
        else twicontext.setEdit("");
        twicontext.setisConnect(isConnect);
        twicontext.setSelected(true);
        return twicontext;
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
}
