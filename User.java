import java.io.*;
import java.util.*;

class User implements Serializable {
  static final long serialVersionUID = 1L;
  private String name;
  private String password;
  private ArrayList<Exam> examsList = new ArrayList<>();

  User(String n, String p) {
    name = n;
    password = p;
  }

  public void setName(String n) {
    name = n;
  }

  public void setPassword(String p) {
    password = p;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public void addExam(Exam exam) {
    examsList.add(exam);
  }

  public Exam getExam(int value) {
    return examsList.get(value);
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
  private Calendar cal = Calendar.getInstance();

  Exam(String n) {
    name = n;
  }

  public String getName() {
    return name;
  }

  public void addData(String key, Subject data) {
    subsdata.put(key, data);
    usesubs.add(key);
  }

  public void addDataAll(HashMap<String, Subject> datas, ArrayList<String> names) {
    subsdata.putAll(datas);
    usesubs.addAll(names);
  }

  public Subject getSubData(String key) {
    return subsdata.get(key);
  }

  public Subject getSubDataInt(int value) {
    return subsdata.get(usesubs.get(value));
  }

  public HashMap<String, Subject> getSubDataAll() {
    return subsdata;
  }

  public String getSubName(int value) {
    return usesubs.get(value);
  }

  public ArrayList<String> getSubNameAll() {
    return usesubs;
  }

  public int getSubsize() {
    return subsdata.size();
  }

  public int getTotal() {
    total = 0;
    for (String s : usesubs) {
      total += subsdata.get(s).getScore();
    }
    return total;
  }

  public float getAverage() {
    if (total == 0)
      this.getTotal();
    ave = (float) total / (float) subsdata.size();
    return ave;
  }

  public Calendar getCalendar() {
    return cal;
  }
}

class Subject implements Serializable {
  static final long serialVersionUID = 1L;
  private String name;
  private int score;

  Subject(String n) {
    name = n;
  }

  public void setScore(int s) {
    score = s;
  }

  public String getName() {
    return name;
  }

  public int getScore() {
    return score;
  }
}
