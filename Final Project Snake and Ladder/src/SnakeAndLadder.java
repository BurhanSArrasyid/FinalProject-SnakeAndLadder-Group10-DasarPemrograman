import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SnakeAndLadder {
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private int boardSize;
    private int gameStatus;
    private int nowPlaying;
    private int snakeCount;
    private int ladderCount;

    public SnakeAndLadder(int s){
        this.boardSize = s;
        this.gameStatus = 0;
        this.players = new ArrayList<Player>();
        this.snakes = new ArrayList<Snake>();
        this.ladders = new ArrayList<Ladder>();

    }
    String ANSI_RESET = "\u001B[0m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";

    Scanner read = new Scanner(System.in);

    public void setBoardSize(int s){
        this.boardSize = s;
    }
    public void setGameStatus(int s){
        this.gameStatus = s;
    }
    public void addPlayer(Player s){
        this.players.add(s);
    }
    public int getBoardSize(){
        return this.boardSize;
    }


    public void addSnake(Snake s){
        this.snakes.add(s);
    }

    public void addSnakes(int[][] s){
        for(int row = 0; row < s.length; row++){
            Snake snake = new Snake(s[row][0], s[row][1]);
            this.snakes.add(snake);
        }
    }


    public void addLadder(Ladder l){
        this.ladders.add(l);
    }

    public void addLadders(int[][] l){
        for(int row = 0; row < l.length; row++){
            Ladder ladder = new Ladder(l[row][1], l[row][0]);
            this.ladders.add(ladder);
        }
    }


    public int getGameStatus(){
        return this.gameStatus;
    }

    public void play(){
        while(true){
            File voicePlay = new File("SuaraPermainan.wav");
                if (!voicePlay.exists()) {
                    System.out.println("Error: Audio file 'SuaraPermainan.wav' not found!");
                    return;
                }

                try {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(voicePlay);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    clip.start();

                    // Ensure the sound clip is completed before proceeding
                    while (clip.isRunning()) {
                        Thread.sleep(100);
                    }
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException |
                         InterruptedException e) {
                    System.out.println("Error playing game sound!");
                    e.printStackTrace();
                }

            System.out.println("Snake and Ladder (Lite Ver.) \nGame Mode: \n1. Competitive Arena \n2. Fun Games \n3. The Madness Arena \n4. The Hellbound. \n0. Exit");
            System.out.print("Choose game mode: ");
            String menu = read.nextLine();
            Player playerInTurn;
            int playerNumber = 0;
            int totalPlayers = 0;
            int totalRound = 0;
            int level = 0;

            if (menu.equals("0")) {
                System.out.println("Exiting...");
                break;
            }

            switch (menu) {
                case "1":
                    System.out.println("\nYou entered the Competitive Arena!");

                    level++;

                    for (int i = 1; i <= 1; i++) {
                        System.out.println("Please enter your name, Player " + i + ":");
                        String playerName = read.nextLine();
                        Player player = new Player(playerName);
                        addPlayer(player);
                    }

                    while (true) {
                        initiateGame(level);
                        for (Player player : players) {
                            player.setPosition(0);
                        }

                        System.out.println();
                        System.out.println("Level: " + level);
                        System.out.println("Board Size: " + boardSize);
                        System.out.println("Snake Count: " + snakeCount);
                        System.out.println("Ladder Count: " + ladderCount);

                        gameStatus = 1;

                        do {
                            playerInTurn = getWhoseTurn();

                            System.out.println(playerInTurn.getName() + ", please tap enter to roll the dice:");
                            String rollDice = read.nextLine();

                            File file = new File("SuaraDadu.wav");
                            if (!file.exists()) {
                                System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                return;
                            }

                            try {
                                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioStream);
                                clip.start();

                                // Ensure the sound clip is completed before proceeding
                                while (clip.isRunning()) {
                                    Thread.sleep(100);
                                }
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                System.out.println("Error playing dice sound!");
                                e.printStackTrace();
                            }


                            int x = 0;
                            int totalMove = 0;
                            if (rollDice.isEmpty()) {


                                x = playerInTurn.rollDice();
                                playerInTurn.rollCount();
                                totalMove += x;
                                System.out.println("Dice Number: " + x);
                                if (x == 6) {
                                    playerInTurn.sixesCount();
                                    System.out.println(ANSI_GREEN + "You got a chance to roll again!" + ANSI_RESET + "\nPlease tap enter to roll the dice:");
                                    read.nextLine();
                                    if (!file.exists()) {
                                        System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                        return;
                                    }

                                    try {
                                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioStream);
                                        clip.start();

                                        // Ensure the sound clip is completed before proceeding
                                        while (clip.isRunning()) {
                                            Thread.sleep(100);
                                        }
                                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                        System.out.println("Error playing dice sound!");
                                        e.printStackTrace();
                                    }
                                    x = playerInTurn.rollDice();
                                    System.out.println("Dice Number: " + x);
                                    totalMove += x;
                                }
                            }

                            movePlayerAround(playerInTurn, totalMove);
                            System.out.println("New Position of " + playerInTurn.getName() + ": " + playerInTurn.getPosition());
                            System.out.println("===========================================================");
                        }
                        while (getGameStatus() != 2);
                        System.out.println(ANSI_GREEN + "Congratulations! You've completed level: " + level + ANSI_RESET);
                        displayPlayerStats();

                        level++;
                        System.out.println("Shall we continue the journey?");
                        String continuation = read.nextLine();
                        if (continuation.equalsIgnoreCase("No")) {
                            break;
                        }
                    }
                    break;

                case "2":
                    //Player playerInTurn;
                    System.out.println("\nYou entered Fun Game. Have some fun with your pals (or alone LOL)!");
                    System.out.println("Please input total player: ");
                    totalPlayers = read.nextInt();
                    read.nextLine();

                    for (int i = 1; i <= totalPlayers; i++) {
                        System.out.println("Please enter your name, Player " + i + ":");
                        String playerName = read.nextLine();
                        Player player = new Player(playerName);
                        addPlayer(player);
                    }

                    System.out.println("Please enter total round: ");
                    totalRound = read.nextInt();

                    for (int i = 0; i < totalRound; i++) {

                        for (Player player : players) {
                            player.setPosition(0);
                        }

                        initiateGame(1);

                        gameStatus = 1;

                        do {
                            playerInTurn = getWhoseTurn();

                            System.out.println("\nNow Playing: " + playerInTurn.getName());
                            //System.out.println(playerInTurn.getName());

                            System.out.println(playerInTurn.getName() + ", please tap enter to roll the dice:");
                            String rollDice = read.nextLine();

                            File file = new File("SuaraDadu.wav");
                            if (!file.exists()) {
                                System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                return;
                            }

                            try {
                                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioStream);
                                clip.start();

                                // Ensure the sound clip is completed before proceeding
                                while (clip.isRunning()) {
                                    Thread.sleep(100);
                                }
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                System.out.println("Error playing dice sound!");
                                e.printStackTrace();
                            }

                            int x = 0;
                            int totalMove = 0;
                            if (rollDice.isEmpty()) {
                                x = playerInTurn.rollDice();
                                playerInTurn.rollCount();
                                totalMove += x;
                                System.out.println("Dice Number: " + x);
                                if (x == 6) {
                                    playerInTurn.sixesCount();
                                    System.out.println(ANSI_GREEN + "You got a chance to roll again!" + ANSI_RESET + "\nPlease tap enter to roll the dice:");
                                    read.nextLine();
                                    //2File file = new File("SuaraDadu.wav");
                                    if (!file.exists()) {
                                        System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                        return;
                                    }

                                    try {
                                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioStream);
                                        clip.start();

                                        // Ensure the sound clip is completed before proceeding
                                        while (clip.isRunning()) {
                                            Thread.sleep(100);
                                        }
                                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                        System.out.println("Error playing dice sound!");
                                        e.printStackTrace();
                                    }
                                    x = playerInTurn.rollDice();
                                    System.out.println("Dice Number: " + x);
                                    totalMove += x;
                                }
                            }

                            movePlayerAround(playerInTurn, totalMove);
                            System.out.println("New Position of " + playerInTurn.getName() + ": " + playerInTurn.getPosition());
                            System.out.println("===========================================================");
                        }
                        while (getGameStatus() != 2);
                        System.out.println("The Winner is: " + ANSI_GREEN + playerInTurn.getName() + ANSI_RESET);
                        displayPlayerStats();
                    }
                case "3":
                    //Player playerInTurn;
                    System.out.println("\nYou entered The Madness Arena. Have some fun with the RANDOMNESS of the World!");
                    System.out.println("Please input total player: ");
                    totalPlayers = read.nextInt();
                    read.nextLine();

                    for (int i = 1; i <= totalPlayers; i++) {
                        System.out.println("Please enter your name, Player " + i + ":");
                        String playerName = read.nextLine();
                        Player player = new Player(playerName);
                        addPlayer(player);
                    }

                    System.out.println("Please enter total round: ");
                    totalRound = read.nextInt();

                    for (int i = 0; i < totalRound; i++) {

                        for (Player player : players) {
                            player.setPosition(0);
                        }

                        initiateMadnessGame();

                        System.out.println();
                        System.out.println("Board Size: " + boardSize);
                        System.out.println("Snake Count: " + snakeCount);
                        System.out.println("Ladder Count: " + ladderCount);

                        gameStatus = 1;

                        do {
                            playerInTurn = getWhoseTurn();

                            System.out.println("\nNow Playing: " + playerInTurn.getName());
                            //System.out.println(playerInTurn.getName());

                            System.out.println(playerInTurn.getName() + ", please tap enter to roll the dice:");
                            String rollDice = read.nextLine();

                            File file = new File("SuaraDadu.wav");
                            if (!file.exists()) {
                                System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                return;
                            }

                            try {
                                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioStream);
                                clip.start();

                                // Ensure the sound clip is completed before proceeding
                                while (clip.isRunning()) {
                                    Thread.sleep(100);
                                }
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                System.out.println("Error playing dice sound!");
                                e.printStackTrace();
                            }

                            int x = 0;
                            int totalMove = 0;
                            if (rollDice.isEmpty()) {
                                x = playerInTurn.rollDice();
                                playerInTurn.rollCount();
                                totalMove += x;
                                System.out.println("Dice Number: " + x);
                                if (x == 6) {
                                    playerInTurn.sixesCount();
                                    System.out.println(ANSI_GREEN + "You got a chance to roll again!" + ANSI_RESET + "\nPlease tap enter to roll the dice:");
                                    read.nextLine();
                                    //2File file = new File("SuaraDadu.wav");
                                    if (!file.exists()) {
                                        System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                        return;
                                    }

                                    try {
                                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioStream);
                                        clip.start();

                                        // Ensure the sound clip is completed before proceeding
                                        while (clip.isRunning()) {
                                            Thread.sleep(100);
                                        }
                                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                        System.out.println("Error playing dice sound!");
                                        e.printStackTrace();
                                    }
                                    x = playerInTurn.rollDice();
                                    System.out.println("Dice Number: " + x);
                                    totalMove += x;
                                }
                            }

                            movePlayerAround(playerInTurn, totalMove);
                            System.out.println("New Position of " + playerInTurn.getName() + ": " + playerInTurn.getPosition());
                            System.out.println("===========================================================");
                        }
                        while (getGameStatus() != 2);
                        System.out.println("The Winner is: " + ANSI_GREEN + playerInTurn.getName() + ANSI_RESET);
                        displayPlayerStats();
                    }

                case "4":
                    //Player playerInTurn;
                    System.out.println("\nYou entered The HELLBOUND. Get ready to go insane with the game. \nGood. Luck.\n");
                    System.out.println("Please input total player: ");
                    totalPlayers = read.nextInt();
                    read.nextLine();

                    for (int i = 1; i <= totalPlayers; i++) {
                        System.out.println("Please enter your name, Player " + i + ":");
                        String playerName = read.nextLine();
                        Player player = new Player(playerName);
                        addPlayer(player);
                    }

                    int hellboundLevels[] = {60, 55, 50, 45, 40, 35, 30, 25, 20};
                    int hellboundMoves = 0;
                    int hellboundLevelEntry = 0;
                    System.out.println("Please enter Hellbound Level (1-9): ");
                    hellboundLevelEntry = read.nextInt();

                    System.out.println("Please enter total round: ");
                    totalRound = read.nextInt();

                    for (int i = 0; i < totalRound; i++) {

                        for (Player player : players) {
                            player.setPosition(0);
                        }

                        DecimalFormat df = new DecimalFormat("0.00");
                        int totalMoves = 0;
                        hellboundMoves = hellboundLevels[hellboundLevelEntry - 1] * totalPlayers;

                        initiateMadnessGame();

                        playerInTurn = getWhoseTurn();

                        System.out.println();
                        System.out.println("Board Size: " + boardSize);
                        System.out.println("Snake Count: " + snakeCount);
                        System.out.println("Ladder Count: " + ladderCount);
                        System.out.println();
                        System.out.println("Dice Probabilities: ");
                        System.out.println("Dice I: " + df.format(playerInTurn.getProbabilities1()) + "%");
                        System.out.println("Dice II: " + df.format(playerInTurn.getProbabilities2()) + "%");
                        System.out.println("Dice III: " + df.format(playerInTurn.getProbabilities3()) + "%");
                        System.out.println("Dice IV: " + df.format(playerInTurn.getProbabilities4()) + "%");
                        System.out.println("Dice V: " + df.format(playerInTurn.getProbabilities5()) + "%");
                        System.out.println("Dice VI: " + df.format(playerInTurn.getProbabilities6()) + "%");
                        System.out.println("Maximum moves: " + hellboundMoves + " (Total player (" + totalPlayers + ") multiplied by total move from Hellbound Level Selection (" + hellboundLevels[hellboundLevelEntry - 1] + "))");
                        read.nextLine();

                        gameStatus = 1;

                        do {
                            totalMoves++;
                            playerInTurn = getWhoseTurn();

                            System.out.println("\nNow Playing: " + playerInTurn.getName());
                            //System.out.println(playerInTurn.getName());

                            System.out.println(playerInTurn.getName() + ", please tap enter to roll the dice:");
                            String rollDice = read.nextLine();

                            File file = new File("SuaraDadu.wav");
                            if (!file.exists()) {
                                System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                return;
                            }

                            try {
                                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioStream);
                                clip.start();

                                // Ensure the sound clip is completed before proceeding
                                while (clip.isRunning()) {
                                    Thread.sleep(100);
                                }
                            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                System.out.println("Error playing dice sound!");
                                e.printStackTrace();
                            }

                            int x = 0;
                            int totalMove = 0;
                            if (rollDice.isEmpty()) {
                                x = playerInTurn.rollHellboundDice();
                                playerInTurn.rollCount();
                                totalMove += x;
                                System.out.println("Dice Number: " + x);
                                System.out.println("Remaining moves: " + --hellboundMoves);
                                if (x == 6) {
                                    playerInTurn.sixesCount();
                                    System.out.println(ANSI_GREEN + "You got a chance to roll again!" + ANSI_RESET + "\nPlease tap enter to roll the dice:");
                                    read.nextLine();
                                    //2File file = new File("SuaraDadu.wav");
                                    if (!file.exists()) {
                                        System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                                        return;
                                    }

                                    try {
                                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioStream);
                                        clip.start();

                                        // Ensure the sound clip is completed before proceeding
                                        while (clip.isRunning()) {
                                            Thread.sleep(100);
                                        }
                                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                                        System.out.println("Error playing dice sound!");
                                        e.printStackTrace();
                                    }
                                    x = playerInTurn.rollDice();
                                    System.out.println("Dice Number: " + x);
                                    totalMove += x;
                                    System.out.println("Remaining moves: " + --hellboundMoves);
                                }
                            }

                            movePlayerAround(playerInTurn, totalMove);
                            System.out.println("New Position of " + playerInTurn.getName() + ": " + playerInTurn.getPosition());
                            System.out.println("===========================================================");

                            if (hellboundMoves = 0) {
                                gameStatus = 2;
                            }
                        }
                        while (getGameStatus() != 2 || totalMoves <= hellboundMoves);
                        if (totalMoves <= hellboundMoves) {
                            System.out.println("The Winner is: " + ANSI_GREEN + playerInTurn.getName() + ANSI_RESET);
                            displayPlayerStats();
                        } else if (totalMoves > hellboundMoves && totalPlayers > 1){
                            System.out.println(ANSI_RED + "Y'all lose so bad. Smh... " + ANSI_RESET);
                            displayPlayerStats();
                        } else {
                            System.out.println(ANSI_RED + "You lose so bad. Smh... " + ANSI_RESET);
                            displayPlayerStats();
                        }

                    }
            }
        }
    }


    public ArrayList<Player> getPlayers(){
        return this.players;
    }
    public ArrayList<Snake> getSnakes(){
        return this.snakes;
    }
    public ArrayList<Ladder> getLadders(){
        return this.ladders;
    }

    public void initiateGame(int level) {
        int[] boardSizeList = {100, 121, 144, 169, 196, 215};
        if (level == 1) {
            int[][] l = {
                    {2, 23},
                    {8, 34},
                    {20, 77},
                    {32, 68},
                    {41, 79},
                    {74, 88},
                    {82, 100},
                    {85, 95}
            };

            addLadders(l);

            int[][] s = {
                    {5, 47},
                    {9, 29},
                    {15, 38},
                    {25, 97},
                    {33, 53},
                    {37, 62},
                    {54, 86},
                    {70, 92}
            };

            addSnakes(s);
            boardSize = boardSizeList[0];
            ladderCount = 8;
            snakeCount = 8;
        }

        if (level == 2) {
            int[][] l = {
                    {3, 22},
                    {10, 44},
                    {30, 67},
                    {42, 65},
                    {52, 90},
                    {60, 82},
                    {71, 98},
                    {81, 105}
            };

            addLadders(l);

            int[][] s = {
                    {4, 15},
                    {18, 25},
                    {36, 50},
                    {45, 60},
                    {53, 72},
                    {64, 80},
                    {75, 92},
                    {85, 110}
            };

            addSnakes(s);
            boardSize = boardSizeList[1];
            ladderCount = 7;
            snakeCount = 9;
        }

        if (level == 3) {
            int[][] l = {
                    {5, 25},
                    {12, 37},
                    {26, 45},
                    {39, 62},
                    {50, 75},
                    {67, 85},
                    {78, 99}
            };

            addLadders(l);

            int[][] s = {
                    {7, 14},
                    {19, 33},
                    {29, 47},
                    {38, 52},
                    {49, 68},
                    {55, 72},
                    {70, 88},
                    {83, 109},
                    {91, 120}
            };

            addSnakes(s);
            boardSize = boardSizeList[2];
            ladderCount = 6;
            snakeCount = 10;
        }

        if (level == 4) {
            int[][] l = {
                    {2, 20},
                    {11, 30},
                    {24, 41},
                    {33, 56},
                    {46, 63},
                    {59, 78}
            };

            addLadders(l);

            int[][] s = {
                    {4, 12},
                    {17, 28},
                    {22, 39},
                    {31, 49},
                    {40, 60},
                    {52, 73},
                    {65, 87},
                    {76, 97},
                    {84, 104},
                    {94, 116}
            };

            addSnakes(s);
            boardSize = boardSizeList[3];
            ladderCount = 5;
            snakeCount = 11;
        }

        if (level == 5) {
            int[][] l = {
                    {6, 18},
                    {14, 32},
                    {29, 43},
                    {38, 57},
                    {51, 66}
            };

            addLadders(l);

            int[][] s = {
                    {8, 16},
                    {21, 36},
                    {27, 46},
                    {35, 53},
                    {47, 64},
                    {54, 71},
                    {62, 80},
                    {68, 83},
                    {74, 95},
                    {82, 110},
                    {89, 121}
            };

            addSnakes(s);
            boardSize = boardSizeList[4];
            ladderCount = 4;
            snakeCount = 12;
        }

        if (level == 6) {
            int[][] l = {
                    {5, 17},
                    {13, 26},
                    {28, 42},
                    {40, 55}
            };

            addLadders(l);

            int[][] s = {
                    {7, 15},
                    {20, 35},
                    {25, 44},
                    {34, 52},
                    {43, 61},
                    {48, 65},
                    {58, 77},
                    {63, 82},
                    {69, 88},
                    {76, 99},
                    {81, 109},
                    {87, 120}
            };

            addSnakes(s);
            boardSize = boardSizeList[5];
            ladderCount = 4;
            snakeCount = 12;
        }
    }

    public void initiateMadnessGame() {
        Random rand = new Random();
        int minBoardSize = 100;
        int maxBoardSize = 250;

        // Randomly select a board size between 100 and 250
        boardSize = rand.nextInt(maxBoardSize - minBoardSize + 1) + minBoardSize;

        // Determine the number of ladders and snakes
        int maxLadders = 10;
        int maxSnakes = 15;
        int ladderCount = rand.nextInt(maxLadders) + 1; // At least 1 ladder
        int snakeCount = rand.nextInt(maxSnakes) + 1; // At least 1 snake

        int[][] ladders = new int[ladderCount][2];
        int[][] snakes = new int[snakeCount][2];

        // Generate random ladder positions
        for (int i = 0; i < ladderCount; i++) {
            int start = rand.nextInt(boardSize - 1) + 1;
            int end = rand.nextInt(boardSize - start) + start + 1;
            ladders[i][0] = start;
            ladders[i][1] = end;
        }

        // Generate random snake positions
        for (int i = 0; i < snakeCount; i++) {
            int start = rand.nextInt(boardSize - 1) + 1;
            int end = rand.nextInt(start) + 1;
            snakes[i][0] = start;
            snakes[i][1] = end;
        }

        addLadders(ladders);
        addSnakes(snakes);

        this.ladderCount = ladderCount;
        this.snakeCount = snakeCount;
    }

    public void movePlayerAround(Player p, int x){
        p.moveAround(x, this.boardSize);
        for(Ladder l : this.ladders){
            if(p.getPosition() == l.getBottomPosition()){
                System.out.println(ANSI_YELLOW + p.getName() + ", you got Ladder from: " + l.getBottomPosition() + " to " + l.getTopPosition() + ANSI_RESET);
                p.setPosition(l.getTopPosition());
                p.ladderCount();

                File diceFile = new File("SuaraDadu.wav");
                File ladderFile = new File("SuaraTangga.wav");

                // Check if dice audio file exists
                if (!diceFile.exists()) {
                    System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                    return;
                }

                // Check if ladder audio file exists
                if (!ladderFile.exists()) {
                    System.out.println("Error: Audio file 'SuaraTangga.wav' not found!");
                    return;
                }

                try {
                    AudioInputStream diceStream = AudioSystem.getAudioInputStream(diceFile);
                    Clip diceClip = AudioSystem.getClip();
                    diceClip.open(diceStream);
                    diceClip.start();

                    // Wait for dice sound to finish
                    while (diceClip.isRunning()) {
                        Thread.sleep(100);
                    }

                    // Sleep for a short while (optional)
                    Thread.sleep(1500);

                    // Play ladder sound
                    AudioInputStream ladderStream = AudioSystem.getAudioInputStream(ladderFile);
                    Clip ladderClip = AudioSystem.getClip();
                    ladderClip.open(ladderStream);
                    ladderClip.start();

                    // Ensure ladder sound clip is completed before proceeding
                    while (ladderClip.isRunning()) {
                        Thread.sleep(100);
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                    System.out.println("Error playing audio file!");
                    e.printStackTrace();
                }
            }
        }
        for(Snake s : this.snakes){
            if(p.getPosition() == s.getHeadPosition()){
                System.out.println(ANSI_RED + p.getName() + ", you hit a Snake from: " + s.getHeadPosition() + " slide down to " + s.getTailPosition() + ANSI_RESET);
                p.setPosition(s.getTailPosition());
                p.snakeCount();

                File diceFile = new File("SuaraDadu.wav");
                File snakeFile = new File("SuaraUlar.wav");

                // Check if dice audio file exists
                if (!diceFile.exists()) {
                    System.out.println("Error: Audio file 'SuaraDadu.wav' not found!");
                    return;
                }

                // Check if ladder audio file exists
                if (!snakeFile.exists()) {
                    System.out.println("Error: Audio file 'SuaraUlar.wav' not found!");
                    return;
                }

                try {
                    AudioInputStream diceStream = AudioSystem.getAudioInputStream(diceFile);
                    Clip diceClip = AudioSystem.getClip();
                    diceClip.open(diceStream);
                    diceClip.start();

                    // Wait for dice sound to finish
                    while (diceClip.isRunning()) {
                        Thread.sleep(100);
                    }

                    // Sleep for a short while (optional)
                    Thread.sleep(1500);

                    // Play ladder sound
                    AudioInputStream ladderStream = AudioSystem.getAudioInputStream(snakeFile);
                    Clip ladderClip = AudioSystem.getClip();
                    ladderClip.open(ladderStream);
                    ladderClip.start();

                    // Ensure ladder sound clip is completed before proceeding
                    while (ladderClip.isRunning()) {
                        Thread.sleep(100);
                    }

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
                    System.out.println("Error playing audio file!");
                    e.printStackTrace();
                }
            }
        }
        if(p.getPosition() == this.boardSize){
            this.gameStatus = 2;
        }
    }


    public Player getWhoseTurn(){

        if(this.gameStatus == 0){
            this.gameStatus = 1;
            double r = Math.random();
            this.nowPlaying = (int) (r * this.players.size());
            return this.players.get(this.nowPlaying);
        } else {
            this.nowPlaying = (this.nowPlaying + 1) % this.players.size();
            return this.players.get(this.nowPlaying);
        }
    }

    private void displayPlayerStats() {
        for (Player player : players) {
            System.out.println("\n" + player.getName() + "'s Stats:");
            System.out.println("Total Rolls: " + player.getTotalRolls());
            System.out.println("Number of 6s Rolled: " + player.getTotalSixes());
            System.out.println("Total Snakes Slided: " + player.getTotalSnakes());
            System.out.println("Total Ladders Climbed: " + player.getTotalLadders());
            System.out.println("Final Position: " + player.getPosition());
            System.out.println("===========================================================\n");
            player.resetStats();
        }
    }
}