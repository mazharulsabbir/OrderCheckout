package tarms.dev.ordercheckout;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        RecyclerView recyclerView = findViewById(R.id.selected_list);

        Intent intent = getIntent();

        if (intent.getParcelableArrayListExtra(MainActivity.EXTRA_SELECTED_ITEMS) != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            ArrayList<Products> products = intent.getParcelableArrayListExtra(MainActivity.EXTRA_SELECTED_ITEMS);

            List<Products> productsList = products;

            CartAdapter adapter = new CartAdapter(getApplicationContext(), productsList);

            recyclerView.setAdapter(adapter);
        }
    }
}
