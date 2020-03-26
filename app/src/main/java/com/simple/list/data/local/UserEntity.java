package com.simple.list.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.simple.list.utilities.Constants;

@Entity(tableName = Constants.TABLE_USER)
public class UserEntity {

  @PrimaryKey
  @ColumnInfo(name = Constants.TABLE_USER_ID)
  private long id;

  @ColumnInfo(name = "name")
  private String name;

  @ColumnInfo(name = "username")
  private String username;

  @ColumnInfo(name = "phone")
  private String phone;

  @ColumnInfo(name = "email")
  private String email;

  @ColumnInfo(name = "website")
  private String website;

  @ColumnInfo(name = "address")
  private String address;

  @ColumnInfo(name = "companyName")
  private String companyName;

  public UserEntity(long id, String name, String username, String phone, String email,
      String website, String address, String companyName) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.phone = phone;
    this.email = email;
    this.website = website;
    this.address = address;
    this.companyName = companyName;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUsername() {
    return username;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public String getWebsite() {
    return website;
  }

  public String getAddress() {
    return address;
  }

  public String getCompanyName() {
    return companyName;
  }
}
