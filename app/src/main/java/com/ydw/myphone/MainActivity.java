package com.ydw.myphone;

import android.app.ActivityOptions;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private boolean isCanSelect = false;
    private MyGlobal myGlobal;
    private final static int REQUEST_CODE=1;
    /**获取库Phon表字段**/
    private static final String[] PHONES_PROJECTION = new String[] {
            Phone.DISPLAY_NAME, Phone.NUMBER
            , Photo.PHOTO_ID, Phone.CONTACT_ID, Phone.LAST_TIME_CONTACTED };

    /**联系人显示名称**/
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;

    /**电话号码**/
    private static final int PHONES_NUMBER_INDEX = 1;

    /**头像ID**/
    private static final int PHONES_PHOTO_ID_INDEX = 2;

    /**联系人的ID**/
    private static final int PHONES_CONTACT_ID_INDEX = 3;
    /**联系人最后通话时间**/
    private static final int PHONES_LAST_TIME_CONTACTED_INDEX = 4;

    private ContactsListAdapter mAdapter;
    private ActionBar actionBar;
//    public static ArrayList<ContactsInfo> contactsInfos = new ArrayList<ContactsInfo>(){
//        {
//            for(int i=0;i<9;i++){
//                add(new ContactsInfo("阿斯顿和健康", "按时发生"));
//            }
//            add(new ContactsInfo("俺是个撒", null));
//        }
//    };


    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("life","mainActivity onCreate");
        setContentView(R.layout.activity_main);
        myGlobal = (MyGlobal)getApplication();
        setupWindowAnimations();
        ButterKnife.bind(this);
        crtlInit();
        toolbar.setTitle("Title");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);//设置是否显示应用程序图标
        actionBar.setHomeButtonEnabled(false);//设置是否将应用程序图标变成可点击的按钮
        actionBar.setDisplayUseLogoEnabled(true);
    }

    private void crtlInit(){

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ContactsListAdapter(this, myGlobal.sContactsInfos);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Edge edge) {
                if(isCanSelect){
                    Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                    intent.putExtra("clickPos", position);
                    intent.putExtra("Edge", edge);
                    //添加额外的FLAG，将Activity栈中处于MainActivity之上的Activity弹出
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ActivityOptions transitionActivityOptions;
                    if(edge.equals(Edge.left)) {
                        transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                Pair.create(view.findViewById(R.id.textView), getString(R.string.contact_name)),
                                Pair.create(view.findViewById(R.id.leftphoto), getString(R.string.contact_photo)));

                    }
                    else {
                        transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                Pair.create(view.findViewById(R.id.textView2), getString(R.string.contact_name)),
                                Pair.create(view.findViewById(R.id.rightphoto), getString(R.string.contact_photo)));
                    }
                    startActivity(intent, transitionActivityOptions.toBundle());
                    overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
                }else{
                    Intent intent = new Intent(MainActivity.this, CallActivity.class);
                    intent.putExtra("clickPos", position);
                    intent.putExtra("Edge", edge);
                    //添加额外的FLAG，将Activity栈中处于MainActivity之上的Activity弹出
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ActivityOptions transitionActivityOptions;
                    if(edge.equals(Edge.left)) {
                        transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                Pair.create(view.findViewById(R.id.textView), getString(R.string.contact_name)),
                                Pair.create(view.findViewById(R.id.leftphoto), getString(R.string.contact_photo)));
                    }
                    else {
                        transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,
                                Pair.create(view.findViewById(R.id.textView2), getString(R.string.contact_name)),
                                Pair.create(view.findViewById(R.id.rightphoto), getString(R.string.contact_photo)));
                    }
                    startActivity(intent, transitionActivityOptions.toBundle());
//                    startActivity(intent);
                    overridePendingTransition(R.anim.out_alpha, R.anim.enter_alpha);
                }
            }
        });
        getPhoneContacts();
        mAdapter.setContactList(myGlobal.sContactsInfos);
    }

    @OnClick(R.id.toolbar)
    public void toolbar_onClick(View v){

    }

    /**
     * 动画设置
     */
    private void setupWindowAnimations() {
//        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
//        getWindow().setExitTransition(slide);
        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(fade);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mian, menu);
        MenuItem mi = menu.findItem(R.id.plain_item);
        if(isCanSelect) {
            mi.setIcon(R.mipmap.ic_launcher_round);
        }
        else {
            mi.setIcon(R.mipmap.ic_launcher);
        }
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem mi){
        if(mi.isCheckable()){
            mi.setChecked(true);
        }
        switch (mi.getItemId()){
//            case android.R.id.home:
//                Intent intent = new Intent(this, MainActivity.class);
//                //添加额外的FLAG，将Activity栈中处于MainActivity之上的Activity弹出
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                break;
            case R.id.plain_item:
                if(isCanSelect) {
                    isCanSelect = false;
                    mi.setIcon(R.mipmap.ic_launcher);
                }
                else {
                    isCanSelect = true;
                    mi.setIcon(R.mipmap.ic_launcher_round);
                }
                break;

        }
        return true;
    }

    /**得到手机通讯录联系人信息**/
    private void getPhoneContacts() {
        myGlobal.sContactsInfos.clear();
        ArrayList<ContactsInfoMid> contactsInfoMids = new ArrayList<>();

        ContentResolver resolver = getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {

                ContactsInfoMid contactsInfoMid = new ContactsInfoMid();
                /**左半边信息**/
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);//得到手机号码
                if (TextUtils.isEmpty(phoneNumber))//当手机号码为空的或者为空字段 跳过当前循环
                    continue;
                String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);//得到联系人名称
                Long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);//得到联系人ID
                Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);//得到联系人头像ID
                Long lasttimeInt = phoneCursor.getLong(PHONES_LAST_TIME_CONTACTED_INDEX);//得到最后通话时间
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d1=new Date(lasttimeInt);
                String lasttimeString=format.format(d1);

                int contactPhoto = 0;//得到联系人头像Bitamp
                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
                if(photoid > 0 ) {
                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,photoid);
                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
//                    contactPhoto = BitmapFactory.decodeStream(input);
                    contactPhoto = R.drawable.awq;
                }else {
//                    contactPhoto = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
                    contactPhoto = R.drawable.zxc;
                }
                /**将数据放入结构体**/
                contactsInfoMid.setContactsID(contactid);
                contactsInfoMid.setContactsLastTimeLong(lasttimeInt);
                contactsInfoMid.setContactsLastTimeString(lasttimeString);
                contactsInfoMid.setContactsName(contactName);
                contactsInfoMid.setContactsNumber(phoneNumber);
                contactsInfoMid.setContactsPhotoId(contactPhoto);
                contactsInfoMids.add(contactsInfoMid);
//                if(lastPos==-1) {//第一次进入
//                    contactsInfoMids.add(contactsInfoMid);
//                    lastPos = 0;
//                    currentPos++;
//                }else{
//                    if(lasttimeInt > contactsInfoMids.get(lastPos).getContactsLastTimeLong()){//这一次的时间值比上一次大
//                        while(){
//
//                        }
//                    }else{
//                        contactsInfoMids.add(contactsInfoMid);
//                    }
//                    lastPos++;
//                    currentPos++;
//                }

//                contactsInfo.setLeftContactsID(contactid);
//                contactsInfo.setLeftContactsName(contactName);
//                contactsInfo.setLeftContactsNumber(phoneNumber);
//                contactsInfo.setLeftContactsPhoto(contactPhoto);
//                contactsInfo.setLeftContactsLastTimeLong(lasttimeInt);
//                contactsInfo.setLeftContactsLastTimeString(lasttimeString);
//
//                /**右半边信息**/
//                if(!phoneCursor.moveToNext())return;
//
//                String phoneNumber2 = phoneCursor.getString(PHONES_NUMBER_INDEX);//得到手机号码
//                if (TextUtils.isEmpty(phoneNumber2)) {//当手机号码为空的或者为空字段 跳过当前循环
//                    contactsInfos.add(contactsInfo);
//                    continue;
//                }
//                String contactName2 = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);//得到联系人名称
//                Long contactid2 = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);//得到联系人ID
//                Long photoid2 = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);//得到联系人头像ID
//                Long lasttimeInt2 = phoneCursor.getLong(PHONES_LAST_TIME_CONTACTED_INDEX);//得到最后通话时间
//                SimpleDateFormat format2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date d2=new Date(lasttimeInt2);
//                String lasttimeString2=format2.format(d2);
//                Bitmap contactPhoto2 = null;//得到联系人头像Bitamp
//                //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
//                if(photoid > 0 ) {
//                    Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,photoid2);
//                    InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
//                    contactPhoto2 = BitmapFactory.decodeStream(input);
//                }else {
//                    contactPhoto2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
//                }
//
//                /**将数据放入结构体**/
//
//                contactsInfo.setRightContactsID(contactid2);
//                contactsInfo.setRightContactsName(contactName2);
//                contactsInfo.setRightContactsNumber(phoneNumber2);
//                contactsInfo.setRightContactsPhoto(contactPhoto2);
//                contactsInfo.setRightContactsLastTimeLong(lasttimeInt2);
//                contactsInfo.setRightContactsLastTimeString(lasttimeString2);
//
//                contactsInfos.add(contactsInfo);
            }
            phoneCursor.close();
//            Set<Map.Entry<ContactsInfoMid, Long>> set = (Set<Map.Entry<ContactsInfoMid, Long>>) contactsInfoMids.iterator();
            Comparator comp = new SortComparator();
            Collections.sort(contactsInfoMids, comp);
            int j=0;
            for(int i=0;i<contactsInfoMids.size();i++){
                ContactsInfo contactsInfo = new ContactsInfo();
                contactsInfo.setLeftContactsID(contactsInfoMids.get(i).getContactsID());
                contactsInfo.setLeftContactsLastTimeString(contactsInfoMids.get(i).getContactsLastTimeString());
                contactsInfo.setLeftContactsPhotoId(contactsInfoMids.get(i).getContactsPhotoId());
                contactsInfo.setLeftContactsNumber(contactsInfoMids.get(i).getContactsNumber());
                contactsInfo.setLeftContactsName(contactsInfoMids.get(i).getContactsName());
                contactsInfo.setLeftContactsLastTimeLong(contactsInfoMids.get(i).getContactsLastTimeLong());
                if((++i)>=contactsInfoMids.size()) {
                    myGlobal.sContactsInfos.add(contactsInfo);
                    return;
                }
                contactsInfo.setRightContactsID(contactsInfoMids.get(i).getContactsID());
                contactsInfo.setRightContactsPhotoId(contactsInfoMids.get(i).getContactsPhotoId());
                contactsInfo.setRightContactsLastTimeString(contactsInfoMids.get(i).getContactsLastTimeString());
                contactsInfo.setRightContactsNumber(contactsInfoMids.get(i).getContactsNumber());
                contactsInfo.setRightContactsName(contactsInfoMids.get(i).getContactsName());
                contactsInfo.setRightContactsLastTimeLong(contactsInfoMids.get(i).getContactsLastTimeLong());
                myGlobal.sContactsInfos.add(contactsInfo);
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("life","mainActivity onDestroy");
    }
}
