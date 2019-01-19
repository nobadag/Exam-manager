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
  private int score;

  Subject(String tmp) {
    name = tmp;
  }

  void setScore(int tmp) {
    score = tmp;
  }

  String getName() {
    return name;
  }
}
