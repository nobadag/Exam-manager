import java.util.*;

class User {
  private String name;
  private String password;

  public void setName(String tmp) {
    name = tmp;
  }

  public void setPassword(String tmp) {
    password = tmp;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }
}

class Subject {
  public String name;
  private ArrayList<Integer> scores = new ArrayList<>();

  void addScore(int tmp) {
    scores.add(tmp);
  }
}
