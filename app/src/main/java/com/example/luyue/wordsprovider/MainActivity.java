package com.example.luyue.wordsprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MyWordsTag";
    private ContentResolver resolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver = this.getContentResolver();

        Button buttonAll=(Button)findViewById(R.id.buttonAll);
        Button buttonInsert=(Button)findViewById(R.id.buttonInsert);
        Button buttonDelete=(Button)findViewById(R.id.buttonDelete);
        Button buttonDeleteAll=(Button)findViewById(R.id.buttonDeleteAll);
        Button buttonUpdate=(Button)findViewById(R.id.buttonUpdate);
        Button buttonQuery=(Button)findViewById(R.id.buttonQuery);

        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = resolver.query(WordsDB.wordsDB.CONTENT_URI,
                        new String[] { WordsDB.wordsDB._ID, WordsDB.wordsDB.Column_Name_Word,WordsDB.wordsDB.Column_Name_Meaning,WordsDB.wordsDB.Column_Name_Sample},
                        null, null, null);
                if (cursor == null){
                    Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                    return;
                }

                String msg = "";
                if (cursor.moveToFirst()){//找到记录，这里简单起见，使用Log输出
                    do{
                        msg += "ID:" + cursor.getInt(cursor.getColumnIndex(WordsDB.wordsDB._ID)) + ",";
                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(WordsDB.wordsDB.Column_Name_Word))+ ",";
                        msg += "含义：" + cursor.getInt(cursor.getColumnIndex(WordsDB.wordsDB.Column_Name_Meaning)) + ",";
                        msg += "示例" + cursor.getFloat(cursor.getColumnIndex(WordsDB.wordsDB.Column_Name_Sample)) + "\n";
                    }while(cursor.moveToNext());
                }

                Log.v(TAG,msg);
            }
        });

        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strWord="luyue";
                String strMeaning="2015011156";
                String strSample="1501";
                ContentValues values = new ContentValues();

                values.put(WordsDB.wordsDB.Column_Name_Word, strWord);
                values.put(WordsDB.wordsDB.Column_Name_Meaning, strMeaning);
                values.put(WordsDB.wordsDB.Column_Name_Sample, strSample);

                Uri newUri = resolver.insert(WordsDB.wordsDB.CONTENT_URI, values);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="1";//简单起见，这里指定ID，用户可在程序中设置id的实际值
                Uri uri = Uri.parse(WordsDB.wordsDB.CONTENT_URI_STRING + "/" + id);
                int result = resolver.delete(uri, null, null);
            }
        });

        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resolver.delete(WordsDB.wordsDB.CONTENT_URI, null, null);
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="3";
                String strWord="Banana";
                String strMeaning="banana";
                String strSample="This banana is very nice.";
                ContentValues values = new ContentValues();

                values.put(WordsDB.wordsDB.Column_Name_Word, strWord);
                values.put(WordsDB.wordsDB.Column_Name_Meaning, strMeaning);
                values.put(WordsDB.wordsDB.Column_Name_Sample, strSample);

                Uri uri = Uri.parse(WordsDB.wordsDB.CONTENT_URI_STRING + "/" + id);
                int result = resolver.update(uri, values, null, null);

            }
        });

        buttonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id="3";
                Uri uri = Uri.parse(WordsDB.wordsDB.CONTENT_URI_STRING + "/" + id);
                Cursor cursor = resolver.query(WordsDB.wordsDB.CONTENT_URI,
                        new String[] { WordsDB.wordsDB._ID, WordsDB.wordsDB.Column_Name_Word,WordsDB.wordsDB.Column_Name_Meaning,WordsDB.wordsDB.Column_Name_Sample},
                        null, null, null);
                if (cursor == null){
                    Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                    return;
                }

                //找到记录，这里简单起见，使用Log输出

                String msg = "";
                if (cursor.moveToFirst()){
                    do{
                        msg += "ID:" + cursor.getInt(cursor.getColumnIndex(WordsDB.wordsDB._ID)) + ",";
                        msg += "单词：" + cursor.getString(cursor.getColumnIndex(WordsDB.wordsDB.Column_Name_Word))+ ",";
                        msg += "含义：" + cursor.getInt(cursor.getColumnIndex(WordsDB.wordsDB.Column_Name_Meaning)) + ",";
                        msg += "示例" + cursor.getFloat(cursor.getColumnIndex(WordsDB.wordsDB.Column_Name_Sample)) + "\n";
                    }while(cursor.moveToNext());
                }

                Log.v(TAG,msg);
            }
        });

    }
}
