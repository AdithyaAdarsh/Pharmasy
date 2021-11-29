/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author tanzeem
 */
public class Medicines {

    public String getNameM() {
        return nameM;
    }

    public void setNameM(String nameM) {
        this.nameM = nameM;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

   
   

    public Medicines(String nameM, String batch, double qty, String amount) {
        this.nameM = nameM;
        this.batch = batch;
        this.qty = qty;
        this.amount = amount;
      
    }

  

  
String nameM;
String batch;
double qty;
String amount;
String salt;    
}
