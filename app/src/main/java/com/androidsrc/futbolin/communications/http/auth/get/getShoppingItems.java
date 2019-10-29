package com.androidsrc.futbolin.communications.http.auth.get;

import com.androidsrc.futbolin.database.models.ShoppingItem;

import java.io.Serializable;
import java.util.List;

public class getShoppingItems implements Serializable {

    List<ShoppingItem> items;

    public getShoppingItems(){

    }

    public List<ShoppingItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "getShoppingItems{" +
                "items=" + items +
                '}';
    }
}
