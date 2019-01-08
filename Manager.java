import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.scene.text.*;

public class Manager extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage stage) throws Exception {
    Label wel = new Label("Exam-manager にようこそ\n");
    Button nw = new Button("新規");
    Button op = new Button("開く");

    wel.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));

    nw.setFont(new Font(20));
    op.setFont(new Font(20));

    BorderPane bp = new BorderPane();
    VBox vb = new VBox(20);

    vb.getChildren().add(wel);
    vb.getChildren().add(nw);
    vb.getChildren().add(op);

    vb.setAlignment(Pos.CENTER);
    bp.setCenter(vb);

    Scene sc = new Scene(bp, 600, 400);

    stage.setScene(sc);

    stage.setTitle("Exam-manager");
    stage.show();
  }
}
