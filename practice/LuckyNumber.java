import javafx.application.Application;
import javafx.scene.Scene;

import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;

class Notifier {
	public static void sendNotification(String title, String message) {
		try {
			ProcessBuilder pb = new ProcessBuilder(
					"notify-send", "-t", "1000", title, message);
			pb.inheritIO(); // Optional: allows error/output messages to print to terminal
			Process process = pb.start();
			process.waitFor(); // Wait for the command to complete
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class LuckyNumber extends Application {
	@Override
	public void start(Stage pStage) {
		Model model = new Model(); // observer
		GridPane root = new GridPane();
		// Panel classes defined earlier.
		// root.add(new SliderPanel(model), 0, 0, 1, 1);
		root.add(new IntegerPanel(model), 0, 1, 1, 1);
		// root.add(new TextPanel(model), 0, 2, 1, 1);
		pStage.setScene(new Scene(root));
		pStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}

interface Observer {
	void numberChanged(int pNumber);
}

class IntegerPanel extends Parent implements Observer {
	private TextField aText = new TextField();
	private Model aModel;

	public IntegerPanel(Model pModel) {

		aModel = pModel;
		aModel.addObserver(this);
		aText.setText(aModel.getNumber().toString());
		getChildren().add(aText);

		aText.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent pEvent) {
				int number;
				try {
					number = Integer.parseInt(aText.getText());
				} catch (NumberFormatException pException) {
					number = 1;
				}
				aModel.setNumber(number);
				// Notifier.sendNotification("Java Debug","changed to " +
				// Integer.toString(number) );
			}
		});
	}

	@Override
	public void numberChanged(int pNumber) {
		aText.setText(Integer.toString(pNumber));
		Notifier.sendNotification("Java Debug (numberChanged)", "changed to " + Integer.toString(pNumber));
	}
}

// Observer code:

class Model {
	private int aNumber = 5;
	private List<Observer> aObservers = new ArrayList<>();

	public void addObserver(Observer pObserver) {
		aObservers.add(pObserver);
	}

	public void removeObserver(Observer pObserver) {
		aObservers.remove(pObserver);
	}

	private void notifyObservers() {
		for (Observer observer : aObservers) {
			observer.numberChanged(aNumber);
		}
	}

	public void setNumber(Integer pNumber) {
		aNumber = pNumber;
		notifyObservers();
	}

	public Integer getNumber() {
		return aNumber;
	}
}
