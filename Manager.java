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
    Label wel = new Label("Exam-manager Ç…ÇÊÇ§Ç±Çª\n");
    Button nw = new Button("êVãK");
    Button op = new Button("äJÇ≠");

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
