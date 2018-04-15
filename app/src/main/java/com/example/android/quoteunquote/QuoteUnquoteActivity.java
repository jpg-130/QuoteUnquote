package com.example.android.quoteunquote;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static java.security.AccessController.getContext;
public class QuoteUnquoteActivity extends AppCompatActivity {

    private QuoteUnquoteDictionary dictionary;
    View view;
    ArrayList<Character> hintsGiven=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote_unquote);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("quote.txt");
            dictionary = new QuoteUnquoteDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        String START_MESSAGE="Press Play to Start";

        TextView encrypted = (TextView)findViewById(R.id.Encrypt);
        EditText solution = (EditText)findViewById(R.id.Solution);

        encrypted.setVisibility(view.GONE);
        solution.setVisibility(view.GONE);
        //encrypted.setText(START_MESSAGE);

        Button quit = (Button)findViewById(R.id.Quit);
        Button check_answer = (Button)findViewById(R.id.Reset);
        Button get_answer = (Button)findViewById(R.id.GetAnswer);
        Button hint = (Button)findViewById(R.id.Hint);

        quit.setVisibility(view.GONE);
        check_answer.setVisibility(view.GONE);
        get_answer.setVisibility(view.GONE);
        hint.setVisibility(view.GONE);
        Toast.makeText(getApplicationContext(),"Press Play",Toast.LENGTH_LONG).show();

    }


    public boolean defaultAction(View view){
        TextView encrypted = (TextView)findViewById(R.id.Encrypt);
        TextView solution = (TextView)findViewById(R.id.Solution);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        Button quit = (Button)findViewById(R.id.Quit);
        Button reset = (Button)findViewById(R.id.Reset);
        Button get_answer = (Button)findViewById(R.id.GetAnswer);
        final Button hint = (Button)findViewById(R.id.Hint);


        fab.setImageResource(android.R.drawable.ic_menu_help);
        fab.hide();

        encrypted.setText("");
        solution.setText("");
        solution.setEnabled(true);
        solution.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(solution, InputMethodManager.SHOW_IMPLICIT);

        quit.setVisibility(view.VISIBLE);
        reset.setVisibility(view.VISIBLE);
        get_answer.setVisibility(view.VISIBLE);
        hint.setVisibility(view.VISIBLE);

        encrypted.setVisibility(view.VISIBLE);
        solution.setVisibility(view.VISIBLE);

        encrypted.setText(dictionary.EncryptText());
        solution.setText(dictionary.getEncrypt());
        reset();
        hint();
        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                reset();
            }
        });
        hint.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                hint();
            }
        });


        quit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                quit();
            }
        });

        get_answer.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                TextView hint = (TextView)findViewById(R.id.userHints);
                EditText solution = (EditText)findViewById(R.id.Solution);
                String answer = solution.getEditableText().toString();
                if(dictionary.CheckAns(answer)){
                    hint.setTextColor(Color.GREEN);
                    hint.setText("CORRECT");
                    //hint.setTextColor(Color.BLACK);
                }
                else {
                    Toast.makeText(getApplicationContext(),"INCORRECT :~(",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return true;
    }

    public void hint(){
        String oldText;
        char letter=dictionary.generate();
        hintsGiven=new ArrayList<>();
        TextView hintText =(TextView)findViewById(R.id.userHints);
        hintText.setTextColor(Color.BLACK);
        oldText=hintText.getText().toString();
        if(hintsGiven.isEmpty())
        {
            String Quote=dictionary.getQuote();
            {
                for (int i = 0; i < Quote.length(); i++) {
                    if (Quote.charAt(i) == letter) {
                        hintsGiven.add(letter);
                        hintText.setText(dictionary.dict.get(letter)+" is "+letter );
                        if (oldText==hintText.getText()){
                            hint();
                        }
                        return;
                    }
                }
            }
        }
        else {
            while(hintsGiven.contains(dictionary.dict.get(letter))){
                letter=dictionary.generate();
                String Quote=dictionary.getQuote();
                {
                    for (int i = 0; i < Quote.length(); i++) {
                        if (Quote.charAt(i) == letter) {
                            hintsGiven.add(letter);
                            hintText.setText(dictionary.dict.get(letter)+" is "+letter );
                            if (oldText==hintText.getText()){
                                hint();
                            }
                            return;
                        }
                    }
                }

            }
        }
    }

    public void quit(){
        dictionary.quotelist.clear();
        dictionary.dict.clear();
        hintsGiven.clear();
        this.finish();

    }

    public void reset(){
        EditText solution = (EditText)findViewById(R.id.Solution);
        solution.setTextColor(Color.BLUE);
        solution.setText(dictionary.getEncrypt().toUpperCase(), TextView.BufferType.EDITABLE);
    }
}