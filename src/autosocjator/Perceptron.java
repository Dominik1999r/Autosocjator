package autosocjator;

public class Perceptron {

    public static final int MAX = 1600;
    public double [] weigths ;
    public double theta;
    public double error;
    public double constLearning;
    public int lifeTime;
    public int max;
    public double[]  pocket = new double[35];

    public Perceptron(double[] weigths, double constLearning, double theta)
    {
        this.weigths = weigths;
        this.theta = theta;
        this.constLearning=constLearning;
        pocket = weigths;
        lifeTime = 0;
        max = 0;
    }

    public int getResult(int [] v)
    {
        int result = 0;
        double tmp =0.0;

        for(int i = 0; i < MAX; i++)
        {
            tmp += v[i]*pocket[i];
        }

        tmp -= theta;

        if (tmp>0) result = 1;
        else result = -1;

        return result;
    }

    public void learnPerceptron(int []example , int result)
    {
        int o = 0;
        double tmp = 0;

        for (int i = 0; i < MAX; i++)
        {
            tmp = tmp + (example[i]*weigths[i]);
        }

        tmp -= theta;

        if(tmp > 0) o = 1;
        else o = -1;

        error = result - o;

        if (error !=0)
        {
            lifeTime = 0;

            for(int i = 0; i < MAX; i++)
            {
                double w =  weigths[i] + constLearning * error * example[i];
                weigths[i] = w;
            }

            theta = theta - (constLearning * error);
        } else {

            lifeTime++;

            if (lifeTime > max)
            {
                for (int i = 0; i < MAX; i++)
                {
                    pocket[i] = weigths[i];
                    max = lifeTime;
                }
            }
        }
    }
}
