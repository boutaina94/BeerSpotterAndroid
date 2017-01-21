package fr.project.isep.beerspotter;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Boutaina on 04/12/2016.
 */

public class Places{

    private String name;
    private Double price;
    private Double latitude;
    private Double longitude;
    private static final long serialVersionUID = 1L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Places(String name, Double price,Double latitude, Double longitude)
    {
        this.name=name;
        this.price=price;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

}
