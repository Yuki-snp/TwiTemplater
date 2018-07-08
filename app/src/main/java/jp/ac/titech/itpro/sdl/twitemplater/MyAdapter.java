package jp.ac.titech.itpro.sdl.twitemplater;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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

        int bgDrawable;
        if(nounList.get(position).getSelected())bgDrawable = R.drawable.status_selected;
        else bgDrawable = R.drawable.status_unselected;
        convertView.setBackgroundDrawable(convertView.getResources().getDrawable(bgDrawable));

        String setAnswer = nounList.get(position).getAnswer();
        if(!nounList.get(position).getisConnect()) setAnswer += "⏎";
        ((TextView)convertView.findViewById(R.id.Answer)).setText(setAnswer);
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
            //EditTextが編集し終わったときに呼ばれる関数
            @Override
            public void afterTextChanged(Editable s) {
                //editのなかにこれいれないと更新されない
                nounList.get(position).setEdit(String.valueOf(s));
            }
        });

        //各処理とも最後に文字数計算を忘れない

        ((Switch)convertView.findViewById(R.id.switch_c)).setChecked(nounList.get(position).getisConnect());
        ((Switch)convertView.findViewById(R.id.switch_c)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()) {
                    //Switch : Off -> On の時の処理
                    nounList.get(position).setisConnect(true);
                    notifyDataSetChanged();
                } else {
                    //Switch : On -> Off の時の処理
                    nounList.get(position).setisConnect(false);
                    notifyDataSetChanged();
                }
            }
        });

        ((Switch)convertView.findViewById(R.id.switch_s)).setChecked(nounList.get(position).getSelected());
        ((Switch)convertView.findViewById(R.id.switch_s)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()) {
                    //Switch : Off -> On の時の処理
                    nounList.get(position).setSelected(true);
                    notifyDataSetChanged();
                } else {
                    //Switch : On -> Off の時の処理
                    nounList.get(position).setSelected(false);
                    notifyDataSetChanged();
                }
            }
        });

        ((Button)convertView.findViewById(R.id.button_c)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nounList.get(position).setEdit("");
                notifyDataSetChanged();
            }
        });

        ((Button)convertView.findViewById(R.id.button_f)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nounList.get(position).setEdit(nounList.get(position).getAnswer());
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

}