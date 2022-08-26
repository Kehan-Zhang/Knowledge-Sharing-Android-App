package com.example.graduation.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.graduation.Entity.User;
import com.example.graduation.R;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class VerifyPhoneActivity extends AppCompatActivity {

    private EditText phone_number, message;
    private Button confirm_register, get_message, private_policy;
    private RadioButton confirm_agreement;
    private String phoneNumber, code;
    private boolean phoneExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);
        Bmob.initialize(this, "bca7c117369ec7149a5a2fef8e2641a8");

        phone_number = this.findViewById(R.id.et_phone_number);
        message = this.findViewById(R.id.et_message);
        confirm_agreement = this.findViewById(R.id.rbt_comfirm_agreement);

        confirm_register = this.findViewById(R.id.bt_register_next);
        confirm_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                code = message.getText().toString();

                if ( !confirm_agreement.isChecked()) {
                    Toast.makeText(VerifyPhoneActivity.this, "请阅读并同意用户隐私协议", Toast.LENGTH_SHORT).show();
                } else {
                    BmobUser.signOrLoginByMobilePhone(phoneNumber, code, new LogInListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                Toast.makeText(VerifyPhoneActivity.this, "手机验证成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(VerifyPhoneActivity.this, SetPasswordActivity.class);
                                intent.putExtra("phoneNumber",phoneNumber);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(VerifyPhoneActivity.this, "验证码错误，请重新输入", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        get_message = this.findViewById(R.id.bt_get_message);
        get_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = phone_number.getText().toString();
                BmobQuery<User> bmobQuery = new BmobQuery<User>();
                bmobQuery.addWhereEqualTo("mobilePhoneNumber", phoneNumber);
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> object, BmobException e) {
                        if(e == null){
                            if (object.size() != 0){
                                phoneExist = true;
                            }
                            else {
                                phoneExist = false;
                            }
                            if (phoneNumber.equals("")) {
                                Toast.makeText(VerifyPhoneActivity.this,"手机号不能为空",Toast.LENGTH_SHORT).show();
                            }else if (phoneExist) {
                                Toast.makeText(VerifyPhoneActivity.this, "手机号已存在", Toast.LENGTH_SHORT).show();
                            } else{
                                BmobSMS.requestSMSCode(phoneNumber, "知否问答", new QueryListener<Integer>() {
                                    @Override
                                    public void done(Integer smsId, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(VerifyPhoneActivity.this,"验证码发送成功" ,Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(VerifyPhoneActivity.this, "验证码发送失败", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            Log.i("bmob","查询失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });
            }
        });

        private_policy = this.findViewById(R.id.bt_private_policy);
        private_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextView textView=new TextView(VerifyPhoneActivity.this);
                textView.setText(
                        "本应用尊重并保护所有使用服务用户的个人隐私权。为了给您提供更准确、更有个性化的服务，本应用会按照本隐私权政策的规定使用和披露您的个人信息。但本应用将以高度的勤勉、审慎义务对待这些信息。除本隐私权政策另有规定外，在未征得您事先许可的情况下，本应用不会将这些信息对外披露或向第三方提供。本应用会不时更新本隐私权政策。 您在同意本应用服务使用协议之时，即视为您已经同意本隐私权政策全部内容。本隐私权政策属于本应用服务使用协议不可分割的一部分。\n" +
                        "适用范围：\n" +
                        "a) 在您注册本应用帐号时，您根据本应用要求提供的个人注册信息；\n" +
                        "b) 在您使用本应用网络服务，或访问本应用平台网页时，本应用自动接收并记录的您手机上的信息，包括但不限于您的IP地址、浏览器的类型、使用的语言、访问日期和时间、软硬件特征信息及您需求的网页记录等数据；\n" +
                        "c) 本应用通过合法途径从商业伙伴处取得的用户个人数据。\n" +
                        "您了解并同意，以下信息不适用本隐私权政策：\n" +
                        "a) 您在使用本应用平台提供的搜索服务时输入的关键字信息；\n" +
                        "b) 本应用收集到的您在本应用发布的有关信息数据，包括但不限于参与活动、成交信息及评价详情；\n" +
                        "c) 违反法律规定或违反本应用规则行为及本应用已对您采取的措施。\n" +
                        "信息使用：\n" +
                        "a) 本应用不会向任何无关第三方提供、出售、出租、分享或交易您的个人信息，除非事先得到您的许可，或该第三方和本应用（含本应用关联公司）单独或共同为您提供服务，且在该服务结束后，其将被禁止访问包括其以前能够访问的所有这些资料。\n" +
                        "b) 本应用亦不允许任何第三方以任何手段收集、编辑、出售或者无偿传播您的个人信息。任何本应用平台用户如从事上述活动，一经发现，本应用有权立即终止与该用户的服务协议。\n" +
                        "c) 为服务用户的目的，本应用可能通过使用您的个人信息，向您提供您感兴趣的信息，包括但不限于向您发出产品和服务信息，或者与本应用合作伙伴共享信息以便他们向您发送有关其产品和服务的信息（后者需要您的事先同意）。\n" +
                        "信息披露：\n" +
                        "在如下情况下，本应用将依据您的个人意愿或法律的规定全部或部分的披露您的个人信息：\n" +
                        "a) 经您事先同意，向第三方披露；\n" +
                        "b) 为提供您所要求的产品和服务，而必须和第三方分享您的个人信息；\n" +
                        "c) 根据法律的有关规定，或者行政或司法机构的要求，向第三方或者行政、司法机构披露；\n" +
                        "d) 如您出现违反中国有关法律、法规或者本应用服务协议或相关规则的情况，需要向第三方披露；\n" +
                        "e) 如您是适格的知识产权投诉人并已提起投诉，应被投诉人要求，向被投诉人披露，以便双方处理可能的权利纠纷；\n" +
                        "f) 在本应用平台上创建的某一交易中，如交易任何一方履行或部分履行了交易义务并提出信息披露请求的，本应用有权决定向该用户提供其交易对方的联络方式等必要信息，以促成交易的完成或纠纷的解决。\n" +
                        "g) 其它本应用根据法律、法规或者网站政策认为合适的披露。\n" +
                        "信息存储和交换：\n" +
                        "本应用收集的有关您的信息和资料将保存在本应用及（或）其关联公司的服务器上，这些信息和资料可能传送至您所在国家、地区或本应用收集信息和资料所在地的境外并在境外被访问、存储和展示。\n" +
                        "信息安全：\n" +
                        "a) 本应用帐号均有安全保护功能，请妥善保管您的用户名及密码信息。本应用将通过对用户密码进行加密等安全措施确保您的信息不丢失，不被滥用和变造。尽管有前述安全措施，但同时也请您注意在信息网络上不存在“完善的安全措施”。\n" +
                        "b) 在使用本应用网络服务进行网上交易时，您不可避免的要向交易对方或潜在的交易对方披露自己的个人信息，如联络方式或者邮政地址。请您妥善保护自己的个人信息，仅在必要的情形下向他人提供。如您发现自己的个人信息泄密，尤其是本应用用户名及密码发生泄露，请您立即联络本应用客服，以便本应用采取相应措施。\n" +
                        "\n" +
                        "\n" );
                textView.setMovementMethod(new ScrollingMovementMethod());
                AlertDialog.Builder builder = new AlertDialog.Builder(VerifyPhoneActivity.this);
                builder.setTitle("个人隐私协议").setView(textView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
    }


}
