package tarms.dev.ordercheckout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {

    private double price = 0.0;
    private NumberFormat numberFormat = new DecimalFormat("##,##,###.##");

    private TextView totalPrice;

    private double aDouble = 0.0;

    private String currency = "$";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Checkout");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        totalPrice = findViewById(R.id.total_price);

        RecyclerView recyclerView = findViewById(R.id.selected_list);

        Intent intent = getIntent();

        if (intent.getParcelableArrayListExtra(MainActivity.EXTRA_SELECTED_ITEMS) != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            final ArrayList<Products> products = intent.getParcelableArrayListExtra(MainActivity.EXTRA_SELECTED_ITEMS);

            CartAdapter adapter = new CartAdapter(getApplicationContext(), products, "#ffffff", "#ffffff");

            recyclerView.setAdapter(adapter);

            for (int i = 0; i < products.size(); i++) {
                Products p = products.get(i);

                price += Double.parseDouble(p.getPrice().replace("$", ""));
            }

            aDouble = price;

            totalPrice.setText("$ " + price);

            Switch switchPrice = findViewById(R.id.price_in_bdt);
            switchPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        double p = price * 84.0;
                        aDouble = p;

                        totalPrice.setText("৳ " + numberFormat.format(p));
                        currency = "৳";
                    } else {
                        price = 0.0;
                        for (int i = 0; i < products.size(); i++) {
                            Products p = products.get(i);

                            price += Double.parseDouble(p.getPrice().replace("$", ""));
                        }

                        aDouble = price;
                        currency = "$";

                        totalPrice.setText("$ " + numberFormat.format(price));
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("IntentReset")
    public void confirmOrderByMessage(View view) {
        String msg = "Hello! Dear Customer," +
                "\n\n" +
                "Thank you for your order. Your order no #xyz. Total cost is : " + aDouble + " " + currency;

//        SmsManager smsManager = SmsManager.getDefault();
//        smsManager.sendTextMessage("+8801825632294", null, msg, null, null);

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", msg);
        sendIntent.putExtra("address", "+8801825632294");
        sendIntent.setData(Uri.parse("smsto:"+"+8801825632294"));
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);
    }

    public void confirmOrderByCall(View view) {
        String number = "01825632294";
        Uri call = Uri.parse("tel:" + number);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }
}
