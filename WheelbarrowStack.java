import java.util.Stack;
import java.util.Iterator;
public class WheelbarrowStack {
    private Stack<Wheelbarrow> wheelbarrowStack;
    private Iterator<Wheelbarrow> iterator;

    public WheelbarrowStack() {
        wheelbarrowStack = new Stack<>();
    }

    public void push(Wheelbarrow wheel) {
        System.out.println("Wheelbarrow " + wheel.getID() + " pushed to the stack");
        wheelbarrowStack.push(wheel);
    }

    public Wheelbarrow pop() {
        if (!wheelbarrowStack.isEmpty()) {
            System.out.println("Wheelbarrow popped from the stack");
            return wheelbarrowStack.pop();
        } else {
            System.out.println("Stack is empty. Cannot pop a wheelbarrow.");
            return null;
        }
    }

    public Wheelbarrow peek() {
        if (!wheelbarrowStack.isEmpty()) {
            System.out.println("Peeking at the top wheelbarrow.");
            return wheelbarrowStack.peek();
        } else {
            System.out.println("Stack is empty. Cannot peek at a wheelbarrow.");
            return null;
        }
    }

    public Stack<Wheelbarrow> clone() {
        return wheelbarrowStack;
    }

    public boolean isEmpty() {
        return wheelbarrowStack.isEmpty();
    }

    public void remove(Wheelbarrow wheel){
        wheelbarrowStack.remove(wheel);
    }

}

