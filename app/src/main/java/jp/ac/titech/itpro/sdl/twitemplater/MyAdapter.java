package jp.ac.titech.itpro.sdl.twitemplater;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<TwiContext> nounList;

    public MyAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setNounList(ArrayList<TwiContext> nounList) {
        this.nounList = nounList;
    }

    @Override
    public int getCount() {
        return nounList.size();
    }

    @Override
    public Object getItem(int position) {
        return nounList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return nounList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.twi_context,parent,false);

        ((TextView)convertView.findViewById(R.id.Answer)).setText(nounList.get(position).getAnswer());
        ((EditText)convertView.findViewById(R.id.Edit)).setHint(nounList.get(position).getAnswer());
        ((EditText)convertView.findViewById(R.id.Edit)).setText(nounList.get(position).getEdit());
        //ココ重要
        ((EditText)convertView.findViewById(R.id.Edit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            //EditTexitが編集し終わったときに呼ばれる関数
            @Override
            public void afterTextChanged(Editable s) {
                //editのなかにこれいれないと更新されない
                nounList.get(position).setEdit(String.valueOf(s));
            }
        });

        /**
        ((EditText)convertView.findViewById(R.id.Edit)).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    // フォーカスが外れた場合キーボードを非表示にする
                    InputMethodManager inputMethodMgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodMgr.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });
         */

        return convertView;
    }

}