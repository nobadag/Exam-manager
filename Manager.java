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

  public final String[] subjects = { "����", "�Љ�", "���w", "����", "�p��", "���p", "�Z�p", "�ƒ�", "�ی��̈�", "���y" };
  public final String[] when = { "�P�w�����̓e�X�g", "�P�w�����ԃe�X�g", "�P�w�������e�X�g", "�Q�w���m�F�e�X�g", "�Q�w�����ԃe�X�g", "�Q�w�������e�X�g", "�R�w�����̓e�X�g",
      "�R�w���w�N���e�X�g" };

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
    maru = new Image("Image\\�}��.png", 50, 0, true, false);
    batsu = new Image("Image\\�o�c.png", 50, 0, true, false);
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
    // welcome�̉��
    Label wel = new Label("Exam-manager �ɂ悤����\n");
    Button nw = new Button("�V�K�쐬");
    Button op = new Button("���O�C��");

    wel.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));

    nw.setFont(new Font(20));
    op.setFont(new Font(20));

    nw.setPrefWidth(150);
    op.setPrefWidth(150);

    nw.setTooltip(new Tooltip("�A�J�E���g��V�K�쐬���܂�"));
    op.setTooltip(new Tooltip("�A�J�E���g�Ƀ��O�C�����܂�"));

    BorderPane bp1 = new BorderPane();
    VBox home = new VBox(20);

    home.getChildren().add(wel);
    home.getChildren().add(nw);
    home.getChildren().add(op);

    home.setAlignment(Pos.CENTER);
    bp1.setCenter(home);

    welcome = new Scene(bp1);

    // �u�V�K�v�������ƁA�A�J�E���g�쐬��ʂ�
    nw.setOnAction(e -> {
      make_acc();
    });

    stage.setScene(welcome);
  }

  void make_acc() {
    // �A�J�E���g�쐬���
    Label des = new Label("�A�J�E���g���쐬���܂��傤");
    Label acc = new Label("�A�J�E���g���F");
    Label pas = new Label("�p�X���[�h�F");

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
      // �A�J�E���g�����L�������肷��
      int c = 0;
      if (c == 0) {
        // �A�J�E���g���m��̂��߂̃A���[�g
        Alert really = new Alert(Alert.AlertType.CONFIRMATION);
        really.setTitle("�m�F");
        really.getDialogPane().setHeaderText("�{���� " + actf.getText() + " ���A�J�E���g���ł����ł����H");

        msg.setText("���̃A�J�E���g�����g�����Ƃ��ł��܂��B");
        msg.setGraphic(new ImageView(maru));
        ok.setDisable(false);

        ok.setOnAction(e -> {
          // �uOK�v�������ƁA�A���[�g��\��
          Optional<ButtonType> res = really.showAndWait();
          // �uOK�v�������ƁA���ȑI����ʂ�
          if (res.get() == ButtonType.OK) {
            select_sub();
          }
        });
      } else {
        msg.setText("���̃A�J�E���g�����g�����Ƃ͂ł��܂���B");
        msg.setGraphic(new ImageView(batsu));
      }
    }
  }

  void select_sub() {
    // ���ȑI�����
    Label des1 = new Label("���Ȃ�I��ł��������B");
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
        // �P�ȏ�`�F�b�N����Ă��Ȃ��ƁuOK�v�𖳌��ɂ���
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
      // �uOK�v�������ƁA�����I����ʂ�
      select_when();
      // �`�F�b�N���ꂽ���ASubject�̃I�u�W�F�N�g�𐶐�
      for (int i = 0; i < subs.length; i++) {
        if (subs[i].isSelected()) {
          subsList.add(new Subject(subs[i].getText()));
        }
      }
    });

    stage.setScene(select_sub);
  }

  void select_when() {
    // �����I�����
    Label des2 = new Label("���̎������I��ł��������B");
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
      // �ǂ̎����Ȃ̂��L��
      Toggle sel = whtg.getSelectedToggle();
      RadioButton t = (RadioButton) sel;
      usewhen = t.getText();
      // �uOK�v�������ƁA�_�����͉�ʂ�
      input_sco();
    });

    stage.setScene(select_when);
  }

  void input_sco() {
    // �_�����͉��
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

    // �e���L�[�̎���
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
      // �L���Ȑ��l������
      try {
        int score = Integer.parseInt(scotf.getText());
        if (score < 0 || score > 100) {
          // �͈͓����̔���
          msg1.setText("�L���Ȑ��l�ł͂���܂���B");
          msg1.setGraphic(new ImageView(batsu));
          scotf.setText("");
        } else {
          msg1.setText("");
          msg1.setGraphic(null);
          subsList.get(count).setScore(score);
          count++;
          if (count >= subsList.size()) {
            // �Ō�̋��Ȃ��I���Ɠ_���m�F��ʂ�
            check_sco();
          } else {
            // ���̋��Ȃ�
            sub.setText(subsList.get(count).getName());
            scotf.setText("");
          }
        }
      } catch (NumberFormatException exp) {
        // �������܂܂�Ă����ꍇ
        msg1.setText("���l�Ƃ��ēǂݎ�邱�Ƃ��ł��܂���B");
        msg1.setGraphic(new ImageView(batsu));
        scotf.setText("");
      }
    }
  }

  void check_sco() {
    // �_���m�F���
    Label right = new Label("���̓_���ł����ł����H");
    Button ok3 = new Button("  OK  ");
    Label[] subch = new Label[subsList.size()];
    TextField[] scoch = new TextField[subsList.size()];

    GridPane gp4 = new GridPane();
    VBox chvb = new VBox(10);
    BorderPane bp6 = new BorderPane();

    ok3.setFont(new Font(15));
    right.setFont(new Font(18));

    for (int i = 0; i < subsList.size(); i++) {
      subch[i] = new Label(subsList.get(i).getName() + "�F");
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
