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
  private String name;
  private ArrayList<Integer> scores = new ArrayList<>();

  Subject(String tmp) {
    name = tmp;
  }

  void addScore(int tmp) {
    scores.add(tmp);
  }

  String getName() {
    return name;
  }
}
