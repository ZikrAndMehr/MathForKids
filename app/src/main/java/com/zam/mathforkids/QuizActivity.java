package com.zam.mathforkids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity implements View.OnTouchListener{

    private SharedPreferences sharedPreferences1, sharedPreferences2;
    private int n=0, ansCorrect=0, c = 0, max, min, ans;
    private  String s, question;
    private Question[] quizQuestions;
    private ImageView ivBackQA, ivAQA, ivBQA, ivCQA, ivDQA;
    private TextView tvNumberOfQuestionQA, tvXQA, tvOperationQA, tvYQA, tvAQA, tvBQA, tvCQA, tvDQA;
    private String correct="", wrong="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_quiz);

        sharedPreferences1 = getApplicationContext().getSharedPreferences("DIGIT_RANGE",MODE_PRIVATE);
        sharedPreferences2 = getApplicationContext().getSharedPreferences("OPERATION",MODE_PRIVATE);

        min = sharedPreferences1.getInt("MIN",10);
        max = sharedPreferences1.getInt("MAX",20);
        s = sharedPreferences2.getString("Operation","+");

        quizQuestions = new Question[10];
        for (int i=0; i<10; i++){
            quizQuestions[i] = new Question(s,max,min);

        }

        ivBackQA = findViewById(R.id.ivBackQA);
        ivBackQA.setOnTouchListener(this);

        tvNumberOfQuestionQA = findViewById(R.id.tvNumberOfQuestionQA);

        tvXQA = findViewById(R.id.tvXQA);
        tvOperationQA = findViewById(R.id.tvOperationQA);
        tvYQA = findViewById(R.id.tvYQA);

        switch (s){
            case "+": tvOperationQA.setText("+"); break;
            case "-": tvOperationQA.setText("-"); break;
            case "×": tvOperationQA.setText("×"); break;
            case "÷": tvOperationQA.setText("÷"); break;
        }

        tvAQA = findViewById(R.id.tvAQA);
        tvBQA = findViewById(R.id.tvBQA);
        tvCQA = findViewById(R.id.tvCQA);
        tvDQA = findViewById(R.id.tvDQA);

        ivAQA = findViewById(R.id.ivAQA);
        ivAQA.setOnTouchListener(this);
        ivBQA = findViewById(R.id.ivBQA);
        ivBQA.setOnTouchListener(this);
        ivCQA = findViewById(R.id.ivCQA);
        ivCQA.setOnTouchListener(this);
        ivDQA = findViewById(R.id.ivDQA);
        ivDQA.setOnTouchListener(this);

        setUpQuestion();
    }

    private void setUpQuestion() {
        tvNumberOfQuestionQA.setText(String.valueOf(n+1)+"/10");

        tvXQA.setText(quizQuestions[n].getA());
        tvYQA.setText(quizQuestions[n].getB());

        c = quizQuestions[n].getC();
        int[] random = quizQuestions[n].getRandom();
        question = quizQuestions[n].getQuestion();

        tvAQA.setText(String.valueOf(random[0]));
        tvBQA.setText(String.valueOf(random[1]));
        tvCQA.setText(String.valueOf(random[2]));
        tvDQA.setText(String.valueOf(random[3]));

        switch ((int) (Math.random() * 5)){
            case 1: ans=ivAQA.getId(); tvAQA.setText(String.valueOf(c)); break;
            case 2: ans=ivBQA.getId(); tvBQA.setText(String.valueOf(c)); break;
            case 3: ans=ivCQA.getId(); tvCQA.setText(String.valueOf(c)); break;
            default: ans=ivDQA.getId(); tvDQA.setText(String.valueOf(c)); break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView imageView = (ImageView) v;

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            imageView.setColorFilter(getColor(R.color.transparent));
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            imageView.clearColorFilter();

            if (imageView.getId()==R.id.ivBackQA){
                finish();
            }
            else {
                n++;
                if (imageView.getId()==ans){
                    ansCorrect++;
                    correct+=question+"\n";
                }
                else {
                    changeAns(imageView.getId());
                    wrong+=question+"\n";
                }
                if (n==10) {
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("ACTIVITY","QA");
                    intent.putExtra("CORRECT",String.valueOf(ansCorrect));
                    intent.putExtra("WRONG",String.valueOf(10-ansCorrect));
                    intent.putExtra("C_QUESTIONS",correct);
                    intent.putExtra("W_QUESTIONS",wrong);
                    startActivity(intent);
                    finish();
                }
                else {setUpQuestion();}
            }
        }
        return true;
    }

    private void changeAns(int id) {
        int x=question.indexOf('=')+1;

        switch (id){
            case R.id.ivAQA: question=question.substring(0,x)+tvAQA.getText(); break;
            case R.id.ivBQA: question=question.substring(0,x)+tvBQA.getText(); break;
            case R.id.ivCQA: question=question.substring(0,x)+tvCQA.getText(); break;
            case R.id.ivDQA: question=question.substring(0,x)+tvDQA.getText(); break;
        }
    }
}