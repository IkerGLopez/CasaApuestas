package domain;

import java.util.ArrayList;

public class AnitzakTemp {
    private ArrayList<Question> questions;
    private ArrayList<String> emaitzak;
    private Double kuota;

    public AnitzakTemp() {
        this.questions = new ArrayList<Question>();
        this.emaitzak = new ArrayList<String>();
        this.kuota = 1.0;
    }

    public void gehitu(Question question, String emaitza, Double kuota) {
        this.questions.add(question);
        this.emaitzak.add(emaitza);
        this.kuota *= kuota;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public ArrayList<String> getEmaitzak() {
        return emaitzak;
    }

    public void setEmaitzak(ArrayList<String> emaitzak) {
        this.emaitzak = emaitzak;
    }


    public Double getKuota() {
        return kuota;
    }

    public void setKuota(Double kuota) {
        this.kuota = kuota;
    }

    public String toString() {
        return this.questions.toString() + ";" + this.emaitzak.toString() + ";" + this.kuota;
    }


}
