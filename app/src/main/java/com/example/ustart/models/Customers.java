package com.example.ustart.models;

public class Customers {
    String id,name,address,shopname,email,telephone,province,district,city,joined_date,profile_pic,gooogleaddress,lat,lng;

    public Customers(){

    }

    public Customers(String id, String name, String address, String shopname, String email,
                     String telephone, String province, String district, String city,
                     String joined_date, String profile_pic, String gooogleaddress,
                     String lat, String lng) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.shopname = shopname;
        this.email = email;
        this.telephone = telephone;
        this.province = province;
        this.district = district;
        this.city = city;
        this.joined_date = joined_date;
        this.profile_pic = profile_pic;
        this.gooogleaddress = gooogleaddress;
        this.lat = lat;
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getJoined_date() {
        return joined_date;
    }

    public void setJoined_date(String joined_date) {
        this.joined_date = joined_date;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getGooogleaddress() {
        return gooogleaddress;
    }

    public void setGooogleaddress(String gooogleaddress) {
        this.gooogleaddress = gooogleaddress;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
