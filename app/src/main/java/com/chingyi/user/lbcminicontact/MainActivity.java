package com.chingyi.user.lbcminicontact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView card1;
    private CardView card2;
    private CardView card3;
    private CardView card4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card1 = (CardView) findViewById(R.id.cardView1);
        card2 = (CardView) findViewById(R.id.cardView2);
        card3 = (CardView) findViewById(R.id.cardView3);
        card4 = (CardView) findViewById(R.id.cardView4);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.cardView1:
                Toast.makeText(MainActivity.this,"LBC CHUNGTEL",Toast.LENGTH_SHORT).show();
                Intent IntentChungTel = new Intent(MainActivity.this,ChungTelActivity.class);
                startActivity(IntentChungTel);
               break;

            case R.id.cardView2:
                Toast.makeText(MainActivity.this,"LBC Mino",Toast.LENGTH_SHORT).show();
                Intent IntentOfficers = new Intent(MainActivity.this,MinoActivity.class);
                startActivity(IntentOfficers);
               break;


            case R.id.cardView3:
                Toast.makeText(MainActivity.this,"PSTOR LAE OFFICER PAWL",Toast.LENGTH_SHORT).show();
                Intent IntentMino = new Intent(MainActivity.this,PastorActivity.class);
                startActivity(IntentMino);
              break;


            case R.id.cardView4:
                Toast.makeText(MainActivity.this,"Mandalay Um Lai Mi",Toast.LENGTH_SHORT).show();
                Intent IntentADangLai = new Intent(MainActivity.this,ADangContact.class);
                startActivity(IntentADangLai);
                break;


        }
    }
}
