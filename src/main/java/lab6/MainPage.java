package lab6;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainPage extends Application {

    public interface Converter {
        double convert(double input);
        String inputUnit();
        String outputUnit();
        String displayName();
    }

    public interface ReversibleConverter extends Converter {
        ReversibleConverter reverse();
    }

    public static class FootToCm implements ReversibleConverter {
        @Override public double convert(double ft) { return ft * 30.48; }
        @Override public String inputUnit() { return "ft"; }
        @Override public String outputUnit() { return "cm"; }
        @Override public String displayName() { return "Feet to Centimeters"; }
        @Override public ReversibleConverter reverse() { return new CmToFoot(); }
    }

    public static class CmToFoot implements ReversibleConverter {
        @Override public double convert(double cm) { return cm / 30.48; }
        @Override public String inputUnit() { return "cm"; }
        @Override public String outputUnit() { return "ft"; }
        @Override public String displayName() { return "Centimeters to Feet"; }
        @Override public ReversibleConverter reverse() { return new FootToCm(); }
    }

    public static class KmToMiles implements ReversibleConverter {
        @Override public double convert(double km) { return km * 0.621371; }
        @Override public String inputUnit() { return "km"; }
        @Override public String outputUnit() { return "mi"; }
        @Override public String displayName() { return "Kilometers to Miles"; }
        @Override public ReversibleConverter reverse() { return new MilesToKm(); }
    }

    public static class MilesToKm implements ReversibleConverter {
        @Override public double convert(double mi) { return mi / 0.621371; }
        @Override public String inputUnit() { return "mi"; }
        @Override public String outputUnit() { return "km"; }
        @Override public String displayName() { return "Miles to Kilometers"; }
        @Override public ReversibleConverter reverse() { return new KmToMiles(); }
    }

    public static class CeToFa implements ReversibleConverter {
        @Override public double convert(double c) { return c * 9.0 / 5.0 + 32; }
        @Override public String inputUnit() { return "°C"; }
        @Override public String outputUnit() { return "°F"; }
        @Override public String displayName() { return "Celsius to Fahrenheit"; }
        @Override public ReversibleConverter reverse() { return new FaToCe(); }
    }

    public static class FaToCe implements ReversibleConverter {
        @Override public double convert(double f) { return (f - 32) * 5.0 / 9.0; }
        @Override public String inputUnit() { return "°F"; }
        @Override public String outputUnit() { return "°C"; }
        @Override public String displayName() { return "Fahrenheit to Celsius"; }
        @Override public ReversibleConverter reverse() { return new CeToFa(); }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Multi Converter");
        Scene chooserScene = buildChooserScene(stage);
        stage.setScene(chooserScene);
        stage.setWidth(700);
        stage.setHeight(450);
        stage.show();
    }

    private Scene buildChooserScene(Stage stage) {
        VBox root = new VBox(25);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);

        Label title = new Label("Choose a converter");
        title.setFont(new Font("System Bold", 30));

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);
        ReversibleConverter[] converters = {
                new FootToCm(),
                new KmToMiles(),
                new CeToFa()
        };
        for (ReversibleConverter conv : converters) {
            Button b = new Button(conv.displayName());
            b.setOnAction(e -> {
                Scene converterScene = buildConverterScene(stage, conv);
                stage.setScene(converterScene);
            });
            buttons.getChildren().add(b);
        }

        root.getChildren().addAll(title, buttons);
        return new Scene(root);
    }

    private Scene buildConverterScene(Stage stage, ReversibleConverter initialConverter) {
        BorderPane outer = new BorderPane();
        outer.setPadding(new Insets(20));

        ObjectProperty<ReversibleConverter> current = new SimpleObjectProperty<>(initialConverter);


        HBox header = new HBox(10);
        Button back = new Button("← Back");
        Label title = new Label();
        title.setFont(new Font("Aptos Black", 36));
        header.getChildren().addAll(back, title);
        header.setAlignment(Pos.CENTER_LEFT);
        back.setOnAction(e -> stage.setScene(buildChooserScene(stage)));
        outer.setTop(header);


        VBox centerBox = new VBox(15);
        centerBox.setPadding(new Insets(10));

        HBox inputRow = new HBox(5);
        Label valueLabel = new Label();
        TextField inputField = new TextField();
        inputRow.getChildren().addAll(valueLabel, inputField);

        Button convertBtn = new Button("Convert");
        Label resultLabel = new Label("Result: ");
        Circle decor = new Circle(5);

        centerBox.getChildren().addAll(inputRow, convertBtn, resultLabel, decor);
        outer.setCenter(centerBox);


        HBox reverseContainer = new HBox();
        reverseContainer.setAlignment(Pos.BOTTOM_RIGHT);
        reverseContainer.setPadding(new Insets(0, 10, 10, 0));
        Button reverseBtn = new Button();
        try {
            Image img = new Image(MainPage.class.getResourceAsStream("/reverse.png"));
            if (img.isError() || MainPage.class.getResourceAsStream("/reverse.png") == null) {
                throw new IllegalStateException("image failed");
            }
            ImageView iv = new ImageView(img);
            iv.setFitWidth(28);
            iv.setFitHeight(28);
            reverseBtn.setGraphic(iv);
            reverseBtn.setTooltip(new Tooltip("Reverse conversion"));
            System.out.println("Reverse image loaded successfully.");
        } catch (Exception ex) {
            reverseBtn.setText("Reverse");
            System.err.println("Could not load reverse.png: " + ex.getMessage());
        }

        reverseContainer.getChildren().add(reverseBtn);
        outer.setBottom(reverseContainer);


        Runnable refreshUI = () -> {
            ReversibleConverter conv = current.get();
            title.setText(conv.displayName());
            valueLabel.setText("Value (" + conv.inputUnit() + "):");
            resultLabel.setText("Result: ");
            inputField.clear();
        };
        refreshUI.run();


        convertBtn.setOnAction((ActionEvent event) -> {
            String text = inputField.getText().trim();
            if (text.isEmpty()) {
                resultLabel.setText("Enter a value.");
                return;
            }
            try {
                double value = Double.parseDouble(text);
                ReversibleConverter conv = current.get();
                double res = conv.convert(value);
                resultLabel.setText(String.format("%.2f %s = %.2f %s",
                        value, conv.inputUnit(),
                        res, conv.outputUnit()));
            } catch (NumberFormatException e) {
                resultLabel.setText("Enter a valid number.");
            }
        });

        reverseBtn.setOnAction(e -> {
            current.set(current.get().reverse());
            refreshUI.run();
        });

        return new Scene(outer, 800, 500);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
