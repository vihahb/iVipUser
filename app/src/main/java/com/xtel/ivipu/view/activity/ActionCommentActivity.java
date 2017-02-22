package com.xtel.ivipu.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.xtel.ivipu.R;

/**
 * Created by vivhp on 2/20/2017.
 */

public class ActionCommentActivity extends BasicActivity {

    int layoutId = R.layout.action_comment_activity;
    int id_toolbar = R.id.toolbar_comment_action;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
        initToolbar(id_toolbar, this.getString(R.string.activity_comment));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_send_comment) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comment_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
