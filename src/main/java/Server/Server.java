package Server;
import common.Admin;
import common.Food;
import common.Restaurant;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    public static final int PORT = 8080;
    private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    private static Admin admin = new Admin();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        loadRestaurants();
//        for(Restaurant i:restaurants){
//            System.out.println(i);
//        }
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started. Waiting for client...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                try {
                    System.out.println("Client connected.");
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.flush();
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    String situation = inputStream.readUTF();

                    System.out.println(situation);
                    if(situation.equals("login")){
                        String username = inputStream.readUTF();
                        String password = inputStream.readUTF();

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
                                    findUser = true;
                                    break;
                                }
                            }

                            if (findUser) {
                                outputStream.writeUTF("Found");
                                outputStream.flush();
                            } else {
                                outputStream.writeUTF("!foundUser");
                                outputStream.flush();
                            }
                        } catch (IOException e) {
                            System.out.println("Error reading usernames file: " + e.getMessage());
                            e.printStackTrace();
                        }finally {
                            outputStream.close();
                            inputStream.close();
                        }
                    }
                    else if(situation.equals("list")){
//                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        outputStream.writeObject(restaurants);
                        outputStream.close();
                        System.out.println("Sent ArrayList");
                    }
                    else if(situation.equals("signup")){
                        try{
                            FileWriter file = new FileWriter("src/main/java/Server/usernames", true);
                            outputStream.flush();
                            Admin tempAdmin = (Admin) inputStream.readObject();
                            System.out.println(tempAdmin);
                            System.out.println("received new Admin");
                            String errorCode = checkInput(tempAdmin);
                            System.out.println(errorCode);
                            if(errorCode.equals("00000")){
                                outputStream.writeUTF("true");
                                outputStream.flush();
                                System.out.println(tempAdmin);
                                admin = tempAdmin;
                                file.write(admin.getName()+",");
                                file.write(admin.getPassword()+",");
                                file.write(admin.getPhoneNumber()+",");
                                file.write(admin.getEmail()+",");
                                file.write(admin.getAddress()+"\n");
                            }
                            else {
                                outputStream.writeUTF(errorCode);
                                outputStream.flush();
//                                break;
                            }
                            inputStream.close();
                            outputStream.close();
                            file.close();
                        }catch (IOException | ClassNotFoundException e) {
                            System.out.println("\nerror\n");
                            e.printStackTrace();
                        }
                    }
                    else if(situation.equals("getAdmin")){
//                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        outputStream.writeObject(admin);
                        outputStream.flush();
                        outputStream.close();
                        System.out.println("Sent Admin");
                    }
                    else if(situation.equals("setAdmin")){
//                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        admin = (Admin) inputStream.readObject();
                        System.out.println("received new Admin ");
                        inputStream.close();
                    }
                    else if (situation.equals("changeInfo")){
                        outputStream.flush();
                        Admin tempAdmin = (Admin) inputStream.readObject();
                        System.out.println(tempAdmin);
                        System.out.println("received new Admin changes");
                        String errorCode = checkInput(tempAdmin);
                        System.out.println(errorCode);
                        if(errorCode.equals("00000")){
                            outputStream.writeUTF("true");
                            outputStream.flush();
                            System.out.println(tempAdmin);
                            String newLine = tempAdmin.getName()+","+tempAdmin.getPassword()+","+tempAdmin.getPhoneNumber()+","+tempAdmin.getEmail()+","+tempAdmin.getAddress();
                            try {
                                // input the (modified) file content to the StringBuffer "input"
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

                                // write the new string with the replaced line OVER the same file
                                FileOutputStream fileOut = new FileOutputStream("src/main/java/Server/usernames");
                                fileOut.write(inputBuffer.toString().getBytes());
                                fileOut.close();

                            } catch (Exception e) {
                                System.out.println("Problem reading file.");
                            }
                            admin = tempAdmin;
                        }
                        else {
                            outputStream.writeUTF(errorCode);
                            outputStream.flush();
                        }
                        inputStream.close();
                        outputStream.close();
                    }
                } finally {
                    System.out.println("Closing connection...");
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
//            socket.close();
    }
    private static void loadRestaurants(){
        try {
            BufferedReader restaurantFile = new BufferedReader(new FileReader("src/main/java/Server/Restaurants"));
            BufferedReader menuFile = new BufferedReader(new FileReader("src/main/java/Server/Menus"));
            String resline;
            String menline;
            while ((resline = restaurantFile.readLine()) != null) {
                String[] parts = resline.split(",");
                boolean isTakeAway = (parts[3].equals("true"));
                int count = Integer.parseInt(parts[4]);
                restaurants.add(new Restaurant(parts[0], parts[1], parts[2], isTakeAway, count, parts[5]));
                int menu_items = Integer.parseInt(parts[6]);
                Restaurant rest = restaurants.get(restaurants.size() - 1);

                for (int i = 0; i < menu_items && (menline = menuFile.readLine()) != null; i++) {
                    String[] foodParts = menline.split(",");
                    double price = Double.parseDouble(foodParts[1]);
                    boolean isAvailable = foodParts[2].equals("true");
                    rest.add_menu(new Food(foodParts[0], price, isAvailable, foodParts[3]));
                }
            }
            menuFile.close();
            restaurantFile.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private static String checkInput(Admin admin){
        System.out.println(admin.getEmail());
        System.out.println(checkEmail(admin.getEmail()));
        String errorCode="";
        errorCode +=checkName(admin.getName());
        errorCode += checkPass(admin.getPassword());
        errorCode += checkNumber(admin.getPhoneNumber());
        errorCode += checkEmail(admin.getEmail());
        errorCode += "0";
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
