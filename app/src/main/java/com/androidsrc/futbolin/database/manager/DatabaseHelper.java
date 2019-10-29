package com.androidsrc.futbolin.database.manager;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.androidsrc.futbolin.database.models.MarketNotification;
import com.androidsrc.futbolin.database.models.Notification;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.Position;
import com.androidsrc.futbolin.database.models.Strategy;
import com.androidsrc.futbolin.database.models.TeamDB;
import com.androidsrc.futbolin.database.models.User;
import com.androidsrc.futbolin.database.models.currentScreen;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;



public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "futbolin.db";
    private static final int DATABASE_VERSION = 36;

    private Dao<User, Long> userDao = null;
    private Dao<Strategy, Long> strategyDao = null;
    private Dao<Position, Long> positionDao = null;
    private Dao<Notification, Long> notificationDao = null;
    private Dao<currentScreen, Long> currentScreenDao = null;
    private Dao<TeamDB, Long> TeamDBDao = null;
    private Dao<MarketNotification, Long> marketNotificationDao = null;
    private Dao<Player, Long> playerDao = null;




    /**
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *
     */

    public void onCreate(SQLiteDatabase database,
                         ConnectionSource connectionSource) {
        try {

            TableUtils.createTableIfNotExists(connectionSource, User.class );
            TableUtils.createTableIfNotExists(connectionSource, Strategy.class );
            TableUtils.createTableIfNotExists(connectionSource, Position.class );
            TableUtils.createTableIfNotExists(connectionSource, Notification.class );
            TableUtils.createTableIfNotExists(connectionSource, currentScreen.class );
            TableUtils.createTableIfNotExists(connectionSource, TeamDB.class );
            TableUtils.createTableIfNotExists(connectionSource, MarketNotification.class );
            TableUtils.createTableIfNotExists(connectionSource, Player.class );


        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database!!!!!!!!!", e);
            throw new RuntimeException(e);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    /**
     *
     */

    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            try{
                TableUtils.dropTable(connectionSource, Notification.class, true);
            } catch (java.sql.SQLException e) {e.printStackTrace();}
        /*      try {
              TableUtils.dropTable(connectionSource, User.class, true);
                TableUtils.dropTable(connectionSource, Strategy.class, true);
                TableUtils.dropTable(connectionSource, Position.class, true);
                TableUtils.dropTable(connectionSource, Notification.class, true);
                TableUtils.dropTable(connectionSource, currentScreen.class, true);
                TableUtils.dropTable(connectionSource, TeamDB.class, true);
                TableUtils.dropTable(connectionSource, MarketNotification.class, true);


            } catch (java.sql.SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            */
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }

    }

    /**
     *
     * @return
     */
    public Dao<TeamDB, Long> getTeamDBDao() {
        if (null == TeamDBDao) {
            try {
                TeamDBDao = getDao(TeamDB.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return TeamDBDao;
    }
    public Dao<currentScreen, Long> getCurrentScreenDao() {
        if (null == currentScreenDao) {
            try {
                currentScreenDao = getDao(currentScreen.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return currentScreenDao;
    }
    public Dao<Notification, Long> getNotificationDao() {
        if (null == notificationDao) {
            try {
                notificationDao = getDao(Notification.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return notificationDao;
    }

    public Dao<User, Long> getUserDao() {
        if (null == userDao) {
            try {
                userDao = getDao(User.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return userDao;
    }

    public Dao<Strategy, Long> getStrategyDao() {
        if (null == strategyDao) {
            try {
                strategyDao = getDao(Strategy.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return strategyDao;
    }
    public Dao<Position, Long> getPositionDao() {
        if (null == positionDao) {
            try {
                positionDao = getDao(Position.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return positionDao;
    }
    public Dao<MarketNotification, Long> getMarketNotificationDao() {
        if (null == marketNotificationDao) {
            try {
                marketNotificationDao = getDao(MarketNotification.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return marketNotificationDao;
    }
    public Dao<Player, Long> getPlayerDao() {
        if (null == playerDao) {
            try {
                playerDao = getDao(Player.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return playerDao;
    }
    @Override
    public void close() {
        super.close();
        userDao = null;
        strategyDao = null;
        positionDao = null;
        notificationDao = null;
        currentScreenDao = null;
        TeamDBDao = null;
        marketNotificationDao = null;
        playerDao = null;
    }


}

