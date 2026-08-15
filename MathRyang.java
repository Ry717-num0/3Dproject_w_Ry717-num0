public class MathRyang {
    double pi = 3.14159265358979323846264338327f;

    double sqrt(double num, int accurateness) {
        // not my own
        double y = 1;
        int im = 0;
        while (im < accurateness) {
            y = 0.5 * (y + num / y);
            im++;
        }

        return y;
    }

    double sin(double y) {
        // sin calculated by creating a parabola for the first and second parabola of the way to pi and 2*pi as sin graphed
        // sin is also calculated using 1/2.4674*(-x^2+3.14159265358979323846264x) 0.05 dissonance
        double x = (y / 360) * 2 * pi;

        while(x<0){
            x=x+(2*pi);
        }
        while(x>(2*pi)){
            x=x-(2*pi);
        }
        if (x < pi) {
            double parabolasin = -1 * 0.40528473456480771595285326135834 * x * x + 1.2732395447208892688936454647003 * x;
            return parabolasin;
        } else if (x > pi) {
            double parabolasinoffset = 1 * 0.40528473456480771595285326135834 * (x-pi) * (x-pi) - 1.2732395447208892688936454647003 * (x-pi);
            return parabolasinoffset;
        } else {
            return 0;
        }


    }

    double cos(double y) {
        // let 90 be 0, 180 be -1, 0.5 being close to 1
        double x = (y/ 360) * 2 * pi;
        while(x<0){
            x=x+(2*pi);
        }
        while(x>(2*pi)){
            x=x-(2*pi);
        }
        if (x < (0.5*pi)) {
            double parabolasin = -1 * 0.40528473456480771595285326135834 * (x+(0.5*pi)) * (x+(0.5*pi)) + 1.2732395447208892688936454647003 * (x+(0.5*pi));
            return parabolasin;
        } else if (x > (0.5*pi)) {
            if(x<1.5*pi){
                double parabolasinoffset = 1 * 0.40528473456480771595285326135834 * (x-(0.5*pi)) * (x-(0.5*pi)) - 1.2732395447208892688936454647003 * (x-(0.5*pi));
                return parabolasinoffset;
            }
            else if(x>1.5*pi){
                double parabolasin = -1 * 0.40528473456480771595285326135834 * (x-(1.5*pi)) * (x-(1.5*pi)) + 1.2732395447208892688936454647003 * (x-(1.5*pi));
                return parabolasin;
            }
            else{
                return 0;
            }

        } else {
            return 0;
        }
    }
    double inversesin(double y) {
        double accuracy = 0;
        for(double i = 0; i<=90; i++){
            if(sin(i)>=y){
                accuracy=i;
                break;
            }
        }

        return accuracy;
    }
    double inversecos(double y) {
        double accuracy = 0;

        for(double i = 0; i<=90; i++){
            if(cos(i)<=y){
                accuracy=i;
                break;
            }
        }

        return accuracy;
    }

    void run() {
        double[] vertices = {40, 20, 0, 30, 20, 0, 30, 40, 0};
    }
    double[] rotate(double[] vertices, int angle, int xposition, int yposition) {
        while(angle>360){
            angle=angle-360;
        }
        while(angle<360){
            angle=angle+360;
        }
        int quadrant = 0;
        if(vertices[0]>=xposition){
            if(vertices[1]>=yposition){
                quadrant=0;
            }
            if(vertices[1]<yposition){
                quadrant=3;
            }
        }
        if(vertices[0]<xposition){
            if(vertices[1]>=yposition){
                quadrant=1;
            }
            if(vertices[1]<yposition){
                quadrant=2;
            }
        }
        for (int i = 0; i <= 8; i = i + 3) {
            double newanglex = inversecos(sqr(absd(vertices[i]-xposition))/sqrt(sqr(absd(vertices[i]-xposition))+sqr(absd(vertices[i+1]-yposition)), 10))+quadrant*90;
            double totalanglex = newanglex+angle;
            while(totalanglex>=360) {
                totalanglex = totalanglex - 360;
            }

            vertices[i] = sqrt(sqr(absd(vertices[i]-xposition))+sqr(absd(vertices[i+1]-yposition)), 100)*cos(totalanglex);
            vertices[i+1] = sqrt(sqr(absd(vertices[i]-xposition))+sqr(absd(vertices[i+1]-yposition)), 100)*sin(totalanglex);


        }

        return vertices;
    }
    double[] distancefrompoint(double[] vertices, double[] vertices2){
        double[] distance = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        distance[0] = absd(vertices[0]-vertices2[0]);
        distance[1] = absd(vertices[1]-vertices2[1]);
        distance[2] = absd(vertices[2]-vertices2[2]);
        distance[3] = absd(vertices[3]-vertices2[0]);
        distance[4] = absd(vertices[4]-vertices2[1]);
        distance[5] = absd(vertices[5]-vertices2[2]);
        distance[6] = absd(vertices[6]-vertices2[0]);
        distance[7] = absd(vertices[7]-vertices2[1]);
        distance[8] = absd(vertices[8]-vertices2[2]);

        return distance;

    }
    void interpolation(double[] vertices, int anglexz, int anglexy, int angleyz) {
        int quadrant = 1;
        for (int i = 0; i <= 8; i = i + 3) {
            System.out.println(vertices[i]);
            System.out.println(vertices[i + 1]);
            System.out.println(vertices[i + 2]);
        }
        double[] center = {0, 0, 0};


        double[] difference = distancefrompoint(vertices, center);
        double[] angle = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i <= 8; i = i + 3){
            if(vertices[0]>=0){
                if(vertices[1]>=0){
                    quadrant=1;
                }
                if(vertices[1]<0){
                    quadrant=2;
                }
            }
            if(vertices[0]<0){
                if(vertices[1]>=0){
                    quadrant=4;
                }
                if(vertices[1]<0){
                    quadrant=3;
                }
            }
            angle[i] = inversecos(sqr(difference[i])/sqrt(sqr(difference[i])+sqr(difference[i+1]), 10))+(quadrant*90);
            angle[i+1] = inversesin(sqr(difference[i+1])/sqrt(sqr(difference[i])+sqr(difference[i+1]), 10))+(quadrant*90);
            vertices[i] = sqrt(sqr(difference[i])+sqr(difference[i+1]), 10)*cos(anglexy+angle[i]);
            vertices[i+1] = sqrt(sqr(difference[i])+sqr(difference[i+1]), 10)*sin(anglexy+angle[i+1]);
        }

        for (int i = 0; i <= 8; i = i + 3) {
            vertices[i + 2] = 0;
        }
        for (int i = 0; i <= 8; i = i + 3) {
            System.out.println(vertices[i]);
            System.out.println(vertices[i + 1]);
            System.out.println(vertices[i + 2]);
        }


    }
    double[] center(double[] vertices){
        double[] center = {0, 0, 0};
        double ymax = vertices[1];
        double ymin = vertices[1];
        for (int i = 0; i <= 8; i = i + 3) {
            if (ymax <= vertices[i + 1]) {
                ymax = vertices[i + 1];
            } else {
                ymax = ymax;
            }
        }
        for (int i = 0; i <= 8; i = i + 3) {
            if (ymin >= vertices[i + 1]) {
                ymin = vertices[i + 1];
            } else {
                ymin = ymin;
            }
        }
        float distancy = (float) (ymax - (0.5 * (ymax - ymin)));
        double xmax = vertices[0];
        double xmin = vertices[0];
        for (int i = 0; i <= 8; i = i + 3) {
            if (xmax <= vertices[i]) {
                xmax = vertices[i];
            } else {
                xmax = xmax;
            }
        }
        for (int i = 0; i <= 8; i = i + 3) {
            if (xmin >= vertices[i]) {
                xmin = vertices[i];
            } else {
                xmin = xmin;
            }
        }
        float distancx = (float) (xmax - (0.5 * (xmax - xmin)));
        double zmax = vertices[2];
        double zmin = vertices[2];
        for (int i = 0; i <= 8; i = i + 3) {
            if (zmax <= vertices[i + 2]) {
                zmax = vertices[i + 2];
            } else {
                zmax = zmax;
            }
        }
        for (int i = 0; i <= 8; i = i + 3) {
            if (zmin >= vertices[i + 2]) {
                zmin = vertices[i + 2];
            } else {
                zmin = zmin;
            }
        }
        float distancz = (float) (zmax - (0.5 * (zmax - zmin)));
        center[0] = distancx;
        center[1] = distancy;
        center[2] = distancz;
        return center;
    }

    int abs(int x) {
        if (x < 0) {
            x = -1 * x;
        } else if (x == 0) {
            x = 0;
        } else {
            x = x;
        }
        return x;
    }

    float absf(float x) {
        if (x < 0) {
            x = -1 * x;
        } else if (x == 0) {
            x = 0;
        } else {
            x = x;
        }
        return x;
    }

    double absd(double x) {
        if (x < 0) {
            x = -1 * x;
        } else if (x == 0) {
            x = 0;
        } else {
            x = x;
        }
        return x;
    }



    void translation(int offsetx, int offsety, int[] vertices) {
        vertices[0] = vertices[0] + offsetx;
        vertices[1] = vertices[1] + offsety;
        vertices[3] = vertices[3] + offsetx;
        vertices[4] = vertices[4] + offsety;
        vertices[6] = vertices[6] + offsetx;
        vertices[7] = vertices[7] + offsety;
    }

    // 0 is x and 1 is y
    void reflection(boolean rotaxis, int position, int[] vertices) {
        if (!rotaxis) {
            for (int sma = 0; sma <= 6; sma = sma + 3) {
                int m = abs(vertices[sma] - position);
                if (vertices[sma] < position) {
                    vertices[sma] = vertices[sma] + (2 * m);
                }
                if (vertices[sma] > position) {
                    vertices[sma] = vertices[sma] - (2 * m);
                }
            }
        }
        if (rotaxis) {
            for (int smass = 1; smass <= 7; smass = smass + 3) {
                int n = abs(vertices[smass] - position);
                if (vertices[smass] < position) {
                    vertices[smass] = vertices[smass] + (2 * n);
                }
                if (vertices[smass] > position) {
                    vertices[smass] = vertices[smass] - (2 * n);
                }
            }
        }
    }

    void stretch(int level, double[] vertices){
        double xmax = vertices[0];
        double xmin = vertices[0];
        for (int i = 0; i <= 8; i = i + 3) {
            if (xmax <= vertices[i]) {
                xmax = vertices[i];
            } else {
                xmax = xmax;
            }
        }
        for (int i = 0; i <= 8; i = i + 3) {
            if (xmin >= vertices[i]) {
                xmin = vertices[i];
            } else {
                xmin = xmin;
            }
        }
        float distancx = (float) (0.5 * (xmax - xmin));
        vertices[0] = (absd(vertices[0] - distancx));
        vertices[3] = (absd(vertices[3] - distancx));
        vertices[6] = (absd(vertices[6] - distancx));
        for(int i=0; i<=8; i=i+3){
            double x = absd(vertices[i]-distancx);
            double m = x/90;
            vertices[i] = absd(vertices[i]-m*level);
        }


    }
    void squeeze(int level, double[] vertices) {
        double ymax = vertices[1];
        double ymin = vertices[1];
        for (int i = 0; i <= 8; i = i + 3) {
            if (ymax <= vertices[i + 1]) {
                ymax = vertices[i + 1];
            } else {
                ymax = ymax;
            }
        }
        for (int i = 0; i <= 8; i = i + 3) {
            if (ymin >= vertices[i + 1]) {
                ymin = vertices[i + 1];
            } else {
                ymin = ymin;
            }
        }
        float distancy = (float) (0.5 * (ymax - ymin));
        for(int i=0; i<=8; i=i+3){
            double x = absd(vertices[i+1]-distancy);
            double m = x/90;
            vertices[i+1] = absd(vertices[i+1]-m*level);
        }
    }

    double sqr(double x) {
        x = x * x;
        return x;
    }

    int sqr(int x) {
        x = x * x;
        return x;
    }

    float sqr(float x) {
        x = x * x;
        return x;
    }



    public static void main(String[] args) {

        new MathRyang().run();
    }


}


