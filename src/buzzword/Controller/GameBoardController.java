package buzzword.Controller;

import buzzword.Model.AppContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

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
    TextArea wordCompletedBox;
    private String selectedWord = "";
    Label lastLabel;
    List<Label> selectedLabels = new ArrayList<Label>();
    List<String> highlightedWordList = new ArrayList<String>();
    @FXML
    BorderPane theBorderPane;

    Label[][] board;

    static class Position {
        public int x;
        public int y;
    }

    public void boggleMousePressed(MouseEvent event) {
        System.out.println("starting new word");
        Label test = (Label) event.getSource();
        test.startFullDrag();
        selectedLabels.clear();
    }

    public void boggleMouseEntered(MouseEvent event) {
        Label currentLabel;
        currentLabel = (Label) event.getSource();
        currentLabel.setTextFill(Color.RED);
        if (lastLabel == null || !lastLabel.equals(currentLabel)) {
            selectedLabels.add(currentLabel);
        }
        lastLabel = currentLabel;
        System.out.println("On Mouse Entered");
    }

    public void boggleMouseReleased(MouseEvent event) {
        // TODO: Check if the word is allowed
        //selectedWord = "";
        for (Label selectedLabel : selectedLabels) {
            selectedWord += selectedLabel.getText();
            selectedLabel.setTextFill(Color.WHITE);
        }
        boolean contains = AppContext.getSingleton().getGameState().getAllowedWords().contains(selectedWord);
        if (selectedWord.length() > 2) {
            highlightedWordList.add(selectedWord);
            addToTextArea(highlightedWordList);
            System.out.printf((contains) ? "Found " + selectedWord : "Not Found: " + selectedWord);
        } else {
            System.out.println(selectedWord);
        }

    }

    private void addToTextArea(List<String> listOfWords) {
        for (int i = 0; i < listOfWords.size(); i++) {
            wordCompletedBox.setText(listOfWords.get(i));
        }
    }

    private boolean alreadySelected(String highlightedWord) {
        if (highlightedWord.contains(highlightedWord)) {
            return true;
        } else
            return false;
    }

    @Override
    public void initialize() {
        // Board contains the 2d array that has references to the labels
        board = createGrid();
        generateGrid(board);

        // TODO: Place a random word from the dictionary
        placeWordInBoard(board, "BEAR");

        // TODO: Set list of all possible words
        AppContext.getSingleton().getGameState().getAllowedWords().clear();

        // Loop through each word in app context game state dictionary
        //   call find word in board
        //   if found -> add to allowed words set in game state
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


    // THIS IS WHERE I CAN APPLY THE BOARD GENERATOR
    public void generateGrid(Label[][] boggleGrid) {
        final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int N = alphabet.length();
        Random r = new Random();

        for (int i = 0; i < boggleGrid.length; i++) {
            for (int j = 0; j < boggleGrid[i].length; j++) {
                boggleGrid[i][j].setText(Character.toString(alphabet.charAt(r.nextInt(N))));
            }
        }
    }

    public boolean wordExistsInBoggleBoard(Label[][] boggleGrid, String wordToCheck) {
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

    public void placeWordInBoard(Label[][] boggleBoard, String wordToPlace) {
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
    }

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

    public void handlePlayButton(ActionEvent event) {

        Button btn = (Button) event.getSource();
        if (btn.getText().equals("Play")) {
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
            btn.setText("Pause");
        } else {
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
            btn.setText("Play");
        }
    }


    private boolean[][] copyVisitedNodes(boolean[][] nodes) {
        boolean[][] copied = new boolean[nodes.length][nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            System.arraycopy(nodes[i], 0, copied[i], 0, nodes[i].length);
        }
        return copied;
    }
}
