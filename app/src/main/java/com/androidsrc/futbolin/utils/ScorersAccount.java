package com.androidsrc.futbolin.utils;

public class ScorersAccount {
    String local_id;
    String visit_id;


    int localGoal;
    int visitGoal;
    String resultGoal;

    int localShootOnTarget;
    int localShootOut;
    String resultLocalShoots;

    int visitShootOnTarget;
    int visitShootOut;
    String resultVisitShoots;

    int localFouls;
    int visitFouls;
    String resultLocalFouls;
    String resultVisitFouls;

    int localYellowCards;
    int localRedCards;
    String resultLocalCards;

    int visitYellowCards;
    int visitRedCards;
    String resultVisitCards;

    int localSubs;
    int visitSubs;
    String resultLocalSubs;
    String resultVisitSubs;

    int localPoints;
    int visitPoints;

    int localGoalsDifference;
    int visitGoalsDifference;

    int previousLocalPoints;
    int previousVisitPoints;

    int previousLocalDFs;
    int previousVIsitDFs;

    public ScorersAccount(){
        localGoal = 0;
        visitGoal = 0;
        localShootOnTarget = 0;
        localShootOut = 0;
        visitShootOnTarget = 0;
        visitShootOut = 0;
        localFouls = 0;
        visitFouls = 0;
        localYellowCards = 0;
        localRedCards = 0;
        visitYellowCards = 0;
        visitRedCards = 0;
        localSubs = 0;
        visitSubs = 0;
        localPoints = 1;
        visitPoints = 1;
    }

    public int getLocalGoalsDifference() {
        this.localGoalsDifference = getLocalGoal() - getVisitGoal() + previousLocalDFs;
        return localGoalsDifference;
    }

    public void setLocalGoalsDifference(int localGoalsDifference) {
        this.localGoalsDifference = localGoalsDifference;
    }

    public int getVisitGoalsDifference() {
        this.visitGoalsDifference = getVisitGoal() - getLocalGoal() + previousVIsitDFs;
        return visitGoalsDifference;
    }

    public void setVisitGoalsDifference(int visitGoalsDifference) {
        this.visitGoalsDifference = visitGoalsDifference;
    }

    public ScorersAccount(String local_id, String visit_id){
        this.local_id = local_id;
        this.visit_id = visit_id;
        localGoal = 0;
        visitGoal = 0;
        localShootOnTarget = 0;
        localShootOut = 0;
        visitShootOnTarget = 0;
        visitShootOut = 0;
        localFouls = 0;
        visitFouls = 0;
        localYellowCards = 0;
        localRedCards = 0;
        visitYellowCards = 0;
        visitRedCards = 0;
        localSubs = 0;
        visitSubs = 0;
        localPoints = 1;
        visitPoints = 1;
    }

    public int getPreviousLocalDFs() {
        return previousLocalDFs;
    }

    public void setPreviousLocalDFs(int previousLocalDFs) {
        this.previousLocalDFs = previousLocalDFs;
    }

    public int getPreviousVIsitDFs() {
        return previousVIsitDFs;
    }

    public void setPreviousVIsitDFs(int previousVIsitDFs) {
        this.previousVIsitDFs = previousVIsitDFs;
    }

    public int getLocalPoints() {
        if(getLocalGoal() == getVisitGoal() ){
            this.localPoints = 1 + previousLocalPoints;
            this.visitPoints = 1 + previousVisitPoints;
        }else if(getLocalGoal() > getVisitGoal()){
            this.localPoints = 3 + previousLocalPoints;
            this.visitPoints = 0 + previousVisitPoints;
        }else if(getLocalGoal() < getVisitGoal()){
            this.localPoints = 0 + previousLocalPoints;
            this.visitPoints = 3 + previousVisitPoints;
        }

        return localPoints;
    }

    public void setLocalPoints(int localPoints) {
        this.localPoints = localPoints;
    }

    public int getVisitPoints() {
        if(getLocalGoal() == getVisitGoal() ){
            this.localPoints = 1 + previousLocalPoints;
            this.visitPoints = 1 + previousVisitPoints;
        }else if(getLocalGoal() > getVisitGoal()){
            this.localPoints = 3 + previousLocalPoints;
            this.visitPoints = 0 + previousVisitPoints;
        }else if(getLocalGoal() < getVisitGoal()){
            this.localPoints = 0 + previousLocalPoints;
            this.visitPoints = 3 + previousVisitPoints;
        }
        return visitPoints;
    }

    public int getPreviousLocalPoints() {
        return previousLocalPoints;
    }

    public void setPreviousLocalPoints(int previousLocalPoints) {
        this.previousLocalPoints = previousLocalPoints;
    }

    public int getPreviousVisitPoints() {
        return previousVisitPoints;
    }

    public void setPreviousVisitPoints(int previousVisitPoints) {
        this.previousVisitPoints = previousVisitPoints;
    }

    public void setVisitPoints(int visitPoints) {
        this.visitPoints = visitPoints;
    }

    public String getLocal_id() {
        return local_id;
    }

    public void setLocal_id(String local_id) {
        this.local_id = local_id;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public void increaseGoal(int team_id){
        if(team_id == 0)
            setLocalGoal(getLocalGoal() + 1);
        else
            setVisitGoal(getVisitGoal() + 1);
    }
    public void increaseShootOut(int team_id){
        if(team_id == 0)
            setLocalShootOut(getLocalShootOut() + 1);
        else
            setVisitShootOut(getVisitShootOut() + 1);
    }
    public void increaseShootOnTarget(int team_id){
        if(team_id == 0) {
            setLocalShootOut(getLocalShootOut() + 1);
            setLocalShootOnTarget(getLocalShootOnTarget() + 1);
        }
        else {
            setVisitShootOut(getVisitShootOut() + 1);
            setVisitShootOnTarget(getVisitShootOnTarget() + 1);
        }
    }
    public void increaseSubs(int team_id){
        if(team_id == 0)
            setLocalSubs(getLocalSubs() + 1);
        else
            setVisitSubs(getVisitSubs() + 1);
    }
    public int getLocalGoal() {
        return localGoal;
    }
    public void increaseFoul(int team_id){
        if(team_id == 0)
            setLocalFouls(getLocalFouls() + 1);
        else
            setVisitFouls(getVisitFouls() + 1);
    }
    public void increaseYellowCard(int team_id){
        if(team_id == 0){
            setLocalFouls(getLocalFouls() + 1);
            setLocalYellowCards(getLocalYellowCards() + 1);
        }else {
            setVisitFouls(getVisitFouls() + 1);
            setVisitYellowCards(getVisitYellowCards() + 1);
        }
    }
    public void increaseRedCard(int team_id){
        if(team_id == 0){
            setLocalFouls(getLocalFouls() + 1);
            setLocalRedCards(getLocalRedCards() + 1);
        }
        else {
            setVisitFouls(getVisitFouls() + 1);
            setVisitRedCards(getVisitRedCards() + 1);
        }
    }
    public int getSubs(int team_id){
        if(team_id == 0){
            return getLocalSubs();
        }else{
            return  getVisitSubs();
        }
    }


    public void setLocalGoal(int localGoal) {
        this.localGoal = localGoal;
    }

    public int getVisitGoal() {
        return visitGoal;
    }

    public void setVisitGoal(int visitGoal) {
        this.visitGoal = visitGoal;
    }

    public String getResultGoal() {
        resultGoal = localGoal + " - " + visitGoal;
        return resultGoal;
    }

    public void setResultGoal(String resultGoal) {
        this.resultGoal = resultGoal;
    }

    public int getLocalShootOnTarget() {
        return localShootOnTarget;
    }

    public void setLocalShootOnTarget(int localShootOnTarget) {
        this.localShootOnTarget = localShootOnTarget;
    }

    public int getLocalShootOut() {
        return localShootOut;
    }

    public void setLocalShootOut(int localShootOut) {
        this.localShootOut = localShootOut;
    }

    public String getResultLocalShoots() {
        resultLocalShoots = localShootOut + " (" + localShootOnTarget + ")";
        return resultLocalShoots;
    }

    public void setResultLocalShoots(String resultLocalShoots) {
        this.resultLocalShoots = resultLocalShoots;
    }

    public int getVisitShootOnTarget() {
        return visitShootOnTarget;
    }

    public void setVisitShootOnTarget(int visitShootOnTarget) {
        this.visitShootOnTarget = visitShootOnTarget;
    }

    public int getVisitShootOut() {
        return visitShootOut;
    }

    public void setVisitShootOut(int visitShootOut) {
        this.visitShootOut = visitShootOut;
    }

    public String getResultVisitShoots() {
        resultVisitShoots = visitShootOut + " (" + visitShootOnTarget + ")";
        return resultVisitShoots;
    }

    public void setResultVisitShoots(String resultVisitShoots) {
        this.resultVisitShoots = resultVisitShoots;
    }

    public int getLocalFouls() {
        return localFouls;
    }

    public void setLocalFouls(int localFouls) {
        this.localFouls = localFouls;
    }

    public int getVisitFouls() {
        return visitFouls;
    }

    public void setVisitFouls(int visitFouls) {
        this.visitFouls = visitFouls;
    }



    public int getLocalYellowCards() {
        return localYellowCards;
    }

    public void setLocalYellowCards(int localYellowCards) {
        this.localYellowCards = localYellowCards;
    }

    public int getLocalRedCards() {
        return localRedCards;
    }

    public void setLocalRedCards(int localRedCards) {
        this.localRedCards = localRedCards;
    }

    public String getResultLocalCards() {
        resultLocalCards = localYellowCards + " / " + localRedCards;
        return resultLocalCards;
    }

    public void setResultLocalCards(String resultLocalCards) {
        this.resultLocalCards = resultLocalCards;
    }

    public int getVisitYellowCards() {
        return visitYellowCards;
    }

    public void setVisitYellowCards(int visitYellowCards) {
        this.visitYellowCards = visitYellowCards;
    }

    public int getVisitRedCards() {
        return visitRedCards;
    }

    public void setVisitRedCards(int visitRedCards) {
        this.visitRedCards = visitRedCards;
    }

    public String getResultVisitCards() {
        resultVisitCards = visitYellowCards + " / " + visitRedCards;
        return resultVisitCards;
    }

    public void setResultVisitCards(String resultVisitCards) {
        this.resultVisitCards = resultVisitCards;
    }

    public int getLocalSubs() {
        return localSubs;
    }

    public void setLocalSubs(int localSubs) {
        this.localSubs = localSubs;
    }

    public int getVisitSubs() {
        return visitSubs;
    }

    public void setVisitSubs(int visitSubs) {
        this.visitSubs = visitSubs;
    }



    public void incraseLocalGoal(){
        localGoal = localGoal + 1;
    }
    public void incraseVisitGoal(){
        visitGoal = visitGoal + 1;
    }

    public String getResultLocalFouls() {
        resultLocalFouls = localFouls + "";
        return resultLocalFouls;
    }

    public void setResultLocalFouls(String resultLocalFouls) {
        this.resultLocalFouls = resultLocalFouls;
    }

    public String getResultVisitFouls() {
        resultVisitFouls = visitFouls + "";
        return resultVisitFouls;
    }

    public void setResultVisitFouls(String resultVisitFouls) {
        this.resultVisitFouls = resultVisitFouls;
    }

    public String getResultLocalSubs() {
        resultLocalSubs  = "" + localSubs;
        return resultLocalSubs;
    }

    public void setResultLocalSubs(String resultLocalSubs) {
        this.resultLocalSubs = resultLocalSubs;
    }

    public String getResultVisitSubs() {
        resultVisitSubs = "" + visitSubs;
        return resultVisitSubs;
    }

    public void setResultVisitSubs(String resultVisitSubs) {
        this.resultVisitSubs = resultVisitSubs;
    }

    @Override
    public String toString() {
        return "ScorersAccount{" +
                "local_id=" + local_id +
                ", visit_id=" + visit_id +
                ", localGoal=" + localGoal +
                ", visitGoal=" + visitGoal +
                ", resultGoal='" + resultGoal + '\'' +
                ", localShootOnTarget=" + localShootOnTarget +
                ", localShootOut=" + localShootOut +
                ", resultLocalShoots='" + resultLocalShoots + '\'' +
                ", visitShootOnTarget=" + visitShootOnTarget +
                ", visitShootOut=" + visitShootOut +
                ", resultVisitShoots='" + resultVisitShoots + '\'' +
                ", localFouls=" + localFouls +
                ", visitFouls=" + visitFouls +
                ", resultLocalFouls='" + resultLocalFouls + '\'' +
                ", resultVisitFouls='" + resultVisitFouls + '\'' +
                ", localYellowCards=" + localYellowCards +
                ", localRedCards=" + localRedCards +
                ", resultLocalCards='" + resultLocalCards + '\'' +
                ", visitYellowCards=" + visitYellowCards +
                ", visitRedCards=" + visitRedCards +
                ", resultVisitCards='" + resultVisitCards + '\'' +
                ", localSubs=" + localSubs +
                ", visitSubs=" + visitSubs +
                ", resultLocalSubs='" + resultLocalSubs + '\'' +
                ", resultVisitSubs='" + resultVisitSubs + '\'' +
                '}';
    }
}
