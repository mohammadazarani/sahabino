package ir.sahabino.rules_evaluator.rules;

import java.util.LinkedList;
import java.util.Optional;

class CircularFifoQueue<E>  extends LinkedList<E>{
    private Integer queueSize;

    public CircularFifoQueue(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Optional<E> put(E e){
        super.add(e);
        if(this.size() == queueSize) {
            E removedElement = this.removeFirst();
            return Optional.ofNullable(removedElement);
        }
        return Optional.empty();
    }
}
