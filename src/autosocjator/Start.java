package autosocjator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class Start extends JFrame{

    public static final int MAX = 1600;
    private JPanel rightPanel = new JPanel();
    private Panel leftWindow = new Panel();
    private Panel rightWindow = new Panel();
    private JPanel picturePanel = new JPanel();
    private JButton house = new JButton("Dom");
    private JButton cube = new JButton("Szescian");
    private JButton sun = new JButton("Slonce");
    private JButton hand = new JButton("Reka");
    private JButton heart = new JButton("Serce");
    private JButton tv = new JButton("Telewizor");
    private JButton clock = new JButton("Zegar");
    private JButton lollipop = new JButton("Lizak");
    private JButton robot = new JButton("Robot");
    private JButton tree = new JButton("Drzewo");
    private JButton[] pixelLeftWindow = new JButton[MAX];
    private JButton[] pixelRightWindow = new JButton[MAX];
    private int[] pixelColor = new int[MAX];
    private JButton clear = new JButton("Wyczysc");
    private JButton save = new JButton("Zapisz");
    private JButton reduceNoises = new JButton("Oczysc szumy");
    private JButton autoNoises = new JButton("Auto szum");
    private Perceptron[] perceptron;
    private Random rand = new Random();
    private boolean draw = false;
    private int number = 0;

    public static void main(String[] args) throws FileNotFoundException
    {
        Perceptron[] perceptron = new Perceptron[MAX];

        Start start = new Start(perceptron);
        start.randomWeightsAndTheta();
        start.teachPerceptron();
    }

    public Start(Perceptron[] perceptron)
    {
        this.perceptron = perceptron;
        setPreferredSize(new Dimension(1235,600));
        for(int i = 0; i < pixelLeftWindow.length; i++)
        {
            final JButton leftButton = new JButton();
            final JButton rightButton = new JButton();
            leftButton.setBorder(new LineBorder(Color.WHITE, 0));
            rightButton.setBorder(new LineBorder(Color.WHITE, 0));

            leftButton.addMouseListener(new MouseListener()
            {

                @Override
                public void mouseClicked(MouseEvent event) {}

                @Override
                public void mouseEntered(MouseEvent event)
                {
                    if(draw)
                    {
                        if(SwingUtilities.isLeftMouseButton(event)) leftButton.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) leftButton.setBackground(Color.WHITE);

                        if(SwingUtilities.isLeftMouseButton(event)) rightButton.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) rightButton.setBackground(Color.WHITE);
                    }
                }

                @Override
                public void mouseExited(MouseEvent event)
                {
                    if(draw)
                    {
                        if(SwingUtilities.isLeftMouseButton(event)) leftButton.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) leftButton.setBackground(Color.WHITE);

                        if(SwingUtilities.isLeftMouseButton(event)) rightButton.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) rightButton.setBackground(Color.WHITE);
                    }
                }

                @Override
                public void mousePressed(MouseEvent event)
                {
                    draw = true;

                    if(draw)
                    {
                        if(SwingUtilities.isLeftMouseButton(event)) leftButton.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) leftButton.setBackground(Color.WHITE);

                        if(SwingUtilities.isLeftMouseButton(event)) rightButton.setBackground(Color.BLACK);
                        if(SwingUtilities.isRightMouseButton(event)) rightButton.setBackground(Color.WHITE);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent event)
                {
                    draw = false;
                    changeColor();
                }

            });

            pixelLeftWindow[i] = leftButton;
            pixelLeftWindow[i].setPreferredSize(new Dimension(10,10));
            pixelLeftWindow[i].setBackground(Color.WHITE);
            leftWindow.add(pixelLeftWindow[i]);
            pixelRightWindow[i] = rightButton;
            pixelRightWindow[i].setPreferredSize(new Dimension(10,10));
            pixelRightWindow[i].setBackground(Color.WHITE);
            rightWindow.add(pixelRightWindow[i]);
        }

        listenersToPictures();
        listenersToMenu();

        rightPanel();
        picturePanel();

        leftWindow.setLayout(new GridLayout(40,40));
        rightWindow.setLayout(new GridLayout(40,40));

        setMainPanel();

    }

    public void setMainPanel()
    {
        setLayout(new BorderLayout());
        leftWindow.setPreferredSize(new Dimension(500,400));
        rightWindow.setPreferredSize(new Dimension(500,400));
        rightPanel.setPreferredSize(new Dimension(200,600));
        add(rightPanel,BorderLayout.EAST);
        add(leftWindow, BorderLayout.WEST);
        add(rightWindow, BorderLayout.CENTER);
        add(picturePanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void rightPanel()
    {
        rightPanel.setBorder(BorderFactory.createTitledBorder("Panel"));
        rightPanel.setLayout(new GridLayout(7,1));
        rightPanel.add(reduceNoises);
        rightPanel.add(clear);
        rightPanel.add(save);
        rightPanel.add(autoNoises);
    }

    public void picturePanel()
    {
        picturePanel.setBorder(BorderFactory.createTitledBorder("Obrazki"));
        picturePanel.add(house);
        picturePanel.add(cube);
        picturePanel.add(sun);
        picturePanel.add(hand);
        picturePanel.add(heart);
        picturePanel.add(tv);
        picturePanel.add(clock);
        picturePanel.add(lollipop);
        picturePanel.add(robot);
        picturePanel.add(tree);
    }

    public void clear()
    {
        for(int i = 0; i < MAX; i++)
        {
            pixelLeftWindow[i].setBackground(Color.WHITE);
            pixelRightWindow[i].setBackground(Color.WHITE);
        }
    }

    public void drawPicture(int nr_pic)
    {
        File file = new File("examples.txt");
        try
        {
            Scanner in = new Scanner(file);
            while(in.hasNextLine())
            {
                String line = in.nextLine();
                String[] vector = line.split(" ");

                for(int i = 0 ; i < MAX; i++)
                {
                    if(vector[i].equals("1"))
                    {
                        pixelLeftWindow[i].setBackground(Color.BLACK);
                        pixelRightWindow[i].setBackground(Color.BLACK);

                    } else {
                        pixelLeftWindow[i].setBackground(Color.WHITE);
                        pixelRightWindow[i].setBackground(Color.WHITE);
                    }

                    pixelColor[i] = Integer.parseInt(vector[i]);
                }

                if(nr_pic == number)
                {
                    number = 0;
                    break;
                }
                number++;
            }

            in.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void changeColor()
    {
        for(int i = 0; i < MAX; i++)
        {
            if(pixelLeftWindow[i].getBackground() == Color.BLACK) pixelColor[i] = 1;
            else pixelColor[i] = -1;
        }
    }

    public void addPicture()
    {
        try
        {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("examples.txt", true)));
            String save = "";

            for(int i = 0; i < MAX; i++)
            {
                save += pixelColor[i] + " ";
            }

            out.println(save);
            System.out.println("Dodano!");
            out.close();

        } catch (IOException e) {}
    }

    public void reduceNoises()
    {
        for(int i = 0; i < MAX; i++)
        {
            int val =  perceptron[i].getResult(pixelColor);

            if(val == 1) pixelRightWindow[i].setBackground(Color.BLACK);
            else pixelRightWindow[i].setBackground(Color.WHITE);

            pixelColor[i] = val;
        }
    }

    public void randomWeightsAndTheta()
    {
        double constLearn = 0.01;

        for(int i = 0; i < MAX; i++)
        {
            double[] weights = new double[MAX];

            for(int j = 0; j < MAX; j++)
            {
                weights[j] = rand.nextDouble()/1000;
            }

            double theta = rand.nextDouble()/1000;
            perceptron[i] = new Perceptron(weights, constLearn, theta);
        }
    }

    public void teachPerceptron() throws FileNotFoundException
    {
        int[][] list = readExamples("examples.txt");
        int randomPic = 0;
        int result = 0;
        int[] picture = new int[MAX];

        for(int k = 0; k < MAX; k++)
        {
            for(int j = 0; j < 1000; j++)
            {
                randomPic = rand.nextInt(10);
                result = list[randomPic][k];
                picture = list[randomPic];
                perceptron[k].learnPerceptron(picture, result);
            }
        }

        System.out.println("Gotowy");
    }

    public int[][] readExamples(String name)
    {
        int i = 0, k = 0;
        File file = new File(name);

        try
        {
            Scanner in = new Scanner(file);
            while(in.hasNextLine())
            {
                String line = in.nextLine();
                i++;

            }
            in.close();

            Scanner in2 = new Scanner(file);
            int[][] picture = new int[i][MAX];
            while(in2.hasNextLine())
            {
                String line = in2.nextLine();
                String[] s = line.split(" ");

                for(int j = 0; j < MAX; j++)
                {
                    picture[k][j] = Integer.parseInt(s[j]);
                }

                k++;
            }
            in2.close();

            return picture;

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public void listenersToPictures()
    {
        house.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(0);
            }
        });

        cube.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(1);
            }
        });

        sun.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(2);
            }
        });

        hand.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(3);
            }
        });

        heart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(4);
            }
        });

        tv.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(5);
            }
        });

        clock.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(6);
            }
        });

        lollipop.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(7);
            }
        });

        robot.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(8);
            }
        });

        tree.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                drawPicture(9);
            }
        });
    }

    public void listenersToMenu()
    {
        clear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                clear();
            }
        });

        reduceNoises.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                reduceNoises();
            }
        });

        save.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                addPicture();
            }
        });

        autoNoises.addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent event) {}

            @Override
            public void mouseEntered(MouseEvent event)
            {
                if(SwingUtilities.isLeftMouseButton(event)) autoBlack();
                if(SwingUtilities.isRightMouseButton(event)) autoWhite();
            }

            @Override
            public void mouseExited(MouseEvent event)
            {
                if(SwingUtilities.isLeftMouseButton(event)) autoBlack();
                if(SwingUtilities.isRightMouseButton(event)) autoWhite();
            }

            @Override
            public void mousePressed(MouseEvent event)
            {
                if(SwingUtilities.isLeftMouseButton(event)) autoBlack();
                if(SwingUtilities.isRightMouseButton(event)) autoWhite();
            }

            @Override
            public void mouseReleased(MouseEvent event) {}
        });
    }

    public void autoBlack()
    {
        for(int i = 0; i < 40; i++)
        {
            int randomNoise = rand.nextInt(MAX);
            pixelLeftWindow[randomNoise].setBackground(Color.BLACK);
            pixelRightWindow[randomNoise].setBackground(Color.BLACK);
            pixelColor[randomNoise] = 1;
        }
    }

    public void autoWhite()
    {
        for(int i = 0; i < 40; i++)
        {
            int randomNoise = rand.nextInt(MAX);
            pixelLeftWindow[randomNoise].setBackground(Color.WHITE);
            pixelRightWindow[randomNoise].setBackground(Color.WHITE);
            pixelColor[randomNoise] = -1;
        }
    }
}
