/**
 * 
 */
package tp.pr5;

import java.util.Iterator;

import tp.pr5.items.CodeCard;
import tp.pr5.items.Fuel;
import tp.pr5.items.Garbage;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

/**
 * 
 * @author Abel Serrano Juste
 */
public class Shop {
	
	ItemContainer stock;

	public Shop(){
		stock = new ItemContainer();
		//loadDefaultStock();
	}
	
	public void loadDefaultStock() {
		CodeCard card = new CodeCard ("beatiful-key","esto abre todo","1");
		card.setPrice(50);
		stock.addItem(card);
		
		Garbage garbage = new Garbage("newspapers","news on sports",5);
		garbage.setPrice(10);
		stock.addItem(garbage);
		
		Fuel grapes = new Fuel ("grapes","celebrations of the new year",1,1);
		grapes.setPrice(20);
		stock.addItem(grapes);
		
		Fuel coal  = new Fuel("Coal","Be careful with this fuel because it is extremely contaminant",-80,1);
		coal.setPrice(10);
		stock.addItem(coal);
	}

	public boolean addItemToStock(Item it){
		return stock.addItem(it);
	}
	
	public Item lookForItemInStock(String itemId){
		return stock.getItem(itemId);
	}
	
	public boolean removeItemOfStock(String itemId){
		
		return stock.pickItem(itemId)==null? false : true;
		
	}
	
	@Override
	public String toString(){
		StringBuilder output = new StringBuilder("The shop contains the following items:"+Interpreter.LINE_SEPARATOR);
		Iterator<Item> iterator  =  stock.iterator();
		Item it;
		while (iterator.hasNext()){
			output.append("--item ");
			it = iterator.next();
			output.append("("+it.getPrice()+"$): "+it.toString());
			output.append(Interpreter.LINE_SEPARATOR);
		}
		return output.toString();
	}
	
	
}
