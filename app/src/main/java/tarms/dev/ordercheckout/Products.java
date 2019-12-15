package tarms.dev.ordercheckout;

import android.os.Parcel;
import android.os.Parcelable;

public class Products implements Parcelable {
    private String name, type, price;
    private int image;
    private boolean isSelected;

    public Products(String name, String type, String price, int image, boolean isSelected) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.image = image;
        this.isSelected = isSelected;
    }

    protected Products(Parcel in) {
        name = in.readString();
        type = in.readString();
        price = in.readString();
        image = in.readInt();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(price);
        parcel.writeInt(image);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
