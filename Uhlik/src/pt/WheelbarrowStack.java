package pt;

import java.util.List;
import java.util.Stack;

/**
 * Trida zasobniku na kolecka
 * @author Adam Mika
 *
 */
public class WheelbarrowStack {
	/**
	 * Zasobnik kolecek
	 */
    private final List<Wheelbarrow> wheelbarrowStack;
    /**
     * Konstruktor zasobniku
     */
    public WheelbarrowStack() {
        wheelbarrowStack = new Stack<Wheelbarrow>();
    }

    /**
     * Pridani kolecka do zasobniku
     * @param wheel vkladane kolecko
     */
    public void push(Wheelbarrow wheel) {
        //System.out.println("Wheelbarrow " + wheel.getID() + " pushed to the stack");
        ((Stack<Wheelbarrow>) wheelbarrowStack).push(wheel);
    }

    /**
     * Odebrani kolecka z vrcholu zasobniku
     * @return vrchni kolecko
     */
    public Wheelbarrow pop() {
        if (!wheelbarrowStack.isEmpty()) {
            //System.out.println("Wheelbarrow popped from the stack");
            return ((Stack<Wheelbarrow>) wheelbarrowStack).pop();
        } else {
           // System.out.println("Stack is empty. Cannot pop a wheelbarrow.");
            return null;
        }
    }

    /**
     * Vraceni vrchniho kolecka bey odstraneni
     * @return vrchni kolecko
     */
    public Wheelbarrow peek() {
        if (!wheelbarrowStack.isEmpty()) {
          // System.out.println("Peeking at the top wheelbarrow.");
            return ((Stack<Wheelbarrow>) wheelbarrowStack).peek();
        } else {
           // System.out.println("Stack is empty. Cannot peek at a wheelbarrow.");
            return null;
        }
    }

    /**
     * Metoda vracejici zasobnik kolecek
     */
    public Stack<Wheelbarrow> clone() {
        return (Stack<Wheelbarrow>) wheelbarrowStack;
    }

    /**
     * Metoda na zjisteni zda je zasobnik prazdny
     * @return
     */
    public boolean isEmpty() {
        return wheelbarrowStack.isEmpty();
    }

    /**
     * Odstraneni kolecka ze zasobniku
     * @param wheel odstranovane kolecko
     */
    public void remove(Wheelbarrow wheel){
        wheelbarrowStack.remove(wheel);
    }

}

