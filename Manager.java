import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;

public class Manager extends Application {
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
    TextField tf = new TextField();

    acc.setFont(new Font(20));

    BorderPane bp2 = new BorderPane();
    VBox acvb = new VBox(20);

    acvb.getChildren().add(acc);
    acvb.getChildren().add(tf);

    acvb.setAlignment(Pos.CENTER);

    bp2.setCenter(acvb);

    Scene make_acc = new Scene(bp2, 600, 400);
    nw.setOnAction(event -> {
      stage.setScene(make_acc);
    });

    stage.show();
  }
}
