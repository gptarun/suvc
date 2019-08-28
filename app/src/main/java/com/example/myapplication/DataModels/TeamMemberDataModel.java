package com.example.myapplication.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamMemberDataModel {

  @SerializedName("ID")
  @Expose
  private String iD;
  @SerializedName("First_Name")
  @Expose
  private String firstName;
  @SerializedName("Last_Name")
  @Expose
  private String lastName;
  @SerializedName("user_id")
  @Expose
  private String userId;
  @SerializedName("Password")
  @Expose
  private String password;
  @SerializedName("Contact")
  @Expose
  private String contact;
  @SerializedName("email")
  @Expose
  private String email;
  @SerializedName("Team_ID")
  @Expose
  private String teamID;
  @SerializedName("Role")
  @Expose
  private String role;
  @SerializedName("College_Name")
  @Expose
  private String collegeName;
  @SerializedName("CreatedDateTime")
  @Expose
  private String createdDateTime;

  public String getID() {
    return iD;
  }

  public void setID(String iD) {
    this.iD = iD;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTeamID() {
    return teamID;
  }

  public void setTeamID(String teamID) {
    this.teamID = teamID;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getCollegeName() {
    return collegeName;
  }

  public void setCollegeName(String collegeName) {
    this.collegeName = collegeName;
  }

  public String getCreatedDateTime() {
    return createdDateTime;
  }

  public void setCreatedDateTime(String createdDateTime) {
    this.createdDateTime = createdDateTime;
  }

}
