package buzzword;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

/**
 * Created by Feazan on 11/26/2016.
 */
public class ProfileManager {
    private HashMap<String, UserProfile> userProfileMap;

    public ProfileManager ()
    {
        userProfileMap = new HashMap<>();
        this.loadUserData();
    }

    public void createNewProfile(String username, String plainTextPassword)
    {
        try {
            String hashedPassword = encryptedPassword(plainTextPassword);
            UserProfile userprofile = new UserProfile();
            userprofile.setHashedPassword(hashedPassword);
            userprofile.setUserName(username);
            updateUserProfile(userprofile);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void updateUserProfile(UserProfile userprofile)
    {
        userProfileMap.put(userprofile.getUserName(), userprofile);
        ObjectMapper mapper = new ObjectMapper();
        File usersFile = getUserFile();

        try {
            mapper.writeValue(usersFile, userProfileMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean verifyUserLogin(String username, String plainTextPassword)
    {
        if(userProfileMap.containsKey(username))
        {
            UserProfile profileToCheck = userProfileMap.get(username);
            String testPassword = null;
            try {
                testPassword = encryptedPassword(plainTextPassword);
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Failed to hash password");
                return false;
            }
            return (testPassword.equals(profileToCheck.getHashedPassword()));
        }
        else
        {
            return false;
        }
    }

    public UserProfile getUserProfile(String username)
    {
        return userProfileMap.get(username);
    }

    /**
     *  check if the username already exists in the userProfile hashmap
     * @param userName
     * @return boolean
     */
    public boolean checkIfProfileExists (String userName)
    {
        return userProfileMap.containsKey(userName);
    }

    /**
     *
     * @param plainTextPassword
     * @return
     * @see "https://www.mkyong.com/java/java-sha-hashing-example/"
     */
    public String encryptedPassword (String plainTextPassword) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(plainTextPassword.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private void loadUserData ()
    {
        File usersFile = getUserFile();

        ObjectMapper mapper = new ObjectMapper();
        try {
           UserProfile[] users = mapper.readValue(usersFile, UserProfile[].class);

            userProfileMap.clear();
            for(UserProfile userprofile : users)
            {
                userProfileMap.put(userprofile.getUserName(), userprofile);
            }
        } catch (JsonMappingException ex){
            //TODO check if file is empty before deserializing
            System.out.println(ex.getMessage());}
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getUserFile() {
        File usersFile = new File("userData/users.json");
        if(!usersFile.exists())
        {
            try {
                usersFile.createNewFile();
                System.out.println("Creating new File");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return usersFile;
    }


}
