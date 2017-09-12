package com.gacpedromediateam.primus.gachymnal.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;


import com.gacpedromediateam.primus.gachymnal.R;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
public class IntroActivity extends AppIntro{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);*/

        addSlide(AppIntroFragment.newInstance("Welcome","This App is designed to ease the burden of going about with a hymn Book", R.drawable.download2pngedited, ContextCompat.getColor(this,R.color.white)));
        addSlide(AppIntroFragment.newInstance("Language Change","You can change the default language by clicking on the button as shown in the image",R.drawable.tutorial2, ContextCompat.getColor(this,R.color.white)));
        addSlide(AppIntroFragment.newInstance("Search","Click on the button as shown above to move to the search page",R.drawable.searchpng, ContextCompat.getColor(this,R.color.white)));
        addSlide(AppIntroFragment.newInstance("Update","Click on the button as shown above to update the Hymn List",R.drawable.update, ContextCompat.getColor(this,R.color.white)));
        setIndicatorColor(Color.BLACK,Color.DKGRAY);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(IntroActivity.this,FormLoadActivity.class));
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(IntroActivity.this,FormLoadActivity.class));
        finish();
    }





    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        finish();
        super.onBackPressed();
    }

}

