package com.example.vampiresurvivor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity2 extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener, Animator.AnimatorListener{
    private ImageView imvVampire;
    private ImageView titleVampire;
    private ImageView titleSurvive;
    private ImageButton btnPlay;
    private ImageView imvBat1;
    private ImageView imvBat2;
    private ImageView imvBat3;
    private ImageView imvBat4;
    private ImageView imvBat5;
    private ImageView imvBat6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnPlay = findViewById(R.id.btnPlay);
        btnPlay.setAlpha(0.0f);
        imvVampire = findViewById(R.id.vampire);
        imvVampire.setAlpha(0.0f);
        titleVampire = findViewById(R.id.title1);
        titleSurvive = findViewById(R.id.title2);
        imvBat1 = findViewById(R.id.bat1);
        imvBat1.setAlpha(0.0f);
        imvBat2 = findViewById(R.id.bat2);
        imvBat2.setAlpha(0.0f);
        imvBat3 = findViewById(R.id.bat3);
        imvBat3.setAlpha(0.0f);
        imvBat4 = findViewById(R.id.bat4);
        imvBat4.setAlpha(0.0f);
        imvBat5 = findViewById(R.id.bat5);
        imvBat5.setAlpha(0.0f);
        imvBat6 = findViewById(R.id.bat6);
        imvBat6.setAlpha(0.0f);

        ConstraintLayout frm = findViewById(R.id.frm);
        frm.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private Rect getDimensionsFinestra() {
        Rect rect = new Rect();
        MainActivity2.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        // Calculem les dimensions de la toolbar superior
        int contentViewTop = MainActivity2.this.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        // Restem l'alçada de la finestra de la toolbar
        int alsada = Math.abs(rect.height() - contentViewTop);
        return new Rect(0,0,rect.width(), alsada);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationStart(@NonNull Animator animator) {

    }

    @Override
    public void onAnimationEnd(@NonNull Animator animator) {

    }

    @Override
    public void onAnimationCancel(@NonNull Animator animator) {

    }

    @Override
    public void onAnimationRepeat(@NonNull Animator animator) {

    }

    @Override
    public void onGlobalLayout() {
//        MediaPlayer mP = MediaPlayer.create(this, R.raw.vampire);
//        mP.start();
        Rect rec = getDimensionsFinestra();
        Log.d("anim","Amplada: " + rec.width() + " Alçada: " + rec.height());
        //Objecte que fa aparèixer el vampir
        ObjectAnimator animVampireIn = ObjectAnimator.ofFloat(imvVampire, "alpha", 0.0f, 1.0f);
        //Objecte que fa aparèixer el títol vampire
        ObjectAnimator animTitleVampireIn = ObjectAnimator.ofFloat(titleVampire, "Y", 0, rec.height()-titleSurvive.getHeight()-10);
        //Objecte que fa aparèixer el títol survive
        ObjectAnimator animTitleSurviveIn = ObjectAnimator.ofFloat(titleSurvive, "Y", 0, rec.height()-titleSurvive.getHeight()*2-10);

        ObjectAnimator animButton = ObjectAnimator.ofFloat(btnPlay, "alpha", 0.0f, 1.0f);

        AnimatorSet animatorFadeout = new AnimatorSet();
        animatorFadeout.playTogether(
                ObjectAnimator.ofFloat(titleVampire, "alpha", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(titleSurvive, "alpha", 1.0f, 0.0f),
                ObjectAnimator.ofFloat(imvVampire, "alpha", 1.0f, 0.0f)
        );
        animButton.setDuration(2000);
        animTitleVampireIn.setDuration(2000);
        animTitleSurviveIn.setDuration(2000);
        animVampireIn.setDuration(2000);
        animatorFadeout.setDuration(2000);


        AnimationDrawable bat1 = (AnimationDrawable) imvBat1.getDrawable();
        bat1.start();
        AnimationDrawable bat2 = (AnimationDrawable) imvBat2.getDrawable();
        bat2.start();
        AnimationDrawable bat3 = (AnimationDrawable) imvBat3.getDrawable();
        bat3.start();
        AnimationDrawable bat4 = (AnimationDrawable) imvBat4.getDrawable();
        bat4.start();
        AnimatorSet animatorSetBats = new AnimatorSet();
        animatorSetBats.playTogether(
                ObjectAnimator.ofFloat(imvBat1, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(imvBat2, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(imvBat3, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(imvBat4, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(imvBat5, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(imvBat6, "alpha", 0.0f, 1.0f),
                ObjectAnimator.ofFloat(imvBat1, "X",  rec.width(),-imvBat1.getWidth()-50),
                ObjectAnimator.ofFloat(imvBat2, "X", rec.width(),-imvBat1.getWidth()-100),
                ObjectAnimator.ofFloat(imvBat3, "X", rec.width(),-imvBat1.getWidth()-150),
                ObjectAnimator.ofFloat(imvBat4, "X", rec.width(),-imvBat1.getWidth()-20),
                ObjectAnimator.ofFloat(imvBat5, "X", rec.width(),-imvBat1.getWidth()),
                ObjectAnimator.ofFloat(imvBat6, "X", rec.width(),-imvBat1.getWidth()-250)


        );
        animatorSetBats.setDuration(4000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animVampireIn, animTitleVampireIn, animTitleSurviveIn, animatorSetBats, animatorFadeout, animButton);
        animatorSet.start();

    }
}