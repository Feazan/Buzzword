package buzzword.Controller;

import buzzword.Model.AppContext;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

/**
 * Created by Feazan on 11/27/2016.
 */
public class GameBoardController extends Controller {
    @FXML
    Label Boggle1;
    @FXML
    Label Boggle2;
    @FXML
    Label Boggle3;
    @FXML
    Label Boggle4;
    @FXML
    Label Boggle5;
    @FXML
    Label Boggle6;
    @FXML
    Label Boggle7;
    @FXML
    Label Boggle8;
    @FXML
    Label Boggle9;
    @FXML
    Label Boggle10;
    @FXML
    Label Boggle11;
    @FXML
    Label Boggle12;
    @FXML
    Label Boggle13;
    @FXML
    Label Boggle14;
    @FXML
    Label Boggle15;
    @FXML
    Label Boggle16;
    @FXML
    AnchorPane wordCompletedBox;
    private String selectedWord = "";
    Label lastLabel;
    List<Label> selectedLabels = new ArrayList<Label>();
    Set<String> highlightedWordList = new HashSet<>();
    @FXML
    BorderPane theBorderPane;
    Label[][] board;
    @FXML
    Label playerTotalScore;
    private int total;
    @FXML
    Label timer;
    private Timeline timeline;
    private Integer  timeSeconds = 0;
    private static final Integer STARTTIME = 50;
    private final Object mutex = ""; // dummy object to induce locks
    @FXML
    Button thePlayBTN;
    @FXML
    Button restartButton;
    LevelSelectorController theLevel = new LevelSelectorController();
    @FXML
    Label gameModeInBoard;

    static class Position
    {
        public int x;
        public int y;
    }

    public void boggleMousePressed(MouseEvent event) {
        System.out.println("starting new word");
        Label test = (Label) event.getSource();
        test.startFullDrag();
        selectedLabels.clear();
    }

    public void boggleMouseEntered(MouseEvent event)
    {
        Label currentLabel;
        currentLabel = (Label) event.getSource();
        currentLabel.setTextFill(Color.valueOf("#24193E"));
        if (lastLabel == null || !lastLabel.equals(currentLabel))
        {
            selectedLabels.add(currentLabel);
        }
        lastLabel = currentLabel;
        System.out.println("On Mouse Entered");
    }


    private int playerTotal(String playerWord)
    {
        total += (playerWord.length() - 2)*10;
        return total;
    }

    public void boggleMouseReleased(MouseEvent event)
    {
        // TODO: Check if the word is allowed
        //selectedWord = "";
        for (Label selectedLabel : selectedLabels)
        {
            selectedWord += selectedLabel.getText();
            selectedLabel.setTextFill(Color.WHITE);
        }
        boolean contains = AppContext.getSingleton().getGameState().getAllowedWords().contains(selectedWord);

        // NEED TO CHECK
        if (selectedWord.length() > 2 && checkIfProperWord(selectedWord))
        {
            Text test = new Text();
            test.setLayoutX(20.0);
            test.setLayoutY(30.0*(highlightedWordList.size()+1));
            if (!alreadySelected(selectedWord))
            {
                test.setText(selectedWord + "         " + (selectedWord.length() - 2)*10);
                wordCompletedBox.getChildren().add(test);
                playerTotalScore.setText(Integer.toString(playerTotal(selectedWord)));
            }
            highlightedWordList.add(selectedWord);
            System.out.println(highlightedWordList.toString());
        } else
        {
            System.out.println(selectedWord);
        }
        selectedWord = "";
        nextLevel(total, event);
    }

    private boolean alreadySelected(String word) {
        return highlightedWordList.contains(word);
    }

    @Override
    public void initialize()
    {
        theLevel.addDictionaryToTrie((AppContext.getSingleton().getGameState().getGameMode().toString()));
        // Board contains the 2d array that has references to the labels
        board = createGrid();
        generateGrid(board, (AppContext.getSingleton().getGameState().getGameMode().toString()));


        AppContext.getSingleton().getGameState().getAllowedWords().clear();
        timer.setText(timeSeconds.toString());
            if (timeline != null)
                timeline.stop();
            timeSeconds = STARTTIME;

            timer.setText(timeSeconds.toString());  // updating the timer label every second
            timeline = new Timeline();                   // setting up the timeline
            timeline.setCycleCount(Timeline.INDEFINITE); // animation continues until stop() is called

            // A timeline consists of KeyFrames
            // It uses these KeyFrame objects to represent the different time frames
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(1),       // each time frame lasts for one second
                            keyframeEventHandler -> {  // at the end of each frame, this event handler is executed
                                timer.setText((--timeSeconds).toString());
                                if (timeSeconds <= 0) {
                                    synchronized (mutex) {
                                        timeline.stop();
                                        // e.g., add code to disable entering new words
                                        setVisability();
                                        clearGrid(board);
                                        thePlayBTN.setVisible(false);
                                        Button OK = new Button();
                                        final Stage dialog = new Stage();
                                        dialog.initModality(Modality.APPLICATION_MODAL);
                                        VBox dialogVbox = new VBox(10);
                                        dialogVbox.getChildren().add(OK);
                                        dialogVbox.getChildren().add(new Text("Game Over!"));
                                        Scene dialogScene = new Scene(dialogVbox, 300, 200);
                                        dialog.setScene(dialogScene);
                                        dialog.show();
                                        OK.setText("OK");
                                        OK.setOnAction(even-> dialog.close());
                                    }
                                }
                            }));
            timeline.playFromStart();
        gameModeInBoard.setText((AppContext.getSingleton().getGameState().getGameMode().toString()));
    }

    // This method checks if the word is a correct word
    private boolean checkIfProperWord(String word)
    {
        if (theLevel.dictionaryTrie.containsKey(word))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public void handlePlayButton(ActionEvent event) {
        Button btn = (Button) event.getSource();
        if (btn.getText().equals("Play")) {
            timeline.play();
            setVisability();
            btn.setText("Pause");
        } else {
            timeline.pause();
            setVisability();
            btn.setText("Play");
        }
    }

    private Label[][] createGrid() {
        Label[][] generator = new Label[4][4];

        generator[0][0] = Boggle1;
        generator[0][1] = Boggle2;
        generator[0][2] = Boggle3;
        generator[0][3] = Boggle4;
        generator[1][0] = Boggle5;
        generator[1][1] = Boggle6;
        generator[1][2] = Boggle7;
        generator[1][3] = Boggle8;
        generator[2][0] = Boggle9;
        generator[2][1] = Boggle10;
        generator[2][2] = Boggle11;
        generator[2][3] = Boggle12;
        generator[3][0] = Boggle13;
        generator[3][1] = Boggle14;
        generator[3][2] = Boggle15;
        generator[3][3] = Boggle16;
        return generator;
    }

    public static char selectAChar(String s){
        Random random = new Random();
        int index = random.nextInt(s.length());
        return s.charAt(index);
    }

    // THIS IS WHERE I CAN APPLY THE BOARD GENERATOR
    public void generateGrid(Label[][] boggleGrid, String gameMode) {
        String[] die = new String[16];
        if(gameMode.equals("ENGLISH")) {
            die[0] = "AAEEGN";
            die[1] = "ABBJOO";
            die[2] = "ACHOPS";
            die[3] = "AFFKPS";
            die[4] = "AOOTTW";
            die[5] = "CIMOTU";
            die[6] = "DEILRX";
            die[7] = "DELRVY";
            die[8] = "DISTTY";
            die[9] = "EEGHNW";
            die[10] = "EEINSU";
            die[11] = "EHRTVW";
            die[12] = "EIOSST";
            die[13] = "ELRTTY";
            die[14] = "HIMNUE";
            die[15] = "HLNNRZ";
        }
        else if (gameMode.equals("SPANISH"))
        {
            die[0] = "QBZJXL";
            die[1] = "TOUOTO";
            die[2] = "OVCRGR";
            die[3] = "AAAFSR";
            die[4] = "AUMEEO";
            die[5] = "EHLRDO";
            die[6] = "NHDTHO";
            die[7] = "LHNROD";
            die[8] = "ADAISR";
            die[9] = "UIFASR";
            die[10] = "TELPCI";
            die[11] = "SSNSEU";
            die[12] = "RIYPRH";
            die[13] = "DORDLN";
            die[14] = "CCÑNST";
            die[15] = "UOTOÑN";
        }
        else if(gameMode.equals("ITALIAN"))
        {
            die[0] = "AAEIOT";
            die[1] = "ACFIOR";
            die[2] = "EEFHIS";
            die[3] = "ELPSTU";
            die[4] = "AFGIPR";
            die[5] = "ABILRT";
            die[6] = "ADENVZ";
            die[7] = "EGINTV";
            die[8] = "ABCIMO";
            die[9] = "BEFLNO";
            die[10] = "ABMOOE";
            die[11] = "AIMORS";
            die[12] = "EGLNOU";
            die[13] = "CDILMO";
            die[14] = "ACDEMP";
            die[15] = "CENOTU";
        }

        ArrayList<String> boggleDice = new ArrayList<>(Arrays.asList(die));
        Collections.shuffle(boggleDice);
        Random r = new Random();

        int numOfDices = 0;
        for (int i = 0; i < boggleGrid.length; i++) {
            for (int j = 0; j < boggleGrid[i].length; j++) {
                boggleGrid[i][j].setText(Character.toString(boggleDice.get(numOfDices).charAt(r.nextInt(boggleDice.get(numOfDices).length()))));
                numOfDices++;
            }
        }
    }

    public void clearGrid(Label[][] boggleGrid) {
        for (int i = 0; i < boggleGrid.length; i++) {
            for (int j = 0; j < boggleGrid[i].length; j++) {
                boggleGrid[i][j].setText(" ");
            }
        }
    }






    public boolean wordExistsInBoggleBoard(Label[][] boggleGrid, String wordToCheck)
    {
        if (wordToCheck.length() > 16) {
            return false;
        }
        for (int i = 0; i < boggleGrid.length; i++) {
            for (int j = 0; j < boggleGrid[i].length; j++) {
                boolean[][] visitedNodes = new boolean[boggleGrid.length][boggleGrid.length];
                boolean found = findWordInGrid(boggleGrid, wordToCheck, 0, visitedNodes, i, j);
                if (found) return true;
            }
        }
        return false;
    }

    public boolean findWordInGrid(Label[][] boggleGrid, String wordToCheck, int index, boolean[][] visitedNode,
                                  int i, int j) {
        if (index >= wordToCheck.length()) return true;
        if (i < 0 || i >= boggleGrid.length || j < 0 || j >= boggleGrid.length) {
            return false;
        }
        if (boggleGrid[i][j].getText().equals(Character.toString(wordToCheck.charAt(index))) && !visitedNode[i][j]) {
            visitedNode[i][j] = true;
            boolean found = findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i - 1, j) ||
                    findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i - 1, j + 1) ||
                    findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i, j + 1) ||
                    findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i + 1, j + 1) ||
                    findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i + 1, j) ||
                    findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i + 1, j - 1) ||
                    findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i, j - 1) ||
                    findWordInGrid(boggleGrid, wordToCheck, index + 1, copyVisitedNodes(visitedNode), i - 1, j - 1);
            return found;
        } else {
            return false;
        }
    }

  /* public void placeWordInBoard(Label[][] boggleBoard, String wordToPlace) {
        int x = (int) (Math.random() * boggleBoard.length);
        int y = (int) (Math.random() * boggleBoard.length);

        System.out.println(x);
        System.out.println(y);
        List<Position> path = getPathToPlaceWordInBoardRecursive(boggleBoard, wordToPlace, 0,
                new boolean[boggleBoard.length][boggleBoard.length], x, y);
        if (path != null) {
            int i = 0;
            for (Position position : path) {
                boggleBoard[position.x][position.y].setText(Character.toString(wordToPlace.charAt(i++)));
            }
        }
    }*/

    private List<Position> getPathToPlaceWordInBoardRecursive(Label[][] boggleGrid, String wordToPlace, int index,
                                                              boolean[][] visitedNode, int i, int j) {
        if (i < 0 || i >= boggleGrid.length || j < 0 || j >= boggleGrid.length) {
            return null;
        }
        if (!visitedNode[i][j]) {
            visitedNode[i][j] = true;

            Position p = new Position();
            p.x = i;
            p.y = j;
            List<Position> path = new ArrayList<>();
            path.add(p);

            if (index == wordToPlace.length() - 1) return path;

            List<Position> subPath = null;
            subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i - 1, j);
            if (subPath == null)
                subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i - 1, j + 1);
            if (subPath == null)
                subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i, j + 1);
            if (subPath == null)
                subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i + 1, j + 1);
            if (subPath == null)
                subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i + 1, j);
            if (subPath == null)
                subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i + 1, j - 1);
            if (subPath == null)
                subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i, j - 1);
            if (subPath == null)
                subPath = getPathToPlaceWordInBoardRecursive(boggleGrid, wordToPlace, index + 1, copyVisitedNodes(visitedNode), i - 1, j - 1);

            if (subPath != null) {
                path.addAll(subPath);
                return path;
            }
        }
        return null;
    }

    private boolean[][] copyVisitedNodes(boolean[][] nodes) {
        boolean[][] copied = new boolean[nodes.length][nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            System.arraycopy(nodes[i], 0, copied[i], 0, nodes[i].length);
        }
        return copied;
    }

    public void nextLevel(int score, MouseEvent event)
    {
        if (score == 100)
        {
            Label btn = (Label)event.getSource();
            Stage stageTheLabelBelongs = (Stage) btn.getScene().getWindow();
            btn.setOnMouseMoved(this::showNewWindow);
            stageTheLabelBelongs.show();
        }
    }
    public void restart(ActionEvent event)
    {
        System.out.println("restart pressed");
        Duration time = new Duration(60000);
    }



    public void showNewWindow(MouseEvent event)
    {
        generateGrid(board, (AppContext.getSingleton().getGameState().getGameMode().toString()));
        Button OK = new Button();
        Label btn = (Label)event.getSource();
        Stage stageTheLabelBelongs = (Stage) btn.getScene().getWindow();
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(stageTheLabelBelongs);
        VBox dialogVbox = new VBox(10);
        dialogVbox.getChildren().add(OK);
        dialogVbox.getChildren().add(new Text("Congratulations you've completed the level!"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
        OK.setText("OK");
        OK.setOnAction(even-> dialog.close());
    }

    public void setVisability()
    {
        if(Boggle1.visibleProperty().getValue())
        {
            Boggle1.setVisible(false);
            Boggle2.setVisible(false);
            Boggle3.setVisible(false);
            Boggle4.setVisible(false);
            Boggle5.setVisible(false);
            Boggle6.setVisible(false);
            Boggle7.setVisible(false);
            Boggle8.setVisible(false);
            Boggle9.setVisible(false);
            Boggle10.setVisible(false);
            Boggle11.setVisible(false);
            Boggle12.setVisible(false);
            Boggle13.setVisible(false);
            Boggle14.setVisible(false);
            Boggle15.setVisible(false);
            Boggle16.setVisible(false);
        }
        else
        {
            Boggle1.setVisible(true);
            Boggle2.setVisible(true);
            Boggle3.setVisible(true);
            Boggle4.setVisible(true);
            Boggle5.setVisible(true);
            Boggle6.setVisible(true);
            Boggle7.setVisible(true);
            Boggle8.setVisible(true);
            Boggle9.setVisible(true);
            Boggle10.setVisible(true);
            Boggle11.setVisible(true);
            Boggle12.setVisible(true);
            Boggle13.setVisible(true);
            Boggle14.setVisible(true);
            Boggle15.setVisible(true);
            Boggle16.setVisible(true);
        }
    }

    public void closeGameProgram(ActionEvent event)
    {
        timeline.pause();
        Button yes = new Button();
        Button no = new Button();
        Button btn = (Button)event.getSource();
        btn.setOnAction(
                event1 -> {
                    setVisability();
                    Stage stageTheLabelBelongs = (Stage) btn.getScene().getWindow();
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(stageTheLabelBelongs);
                    VBox dialogVbox = new VBox(10);
                    dialogVbox.getChildren().add(yes);
                    dialogVbox.getChildren().add(no);
                    dialogVbox.getChildren().add(new Text("Are you sure you want to exit?"));
                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    yes.setText("YES");
                    no.setText("NO");
                    yes.setOnAction(even-> System.exit(0));
                    no.setOnAction(even-> {
                        timeline.play();
                        setVisability();
                        dialog.close();
                    });
                });
    }
}
