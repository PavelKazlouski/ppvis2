package plagamedicum.ppvis.lab2s4;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.stage.Stage;
import plagamedicum.ppvis.lab2s4.Controller.Controller;
import plagamedicum.ppvis.lab2s4.model.Model;
import plagamedicum.ppvis.lab2s4.view.View;

public class MainWindow extends Application {
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage mainStage) {
		Model 	   model 	  = new Model( 0);
		Controller controller = new Controller(model);
		View view 	  = new View(controller);
		
		mainStage = view.getStage();
		mainStage.show();
	}
}
