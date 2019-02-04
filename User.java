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

class Exams {
  private String name;
  private HashMap<String, Subject> subsdata = new HashMap<String, Subject>();

  Exams(String tmp) {
    name = tmp;
  }

  public String getName() {
    return name;
  }

  public void setData(HashMap<String, Subject> tmp) {
    subsdata.putAll(tmp);
  }

  public HashMap<String, Subject> getData() {
    return subsdata;
  }
}

class Subject {
  private String name;
  private int score;

  Subject(String tmp) {
    name = tmp;
  }

  public void setScore(int tmp) {
    score = tmp;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }
}
