package com.ydw.myphone;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydw.myphone.databinding.ContactInterfaceBinding;

import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2017/7/28.
 */

public class ContactActivity extends AppCompatActivity {
    private   boolean isCanSelect = false;
    private ActionBar actionBar;
    private View view;
    private ContactInterfaceState contactInterfaceState;
    private MyGlobal myGlobal;

    @BindView(R.id.nac_root)
    FadingScrollView fadingScrollView;

    @BindView(R.id.nav_layout)
    LinearLayout nav_layout;

//    @BindView(R.id.nac_layout)
//    View layout;

    @BindView(R.id.nac_image)
    ImageView photo;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView toolbartitle;

    @BindView(R.id.place_tv)
    TextView place_tv;

    @BindView(R.id.name)
    EditText contactName;

    @BindView(R.id.phone)
    EditText contactPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("life","contactActivity onCreate");
//        setContentView(R.layout.contact_interface);
        ContactInterfaceBinding binding = DataBindingUtil.setContentView(this
                , R.layout.contact_interface);
        view = binding.getRoot();
        myGlobal = (MyGlobal)getApplication();
        contactInterfaceState = new ContactInterfaceState();
        binding.setContactInterfaceState(contactInterfaceState);
        setupWindowAnimations();
//        setUpWindowTrisience();
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//设置是否将应用程序图标转变成可点击的图标，并在图标上添加一个向左的箭头
//        actionBar = getActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);//设置是否显示应用程序图标
//        actionBar.setHomeButtonEnabled(true);//设置是否将应用程序图标变成可点击的按钮
//        actionBar.setTitle("某某");
//        actionBar.hide();
//        layout.setAlpha(0);
        toolbar.setAlpha(0);
        fadingScrollView.setFadingView(toolbar);
        fadingScrollView.setFadingHeightView(photo);
        fadingScrollView.setFadingTextview(place_tv, toolbartitle, nav_layout, toolbar.getMinimumHeight());

        ctrlInit();
    }

    private void ctrlInit(){

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        int pos = (int)bundle.get("clickPos");
        Edge edge = (Edge)bundle.get("Edge");
        if(edge.equals(Edge.left)) {
            contactInterfaceState.setContactPhotoId(R.drawable.awq);
            contactInterfaceState.setContactName(myGlobal.sContactsInfos.get(pos).getLeftContactsName());
            contactInterfaceState.setContactPhone(myGlobal.sContactsInfos.get(pos).getLeftContactsNumber());
//            photo.setImageResource(R.mipmap.ic_launcher);
//            toolbartitle.setText(contactsInfos.get(pos).getLeftContactsName());
        }
        else {
            contactInterfaceState.setContactPhotoId(R.drawable.zxc);
            contactInterfaceState.setContactName(myGlobal.sContactsInfos.get(pos).getRightContactsName());
            contactInterfaceState.setContactPhone(myGlobal.sContactsInfos.get(pos).getRightContactsNumber());
//            photo.setImageResource(R.mipmap.ic_launcher_round);
//            toolbartitle.setText(contactsInfos.get(pos).getRightContactsName());
        }
//        ViewCompat.setTransitionName(contactName, getString(R.string.contact_name));
        ViewCompat.setTransitionName(photo, getString(R.string.contact_photo));
    }

    @OnClick(R.id.nac_image)
    public void image_onClick(){
        Intent intent = new Intent(ContactActivity.this, PhotoSelectDialogActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.bottomin, R.anim.bottomout);
    }

    private void setupWindowAnimations() {
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
//        getWindow().setEnterTransition(fade);
//        Fade explode = new Fade();
//        Fade.setDuration(1000);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(500);

        getWindow().setEnterTransition(fade);
        getWindow().setSharedElementEnterTransition(changeBounds);
        getWindow().setExitTransition(fade);
    }

    private void setUpWindowTrisience() {
        TransitionSet mtransitionset=new TransitionSet();
        mtransitionset.addTransition(new ChangeBounds());
        mtransitionset.addTransition(new ChangeImageTransform());
//        mtransitionset.addTransition(new Fade());
        mtransitionset.setDuration(250);
        getWindow().setEnterTransition(mtransitionset);
        getWindow().setExitTransition(mtransitionset);
        getWindow().setSharedElementEnterTransition(mtransitionset);
        getWindow().setSharedElementExitTransition(mtransitionset);
        getWindow().setAllowEnterTransitionOverlap(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_contact, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        if(mi.isCheckable()){
            mi.setChecked(true);
        }
        switch (mi.getItemId()){
            case android.R.id.home:
//                Intent intent = new Intent(this, MainActivity.class);
//                //添加额外的FLAG，将Activity栈中处于MainActivity之上的Activity弹出
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
                ActivityCompat.finishAfterTransition(this);//兼容5.0以下的手机，运行不会出错，但是只有在5.0以上的手机才能看到共享元素回退的效果
                break;
            case R.id.plain_item:
                if(contactInterfaceState.isCanSelect()) {
//                    isCanSelect = false;
                    contactInterfaceState.setCanSelect(false);
                    contactInterfaceState.setContactName(contactName.getText().toString());
                    contactInterfaceState.setContactPhone(contactPhone.getText().toString());
                    mi.setIcon(R.mipmap.ic_launcher);
                }
                else {
//                    isCanSelect = true;
                    contactInterfaceState.setCanSelect(true);
                    mi.setIcon(R.mipmap.ic_launcher_round);
                }
//                Intent intent2 = new Intent(this, MainActivity.class);
//                //添加额外的FLAG，将Activity栈中处于MainActivity之上的Activity弹出
//                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent2);
                break;
        }
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("life","contactActivity onDestroy");
        System.gc();
    }
}
