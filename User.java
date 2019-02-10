import java.io.*;
import java.util.*;

class User implements Serializable {
  static final long serialVersionUID = 1L;
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

  public void addExam(Exam tmp) {
    examsList.add(tmp);
  }

  public Exam getExam(int tmp) {
    return examsList.get(tmp);
  }

  public ArrayList<Exam> getExamAll() {
    return examsList;
  }
}

class Exam implements Serializable {
  static final long serialVersionUID = 1L;
  private String name;
  private ArrayList<String> usesubs;
  private HashMap<String, Subject> subsdata = new HashMap<String, Subject>();

  Exam(String tmp) {
    name = tmp;
  }

  public String getName() {
    return name;
  }

  public void setData(String key, Subject tmp) {
    subsdata.put(key, tmp);
    usesubs.add(key);
  }

  public void setDataAll(HashMap<String, Subject> tmp1, ArrayList<String> tmp2) {
    subsdata.putAll(tmp1);
    usesubs.addAll(tmp2);
  }

  public Subject getData(String key) {
    return subsdata.get(key);
  }

  public HashMap<String, Subject> getDataAll() {
    return subsdata;
  }

  public String getSub(int tmp) {
    return usesubs.get(tmp);
  }

  public ArrayList<String> getSubAll() {
    return usesubs;
  }
}

class Subject implements Serializable {
  static final long serialVersionUID = 1L;
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
