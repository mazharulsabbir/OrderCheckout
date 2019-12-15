package tarms.dev.ordercheckout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_ITEMS = "tarms.dev.ordercheckout.EXTRA_ITEMS";

    private String[] productName = {"Eos Camera", "Nike Shoes", "SanDisk PenDrive", "Ecobee4",
            "Beats Solo3 Wireless", "Microsoft Surface Go", "Vostok Man Watch"};

    private String[] productType = {"Black", "Running Shoes", "32 GB", "Thermostat", "Black", "Intel Pentium Gold, 8GB RAM, 128GB", "Green"};

    private String[] productPrice = {"$540", "$100", "$20", "$249", "$299", "$549", "$130"};

    private int[] productImage = {R.drawable.eos_camera, R.drawable.nike_shoes, R.drawable.pendrive,
            R.drawable.smart_home_devices, R.drawable.solo3_base, R.drawable.surface_go, R.drawable.watch_man_green};

    private ArrayList<Products> selectedProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Checkout");
        }

        final List<Products> products = new ArrayList<>();

        for (int i = 0; i < productName.length; i++) {
            products.add(new Products(productName[i], productType[i], productPrice[i], productImage[i], false));
        }

        RecyclerView items = findViewById(R.id.items);
        items.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        items.setHasFixedSize(true);

        final CartAdapter adapter = new CartAdapter(getApplicationContext(), products);
        items.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(int p) {
                if (products.get(p).isSelected()) {
                    products.get(p).setSelected(false);
                } else {
                    products.get(p).setSelected(true);
                }
                adapter.notifyDataSetChanged();

                selectedProduct.clear();
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).isSelected()) {
                        Products products1 = products.get(i);
                        selectedProduct.add(products1);
                    }
                }
            }
        });

        MaterialButton checkOut = findViewById(R.id.check_out);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckOutActivity.class);
                intent.putParcelableArrayListExtra(EXTRA_SELECTED_ITEMS, selectedProduct);
                startActivity(intent);
            }
        });
    }
}
