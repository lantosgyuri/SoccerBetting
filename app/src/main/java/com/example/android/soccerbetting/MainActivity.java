package com.example.android.soccerbetting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int teamAScore = 0;
    int teamBScore = 0;
    double teamAOdd = 1.48;
    double teamBOdd = 1.48;
    int teamAStake = 0;
    int teamBStake = 0;
    double profitTeamA = 0;
    double profitTeamB = 0;
    TextView everyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        }

        // Set Score. Calculating odds. Calculating expected profit. Display everything.

    public void setScoreTeamA (View view){
        teamAScore = teamAScore + 1;
        calculateOddBeiDominance();
        avoidNullOdd();
        profitTeamA = teamAOdd * teamAStake;
        profitTeamB = teamBOdd * teamBStake;
        displayForTeamA(teamAScore);
        displayForOddA(teamAOdd);
        displayForOddB(teamBOdd);
        displayForProfitA(profitTeamA);
        displayForProfitB(profitTeamB);
    }

    public void setScoreTeamB (View view){
        teamBScore = teamBScore + 1;
        calculateOddBeiDominance();
        avoidNullOdd();
        profitTeamA = teamAOdd * teamAStake;
        profitTeamB = teamBOdd * teamBStake;
        displayForTeamB(teamBScore);
        displayForOddA(teamAOdd);
        displayForOddB(teamBOdd);
        displayForProfitA(profitTeamA);
        displayForProfitB(profitTeamB);
    }

    // Take stake. Calculating expected Profit, than display.

    public void setTeamAStakePLus (View view){
        teamAStake = teamAStake + 1;
        profitTeamA = teamAOdd * teamAStake;
        displayForStakeA(teamAStake);
        displayForProfitA(profitTeamA);
    }

    public void setTeamBStakePLus (View view){
        teamBStake = teamBStake + 1;
        profitTeamB = teamBOdd * teamBStake;
        displayForStakeB(teamBStake);
        displayForProfitB(profitTeamB);
    }

    public void setTeamAStakeMinus (View view){
        teamAStake = teamAStake - 1;
        avoidNullStake ();
        profitTeamA = teamAOdd * teamAStake;
        displayForStakeA(teamAStake);
        displayForProfitA(profitTeamA);
    }

    public void setTeamBStakeMinus (View view){
        teamBStake = teamBStake - 1;
        avoidNullStake ();
        profitTeamB = teamBOdd * teamBStake;
        displayForStakeB(teamBStake);
        displayForProfitB(profitTeamB);
    }

    // Reset everything to 0

    public void reset (View view){
        teamAScore = 0;
        teamBScore = 0;
        teamAOdd = 1.48;
        teamBOdd = 1.48;
        teamAStake = 0;
        teamBStake = 0;
        profitTeamA = 0000;
        profitTeamB = 0000;
        displayForTeamA(teamAScore);
        displayForTeamB(teamBScore);
        displayForOddA(teamAOdd);
        displayForOddB(teamBOdd);
        displayForProfitA(profitTeamA);
        displayForProfitB(profitTeamB);
        displayForStakeA(teamAStake);
        displayForStakeB(teamBStake);
    }

    // Display Methods

    public void displayForTeamA(int score) {
        everyTextView = findViewById(R.id.team_a_score_text);
        everyTextView.setText(String.valueOf(score));
    }

    public void displayForTeamB(int score) {
        everyTextView = findViewById(R.id.team_b_score_text);
        everyTextView.setText(String.valueOf(score));
    }

    public void displayForOddA(double odd) {
        everyTextView = findViewById(R.id.team_a_odd);
        everyTextView.setText(String.format("%.2f", odd));
    }

    public void displayForOddB(double odd) {
        everyTextView = findViewById(R.id.team_b_odd);
        everyTextView.setText(String.format("%.2f", odd));
    }

    public void displayForStakeA(int stake) {
        everyTextView = findViewById(R.id.team_a_stake);
        everyTextView.setText(String.valueOf(stake) + "€");
    }

    public void displayForStakeB(int stake) {
        everyTextView =  findViewById(R.id.team_b_stake);
        everyTextView.setText(String.valueOf(stake) + "€");
    }

    public void displayForProfitA (double profit) {
        everyTextView = findViewById(R.id.team_a_profit);
        String profitA = ("Profit: " + String.format("%.2f", profit) + "€");
        everyTextView.setText(profitA);
    }

    public void displayForProfitB (double profit) {
        everyTextView = findViewById(R.id.team_b_profit);
        String profitB = ("Profit: " + String.format("%.2f", profit) + "€");
        everyTextView.setText(profitB);
    }

    // Prevent odd to be under 1

    public void avoidNullOdd () {
        if (teamAOdd <=1.1) teamAOdd = 1.04;
        if (teamBOdd <=1.1) teamBOdd = 1.04;
    }

    // Prevent Stake to be under 1

    public void avoidNullStake () {
        if (teamAStake <=0) teamAStake = 0;
        if (teamBStake <= 0) teamBStake = 0;
    }

    //Calculate Odd bei dominance

    public void calculateOddBeiDominance(){

        double dynamicOddLose;
        int aMinusBScore;
        int bMinusAScore;
        double dynamicOddWin;
        double dynamicOddBasic;

        aMinusBScore = teamAScore - teamBScore;
        bMinusAScore = teamBScore - teamAScore;

        // For a dynamic Odd I calculate first the multiplier compared to the goal difference
        dynamicOddBasic = Math.abs(aMinusBScore) * 0.08;

        // Than with the multiplier I calculate the Odd compared to the goal difference
        dynamicOddWin = Math.abs(aMinusBScore) * dynamicOddBasic;

        // The Loser Odd have to go down slower
        dynamicOddLose = Math.abs(aMinusBScore) * (dynamicOddBasic / 3);

        // Equal - > basic position
        if(aMinusBScore == 0 || bMinusAScore == 0){
            teamBOdd = 1.48;
            teamAOdd = 1.48;
        }

        // When A lead
        if (aMinusBScore> 0) {
            teamBOdd = 1.48 + dynamicOddWin;
            teamAOdd = 1.48 - dynamicOddLose;
        }

        // When B lead
        if (bMinusAScore> 0) {
            teamAOdd = 1.48 + dynamicOddWin;
            teamBOdd = 1.48 - dynamicOddLose;
        }

    }

}
