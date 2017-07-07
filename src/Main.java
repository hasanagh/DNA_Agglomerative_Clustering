import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class Main extends Application {
    static int w = 1380;
    static int h = 700;

    public ArrayList<String> sequences = new ArrayList<>();
    public TextArea area2;
    public String type;
    private double stop = 0;
    private String[] types = {"complete", "single", "average"};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        //Labels
        Label lau = new Label("LEBANESE AMERICAN UNIVERSITY");
        lau.getStyleClass().addAll("smaller-title-white");

        Label soe = new Label("SCHOOL OF ENGINEERING");
        soe.getStyleClass().addAll("smaller-title-white");

        Label cce = new Label("Department of Electrical and Computer Engineering");
        soe.getStyleClass().addAll("smaller-title-white");

        VBox vbox = new VBox(lau, soe, cce);
        vbox.setAlignment(Pos.CENTER);


        Rectangle r = new Rectangle();
        r.setHeight(h);
        r.setWidth(w);

        HBox hboxx = new HBox(r);
        hboxx.setAlignment(Pos.CENTER);

        FadeTransition ft = new FadeTransition(Duration.millis(1500), hboxx);
        ft.setFromValue(1.0);
        ft.setToValue(0.3);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();

        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            System.out.println("Change Scene");
            popMainScene(primaryStage);
        });
        delay.play();


        StackPane s = new StackPane();
        s.getChildren().addAll(hboxx, vbox);

        root.setCenter(s);
        primaryStage.setOnCloseRequest(e -> System.exit(0));


        root.getStyleClass().addAll("");
        primaryStage.setTitle("Project");
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root, w, h);
        scene.getStylesheets().add("main.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void popMainScene(Stage stage) {
        BorderPane root = new BorderPane();

        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setAlignment(Pos.CENTER);

        Label title = new Label("DNA SCT ");
        title.getStyleClass().addAll("title");

        Button acBtn = new Button("Agglomerative Clustering");
        acBtn.setMinWidth(150);
        acBtn.setMinHeight(50);
        acBtn.setOnAction(e -> popAgglomerativeClusteringScene(stage));
        acBtn.setDefaultButton(false);


        vbox1.getChildren().addAll(title, acBtn);


        root.setCenter(vbox1);
        stage.setOnCloseRequest(e -> System.exit(0));
        root.getStyleClass().addAll("");
        stage.setTitle("Project");
        Scene scene = new Scene(root, w, h);
        root.setId("pane");
        scene.getStylesheets().add("main.css");
        stage.setScene(scene);
    }

    public void popAgglomerativeClusteringScene(Stage stage) {
        BorderPane root = new BorderPane();

        //top

        BorderPane topPane = new BorderPane();

        Image image = new Image(getClass().getResourceAsStream("Arrow_Back-128.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(25);


        Button back = new Button("", imageView);
        back.setOnAction(e -> popMainScene(stage));
        topPane.setLeft(back);
        back.setAlignment(Pos.CENTER);


        Label title = new Label("DNA SCT - Agglomerative Clustering");
        title.getStyleClass().addAll("title-white");
        title.setAlignment(Pos.CENTER);
        topPane.setCenter(title);


        HBox hbox1 = new HBox(back, title);
        hbox1.setAlignment(Pos.CENTER_LEFT);
        hbox1.getStyleClass().addAll("top");
        hbox1.setMinHeight(75);

        //end top


        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);

        //Col 1

        VBox vbox1 = new VBox();
        vbox1.setSpacing(10);
        vbox1.setAlignment(Pos.CENTER);

        Label seqsLabel = new Label("Sequences");
        seqsLabel.getStyleClass().addAll("smaller-title");

        TextArea area1 = new TextArea();
        area1.setPrefHeight(500);
        area1.setPrefWidth(550);
        area1.setEditable(false);
        vbox1.getChildren().addAll(seqsLabel, area1);


        //Col 2


        VBox vbox2 = new VBox();
        vbox2.setAlignment(Pos.CENTER);
        vbox2.setSpacing(15);

        Label labelst = new Label("Stopping Rule:");
        TextField textField = new TextField();
        textField.setStyle("");

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("")) {
                    stop = Double.parseDouble(newValue);
                }
            }
        });


        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
                "complete", "single", "average")
        );
        type = types[0];
        cb.getSelectionModel().selectFirst();

        Button sourceBtn = new Button("Source");
        sourceBtn.setMinWidth(75);

        TextField sourceField = new TextField();
        sourceField.setEditable(false);

        Button goBtn = new Button("Go!");
        goBtn.setMinWidth(75);

        vbox2.getChildren().addAll(labelst, textField, cb, sourceBtn, sourceField, goBtn);

        //Buttons
        sourceBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            configChooser(chooser);
            chooser.setTitle("Choose Source File");
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                sourceField.setText(file.toString() + "");
            }
        });

        goBtn.setOnAction(e -> {
            String sfile = sourceField.getText();
            if (!sfile.equals("")) {
                goSD();
            }
        });


        //Col 3

        VBox vbox3 = new VBox();
        vbox3.setAlignment(Pos.CENTER);
        vbox3.setSpacing(10);

        Label seq2Label = new Label("Result");
        seq2Label.getStyleClass().addAll("smaller-title");

        area2 = new TextArea();
        area2.setPrefHeight(500);
        area2.setPrefWidth(550);
        area2.setEditable(false);
        vbox3.getChildren().addAll(seq2Label, area2);


        //Listners
        sourceField.textProperty().addListener((observable, oldValue, newValue) -> {
            String xml = readFile(newValue);
            area1.setText(xml);
        });

        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                type = types[(int) newValue];
            }
        });


        hbox.getChildren().addAll(vbox1, vbox2, vbox3);
        root.setCenter(hbox);
        root.setTop(hbox1);
        root.getStyleClass().addAll("");
        stage.setOnCloseRequest(e -> System.exit(0));
        stage.setTitle("Project");
        Scene scene = new Scene(root, w, h);
        scene.getStylesheets().add("main.css");
        stage.setScene(scene);
    }


    public void configChooser(FileChooser chooser) {
        chooser.setInitialDirectory(
                new File(System.getProperty("user.dir"))
        );
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML", "*.xml"));

    }

    public String readFile(String str) {

        StringBuilder sb = new StringBuilder();
        try {
            WagnerFischer WF = new WagnerFischer();
            sequences = WF.getSequencesFromXML(str);
            System.out.println(sequences.toString());

            for (int i = 0; i < sequences.size(); i++) {
                sb.append(sequences.get(i) + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();

    }


    public void goSD() {
        try {
            if (!sequences.isEmpty()) {
                WagnerFischer WF = new WagnerFischer();

                double[][] distances = WF.getDistanceMatrix(sequences);
                System.out.println(WF.toSt(distances));

                double[][] similarityMatrix = WF.getSimilarityMatrix(distances);
                System.out.println(WF.toSt(similarityMatrix));

                ArrayList<Cluster> SimpleInitialClusters = new ArrayList<>();

                for (int i = 1; i <= distances.length; i++) {
                    int[] arr = {i};
                    SimpleInitialClusters.add(new Cluster("child", similarityMatrix, arr));
                }
                Stack<ResultCluster> result = WF.AgglomerativeClustering(SimpleInitialClusters, type, stop);
                area2.setText(result.peek().getString());
                popNewFile(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void popNewFile(Stack<ResultCluster> result) {
        Group root = new Group();
        Scene s = new Scene(root, 500, 600, Color.WHITE);
        Stage stage = new Stage();
        Stack<ResultCluster> stout = new Stack<>();

        ArrayList<Integer> indexes = new ArrayList<>();
        Line line1 = new Line(0, 500, 500, 500);
        while(!result.empty()){
            for(int i= 0 ; i<result.peek().getNewIndexes().length ; i++){
                if(!indexes.contains(result.peek().getNewIndexes()[i])) {
                    indexes.add(result.peek().getNewIndexes()[i]);
                    System.out.print(result.peek().getNewIndexes()[i]);
                }
            }
            stout.push(result.pop());
        }

        double x = 500/(indexes.size()+1);
        double y = 700/(indexes.size()+1);
        Collections.reverse(indexes);
        ArrayList<grp> groups = new ArrayList<>();
        for(int i=1 ; i<=indexes.size() ; i++){
            Text t = new Text((i*x), 500, (indexes.get(i-1))+"");
            root.getChildren().add(t);
            ArrayList<Integer> index = new ArrayList<>();
            index.add(indexes.get(i-1));
            groups.add(new grp(i*x,i*x,500,i*x,index));

        }

        while(!stout.isEmpty()){

            int[] index1 = stout.peek().getIndexes1();
            ArrayList<Integer> indexes1 = new ArrayList<Integer>();
            for (int index = 0; index < index1.length; index++)
            {
                indexes1.add(index1[index]);
            }

            int[] index2 = stout.peek().getIndexes2();
            ArrayList<Integer> indexes2 = new ArrayList<Integer>();
            for (int index = 0; index < index2.length; index++)
            {
                indexes2.add(index2[index]);
            }

            int loc1= -1  ;
            int loc2 = -1;

            for(int k = 0 ; k<groups.size();k++){
                if(isTwoArrayListsWithSameValues(indexes1,groups.get(k).getIndexes())){
                    loc1 = k;
                }
                if(isTwoArrayListsWithSameValues(indexes2,groups.get(k).getIndexes())){
                    loc2 = k;
                }
            }

            double ynow = Math.min(groups.get(loc1).getYnow(),groups.get(loc2).getYnow())- y*(1-stout.peek().getSimilarity());
            grp newGroup = groups.get(loc1).joinGroups(groups.get(loc2),ynow);

            Line horizantal = new Line(newGroup.getStartX(),ynow,newGroup.getEndX(),ynow);
            Line vertical1 = new Line(groups.get(loc1).getMidpoint(),groups.get(loc1).getYnow(),groups.get(loc1).getMidpoint(),ynow);
            Line vertical2 = new Line(groups.get(loc2).getMidpoint(),groups.get(loc2).getYnow(),groups.get(loc2).getMidpoint(),ynow);
            Text t = new Text(newGroup.getMidpoint(), ynow,stout.peek().getSimilarity()+"" );

            grp bibz = groups.get(loc1);
            grp mibz = groups.get(loc2);
            groups.remove(bibz);
            groups.remove(mibz);
            groups.add(newGroup);
            root.getChildren().addAll(horizantal,vertical1,vertical2,t);
            stout.pop();
        }


        root.getChildren().addAll(line1);
        root.getStyleClass().addAll("");
        stage.setTitle("Dendrogram");
        s.getStylesheets().add("main.css");
        stage.setScene(s);
        stage.show();

    }
    public boolean isTwoArrayListsWithSameValues(ArrayList<Integer> list1, ArrayList<Integer> list2)
    {
        //null checking
        if(list1==null && list2==null)
            return true;
        if((list1 == null && list2 != null) || (list1 != null && list2 == null))
            return false;

        if(list1.size()!=list2.size())
            return false;
        for(int itemList1: list1)
        {
            if(!list2.contains(itemList1))
                return false;
        }

        return true;
    }


}