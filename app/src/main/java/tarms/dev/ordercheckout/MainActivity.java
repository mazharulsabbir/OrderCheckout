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
                intent.putParcelableArrayListExtra("SELECTED_ITEMS", selectedProduct);
            }
        });


    }

    private class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductHolder> {

        private OnItemClickListener onItemClickListener;

        private Context context;
        private List<Products> products;

        public CartAdapter(Context context, List<Products> products) {
            this.context = context;
            this.products = products;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.cart_item, parent, false
            );

            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            Products product = products.get(position);

            holder.pName.setText(product.getName());
            holder.pType.setText(product.getType());
            holder.pPrice.setText(product.getPrice());

            Glide.with(context).load(product.getImage()).into(holder.pImage);

            if (product.isSelected()) {
                holder.root.setCardBackgroundColor(Color.parseColor("#cccccc"));
            } else holder.root.setCardBackgroundColor(Color.parseColor("#ffffff"));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        class ProductHolder extends RecyclerView.ViewHolder {

            private ImageView pImage;
            private TextView pName, pType, pPrice;

            private CardView root;

            public ProductHolder(@NonNull View itemView) {
                super(itemView);

                pImage = itemView.findViewById(R.id.p_image);

                pName = itemView.findViewById(R.id.p_name);
                pType = itemView.findViewById(R.id.p_type);
                pPrice = itemView.findViewById(R.id.p_price);

                root = itemView.findViewById(R.id.root);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            if (onItemClickListener != null) {
                                onItemClickListener.itemClick(getAdapterPosition());
                            }
                        }
                    }
                });
            }
        }
    }
}
