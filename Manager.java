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
import javafx.geometry.*;
import javafx.event.*;
import javafx.collections.*;
import javafx.beans.property.*;

public class Manager extends Application {
  public Stage stage;

  public final String[] subnames = { "����", "�Љ�", "���w", "����", "�p��", "���p", "�Z�p", "�ƒ�", "�ی��̈�", "���y" };
  public final String[] when = { "�P�w�����̓e�X�g", "�P�w�����ԃe�X�g", "�P�w�������e�X�g", "�Q�w���m�F�e�X�g", "�Q�w�����ԃe�X�g", "�Q�w�������e�X�g", "�R�w�����̓e�X�g",
      "�R�w���w�N���e�X�g" };

  private Scene welcome;
  private Scene make_acc;
  private Scene login_acc;
  private Scene home;
  private Scene select_sub;
  private Scene select_when;
  private Scene input_sco;
  private Scene check_sco;
  private Scene updown;
  private Scene database;

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
  private boolean acwrote = false;
  private boolean pswrote = false;

  public static void main(String[] args) {
    launch(args);
  }

  public void init() throws Exception {
    maru = new Image("file:Image/�}��.png", 50, 0, true, false);
    batsu = new Image("file:Image/�o�c.png", 50, 0, true, false);
    pencil = new Image("file:Image/�G���s�c.png", 30, 0, true, false);
    up = new Image("file:Image/�A�b�v.png", 30, 0, true, false);
    down = new Image("file:Image/�_�E��.png", 30, 0, true, false);
    flat = new Image("file:Image/�t���b�g.png", 30, 0, true, false);
    bar = new Image("file:Image/�o�[.png", 30, 0, true, false);

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
    // welcome�̉��
    Label des = new Label("Exam-manager �ɂ悤����\n");
    Button nw = new Button("�V�K�쐬");
    Button op = new Button("���O�C��");

    des.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));

    nw.setFont(new Font(20));
    op.setFont(new Font(20));

    nw.setPrefWidth(150);
    op.setPrefWidth(150);

    nw.setTooltip(new Tooltip("�A�J�E���g��V�K�쐬���܂�"));
    op.setTooltip(new Tooltip("�A�J�E���g�Ƀ��O�C�����܂�"));

    BorderPane bp = new BorderPane();
    VBox home = new VBox(20);

    home.getChildren().add(des);
    home.getChildren().add(nw);
    home.getChildren().add(op);

    home.setAlignment(Pos.CENTER);
    bp.setCenter(home);

    welcome = new Scene(bp);

    // �u�V�K�v�������ƁA�A�J�E���g�쐬��ʂ�
    nw.setOnAction(e -> {
      make_acc();
    });

    op.setOnAction(e -> {
      login_acc();
    });
    stage.setScene(welcome);
  }

  void make_acc() {
    // �A�J�E���g�쐬���
    Label des = new Label("�A�J�E���g���쐬���܂��傤");
    Label acc = new Label("�A�J�E���g���F");
    Label pas = new Label("�p�X���[�h�F");

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

      // �A�J�E���g�����L�������肷��
      if (name.length() == 0 || usersname.contains(name)) {
        msg1.setText("���̃A�J�E���g�����g�����Ƃ͂ł��܂���B");
        msg1.setGraphic(new ImageView(batsu));
        ok.setDisable(true);
        acwrote = false;
      } else {
        msg1.setText("���̃A�J�E���g�����g�����Ƃ��ł��܂��B");
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

      // �p�X���[�h���L�������肷��
      if (pass.length() >= 6 && acwrote) {
        msg2.setText("���̃p�X���[�h���g�����Ƃ��ł��܂��B");
        msg2.setGraphic(new ImageView(maru));
        ok.setDisable(false);
        pswrote = true;
        ok.setOnAction(e -> {
          // �A�J�E���g���m��̂��߂̃A���[�g
          Alert really = new Alert(Alert.AlertType.CONFIRMATION);
          really.setTitle("�m�F");
          really.getDialogPane().setHeaderText("�{���� " + actf1.getText() + " ���A�J�E���g���ł����ł����H");

          // �uOK�v�������ƁA�A���[�g��\��
          Optional<ButtonType> res = really.showAndWait();
          if (res.get() == ButtonType.OK) {
            // �uOK�v�������ƁAUser�̃I�u�W�F�N�g�𐶐����A���ȑI����ʂ�
            user = new User(actf1.getText(), pass);
            try {
              usersname.add(actf1.getText());
              roster_write();
            } catch (Exception exp) {
              Alert err = new Alert(Alert.AlertType.ERROR);
              err.setTitle("�G���[");
              err.getDialogPane().setHeaderText("�A�J�E���g���ƃp�X���[�h��ۑ��ł��܂���B");
              Optional<ButtonType> reserr = really.showAndWait();
              if (reserr.get() == ButtonType.OK) {
                System.exit(1);
              }
            }
            home();
          }
        });
      } else {
        msg2.setText("�p�X���[�h�̒���������܂���");
        msg2.setGraphic(new ImageView(batsu));
        ok.setDisable(true);
        pswrote = false;
      }
    }
  }

  void login_acc() {
    // ���O�C�����
    Label des = new Label("���O�C�����܂��傤");
    Label acc = new Label("�A�J�E���g���F");
    Label pas = new Label("�p�X���[�h�F");

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
        msg3.setText("�A�J�E���g��������܂����B");
        msg3.setGraphic(new ImageView(maru));
        data_read();
        if (user.getPassword().equals(pwf2.getText())) {
          msg4.setText("�p�X���[�h����v���܂����B");
          msg4.setGraphic(new ImageView(maru));
          ok1.setDisable(false);
        } else {
          if (pwf2.getText().length() != 0) {
            msg4.setText("�p�X���[�h����v���܂���B");
            msg4.setGraphic(new ImageView(batsu));
            ok1.setDisable(true);
          } else {
            msg4.setText("");
            msg4.setGraphic(null);
          }
        }
      } else {
        if (actf2.getText().length() != 0) {
          msg3.setText("�A�J�E���g��������܂���B");
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
      err.setTitle("�G���[");
      err.getDialogPane().setHeaderText("�f�[�^��ǂݍ��ނ��ƂɎ��s���܂����B\n" + exp);
      Optional<ButtonType> reserr = err.showAndWait();
      if (reserr.get() == ButtonType.OK) {
        System.exit(1);
      }
    }
  }

  void home() {
    // ���[�U�[�̃z�[�����
    Label name = new Label(user.getName() + " ����");
    Button newex = new Button("�V�K����");
    Button dataex = new Button("�f�[�^�x�[�X");

    name.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));
    newex.setFont(new Font(20));
    dataex.setFont(new Font(20));

    newex.setPrefWidth(180);
    dataex.setPrefWidth(180);

    newex.setOnAction(e -> {
      select_sub();
    });

    dataex.setOnAction(e -> {
      database();
    });

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
    // ���ȑI�����
    Label des = new Label("���Ȃ�I��ł��������B");
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
        // �P�ȏ�`�F�b�N����Ă��Ȃ��ƁuOK�v�𖳌��ɂ���
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
      // �uOK�v�������ƁA�����I����ʂ�
      select_when();
      // �`�F�b�N���ꂽ���ASubject�̃I�u�W�F�N�g�𐶐�
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
    // �����I�����
    Label des = new Label("���̎������I��ł��������B");
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
      // �ǂ̎����Ȃ̂��L��
      Toggle sel = whtg.getSelectedToggle();
      RadioButton t = (RadioButton) sel;
      usewhen = t.getText();
      // Exam�N���X�̃I�u�W�F�N�g�𐶐�
      exam = new Exam(usewhen);
      // �uOK�v�������ƁA�_�����͉�ʂ�
      count = 0;
      input_sco();
    });

    stage.setScene(select_when);
  }

  void input_sco() {
    // �_�����͉��
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

    // �e���L�[�̎���
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
          // �����ƃe�L�X�g�t�B�[���h�ɓ��͂���Ă���
          Button tmp = (Button) e.getSource();
          scotf.setText(scotf.getText() + tmp.getText());
        });
      }
    }

    tenkey[10].setOnAction(e -> {
      // �e�L�X�g�t�B�[���h���N���A
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
      // �L���Ȑ��l������
      try {
        int score = Integer.parseInt(scotf.getText());
        if (score < 0 || score > 100) {
          // �͈͓����̔���
          check.setText("�L���Ȑ��l�ł͂���܂���B");
          check.setGraphic(new ImageView(batsu));
          scotf.setText("");
        } else {
          subsMap.get(usesubs.get(count)).setScore(score);
          count++;
          if (count >= subsMap.size() || rand) {
            // �Ō�̋��Ȃ��I���Ɠ_���m�F��ʂ�
            // �_���ύX�ɂ�郉���_���A�N�Z�X�Ȃ�Γ_���m�F��ʂ�
            rand = false;
            check_sco();
          } else {
            // ����̓��͂Ȃ�Ύ��̋��Ȃ�
            input_sco();
          }
        }
      } catch (NumberFormatException exp) {
        // �������܂܂�Ă����ꍇ
        check.setText("���l�Ƃ��ēǂݎ�邱�Ƃ��ł��܂���B");
        check.setGraphic(new ImageView(batsu));
        scotf.setText("");
      }
    }
  }

  void check_sco() {
    // �_���m�F���
    Label des = new Label("���̓_���ł����ł����H");
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
      subch[i] = new Label(usesubs.get(i) + "�F");
      scoch[i] = new Label(String.valueOf(subsMap.get(usesubs.get(i)).getScore()) + " �_");
      change[i] = new Button("�ύX");
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
        // �u�ύX�v�������ꂽ��A���̋��Ȃ̓_�����͉�ʂɈڂ�
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
    // ���ʕ񍐉��
    Label des = new Label("��������");
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
        sublb[i].setText("���v�_�F");
        subvl[i].setText(String.valueOf(exam.getTotal()) + " �_");
      } else if (i == 1) {
        sublb[i].setText("���ϓ_");
        subvl[i].setText(String.valueOf(String.format("%.1f", exam.getAverage())) + " �_");
      } else {
        sublb[i].setText(String.valueOf(exam.getSubDataInt(i - 2).getName() + "�F"));
        subvl[i].setText(String.valueOf(exam.getSubDataInt(i - 2).getScore()) + " �_");
      }

      if (user.getExamsize() > 1) {
        if (i == 0) {
          now = exam.getTotal();
          last = user.getExam(user.getExamsize() - 2).getTotal();
          subdif[i].setText(String.valueOf(String.format("%.0f", Math.abs(now - last))) + " �_");
        } else if (i == 1) {
          now = exam.getAverage();
          last = user.getExam(user.getExamsize() - 2).getAverage();
          subdif[i].setText(String.valueOf(String.format("%.1f", Math.abs(now - last))) + " �_");
        } else {
          if (user.getExam(user.getExamsize() - 2).getSubNameAll().contains(usesubs.get(i - 2))) {
            now = exam.getSubDataInt(i - 2).getScore();
            last = user.getExam(user.getExamsize() - 2).getSubDataInt(i - 2).getScore();
            subdif[i].setText(String.valueOf(String.format("%.0f", Math.abs(now - last))) + " �_");
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

  void database() {
    Label des = new Label(user.getName() + " ����̃f�[�^�x�[�X");
    Tab[] tabs = new Tab[subnames.length + 1];
    TabPane tbp = new TabPane();

    des.setFont(new Font(18));

    for (int i = 0; i < subnames.length + 1; i++) {
      TableView<RowSubData> tv = new TableView<>();
      ObservableList<RowSubData> ovl = FXCollections.observableArrayList();
      TableColumn<RowSubData, String> tc1 = new TableColumn<RowSubData, String>("����");
      TableColumn<RowSubData, String> tc2 = new TableColumn<RowSubData, String>("�_��");
      TableColumn<RowSubData, String> tc3 = new TableColumn<RowSubData, String>("����");

      tc1.setCellValueFactory(new PropertyValueFactory<RowSubData, String>("examname"));
      tc2.setCellValueFactory(new PropertyValueFactory<RowSubData, String>("score"));
      tc3.setCellValueFactory(new PropertyValueFactory<RowSubData, String>("date"));

      for (int j = 0; j < user.getExamsize(); j++) {
        Exam t = user.getExam(j);
        if (i == 0) {
          tabs[i] = new Tab("����");
          ovl.add(new RowSubData(t.getName(), t.getAverage(), t.getCalendar()));
        } else {
          tabs[i] = new Tab(subnames[i - 1]);
          if (t.getSubNameAll().contains(subnames[i - 1])) {
            ovl.add(new RowSubData(t.getName(), (float) t.getSubData(subnames[i - 1]).getScore(), t.getCalendar()));
          }
        }
      }

      tv.getColumns().add(tc1);
      tv.getColumns().add(tc2);
      tv.getColumns().add(tc3);

      tv.setItems(ovl);

      tabs[i].setContent(tv);
      tbp.getTabs().add(tabs[i]);
    }

    VBox vb = new VBox(10);
    BorderPane bp = new BorderPane();

    vb.getChildren().add(des);
    vb.getChildren().add(tbp);

    vb.setAlignment(Pos.CENTER);

    bp.setCenter(vb);

    database = new Scene(bp);

    stage.setScene(database);
  }

  public class RowSubData {
    private final SimpleStringProperty examname;
    private final SimpleFloatProperty score;
    private final SimpleStringProperty date;

    RowSubData(String exn, Float sco, Calendar cal) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/(E)");

      examname = new SimpleStringProperty(exn);
      score = new SimpleFloatProperty(sco);
      date = new SimpleStringProperty(String.valueOf(sdf.format(cal.getTime())));
    }

    public SimpleStringProperty examnameProperty() {
      return examname;
    }

    public SimpleFloatProperty scoreProperty() {
      return score;
    }

    public SimpleStringProperty dateProperty() {
      return date;
    }
  }

  void data_write() {
    try {
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Datas/" + user.getName() + ".ser"));
      os.writeObject(user);
      os.close();
    } catch (Exception exp) {
      Alert err = new Alert(Alert.AlertType.ERROR);
      err.setTitle("�G���[");
      err.getDialogPane().setHeaderText("�f�[�^���������ނ��ƂɎ��s���܂����B\n�f�[�^��������\��������܂��B\n" + exp);
      Optional<ButtonType> reserr = err.showAndWait();
      if (reserr.get() == ButtonType.OK) {
        System.exit(1);
      }
    }
  }

  void roster_write() throws Exception {
    PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(Roster.getPath())));
    for (String t : usersname) {
      pw.println(t);
    }
    pw.close();
  }

  public void stop() {
    if (user != null) {
      data_write();
    }
  }
}
