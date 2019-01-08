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
    Label wel = new Label("Exam-manager Ç…ÇÊÇ§Ç±Çª");
    Button nw = new Button("êVãK");
    Button op = new Button("äJÇ≠");

    wel.setFont(Font.font("SanSerif", FontWeight.BOLD, 36));

    BorderPane bp = new BorderPane();
    HBox btn = new HBox();
    VBox vb = new VBox();

    btn.getChildren().add(nw);
    btn.getChildren().add(op);

    vb.getChildren().add(wel);
    vb.getChildren().add(btn);

    vb.setAlignment(Pos.CENTER);
    bp.setCenter(vb);

    Scene sc = new Scene(bp, 600, 400);

    stage.setScene(sc);

    stage.setTitle("Exam-manager");
    stage.show();
  }
}
