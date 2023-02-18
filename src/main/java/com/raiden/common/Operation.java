package com.raiden.common;

/**
 * @author: JiangJi
 * @Descriotion:
 * @Date:Created in 2023/1/2 15:40
 */
public enum Operation {
    PULS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    };


    private final String symbol;
    
    Operation(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
    
    public abstract double apply(double x, double y);
}
