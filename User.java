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
  private ArrayList<Subject> subsdata = new ArrayList<>();

  public void setName(String tmp) {
    name = tmp;
  }

  public String getName() {
    return name;
  }

  public void setData(ArrayList<Subject> tmp) {
    subsdata.addAll(tmp);
  }

  public ArrayList<Subject> getData() {
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
