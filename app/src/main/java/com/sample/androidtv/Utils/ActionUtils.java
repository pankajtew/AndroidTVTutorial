package com.sample.androidtv.Utils;

import android.content.Context;
import android.support.v17.leanback.widget.GuidedAction;

import java.util.List;

/**
 * Created by pankaj on 5/4/16.
 */
public class ActionUtils {

    /* Action ID definition */
    public static final int ACTION_CONTINUE = 0;
    public static final int ACTION_BACK = 1;
    public static final int ACTION_EDITABLE = 2;

    public static void addAction(List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .editable(true)
                .build());
    }

    public static void addInfoActions(List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .infoOnly(true)
                .enabled(false)
                .editable(true)
                .multilineDescription(true)
                .build());
    }

    public static void addCheckedAction(List<GuidedAction> actions, int iconResId, Context context,
                                        String title, String desc, boolean checked) {
        GuidedAction guidedAction = new GuidedAction.Builder()
                .title(title)
                .description(desc)
                .checkSetId(1)
                .iconResourceId(iconResId, context)
                .hasNext(true)
                .build();
        guidedAction.setChecked(checked);
        actions.add(guidedAction);
    }

}
