package org.kapildev333.ecommerce.models;


import org.kapildev333.ecommerce.features.address.ShippingAddress;

public class ShippingAddressRequestModel {
    private String type;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Double latitude;

    private Double longitude;

    public ShippingAddressRequestModel() {
    }

    public ShippingAddressRequestModel(String type, String street, String city, String state, String postalCode, String country) {
        this.type = type;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public static ShippingAddress getShippingAddress(ShippingAddressRequestModel shippingAddressRequestModel){
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setType(shippingAddressRequestModel.getType());
        shippingAddress.setStreet(shippingAddressRequestModel.getStreet());
        shippingAddress.setCity(shippingAddressRequestModel.getCity());
        shippingAddress.setState(shippingAddressRequestModel.getState());
        shippingAddress.setPostalCode(shippingAddressRequestModel.getPostalCode());
        shippingAddress.setCountry(shippingAddressRequestModel.getCountry());
        shippingAddress.setLatitude(shippingAddressRequestModel.getLatitude());
        shippingAddress.setLongitude(shippingAddressRequestModel.getLongitude());
        return shippingAddress;
    }
}
