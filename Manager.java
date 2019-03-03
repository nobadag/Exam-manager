import java.io.*;
import java.util.*;
import java.text.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.chart.*;
import javafx.geometry.*;
import javafx.event.*;
import javafx.collections.*;
import javafx.beans.property.*;

public class Manager extends Application {
  public Stage stage;

  public final String[] subnames = { "国語", "社会", "数学", "理科", "英語", "美術", "技術", "家庭", "保健体育", "音楽" };
  public final String[] when = { "１学期実力テスト", "１学期中間テスト", "１学期期末テスト", "２学期確認テスト", "２学期中間テスト", "２学期期末テスト", "３学期実力テスト",
      "３学期学年末テスト" };
  public final String[] omit = { "１実力", "１中間", "１期末", "２確認", "２中間", "２期末", "３実力", "３学末" };
  public HashMap<String, String> whomit = new HashMap<String, String>();
  public final int min = 0;
  public final int max = 100;

  private Scene welcome;
  private Scene make_acc;
  private Scene login_acc;
  private Scene home;
  private Scene select_sub;
  private Scene select_when;
  private Scene input_sco;
  private Scene check_sco;
  private Scene updown;
  private Scene sco_table;

  private File Datas;
  private File Roster;

  private Image maru;
  private Image batsu;
  private Image pencil;
  private Image up;
  private Image down;
  private Image flat;
  private Image bar;

  private User user;
  private Exam exam;
  private HashSet<String> usersname = new HashSet<>();
  private HashMap<String, Subject> subsMap = new HashMap<String, Subject>();
  private ArrayList<String> usesubs = new ArrayList<>();
  private String usewhen;

  private TextField actf1;
  private PasswordField pwf1;
  private Label msg1;
  private Label msg2;
  private Button ok;

  private TextField actf2;
  private PasswordField pwf2;
  private Label msg3;
  private Label msg4;
  private Button ok1;

  private int dis = 0;

  private Label sub;
  private Label check;
  private TextField scotf;
  private int count = 0;

  private boolean rand = false;
  private boolean fromtable = false;
  private boolean acwrote = false;
  private boolean pswrote = false;

  private Tab[] tabs;
  private TabPane tbp;
  private ArrayList<TableView<RowSubData>> tvs;
  private ArrayList<LineChart<String, Number>> lns;
  private Button chart;
  private Button change;
  private boolean lnok = false;
  private RowSubData row;

  public static void main(String[] args) {
    launch(args);
  }

  public void init() throws Exception {
    maru = new Image("file:Image/マル.png", 50, 0, true, false);
    batsu = new Image("file:Image/バツ.png", 50, 0, true, false);
    pencil = new Image("file:Image/エンピツ.png", 30, 0, true, false);
    up = new Image("file:Image/アップ.png", 30, 0, true, false);
    down = new Image("file:Image/ダウン.png", 30, 0, true, false);
    flat = new Image("file:Image/フラット.png", 30, 0, true, false);
    bar = new Image("file:Image/バー.png", 30, 0, true, false);

    Datas = new File("Datas");
    Roster = new File("Datas/Roster.txt");

    if (!Datas.exists()) {
      Datas.mkdir();
    }

    if (!Roster.exists()) {
      Roster.createNewFile();
    } else {
      String line;
      BufferedReader br = new BufferedReader(new FileReader(Roster.getPath()));
      while ((line = br.readLine()) != null) {
        usersname.add(line);
      }
      br.close();
    }

    for (int i = 0; i < when.length; i++) {
      whomit.put(when[i], omit[i]);
    }
  }

  public void start(Stage temp) throws Exception {
    stage = temp;
    stage.setTitle("Exam-manager");

    stage.setHeight(400);
    stage.setWidth(600);

    welcome();

    stage.show();
  }

  void welcome() {
    // welcomeの画面
    Label des = new Label("Exam-manager にようこそ\n");
    Button nw = new Button("新規作成");
    Button op = new Button("ログイン");

    des.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));

    nw.setFont(new Font(20));
    op.setFont(new Font(20));

    nw.setPrefWidth(150);
    op.setPrefWidth(150);

    nw.setTooltip(new Tooltip("アカウントを新規作成します"));
    op.setTooltip(new Tooltip("アカウントにログインします"));

    BorderPane bp = new BorderPane();
    VBox home = new VBox(20);

    home.getChildren().add(des);
    home.getChildren().add(nw);
    home.getChildren().add(op);

    home.setAlignment(Pos.CENTER);
    bp.setCenter(home);

    welcome = new Scene(bp);

    // 「新規」を押すと、アカウント作成画面へ
    nw.setOnAction(e -> {
      make_acc();
    });

    op.setOnAction(e -> {
      login_acc();
    });

    stage.setScene(welcome);
  }

  void make_acc() {
    // アカウント作成画面
    Label des = new Label("アカウントを作成しましょう");
    Label acc = new Label("アカウント名：");
    Label pas = new Label("パスワード：");

    actf1 = new TextField();
    pwf1 = new PasswordField();
    msg1 = new Label();
    msg2 = new Label();
    ok = new Button("  OK  ");

    des.setFont(new Font(18));
    acc.setFont(new Font(17));
    pas.setFont(new Font(17));
    msg1.setFont(new Font(18));
    msg2.setFont(new Font(18));
    actf1.setFont(new Font(15));
    pwf1.setPrefWidth(300);
    actf1.setPrefHeight(30);
    pwf1.setPrefHeight(30);
    ok.setDisable(true);

    actf1.setOnAction(new Inspection_name());
    pwf1.setOnAction(new Inspection_password());

    ok.setFont(new Font(15));

    ok.setOnAction(e -> {
      // アカウント名確定のためのアラート
      Alert really = new Alert(Alert.AlertType.CONFIRMATION);
      really.setTitle("確認");
      really.getDialogPane().setHeaderText("本当に " + actf1.getText() + " がアカウント名でいいですか？");

      // 「OK」を押すと、アラートを表示
      Optional<ButtonType> res = really.showAndWait();

      if (res.get() == ButtonType.OK) {
        // 「OK」を押すと、Userのオブジェクトを生成し、教科選択画面へ
        user = new User(actf1.getText(), pwf1.getText());
        usersname.add(actf1.getText());
        roster_write();
        home();
      }
    });

    BorderPane bp = new BorderPane();
    VBox vb = new VBox(20);
    GridPane gp = new GridPane();

    gp.add(acc, 0, 0);
    gp.add(pas, 0, 1);
    gp.add(actf1, 1, 0);
    gp.add(pwf1, 1, 1);

    gp.setVgap(10);
    gp.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);
    vb.getChildren().add(gp);
    vb.getChildren().add(msg1);
    vb.getChildren().add(msg2);
    vb.getChildren().add(ok);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    make_acc = new Scene(bp);

    stage.setScene(make_acc);
  }

  class Inspection_name implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      String name = new String(actf1.getText());

      // アカウント名が有効か判定する
      if (name.length() == 0 || usersname.contains(name)) {
        msg1.setText("このアカウント名を使うことはできません。");
        msg1.setGraphic(new ImageView(batsu));
        ok.setDisable(true);
        acwrote = false;
      } else {
        msg1.setText("このアカウント名を使うことができます。");
        msg1.setGraphic(new ImageView(maru));
        if (pswrote) {
          ok.setDisable(false);
        }
        acwrote = true;
      }
    }
  }

  class Inspection_password implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      String pass = new String(pwf1.getText());

      // パスワードが有効か判定する
      if (pass.length() >= 6 && acwrote) {
        msg2.setText("このパスワードを使うことができます。");
        msg2.setGraphic(new ImageView(maru));
        ok.setDisable(false);
        pswrote = true;
      } else {
        msg2.setText("パスワードの長さが足りません");
        msg2.setGraphic(new ImageView(batsu));
        ok.setDisable(true);
        pswrote = false;
      }
    }
  }

  void login_acc() {
    // ログイン画面
    Label des = new Label("ログインしましょう");
    Label acc = new Label("アカウント名：");
    Label pas = new Label("パスワード：");

    actf2 = new TextField();
    pwf2 = new PasswordField();
    msg3 = new Label();
    msg4 = new Label();
    ok1 = new Button("  OK  ");

    des.setFont(new Font(18));
    acc.setFont(new Font(17));
    pas.setFont(new Font(17));
    msg3.setFont(new Font(18));
    msg4.setFont(new Font(18));
    actf2.setFont(new Font(15));
    pwf2.setPrefWidth(300);
    actf2.setPrefHeight(30);
    pwf2.setPrefHeight(30);
    ok1.setDisable(true);

    ok1.setFont(new Font(15));

    actf2.setOnAction(new Check_acc());
    pwf2.setOnAction(new Check_acc());

    ok1.setOnAction(e -> {
      home();
    });

    BorderPane bp = new BorderPane();
    VBox vb = new VBox(20);
    GridPane gp = new GridPane();

    gp.add(acc, 0, 0);
    gp.add(pas, 0, 1);
    gp.add(actf2, 1, 0);
    gp.add(pwf2, 1, 1);

    gp.setVgap(10);
    gp.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);
    vb.getChildren().add(gp);
    vb.getChildren().add(msg3);
    vb.getChildren().add(msg4);
    vb.getChildren().add(ok1);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    login_acc = new Scene(bp);

    stage.setScene(login_acc);
  }

  class Check_acc implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      if (usersname.contains(actf2.getText())) {
        msg3.setText("アカウントが見つかりました。");
        msg3.setGraphic(new ImageView(maru));
        data_read();
        if (user.getPassword().equals(pwf2.getText())) {
          msg4.setText("パスワードが一致しました。");
          msg4.setGraphic(new ImageView(maru));
          ok1.setDisable(false);
        } else {
          if (pwf2.getText().length() != 0) {
            msg4.setText("パスワードが一致しません。");
            msg4.setGraphic(new ImageView(batsu));
            ok1.setDisable(true);
          } else {
            msg4.setText("");
            msg4.setGraphic(null);
          }
        }
      } else {
        if (actf2.getText().length() != 0) {
          msg3.setText("アカウントが見つかりません。");
          msg3.setGraphic(new ImageView(batsu));
          ok1.setDisable(true);
        } else {
          msg3.setText("");
          msg3.setGraphic(null);
        }
      }
    }
  }

  void data_read() {
    try {
      ObjectInputStream os = new ObjectInputStream(new FileInputStream("Datas/" + actf2.getText() + ".ser"));
      user = (User) os.readObject();
      os.close();
    } catch (Exception exp) {
      Alert err = new Alert(Alert.AlertType.ERROR);
      err.setTitle("エラー");
      err.getDialogPane().setHeaderText("データを読み込むことに失敗しました。" + exp);
      Optional<ButtonType> reserr = err.showAndWait();
      if (reserr.get() == ButtonType.OK) {
        System.exit(1);
      }
    }
  }

  void home() {
    // ユーザーのホーム画面
    Label name = new Label(user.getName() + " さん");
    Button newex = new Button("新規試験");
    Button dataex = new Button("データベース");

    name.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));
    newex.setFont(new Font(20));
    dataex.setFont(new Font(20));

    newex.setPrefWidth(180);
    dataex.setPrefWidth(180);

    newex.setOnAction(e -> {
      select_sub();
    });

    dataex.setOnAction(e -> {
      sco_table();
    });

    if (user.getExamsize() == 0) {
      dataex.setDisable(true);
    }

    VBox vb = new VBox(20);
    BorderPane bp = new BorderPane();

    vb.getChildren().add(name);
    vb.getChildren().add(newex);
    vb.getChildren().add(dataex);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    home = new Scene(bp);

    stage.setScene(home);
  }

  void select_sub() {
    // 教科選択画面
    Label des = new Label("教科を選んでください。");
    CheckBox[] subs = new CheckBox[subnames.length];
    Button ok2 = new Button("  OK  ");
    GridPane gp = new GridPane();

    VBox subvb = new VBox(10);

    des.setFont(new Font(18));
    subvb.getChildren().add(des);

    ok2.setFont(new Font(15));

    usesubs.clear();

    int x = 0, y = 0;
    for (int i = 0; i < subnames.length; i++) {
      subs[i] = new CheckBox(subnames[i]);
      subs[i].setFont(new Font(15));
      subs[i].setPrefWidth(100);
      if (i % 3 == 0) {
        x = 0;
        y++;
      }
      gp.add(subs[i], x, y);
      subs[i].setOnAction(e -> {
        // １つ以上チェックされていないと「OK」を無効にする
        CheckBox t = (CheckBox) e.getSource();
        if (t.isSelected()) {
          dis++;
        } else {
          dis--;
        }
        if (dis == 0) {
          ok2.setDisable(true);
        } else {
          ok2.setDisable(false);
        }
      });
      x++;
    }

    ok2.setDisable(true);

    gp.setHgap(10);
    gp.setVgap(10);
    gp.setAlignment(Pos.CENTER);

    subvb.getChildren().add(gp);
    subvb.getChildren().add(ok2);

    subvb.setAlignment(Pos.CENTER);

    BorderPane bp = new BorderPane();

    bp.setCenter(subvb);

    select_sub = new Scene(bp);

    ok2.setOnAction(e -> {
      // 「OK」を押すと、試験選択画面へ
      select_when();
      // チェックされた分、Subjectのオブジェクトを生成
      for (int i = 0; i < subs.length; i++) {
        if (subs[i].isSelected()) {
          usesubs.add(subs[i].getText());
          subsMap.put(subs[i].getText(), new Subject(subs[i].getText()));
        }
      }
    });

    stage.setScene(select_sub);
  }

  void select_when() {
    // 試験選択画面
    Label des = new Label("いつの試験か選んでください。");
    RadioButton[] wh = new RadioButton[when.length];
    ToggleGroup whtg = new ToggleGroup();
    Button ok3 = new Button("  OK  ");

    BorderPane bp = new BorderPane();
    VBox vb = new VBox(10);

    des.setFont(new Font(18));
    vb.getChildren().add(des);

    ok3.setFont(new Font(15));

    for (int i = 0; i < when.length; i++) {
      wh[i] = new RadioButton(when[i]);
      wh[i].setFont(new Font(15));
      wh[i].setPrefWidth(150);
      wh[i].setToggleGroup(whtg);
      vb.getChildren().add(wh[i]);
    }

    int last;
    if (user.getExamsize() > 0
        && (last = Arrays.asList(when).indexOf(user.getExam(user.getExamsize() - 1).getName())) != when.length - 1) {
      wh[last + 1].setSelected(true);
    } else {
      wh[0].setSelected(true);
    }

    vb.getChildren().add(ok3);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    select_when = new Scene(bp);

    ok3.setOnAction(e -> {
      // どの試験なのか記憶
      Toggle sel = whtg.getSelectedToggle();
      RadioButton t = (RadioButton) sel;
      usewhen = t.getText();
      // Examクラスのオブジェクトを生成
      exam = new Exam(usewhen);
      // 「OK」を押すと、点数入力画面へ
      count = 0;
      input_sco();
    });

    stage.setScene(select_when);
  }

  void input_sco() {
    // 点数入力画面
    Label selwh = new Label(usewhen);
    sub = new Label(usesubs.get(count));
    check = new Label();
    scotf = new TextField();
    Button[] tenkey = new Button[12];

    selwh.setFont(new Font(20));
    sub.setFont(Font.font("SansSerif", FontWeight.BLACK, 25));
    check.setFont(new Font(17));
    scotf.setFont(new Font(30));

    BorderPane bp = new BorderPane();
    VBox vb = new VBox(20);
    HBox hb = new HBox(30);
    GridPane gp = new GridPane();

    scotf.setAlignment(Pos.CENTER);

    // テンキーの実装
    int x = 0;
    int y = 0;
    for (int i = 1; i <= 9; i++) {
      tenkey[i] = new Button(String.valueOf(i));
      if (i % 3 == 1) {
        x = 0;
        y++;
      }
      gp.add(tenkey[i], x, y);
      x++;
    }

    tenkey[0] = new Button(String.valueOf(0));
    tenkey[10] = new Button("Clear");
    tenkey[11] = new Button("Enter");

    gp.add(tenkey[0], 1, 4);
    gp.add(tenkey[10], 0, 4);
    gp.add(tenkey[11], 2, 4);

    for (int i = 0; i < 12; i++) {
      tenkey[i].setPrefHeight(50);
      tenkey[i].setPrefWidth(50);
      if (i <= 9) {
        tenkey[i].setOnAction(e -> {
          // 押すとテキストフィールドに入力されていく
          Button tmp = (Button) e.getSource();
          scotf.setText(scotf.getText() + tmp.getText());
        });
      }
    }

    tenkey[10].setOnAction(e -> {
      // テキストフィールドをクリア
      scotf.setText("");
    });

    tenkey[11].setOnAction(new Check_score());
    scotf.setOnAction(new Check_score());

    gp.setAlignment(Pos.CENTER);

    vb.getChildren().add(selwh);
    vb.getChildren().add(sub);
    vb.getChildren().add(scotf);
    vb.getChildren().add(check);
    vb.setAlignment(Pos.CENTER);

    hb.getChildren().add(vb);
    hb.getChildren().add(gp);
    hb.setAlignment(Pos.CENTER);

    bp.setCenter(hb);

    input_sco = new Scene(bp);

    stage.setScene(input_sco);
  }

  class Check_score implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      // 有効な数値か判定
      try {
        int score = Integer.parseInt(scotf.getText());
        if (score < min || score > max) {
          // 範囲内かの判定
          check.setText("有効な数値ではありません。");
          check.setGraphic(new ImageView(batsu));
          scotf.setText("");
        } else {
          if (fromtable) {
            user.getExam(row.getNumber()).getSubDataInt(count).setScore(score);
            sco_table();
            count = 0;
            fromtable = false;
          } else {
            subsMap.get(usesubs.get(count)).setScore(score);
            count++;
            if (count >= subsMap.size() || rand) {
              // 最後の教科が終わると点数確認画面へ
              // 点数変更によるランダムアクセスならば点数確認画面へ
              rand = false;
              check_sco();
            } else {
              // 初回の入力ならば次の教科へ
              input_sco();
            }
          }
        }
      } catch (NumberFormatException exp) {
        // 文字が含まれていた場合
        check.setText("数値として読み取ることができません。");
        check.setGraphic(new ImageView(batsu));
        scotf.setText("");
      }
    }
  }

  void check_sco() {
    // 点数確認画面
    Label des = new Label("この点数でいいですか？");
    Button ok4 = new Button("  OK  ");
    Label[] subch = new Label[subsMap.size()];
    Label[] scoch = new Label[subsMap.size()];
    Button[] change = new Button[subsMap.size()];

    GridPane gp = new GridPane();
    VBox vb = new VBox(10);
    ScrollPane scp = new ScrollPane();
    BorderPane bp = new BorderPane();

    ok4.setFont(new Font(15));
    des.setFont(new Font(18));

    for (int i = 0; i < subsMap.size(); i++) {
      subch[i] = new Label(usesubs.get(i) + "：");
      scoch[i] = new Label(String.valueOf(subsMap.get(usesubs.get(i)).getScore()) + " 点");
      change[i] = new Button("変更");
      change[i].setGraphic(new ImageView(pencil));
      subch[i].setFont(new Font(17));
      scoch[i].setFont(new Font(17));
      subch[i].setPrefWidth(100);
      scoch[i].setPrefWidth(100);
      subch[i].setAlignment(Pos.CENTER);
      scoch[i].setAlignment(Pos.CENTER);
      gp.add(subch[i], 0, i);
      gp.add(scoch[i], 1, i);
      gp.add(change[i], 2, i);
      change[i].setOnAction(e -> {
        // 「変更」が押されたら、その教科の点数入力画面に移る
        Button t = (Button) e.getSource();
        count = GridPane.getRowIndex(t);
        rand = true;
        input_sco();
      });
    }

    gp.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);
    vb.getChildren().add(gp);
    vb.getChildren().add(ok4);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    bp.setPrefHeight(stage.getHeight());
    bp.setPrefWidth(stage.getWidth());

    scp.setContent(bp);

    scp.setHbarPolicy(ScrollBarPolicy.NEVER);

    ok4.setOnAction(e -> {
      exam.addDataAll(subsMap, usesubs);
      user.addExam(exam);
      data_write();
      updown();
    });

    check_sco = new Scene(scp);

    stage.setScene(check_sco);
  }

  void updown() {
    // 結果報告画面
    Label des = new Label("試験結果");
    Label[] sublb = new Label[exam.getSubsize() + 2];
    Label[] subvl = new Label[exam.getSubsize() + 2];
    Label[] subdif = new Label[exam.getSubsize() + 2];
    Button ok5 = new Button("  OK  ");
    float now = 0;
    float last = 0;

    GridPane gp = new GridPane();
    BorderPane bp = new BorderPane();
    VBox vb = new VBox(10);
    ScrollPane scp = new ScrollPane();

    ok5.setFont(new Font(15));
    des.setFont(new Font(18));

    for (int i = 0; i < exam.getSubsize() + 2; i++) {
      sublb[i] = new Label();
      subvl[i] = new Label();
      subdif[i] = new Label();
      if (i == 0) {
        sublb[i].setText("合計点：");
        subvl[i].setText(String.valueOf(exam.getTotal()) + " 点");
      } else if (i == 1) {
        sublb[i].setText("平均点");
        subvl[i].setText(String.valueOf(String.format("%.1f", exam.getAverage())) + " 点");
      } else {
        sublb[i].setText(String.valueOf(exam.getSubDataInt(i - 2).getName() + "："));
        subvl[i].setText(String.valueOf(exam.getSubDataInt(i - 2).getScore()) + " 点");
      }

      if (user.getExamsize() > 1) {
        if (i == 0) {
          now = exam.getTotal();
          last = user.getExam(user.getExamsize() - 2).getTotal();
          subdif[i].setText(String.valueOf((int) Math.abs(now - last)) + " 点");
        } else if (i == 1) {
          now = exam.getAverage();
          last = user.getExam(user.getExamsize() - 2).getAverage();
          subdif[i].setText(String.valueOf(String.format("%.1f", Math.abs(now - last))) + " 点");
        } else {
          if (user.getExam(user.getExamsize() - 2).getSubNameAll().contains(usesubs.get(i - 2))) {
            now = exam.getSubDataInt(i - 2).getScore();
            last = user.getExam(user.getExamsize() - 2).getSubDataInt(i - 2).getScore();
            subdif[i].setText(String.valueOf((int) Math.abs(now - last)) + " 点");
          }
        }

        if (now > last) {
          subdif[i].setGraphic(new ImageView(up));
        } else if (now < last) {
          subdif[i].setGraphic(new ImageView(down));
        } else {
          subdif[i].setGraphic(new ImageView(flat));
        }

        if (i > 1 && !user.getExam(user.getExamsize() - 2).getSubNameAll().contains(usesubs.get(i - 2))) {
          subdif[i].setGraphic(new ImageView(bar));
        }
      } else {
        subdif[i].setGraphic(new ImageView(bar));
      }

      sublb[i].setFont(new Font(17));
      subvl[i].setFont(new Font(17));
      subdif[i].setFont(new Font(17));
      sublb[i].setPrefWidth(100);
      subvl[i].setPrefWidth(100);
      subdif[i].setPrefHeight(40);
      sublb[i].setAlignment(Pos.CENTER);

      gp.add(sublb[i], 0, i);
      gp.add(subvl[i], 1, i);
      gp.add(subdif[i], 2, i);
    }

    gp.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);
    vb.getChildren().add(gp);
    vb.getChildren().add(ok5);
    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    bp.setPrefHeight(stage.getHeight());
    bp.setPrefWidth(stage.getWidth());

    scp.setContent(bp);

    scp.setHbarPolicy(ScrollBarPolicy.NEVER);

    ok5.setOnAction(e -> {
      home();
    });

    updown = new Scene(scp);

    stage.setScene(updown);
  }

  void sco_table() {
    tvs = new ArrayList<>();
    Label des = new Label(user.getName() + " さんのデータベース");
    chart = new Button("グラフ");
    change = new Button("変更");
    tabs = new Tab[subnames.length + 1];
    tbp = new TabPane();

    des.setFont(new Font(18));
    chart.setFont(new Font(16));
    change.setFont(new Font(16));

    change.setGraphic(new ImageView(pencil));

    chart.setPrefWidth(100);
    change.setPrefWidth(100);

    chart.setPrefHeight(40);
    change.setPrefHeight(40);

    FlowPane fp = new FlowPane();

    fp.getChildren().add(change);
    fp.getChildren().add(chart);

    fp.setAlignment(Pos.BOTTOM_RIGHT);

    for (int i = 0; i < subnames.length + 1; i++) {
      TableView<RowSubData> tv = new TableView<>();
      ObservableList<RowSubData> ovl = FXCollections.observableArrayList();
      TableColumn<RowSubData, String> tc1 = new TableColumn<RowSubData, String>("番号");
      TableColumn<RowSubData, String> tc2 = new TableColumn<RowSubData, String>("試験");
      TableColumn<RowSubData, String> tc3 = new TableColumn<RowSubData, String>("点数");
      TableColumn<RowSubData, String> tc4 = new TableColumn<RowSubData, String>("月日");

      tc1.setCellValueFactory(new PropertyValueFactory<RowSubData, String>("number"));
      tc2.setCellValueFactory(new PropertyValueFactory<RowSubData, String>("examname"));
      tc3.setCellValueFactory(new PropertyValueFactory<RowSubData, String>("score"));
      tc4.setCellValueFactory(new PropertyValueFactory<RowSubData, String>("date"));

      tc1.setStyle("-fx-alignment: CENTER;");
      tc3.setStyle("-fx-alignment: CENTER;");

      tc2.setPrefWidth(120);
      tc3.setPrefWidth(50);
      tc4.setPrefWidth(120);

      int c = 1;
      for (int j = 0; j < user.getExamsize(); j++) {
        Exam t = user.getExam(j);
        if (i == 0) {
          tabs[i] = new Tab("平均");
          ovl.add(new RowSubData(j + 1, t.getName(), String.valueOf(String.format("%.1f", t.getAverage())),
              t.getCalendar()));
        } else {
          tabs[i] = new Tab(subnames[i - 1]);
          if (t.getSubNameAll().contains(subnames[i - 1])) {
            ovl.add(new RowSubData(c, t.getName(), String.valueOf(t.getSubDataInt(i - 1).getScore()), t.getCalendar()));
          } else {
            ovl.add(new RowSubData(c, t.getName(), "", t.getCalendar()));
          }
          c++;
        }
        tabs[i].setClosable(false);
      }

      tv.getColumns().add(tc1);
      tv.getColumns().add(tc2);
      tv.getColumns().add(tc3);
      tv.getColumns().add(tc4);

      tv.setItems(ovl);

      tv.setStyle("-fx-font-size: " + 10 + "pt;");

      tvs.add(tv);
    }

    set_table();

    tbp.setStyle("-fx-font-size: " + 10 + "pt;");

    VBox vb = new VBox(10);
    BorderPane bp = new BorderPane();

    vb.getChildren().add(des);
    vb.getChildren().add(tbp);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);
    bp.setBottom(fp);

    sco_table = new Scene(bp);

    stage.setScene(sco_table);
  }

  void set_table() {
    change.setDisable(true);

    for (int i = 0; i < tabs.length; i++) {
      tabs[i].setContent(tvs.get(i));
      tbp.getTabs().add(tabs[i]);

      TableView.TableViewSelectionModel<RowSubData> pos = tvs.get(i).getSelectionModel();
      pos.selectedItemProperty().addListener(e -> {
        row = pos.getSelectedItem();
        if (row.containsValue()) {
          change.setDisable(false);
          subsMap = user.getExam(row.getNumber()).getSubDataAll();
          usesubs = user.getExam(row.getNumber()).getSubNameAll();
          usewhen = user.getExam(row.getNumber()).getName();
          count = tvs.indexOf(pos.getTableView()) - 1;
        } else {
          change.setDisable(true);
        }
      });
    }

    tbp.getSelectionModel().selectedItemProperty().addListener(e -> {
      change.setDisable(true);
    });

    chart.setOnAction(e -> {
      chart.setText("表");
      if (lnok) {
        set_chart();
      } else {
        sco_chart();
      }
    });

    change.setOnAction(e -> {
      fromtable = true;
      input_sco();
    });
  }

  public class RowSubData {
    private boolean value = false;
    private int num;
    private final SimpleIntegerProperty number;
    private final SimpleStringProperty examname;
    private final SimpleStringProperty score;
    private final SimpleStringProperty date;

    RowSubData(Integer num, String exn, String sco, Calendar cal) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/(E)");
      this.num = num - 1;
      if (!sco.equals(""))
        value = true;
      number = new SimpleIntegerProperty(num);
      examname = new SimpleStringProperty(exn);
      score = new SimpleStringProperty(sco);
      date = new SimpleStringProperty(String.valueOf(sdf.format(cal.getTime())));
    }

    public SimpleIntegerProperty numberProperty() {
      return number;
    }

    public SimpleStringProperty examnameProperty() {
      return examname;
    }

    public SimpleStringProperty scoreProperty() {
      return score;
    }

    public SimpleStringProperty dateProperty() {
      return date;
    }

    public int getNumber() {
      return num;
    }

    public boolean containsValue() {
      return value;
    }
  }

  void sco_chart() {
    lns = new ArrayList<>();

    for (int i = 0; i < subnames.length + 1; i++) {
      CategoryAxis xAxis = new CategoryAxis();
      NumberAxis yAxis = new NumberAxis();

      xAxis.setLabel("試験");
      yAxis.setLabel("点数");

      LineChart<String, Number> linechart = new LineChart<String, Number>(xAxis, yAxis);

      XYChart.Series<String, Number> line = new XYChart.Series<>();

      line.setName("点数");

      for (int j = 0; j < user.getExamsize(); j++) {
        Exam t = user.getExam(j);
        if (i == 0) {
          linechart.setTitle("平均点の変化");
          line.getData().add(new XYChart.Data<>(whomit.get(t.getName()), t.getAverage()));
        } else {
          linechart.setTitle(subnames[i - 1] + " の点数の変化");
          if (t.getSubNameAll().contains(subnames[i - 1])) {
            line.getData().add(new XYChart.Data<>(whomit.get(t.getName()), t.getSubData(subnames[i - 1]).getScore()));
          }
        }
      }

      linechart.getData().add(line);

      lns.add(linechart);
    }

    set_chart();
  }

  void set_chart() {
    for (int i = 0; i < tabs.length; i++) {
      tabs[i].setContent(lns.get(i));
      tbp.getTabs().add(tabs[i]);
    }

    chart.setOnAction(e -> {
      chart.setText("グラフ");
      set_table();
    });

    change.setDisable(true);
  }

  void data_write() {
    try {
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Datas/" + user.getName() + ".ser"));
      os.writeObject(user);
      os.close();
    } catch (Exception exp) {
      Alert err = new Alert(Alert.AlertType.ERROR);
      err.setTitle("エラー");
      err.getDialogPane().setHeaderText("データを書き込むことに失敗しました。" + exp);
      Optional<ButtonType> reserr = err.showAndWait();
      if (reserr.get() == ButtonType.OK) {
        System.exit(1);
      }
    }
  }

  void roster_write() {
    try {
      PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(Roster.getPath())));
      for (String t : usersname) {
        pw.println(t);
      }
      pw.close();
    } catch (Exception exp) {
      Alert err = new Alert(Alert.AlertType.ERROR);
      err.setTitle("エラー");
      err.getDialogPane().setHeaderText("アカウント名とパスワードを保存できません。");
      Optional<ButtonType> reserr = err.showAndWait();
      if (reserr.get() == ButtonType.OK) {
        System.exit(1);
      }
    }
  }

  public void stop() {
    if (user != null) {
      data_write();
    }
  }
}
