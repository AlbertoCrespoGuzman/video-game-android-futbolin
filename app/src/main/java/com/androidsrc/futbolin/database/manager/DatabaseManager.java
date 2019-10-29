package com.androidsrc.futbolin.database.manager;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.androidsrc.futbolin.database.models.MarketNotification;
import com.androidsrc.futbolin.database.models.Notification;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.Position;
import com.androidsrc.futbolin.database.models.Strategy;
import com.androidsrc.futbolin.database.models.TeamDB;
import com.androidsrc.futbolin.database.models.User;
import com.androidsrc.futbolin.database.models.currentScreen;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.dao.ForeignCollection;

import android.content.Context;
import android.util.Log;



public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper helper = null;


    public static void init(Context ctx) {

        if (null == instance) {
            instance = new DatabaseManager(ctx);

        }
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);

    }

    private DatabaseHelper getHelper() {

        return helper;
    }

    public void closeDB() {
        Log.e("closing HelperDB", "closing HelperDB");
        if (helper != null) {
            helper.close();
        }
    }

    /**
     * @return
     */

//SINCRO

    public CreateOrUpdateStatus saveUser(User au) {
        CreateOrUpdateStatus i = null;
        try {
            i = getHelper().getUserDao().createOrUpdate(au);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("register saved ok");
        return i;
    }
    public User findUser() {
        List<User> list = null;
        try {
            list = getHelper().getUserDao().queryBuilder()
                    .orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(list == null || list.size() == 0){
            return null;
        }else{
            return list.get(0);
        }

    }

    public boolean ExistsUser() {
        boolean exist = true;
        List<User> list = null;
        try {
            list = getHelper().getUserDao().queryBuilder()
                    .orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (list == null || list.isEmpty()) {
            exist = false;
        }
        return exist;
    }
    public boolean UserIslogged(){
        boolean logged = false;
        if(ExistsUser()){
            if(findUser().isLogged()){
                logged = true;
            }
        }
        return logged;
    }
    public void removeUser() {
        User p = findUser();

        if (p != null) {
            try {
                getHelper().getUserDao().delete(p);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Strategy
    public long saveStrategy(Strategy strategy){

        try {
            ForeignCollection<Position> positionORM = helper.getStrategyDao().getEmptyForeignCollection("positionORM");
            strategy.setPositionsORM(positionORM);

            Log.e("saveStrategy",strategy.getPositions().toString());
            if (strategy.getPositions().size() > 0) {
                for (Position position: strategy.getPositions()) {
                    removePosition(position);
                    position.setStrategy(strategy);
                    strategy.addPositionORM(position);

                }
            }
            getHelper().getStrategyDao().createOrUpdate(strategy);
        } catch (SQLException e) {e.printStackTrace();}

        return strategy.getId();
    }
    public void saveAllStrategies(List<Strategy> strategies){
        for(Strategy strategy : strategies){
            saveStrategy(strategy);
        }
    }

    public Strategy findStrategyById(long id) {
        Strategy strategy = null;
        try {
            strategy =  getHelper().getStrategyDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return strategy;


    }
    public List<Strategy> findAllStrategies() {
        List<Strategy> list = null;
        try {
            list = getHelper().getStrategyDao().queryBuilder()
                    .orderBy("id", true).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(list == null || list.size() == 0){
            return new ArrayList<>();
        }else{
            return list;
        }
    }
    public void removeStrategy(Strategy strategy) {
        if (strategy != null) {
            try {
                getHelper().getStrategyDao().delete(strategy);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    // POSITION

    public CreateOrUpdateStatus savePosition(Position au) {
        CreateOrUpdateStatus i = null;
        try {
            i = getHelper().getPositionDao().createOrUpdate(au);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return i;
    }
    public void saveAllPositions(List<Position> au) {
        for(Position p : au){
            savePosition(p);
        }
    }
    public Position findPositionById(long id) {
        Position position = null;
        try {
            position =  getHelper().getPositionDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return position;


    }
    public List<Position> findAllPositions() {
        List<Position> list = null;
        try {
            list = getHelper().getPositionDao().queryBuilder()
                    .orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(list == null || list.size() == 0){
            return new ArrayList<>();
        }else{
            return list;
        }
    }
    public void removePosition(Position position) {
        if (position != null) {
            try {
                getHelper().getPositionDao().delete(position);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // NOTIFICATION
    public CreateOrUpdateStatus saveNotification(Notification au) {
        CreateOrUpdateStatus i = null;
        try {
            i = getHelper().getNotificationDao().createOrUpdate(au);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("register saved ok");
        return i;
    }
    public Notification findNotification() {
        List<Notification> list = null;
        try {
            list = getHelper().getNotificationDao().queryBuilder()
                    .orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(list == null || list.size() == 0){
            return null;
        }else{
            return list.get(0);
        }

    }

    public boolean ExistsNotification() {
        boolean exist = true;
        List<Notification> list = null;
        try {
            list = getHelper().getNotificationDao().queryBuilder()
                    .orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (list == null || list.isEmpty()) {
            exist = false;
        }
        return exist;
    }
    // CURRENT Screen

    public CreateOrUpdateStatus saveCurrentScreen(currentScreen au) {
        CreateOrUpdateStatus i = null;
        try {
            i = getHelper().getCurrentScreenDao().createOrUpdate(au);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("currentScreen saved ok");
        return i;
    }
    public currentScreen findCurrentScreen() {
        List<currentScreen> list = null;
        try {
            list = getHelper().getCurrentScreenDao().queryBuilder()
                    .orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(list == null || list.size() == 0){
            return null;
        }else{
            return list.get(0);
        }

    }

    public boolean ExistsCurrentScreen() {
        boolean exist = true;
        List<currentScreen> list = null;
        try {
            list = getHelper().getCurrentScreenDao().queryBuilder()
                    .orderBy("id", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (list == null || list.isEmpty()) {
            exist = false;
        }
        return exist;
    }

    //TeamDB
    public long saveTeamDB(TeamDB teamDB){
        try {

            getHelper().getTeamDBDao().createOrUpdate(teamDB);
        } catch (SQLException e) {e.printStackTrace();}

        return teamDB.getId();
    }
    public void saveAllTeamDB(List<TeamDB> teamDBS){
        for(TeamDB teamDB : teamDBS){
            saveTeamDB(teamDB);
        }
    }

    public TeamDB findTeamDBById(long id) {
        List<TeamDB> teamDBs = null;
        try {
            teamDBs =  getHelper().getTeamDBDao().
                    queryBuilder().where().eq("id",id).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(teamDBs == null){
            return null;
        }else{
            if(teamDBs.size() == 0){
                return null;
            }else{
                return teamDBs.get(0);
            }
        }
    }
    public List<TeamDB> findAllTeamDBs() {
        List<TeamDB> list = null;
        try {
            list = getHelper().getTeamDBDao().queryBuilder()
                    .orderBy("id", true).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(list == null || list.size() == 0){
            return new ArrayList<>();
        }else{
            return list;
        }
    }
    public void removeTeamDB(TeamDB teamDB) {
        if (teamDB != null) {
            try {
                getHelper().getTeamDBDao().delete(teamDB);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /*
    MARKET NOTIFICATIONS AND MESSAGE
     */

    public MarketNotification findMarketNotificationByFollowPlayerId(long followPlayerId){
        List<MarketNotification> marketNotifications = null;
        try{
         marketNotifications = getHelper().getMarketNotificationDao().
                 queryBuilder().where().eq("followplayerid", followPlayerId).query();
        }catch (SQLException e){e.printStackTrace();}

        if(marketNotifications != null){
            if(!marketNotifications.isEmpty()){
                return marketNotifications.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    public MarketNotification findMarketNotificationByOfferPlayerId(long offerPlayerId){
        List<MarketNotification> marketNotifications = null;
        try{
            marketNotifications = getHelper().getMarketNotificationDao().
                    queryBuilder().where().eq("offerplayerid", offerPlayerId).query();
        }catch (SQLException e){e.printStackTrace();}

        if(marketNotifications != null){
            if(!marketNotifications.isEmpty()){
                return marketNotifications.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    public MarketNotification findMarketNotificationByMessageId(long messageId){
        List<MarketNotification> marketNotifications = null;
        try{
            marketNotifications = getHelper().getMarketNotificationDao().
                    queryBuilder().where().eq("messageid", messageId).query();
        }catch (SQLException e){e.printStackTrace();}

        if(marketNotifications != null){
            if(!marketNotifications.isEmpty()){
                return marketNotifications.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    public void saveMarketNotification(MarketNotification marketNotification){
        try{
            getHelper().getMarketNotificationDao().createOrUpdate(marketNotification);
        }catch (SQLException e){e.printStackTrace();}

    }

    /*
                PLAYER
     */

    public Player findPlayerById(long id){
        List<Player> players = null;
        try{
            players = getHelper().getPlayerDao().
                    queryBuilder().where().eq("id", id).query();
        }catch (SQLException e){e.printStackTrace();}

        if(players != null){
            if(!players.isEmpty()){
                return players.get(0);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    public void savePlayer(Player player){
        try{
            getHelper().getPlayerDao().createOrUpdate(player);
        }catch (SQLException e){e.printStackTrace();}

    }
}

