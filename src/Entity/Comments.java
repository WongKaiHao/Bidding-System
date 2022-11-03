/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.ArrayList;
import ADT.ListInterface;

/**
 *
 * @author Ho Wei Lee
 */
public class Comments {
    private String itemID;
    private String date;
    private String newUserID;
    private ListInterface<String> comment;

    public Comments(String itemID, String date, String newUserID) {
        this.itemID = itemID;
        this.date = date;
        this.newUserID = newUserID;
        this.comment=new ArrayList<>();
    }
    
    public Comments(String itemID, String date, String newUserID, ListInterface<String> comment) {
        this.itemID = itemID;
        this.date = date;
        this.newUserID = newUserID;
        this.comment = comment;
    }
    
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getNewUserID() {
        return newUserID;
    }

    public void setNewUserID(String newUserID) {
        this.newUserID = newUserID;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ListInterface<String> getComment() {
        return comment;
    }

    public void setComment(ListInterface<String> comment) {
        this.comment = comment;
    }
    
    public void addComment(String str){
        comment.add(str);
    }

    public String display(){
        String outputStr="";
        for(int i=1;i<=comment.getSize();i++){
            outputStr+=comment.getEntry(i)+"\n";
        }
        return outputStr;
    }
    
    @Override
    public String toString() {
        String outputStr=itemID+"~"+date+"~"+newUserID;
        for(int i=1;i<=comment.getSize();i++){
            outputStr+="~"+comment.getEntry(i);
        }
        return outputStr;
    }
}
