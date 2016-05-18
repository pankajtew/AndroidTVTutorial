package com.sample.androidtv.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v17.leanback.app.GuidedStepFragment;
import android.support.v17.leanback.widget.GuidanceStylist;
import android.support.v17.leanback.widget.GuidedAction;
import android.support.v17.leanback.widget.GuidedActionsStylist;
import android.util.Log;

import com.sample.androidtv.R;
import com.sample.androidtv.Utils.ActionUtils;

import java.util.List;

/**
 * Created by pankaj on 5/4/16.
 */
public class FirstStepFragment extends GuidedStepFragment {

    @NonNull
    @Override
    public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
        String title = "Title";
        String breadcrumb = "Breadcrumb";
        String description = "Description";
        Drawable icon = getActivity().getDrawable(R.drawable.custom_first_step_guidance_icon);

        return new GuidanceStylist.Guidance(title, description, breadcrumb, icon);
    }

    @Override
    public void onCreateActions(@NonNull List<GuidedAction> actions, Bundle savedInstanceState) {
        ActionUtils.addAction(actions, ActionUtils.ACTION_CONTINUE, "Next Step", "Move to Second Fragment");
        ActionUtils.addAction(actions, ActionUtils.ACTION_BACK, "Go Back", "Exit Guidance");
        ActionUtils.addInfoActions(actions, ActionUtils.ACTION_EDITABLE, "info Only", "This\nShould\nHave Info in Single Line");
        ActionUtils.addCheckedAction(actions, R.drawable.ic_credit_card, getActivity(), "Credit Card", "Enter your details", true);
    }

    @Override
    public void onGuidedActionClicked(GuidedAction action) {
        super.onGuidedActionClicked(action);
        int choice = (int) action.getId();
        switch (choice) {
            case ActionUtils.ACTION_BACK:
                Log.i("TAG", "BACK");
                for (int i = 0; i < getActions().size(); i++) {
                    GuidedAction comparedAction = getActions().get(i);
                    if (comparedAction.getId() == ActionUtils.ACTION_CONTINUE) {
                        getActions().get(i).setDescription("This\n" +
                                "Should\n" +
                                "Have Info in Single Line");
                    }
                }
                break;
            case ActionUtils.ACTION_CONTINUE:
                Log.i("TAG", "OKAY");
                for (int i = 0; i < getActions().size(); i++) {
                    GuidedAction comparedAction = getActions().get(i);
                    if (comparedAction.getId() == ActionUtils.ACTION_BACK) {
                        getActions().get(i).setDescription("Its content has changed");
                        getActions().get(i).setTitle("TEst");
                        Log.i("TAG", getActions().get(i).getTitle().toString());
                        comparedAction.setEditTitle("Test");
                    }
                }

                break;
        }
    }


    @Override
    public void onGuidedActionFocused(GuidedAction action) {
        super.onGuidedActionFocused(action);
    }

    @Override
    public void onGuidedActionEdited(GuidedAction action) {
        super.onGuidedActionEdited(action);
        Log.i("TAG", action.getId() + "Edited");
    }

    @Override
    public int onProvideTheme() {
        return R.style.Theme_Example_Leanback_GuidedStep_First;
    }

    @Override
    public GuidanceStylist onCreateGuidanceStylist() {
        return new GuidanceStylist() {
            @Override
            public int onProvideLayoutId() {
                return super.onProvideLayoutId();
            }
        };
    }

    @Override
    public GuidedActionsStylist onCreateActionsStylist() {
        return new GuidedActionsStylist() {

            @Override
            public int onProvideLayoutId() {
                return super.onProvideLayoutId();
            }

            @Override
            public int onProvideItemLayoutId() {
                return super.onProvideItemLayoutId();
            }
        };
    }
}
