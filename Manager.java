import java.io.*;
import java.util.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.event.*;

public class Manager extends Application {
  public Stage stage;

  public final String[] subjects = { "国語", "社会", "数学", "理科", "英語", "美術", "技術", "家庭", "保健体育", "音楽" };
  public final String[] when = { "１学期実力テスト", "１学期中間テスト", "１学期期末テスト", "２学期確認テスト", "２学期中間テスト", "２学期期末テスト", "３学期実力テスト",
      "３学期学年末テスト" };

  private Scene welcome;
  private Scene make_acc;
  private Scene select_sub;
  private Scene select_when;
  private Scene input_sco;

  private Image maru;
  private Image batsu;

  private ArrayList<Subject> subsList;
  private String usewhen;

  private TextField actf;
  private PasswordField pwf1;
  private Label msg;
  private Button ok;

  private int dis = 0;

  private Label sub;
  private Label msg1;
  private TextField scotf;
  public static int count = 0;

  private Scene check_sco;

  public static void main(String[] args) {
    launch(args);
  }

  public void init() throws Exception {
    maru = new Image("Image\\マル.png", 50, 0, true, false);
    batsu = new Image("Image\\バツ.png", 50, 0, true, false);
  }

  public void start(Stage temp) throws Exception {
    stage = temp;
    stage.setTitle("Exam-manager");

    stage.setHeight(400);
    stage.setWidth(600);

    subsList = new ArrayList<Subject>();

    welcome();

    stage.show();
  }

  void welcome() {
    // welcomeの画面
    Label wel = new Label("Exam-manager にようこそ\n");
    Button nw = new Button("新規作成");
    Button op = new Button("ログイン");

    wel.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));

    nw.setFont(new Font(20));
    op.setFont(new Font(20));

    nw.setPrefWidth(150);
    op.setPrefWidth(150);

    nw.setTooltip(new Tooltip("アカウントを新規作成します"));
    op.setTooltip(new Tooltip("アカウントにログインします"));

    BorderPane bp1 = new BorderPane();
    VBox home = new VBox(20);

    home.getChildren().add(wel);
    home.getChildren().add(nw);
    home.getChildren().add(op);

    home.setAlignment(Pos.CENTER);
    bp1.setCenter(home);

    welcome = new Scene(bp1);

    // 「新規」を押すと、アカウント作成画面へ
    nw.setOnAction(e -> {
      make_acc();
    });

    stage.setScene(welcome);
  }

  void make_acc() {
    // アカウント作成画面
    Label des = new Label("アカウントを作成しましょう");
    Label acc = new Label("アカウント名：");
    Label pas = new Label("パスワード：");

    actf = new TextField();
    pwf1 = new PasswordField();
    msg = new Label();
    ok = new Button("  OK  ");

    des.setFont(new Font(18));
    acc.setFont(new Font(17));
    pas.setFont(new Font(17));
    msg.setFont(new Font(18));
    actf.setFont(new Font(15));
    pwf1.setPrefWidth(300);
    actf.setPrefHeight(30);
    pwf1.setPrefHeight(30);
    ok.setDisable(true);

    actf.setOnAction(new Check_name());

    ok.setFont(new Font(15));

    BorderPane bp2 = new BorderPane();
    VBox acvb = new VBox(20);
    GridPane gp1 = new GridPane();

    gp1.add(acc, 0, 0);
    gp1.add(pas, 0, 1);
    gp1.add(actf, 1, 0);
    gp1.add(pwf1, 1, 1);

    gp1.setVgap(10);
    gp1.setAlignment(Pos.CENTER);

    acvb.getChildren().add(des);
    acvb.getChildren().add(gp1);
    acvb.getChildren().add(msg);
    acvb.getChildren().add(ok);

    acvb.setAlignment(Pos.CENTER);

    bp2.setCenter(acvb);

    make_acc = new Scene(bp2);

    stage.setScene(make_acc);
  }

  class Check_name implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      // アカウント名が有効か判定する
      int c = 0;
      if (c == 0) {
        // アカウント名確定のためのアラート
        Alert really = new Alert(Alert.AlertType.CONFIRMATION);
        really.setTitle("確認");
        really.getDialogPane().setHeaderText("本当に " + actf.getText() + " がアカウント名でいいですか？");

        msg.setText("このアカウント名を使うことができます。");
        msg.setGraphic(new ImageView(maru));
        ok.setDisable(false);

        ok.setOnAction(e -> {
          // 「OK」を押すと、アラートを表示
          Optional<ButtonType> res = really.showAndWait();
          // 「OK」を押すと、教科選択画面へ
          if (res.get() == ButtonType.OK) {
            select_sub();
          }
        });
      } else {
        msg.setText("このアカウント名を使うことはできません。");
        msg.setGraphic(new ImageView(batsu));
      }
    }
  }

  void select_sub() {
    // 教科選択画面
    Label des1 = new Label("教科を選んでください。");
    CheckBox[] subs = new CheckBox[subjects.length];
    Button ok1 = new Button("  OK  ");
    GridPane gp2 = new GridPane();

    VBox subvb = new VBox(10);

    des1.setFont(new Font(18));
    subvb.getChildren().add(des1);

    ok1.setFont(new Font(15));

    int x = 0, y = 0;
    for (int i = 0; i < subjects.length; i++) {
      subs[i] = new CheckBox(subjects[i]);
      subs[i].setFont(new Font(15));
      subs[i].setPrefWidth(100);
      if (i % 3 == 0) {
        x = 0;
        y++;
      }
      gp2.add(subs[i], x, y);
      subs[i].setOnAction(e -> {
        // １つ以上チェックされていないと「OK」を無効にする
        CheckBox t = (CheckBox) e.getSource();
        if (t.isSelected()) {
          dis++;
        } else {
          dis--;
        }
        if (dis == 0) {
          ok1.setDisable(true);
        } else {
          ok1.setDisable(false);
        }
      });
      x++;
    }

    ok1.setDisable(true);

    gp2.setHgap(10);
    gp2.setVgap(10);
    gp2.setAlignment(Pos.CENTER);

    subvb.getChildren().add(gp2);
    subvb.getChildren().add(ok1);

    subvb.setAlignment(Pos.CENTER);

    BorderPane bp3 = new BorderPane();

    bp3.setCenter(subvb);

    select_sub = new Scene(bp3);

    ok1.setOnAction(e -> {
      // 「OK」を押すと、試験選択画面へ
      select_when();
      // チェックされた分、Subjectのオブジェクトを生成
      for (int i = 0; i < subs.length; i++) {
        if (subs[i].isSelected()) {
          subsList.add(new Subject(subs[i].getText()));
        }
      }
    });

    stage.setScene(select_sub);
  }

  void select_when() {
    // 試験選択画面
    Label des2 = new Label("いつの試験か選んでください。");
    RadioButton[] wh = new RadioButton[when.length];
    ToggleGroup whtg = new ToggleGroup();
    Button ok2 = new Button("  OK  ");

    BorderPane bp4 = new BorderPane();
    VBox whvb = new VBox(10);

    des2.setFont(new Font(18));
    whvb.getChildren().add(des2);

    ok2.setFont(new Font(15));

    for (int i = 0; i < when.length; i++) {
      wh[i] = new RadioButton(when[i]);
      wh[i].setFont(new Font(15));
      wh[i].setPrefWidth(150);
      wh[i].setToggleGroup(whtg);
      whvb.getChildren().add(wh[i]);
    }
    wh[0].setSelected(true);

    whvb.getChildren().add(ok2);

    whvb.setAlignment(Pos.CENTER);

    bp4.setCenter(whvb);

    select_when = new Scene(bp4);

    ok2.setOnAction(e -> {
      // どの試験なのか記憶
      Toggle sel = whtg.getSelectedToggle();
      RadioButton t = (RadioButton) sel;
      usewhen = t.getText();
      // 「OK」を押すと、点数入力画面へ
      input_sco();
    });

    stage.setScene(select_when);
  }

  void input_sco() {
    // 点数入力画面
    Label selwh = new Label(usewhen);
    sub = new Label(subsList.get(0).getName());
    msg1 = new Label();
    scotf = new TextField();
    Button[] tenkey = new Button[12];

    selwh.setFont(new Font(20));
    sub.setFont(Font.font("SansSerif", FontWeight.BLACK, 25));
    msg1.setFont(new Font(17));
    scotf.setFont(new Font(30));

    BorderPane bp5 = new BorderPane();
    VBox vb1 = new VBox(20);
    HBox hb1 = new HBox(30);
    GridPane gp3 = new GridPane();

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
      gp3.add(tenkey[i], x, y);
      x++;
    }

    tenkey[0] = new Button(String.valueOf(0));
    tenkey[10] = new Button("Clear");
    tenkey[11] = new Button("Enter");

    gp3.add(tenkey[0], 1, 4);
    gp3.add(tenkey[10], 0, 4);
    gp3.add(tenkey[11], 2, 4);

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

    gp3.setAlignment(Pos.CENTER);

    vb1.getChildren().add(selwh);
    vb1.getChildren().add(sub);
    vb1.getChildren().add(scotf);
    vb1.getChildren().add(msg1);
    vb1.setAlignment(Pos.CENTER);

    hb1.getChildren().add(vb1);
    hb1.getChildren().add(gp3);
    hb1.setAlignment(Pos.CENTER);

    bp5.setCenter(hb1);

    input_sco = new Scene(bp5);

    stage.setScene(input_sco);
  }

  class Check_score implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      // 有効な数値か判定
      try {
        int score = Integer.parseInt(scotf.getText());
        if (score < 0 || score > 100) {
          // 範囲内かの判定
          msg1.setText("有効な数値ではありません。");
          msg1.setGraphic(new ImageView(batsu));
          scotf.setText("");
        } else {
          msg1.setText("");
          msg1.setGraphic(null);
          subsList.get(count).setScore(score);
          count++;
          if (count >= subsList.size()) {
            // 最後の教科が終わると点数確認画面へ
            check_sco();
          } else {
            // 次の教科へ
            sub.setText(subsList.get(count).getName());
            scotf.setText("");
          }
        }
      } catch (NumberFormatException exp) {
        // 文字が含まれていた場合
        msg1.setText("数値として読み取ることができません。");
        msg1.setGraphic(new ImageView(batsu));
        scotf.setText("");
      }
    }
  }

  void check_sco() {
    // 点数確認画面
    Label right = new Label("この点数でいいですか？");
    Button ok3 = new Button("  OK  ");
    Label[] subch = new Label[subsList.size()];
    TextField[] scoch = new TextField[subsList.size()];

    GridPane gp4 = new GridPane();
    VBox chvb = new VBox(10);
    BorderPane bp6 = new BorderPane();

    ok3.setFont(new Font(15));
    right.setFont(new Font(18));

    for (int i = 0; i < subsList.size(); i++) {
      subch[i] = new Label(subsList.get(i).getName() + "：");
      scoch[i] = new TextField();
      subch[i].setFont(new Font(15));
      scoch[i].setFont(new Font(15));
      subch[i].setPrefWidth(100);
      scoch[i].setPrefWidth(150);
      subch[i].setAlignment(Pos.CENTER_RIGHT);
      scoch[i].setText(String.valueOf(subsList.get(i).getScore()));
      gp4.add(subch[i], 0, i);
      gp4.add(scoch[i], 1, i);
    }

    gp4.setAlignment(Pos.CENTER);
    gp4.setVgap(10);

    chvb.getChildren().add(right);
    chvb.getChildren().add(gp4);
    chvb.getChildren().add(ok3);

    chvb.setAlignment(Pos.CENTER);

    bp6.setCenter(chvb);

    check_sco = new Scene(bp6);

    stage.setScene(check_sco);
  }
}
