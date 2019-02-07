import java.util.*;

class User {
  private String name;
  private String password;
  private ArrayList<Exam> examsList = new ArrayList<>();

  User(String tmp1, String tmp2) {
    name = tmp1;
    password = tmp2;
  }

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

  public void addExam(ArrayList<Exam> tmp) {
    examsList.addAll(tmp);
  }

  public Exam getExam(int tmp) {
    return examsList.get(tmp);
  }

  public ArrayList<Exam> getExamAll() {
    return examsList;
  }
}

class Exam {
  private String name;
  private HashMap<String, Subject> subsdata = new HashMap<String, Subject>();

  Exam(String tmp) {
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
