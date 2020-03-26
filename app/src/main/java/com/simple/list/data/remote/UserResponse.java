package com.simple.list.data.remote;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

  @SerializedName("company") private Company company;
  @SerializedName("website") private String website;
  @SerializedName("phone") private String phone;
  @SerializedName("address") private Address address;
  @SerializedName("email") private String email;
  @SerializedName("username") private String username;
  @SerializedName("name") private String name;
  @SerializedName("id") private int id;

  public Company getCompany() {
    return company;
  }

  public String getWebsite() {
    return website;
  }

  public String getPhone() {
    return phone;
  }

  public Address getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static class Company {

    @SerializedName("bs") private String bs;
    @SerializedName("catchPhrase") private String catchphrase;
    @SerializedName("name") private String name;

    public String getBs() {
      return bs;
    }

    public String getCatchphrase() {
      return catchphrase;
    }

    public String getName() {
      return name;
    }
  }

  public static class Address {

    @SerializedName("geo") private Geo geo;
    @SerializedName("zipcode") private String zipcode;
    @SerializedName("city") private String city;
    @SerializedName("suite") private String suite;
    @SerializedName("street") private String street;

    public Geo getGeo() {
      return geo;
    }

    public String getZipcode() {
      return zipcode;
    }

    public String getCity() {
      return city;
    }

    public String getSuite() {
      return suite;
    }

    public String getStreet() {
      return street;
    }
  }

  public static class Geo {

    @SerializedName("lng") private String lng;
    @SerializedName("lat") private String lat;

    public String getLng() {
      return lng;
    }

    public String getLat() {
      return lat;
    }
  }
}
