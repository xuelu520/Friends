package makyu.friends;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import makyu.friends.beans.Contact;

public class DetailActivity extends AppCompatActivity {
    Contact contact;
    TextView nameTv;
    TextView phoneTv;

    FloatingActionButton call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Long id = intent.getLongExtra("contactId", 0L);
        String name = intent.getStringExtra("contactName");
        String phone = intent.getStringExtra("contactPhone");

        contact = new Contact(id, name, phone);
        nameTv.setText(name);
        phoneTv.setText(phone);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用intent启动拨打电话
                Uri uri=Uri.parse("tel:10010");
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(uri);
                DetailActivity.this.startActivity(intent);
            }
        });
    }

    private void initView() {
        this.nameTv = (TextView) findViewById(R.id.nameTv);
        this.phoneTv = (TextView) findViewById(R.id.phoneTv);
        call = (FloatingActionButton) findViewById(R.id.call);
    }

    private String clearPhoneNum(String num) {
        num = num.replaceAll("\\+", "");
        return  "";
    }
}
