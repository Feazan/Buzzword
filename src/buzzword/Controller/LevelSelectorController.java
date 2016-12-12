package buzzword.Controller;

import buzzword.Model.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Feazan on 11/26/2016.
 */
public class LevelSelectorController extends Controller {
    @FXML private Label modeLabel;
    Scanner readFile;
    PatriciaTrie dictionaryTrie = new PatriciaTrie();

    @Override
    public void initialize()
    {
        modeLabel.setText(AppContext.getSingleton().getGameState().getGameMode().toString());
        System.out.println("initialize inside of level selector");

    }

    public void addDictionaryToTrie(String mode)
    {
        URL url = getClass().getClassLoader().getResource("english.txt");
        //TEMPORARY STRING TO READ IN DATA
        String tempRead;
        try
        {
            if (mode.equals("SPANISH"))
            {
                url = getClass().getClassLoader().getResource("spanish.txt");
            }
            else if (mode.equals("ITALIAN"))
            {
                url = getClass().getClassLoader().getResource("italian.txt");
            }
            //INSTANTIATE URL OBJECT WITH GIVEN FILE NAME


            assert url != null;
            //URL url  = getClass().getResource("words.txt");

            //INSTANTIATE FILE OBJECT WITH GIVEN URL PATH
            File urlFile = new File(url.getPath());

            //SET MEMBER VAR. READ FILE AS NEW SCANNER OBJECT WITH CREATED URL
            readFile = new Scanner(urlFile);

            //LOOP THROUGH FILE UNTIL DOESNT HAVE NEXT
            while(readFile.hasNext())
            {
                //SET TEMP STRING TO NEXT STRING
                tempRead = readFile.next();
                String s = tempRead.toUpperCase();

                //ADD TEMP STRING TO FILE DATA ARRAY LIST OF STRINGS
                dictionaryTrie.put(s, s);
            }
        }
        //CATCH FILE NOT FOUND EXCEPTION
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //CLOSE FILE
            readFile.close();
        }
    }
}
