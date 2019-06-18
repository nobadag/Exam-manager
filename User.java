import java.io.*;
import java.util.*;

class User implements Serializable {
  static final long serialVersionUID = 1L;
  private String name;
  private String password;
  private ArrayList<Exam> examsList = new ArrayList<>();
  private ArrayList<String> subnames = new ArrayList<>();
  private ArrayList<String> whens = new ArrayList<>();
  private ArrayList<String> omits = new ArrayList<>();
  private HashMap<String, String> whomits = new HashMap<String, String>();
  private HashMap<String, String> omwhens = new HashMap<String, String>();
  private boolean set = false;

  User(String n, String p) {
    name = n;
    password = p;
  }

  public void userinit() {
    if (subnames.isEmpty())
      subnames.addAll(Arrays.asList("国語", "社会", "数学", "理科", "英語", "美術", "技術", "家庭", "保健体育", "音楽"));
    if (whens.isEmpty())
      whens.addAll(Arrays.asList("１学期実力テスト", "１学期中間テスト", "１学期期末テスト", "２学期確認テスト", "２学期中間テスト", "２学期期末テスト", "３学期実力テスト",
          "３学期学年末テスト"));
    if (omits.isEmpty())
      omits.addAll(Arrays.asList("１実力", "１中間", "１期末", "２確認", "２中間", "２期末", "３実力", "３学末"));

    for (int i = 0; i < whens.size(); i++) {
      whomits.put(whens.get(i), omits.get(i));
      omwhens.put(omits.get(i), whens.get(i));
    }
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

  public ArrayList<String> getSubNames() {
    return subnames;
  }

  public ArrayList<String> getWhens() {
    return whens;
  }

  public ArrayList<String> getOmits() {
    return omits;
  }

  public HashMap<String, String> getWhomits() {
    return whomits;
  }

  public HashMap<String, String> getOmwhens() {
    return omwhens;
  }

  public void changeSubName(int index, String n) {
    if(index == 0)
      subnames.clear();

    subnames.add(index,n);

  }

  public void changeWhen(int index, String w,String o) {
    if(index == 0){
      whens.clear();
      omits.clear();
      whomits.clear();
      omwhens.clear();
    }

    whens.add(index,w);
    omits.add(index,o);
    whomits.put(w,o);
    omwhens.put(o,w);
  }

  public void SetOK() {
    set = true;
  }

  public boolean isSetOK() {
    return set;
  }
}

class Exam implements Serializable {
  static final long serialVersionUID = 1L;
  private String name;
  private String omit;
  private ArrayList<String> usesubs = new ArrayList<>();
  private HashMap<String, Subject> subsdata = new HashMap<String, Subject>();
  private int total = 0;
  private float ave = 0;
  private Calendar cal = Calendar.getInstance();

  Exam(String n,User u) {
    name = n;
    omit = u.getWhomits().get(n);
  }

  public String getName() {
    return name;
  }

  public String getOmit() {
    return omit;
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
