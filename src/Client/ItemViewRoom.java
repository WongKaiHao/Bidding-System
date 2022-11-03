/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import ADT.ArrayList;
import ADT.ListInterface;
import ADT.SortedDoublyLinkedList;
import ADT.SortedList;
import Entity.Comments;
import Entity.Item;
import Entity.Transaction;
import Entity.User;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Ho Wei Lee
 */
public class ItemViewRoom extends javax.swing.JFrame {

    private ListInterface<Item> itemList = new ArrayList<>();
    private ListInterface<Comments> record = new ArrayList<>();
    private SortedList<User> userList = new SortedDoublyLinkedList<>();
    private ListInterface<Transaction> payList = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private int count = 0;
    private User itemOwner;
    private double bidprice;
    private User user;
    private Image pic;
    private int sequence = 1;

    /**
     * Creates new form ItemListingRoom
     */
    public ItemViewRoom() {
        try {
            readFile();
            initComponents();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Auction haven't started yet", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public ItemViewRoom(User userLogin) {
        try {
            this.user = userLogin;
            readFile();
            initComponents();
            countItem();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Auction haven't started yet", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void readFile() {
        readItem();
        initUser();
        createTran();
        readTran();
        createComment();
        readComment();
    }

    public void initUser() {
        try {
            File myWrt = new File("userlist.txt");
            Scanner myReader = new Scanner(myWrt);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Scanner input = new Scanner(data);
                input.useDelimiter("[@]");
                while (input.hasNext()) {
                    String userId = input.next();
                    String name = input.next();
                    String password = input.next();
                    long bankNo = Long.parseLong(input.next());
                    double balance = Double.parseDouble(input.next());
                    userList.add(new User(userId, name, password, bankNo, balance));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured!");
            e.printStackTrace();
        }
    }

    private void writeUserList() {
        try {
            FileWriter myWriter = new FileWriter("userlist.txt");
            myWriter.write(userList.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void readItem() {
        try {
            File myWrt = new File("itemlist.txt");
            Scanner myReader = new Scanner(myWrt);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Scanner input = new Scanner(data);
                input.useDelimiter("[#]");
                while (input.hasNext()) {
                    String itemId = input.next();
                    String itemPic = input.next();
                    String itemName = input.next();
                    String itemDesc = input.next();
                    double cost = Double.parseDouble(input.next());
                    double bidPrice = Double.parseDouble(input.next());
                    String endBidTime = input.next();
                    String ownerID = input.next();
                    itemList.add(new Item(itemId, itemPic, itemName, itemDesc, cost, bidPrice, endBidTime, ownerID));
                }
            }
            User.setItemList(itemList);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured!");
            e.printStackTrace();
        }
    }

    private void writeItem() {
        try {
            FileWriter myWriter = new FileWriter("itemlist.txt");
            myWriter.write(User.getItemList().toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void countItem() {
        for (int i = 1; i <= itemList.getSize(); i++) {
            if (itemList.getEntry(i).getBidPrice() != 0) {
                count++;
            }
        }
        if (count == 0) {
            JOptionPane.showMessageDialog(null, "Auction haven't started yet", "INFO", JOptionPane.INFORMATION_MESSAGE);
        } else {
            displayNext();
            displayTimer();
        }
    }

    private void createTran() {
        try {
            File myObj = new File("transaction.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("There is an error occurred.");
            e.printStackTrace();
        }
    }

    private void readTran() {
        try {
            File myWrt = new File("transaction.txt");
            Scanner myReader = new Scanner(myWrt);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                Scanner input = new Scanner(data);
                input.useDelimiter("[!]");
                while (input.hasNext()) {
                    String newOwner = input.next();
                    String name = input.next();
                    String password = input.next();
                    long bankNo = Long.parseLong(input.next());
                    double balance = Double.parseDouble(input.next());
                    String itemId = input.next();
                    String itemPic = input.next();
                    String itemName = input.next();
                    String itemDesc = input.next();
                    double cost = Double.parseDouble(input.next());
                    double bidPrice = Double.parseDouble(input.next());
                    String endBidTime = input.next();
                    String ownerID = input.next();
                    payList.add(new Transaction(new User(newOwner, name, password, bankNo, balance), new Item(itemId, itemPic, itemName, itemDesc, cost, bidPrice, endBidTime, ownerID)));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured!");
            e.printStackTrace();
        }
    }

    private void writeTran() {
        try {
            FileWriter myWriter = new FileWriter("transaction.txt");
            myWriter.write(payList.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void createComment() {
        try {
            File myObj = new File("comment.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("There is an error occurred.");
            e.printStackTrace();
        }
    }

    private void readComment() {
        try {
            File myWrt = new File("comment.txt");
            Scanner myReader = new Scanner(myWrt);
            while (myReader.hasNextLine()) {
                ListInterface<String> commentList = null;
                String data = myReader.nextLine();
                Scanner input = new Scanner(data);
                input.useDelimiter("[~]");
                while (input.hasNext()) {
                    commentList = new ArrayList<>();
                    String itemId = input.next();
                    String date = input.next();
                    String newOwner = input.next();
                    while (input.hasNext()) {
                        commentList.add(input.next());
                    }
                    record.add(new Comments(itemId, date, newOwner, commentList));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occured!");
            e.printStackTrace();
        }
    }

    private void writeComment() {
        try {
            FileWriter myWriter = new FileWriter("comment.txt");
            myWriter.write(record.toString());
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void displayNext() {
        while (User.displayItem(sequence).getBidPrice() == 0) {
            sequence++;
        }
        displayNew();
    }

    private void displayPre() {
        if (sequence == 1) {
            if (User.displayItem(sequence).getBidPrice() == 0) {
                JOptionPane.showMessageDialog(null, "Already moving reach ending", "INFO", JOptionPane.INFORMATION_MESSAGE);
                displayNext();
            }
        } else {
            boolean check = false;
            while (User.displayItem(sequence).getBidPrice() == 0 && !check) {
                sequence--;
                if (sequence == 1) {
                    JOptionPane.showMessageDialog(null, "Already moving reach ending", "INFO", JOptionPane.INFORMATION_MESSAGE);
                    displayNext();
                }
            }
        }
    }

    private void displayTimer() {
        jLabel2.setText("Endng Time : " + User.displayItem(sequence).getEndBidTime());
    }

    private void displayNew() {
        boolean check = false;
        int i;
        for (i = 1; i <= record.getSize(); i++) {
            if (record.getEntry(i).getItemID().equals(User.displayItem(sequence).getItemId())) {
                check = true;
            }
        }
        if (check) {
            jTextArea1.setText(record.getEntry(i - 1).display());
        } else {
            jTextArea1.setText("");
        }
        jTextField1.setForeground(new Color(153, 153, 153));
        pic = new ImageIcon(User.displayItem(sequence).getItemPic()).getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon picture = new ImageIcon(pic);
        displayTimer();
        jTextArea2.setText(User.displayItem(sequence).getItemId() + "\n" + User.displayItem(sequence).getItemName() + "\n" + User.displayItem(sequence).getItemDesc());
        jTextField1.setText("RM " + String.format("%.2f", User.displayItem(sequence).getBidPrice()));
        jLabel1.setIcon(picture);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(java.awt.Color.lightGray);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setText("Previous");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(102, 255, 102));
        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("Proceed");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(102, 255, 102));
        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setText("Next");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Bidding Price :");

        jTextArea2.setEditable(false);
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTextField1.setForeground(java.awt.Color.lightGray);
        jTextField1.setText("RM 0.00");
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 51, 51));
        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton4.setText("BACK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addGap(33, 33, 33)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3)
                        .addComponent(jButton1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jButton2))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        //timeLimit();
        dispose();
        new UserMain(user).setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        // TODO add your handling code here:
        if (jTextField1.getText().equals("RM " + String.format("%.2f", User.displayItem(sequence).getBidPrice()))) {
            jTextField1.setText("");
            jTextField1.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_jTextField1FocusGained

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        // TODO add your handling code here:
        if (jTextField1.getText().equals("")) {
            jTextField1.setText("RM " + String.format("%.2f", User.displayItem(sequence).getBidPrice()));
            jTextField1.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (sequence < User.getItemList().getSize()) {
            sequence++;
            displayNext();
        } else {
            JOptionPane.showMessageDialog(null, "Already moving reach ending", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (sequence > 1) {
            sequence--;
            displayPre();
        } else {
            JOptionPane.showMessageDialog(null, "Already moving reach ending", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        new EnlargeImage(User.displayItem(sequence).getItemPic()).setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try {
            if (!LocalDateTime.now().isBefore(LocalDateTime.parse(User.displayItem(sequence).getEndBidTime(), formatter))) {
                timeLimit();
            } else {
                bidprice = Double.parseDouble(jTextField1.getText());
                LocalDateTime current = LocalDateTime.now();
                String now = current.format(formatter);
                String str = now + " \t " + user.getName() + " have joined this item bid with RM " + String.format("%.2f", bidprice);
                String endBidTime = current.plusMinutes(5).format(formatter);
                if (!user.getId().equals(User.displayItem(sequence).getOwnerID())) {
                    if (bidprice > User.displayItem(sequence).getBidPrice()) {
                        if (bidprice < user.getBalance()) {
                            checkComment(now, str);
                            User.displayItem(sequence).setEndBidTime(endBidTime);
                            User.displayItem(sequence).setBidPrice(bidprice);
                            writeItem();
                            JOptionPane.showMessageDialog(null, "Bid Successfully ! ! !", "INFO", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Insufficient balance\nPlease Top-Up your balance ! ! !", "INFO", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter bidding amount exceed the previous bidding amount ! ! !", "INFO", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You are not allowed to join this item bidding because you are the owner of the item", "INFO", JOptionPane.INFORMATION_MESSAGE);
                }
                jLabel2.setText("Endng Time : " + User.displayItem(sequence).getEndBidTime());
                jTextField1.setForeground(new Color(153, 153, 153));
                jTextField1.setText("RM " + String.format("%.2f", User.displayItem(sequence).getBidPrice()));

            }
        } catch (Exception ex) {
            try {
                bidprice = Double.parseDouble(jTextField1.getText());
                LocalDateTime current = LocalDateTime.now();
                String now = current.format(formatter);
                String str = now + " \t " + user.getName() + " have joined this item bid with RM " + String.format("%.2f", bidprice);
                String endBidTime = current.plusMinutes(5).format(formatter);
                if (!user.getId().equals(User.displayItem(sequence).getOwnerID())) {
                    if (bidprice > User.displayItem(sequence).getBidPrice()) {
                        if (bidprice < user.getBalance()) {
                            checkComment(now, str);
                            User.displayItem(sequence).setEndBidTime(endBidTime);
                            User.displayItem(sequence).setBidPrice(bidprice);
                            writeItem();
                            JOptionPane.showMessageDialog(null, "Bid Successfully ! ! !", "INFO", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Insufficient balance\nPlease Top-Up your balance ! ! !", "INFO", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter bidding amount exceed the previous bidding amount ! ! !", "INFO", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "You are not allowed to join this item bidding because you are the owner of the item", "INFO", JOptionPane.INFORMATION_MESSAGE);
                }
                jLabel2.setText("Endng Time : " + User.displayItem(sequence).getEndBidTime());
                jTextField1.setForeground(new Color(153, 153, 153));
                jTextField1.setText("RM " + String.format("%.2f", User.displayItem(sequence).getBidPrice()));
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(null, "The amounts bidding should consist of numbers only !!!", "INFO", JOptionPane.INFORMATION_MESSAGE);
                jTextField1.setText("RM " + String.format("%.2f", User.displayItem(sequence).getBidPrice()));
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void checkComment(String now, String comment) {
        int i;
        boolean found = false;
        for (i = 1; i <= record.getSize() && !found; i++) {
            if (record.getEntry(i).getItemID().equals(User.displayItem(sequence).getItemId())) {
                found = true;
            }
        }
        if (found) {
            record.getEntry(i - 1).addComment(comment);
            record.getEntry(i - 1).setDate(now);
            record.getEntry(i - 1).setNewUserID(user.getId());
        } else {
            record.add(new Comments(User.displayItem(sequence).getItemId(), now, user.getId()));
            for (i = 1; i <= record.getSize() && !found; i++) {
                if (record.getEntry(i).getItemID().equals(User.displayItem(sequence).getItemId())) {
                    found = true;
                }
            }
            record.getEntry(i - 1).addComment(comment);
        }
        jTextArea1.setText(record.getEntry(i - 1).display());
        writeComment();
    }

    private void timeLimit() {
        int i, j;
        boolean found = false;
        for (i = 1; i <= record.getSize() && !found; i++) {
            if (record.getEntry(i).getItemID().equals(User.displayItem(sequence).getItemId())) {
                found = true;
            }
        }
        if (found) {
            Iterator userLoop = userList.getIterator();
            while (userLoop.hasNext()) {
                User users = (User) userLoop.next();
                if (users.getId().equals(User.displayItem(sequence).getOwnerID())) {
                    users.setBalance(users.getBalance() + User.displayItem(sequence).getBidPrice());//increase original owner balance
                }
            }
            boolean check = false;
            Iterator userLoops = userList.getIterator();
            while (userLoops.hasNext() && !check) {
                itemOwner = (User) userLoops.next();
                if (itemOwner.getId().equals(record.getEntry(i - 1).getNewUserID())) {
                    itemOwner.setBalance(itemOwner.getBalance() - User.displayItem(sequence).getBidPrice());//decrease original owner balance
                    check = true;
                }
            }
            if (check) {
                payList.add(new Transaction(itemOwner, User.displayItem(sequence), record.getEntry(i)));
                writeTran();
                writeUserList();
            }
            User.deleteItemById(record.getEntry(i - 1).getItemID());
            writeItem();
            JOptionPane.showMessageDialog(null, "This item bid already closed.\nThank You.", "INFO", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new UserMain(user).setVisible(true);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ItemViewRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemViewRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemViewRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemViewRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemViewRoom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
