package Server;

import common.Admin;
import java.io.*;
import java.net.Socket;
import java.sql.*;

public class ClientHandler extends Server implements Runnable {
    private  Admin admin;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Connection connection;
    private static Statement statement;
    public ClientHandler(Socket socket, Admin admin, Connection connection) throws IOException, SQLException {
        this.socket = socket;
        this.admin = admin;
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
        this.connection = connection;
        statement = connection.createStatement();
    }

    @Override
    public void run() {
        try {
            while (true) {
                out.flush();
                String situation = in.readUTF();
                System.out.println(situation);
                if(situation.equals("SignUp")){
                    out.flush();
                    Admin tempAdmin = (Admin) in.readObject();
                    System.out.println(tempAdmin);
                    String errorCode = checkInput(tempAdmin);
                    System.out.println(errorCode);
                    out.flush();
                    if(errorCode.equals("00000")){
                        out.writeUTF("true");
                        out.flush();
                        admin = tempAdmin;
                        try{
                            String sql = "INSERT INTO usernames (name, password, phone, address, email, Balance) VALUES (?,?,?,?,?,?) ";
                            PreparedStatement pstmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                            // set parameters for statement
                            pstmt.setString(1, admin.getName());
                            pstmt.setString(2, admin.getPassword());
                            pstmt.setString(3, admin.getPhoneNumber());
                            pstmt.setString(4, admin.getAddress());
                            pstmt.setString(5, admin.getEmail());
                            pstmt.setDouble(6, admin.getMojodi());
                            pstmt.execute();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        out.writeUTF(errorCode);
                        out.flush();
                    }
                }
                else if (situation.equals("Login")){
                    String username = in.readUTF();
                    String password = in.readUTF();
                    boolean findUser = false;
                    try{
                        Statement statement = connection.createStatement();
                        ResultSet rs = statement.executeQuery("Select * From usernames");
                        while(rs.next()){
                            String name = rs.getString(2);
                            String pass = rs.getString(3);
                            if(username.equals(name) && password.equals(pass)){
                                admin.setName(rs.getString(2));
                                admin.setPassword(rs.getString(3));
                                admin.setPhoneNumber(rs.getString(4));
                                admin.setEmail(rs.getString(5));
                                admin.setAddress(rs.getString(6));
                                admin.setMojodi(rs.getDouble(7));
                                findUser = true;
                                break;
                            }
                        }
                        if (findUser) {
                            out.writeUTF("Found");
                            out.flush();
                        } else {
                            out.writeUTF("!foundUser");
                            out.flush();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
                else if(situation.equals("list")){
                    out.writeObject(restaurants);
                    System.out.println("Sent ArrayList");
                }
                else if(situation.equals("getAdmin")){
                    out.writeObject(admin);
                    out.flush();
                    System.out.println("Sent Admin");
                }
                else if(situation.equals("setAdmin")){
                    admin = (Admin) in.readObject();
                    System.out.println("received new Admin ");
                    in.close();
                }
                else if(situation.equals("ChangeMojodi")){
                    Admin tempAdmin = (Admin) in.readObject();
                    double balance = tempAdmin.getMojodi();
                    try{
                        PreparedStatement ps = (PreparedStatement) connection.prepareStatement("UPDATE usernames SET Balance = ? WHERE name = ?");
                        ps.setDouble(1, balance);
                        ps.setString(2, admin.getName());
                        ps.execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    admin = tempAdmin;
                }
                else if(situation.equals("ChangeInfo")){
                    out.flush();
                    Admin tempAdmin = (Admin) in.readObject();
                    String errorCode = checkInput(tempAdmin);
                    System.out.println(errorCode);
                    out.flush();
                    if(errorCode.equals("00000")){
                        out.flush();
                        out.writeUTF("true");
                        out.flush();
                        try{
                            PreparedStatement ps = (PreparedStatement) connection.prepareStatement("UPDATE usernames SET name = ?, password=?, phone=?, address=?, email=?, Balance=? WHERE name = ?");
                            ps.setString(1, tempAdmin.getName());
                            ps.setString(2, tempAdmin.getPassword());
                            ps.setString(3, tempAdmin.getPhoneNumber());
                            ps.setString(4, tempAdmin.getAddress());
                            ps.setString(5, tempAdmin.getEmail());
                            ps.setDouble(6, tempAdmin.getMojodi());
                            ps.setString(7, admin.getName());
                            ps.execute();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        admin = tempAdmin;
                    }
                    else {
                        out.writeUTF(errorCode);
                        out.flush();
                    }
                }
                else{
                    System.out.println("Closing the socket...");
                    break;
                }
            }
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String checkInput(Admin admin){
        String errorCode="";
        errorCode +=checkName(admin.getName());
        errorCode += checkPass(admin.getPassword());
        errorCode += checkNumber(admin.getPhoneNumber());
        errorCode += checkEmail(admin.getEmail());
        if(admin.getAddress().equals("")){
            errorCode += "1";
        }
        else {
            errorCode += "0";
        }
            return errorCode+"";
    }
    private static int checkName(String name){
        // 1== already in use
        //2==short
        if(name.length()<4)return 2;
        try{
            ResultSet rs = statement.executeQuery("SELECT name FROM usernames");
            while(rs.next()){
                System.out.println(rs);
                if(rs.equals(name))
                    return 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
    private static int checkPass(String pass){
        //1 weak
        //2 length
        if(pass.length()<8)return 2;
        char[] parts = pass.toCharArray();
        int upperCaseCount=0;
        int numberCount=0;
        for(int i=0; i<pass.length(); i++){
            int ascii = parts[i];
            if(ascii>=65 && ascii<=90)upperCaseCount++;
            else if((ascii>=48 && ascii<=57)) numberCount++;
            else continue;
        }
        if(upperCaseCount<2 || numberCount<2)return 1;
        return 0;
    }
    private static int checkNumber(String number){
        //1 invalid
        if(number.length() != 11)return 1;
        if(!number.substring(0,2).equals("09"))return 1;
        char[] parts = number.toCharArray();
        for(int i=0; i<11; i++){
            int ascii = parts[i];
            if(ascii<48 || ascii>57)return 1;
        }
        return 0;
    }
    public static int checkEmail(String email) {
        if (email == null || email.isEmpty()) {
            return 1;
        }
        // Check if the email contains an @ symbol
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return 1;
        }

        // Check if there is at least one character before the @ symbol
        if (atIndex == 0) {
            return 1;
        }

        // Check if there is at least one character after the @ symbol
        if (atIndex == email.length() - 1) {
            return 1;
        }

        // Split the email into local-part and domain
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex + 1);

        // Check if the local-part is valid
        if (!isValidLocalPart(localPart)) {
            return 1;
        }

        // Check if the domain part is valid
        if (!isValidDomainPart(domainPart)) {
            return 1;
        }

        return 0;
    }
    private static boolean isValidLocalPart(String localPart) {
        String localPartPattern = "[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+";
        return localPart.matches(localPartPattern);
    }
    private static boolean isValidDomainPart(String domainPart) {
        String domainPartPattern = "[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*";
        return domainPart.matches(domainPartPattern) || domainPart.matches("[a-zA-Z]{2,}");
    }
}
