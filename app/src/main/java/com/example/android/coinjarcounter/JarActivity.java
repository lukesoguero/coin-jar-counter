package com.example.android.coinjarcounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.List;

public class JarActivity extends AppCompatActivity {

    public double total = 0.00;
    public static final String MODIFIED_TOTAL = "Modified Total";
    public long jarId = 0;
    public int jarCode;
    public String jarName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jar_activity);

        ImageView oneCent = (ImageView) findViewById(R.id.one_cent);
        oneCent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total += 0.01;
                displayTotal(total);
            }
        });

        ImageView fiveCents = (ImageView) findViewById(R.id.five_cents);
        fiveCents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total += 0.05;
                displayTotal(total);
            }
        });

        ImageView tenCents = (ImageView) findViewById(R.id.ten_cents);
        tenCents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total += 0.10;
                displayTotal(total);
            }
        });

        ImageView twentyFiveCents = (ImageView) findViewById(R.id.twenty_five_cents);
        twentyFiveCents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total += 0.25;
                displayTotal(total);
            }
        });

        ImageView fiftyCents = (ImageView) findViewById(R.id.fifty_cents);
        fiftyCents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total += 0.50;
                displayTotal(total);
            }
        });

        ImageView oneDollar = (ImageView) findViewById(R.id.one_dollar);
        oneDollar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total += 1.00;
                displayTotal(total);
            }
        });

        Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            AlertDialog.Builder alert = new AlertDialog.Builder(getBaseContext());

            alert.setTitle("Rename");

            // Set an EditText view to get user input

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {



                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();
            public void onClick(View v) {
                total = 0.00;
                displayTotal(total);
            }
        });

        Button jars = (Button) findViewById(R.id.jars);
        jars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = this.getIntent();
        jarId = intent.getExtras().getLong(MainActivity.JAR_ID_EXTRA);

        jarCode = intent.getExtras().getInt(MainActivity.JAR_CODE_EXTRA);

        double newTotal = intent.getExtras().getDouble(MainActivity.JAR_TOTAL_EXTRA);
        displayTotal(newTotal);
        total = newTotal;

        jarName = intent.getExtras().getString(MainActivity.JAR_NAME_EXTRA);
        setTitle(jarName);

    }

    private void displayTotal(double total) {
        TextView totalTextView = (TextView) findViewById(R.id.total);
        totalTextView.setText(NumberFormat.getCurrencyInstance().format(total));
    }

    @Override
    protected void onPause() {
        super.onPause();
        JarDbAdapter dbAdapter = new JarDbAdapter(this);
        dbAdapter.open();
        if (jarCode == 0) {
            dbAdapter.createJar(jarName, total);
        } else {
            dbAdapter.updateJar(jarId, total);
        }
        dbAdapter.close();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
