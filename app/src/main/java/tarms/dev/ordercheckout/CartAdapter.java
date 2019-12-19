package tarms.dev.ordercheckout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductHolder> {

    private OnItemClickListener onItemClickListener;

    private Context context;
    private List<Products> products;

    private String selectedItemColor;
    private String unSelectedItemColor;

    public CartAdapter(Context context, List<Products> products, String selectedItemColor, String unSelectedItemColor) {
        this.context = context;
        this.products = products;
        this.selectedItemColor = selectedItemColor;
        this.unSelectedItemColor = unSelectedItemColor;
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
            holder.root.setCardBackgroundColor(Color.parseColor(selectedItemColor));
        } else holder.root.setCardBackgroundColor(Color.parseColor(unSelectedItemColor));
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
