import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;

public class Manager extends Application {
  private TextField tf;
  private Label msg;
  private Button ok;

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage stage) throws Exception {
    stage.setTitle("Exam-manager");

    Label wel = new Label("Exam-manager にようこそ\n");
    Button nw = new Button("新規");
    Button op = new Button("開く");

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

    Label acc = new Label("アカウント名を入力してください。");
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
    nw.setOnAction(e -> {
      stage.setScene(make_acc);
    });

    stage.show();
  }

  class Check_name implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      int check = 0;
      if (check == 0) {
        msg.setText("このアカウント名を使うことができます。");
        ok.setDisable(false);
      } else {
        msg.setText("このアカウント名を使うことはできません。");
      }
    }
  }
}
