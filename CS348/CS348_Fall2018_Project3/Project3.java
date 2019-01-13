import java.io.*;
import java.sql.*;

/* Project3 CS348 */
public class Project3 {

    //connecting to Oracle database information
    private static final String ORACLE_DRIVER = "oracle.jdbc.OracleDriver";
    private static final String JDBC_URL = "jdbc:oracle:thin:@claros.cs.purdue.edu:1524:strep";
    private static final String USERNAME = "womalley";
    private static final String PASSWORD = "caddy1990";

    private static Connection conn = null;
    private static Statement state = null;

    //encrypt and decrypt variables
    private static String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String lowerCase = "abcdefghijklmnopqrstuvwxyz";

    /* ENCRYPT */
    private static String encrypt (String plaintext, String key) {
        String ciphertext = ""; //initailize empty string to store calculated cipher for return

        int plainIndex = 0;         //plaintext index
        int encIndex = 0;           //k` index
        int i = plaintext.length(); //decrementor 

        String encStr = (key + plaintext); //concat plaintext onto key for encryption

        for (i = plaintext.length() - 1; i >= 0; i--) { //Added -1 to length EDITED
            if ((((plaintext.charAt(plainIndex) >= 'a') && (plaintext.charAt(plainIndex) <= 'z')) || ((plaintext.charAt(plainIndex) >= 'A') && (plaintext.charAt(plainIndex) <= 'Z'))) &&
                (((encStr.charAt(encIndex) >= 'a') && (encStr.charAt(encIndex) <= 'z')) || ((encStr.charAt(encIndex) >= 'A') && (encStr.charAt(encIndex) <= 'Z')))) {
                
                //must ensure same letter casing for quick mafs
                int letterVal1 = upperCase.indexOf(Character.toUpperCase(plaintext.charAt(plainIndex)));
                int letterVal2 = upperCase.indexOf(Character.toUpperCase(encStr.charAt(encIndex)));

                int letterNumber = ((letterVal1 + letterVal2) % 26); // <- the quick mafs

                //restore letter case
                if (Character.isLowerCase(plaintext.charAt(plainIndex))) {
                    char cipherLetter = lowerCase.charAt(letterNumber);
                    ciphertext = (ciphertext + cipherLetter);
                }
                else {
                    char cipherLetter = upperCase.charAt(letterNumber);
                    ciphertext = (ciphertext + cipherLetter);
                }

                //update indices
                plainIndex++;
                encIndex++;
            }
            //case when not a letter (i.e. number or other non-alphabetic characters)
            else {
                //set to var and to lower case for simplicity of checking
                char plainChar = plaintext.charAt(plainIndex);
                plainChar = Character.toLowerCase(plainChar);
                char encChar = encStr.charAt(encIndex);
                encChar = Character.toLowerCase(encChar);

                if (!((plainChar >= 'a') && (plainChar <= 'z'))) {
                    ciphertext = ciphertext + plaintext.charAt(plainIndex);
                    plainIndex++;
                }

                if (!((encChar >= 'a') && (encChar <= 'z'))) {
                    encIndex++;
                }
            }
        }
	if (!ciphertext.substring(ciphertext.length() - 1).equals("\'")) { ciphertext += "'"; } //Added to make sure \' was added EDITED
	
        return (ciphertext);
    }

    /* DECRYPT */
    private static String decrypt (String ciphertext, String key) {
        String plaintext = ""; //initialize empty string to store decrypted cipher for return

        int cipherIndex = 0;        //ciphertext index
        int encIndex = 0;           //k` index

        int i = ciphertext.length(); //decrementor 

        //System.out.println("CIPHER: " + ciphertext);
        //System.out.println("key: " + key);
        for (i = ciphertext.length(); i > 0; i--) {
            if ((((ciphertext.charAt(cipherIndex) >= 'a') && (ciphertext.charAt(cipherIndex) <= 'z')) || ((ciphertext.charAt(cipherIndex) >= 'A') && (ciphertext.charAt(cipherIndex) <= 'Z'))) &&
                (((key.charAt(encIndex) >= 'a') && (key.charAt(encIndex) <= 'z')) || ((key.charAt(encIndex) >= 'A') && (key.charAt(encIndex) <= 'Z')))) {
                
                //must ensure same letter casing for quick mafs
                int letterVal1 = upperCase.indexOf(Character.toUpperCase(ciphertext.charAt(cipherIndex)));
                int letterVal2 = upperCase.indexOf(Character.toUpperCase(key.charAt(encIndex)));

                //System.out.println(letterVal1 + " - " + letterVal2);
                int letterNumber = ((letterVal1 - letterVal2) % 26); // <- the quick mafs

                //handle negative value, apparently modulo does not handle this...
                if (letterNumber < 0) {
                    letterNumber = (letterNumber + 26);
                }

                //System.out.println("letternum: " + letterNumber);

                //restore letter case
                if (Character.isLowerCase(ciphertext.charAt(cipherIndex))) {
                    char plainLetter = lowerCase.charAt(letterNumber);
                    key = (key + plainLetter);
                    plaintext = (plaintext + plainLetter);
                }
                else {
                    char plainLetter = upperCase.charAt(letterNumber);
                    key = (key + plainLetter);
                    plaintext = (plaintext + plainLetter);
                }

                //update indices
                cipherIndex++;
                encIndex++;
            }
            //case when not a letter (i.e. number or other non-alphabetic characters)
            else {
                //set to var and to lower case for simplicity of checking
                char cipherChar = ciphertext.charAt(cipherIndex);
                cipherChar = Character.toLowerCase(cipherChar);
                char encChar = key.charAt(encIndex);
                encChar = Character.toLowerCase(encChar);

                if (!((cipherChar >= 'a') && (cipherChar <= 'z'))) {
                    plaintext = plaintext + ciphertext.charAt(cipherIndex);
                    cipherIndex++;
                }

                if (!((encChar >= 'a') && (encChar <= 'z'))) {
                    encIndex++;
                }
            }
        }
        return (plaintext);
    }

    /*
    //checks to see if current user is an ADMIN
    private boolean isAdmin() {
        return (this._userName.equals("admin"));
    }
    */

    private static int lineCounter = 0; //displays the line number
    private static FileWriter fw;
    private static BufferedWriter bw;

    //writes message to output file
    public static void writeToFile (String write) {
        if (bw != null) {
            try {
		        //System.out.println("WRITE: " + write);
                bw.write(++lineCounter + ": " + write + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("ERROR: FileWriter not initialized");
        }
    }

    //CREATE ROLES function
    public static void createRoles(String[] strArr, int userId, Statement state) {
        try {
            //normal user
            if (userId != 1) {
                writeToFile("CREATE ROLE " + strArr[2] + " " + strArr[3] + "\nAuthorization failure\n");
            }
            //admin case
            else {
                ResultSet rs = state.executeQuery("SELECT MAX(RoleId) AS nxt FROM Roles");

                int nxt = 0;
                while(rs.next()) {
                    nxt = (rs.getInt("nxt") + 1);
                }

                rs = state.executeQuery("INSERT INTO Roles VALUES (" + nxt + ", '" + strArr[2] + "', '" + strArr[3] + "')"); //(%d, '%s', '%s')", nxt, strArr[2], strArr[3]);

                writeToFile("CREATE ROLE " + strArr[2] + " " + strArr[3] + "\nRole created successfully\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CREATE USER function
    public static void createUser(String[] strArr, int userId) {
        try {
            //normal user
            if (userId != 1) {
                writeToFile("CREATE USER " + strArr[2] + " " + strArr[3] + "\nAuthorization failure\n");
            }
            //admin user
            else {
                ResultSet rs = state.executeQuery("SELECT MAX(UserId) AS nxt FROM Users");

                int nxt = 0;
                while(rs.next()) {
                    nxt = (rs.getInt("nxt") + 1);
                }

                rs = state.executeQuery("INSERT INTO Users VALUES (" + nxt + ", '" + strArr[2] + "', '" + strArr[3] + "')");

                writeToFile("CREATE USER " + strArr[2] + " " + strArr[3] + "\nUser created successfully\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GRANT ROLE function
    public static void grantRole(String[] strArr, int userId) {
        try {
            //normal user
            if (userId != 1) {
                writeToFile("GRANT ROLE " + strArr[2] + " " + strArr[3] + "\nAuthorization failure\n");
            }
            //admin user
            else {
                int usernameId;
                int roleNameId;

                //gets roleName ID
                ResultSet rs = state.executeQuery("SELECT RoleId FROM Roles WHERE RoleName = '" + strArr[3] + "'");
                rs.next();
                roleNameId = rs.getInt("RoleId");

                //gets username ID
                rs = state.executeQuery("SELECT UserId FROM Users WHERE username = '" + strArr[2] + "'");
                rs.next();
                usernameId = rs.getInt("UserId");

                //Insert into table
                rs = state.executeQuery("INSERT INTO UsersRoles VALUES (" + usernameId + "," + roleNameId + ")");
                rs.next();

                writeToFile("GRANT ROLE " + strArr[2] + " " + strArr[3] + "\nRole assigned successfully\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //GRANT PRIVILEGE function
    public static void grantPrivilege(String[] strArr, int userId) {
        try {
            if (userId != 1) {
                writeToFile("GRANT PRIVILEGE " + strArr[2] + " TO " + strArr[4] + " ON " + strArr[6] + "\nAuthorization failure\n");
            }
            else {
                int roleNameId;
                int privilegeId;

                //gets roleName ID
                ResultSet rs;
                rs = state.executeQuery("SELECT RoleId FROM Roles WHERE RoleName = '" + strArr[4] + "'");
                rs.next();
                roleNameId = rs.getInt("RoleId");

                //gets privilege ID
                rs = state.executeQuery("SELECT PrivId FROM Privileges WHERE PrivName = '" + strArr[2] + "'");
                rs.next();
                privilegeId = rs.getInt("PrivId");

                //Insert into table
                rs = state.executeQuery("INSERT INTO RolesPrivileges VALUES (" + roleNameId + "," + privilegeId + ",'" + strArr[6] + "')");
                rs.next();

                writeToFile("GRANT PRIVILEGE " + strArr[2] + " TO " + strArr[4] + " ON " + strArr[6] + "\nPrivilege granted successfully\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //REVOKE PRIVILEGE function
    public static void revokePrivilege(String[] strArr, int userId) {
        try {
            if (userId != 1) {
                writeToFile("GRANT PRIVILEGE " + strArr[2] + " TO " + strArr[4] + " ON " + strArr[6] + "\nAuthorization failure\n");
            }
            else {
                int roleNameId;
                int privilegeId;

                //gets roleName ID
                ResultSet rs;
                rs = state.executeQuery("SELECT RoleId FROM Roles WHERE RoleName = '" + strArr[4] + "'");
                rs.next();
                roleNameId = rs.getInt("RoleId");

                //gets privilege ID
                rs = state.executeQuery("SELECT PrivId FROM Privileges WHERE PrivName = '" + strArr[2] + "'");
                rs.next();
                privilegeId = rs.getInt("PrivId");

                //DELETE from table
                rs = state.executeQuery("DELETE FROM RolesPrivileges WHERE RoleId = " + roleNameId + " AND PrivId = " + privilegeId + " AND TableName = '" + strArr[6] + "'");
                rs.next();

                writeToFile("REVOKE PRIVILEGE " + strArr[2] + " FROM " + strArr[4] + " ON " + strArr[6] + "\nPrivilege revoked successfully\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //INSERT INTO <tablename> VALUES function
    public static void insertInto(String inputStr, String[] strArr, int userId) {
        try {
            ResultSet rs;
            boolean hasPrivi = false;

            //check user by ID for INSERT privilege
            rs = state.executeQuery("SELECT RoleId FROM UsersRoles WHERE UserId = " + userId);
            
            //grab all VALUE attributes and parse into array for later use
            String[] values = inputStr.split("\\(");
            String tmp = values[1];
            values = tmp.split("\\)");
            tmp = values[0];
            String[] parsedValues = tmp.split(", ");

            if (rs.isBeforeFirst()) {
                //user has privileges
                while (rs.next()) {
                    int roleId1 = rs.getInt("RoleId");
                    ResultSet rsNew;
                    Statement stateNew = conn.createStatement();

                    rsNew = stateNew.executeQuery("SELECT * FROM RolesPrivileges WHERE PrivId = 1 AND RoleId = " + roleId1 + " AND tableName = '" + strArr[2] + "'");
                
                    //user has INSERT privilege!
                    if (rsNew.isBeforeFirst()) {
                        hasPrivi = true;
                        String[] encryptStr = inputStr.split("ENCRYPT ");
                        String temp = encryptStr[1];
                        encryptStr = temp.split(" ");

                        //check encryption column number value
                        //encryption column value is zero
                        if (encryptStr[0].equals("0")) {
                            int i = 0;
                            String insertVals = "";
                            for (i = 0; i <= parsedValues.length - 1; i++) {
                                //add 2nd to n values
                                if (insertVals.length() != 0) {
                                    insertVals += ("," + parsedValues[i]);
                                }
                                //add first value
                                else {
                                    insertVals = parsedValues[i];
                                }
                            }

                            //insert values
                            rsNew = stateNew.executeQuery("INSERT INTO " + strArr[2] + " VALUES (" + insertVals + ",0,0)");
                       	    writeToFile(inputStr + "\nRow inserted successfully\n"); //Forgot this line..... EDITED!
		       	}
                        //encryption column value is positive number
                        else {
                            int i = 0;
                            String insertVals = "";
                            String encKey = "";
                            int orId = 0; //owner role ID

                            rsNew = stateNew.executeQuery("SELECT EncryptionKey FROM Roles WHERE RoleName = '" + encryptStr[1] + "'");
                            rsNew.next();
                            encKey = rsNew.getString("EncryptionKey");

                            while (i <= (parsedValues.length - 1)) {
                                
                                String encVal = parsedValues[i];
                                if (i == (Integer.parseInt(encryptStr[0]) - 1)) {
                                    //System.out.println("INSERT: " + encVal);
                                    encVal = encrypt(encVal, encKey);
                                    //System.out.println("AFTER: " + encVal);
                                }

                                //add 2nd to n values
                                if (insertVals.length() != 0) {
                                    insertVals += ("," + encVal);
                                }
                                //add first value
                                else {
                                    insertVals = encVal;
                                }

                                i++;
                            }

                            rsNew = stateNew.executeQuery("SELECT RoleId FROM Roles WHERE RoleName = '" + encryptStr[1] + "'");
                            rsNew.next();

                            orId = rsNew.getInt("RoleId");
                            rsNew = stateNew.executeQuery("INSERT INTO " + strArr[2] + " VALUES (" + insertVals + "," + encryptStr[0] + "," + orId + ")");

                            writeToFile(inputStr + "\nRow inserted successfully\n");
                        }
                    }

                }
            }
            else {
                //user does NOT have any privilege
                writeToFile(inputStr + "\nAuthorization failure\n");
            }

            //no privilege after checking roles
            if (hasPrivi == false) { writeToFile(inputStr + "\nAuthorization failure\n");}

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //SELECT * FROM function
    private static void selectFrom(String inputStr, String[] strArr, int userId) {
        try {
            ResultSet rs;
            ResultSet rsNew;
            Statement stateNew;
            ResultSet rsNewNew;
            //Statement StateNewNew;
            boolean hasPrivi = false;
            String colNameStr = "";
            boolean entered = false;
            String decryptStr = "";
            String decryptWrite = "";

            //check user by ID for SELECT privilege
            rs = state.executeQuery("SELECT RoleId FROM UsersRoles WHERE UserId = " + userId);
            
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    int roleId1 = rs.getInt("RoleId");

                    stateNew = conn.createStatement();
                    //BELOW COMMAND MUST BE WRONG!!!!!!!
                    rsNew = stateNew.executeQuery("SELECT * FROM RolesPrivileges WHERE PrivId = 2 AND RoleId = " + roleId1 + " AND tableName = '" + strArr[3] + "'");
                
                    if (rsNew.isBeforeFirst()) {

                        rsNew = stateNew.executeQuery("SELECT * FROM " + strArr[3]);
                        ResultSetMetaData meta = rsNew.getMetaData();

                        int i = 0;
                        while (i < (meta.getColumnCount() - 2)) {
                            //first addition to string
                            if (i == 0) {
                                colNameStr = meta.getColumnName(++i);
                            }
                            //adding to non-empty string
                            else {
                                colNameStr += (", " + meta.getColumnName(++i));
                            }
                        }

                        while (rsNew.next()) {

                            int k = 0;
                            while (k < (meta.getColumnCount() - 2)) {
                                decryptStr = rsNew.getString(++k);

                                if ((rsNew.getInt(meta.getColumnCount() - 1)) == k) {
                                    Statement stateNewNew = conn.createStatement();
                                    rsNewNew = stateNewNew.executeQuery("SELECT RoleId FROM UsersRoles WHERE UserId = " + userId);

                                    if (rsNewNew.isBeforeFirst()) {

                                        while (rsNewNew.next()) {

                                            if (rsNew.getInt(meta.getColumnCount()) == rsNewNew.getInt("RoleId")) {
                                                rsNewNew = stateNewNew.executeQuery("SELECT EncryptionKey FROM Roles WHERE RoleId = " + rsNew.getInt(meta.getColumnCount()));
                                                rsNewNew.next();
                                                 
                                                //DECRYPT string
                                                decryptStr = decrypt(decryptStr, rsNewNew.getString("EncryptionKey"));
                                                //System.out.println("DEC: " + decryptStr);
                                                entered = true; //used to break while loop
                                            }
                                        }
				    }

                                }


                                //first meta string to be added to write out
                                if (decryptWrite.length() == 0) {
                                    decryptWrite = decryptStr;
                                }
                                //second to n meta string to be added to write out variable
                                else {
                                    decryptWrite += (", " + decryptStr);
                                }

			    }
			    //if (entered == true) { break; }
                            //add meta to string for printing
                            //writeToFile(inputStr + "\n" + colNameStr + "\n" + decryptWrite + "\n");
			}
                        hasPrivi = true;
                    }
		    if (entered == true) { break; } //Added the needed break EDITED!

                }
                if (colNameStr.length() != 0 && decryptWrite.length() != 0) { writeToFile(inputStr + "\n" + colNameStr + "\n" + decryptWrite + "\n"); }  //Moved from above EDITED!
            }
            else {
                //not authorized
                writeToFile(inputStr + "\nAuthorization failure\n");
            }

            if (hasPrivi == false) {
                writeToFile(inputStr + "\nAuthorization failure\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String command = null; //sql command for functions

    public static void main(String[] args) {

        boolean quitCalled = false;

        //initialize database connection
        try {
            Class.forName(ORACLE_DRIVER);
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            state = conn.createStatement();
 
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String inputStr = null;
            int userId = 0; //holds the current users ID
            BufferedReader br = new BufferedReader(new FileReader(args[0])); //reads in input
            bw = new BufferedWriter(new FileWriter(args[1])); //for writing to output file

            while ((inputStr = br.readLine()) != null ) {

                String[] strArr = inputStr.split(" "); //holds split string in array

                //handles LOGIN
                if (strArr[0].equals("LOGIN")) {
                    String user = strArr[1];
                    String pass = strArr[2];

                    command = "SELECT UserId FROM Users WHERE Username = '" + user + "' AND Password = '" + pass + "'";

                    ResultSet rs = state.executeQuery(command);
			
                    if (rs.isBeforeFirst()) {
                        while (rs.next()) {
                            userId = rs.getInt("userid");
                            writeToFile(inputStr + "\nLogin successful\n");
                        }
                    }
                    else {
                        writeToFile(inputStr + "\nInvalid login\n"); //added inputStr EDITED!
                    }
                }
                //handles CREATE ROLE
                else if (strArr[0].equals("CREATE") && strArr[1].equals("ROLE")) {
                    if (quitCalled == false) {
                        createRoles(strArr, userId, state);
                    }
                }
                //handles CREATE USER
                else if (strArr[0].equals("CREATE") && strArr[1].equals("USER")) {
                    if (quitCalled == false) {
                        createUser(strArr, userId);
                    }
                }
                //handles GRANT ROLE
                else if (strArr[0].equals("GRANT") && strArr[1].equals("ROLE")) {
                    if (quitCalled == false) {
                        grantRole(strArr, userId);
                    }
                }
                //handles GRANT PRIVILEGE
                else if (strArr[0].equals("GRANT") && strArr[1].equals("PRIVILEGE")) {
                    if (quitCalled == false) {
                        grantPrivilege(strArr, userId);
                    }
                }
                //handles REVOKE PRIVILEGE
                else if (strArr[0].equals("REVOKE") && strArr[1].equals("PRIVILEGE")) {
                    if (quitCalled == false) {
                        revokePrivilege(strArr, userId);
                    }
                }
                //handles INSERT INTO <TABLENAME> VALUES
                else if (strArr[0].equals("INSERT") && strArr[1].equals("INTO") && strArr[3].equals("VALUES")) {
                    if (quitCalled == false) {
                        insertInto(inputStr, strArr, userId);
                    }
                }
                //handles SELECT * FROM <TABLENAME>
                else if (strArr[0].equals("SELECT") && strArr[2].equals("FROM")) {
                    if (quitCalled == false) {
                        selectFrom(inputStr, strArr, userId);
                    }
                }
                //handles QUIT
                else if (strArr[0].equals("QUIT")) {
                    quitCalled = true;
                    writeToFile("QUIT");
                }
                //handles none cases
                else {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (bw != null) {
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
