package Entity;

/**
 *
 * @author Ho Wei Lee
 */

public class Transaction {

    private User newOwner;
    private Item itemBid;
    private Comments record;

    public Transaction(User newOwner, Item itemBid) {
        this.newOwner = newOwner;
        this.itemBid = itemBid;
    }

    public Transaction(User newOwner, Item itemBid, Comments record) {
        this.newOwner = newOwner;
        this.itemBid = itemBid;
        this.record = record;
    }

    public Item getItemBid() {
        return itemBid;
    }

    public void setItemBid(Item itemBid) {
        this.itemBid = itemBid;
    }

    public User getNewOwner() {
        return newOwner;
    }

    public void setNewOwner(User newOwner) {
        this.newOwner = newOwner;
    }

    public Comments getRecord() {
        return record;
    }

    public void setRecord(Comments record) {
        this.record = record;
    }
    
    @Override
    public String toString() {
        return newOwner.getId() + "!" + newOwner.getName() + "!" + newOwner.getPassword() + "!" + newOwner.getBankNo() + "!" + newOwner.getBalance() + "!" + itemBid.getItemId() + "!" + itemBid.getItemPic() + "!" + itemBid.getItemName() + "!" + itemBid.getItemDesc() + "!" + itemBid.getCost() + "!" + itemBid.getBidPrice() + "!" + itemBid.getEndBidTime() + "!" + itemBid.getOwnerID();
    }
}