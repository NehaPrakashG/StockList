package com.example.stocklist;


import android.widget.AdapterView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.stocklist.Interface.RecyclerViewClickListener;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

   @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule=
           new ActivityTestRule<>(MainActivity.class);

    @Test
    public void recyclerTest(){
        if(getRVcount()>0) {
            Espresso.onView(withId(R.id.recyclerViewmv)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        }
    }
    private int getRVcount(){
        RecyclerView recyclerView = (RecyclerView) mainActivityActivityTestRule.getActivity().findViewById(R.id.recyclerViewmv);
        return recyclerView.getAdapter().getItemCount();
    }
}