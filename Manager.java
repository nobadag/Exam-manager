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
    Label wel = new Label("Exam-manager Ç…ÇÊÇ§Ç±Çª\n");
    Button nw = new Button("êVãK");
    Button op = new Button("äJÇ≠");

    wel.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));

    nw.setFont(new Font(20));
    op.setFont(new Font(20));

    BorderPane bp = new BorderPane();
    VBox home = new VBox(20);

    home.getChildren().add(wel);
    home.getChildren().add(nw);
    home.getChildren().add(op);

    home.setAlignment(Pos.CENTER);
    bp.setCenter(home);

    Scene welcome = new Scene(bp, 600, 400);

    stage.setScene(welcome);

    stage.setTitle("Exam-manager");
    stage.show();
  }
}
