package sample;

/**
 * Project: NumericalMethods
 *
 * @author Егор Ивков
 * @since 13.03.2018
 */
public class Model {

    public static final String rungeKutta = "Runge-Kutta method";
    public static final String euler = "Euler's method";
    public static final String eulerImproved = "Euler's improved method";
    public static final String directSolution = "Direct Solution";

    private static double f(double x, double y) {
        return Math.pow(y, 2) / Math.pow(x, 2) - 2;
    }

    public static double[] getYAxes(double[] xAxes, double y0, String method){
        switch (method){
            case Model.euler:
                return Model.getEMYAxes(xAxes, y0);
            case Model.eulerImproved:
                return Model.getIEMYAxes(xAxes, y0);
            case Model.rungeKutta:
                return Model.getRKMYAxes(xAxes, y0);
            default:
                return Model.getDSAxes(xAxes, y0);
        }
    }

    static double[] getDSAxes(double[] xAxes, double y0) {
        double[] yAxes = new double[xAxes.length];
        yAxes[0] = y0;
        double x0 = xAxes[0];
        double c = (y0 - 2*x0)/(Math.pow(x0, 3) * y0 + Math.pow(x0, 4));
        for (int i = 1; i < yAxes.length; i++)
            yAxes[i] = (c*Math.pow(xAxes[i], 4) + 2*xAxes[i]) / (1 - c*Math.pow(xAxes[i], 3));
        return yAxes;
    }

    static double[] getEMYAxes(double[] xAxes, double y0) {
        double[] yAxes = new double[xAxes.length];
        yAxes[0] = y0;
        for (int i = 1; i < yAxes.length; i++)
            yAxes[i] = yAxes[i - 1] + (xAxes[i] - xAxes[i - 1]) * f(xAxes[i - 1], yAxes[i - 1]);
        return yAxes;
    }

    static double[] getIEMYAxes(double[] xAxes, double y0) {
        double[] yAxes = new double[xAxes.length];
        yAxes[0] = y0;
        for (int i = 1; i < yAxes.length; i++) {
            double k1 = f(xAxes[i - 1], yAxes[i - 1]);
            double h = xAxes[i] - xAxes[i - 1];
            double k2 = f(xAxes[i - 1] + h, yAxes[i - 1] + h * k1);
            yAxes[i] = yAxes[i - 1] + h * (k1 + k2) / 2;
        }
        return yAxes;
    }

    static double[] getRKMYAxes(double[] xAxes, double y0) {
        double[] yAxes = new double[xAxes.length];
        yAxes[0] = y0;
        for (int i = 1; i < yAxes.length; i++) {
            double k1 = f(xAxes[i - 1], yAxes[i - 1]);
            double h = xAxes[i] - xAxes[i - 1];
            double k2 = f(xAxes[i - 1] + h / 2, yAxes[i - 1] + h / 2 * k1);
            double k3 = f(xAxes[i - 1] + h / 2, yAxes[i - 1] + h / 2 * k2);
            double k4 = f(xAxes[i - 1] + h, yAxes[i - 1] + h * k3);
            yAxes[i] = yAxes[i - 1] + h * (k1 + 2 * k2 + 2 * k3 + k4) / 6;
        }
        return yAxes;
    }
}
