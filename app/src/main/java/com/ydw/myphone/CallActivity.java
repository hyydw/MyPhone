package com.ydw.myphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/8/3.
 */

public class CallActivity extends AppCompatActivity {
    private MyGlobal myGlobal;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.toolbar_title)
    TextView toolbartitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("life","callActivity onCreate");
        setContentView(R.layout.call_interface);
        myGlobal = (MyGlobal)getApplication();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        crtlInit();
    }

    private void crtlInit(){
        toolbar.setAlpha((float)(120.0/255.0));
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int pos = (int)bundle.get("clickPos");
        Edge edge = (Edge)bundle.get("Edge");
        if(edge.equals(Edge.left)) {
            toolbartitle.setText(myGlobal.sContactsInfos.get(pos).getLeftContactsName());
        } else {
            toolbartitle.setText(myGlobal.sContactsInfos.get(pos).getRightContactsName());
        }
        ViewCompat.setTransitionName(toolbartitle, getString(R.string.contact_name));
        ViewCompat.setTransitionName(photo, getString(R.string.contact_photo));
    }

    @OnClick(R.id.button)
    public void Call_onClick(){

    }

    @OnClick(R.id.button1)
    public void Cancel_onClick(){
//        finish();
        ActivityCompat.finishAfterTransition(this);//兼容5.0以下的手机，运行不会出错，但是只有在5.0以上的手机才能看到共享元素回退的效果
//        Intent intent = new Intent(this, MainActivity.class);
//        //添加额外的FLAG，将Activity栈中处于MainActivity之上的Activity弹出
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//        overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mian, menu);
        MenuItem mi = menu.findItem(R.id.plain_item);
        mi.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        if(mi.isCheckable()){
            mi.setChecked(true);
        }
        switch (mi.getItemId()){
            case R.id.plain_item:
                break;

        }
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("life","calActivity onDestroy");
        System.gc();
    }
}
