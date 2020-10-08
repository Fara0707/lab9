package lab1;

import java.util.function.DoubleUnaryOperator;


public class Func {

    public double[][] calcFunc(double start, double finish, double h, DoubleUnaryOperator f) {


        var n = calcStep(start, finish, h);
        var y = new double[2][n];

        for (int i = 0; i < n; i++) {
            var x = start + i*h;

            y[0][i] = f.applyAsDouble(x);
            y[1][i] = x;

        }

        return y;
    }

    public int calcStep(double start, double finish, double step){
        return (int)Math.round((finish-start)/step)+1;
    }

}

