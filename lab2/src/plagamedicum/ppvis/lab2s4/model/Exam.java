package plagamedicum.ppvis.lab2s4.model;

public class Exam {
    private String name;
    private int score;

    public Exam(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }
}
