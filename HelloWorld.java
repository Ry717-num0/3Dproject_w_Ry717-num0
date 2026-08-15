import org.lwjgl.opengl.*;


import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.system.MemoryUtil.*;


public class HelloWorld {
    public long window;
    double pi = 3.14159265358979323846264338327f;



    double[] rotate(double[] vertices, double angle, double xposition, double yposition) {
        while(angle>=360){
            angle=angle-360;
        }
        while(angle<0){
            angle=angle+360;
        }

        for (int i = 0; i < 9; i = i + 3) {
            // not working
            double newanglex = inversecos(absd(absd(vertices[i])-absd(xposition))/sqrt(sqr(absd(absd(vertices[i])-absd(xposition)))+sqr(absd(absd(vertices[i+1])-absd(yposition))), 25));
            double totalanglex = newanglex+angle;
            while(totalanglex>=360){
                totalanglex=totalanglex-360;
            }
            while(totalanglex<0){
                totalanglex=totalanglex-360;
            }

            System.out.println(totalanglex);
            System.out.println(cos(totalanglex));
            System.out.println(sin(totalanglex));
            // working as intended after this point
            vertices[i] = sqrt(sqr(absd(absd(vertices[i])-absd(xposition)))+sqr(absd(absd(vertices[i+1])-absd(yposition))), 25)*cos(totalanglex);
            vertices[i+1] = sqrt(sqr(absd(absd(vertices[i])-absd(xposition)))+sqr(absd(absd(vertices[i+1])-absd(yposition))), 25)*sin(totalanglex);

            System.out.println(vertices[i]);
            System.out.println(vertices[i+1]);
            System.out.println();



        }
        return vertices;
    }
    public void run(){
        int resolutionx = 300;
        int resolutiony = 300;
        init(resolutionx, resolutiony, "8/15/2026 AD");
        double[] vertinint = {0, 0, 0, 0, 300, 0, 300, 0, 0};
        double[] newvert = {0, 0, 0, 0, 30, 0, 300, 0, 0};
        int[] rgbbg = {100, 100, 100};
        double[] newverts = {300, 0, 0, 0, 30, 0, 300, 30, 0};
        int[] rgbbgs = {100, 100, 100};
        int[] rgbstate = {0, 250, 205};
        double xm = 0;
        double yud = 0;
        double xrl = 0;

        double jumpnumber = 0;
        double jumpamount = 0;
        boolean isjumping = false;
        boolean isjumpingup = false;
        while( glfwGetKey(window, GLFW_KEY_ESCAPE ) != GLFW_PRESS && !glfwWindowShouldClose(window)) {



            GL.createCapabilities();

            wait(100);


            glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT);



            if(glfwGetKey(window, GLFW_KEY_LEFT)==1){
                xrl=5;
            }
            if(glfwGetKey(window, GLFW_KEY_RIGHT)==1){
                xrl=-5;
            }
            if(glfwGetKey(window, GLFW_KEY_UP)==1 && glfwGetKey(window, GLFW_KEY_UP)==0 & !isjumpingup){
                isjumping=true;
                yud=10;

            }
            if(isjumping){
                isjumpingup=true;

                yud=yud-1;
                jumpamount=jumpamount+yud;
                if(jumpamount==0){
                    isjumpingup=false;
                    yud=0;
                    jumpamount=0;
                    jumpnumber=0;
                    isjumping=false;
                }
                System.out.println(jumpamount);
                System.out.println(jumpnumber);
                System.out.println(yud);
            }



            translation(xrl, yud, 0, vertinint);


            // render

            Trianglecreatefull(resolutionx, resolutiony, vertinint, rgbstate);
            Trianglecreatefull(resolutionx, resolutiony, newvert, rgbbg);
            Trianglecreatefull(resolutionx, resolutiony, newverts, rgbbgs);

            xrl=0;


            // Swap buffers
            glfwSwapBuffers(window);

            glfwPollEvents();


        }
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    private void init(int resx, int resy, String Title) {

        if (!glfwInit()) {
            System.out.println("Failed to initialize GLFW\n");
        }


        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); // To make macOS happy; should not be needed
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);


        // Open a window and create its OpenGL context
        window = glfwCreateWindow( resx, resy, Title, NULL, NULL);
        if( window == NULL ){
            System.out.println("Failed to open GLFW window. If you have an Intel GPU, they are not 3.3 compatible. Try the 2.1 version of the tutorials.\n" );


            glfwTerminate();


        }
        glfwMakeContextCurrent(window);


        // Ensure we can capture the escape key being pressed below
        glfwSetInputMode(window, GLFW_STICKY_KEYS, GL_TRUE);
    }
    public void trianglemaker(float rval, float gval, float bval, float[] vertices){
        GL.createCapabilities();
        // Dark blue background



        int VertexArrayID = glGenVertexArrays();
        glBindVertexArray(VertexArrayID);

        String rtrue = Float.toString(rval);
        String gtrue = Float.toString(gval);
        String btrue = Float.toString(bval);

        // Create and compile our GLSL program from the shaders
        int programID = LoadShaders(rtrue, gtrue, btrue);


        // Get a handle for our "MVP" uniform


        // Our vertices. Tree consecutive floats give a 3D vertex; Three consecutive vertices give a triangle.
        // A cube has 6 faces with 2 triangles each, so this makes 6*2=12 triangles, and 12*3 vertices


        int vertexbuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexbuffer);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);











        // Check if the ESC key was pressed or the window was closed

            // Clear the screen



            // Use our shader
            glUseProgram(programID);


            // 1rst attribute buffer : vertices
            glEnableVertexAttribArray(0);
            glBindBuffer(GL_ARRAY_BUFFER, vertexbuffer);
            glVertexAttribPointer(
                    0,                  // attribute. No particular reason for 0, but must match the layout in the shader.
                    3,                  // size
                    GL_FLOAT,           // type
                    false,           // normalized?
                    0,                  // stride
                    0            // array buffer offset
            );


            // 2nd attribute buffer : colors



            // Draw the triangle !
            glDrawArrays(GL_TRIANGLES, 0, 3); // 12*3 indices starting at 0 -> 12 triangles






        // Cleanup VBO and shader
        glDisableVertexAttribArray(0);
        glDeleteBuffers(vertexbuffer);

        glDeleteProgram(programID);
        glDeleteVertexArrays(VertexArrayID);


        // Close OpenGL window and terminate GLFW



    }
    float[] convert(double[] make, int resolutionx, int resolutiony){
        float[] vert  = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        vert[0] = ((float) (make[0] * 2) /resolutionx)-1;
        vert[3] = ((float) (make[3] * 2) /resolutionx)-1;
        vert[6] = ((float) (make[6] * 2) /resolutionx)-1;
        vert[1] = ((float) (make[1] * 2) /resolutiony)-1;
        vert[4] = ((float) (make[4] * 2) /resolutiony)-1;
        vert[7] = ((float) (make[7] * 2) /resolutiony)-1;
        return vert;
    }
    void Trianglecreatefull(int resolutionx, int resolutiony, double[] vertices, int[] rgb){
        float[] vert  = convert(vertices, resolutionx, resolutiony);
        float[] rgbfloat = {0, 0, 0};
        rgbfloat[0] = (float) rgb[0]/255;
        rgbfloat[1] = (float) rgb[1]/255;
        rgbfloat[2] = (float) rgb[2]/255;
        trianglemaker(rgbfloat[0], rgbfloat[1], rgbfloat[2], vert);
    }// Check if the ESC key was pressed or the window was closed




    int LoadShaders(String redvalue, String greenvalue, String bluevalue){


        // Create the shaders
        GL.createCapabilities();
        // Read the Vertex Shader code from the file
        String VertexShaderCode = """
                #version 140
                
                #extension GL_ARB_explicit_attrib_location : enable
                
                #extension GL_ARB_separate_shader_objects : enable
                
                layout(location = 0) in vec3 vertexPosition_modelspace;
                
                void main(){
                      gl_Position.xyz = vertexPosition_modelspace;
                      gl_Position.w = 1.0;
                    }
                """
                ;




        // Read the Fragment Shader code from the file
        String FragmentShaderCode = "#version 140\n" +
                "out vec3 color;\n" +
                "void main(){\n" +
                "  color = vec3(" + redvalue + ", " + greenvalue + ", " + bluevalue + ");\n" +
                "}";




        // Compile Vertex Shader
        int VertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(VertexShaderID, VertexShaderCode);
        glCompileShader(VertexShaderID);


        // Compile Fragment Shader
        int FragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(FragmentShaderID, FragmentShaderCode);
        glCompileShader(FragmentShaderID);


        int statusvert = glGetShaderi(VertexShaderID, GL_COMPILE_STATUS);
        if (statusvert != GL_TRUE) {
            System.out.println("error in compilatoin vertex shader\n");
        }

        System.out.println(glGetShaderInfoLog(VertexShaderID));

        int statusfrag = glGetShaderi(FragmentShaderID, GL_COMPILE_STATUS);
        if (statusfrag != GL_TRUE) {
            System.out.println("error in compilatoin fragment shader\n");
        }

        System.out.println(glGetShaderInfoLog(FragmentShaderID));






        // Link the program
        int ProgramID = glCreateProgram();
        glAttachShader(ProgramID, VertexShaderID);
        glAttachShader(ProgramID, FragmentShaderID);

        glLinkProgram(ProgramID);


        int status = glGetProgrami(ProgramID, GL_LINK_STATUS);
        if (status != GL_TRUE) {
            System.out.println("Error in linking");
        }

        System.out.println(glGetProgramInfoLog(ProgramID));












        glDetachShader(ProgramID, VertexShaderID);
        glDetachShader(ProgramID, FragmentShaderID);


        glDeleteShader(VertexShaderID);
        glDeleteShader(FragmentShaderID);


        return ProgramID;
    }


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
            double parabolasin = (1/2.4649)*(-1 * x * x + pi * x);
            return parabolasin;
        } else if (x > pi) {
            double parabolasinoffset = (1/2.4649)*((x-pi) * (x-pi) - (pi*(x-pi)));
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
            double parabolasin = (1/2.4649)*(-1 * (x+(0.5*pi)) * (x+(0.5*pi)) + (pi * (x+(0.5*pi))));
            return parabolasin;
        } else if (x > (0.5*pi)) {
            if(x<1.5*pi){
                double parabolasinoffset = (1/2.4649)*((x-(0.5*pi)) * (x-(0.5*pi)) - (pi * (x-(0.5*pi))));
                return parabolasinoffset;
            }
            else if(x>1.5*pi){
                double parabolasin = (1/2.4649)*(-1 * (x-(1.5*pi)) * (x-(1.5*pi)) + (pi * (x-(1.5*pi))));
                return parabolasin;
            }
            else{
                return 0;
            }

        } else {
            return 0;
        }
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


    double inversesin(double y) {
        double accuracy = 0;
            for(double i = 0; i<=360; i++){
                if(sin(i)>=y){
                    accuracy=i;
                    break;
                }
            }

        return accuracy;
    }
    double inversecos(double y) {
        double accuracy = 0;

            for(double i = 0; i<=360; i++){
                if(cos(i)<=y){
                    accuracy=i;
                    break;
                }
            }

        return accuracy;
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

    void translation(double offsetx, double offsety, int offsetz, double[] vertices) {
            for(int i = 0; i<9; i=i+3){
                vertices[i] = vertices[i]+offsetx;
                vertices[i+1] = vertices[i+1]+offsety;
                vertices[i+2] = vertices[i+2]+offsetz;
            }
    }

    // 0 is x and 1 is y
    double[] reflection(boolean rotaxis, int position, double[] vertices) {
        if (!rotaxis) {
            for (int sma = 0; sma <= 6; sma = sma + 3) {
                double m = absd(vertices[sma] - position);
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
                double n = absd(vertices[smass] - position);
                if (vertices[smass] < position) {
                    vertices[smass] = vertices[smass] + (2 * n);
                }
                if (vertices[smass] > position) {
                    vertices[smass] = vertices[smass] - (2 * n);
                }
            }
        }
        return vertices;
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

    void sleep(double time){
        glfwSetTime(0);
        while(!(glfwGetTime()==time)){

        }
    }

    public static void main(String[] args){
        new HelloWorld().run();
    }
}




