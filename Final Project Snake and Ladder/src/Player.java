import java.util.Random;

public class Player {
    private String name;
    private int position;
    private int totalRolls;
    private int totalSixes;
    private int totalSnakes;
    private int totalLadders;
    private Random rand = new Random();
    private double[] probabilities;

    Player(String name) {
        this.name = name;
        this.position = 0;
        this.totalRolls = 0;
        this.totalSixes = 0;
        generateRandomProbabilities();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public String getName(){
        return this.name;
    }

    public int getPosition(){
        return position;
    }

    public int rollDice(){
        return (int)(Math.random()*6+1);
    }

    public void rollCount() {
        totalRolls++;
    }
    public void sixesCount() {
        totalSixes++;
    }
    public void snakeCount() {
        totalSnakes++;
    }
    public void ladderCount() {
        totalLadders++;
    }

    public int getTotalRolls() {
        return totalRolls;
    }
    public int getTotalSixes() {
        return totalSixes;
    }
    public int getTotalSnakes() {
        return totalSnakes;
    }
    public int getTotalLadders() {
        return totalLadders;
    }

    public void resetStats() {
        totalRolls = 0;
        totalSixes = 0;
        totalSnakes = 0;
        totalLadders = 0;
    }

    public void moveAround(int y, int boardSize){
        if(this.position + y > boardSize){
            this.position = boardSize - (this.position + y) % boardSize;
        } else {
            this.position = this.position + y;
        }
    }

    private void generateRandomProbabilities() {
        probabilities = new double[6];
        double total = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = rand.nextDouble();
            total += probabilities[i];
        }
        // Normalize to make the sum equal to 1
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] /= total;
        }

    }

    public double getProbabilities1() {
        return probabilities[0] * 100;
    }
    public double getProbabilities2() {
        return probabilities[1] * 100;
    }
    public double getProbabilities3() {
        return probabilities[2] * 100;
    }
    public double getProbabilities4() {
        return probabilities[3] * 100;
    }
    public double getProbabilities5() {
        return probabilities[4] * 100;
    }
    public double getProbabilities6() {
        return probabilities[5] * 100;
    }

    public int rollHellboundDice() {
        double randomValue = rand.nextDouble();
        double cumulativeProbability = 0.0;

        for (int i = 0; i < probabilities.length; i++) {
            cumulativeProbability += probabilities[i];
            if (randomValue <= cumulativeProbability) {
                return i + 1; // Return dice value (1-6)
            }
        }

        return probabilities.length;
    }
}









