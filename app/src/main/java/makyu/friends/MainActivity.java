package makyu.friends;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import makyu.friends.adapter.ContactListAdapter;
import makyu.friends.beans.Contact;

public class MainActivity extends AppCompatActivity{
    private static final int GET_CONTACT = 0x00F;
    Contact contact;
    List<Contact> contactList = new ArrayList<>();
    ListView contactsListView;
    ContactListAdapter mAdapter;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initPopWindow();
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
            }
        });

        initView();
        initData();
    }

    private void initData() {
        contacts();
    }

    private void initView() {
        contactsListView = (ListView) findViewById(R.id.contactsListView);
    }

    // 读取联系人信息
    public  List getPhoneContacts() {
        ContentResolver cr = this.getContentResolver();
        String str[] = { Phone.CONTACT_ID, Phone.DISPLAY_NAME, Phone.NUMBER,
                Phone.PHOTO_ID };
        Cursor cur = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, str, null,
                null, null);

        if (cur != null) {
            while (cur.moveToNext()) {
                contact = new Contact();
                contact.setPhone(cur.getString(cur
                        .getColumnIndex(Phone.NUMBER)));// 得到手机号码
                contact.setName(cur.getString(cur
                        .getColumnIndex(Phone.DISPLAY_NAME)));
                contact.setId(cur.getLong(cur
                        .getColumnIndex(Phone.CONTACT_ID)));
                contactList.add(contact);
            }
        }
        return contactList;
    }


    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case GET_CONTACT:
                    initListView();
                    break;
            }
        }
    };

    private void initListView() {
        contactList = getPhoneContacts();
        //渲染listView
        //为数据绑定适配器
        mAdapter = new ContactListAdapter(MainActivity.this,contactList);
        contactsListView.setAdapter(mAdapter);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DetailActivity.class);
                Contact contact = contactList.get(position);
                intent.putExtra("contactId", contact.getId());
                intent.putExtra("contactName", contact.getName());
                intent.putExtra("contactPhone", contact.getPhone());
                startActivity(intent);

            }
        });
    }

    public void contacts() {
        handler.sendEmptyMessage(GET_CONTACT);
    }

    private void initPopWindow() {
        // 获取自定义布局文件add_contact.xml的视图
        View popView = getLayoutInflater().inflate(R.layout.add_contact, null, false);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, 600, true);
        //解决输入法盖住弹出框
        //popupWindow 自动顶上去不后悔
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popView.findViewById(R.id.cancel_action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

        // 点击其他地方消失
        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopWindow();
        }
    }
}
