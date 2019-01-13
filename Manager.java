import java.util.*;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;

public class Manager extends Application {
  public static Stage stage;
  public final String[] subjects = { "����", "�Љ�", "���w", "����", "�p��", "���p", "�Z�p", "�ƒ�", "�ی��̈�", "���y" };

  private Scene setting;
  private TextField tf;
  private Label msg;
  private Button ok;

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage temp) throws Exception {
    stage = temp;
    stage.setTitle("Exam-manager");

    // welcome�̉��
    Label wel = new Label("Exam-manager �ɂ悤����\n");
    Button nw = new Button("�V�K");
    Button op = new Button("�J��");

    wel.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));

    nw.setFont(new Font(20));
    op.setFont(new Font(20));

    BorderPane bp1 = new BorderPane();
    VBox home = new VBox(20);

    home.getChildren().add(wel);
    home.getChildren().add(nw);
    home.getChildren().add(op);

    home.setAlignment(Pos.CENTER);
    bp1.setCenter(home);

    Scene welcome = new Scene(bp1, 600, 400);

    stage.setScene(welcome);

    // �A�J�E���g�쐬�̉��
    Label acc = new Label("�A�J�E���g������͂��Ă��������B");
    tf = new TextField();
    msg = new Label();
    ok = new Button("  OK  ");

    acc.setFont(new Font(20));
    msg.setFont(new Font(20));
    ok.setDisable(true);
    tf.setOnAction(new Check_name());

    BorderPane bp2 = new BorderPane();
    VBox acvb = new VBox(20);

    acvb.getChildren().add(acc);
    acvb.getChildren().add(tf);
    acvb.getChildren().add(msg);
    acvb.getChildren().add(ok);

    acvb.setAlignment(Pos.CENTER);

    bp2.setCenter(acvb);

    Scene make_acc = new Scene(bp2, 600, 400);

    // �u�V�K�v�������ƁAmake_acc �V�[����
    nw.setOnAction(e -> {
      stage.setScene(make_acc);
    });

    // ���ȑI��
    Label des = new Label("���Ȃ�I��ł��������B");
    CheckBox[] subs = new CheckBox[subjects.length];
    Button ok1 = new Button("  OK  ");

    BorderPane bp3 = new BorderPane();
    VBox subvb = new VBox(10);

    des.setFont(new Font(15));
    subvb.getChildren().add(des);
    for (int i = 0; i < subjects.length; i++) {
      subs[i] = new CheckBox(subjects[i]);
      subs[i].setFont(new Font(15));
      subs[i].setPrefWidth(100);
      subvb.getChildren().add(subs[i]);
    }

    subvb.getChildren().add(ok1);

    subvb.setAlignment(Pos.CENTER);

    bp3.setCenter(subvb);

    setting = new Scene(bp3, 600, 400);

    stage.show();
  }

  class Check_name implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      // �A�J�E���g�����L�������肷��
      int check = 0;
      if (check == 0) {
        // �A�J�E���g���m��̂��߂̃A���[�g
        Alert really = new Alert(Alert.AlertType.CONFIRMATION);
        really.setTitle("�m�F");
        really.getDialogPane().setHeaderText("�{���� " + tf.getText() + " ���A�J�E���g���ł����ł����H");

        msg.setText("���̃A�J�E���g�����g�����Ƃ��ł��܂��B");
        ok.setDisable(false);

        ok.setOnAction(e -> {
          Optional<ButtonType> res = really.showAndWait();
          if (res.get() == ButtonType.OK) {
            stage.setScene(setting);
          }
        });
      } else {
        msg.setText("���̃A�J�E���g�����g�����Ƃ͂ł��܂���B");
      }
    }
  }
}
