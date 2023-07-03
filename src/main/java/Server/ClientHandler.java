package Server;

import common.Admin;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Server implements Runnable {
    private  Admin admin;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public ClientHandler(Socket socket, Admin admin) throws IOException {
        this.socket = socket;
        this.admin = admin;
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
            out.flush();
            String situation = in.readUTF();
            System.out.println(situation);
            if(situation.equals("login")){
                String username = in.readUTF();
                String password = in.readUTF();

                System.out.println(username + " " + password);
                boolean findUser = false;
                try (BufferedReader userFile = new BufferedReader(new FileReader("src/main/java/Server/usernames"))) {
                    String line;
                    while ((line = userFile.readLine()) != null && !findUser) {
                        String[] parts = line.split(",");
//                                System.out.println(Arrays.toString(parts));
                        if (parts[0].equals(username) && parts[1].equals(password)) {
                            admin.setName(parts[0]);
                            admin.setPassword(parts[1]);
                            admin.setPhoneNumber(parts[2]);
                            admin.setEmail(parts[3]);
                            admin.setAddress(parts[4]);
                            admin.setMojodi(Double.parseDouble(parts[5]));
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
                } catch (IOException e) {
                    System.out.println("Error reading usernames file: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            else if(situation.equals("list")){
                out.writeObject(restaurants);
                System.out.println("Sent ArrayList");
            }
            else if(situation.equals("signup")){
                try{
                    FileWriter file = new FileWriter("src/main/java/Server/usernames", true);
                    out.flush();
                    Admin tempAdmin = (Admin) in.readObject();
                    System.out.println(tempAdmin);
                    System.out.println("received new Admin");
                    String errorCode = checkInput(tempAdmin);
                    System.out.println(errorCode);
                    if(errorCode.equals("00000")){
                        out.writeUTF("true");
                        out.flush();
                        System.out.println(tempAdmin);
                        admin = tempAdmin;
                        file.write(admin.getName()+",");
                        file.write(admin.getPassword()+",");
                        file.write(admin.getPhoneNumber()+",");
                        file.write(admin.getEmail()+",");
                        file.write(admin.getAddress()+",");
                        file.write(admin.getMojodi()+"\n");
                    }
                    else {
                        out.writeUTF(errorCode);
                        out.flush();
                    }
                    file.close();
                }catch (IOException | ClassNotFoundException e) {
                    System.out.println("\nerror\n");
                    e.printStackTrace();
                }
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
            else if(situation.equals("changeMojodi")){
                out.flush();
                Admin tempAdmin = (Admin) in.readObject();
                System.out.println(tempAdmin.getMojodi());
                System.out.println("received new Mojodi");
                String newLine = tempAdmin.getName()+","+tempAdmin.getPassword()+","+tempAdmin.getPhoneNumber()+","+tempAdmin.getEmail()+","+tempAdmin.getAddress()+","+tempAdmin.getMojodi();
                try {
                    BufferedReader file = new BufferedReader(new FileReader("src/main/java/Server/usernames"));
                    StringBuffer inputBuffer = new StringBuffer();
                    String line;

                    while ((line = file.readLine()) != null) {
                        String[] parts = line.split(",");
                        if(parts[0].equals(admin.getName())){
                            line = newLine;
                        }
                        inputBuffer.append(line);
                        inputBuffer.append('\n');
                    }
                    file.close();
                    FileOutputStream fileOut = new FileOutputStream("src/main/java/Server/usernames");
                    fileOut.write(inputBuffer.toString().getBytes());
                    fileOut.close();

                } catch (Exception e) {
                    System.out.println("Problem reading file.");
                }
                admin = tempAdmin;
            }
            else if (situation.equals("changeInfo")){
                out.flush();
                Admin tempAdmin = (Admin) in.readObject();
                System.out.println(tempAdmin);
                System.out.println("received new Admin changes");
                String errorCode = checkInput(tempAdmin);
                System.out.println(errorCode);
                if(errorCode.equals("00000")){
                    out.writeUTF("true");
                    out.flush();
                    System.out.println(tempAdmin);
                    String newLine = tempAdmin.getName()+","+tempAdmin.getPassword()+","+tempAdmin.getPhoneNumber()+","+tempAdmin.getEmail()+","+tempAdmin.getAddress()+","+tempAdmin.getMojodi();
                    try {
                        BufferedReader file = new BufferedReader(new FileReader("src/main/java/Server/usernames"));
                        StringBuffer inputBuffer = new StringBuffer();
                        String line;

                        while ((line = file.readLine()) != null) {
                            String[] parts = line.split(",");
                            if(parts[0].equals(admin.getName())){
                                line = newLine;
                            }
                            inputBuffer.append(line);
                            inputBuffer.append('\n');
                        }
                        file.close();

                        FileOutputStream fileOut = new FileOutputStream("src/main/java/Server/usernames");
                        fileOut.write(inputBuffer.toString().getBytes());
                        fileOut.close();

                    } catch (Exception e) {
                        System.out.println("Problem reading file.");
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
        try (BufferedReader userFile = new BufferedReader(new FileReader("src/main/java/Server/usernames"))){
            String line;
            while((line = userFile.readLine()) != null){
                String[] parts = line.split(",");
                if(parts[0].equals(name))return 1;
            }
        }catch(IOException e){
            e.printStackTrace();
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
