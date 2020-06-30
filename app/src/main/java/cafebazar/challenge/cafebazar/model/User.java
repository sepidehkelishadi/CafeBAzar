package cafebazar.challenge.cafebazar.model;

import com.google.gson.annotations.SerializedName;

public class User {


  @SerializedName("token")
  String token;

  @SerializedName("postid")
  String postid;

  public User(String token, String postid) {
    this.token = token;
    this.postid = postid;
  }
}