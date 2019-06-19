import java.io.*;
import java.util.*;
import java.util.stream.*;
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
  private Scene setting;

  private File Datas;
  private File Roster;

  private Image back;
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

  private ToolBar toolbar = new ToolBar();
  private Button backbt = new Button();

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

  private ArrayList<Tab> tabs;
  private TabPane tbp;
  private ArrayList<TableView<RowSubData>> tvs;
  private ArrayList<LineChart<String, Number>> lns;
  private Button chart;
  private Button change;
  private boolean lnok = false;
  private RowSubData row;

  private Tab[] items = new Tab[3];

  private Button subsave;
  private ArrayList<TextField> subtf = new ArrayList<>();
  private ArrayList<Label> subjudge = new ArrayList<>();
  private ArrayList<CheckBox> submark = new ArrayList<>();
  private int subsavelock = 0;

  private Button whensave;
  private ArrayList<TextField> whentf = new ArrayList<>();
  private ArrayList<TextField> omittf = new ArrayList<>();
  private ArrayList<Label> whenjudge = new ArrayList<>();
  private ArrayList<Label> omitjudge = new ArrayList<>();
  private ArrayList<CheckBox> whenmark = new ArrayList<>();
  private int whensavelock = 0;

  public static void main(String[] args) {
    launch(args);
  }

  public void init() throws Exception {
    back = new Image("file:Image/戻る.png", 20, 0, true, false);
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

    if (Roster.exists()) {
      String line;
      BufferedReader br = new BufferedReader(new FileReader(Roster.getPath()));
      while ((line = br.readLine()) != null) {
        usersname.add(line);
      }
      br.close();
    }

    backbt.setGraphic(new ImageView(back));
    toolbar.getItems().add(backbt);
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
    Label des = new Label("Exam-manager");
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

    backbt.setDisable(true);

    bp.setTop(toolbar);

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
        user.userinit();
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

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      welcome();
    });

    bp.setTop(toolbar);

    bp.setCenter(vb);

    make_acc = new Scene(bp);

    stage.setScene(make_acc);
  }

  class Inspection_name implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      String name = actf1.getText().trim();
      actf1.setText(name);

      // アカウント名が有効か判定する
      if (name.isEmpty() || usersname.contains(name)) {
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

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      welcome();
    });

    bp.setTop(toolbar);

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
          if (!pwf2.getText().isEmpty()) {
            msg4.setText("パスワードが一致しません。");
            msg4.setGraphic(new ImageView(batsu));
            ok1.setDisable(true);
          } else {
            msg4.setText("");
            msg4.setGraphic(null);
          }
        }
      } else {
        if (!actf2.getText().isEmpty()) {
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
      err.showAndWait();
    }
  }

  void home() {
    // ユーザーのホーム画面
    Label name = new Label(user.getName() + " さん");
    Button newex = new Button("新規試験");
    Button dataex = new Button("データベース");
    Button setting = new Button("設定");

    name.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));
    newex.setFont(new Font(20));
    dataex.setFont(new Font(20));
    setting.setFont(new Font(20));

    newex.setPrefWidth(180);
    dataex.setPrefWidth(180);
    setting.setPrefWidth(180);

    newex.setOnAction(e -> {
      select_sub();
    });

    dataex.setOnAction(e -> {
      sco_table();
    });

    if (user.getExamsize() == 0)
      dataex.setDisable(true);

    if (!user.isOK())
      newex.setDisable(true);

    setting.setOnAction(e -> {
      sub_setting();
    });

    VBox vb = new VBox(20);
    BorderPane bp = new BorderPane();

    vb.getChildren().add(name);
    vb.getChildren().add(newex);
    vb.getChildren().add(dataex);
    vb.getChildren().add(setting);

    vb.setAlignment(Pos.CENTER);

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      welcome();
    });

    bp.setTop(toolbar);

    bp.setCenter(vb);

    home = new Scene(bp);

    stage.setScene(home);
  }

  void select_sub() {
    // 教科選択画面
    Label des = new Label("教科を選んでください。");
    CheckBox[] subck = new CheckBox[user.getSubNames().size()];
    Button ok2 = new Button("  OK  ");
    GridPane gp = new GridPane();

    VBox subvb = new VBox(10);

    des.setFont(new Font(18));
    subvb.getChildren().add(des);

    ok2.setFont(new Font(15));

    usesubs.clear();

    subck = user.getSubNames().stream().map(CheckBox::new).toArray(CheckBox[]::new);
    Stream<CheckBox> strsubs = Arrays.stream(subck);

    strsubs.parallel().forEach(c -> {
      c.setFont(new Font(15));
      c.setPrefWidth(100);

      c.setOnAction(e -> {
        // １つ以上チェックされていないと「OK」を無効にする
        CheckBox t = (CheckBox) e.getSource();
        if (t.isSelected())
          dis++;
        else
          dis--;
        if (dis == 0)
          ok2.setDisable(true);
        else
          ok2.setDisable(false);
      });
    });

    int x = 0, y = 0;
    for (int i = 0; i < user.getSubNames().size(); i++) {
      if (i % 3 == 0) {
        x = 0;
        y++;
      }
      gp.add(subck[i], x, y);
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

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      home();
    });

    bp.setTop(toolbar);

    bp.setCenter(subvb);

    select_sub = new Scene(bp);

    Stream<CheckBox> t = Arrays.stream(subck);

    ok2.setOnAction(e -> {
      // 「OK」を押すと、試験選択画面へ
      select_when();
      // チェックされた教科のオブジェクトを作成
      t.filter(c -> c.isSelected()).forEach(c -> {
        usesubs.add(c.getText());
        subsMap.put(c.getText(), new Subject(c.getText()));
      });
    });

    stage.setScene(select_sub);
  }

  void select_when() {
    // 試験選択画面
    Label des = new Label("いつの試験か選んでください。");
    RadioButton[] wh = new RadioButton[user.getWhens().size()];
    ToggleGroup whtg = new ToggleGroup();
    Button ok3 = new Button("  OK  ");

    BorderPane bp = new BorderPane();
    VBox vb = new VBox(10);

    des.setFont(new Font(18));
    vb.getChildren().add(des);

    ok3.setFont(new Font(15));

    wh = user.getWhens().stream().map(RadioButton::new).toArray(RadioButton[]::new);

    Arrays.stream(wh).forEach(r -> {
      r.setFont(new Font(15));
      r.setPrefWidth(150);
      r.setToggleGroup(whtg);
      vb.getChildren().add(r);
    });

    int last;
    if (user.getExamsize() > 0
        && (last = user.getWhens().indexOf(user.getExam(user.getExamsize() - 1).getName())) != user.getWhens().size()
            - 1) {
      wh[last + 1].setSelected(true);
    } else {
      wh[0].setSelected(true);
    }

    vb.getChildren().add(ok3);

    vb.setAlignment(Pos.CENTER);

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      select_sub();
    });

    bp.setTop(toolbar);

    bp.setCenter(vb);

    select_when = new Scene(bp);

    ok3.setOnAction(e -> {
      // どの試験なのか記憶
      Toggle sel = whtg.getSelectedToggle();
      RadioButton t = (RadioButton) sel;
      usewhen = t.getText();
      // Examクラスのオブジェクトを生成
      exam = new Exam(usewhen, user);
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

    tenkey[0] = new Button("0");
    tenkey[10] = new Button("Clear");
    tenkey[11] = new Button("Enter");

    gp.add(tenkey[0], 1, 4);
    gp.add(tenkey[10], 0, 4);
    gp.add(tenkey[11], 2, 4);

    Arrays.stream(tenkey).parallel().forEach(b -> {
      b.setPrefHeight(50);
      b.setPrefWidth(50);
      b.setOnAction(e -> {
        // 押すとテキストフィールドに入力されていく
        Button tmp = (Button) e.getSource();
        scotf.setText(scotf.getText() + tmp.getText());
      });
    });

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

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      if (count == 0) {
        select_when();
      } else {
        count--;
        input_sco();
      }
    });

    bp.setTop(toolbar);

    bp.setCenter(hb);

    input_sco = new Scene(bp);

    stage.setScene(input_sco);
  }

  class Check_score implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      // 有効な数値か判定
      try {
        int score = Integer.parseInt(scotf.getText());
        if (score < user.getMin() || score > user.getMax()) {
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

    subch = usesubs.stream().parallel().map(s -> s + "：").map(Label::new).toArray(Label[]::new);
    scoch = usesubs.stream().parallel().map(s -> String.valueOf(subsMap.get(s).getScore()) + " 点").map(Label::new)
        .toArray(Label[]::new);
    change = Stream.generate(() -> "変更").parallel().limit(subsMap.size()).map(Button::new).toArray(Button[]::new);

    Arrays.stream(subch).parallel().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefWidth(100);
      l.setAlignment(Pos.CENTER);
    });

    Arrays.stream(scoch).parallel().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefWidth(100);
      l.setAlignment(Pos.CENTER);
    });

    Arrays.stream(change).parallel().forEach(b -> {
      b.setGraphic(new ImageView(pencil));
      b.setOnAction(e -> {
        // 「変更」が押されたら、その教科の点数入力画面に移る
        Button t = (Button) e.getSource();
        count = GridPane.getRowIndex(t);
        rand = true;
        input_sco();
      });
    });

    for (int i = 0; i < subsMap.size(); i++) {
      gp.add(subch[i], 0, i);
      gp.add(scoch[i], 1, i);
      gp.add(change[i], 2, i);
    }

    gp.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);
    vb.getChildren().add(gp);
    vb.getChildren().add(ok4);

    vb.setAlignment(Pos.CENTER);

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      count--;
      input_sco();
    });

    bp.setTop(toolbar);

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
    ArrayList<Label> sublb = new ArrayList<>();
    ArrayList<Label> subvl = new ArrayList<>();
    ArrayList<Label> subdif = new ArrayList<>();
    ArrayList<Float> dif = new ArrayList<>();
    Button ok5 = new Button("  OK  ");

    GridPane gp = new GridPane();
    BorderPane bp = new BorderPane();
    VBox vb = new VBox(10);
    ScrollPane scp = new ScrollPane();

    ok5.setFont(new Font(15));
    des.setFont(new Font(18));

    sublb.add(new Label("合計点："));
    subvl.add(new Label(String.valueOf(exam.getTotal()) + " 点"));

    sublb.add(new Label("平均点："));
    subvl.add(new Label(String.format("%.1f", exam.getAverage()) + " 点"));

    usesubs.stream().map(s -> s + "：").map(Label::new).forEach(sublb::add);
    usesubs.stream().map(s -> String.valueOf(exam.getSubData(s).getScore()) + " 点").map(Label::new).forEach(subvl::add);

    if (user.getExamsize() > 1) {
      dif.add(new Float(exam.getTotal() - user.getExam(user.getExamsize() - 2).getTotal()));
      dif.add(new Float(exam.getAverage() - user.getExam(user.getExamsize() - 2).getAverage()));
      usesubs.stream().map(s -> user.getExam(user.getExamsize() - 2).getSubData(s)).forEach(d -> {
        if (d != null) {
          dif.add(new Float(exam.getSubData(d.getName()).getScore() - d.getScore()));
        } else {
          dif.add(null);
        }
      });

      dif.stream().forEach(f -> {
        Label t = new Label();
        if (f != null) {
          float pf = f.floatValue();
          t.setText(String.format("%.1f", Math.abs(pf)) + " 点");
          if (pf > 0) {
            t.setGraphic(new ImageView(up));
          } else if (pf < 0) {
            t.setGraphic(new ImageView(down));
          } else if (pf == 0) {
            t.setGraphic(new ImageView(flat));
          }
        } else {
          t.setGraphic(new ImageView(bar));
        }
        subdif.add(t);
      });
    } else {
      Stream.generate(Label::new).limit(exam.getSubsize() + 2).forEach(l -> {
        l.setGraphic(new ImageView(bar));
        subdif.add(l);
      });
    }

    for (int i = 0; i < exam.getSubsize() + 2; i++) {
      gp.add(sublb.get(i), 0, i);
      gp.add(subvl.get(i), 1, i);
      gp.add(subdif.get(i), 2, i);
    }

    sublb.parallelStream().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefWidth(100);
      l.setAlignment(Pos.CENTER);
    });

    subvl.parallelStream().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefWidth(100);
    });

    subdif.parallelStream().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefHeight(40);
    });

    gp.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);
    vb.getChildren().add(gp);
    vb.getChildren().add(ok5);
    vb.setAlignment(Pos.CENTER);

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      check_sco();
    });

    bp.setTop(toolbar);

    bp.setCenter(vb);

    bp.setPrefHeight(stage.getHeight());
    bp.setPrefWidth(stage.getWidth());

    scp.setContent(bp);

    scp.setHbarPolicy(ScrollBarPolicy.NEVER);

    ok5.setOnAction(e -> {
      count = 0;
      home();
    });

    updown = new Scene(scp);

    stage.setScene(updown);
  }

  void sco_table() {
    tvs = new ArrayList<>();
    chart = new Button("グラフ");
    change = new Button("変更");
    tabs = new ArrayList<>();
    tbp = new TabPane();

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

    tabs.add(new Tab("平均"));
    user.getSubNames().stream().map(Tab::new).forEach(tabs::add);

    tabs.parallelStream().forEach(t -> t.setClosable(false));

    for (int i = 0; i < user.getSubNames().size() + 1; i++) {
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
          ovl.add(new RowSubData(c, t.getName(), String.format("%.1f", t.getAverage()), t.getCalendar()));
        } else {
          if (t.getSubNameAll().contains(user.getSubNames().get(i - 1))) {
            ovl.add(new RowSubData(c, t.getName(), String.valueOf(t.getSubDataInt(i - 1).getScore()), t.getCalendar()));
          } else {
            ovl.add(new RowSubData(c, t.getName(), "", t.getCalendar()));
          }
          c++;
        }
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

    vb.getChildren().add(tbp);

    vb.setAlignment(Pos.CENTER);

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      home();
    });

    bp.setTop(toolbar);

    bp.setCenter(vb);
    bp.setBottom(fp);

    sco_table = new Scene(bp);

    stage.setScene(sco_table);
  }

  void set_table() {
    change.setDisable(true);

    tabs.stream().forEach(tbp.getTabs()::add);

    tvs.parallelStream().map(tv -> tv.getSelectionModel()).forEach(pos -> {
      pos.selectedItemProperty().addListener(e -> {
        row = pos.getSelectedItem();
        if (!tabs.get(0).isSelected() || row.containsValue()) {
          change.setDisable(false);
          subsMap = user.getExam(row.getNumber()).getSubDataAll();
          usesubs = user.getExam(row.getNumber()).getSubNameAll();
          usewhen = user.getExam(row.getNumber()).getName();
          count = tvs.indexOf(pos.getTableView()) - 1;
        } else {
          change.setDisable(true);
        }
      });
    });

    for (int i = 0; i < tabs.size(); i++) {
      tabs.get(i).setContent(tvs.get(i));
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

    for (int i = 0; i < user.getSubNames().size() + 1; i++) {
      CategoryAxis xAxis = new CategoryAxis();
      NumberAxis yAxis = new NumberAxis();

      xAxis.setLabel("試験");
      yAxis.setLabel("点数");

      LineChart<String, Number> linechart = new LineChart<String, Number>(xAxis, yAxis);

      XYChart.Series<String, Number> line = new XYChart.Series<>();

      line.setName("点数");

      ArrayList<String> checker = new ArrayList<>();
      ArrayList<Integer> counter = new ArrayList<>();
      int n = 1;

      for (int j = 0; j < user.getExamsize(); j++) {
        Exam t = user.getExam(j);
        String omit = t.getOmit();

        if (checker.contains(omit)) {
          n = counter.get(checker.indexOf(omit)) + 1;
        } else {
          n = 1;
          checker.add(omit);
          counter.add(1);
        }

        if (i == 0) {
          linechart.setTitle("平均点の変化");
          line.getData().add(new XYChart.Data<>(omit + " (" + n + ")", t.getAverage()));
        } else {
          linechart.setTitle(user.getSubNames().get(i - 1) + " の点数の変化");
          if (t.getSubNameAll().contains(user.getSubNames().get(i - 1))) {
            line.getData()
                .add(new XYChart.Data<>(omit + " (" + n + ")", t.getSubData(user.getSubNames().get(i - 1)).getScore()));
          }
        }
      }

      linechart.getData().add(line);

      lns.add(linechart);
    }

    set_chart();
  }

  void set_chart() {
    tabs.stream().forEach(tbp.getTabs()::add);

    for (int i = 0; i < tabs.size(); i++) {
      tabs.get(i).setContent(lns.get(i));
    }

    chart.setOnAction(e -> {
      chart.setText("グラフ");
      set_table();
    });

    change.setDisable(true);
  }

  void sub_setting() {
    TabPane tbp = new TabPane();

    items[0] = new Tab("教科");
    items[1] = new Tab("試験");
    items[2] = new Tab("ユーザー");

    Label des = new Label("教科の変更");
    Label lead = new Label("まず、あなたの学校に教科と試験を合わせてください。");
    Button plus = new Button("教科を追加");
    Button minus = new Button("教科を削除");
    subsave = new Button("保存");

    VBox vb = new VBox(10);
    GridPane gp = new GridPane();
    HBox hb = new HBox();
    ScrollPane scp = new ScrollPane();
    BorderPane bp = new BorderPane();
    BorderPane bigbp = new BorderPane();

    tbp.setStyle("-fx-font-size: " + 12 + "pt;");

    des.setFont(new Font(20));
    plus.setFont(new Font(17));
    minus.setFont(new Font(17));
    subsave.setFont(new Font(17));

    plus.setPrefWidth(150);
    minus.setPrefWidth(150);
    subsave.setPrefWidth(150);

    user.getSubNames().stream().map(TextField::new).forEach(subtf::add);
    Stream.generate(Label::new).limit(user.getSubNames().size()).forEach(subjudge::add);
    Stream.generate(CheckBox::new).limit(user.getSubNames().size()).forEach(submark::add);

    for (int i = 0; i < subtf.size(); i++) {
      gp.add(submark.get(i), 0, i);
      gp.add(subtf.get(i), 1, i);
      gp.add(subjudge.get(i), 2, i);
    }

    gp.setAlignment(Pos.CENTER);

    subtf.parallelStream().forEach(t -> {
      t.setOnAction(new Inspection_subject());
      t.setFont(new Font(17));
    });

    subjudge.parallelStream().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefWidth(300);
    });

    hb.getChildren().add(plus);
    hb.getChildren().add(minus);
    hb.getChildren().add(subsave);

    hb.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);

    if (!user.isOK()) {
      user.setOK();
      lead.setFont(new Font(17));
      vb.getChildren().add(lead);
    }

    vb.getChildren().add(hb);
    vb.getChildren().add(gp);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    bp.setPrefHeight(stage.getHeight());
    bp.setPrefWidth(stage.getWidth());

    scp.setContent(bp);

    scp.setHbarPolicy(ScrollBarPolicy.NEVER);

    items[0].setContent(scp);

    Arrays.stream(items).forEach(t -> {
      t.setClosable(false);
      tbp.getTabs().add(t);
    });

    plus.setOnAction(e -> {
      TextField newsubtf = new TextField();
      Label newsubjudge = new Label();

      newsubtf.setOnAction(new Inspection_subject());
      newsubtf.setFont(new Font(17));

      newsubjudge.setFont(new Font(17));
      newsubjudge.setPrefWidth(300);

      newsubjudge.setText("この教科名を使うことはできません。");
      newsubjudge.setGraphic(new ImageView(batsu));

      subtf.add(newsubtf);
      subjudge.add(newsubjudge);
      submark.add(new CheckBox());

      sub_change(bp, des, hb);
    });

    minus.setOnAction(e -> {
      for (int i = 0; i < subtf.size(); i++) {
        if (submark.get(i).isSelected()) {
          subtf.remove(i);
          subjudge.remove(i);
          submark.remove(i);
        }
      }

      sub_change(bp, des, hb);
    });

    subsave.setOnAction(e -> {
      for (int i = 0; i < subtf.size(); i++) {
        user.changeSubName(i, subtf.get(i).getText());
      }
    });

    backbt.setDisable(false);

    backbt.setOnAction(e -> {
      home();
    });

    bigbp.setTop(toolbar);

    bigbp.setCenter(tbp);

    setting = new Scene(bigbp);

    stage.setScene(setting);

    when_setting();
  }

  void sub_change(BorderPane bp, Label des, HBox hb) {
    GridPane newgp = new GridPane();
    VBox newvb = new VBox(10);

    for (int i = 0; i < subtf.size(); i++) {
      newgp.add(submark.get(i), 0, i);
      newgp.add(subtf.get(i), 1, i);
      newgp.add(subjudge.get(i), 2, i);
    }

    newgp.setAlignment(Pos.CENTER);

    newvb.getChildren().add(des);
    newvb.getChildren().add(hb);
    newvb.getChildren().add(newgp);

    newvb.setAlignment(Pos.CENTER);

    bp.setCenter(newvb);
  }

  class Inspection_subject implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      TextField t = (TextField) event.getSource();
      String name = t.getText().trim();
      t.setText(name);

      int row = GridPane.getRowIndex(t);
      if (name.isEmpty()) {
        subjudge.get(row).setText("この教科名を使うことはできません。");
        subjudge.get(row).setGraphic(new ImageView(batsu));

        subsavelock++;
        subsave.setDisable(true);
      } else {
        subjudge.get(row).setText("この教科名を使うことができます。");
        subjudge.get(row).setGraphic(new ImageView(maru));

        subsavelock--;
        if (subsavelock == 0)
          subsave.setDisable(false);
      }
    }
  }

  void when_setting() {
    Label des = new Label("試験の変更");
    Button plus = new Button("試験を追加");
    Button minus = new Button("試験を削除");
    whensave = new Button("保存");
    Button upwhen = new Button("↑");
    Button downwhen = new Button("↓");

    VBox vb = new VBox(10);
    GridPane gp = new GridPane();
    HBox hb = new HBox();
    ScrollPane scp = new ScrollPane();
    BorderPane bp = new BorderPane();

    des.setFont(new Font(20));
    plus.setFont(new Font(17));
    minus.setFont(new Font(17));
    whensave.setFont(new Font(17));
    upwhen.setFont(new Font(17));
    downwhen.setFont(new Font(17));

    plus.setPrefWidth(150);
    minus.setPrefWidth(150);
    whensave.setPrefWidth(150);
    whensave.setPrefWidth(100);
    whensave.setPrefWidth(100);

    user.getWhens().stream().map(TextField::new).forEach(whentf::add);
    user.getOmits().stream().map(TextField::new).forEach(omittf::add);
    Stream.generate(Label::new).limit(user.getWhens().size()).forEach(whenjudge::add);
    Stream.generate(Label::new).limit(user.getOmits().size()).forEach(omitjudge::add);
    Stream.generate(CheckBox::new).limit(user.getWhens().size()).forEach(whenmark::add);

    int j = 0;
    for (int i = 0; i < whentf.size(); i++) {
      gp.add(whenmark.get(i), 0, j);
      gp.add(whentf.get(i), 1, j);
      gp.add(whenjudge.get(i), 2, j);
      gp.add(omittf.get(i), 1, j + 1);
      gp.add(omitjudge.get(i), 2, j + 1);
      j += 2;
    }

    gp.setAlignment(Pos.CENTER);

    whentf.parallelStream().forEach(t -> {
      t.setOnAction(new Inspection_when());
      t.setFont(new Font(17));
    });

    omittf.parallelStream().forEach(t -> {
      t.setOnAction(new Inspection_omit());
      t.setFont(new Font(17));
    });

    whenjudge.parallelStream().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefWidth(300);
    });

    omitjudge.parallelStream().forEach(l -> {
      l.setFont(new Font(17));
      l.setPrefWidth(300);
    });

    hb.getChildren().add(plus);
    hb.getChildren().add(minus);
    hb.getChildren().add(whensave);
    hb.getChildren().add(upwhen);
    hb.getChildren().add(downwhen);

    hb.setAlignment(Pos.CENTER);

    vb.getChildren().add(des);
    vb.getChildren().add(hb);
    vb.getChildren().add(gp);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    bp.setPrefHeight(stage.getHeight());
    bp.setPrefWidth(stage.getWidth());

    scp.setContent(bp);

    scp.setHbarPolicy(ScrollBarPolicy.NEVER);

    items[1].setContent(scp);

    plus.setOnAction(e -> {
      TextField newwhentf = new TextField();
      TextField newomittf = new TextField();
      Label newwhenjudge = new Label();
      Label newomitjudge = new Label();

      newwhentf.setOnAction(new Inspection_when());
      newwhentf.setFont(new Font(17));

      newomittf.setOnAction(new Inspection_omit());
      newomittf.setFont(new Font(17));

      newwhenjudge.setFont(new Font(17));
      newwhenjudge.setPrefWidth(300);

      newomitjudge.setFont(new Font(17));
      newomitjudge.setPrefWidth(300);

      newwhenjudge.setText("この試験名を使うことはできません。");
      newomitjudge.setText("この省略名を使うことはできません。");

      newwhenjudge.setGraphic(new ImageView(batsu));
      newomitjudge.setGraphic(new ImageView(batsu));

      whentf.add(newwhentf);
      whenjudge.add(newwhenjudge);
      whenmark.add(new CheckBox());

      omittf.add(newomittf);
      omitjudge.add(newomitjudge);
      whenmark.add(new CheckBox());

      when_change(bp, des, hb);
    });

    minus.setOnAction(e -> {
      for (int i = 0; i < whentf.size(); i++) {
        if (whenmark.get(i).isSelected()) {
          whentf.remove(i);
          whenjudge.remove(i);
          omittf.remove(i);
          omitjudge.remove(i);
          whenmark.remove(i);
        }
      }

      when_change(bp, des, hb);
    });

    whensave.setOnAction(e -> {
      for (int i = 0; i < whentf.size(); i++) {
        user.changeWhen(i, whentf.get(i).getText(), omittf.get(i).getText());
      }
    });

    upwhen.setOnAction(e -> when_updown("UP"));
    downwhen.setOnAction(e -> when_updown("DOWN"));

    user_setting();
  }

  void when_updown(String str) {
    ArrayList<Integer> nexts = new ArrayList<>();
    int val = 0;
    int end = 0;
    int ret = 0;
    int next = 0;

    if (str.equals("UP")) {
      val = -1;
      end = 0;
      ret = whentf.size() - 1;
    } else if (str.equals("DOWN")) {
      val = 1;
      end = whentf.size() - 1;
      ret = 0;
    }

    for (int i = 0; i < whentf.size(); i++) {
      if (whenmark.get(i).isSelected()) {
        if (i == end)
          next = ret;
        else
          next = i + val;

        whenmark.get(i).setSelected(false);

        String tstr;
        ImageView timg;

        tstr = whentf.get(next).getText();
        whentf.get(next).setText(whentf.get(i).getText());
        whentf.get(i).setText(tstr);

        tstr = omittf.get(next).getText();
        omittf.get(next).setText(omittf.get(i).getText());
        omittf.get(i).setText(tstr);

        tstr = whenjudge.get(next).getText();
        whenjudge.get(next).setText(whenjudge.get(i).getText());
        whenjudge.get(i).setText(tstr);

        tstr = omitjudge.get(next).getText();
        omitjudge.get(next).setText(omitjudge.get(i).getText());
        omitjudge.get(i).setText(tstr);

        timg = (ImageView) whenjudge.get(next).getGraphic();
        whenjudge.get(next).setGraphic(whenjudge.get(i).getGraphic());
        whenjudge.get(i).setGraphic(timg);

        timg = (ImageView) omitjudge.get(next).getGraphic();
        omitjudge.get(next).setGraphic(omitjudge.get(i).getGraphic());
        omitjudge.get(i).setGraphic(timg);

        nexts.add(next);
      }
    }

    for (Integer n : nexts) {
      whenmark.get(n).setSelected(true);
    }
  }

  void when_change(BorderPane bp, Label des, HBox hb) {
    GridPane newgp = new GridPane();
    VBox newvb = new VBox(10);

    int j = 0;
    for (int i = 0; i < whentf.size(); i++) {
      newgp.add(whenmark.get(i), 0, j);
      newgp.add(whentf.get(i), 1, j);
      newgp.add(whenjudge.get(i), 2, j);
      newgp.add(omittf.get(i), 1, j + 1);
      newgp.add(omitjudge.get(i), 2, j + 1);
      j += 2;
    }

    newgp.setAlignment(Pos.CENTER);

    newvb.getChildren().add(des);
    newvb.getChildren().add(hb);
    newvb.getChildren().add(newgp);

    newvb.setAlignment(Pos.CENTER);

    bp.setCenter(newvb);
  }

  class Inspection_when implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      TextField t = (TextField) event.getSource();
      String name = t.getText().trim();
      t.setText(name);

      int row = GridPane.getRowIndex(t) / 2;

      if (name.isEmpty()) {
        whenjudge.get(row).setText("この試験名を使うことはできません。");
        whenjudge.get(row).setGraphic(new ImageView(batsu));

        whensavelock++;
        whensave.setDisable(true);
      } else {
        whenjudge.get(row).setText("この試験名を使うことができます。");
        whenjudge.get(row).setGraphic(new ImageView(maru));

        whensavelock--;
        if (whensavelock == 0)
          whensave.setDisable(false);
      }
    }
  }

  class Inspection_omit implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      TextField t = (TextField) event.getSource();
      String name = t.getText().trim();
      t.setText(name);

      int row = (GridPane.getRowIndex(t) - 1) / 2;

      if (name.isEmpty() || name.length() > 4) {
        omitjudge.get(row).setText("この省略名を使うことはできません。");
        omitjudge.get(row).setGraphic(new ImageView(batsu));

        whensavelock++;
        whensave.setDisable(true);
      } else {
        omitjudge.get(row).setText("この省略名を使うことができます。");
        omitjudge.get(row).setGraphic(new ImageView(maru));

        whensavelock--;
        if (whensavelock == 0)
          whensave.setDisable(false);
      }
    }
  }

  void user_setting() {
    Label[] contentlb = new Label[2];
    TextField[] contenttf = new TextField[2];

    GridPane gp = new GridPane();
    BorderPane bp = new BorderPane();

    contentlb[0] = new Label("点数の上限：");
    contentlb[1] = new Label("点数の下限：");

    contenttf[0] = new TextField(String.valueOf(user.getMax()));
    contenttf[1] = new TextField(String.valueOf(user.getMin()));

    for (int i = 0; i < contentlb.length; i++) {
      contentlb[i].setFont(new Font(17));
      contentlb[i].setPrefWidth(100);

      contenttf[i].setFont(new Font(17));
      contenttf[i].setPrefWidth(200);
      contenttf[i].setAlignment(Pos.CENTER);

      gp.add(contentlb[i], 0, i);
      gp.add(contenttf[i], 2, i);
    }

    gp.setAlignment(Pos.CENTER);

    gp.setVgap(10);

    bp.setCenter(gp);

    bp.setPrefHeight(stage.getHeight());
    bp.setPrefWidth(stage.getWidth());

    items[2].setContent(bp);
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
      Roster.createNewFile();
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
