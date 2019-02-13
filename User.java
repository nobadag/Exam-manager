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

  public int getExamsize() {
    return examsList.size();
  }
}

class Exam implements Serializable {
  static final long serialVersionUID = 1L;
  private String name;
  private ArrayList<String> usesubs = new ArrayList<>();
  private HashMap<String, Subject> subsdata = new HashMap<String, Subject>();
  private int total = 0;
  private float ave = 0;

  Exam(String tmp) {
    name = tmp;
  }

  public String getName() {
    return name;
  }

  public void addData(String key, Subject tmp) {
    subsdata.put(key, tmp);
    usesubs.add(key);
  }

  public void addDataAll(HashMap<String, Subject> tmp1, ArrayList<String> tmp2) {
    subsdata.putAll(tmp1);
    usesubs.addAll(tmp2);
  }

  public Subject getSubData(String key) {
    return subsdata.get(key);
  }

  public HashMap<String, Subject> getDataAll() {
    return subsdata;
  }

  public String getSubName(int tmp) {
    return usesubs.get(tmp);
  }

  public ArrayList<String> getSubNameAll() {
    return usesubs;
  }

  public int getSubsize() {
    return subsdata.size();
  }

  public int getTotal() {
    total = 0;
    for (int i = 0; i < subsdata.size(); i++) {
      total += subsdata.get(usesubs.get(i)).getScore();
    }
    return total;
  }

  public float getAverage() {
    ave = (float) this.getTotal() / (float) subsdata.size();
    return ave;
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
