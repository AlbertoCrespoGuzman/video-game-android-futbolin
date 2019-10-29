package com.androidsrc.futbolin.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.view.GravityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidsrc.futbolin.R;
import com.androidsrc.futbolin.activities.fragments.FragmentFriendlies;
import com.androidsrc.futbolin.activities.fragments.FragmentLive;
import com.androidsrc.futbolin.activities.fragments.FragmentLockerRoom;
import com.androidsrc.futbolin.activities.fragments.FragmentMarket;
import com.androidsrc.futbolin.activities.fragments.FragmentMarketFollowing;
import com.androidsrc.futbolin.activities.fragments.FragmentMarketOffers;
import com.androidsrc.futbolin.activities.fragments.FragmentMarketTransactions;
import com.androidsrc.futbolin.activities.fragments.FragmentMeTransactions;
import com.androidsrc.futbolin.activities.fragments.FragmentPlayer;
import com.androidsrc.futbolin.activities.fragments.FragmentPlayers;
import com.androidsrc.futbolin.activities.fragments.FragmentSettings;
import com.androidsrc.futbolin.activities.fragments.FragmentShopping;
import com.androidsrc.futbolin.activities.fragments.FragmentShowTeam;
import com.androidsrc.futbolin.activities.fragments.FragmentStrategy;
import com.androidsrc.futbolin.activities.fragments.FragmentTeam;
import com.androidsrc.futbolin.activities.fragments.FragmentTeamEdit;
import com.androidsrc.futbolin.activities.fragments.FragmentTournament;
import com.androidsrc.futbolin.communications.Svc;
import com.androidsrc.futbolin.communications.SvcApi;
import com.androidsrc.futbolin.communications.http.auth.AndroiduserLoginResponse;
import com.androidsrc.futbolin.communications.http.auth.get.getNotifications;
import com.androidsrc.futbolin.communications.http.auth.get.getStrategies;
import com.androidsrc.futbolin.communications.http.auth.get.getTrain;
import com.androidsrc.futbolin.communications.http.auth.get.getUser;
import com.androidsrc.futbolin.communications.http.auth.post.PostShoppingBuy;
import com.androidsrc.futbolin.communications.http.auth.post.PostTrain;
import com.androidsrc.futbolin.database.manager.DatabaseManager;
import com.androidsrc.futbolin.database.models.Notification;
import com.androidsrc.futbolin.database.models.Player;
import com.androidsrc.futbolin.database.models.SystemNotification;
import com.androidsrc.futbolin.database.models.SystemSubNotifications;
import com.androidsrc.futbolin.database.models.User;
import com.androidsrc.futbolin.database.models.currentScreen;
import com.androidsrc.futbolin.services.MarketNotificationService;
import com.androidsrc.futbolin.services.TrainNotificationsService;
import com.androidsrc.futbolin.utils.Defaultdata;
import com.androidsrc.futbolin.utils.LocaleLanguageHelper;
import com.androidsrc.futbolin.utils.dialogs.MessageDialog;
import com.androidsrc.futbolin.utils.dialogs.MessagePlayerAnchorTagDialog;
import com.androidsrc.futbolin.utils.dialogs.NotificationsAllDialog;
import com.androidsrc.futbolin.utils.dialogs.NotificationsDialog;
import com.androidsrc.futbolin.utils.dialogs.PlayersNotificationDialog;
import com.androidsrc.futbolin.utils.dialogs.TwoButtonsDialog;
import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;
import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener, FragmentFriendlies.OnFragmentInteractionListener,
		FragmentTeamEdit.OnFragmentInteractionListener,
		FragmentMeTransactions.OnFragmentInteractionListener,
		FragmentMarketTransactions.OnFragmentInteractionListener,
		MessagePlayerAnchorTagDialog.DialogPlayerAnchorTagClickListener,
		PlayersNotificationDialog.DialogPlayersNotificationClickListener,
		FragmentMarketFollowing.OnFragmentInteractionListener,
		FragmentMarketOffers.OnFragmentInteractionListener,
		NotificationsDialog.DialogClickListener,NotificationsAllDialog.DialogClickListener,
		FragmentLockerRoom.OnFragmentInteractionListener,FragmentPlayers.OnFragmentInteractionListener,
		FragmentStrategy.OnFragmentInteractionListener,FragmentTeam.OnFragmentInteractionListener,
		FragmentShowTeam.OnFragmentInteractionListener, FragmentLive.OnFragmentInteractionListener,
		FragmentMarket.OnFragmentInteractionListener,FragmentSettings.OnFragmentInteractionListener,
		TwoButtonsDialog.DialogTwoButtonsClickListener,
		FragmentTournament.OnFragmentInteractionListener, FragmentShopping.OnFragmentInteractionListener, FragmentPlayer.OnFragmentInteractionListener,MessageDialog.DialogClickListener{

	TwoButtonsDialog trainmentRachaDialog;
	DrawerLayout drawer;
	ImageButton toggleDrawerButton, gameMessageButton, playersMessageButton, trainmentButton;
	View view;
	ActionBar actionBar;
	TextView trainningText;
	long dateToTrain = 0;
    com.androidsrc.futbolin.communications.http.auth.get.getUser user = null;
	DatabaseManager db;
	SvcApi svc;
	Bundle savedInstanceState;
	MessageDialog messageDialog;
	MainActivity  mainActivity;
	TextView navHeaderTeamName;
	TextView navHeaderEmail;
	Thread timerTrainningThread = null;
	ImageView iconHeaderImage;
	TextView teamName;
	TextView sectionName, sectionIncomes;
	ImageView sectionIcon;
	FragmentManager fragmentManager;
	ProgressDialog dialog;
	String lastSectionName = "";
	boolean changingSection;
	String fragmentNameSection;
	Fragment fragment;
	boolean live = false;
	public static long TrainningInterval = (1000 * 60 * 60 * 24);
	//public static long TrainningInterval = (1000 * 60 * 5);
	int backButtonLockerRoom = 1;
	Guideline guidleLineFragmentHeader;
	RelativeLayout  trainmentRelativeButton, menuSidebarRelativeButton;
	ImageView messagesButton, playersNotificationButton;
	NotificationsDialog notificationsDialog;
	NotificationsAllDialog notificationsAllDialog;
	PlayersNotificationDialog playersNotificationDialog;
	MessagePlayerAnchorTagDialog messagePlayerAnchorTagDialog;
	RelativeLayout layoutMessagesNotification, layoutPlayersNotification;
	ConstraintLayout barLayout;
	String languageChanged = null;

	static {
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	//	getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


		this.savedInstanceState = savedInstanceState;
		mainActivity = this;

		db =  new DatabaseManager(getApplicationContext());
		Log.e("MainActi", "antes de entrar");
		if(db!= null && db.findNotification() != null && db.findNotification().getLanguage() != null && db.findNotification().getLanguage().length() >=2){
			LocaleLanguageHelper.setLocale(getApplicationContext(), db.findNotification().getLanguage());
			Log.e("MainActi", "changing language!!! ->" + db.findNotification().getLanguage());
		}


		try{
            live =  getIntent().getExtras().getBoolean("live", false);
		    user = (com.androidsrc.futbolin.communications.http.auth.get.getUser) getIntent().getExtras().getSerializable("user");

		}catch(Exception e){

		}

	//	setMarketNotificationsAlarm();

		setContentView(R.layout.activity_main);

		actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);


		actionBar.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.MATCH_PARENT);



		view = getLayoutInflater().inflate(R.layout.layout_tool_bar_header, null);

		actionBar.setCustomView(view, layoutParams);
		Toolbar parent =(Toolbar) view.getParent();
		parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
		parent.setContentInsetsAbsolute(0,0);


		barLayout = findViewById(R.id.app_bar_main_layout);
		trainmentRelativeButton = findViewById(R.id.tool_bar_header_train);
		menuSidebarRelativeButton = findViewById(R.id.tool_bar_drawer_relative);

		playersNotificationButton = findViewById(R.id.toolbar_players_message);
		messagesButton = findViewById(R.id.toolbar_game_message);
		trainmentButton = findViewById(R.id.toolbartrainment);

		trainningText = findViewById(R.id.toolbar_trainning_time);

		sectionName = findViewById(R.id.fragment_header_title);
		sectionIncomes = findViewById(R.id.fragment_header_incomes);
		sectionIcon = findViewById(R.id.fragment_section_image);
		guidleLineFragmentHeader = findViewById(R.id.fragment_header_guideline_bar_main);
		layoutMessagesNotification = findViewById(R.id.tool_bar_header_messages);
		layoutPlayersNotification = findViewById(R.id.tool_bar_header_players);



		//It is required to recreate the activity to reflect the change in UI.
		//recreate();

	}

	@Override
	public void onResume() {
		super.onResume();

		svc = Svc.initAuth(getApplicationContext());
		db = new DatabaseManager(getApplicationContext());


		if(db.findCurrentScreen() == null){
			currentScreen currentScreen = new currentScreen();
			currentScreen.setFragmentName("");
			db.saveCurrentScreen(currentScreen);
		}
		Log.e("onResume", "onResume user is null? " + (user == null));
		if (fragmentManager == null) {
			fragmentManager = getSupportFragmentManager();

		}
		if (user == null && !live) {
			getMe();

		} else if (live) {
			changeToLiveFragment();
		}else if(db.findAllStrategies() ==  null || db.findAllStrategies().isEmpty()){
			getStrategies();
		}else{
		//	updateTrainningNotifications(0);
			buildDrawerNavigation();
			loadFragment();
		}
		if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
			guidleLineFragmentHeader.setGuidelinePercent((float)0.1);
		}else{
			guidleLineFragmentHeader.setGuidelinePercent((float)0.2);
			Log.e("ey 0.5", "e");
		}
	}
	@Override
	public void onPause() {
		super.onPause();
		Log.e("onPause","onPause!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if(getSupportFragmentManager().getPrimaryNavigationFragment() != null){
			System.out.println("pause getSupportFragmentManager().getPrimaryNavigationFragment().getClass() = " + getSupportFragmentManager().getPrimaryNavigationFragment().getClass());
		}

		user = null;
		if(timerTrainningThread != null && timerTrainningThread.isAlive()){
			timerTrainningThread.interrupt();
		}
		currentScreen currentScreen;
		if(db.findCurrentScreen() != null){
			currentScreen = db.findCurrentScreen();
		}else{
			currentScreen = new currentScreen();
		}
		currentScreen.setFragmentName(fragmentNameSection);
		db.saveCurrentScreen(currentScreen);

	}

	@Override
	public void onBackPressed() {
        Log.e("MainActivity", "onBackPressed !!!!!!!!!!!!!!!!!!!!!!!!!!");
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			//super.onBackPressed();
		}
		Fragment fragment1,fragment2, fragment3,fragment4,fragment5,fragment6,fragment7,fragment8,fragment9,
				fragment10,fragment11,fragment12, fragment13, fragment14, fragment15, fragment16,
		fragment17, fragment18,fragment19,fragment20,fragment21, fragment22, fragment23,fragment24,
				fragment25,fragment26,fragment27, fragment28;

		if(fragmentManager != null){
			fragment1 = fragmentManager.findFragmentByTag("FragmentFriendlies");
			fragment2 = fragmentManager.findFragmentByTag("FragmentMarket");
			fragment3 = fragmentManager.findFragmentByTag("FragmentPlayer");
			fragment4 = fragmentManager.findFragmentByTag("FragmentPlayers");
			fragment5 = fragmentManager.findFragmentByTag("FragmentSettings");
			fragment6 = fragmentManager.findFragmentByTag("FragmentShopping");
			fragment7 = fragmentManager.findFragmentByTag("FragmentShowTeam");
			fragment8 = fragmentManager.findFragmentByTag("FragmentStrategy");
			fragment9 = fragmentManager.findFragmentByTag("FragmentTeam");
			fragment10 = fragmentManager.findFragmentByTag("FragmentTeamEdit");
			fragment11 = fragmentManager.findFragmentByTag("FragmentTournament");
			fragment12 = fragmentManager.findFragmentByTag("FragmentLockerRoom");
			fragment13 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentFriendlies");
			fragment14 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentLockerRoom");
			fragment15 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentTournament");
			fragment16 = fragmentManager.findFragmentByTag("FragmentMarketTransactions_1");
			fragment17 = fragmentManager.findFragmentByTag("FragmentMarketTransactions_2");
			fragment18 = fragmentManager.findFragmentByTag("FragmentMarketTransactions_3");
			fragment19 = fragmentManager.findFragmentByTag("FragmentMarketTransactions_4");
			fragment20 = fragmentManager.findFragmentByTag("FragmentMarketTransactions_5");
			fragment21 = fragmentManager.findFragmentByTag("FragmentMarketTransactions_6");
			fragment22 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentMarketTransactions_1");
			fragment23 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentMarketTransactions_2");
			fragment24 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentMarketTransactions_3");
			fragment25 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentMarketTransactions_4");
			fragment26 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentMarketTransactions_5");
			fragment27 = fragmentManager.findFragmentByTag("FragmentShowTeamFragmentMarketTransactions_6");
			fragment28 = fragmentManager.findFragmentByTag("FragmentMarketFollowing");
			if(fragment1 != null && fragment1.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment2 != null && fragment2.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment3 != null && fragment3.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment4 != null && fragment4.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment5 != null && fragment5.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment6 != null && fragment6.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment7 != null && fragment7.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment8 != null && fragment8.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment9 != null && fragment9.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment10 != null && fragment10.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment11 != null && fragment11.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}else if(fragment12 != null && fragment12.isVisible()){
				backButtonLockerRoom --;
				if(backButtonLockerRoom == 0){
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.main_activity_back_button), Toast.LENGTH_SHORT).show();
				}else{
					super.onBackPressed();
				}
			}else if(fragment13 != null && fragment13.isVisible()){
				changeToFriendliesFragment();
				backButtonLockerRoom = 1;
			}
			else if(fragment14 != null && fragment14.isVisible()){
				changeToLockerRoom();
				backButtonLockerRoom = 1;
			}
			else if(fragment15 != null && fragment15.isVisible()){
				changeToTournamentFragment();
				backButtonLockerRoom = 1;
			}else if(fragment16 != null && fragment16.isVisible()){
				changeToMarketFragment();
				backButtonLockerRoom = 1;
			}else if(fragment17 != null && fragment17.isVisible()){
				changeToMarketFragment();
				backButtonLockerRoom = 1;
			}else if(fragment18 != null && fragment18.isVisible()){
				changeToMarketFragment();
				backButtonLockerRoom = 1;
			}else if(fragment19 != null && fragment19.isVisible()){
				changeToMarketFragment();
				backButtonLockerRoom = 1;
			}else if(fragment20 != null && fragment20.isVisible()){
				changeToMarketFragment();
				backButtonLockerRoom = 1;
			}else if(fragment21 != null && fragment21.isVisible()){
				changeToMarketFragment();
				backButtonLockerRoom = 1;
			}else if(fragment22 != null && fragment22.isVisible()){
				changeToMarketTransactions(1);
				backButtonLockerRoom = 1;
			}else if(fragment23 != null && fragment23.isVisible()){
				changeToMarketTransactions(1);
				backButtonLockerRoom = 1;
			}else if(fragment24 != null && fragment24.isVisible()){
				changeToMarketTransactions(2);
				backButtonLockerRoom = 1;
			}else if(fragment25 != null && fragment25.isVisible()){
				changeToMarketTransactions(3);
				backButtonLockerRoom = 1;
			}else if(fragment26 != null && fragment26.isVisible()){
				changeToMarketTransactions(4);
				backButtonLockerRoom = 1;
			}else if(fragment27 != null && fragment27.isVisible()){
				changeToMarketTransactions(6);
				backButtonLockerRoom = 1;
			}else if(fragment28 != null && fragment28.isVisible()){
				changeToMarketFragment();
				backButtonLockerRoom = 1;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.e("onCreateOptionsMenu", "onCreateOptionsMenu");
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.e("onOptionsItemSelected", "onOptionsItemSelected");

		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		Log.e("onNavigatio", "onNavigationItemSelected");
		boolean isLogginOut = false;

		int id = item.getItemId();
		fragment = null;
		if(!live) {
			if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			if (id == R.id.nav_locker_room) {
				fragmentNameSection = "FragmentLockerRoom";
				fragment = FragmentLockerRoom.newInstance(user, "");
				sectionName.setText(getResources().getString(R.string.locker_room_title));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_home, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
				sectionIcon.invalidate();

			} else if (id == R.id.nav_team) {
				fragmentNameSection = "FragmentTeam";
				fragment = FragmentTeam.newInstance(user, "");
				sectionName.setText(user.getUser().getTeam().getName());
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			}else if (id == R.id.nav_me_transactions) {
				fragmentNameSection = "FragmentMeTransactions";
				fragment = FragmentMeTransactions.newInstance(user, "");
				sectionName.setText(getResources().getString(R.string.finances));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_money, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			}
			else if (id == R.id.nav_players) {
				fragment = FragmentPlayers.newInstance(user, 0);
				sectionName.setText(getResources().getString(R.string.players));
				fragmentNameSection = "FragmentPlayers";
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_users, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			} else if (id == R.id.nav_market) {
				fragment = FragmentMarket.newInstance(user, 0);
				sectionName.setText(getResources().getString(R.string.market_title));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				fragmentNameSection = "FragmentMarket";
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			} else if (id == R.id.nav_strategy) {
				fragmentNameSection = "FragmentStrategy";
				fragment = FragmentStrategy.newInstance(user, "");
				sectionName.setText(getResources().getString(R.string.strategy));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_cogs, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			} else if (id == R.id.nav_friendlies) {
				fragmentNameSection = "FragmentFriendlies";
				fragment = new FragmentFriendlies();
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				bundle.putSerializable("mparam2", "");
				fragment.setArguments(bundle);
				sectionName.setText(getResources().getString(R.string.friendlies));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_handshake, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			} else if (id == R.id.nav_tournament) {
				fragmentNameSection = "FragmentTournament";
				fragment = FragmentTournament.newInstance(user, "");
				sectionName.setText(getResources().getString(R.string.tournaments));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_trophy, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			} else if (id == R.id.nav_shopping) {
				fragmentNameSection = "FragmentShopping";
				fragment = FragmentShopping.newInstance(user, "");
				sectionName.setText(getResources().getString(R.string.shopping));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_shopping_cart, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			}else if (id == R.id.nav_settings) {
				fragmentNameSection = "FragmentSettings";
				fragment = FragmentSettings.newInstance(user, "");
				sectionName.setText(getResources().getString(R.string.settings));
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_mobile_alt, sectionIcon);
				VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
				path.setFillColor(Color.parseColor("#3b6c8e"));
			} else if (id == R.id.nav_logout) {
				postLogout();
				isLogginOut = true;
			}


			if (!isLogginOut) {
				if (fragmentManager == null) {
					fragmentManager = getSupportFragmentManager();
				}

				changingSection = true;

				fragmentManager.beginTransaction().replace(R.id.flContent, fragment, fragmentNameSection).commit();

				DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
				drawer.closeDrawer(GravityCompat.START);



			}
		}

		return true;
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
        Log.e("MainActivity", "onFragmentInteraction !!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
	void setTrainment(boolean hasTrainnedNow){
		if(timerTrainningThread != null && timerTrainningThread.isAlive()){
			timerTrainningThread.interrupt();
		}
		Log.e("Has trained NOw???",hasTrainnedNow + "");
		Log.e("getLast_trainning()","user.getUser().getTeam().getLast_trainning() " + user.getUser().getTeam().getLast_trainning());
		Date date = null;
		TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(timeZone);
		final long timeNow = calendar.getTimeInMillis();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if(user.getUser().getTeam().getLast_trainning() != null && !hasTrainnedNow){


			try {
				sdf.setTimeZone(timeZone);
				date = sdf.parse(user.getUser().getTeam().getLast_trainning());
				Calendar calendar2 = new GregorianCalendar();

				calendar2.setTimeZone(timeZone);
				calendar2.setTime(sdf.parse(user.getUser().getTeam().getLast_trainning()));
				calendar2.setTimeZone(timeZone);
			}catch (Exception e){
				e.printStackTrace();
			}
			dateToTrain = date.getTime() + TrainningInterval - timeNow;

		}
		Date date2 = null;
		boolean hasPersonalTrainer = false;

		if(user.getUser().getTeam().getTrainer() != null) {
			try {
				sdf.setTimeZone(timeZone);
				date2 = sdf.parse(user.getUser().getTeam().getTrainer());
				if(date2.getTime() - timeNow > 0){
					hasPersonalTrainer = true;
					Log.e("hasPersonalTrainer","hasPersonalTrainer ->" + hasPersonalTrainer);
					updateTrainningNotifications(date2.getTime() - timeNow);
				}
			} catch (Exception e) {

			}
		}


		if(user.getUser().getTeam().getLast_trainning() == null && !hasTrainnedNow){

			if(!hasPersonalTrainer) {
				trainmentButton.setImageResource(R.drawable.ic_follow_boot);
				trainningText.setText(getResources().getString(R.string.train_title));
				trainmentButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (user.getUser().getTeam().getLast_trainning() == null || timeNow > dateToTrain) {
							postTrain(false);
						} else {

						}
					}
				});
				trainmentRelativeButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if (user.getUser().getTeam().getLast_trainning() == null || timeNow > dateToTrain) {
							postTrain(false);
						} else {

						}
					}
				});
			}else{
				trainmentButton.setImageResource(R.drawable.ic_star_half_alt_solid);
				trainningText.setText(getResources().getString(R.string.personal_trainer));
			}
		}else if(0 <  dateToTrain){
			if(!hasPersonalTrainer){
				trainmentButton.setImageResource(R.drawable.ic_follow_boot_empty);
				trainmentButton.setBackgroundColor(android.graphics.Color.TRANSPARENT);
				Runnable runnable = new CountDownRunner();
				timerTrainningThread = new Thread(runnable);
				timerTrainningThread.start();
			}else{
				trainmentButton.setImageResource(R.drawable.ic_star_half_alt_solid);
				trainningText.setText(getResources().getString(R.string.personal_trainer));
			}
		}else{
			if(!hasPersonalTrainer) {
				if(!hasTrainnedNow) {
					trainmentButton.setImageResource(R.drawable.ic_follow_boot);
					trainningText.setText(getResources().getString(R.string.train_title));
					trainmentButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (user.getUser().getTeam().getLast_trainning() == null || timeNow > dateToTrain) {
								postTrain(false);
							} else {

							}
						}
					});
					trainmentRelativeButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							if (user.getUser().getTeam().getLast_trainning() == null || timeNow > dateToTrain) {
								postTrain(false);
							} else {

							}
						}
					});
				}else{
					trainmentButton.setImageResource(R.drawable.ic_follow_boot_empty);
					trainmentButton.setBackgroundColor(android.graphics.Color.TRANSPARENT);
					Runnable runnable = new CountDownRunner();
					timerTrainningThread = new Thread(runnable);
					timerTrainningThread.start();
				}
			}else{
				trainmentButton.setImageResource(R.drawable.ic_star_half_alt_solid);
				trainningText.setText(getResources().getString(R.string.personal_trainer));
			}
		}


	}
	public void doWork() {
		runOnUiThread(new Runnable() {
			public void run() {
				try{


					int seconds = (int) (dateToTrain / 1000) % 60 ;
					int minutes = (int) ((dateToTrain / (1000*60)) % 60);
					int hours   = (int) ((dateToTrain / (1000*60*60)) % 24);
					String curTime = (hours < 10 ? "0"+hours : hours)+ ":" +
							(minutes < 10 ? "0"+minutes : minutes) + ":" + (seconds < 10 ? "0"+seconds : seconds);

					trainningText.setText(curTime);
					if(hours < 0 || minutes < 0 || seconds < 0){
						trainningText.setText(getResources().getString(R.string.train_title));
					}
					dateToTrain -= 1000;
					if(dateToTrain < 0){
						timerTrainningThread.interrupt();
						timerTrainningThread = null;
						trainningText.setText(getResources().getString(R.string.train_title));
						trainmentButton.setImageResource(R.drawable.ic_follow_boot);

						trainmentButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								if(user.getUser().getTeam().getLast_trainning() == null || (new Date()).getTime() > dateToTrain){
									postTrain(false);
								}else{

								}
							}
						});
						trainmentRelativeButton.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								if(user.getUser().getTeam().getLast_trainning() == null || (new Date()).getTime() > dateToTrain){
									postTrain(false);
								}else{

								}
							}
						});
					}
				}catch (Exception e) {}
			}
		});
	}

	@Override
	public void onMessageDialogButtonClick() {
		messageDialog.dismiss();
		startActivity(new Intent(MainActivity.this, MainActivity.class));
		finish();
	}

	@Override
	public void onDialogNotificationsButtonClick(SystemNotification notification, boolean getAllNotifications) {
		if(notificationsDialog != null && notificationsDialog.isShowing()){
			notificationsDialog.dismiss();
		}
		user.setNotifications(notification);
		setNotifications();
		if(getAllNotifications){
		    getAllNotifications();
        }
	}

	@Override
	public void onDialogNotificationsAllButtonClick(List<SystemSubNotifications> notifications) {
		if(notificationsAllDialog != null && notificationsAllDialog.isShowing()){
			notificationsAllDialog.dismiss();
		}
		int unread = 0;
		for(SystemSubNotifications systemSubNotifications : notifications){
			if(systemSubNotifications.isUnread()){
				unread++;
			}
		}
		user.getNotifications().setUnread(unread);
		user.getNotifications().setNotifications(notifications);
		setNotifications();
	}

	@Override
	public void onButtonPlayersNotificationClick() {
		if(playersNotificationDialog != null && playersNotificationDialog.isShowing()){
			playersNotificationDialog.dismiss();
		}
	}

	@Override
	public void onButtonPlayerAnchorTagClick() {
		if(messagePlayerAnchorTagDialog != null && messagePlayerAnchorTagDialog.isShowing()){
			messagePlayerAnchorTagDialog.dismiss();
		}
	}

	@Override
	public void onButtonClick(boolean confirm, int type, Object extra) {

		if(confirm){
			if(trainmentRachaDialog != null && trainmentRachaDialog.isShowing()){
				trainmentRachaDialog.dismiss();
			}
			if(user.getUser().getCredits() > 0) {

				postShoppingBuyTrainRacha();
			}else{
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_enough_fulbos), Toast.LENGTH_SHORT).show();
			}

		}else{
			if(trainmentRachaDialog != null && trainmentRachaDialog.isShowing()){
				trainmentRachaDialog.dismiss();
				postTrain(true);
			}

		}
	}


	class CountDownRunner implements Runnable{
		// @Override
		public void run() {
			while(!Thread.currentThread().isInterrupted()){
				try {
					doWork();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}catch(Exception e){
				}
			}
		}
	}
	public void getMe(){
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setTitle(getResources().getString(R.string.wait));
		dialog.setMessage(getResources().getString(R.string.loading_team_data));
		dialog.show();

		svc = Svc.initAuth(getApplicationContext());
		Call<com.androidsrc.futbolin.communications.http.auth.get.getUser> call = svc.getMe(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
		call.enqueue(new Callback<com.androidsrc.futbolin.communications.http.auth.get.getUser>() {
			@Override
			public void onResponse(Call<com.androidsrc.futbolin.communications.http.auth.get.getUser> call, Response<com.androidsrc.futbolin.communications.http.auth.get.getUser> response) {
				final com.androidsrc.futbolin.communications.http.auth.get.getUser user = response.body();
				final Response<com.androidsrc.futbolin.communications.http.auth.get.getUser> responsefinal = response;

				if(dialog != null && dialog.isShowing()){
					dialog.dismiss();
					dialog = null;
				}

				if (response.isSuccessful()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Log.e("getMe","Success, getMe = " + responsefinal);
							if(responsefinal.code() == 400){

							}else if(responsefinal.code() == 201 || responsefinal.code() == 200){
								MainActivity.this.user = user;
								Log.e("getme", MainActivity.this.user.getUser().toString());
								updateTrainningNotifications(0);
								if(db.findAllStrategies() == null || db.findAllStrategies().isEmpty()){
									getStrategies();
								}else{
									buildDrawerNavigation();
									loadFragment();
									checkIfHasToChangeFragment();
								}

							}
						}
					});
				} else {
					Log.e("ERror","get me error");
					try {
						String error = response.errorBody().string();
						Log.e("ERror","get me error = " + error);
						if(error.contains("live_match") && error.contains("Broadcasting live match")){
							live = true;
							changeToLiveFragment();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(responsefinal.code() == 500|| responsefinal.code() == 503){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call<com.androidsrc.futbolin.communications.http.auth.get.getUser> call, Throwable t) {
				t.printStackTrace();
			}
		});

	}
	void checkIfHasToChangeFragment(){
		Log.e("CHANGE", "checkIfHasToChangeFragment");
		try {
			if (getIntent() != null && getIntent().getExtras() != null) {
				int fragment = getIntent().getExtras().getInt("changeactivity", 0);

				if (fragment == Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_OFFER) {
					changeToMarketOffers();
				} else if (fragment == Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_FOLLOWING) {
					changeToMarketFollowing();
				} else if (fragment == Defaultdata.MAINACTIVITY_CHANGE_TO_MESSAGES_DIALOG) {
					notificationsDialog = new NotificationsDialog(MainActivity.this, user.getNotifications(), MainActivity.this);
					notificationsDialog.show();
				}else if(fragment == Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_DEFAULT){
					changeToMarketFragment();
					Log.e("CHANGING", "CHANGING TO MARKET !!!");
				}else if(fragment == Defaultdata.MAINACTIVITY_CHANGE_TOPLAYERS_DEFAULT){
					changeToFragmentPlayers();
					Log.e("CHANGING", "CHANGING TO PLAYERS !!!");
				}else if(fragment == Defaultdata.MAINACTIVITY_CHANGE_TO_MARKET_DEFAULT_PLAYER_DIALOG){
					changeToMarketFragmentPlayerDialog(getIntent().getLongExtra("player_id", 0));
					Log.e("CHANGING", "CHANGING TO MARKET DIALOG!!! ->>PLAYER_ID :" +getIntent().getLongExtra("player_id", 0));
				}else if(fragment == Defaultdata.MAINACTIVITY_CHANGE_TOPLAYERS_DEFAULT_PLAYER_DIALOG){
					changeToFragmentPlayersPlayerDialog(getIntent().getLongExtra("player_id", 0));
					Log.e("CHANGING", "CHANGING TO PLAYERS DIALOG!!! ->>PLAYER_ID :" +getIntent().getLongExtra("player_id", 0));
				}


			}
		}catch(Exception e){e.printStackTrace();}

	}
	void buildDrawerNavigation(){

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.setStatusBarBackgroundColor(android.graphics.Color.TRANSPARENT);



		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		View headerLayout = navigationView.getHeaderView(0);
		//headerLayout.setFitsSystemWindows(true);
		ImageView shieldImage = headerLayout.findViewById(R.id.nav_shield_image);

		Log.e("imageView","null? " + (shieldImage == null));
if(user != null && user.getUser()!= null && user.getUser().getTeam() != null) {
	VectorChildFinder vector = new VectorChildFinder(this, Defaultdata.shields.get(user.getUser().getTeam().getShield()), shieldImage);

	VectorDrawableCompat.VFullPath path21 = vector.findPathByName("secundary_color1");
	path21.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
	VectorDrawableCompat.VFullPath path22 = vector.findPathByName("secundary_color2");
	path22.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
	VectorDrawableCompat.VFullPath path1 = vector.findPathByName("primary_color");
	path1.setFillColor(Color.parseColor(user.getUser().getTeam().getPrimary_color()));
	shieldImage.invalidate();

}
		toggleDrawerButton = getSupportActionBar().getCustomView().findViewById(R.id.toolbar_drawer_button);
		toggleDrawerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (drawer.isDrawerOpen(GravityCompat.START)) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					drawer.openDrawer(Gravity.LEFT);
				}
			}
		});
		menuSidebarRelativeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (drawer.isDrawerOpen(GravityCompat.START)) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					drawer.openDrawer(Gravity.LEFT);
				}
			}
		});
		if(user != null && user.getUser() != null) {

			navHeaderTeamName = headerLayout.findViewById(R.id.nav_header_team_name);
			navHeaderEmail = headerLayout.findViewById(R.id.nav_header_email);
			navHeaderTeamName.setText(user.getUser().getTeam().getName());
			navHeaderEmail.setText(db.findUser().getEmail());

			ImageView shieldImageHeader = findViewById(R.id.fragment_header_shield_image);

			VectorChildFinder vectorHeader = new VectorChildFinder(getApplicationContext(), Defaultdata.shields.get(user.getUser().getTeam().getShield()), shieldImageHeader);

			VectorDrawableCompat.VFullPath path21Header = vectorHeader.findPathByName("secundary_color1");
			path21Header.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
			VectorDrawableCompat.VFullPath path22Header = vectorHeader.findPathByName("secundary_color2");
			path22Header.setFillColor(Color.parseColor(user.getUser().getTeam().getSecondary_color()));
			VectorDrawableCompat.VFullPath path1Header = vectorHeader.findPathByName("primary_color");
			path1Header.setFillColor(Color.parseColor(user.getUser().getTeam().getPrimary_color()));
			shieldImageHeader.invalidate();

			iconHeaderImage = findViewById(R.id.fragment_section_image);
			iconHeaderImage.setMaxHeight(40);
			teamName = findViewById(R.id.fragment_header_teamname);
			teamName.setText(user.getUser().getTeam().getName());
		}

	}

	void loadFragment(){
      //  printStrategies();
        Log.e("loadFragment", "loadFragment ." + lastSectionName);
		if(user != null) {
			setTrainment(false);
			setNotifications();
			Log.e("eita ma", lastSectionName);
			if(lastSectionName.length() > 0){
				sectionName.setText(lastSectionName);
			}

			if (savedInstanceState == null) {
				/* Fragment fragment = null;
				Class fragmentClass = null;
				fragmentClass = FragmentStrategy.class;
				try {
					fragment = (Fragment) fragmentClass.newInstance();
					fragment.setPa
				} catch (Exception e) {
					e.printStackTrace();
				}
				*/
				String pattern = "###,###,###,###";
				DecimalFormat decimalFormat = new DecimalFormat(pattern);
				String format = decimalFormat.format(user.getUser().getTeam().getFunds());

				if(db.findCurrentScreen() != null && db.findCurrentScreen().getFragmentName()!= null && !db.findCurrentScreen().getFragmentName().equals("")) {
					switch (db.findCurrentScreen().getFragmentName()) {
						case "FragmentFriendlies":
							sectionName.setText(getResources().getString(R.string.friendlies));
							fragment = new FragmentFriendlies();
							Bundle bundle = new Bundle();
							bundle.putSerializable("user", user);
							bundle.putSerializable("mparam2", "");
							fragment.setArguments(bundle);
							VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_handshake, sectionIcon);
							VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
							path.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentLockerRoom":
							sectionName.setText(getResources().getString(R.string.locker_room_title));
							fragment = FragmentLockerRoom.newInstance(user, "");
							VectorChildFinder vector2 = new VectorChildFinder(this, R.drawable.ic_home, sectionIcon);
							VectorDrawableCompat.VFullPath path2 = vector2.findPathByName("color");
							path2.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentPlayer":
							sectionName.setText(getResources().getString(R.string.player));
							fragment = FragmentPlayers.newInstance(user, 0);
							VectorChildFinder vector3 = new VectorChildFinder(this, R.drawable.ic_user, sectionIcon);
							VectorDrawableCompat.VFullPath path3 = vector3.findPathByName("color");
							path3.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentPlayers":
							sectionName.setText(getResources().getString(R.string.players));
							fragment = FragmentPlayers.newInstance(user, 0);
							VectorChildFinder vector4 = new VectorChildFinder(this, R.drawable.ic_users, sectionIcon);
							VectorDrawableCompat.VFullPath path4 = vector4.findPathByName("color");
							path4.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarket":
							sectionName.setText(getResources().getString(R.string.market_title));
							fragment = FragmentMarket.newInstance(user, 0);
							VectorChildFinder vector10 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path10 = vector10.findPathByName("color");
							path10.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentShopping":
							sectionName.setText(getResources().getString(R.string.shopping));
							fragment = FragmentShopping.newInstance(user, "");
							VectorChildFinder vector5 = new VectorChildFinder(this, R.drawable.ic_shopping_cart, sectionIcon);
							VectorDrawableCompat.VFullPath path5 = vector5.findPathByName("color");
							path5.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentStrategy":
							sectionName.setText(getResources().getString(R.string.strategy));
							fragment = FragmentStrategy.newInstance(user, "");
							VectorChildFinder vector6 = new VectorChildFinder(this, R.drawable.ic_cogs, sectionIcon);
							VectorDrawableCompat.VFullPath path6 = vector6.findPathByName("color");
							path6.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentTeam":
							sectionName.setText(getResources().getString(R.string.team));
							fragment = FragmentTeam.newInstance(user, "");
							VectorChildFinder vector7 = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
							VectorDrawableCompat.VFullPath path7 = vector7.findPathByName("color");
							path7.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMeTransactions":
							sectionName.setText(getResources().getString(R.string.finances));
							fragment = FragmentTeam.newInstance(user, "");
							VectorChildFinder vector13 = new VectorChildFinder(this, R.drawable.ic_money, sectionIcon);
							VectorDrawableCompat.VFullPath path13 = vector13.findPathByName("color");
							path13.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentTeamEdit":
							sectionName.setText(getResources().getString(R.string.team));
							fragment = FragmentTeamEdit.newInstance(user, "");
							VectorChildFinder vector8 = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
							VectorDrawableCompat.VFullPath path8 = vector8.findPathByName("color");
							path8.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentSettings":
							sectionName.setText(getResources().getString(R.string.settings));
							fragment = FragmentSettings.newInstance(user, "");
							VectorChildFinder vector11 = new VectorChildFinder(this, R.drawable.ic_mobile_alt, sectionIcon);
							VectorDrawableCompat.VFullPath path11 = vector11.findPathByName("color");
							path11.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_1" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector15 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path15 = vector15.findPathByName("color");
							path15.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_2" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector16 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path16 = vector16.findPathByName("color");
							path16.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_3" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector17 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path17 = vector17.findPathByName("color");
							path17.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_4" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector18 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path18 = vector18.findPathByName("color");
							path18.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_5" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector19 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path19 = vector19.findPathByName("color");
							path19.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentTournament":
							sectionName.setText(getResources().getString(R.string.tournament));
							fragment = FragmentTournament.newInstance(user, "");
							VectorChildFinder vector9 = new VectorChildFinder(this, R.drawable.ic_users, sectionIcon);
							VectorDrawableCompat.VFullPath path9 = vector9.findPathByName("color");
							path9.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "":
							fragment = FragmentLockerRoom.newInstance(user, "");
							sectionIncomes.setText(format + " $");
							sectionName.setText(getResources().getString(R.string.locker_room_title));

							VectorChildFinder vector12 = new VectorChildFinder(this, R.drawable.ic_home, sectionIcon);
							VectorDrawableCompat.VFullPath path12 = vector12.findPathByName("color");
							path12.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;

					}
					fragmentManager = getSupportFragmentManager();
					fragmentNameSection = db.findCurrentScreen().getFragmentName();
					try {
						fragmentManager.beginTransaction().replace(R.id.flContent, fragment, db.findCurrentScreen().getFragmentName()).commit();
					}catch(Exception e){e.printStackTrace();}
				}else{
					fragment = FragmentLockerRoom.newInstance(user, "");
					sectionIncomes.setText(format + " $");
					sectionName.setText(getResources().getString(R.string.locker_room_title));
					fragmentManager = getSupportFragmentManager();
					VectorChildFinder vector2 = new VectorChildFinder(this, R.drawable.ic_home, sectionIcon);
					VectorDrawableCompat.VFullPath path2 = vector2.findPathByName("color");
					path2.setFillColor(Color.parseColor("#3b6c8e"));
					sectionIcon.invalidate();
					fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentLockerRoom").commit();
				}




			}else{
				Log.e("onResume loafdin", changingSection + fragmentNameSection);
				if(user != null) {
					String pattern = "###,###,###,###";
					DecimalFormat decimalFormat = new DecimalFormat(pattern);
					String format = decimalFormat.format(user.getUser().getTeam().getFunds());

					sectionIncomes.setText( format + " $");
				}
				if(changingSection){
					switch (fragmentNameSection){
						case "FragmentFriendlies" :
							sectionName.setText(getResources().getString(R.string.friendlies));
							VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_handshake, sectionIcon);
							VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
							path.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentLockerRoom" :
							sectionName.setText(getResources().getString(R.string.locker_room_title));
							VectorChildFinder vector2 = new VectorChildFinder(this, R.drawable.ic_home, sectionIcon);
							VectorDrawableCompat.VFullPath path2 = vector2.findPathByName("color");
							path2.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentPlayer" :
							sectionName.setText(getResources().getString(R.string.player));
							VectorChildFinder vector3 = new VectorChildFinder(this, R.drawable.ic_user, sectionIcon);
							VectorDrawableCompat.VFullPath path3 = vector3.findPathByName("color");
							path3.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentPlayers" :
							sectionName.setText(getResources().getString(R.string.players));
							VectorChildFinder vector4 = new VectorChildFinder(this, R.drawable.ic_users, sectionIcon);
							VectorDrawableCompat.VFullPath path4 = vector4.findPathByName("color");
							path4.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarket" :
							sectionName.setText(getResources().getString(R.string.market_title));
							VectorChildFinder vector10 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path10 = vector10.findPathByName("color");
							path10.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentShopping" :
							sectionName.setText(getResources().getString(R.string.shopping));
							VectorChildFinder vector5 = new VectorChildFinder(this, R.drawable.ic_shopping_cart, sectionIcon);
							VectorDrawableCompat.VFullPath path5 = vector5.findPathByName("color");
							path5.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentStrategy" :
							sectionName.setText(getResources().getString(R.string.strategy));
							VectorChildFinder vector6 = new VectorChildFinder(this, R.drawable.ic_cogs, sectionIcon);
							VectorDrawableCompat.VFullPath path6 = vector6.findPathByName("color");
							path6.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentTeam" :
							sectionName.setText(getResources().getString(R.string.team));
							VectorChildFinder vector7 = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
							VectorDrawableCompat.VFullPath path7 = vector7.findPathByName("color");
							path7.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMeTransactions" :
							sectionName.setText(getResources().getString(R.string.finances));
							VectorChildFinder vector13 = new VectorChildFinder(this, R.drawable.ic_money, sectionIcon);
							VectorDrawableCompat.VFullPath path13 = vector13.findPathByName("color");
							path13.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentTeamEdit" :
							sectionName.setText(getResources().getString(R.string.team));
							VectorChildFinder vector8 = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
							VectorDrawableCompat.VFullPath path8 = vector8.findPathByName("color");
							path8.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentSettings" :
							sectionName.setText(getResources().getString(R.string.settings));
							VectorChildFinder vector11 = new VectorChildFinder(this, R.drawable.ic_mobile_alt, sectionIcon);
							VectorDrawableCompat.VFullPath path11 = vector11.findPathByName("color");
							path11.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentTournament" :
							sectionName.setText(getResources().getString(R.string.tournament));
							VectorChildFinder vector9 = new VectorChildFinder(this, R.drawable.ic_users, sectionIcon);
							VectorDrawableCompat.VFullPath path9 = vector9.findPathByName("color");
							path9.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector14 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path14 = vector14.findPathByName("color");
							path14.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_1" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector15 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path15 = vector15.findPathByName("color");
							path15.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_2" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector16 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path16 = vector16.findPathByName("color");
							path16.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_3" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector17 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path17 = vector17.findPathByName("color");
							path17.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_4" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector18 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path18 = vector18.findPathByName("color");
							path18.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;
						case "FragmentMarketTransactions_5" :
							sectionName.setText(getResources().getString(R.string.transfer_done_abv));
							VectorChildFinder vector19 = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
							VectorDrawableCompat.VFullPath path19 = vector19.findPathByName("color");
							path19.setFillColor(Color.parseColor("#3b6c8e"));
							sectionIcon.invalidate();
							break;

					}
				}
			}
			checkIfHasToChangeFragment();
		}else{
			getMe();
		}
	}

	private void getStrategies(){
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setTitle(getResources().getString(R.string.wait));
		dialog.setMessage(getResources().getString(R.string.loading_strategies));
		dialog.show();
		svc = Svc.initAuth(getApplicationContext());
		Call<getStrategies> call = svc.getStrategies(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
		call.enqueue(new Callback<getStrategies>() {
			@Override
			public void onResponse(Call<getStrategies> call, Response<getStrategies> response) {

				final Response<getStrategies> responsefinal = response;
//				Log.e("Strategies", responsefinal.body().toString());
				final getStrategies getStrategies = response.body();

				if(dialog != null && dialog.isShowing()){
					dialog.dismiss();
					dialog = null;
				}

				if (response.isSuccessful()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							if(responsefinal.code() == 400){

							}else if(responsefinal.code() == 201 || responsefinal.code() == 200){

								db.saveAllStrategies(getStrategies.getStrategies());
								buildDrawerNavigation();
								loadFragment();
							}
						}
					});
				} else {
					try {
						if(response.errorBody().string().contains("live_match") && response.errorBody().string().contains("Broadcasting live match")){
							Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
							//   mainActivity.putExtra("user", null);
							startActivity(mainActivity);
							finish();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(responsefinal.code() == 500|| responsefinal.code() == 503){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call<getStrategies> call, Throwable t) {
				t.printStackTrace();
			}
		});

	}

    public void setUser(getUser user){
	    this.user = user;
    }
	void postTrain(boolean restart){

		svc = Svc.initAuth(getApplicationContext());
		PostTrain postTrain = new PostTrain();
		postTrain.setDevice_id(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
		postTrain.setRestart(restart);
		Call<getTrain> call = svc.postTrain(postTrain);
		call.enqueue(new Callback<getTrain>() {
			@Override
			public void onResponse(Call<getTrain> call, Response<getTrain> response) {
				final getTrain train = response.body();
				final Response<getTrain> responsefinal = response;
				if (response.isSuccessful()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							System.out.println("Success, getTrain = " + responsefinal);
							if(responsefinal.code() == 400){

							}else if(responsefinal.code() == 201 || responsefinal.code() == 200) {
								Log.e("train.isShow_options()", train.isShow_options() + "");
								if(!train.isShow_options()){
									if(train.isTrained()) {
										String title = getResources().getString(R.string.trainment);
										String text = getResources().getString(R.string.trainment_done_1) + " " + train.getCount() +
												getResources().getString(R.string.trainment_done_2) + " " + train.getPoints() +
												" " + getResources().getString(R.string.trainment_done_3) + " " + train.getPoints() +
												" " + getResources().getString(R.string.trainment_done_4)
												+ (train.getPoints() < 10 ? train.getPoints() + 2 : 10) + " " + getResources().getString(R.string.trainment_done_5);

										messageDialog = new MessageDialog(MainActivity.this, title, text, mainActivity);
										messageDialog.show();
										dateToTrain = train.getNext() * 1000;
										trainmentButton.setOnClickListener(null);
										db = new DatabaseManager(getApplicationContext());
										if (db.ExistsNotification()) {
											Notification notification = db.findNotification();
											notification.setTrainNotification(false);
											db.saveNotification(notification);
										}
										setTrainment(true);
										updateTrainningNotifications(train.getNext() * 1000);
									}else{
										String title = getResources().getString(R.string.trainment);
										String text = getResources().getString(R.string.trainment_still_1) + " "  +
												(train.getNext()) / 3600 + " " + getResources().getString(R.string.trainment_still_2);
										TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

										Calendar calendar = new GregorianCalendar();
										calendar.setTimeZone(timeZone);
										final long timeNow = calendar.getTimeInMillis() + train.getNext() * 1000;
										DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										calendar.setTimeInMillis(timeNow);

										user.getUser().getTeam().setLast_trainning(sdf.format(calendar));
										user.setUser(user.getUser());
										setTrainment(false);
										updateTrainningNotifications(train.getNext() * 1000);
										messageDialog = new MessageDialog(MainActivity.this, title, text, mainActivity);
										messageDialog.show();
									}
								}else{
									showOptionsDialog(train.getCount());
								}
							}
						}
					});
				} else {
					try {
						if(response.errorBody().string().contains("live_match") && response.errorBody().string().contains("Broadcasting live match")){
							Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
							//   mainActivity.putExtra("user", null);
							startActivity(mainActivity);
							finish();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(responsefinal.code() == 500|| responsefinal.code() == 503){
						Toast.makeText(getApplicationContext(), 	getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call<getTrain> call, Throwable t) {
				t.printStackTrace();
			}
		});


	}
	private void postLogout(){
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
		User au = db.findUser();
		Call<AndroiduserLoginResponse> call = svc.postLogout(Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
		call.enqueue(new Callback<AndroiduserLoginResponse>() {
			@Override
			public void onResponse(Call<AndroiduserLoginResponse> call, Response<AndroiduserLoginResponse> response) {
				final Response responsefinal = response;

				final AndroiduserLoginResponse aur = response.body();
				Log.e("response Logout", response.toString());
				if (response.isSuccessful()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {

							if(responsefinal.code() == 400){
								// showErrorsLoginForm(aur.getErrors());
							}else if(responsefinal.code() == 201 || responsefinal.code() == 204 || responsefinal.code() == 200){
								updateLogoutAndSaveAndroiduser();
								startActivity(new Intent(MainActivity.this, SplashActivity.class));
								finish();
							}
						}
					});
				} else {
					try {
						if(response.errorBody().string().contains("live_match") && response.errorBody().string().contains("Broadcasting live match")){
							Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
							//   mainActivity.putExtra("user", null);
							startActivity(mainActivity);
							finish();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(response.code() == 500 || response.code() == 503){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
					}
				}

			}

			@Override
			public void onFailure(Call<AndroiduserLoginResponse> call, Throwable t) {
				t.printStackTrace();
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_SHORT).show();

			}
		});
	}
	private void updateLogoutAndSaveAndroiduser(){

		User au = db.findUser();

		au.setToken(null);
		au.setLogged(false);

		db.saveUser(au);
	}

	public void setChangingScreenOrientation(boolean changing, String fragmentName){
		changingSection = changing;
		fragmentNameSection = fragmentName;

	}
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		Log.e("MainActivity", "onSaveInstanceState !!!!!!!!!!!!!!!!!!!!!!!!!!");
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putBoolean("screen_orientation", changingSection);
		savedInstanceState.putString("section",fragmentNameSection);
		if(languageChanged != null && languageChanged.length() == 2){
			savedInstanceState.putString("language", languageChanged);
		}
	}
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
        Log.e("MainActivity", "onRestoreInstanceState !!!!!!!!!!!!!!!!!!!!!!!!!!");
		changingSection = savedInstanceState.getBoolean("screen_orientation");
		fragmentNameSection = savedInstanceState.getString("section");
		if(savedInstanceState.getString("language") != null && savedInstanceState.getString("language").length() == 2){
			if(!LocaleLanguageHelper.getLanguage(getApplicationContext()).equals(savedInstanceState.get("language"))){
				LocaleLanguageHelper.setLocale(MainActivity.this, savedInstanceState.getString("language"));
			}
		}
	}
	public void setLanguage(String lang){
		languageChanged = lang;
	}
	public void onClickFragmentPlayer(View view){
		System.out.println("onClick MAINACTIVITY view is null? "+ (view == null));
		if(fragment != null) {
			((FragmentPlayers) fragment).onClick(view);
		}else{
			fragment = fragmentManager.findFragmentByTag("FragmentPlayers");
			((FragmentPlayers) fragment).onClick(view);
		}
	}
	public void onClickFragmentMarket(View view){
		Log.e("MainActivity","onClickFragmentMarket MAINACTIVITY view is null? "+ (view == null));
		if(fragment != null) {
			((FragmentMarket) fragment).onClick(view);
		}else{
			fragment = fragmentManager.findFragmentByTag("FragmentMarket");
			((FragmentMarket) fragment).onClick(view);
		}
	}
	public void changeToFragmentPlayer(Player player){
		fragment = FragmentPlayer.newInstance(user, player);
		sectionName.setText(player.getFirst_name() + " " + player.getLast_name());
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_user, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
		path.setFillColor(Color.parseColor("#3b6c8e"));
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentPlayer").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
	}
	public void changeToLockerRoom(){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentLockerRoom.newInstance(user, null);
		sectionName.setText(getResources().getString(R.string.locker_room_title));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_home, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
		path.setFillColor(getResources().getColor(R.color.futbolinAzul));


		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentLockerRoom").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentLockerRoom");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToFragmentPlayersPlayerDialog(long player_id){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentPlayers.newInstance(user, player_id);
		sectionName.setText(getResources().getString(R.string.players));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_users, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
		path.setFillColor(getResources().getColor(R.color.futbolinAzul));


		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentPlayers").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentPlayers");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToFragmentPlayers(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentPlayers.newInstance(user, 0);
		sectionName.setText(getResources().getString(R.string.players));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_users, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));


		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentPlayers").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentPlayers");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToFragmentTeamEdit(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentTeamEdit.newInstance(user, "");
		sectionName.setText(user.getUser().getTeam().getName());
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentTeamEdit").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("changeToFragmentTeamEdit");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToFragmentTeam(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentTeam.newInstance(user, "");
		sectionName.setText(user.getUser().getTeam().getName());
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentTeam").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
	}
    public void changeToSplashScreen(){
       startActivity(new Intent(MainActivity.this, SplashActivity.class));
       finish();
    }
	public void changeToShowTeamFragment(long team_id, String team_name, String previousFragment){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentShowTeam.newInstance(user, team_id + "");
		sectionName.setText(team_name);
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_shield, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentShowTeam" + previousFragment).commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentShowTeam" + previousFragment);
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToTournamentFragment(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentTournament.newInstance(user,  "");
		sectionName.setText(getResources().getString(R.string.tournament));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_trophy, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentTournament").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentTournament");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToTournamentFragmentById(long tournament_id){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentTournament.newInstance(user,  tournament_id  + "");
		sectionName.setText(getResources().getString(R.string.tournament));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_trophy, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentTournament").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentTournament");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToMeTransactions(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentMeTransactions.newInstance(user,  "");
		sectionName.setText(getResources().getString(R.string.finances));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_money, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentMeTransactions").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentMeTransactions");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToMarketTransactions(int current_page){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentMarketTransactions.newInstance(user,  current_page + "");
		sectionName.setText(getResources().getString(R.string.transfer_done_abv));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentMarketTransactions_" + current_page).commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentMarketTransactions_" + current_page);
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToMarketFollowing(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentMarketFollowing.newInstance(user,   "");
		sectionName.setText(getResources().getString(R.string.following));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentMarketFollowing").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentMarketFollowing");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToMarketOffers(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentMarketOffers.newInstance(user,   "");
		sectionName.setText(getResources().getString(R.string.my_offers));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));
//ERROR AQUI SUPER IMPORTANTE: NullPointerException: Attempt to invoke virtual method 'void androidx.constraintlayout.widget.Guideline.setGuidelinePercent(float)'
        //NullPointerException && .IllegalStateException: Can not perform this action after onSaveInstanceState
		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentMarketOffers").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentMarketOffers");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToFriendliesFragment(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = new FragmentFriendlies();
		Bundle bundle = new Bundle();
		bundle.putSerializable("user", user);
		bundle.putSerializable("mparam2", "");
		fragment.setArguments(bundle);

		sectionName.setText(getResources().getString(R.string.friendlies));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_handshake, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentFriendlies").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentFriendlies");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToLiveFragment(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		if(trainmentButton == null){
			trainmentButton = findViewById(R.id.toolbartrainment);
		}
		trainmentButton.setVisibility(View.GONE);
		fragment = FragmentLive.newInstance(user,  "");
		sectionName.setText(getResources().getString(R.string.live));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_trophy, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment, "FragmentLive").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
	}
	public void changeToMarketFragment(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentMarket.newInstance(user,  0);
		sectionName.setText(getResources().getString(R.string.market_title));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentMarket").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentMarket");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToMarketFragmentPlayerDialog(long player_id){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentMarket.newInstance(user,  player_id);
		sectionName.setText(getResources().getString(R.string.market_title));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_retweet, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
		path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentMarket").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentMarket");
		db.saveCurrentScreen(currentScreen);
	}
	public void changeToStrategyFragment(){
		if(user != null) {
			String pattern = "###,###,###,###";
			DecimalFormat decimalFormat = new DecimalFormat(pattern);
			String format = decimalFormat.format(user.getUser().getTeam().getFunds());

			sectionIncomes.setText( format + " $");
		}
		fragment = FragmentStrategy.newInstance(user,  "");
		sectionName.setText(getResources().getString(R.string.strategy));
		VectorChildFinder vector = new VectorChildFinder(this, R.drawable.ic_cogs, sectionIcon);
		VectorDrawableCompat.VFullPath path = vector.findPathByName("color");
        path.setFillColor(getResources().getColor(R.color.futbolinAzul));

		fragmentManager.beginTransaction().replace(R.id.flContent, fragment, "FragmentStrategy").commit();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		if(db == null){
			db = new DatabaseManager(getApplicationContext());
		}
		currentScreen currentScreen =  db.findCurrentScreen();
		currentScreen.setFragmentName("FragmentStrategy");
		db.saveCurrentScreen(currentScreen);
	}
	public void updateTrainningNotifications2(long nextTime){


		Log.e("updateTrainningNOt", "updateTrainningNotifications - nextTime = " + nextTime);
		Notification notification;
		TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

		if(db.ExistsNotification()){
			notification = db.findNotification();
		}else{
			notification = new Notification();
			notification.setMatchActive(true);
			notification.setTrainActive(true);
			notification.setLiveSoundsActive(true);
			notification.setTrainNotification(false);
			notification.setMatchNotification(false);
			notification.setNextMatch(0);
			notification.setMatchNotificationAccount(0);
			notification.setTrainNotificationAccount(0);
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			sdf.setTimeZone(timeZone);
			date = sdf.parse(user.getUser().getTeam().getLast_trainning());
		}catch (Exception e){

		}


		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(timeZone);
		long timeNow = calendar.getTimeInMillis();
		if(nextTime > 0){
			dateToTrain = nextTime + (5 * 60 + 1000 );
		}else {
			if(date != null) {
				dateToTrain = date.getTime() + TrainningInterval - timeNow;
			}else{
				dateToTrain = 0;
			}
		}
		notification.setLastTrain(dateToTrain);

		db.saveNotification(notification);
		Log.e("notif Main", db.findNotification().toString());

		if(dateToTrain > 0) {
			AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			Intent serviceIntent = new Intent(getApplicationContext(), TrainNotificationsService.class);

			PendingIntent servicePendingIntent =
					PendingIntent.getService(getApplicationContext(),
							Defaultdata.TRAINNING_SERVICE, // integer constant used to identify the service
							serviceIntent,
							PendingIntent.FLAG_CANCEL_CURRENT);

			Log.e("dateToTrain", dateToTrain + "");

//			am.setTimeZone("America/Buenos_Aires");

			am.setRepeating(
					AlarmManager.RTC,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
					new GregorianCalendar().getTimeInMillis(),
					dateToTrain,
					servicePendingIntent
			);

			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				am.setExactAndAllowWhileIdle(AlarmManager.RTC,new Date().getTime() + dateToTrain, servicePendingIntent);
				am.setAndAllowWhileIdle(AlarmManager.RTC,new Date().getTime() + dateToTrain, servicePendingIntent);
				AlarmManager.AlarmClockInfo ac=
						new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + dateToTrain,
								servicePendingIntent);
				am.setAlarmClock(ac, servicePendingIntent);
			}
			notification.setTrainNotificationAccount(0);
			db.saveNotification(notification);
			Log.e("NEW ALARM", "TRAIN ALARM SET -> " + ((new GregorianCalendar().getTimeInMillis()) + dateToTrain));
		}else{
			Log.e("dateToTrain negative", dateToTrain + "");
		}
	}
	public void updateTrainningNotifications(long nextTime){


		Log.e("updateTrainningNOt", "updateTrainningNotifications - nextTime = " + nextTime);
		Notification notification;
		TimeZone timeZone = TimeZone.getTimeZone("America/Buenos_Aires");

		if(db.ExistsNotification()){
			notification = db.findNotification();
		}else{
			notification = new Notification();
			notification.setMatchActive(true);
			notification.setTrainActive(true);
			notification.setLiveSoundsActive(true);
			notification.setTrainNotification(false);
			notification.setMatchNotification(false);
			notification.setNextMatch(0);
			notification.setMatchNotificationAccount(0);
			notification.setTrainNotificationAccount(0);
		}
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			sdf.setTimeZone(timeZone);
			date = sdf.parse(user.getUser().getTeam().getLast_trainning());
		}catch (Exception e){

		}


		Calendar calendar = new GregorianCalendar();
		calendar.setTimeZone(timeZone);
		long timeNow = calendar.getTimeInMillis();
		if(nextTime > 0){
			dateToTrain = nextTime + (5 * 60 + 1000 );
		}else {
			if(date != null) {
				dateToTrain = date.getTime() + TrainningInterval - timeNow;
			}else{
				dateToTrain = 0;
			}
		}
		notification.setLastTrain(dateToTrain);

		db.saveNotification(notification);
		Log.e("notif Main", db.findNotification().toString());

		if(dateToTrain > 0) {
			AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			Intent serviceIntent = new Intent(getApplicationContext(), TrainNotificationsService.class);

			PendingIntent servicePendingIntent =
					PendingIntent.getService(getApplicationContext(),
							Defaultdata.TRAINNING_SERVICE, // integer constant used to identify the service
							serviceIntent,
							PendingIntent.FLAG_CANCEL_CURRENT);

			Log.e("dateToTrain", dateToTrain + "");

//			am.setTimeZone("America/Buenos_Aires");

			am.setRepeating(
					AlarmManager.RTC,
					new GregorianCalendar().getTimeInMillis(),
					dateToTrain,
					servicePendingIntent
			);

			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				am.setExactAndAllowWhileIdle(AlarmManager.RTC,new Date().getTime() + dateToTrain, servicePendingIntent);
				am.setAndAllowWhileIdle(AlarmManager.RTC,new Date().getTime() + dateToTrain, servicePendingIntent);
				AlarmManager.AlarmClockInfo ac=
						new AlarmManager.AlarmClockInfo(System.currentTimeMillis() + dateToTrain,
								servicePendingIntent);
				am.setAlarmClock(ac, servicePendingIntent);
			}
			notification.setTrainNotificationAccount(0);
			db.saveNotification(notification);
			Log.e("NEW ALARM", "TRAIN ALARM SET -> " + ((new GregorianCalendar().getTimeInMillis()) + dateToTrain));
		}else{
			Log.e("dateToTrain negative", dateToTrain + "");
		}
	}
	public void setNotifications(){
		for(int i=0; i < layoutMessagesNotification.getChildCount(); i++){
			View view = layoutMessagesNotification.getChildAt(i);
			if(view instanceof TextView){
				if(((TextView) view).getText() != null && !((TextView) view).getText().toString().equals("Mensajes")){

					layoutMessagesNotification.removeView(view);
				}
			}
		}
		if(user.getNotifications()!= null){
			if(user.getNotifications().getUnread() > 0){
				TextView textView = new TextView(getApplicationContext());
				textView.setText(String.valueOf(user.getNotifications().getUnread()));
				textView.setTextColor(Color.parseColor("#FFFFFF"));
				textView.setTextSize(11);
				textView.setPadding(10,1,10,1);
				textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
				GradientDrawable shape = new GradientDrawable();
				shape.setShape(GradientDrawable.RECTANGLE);
				shape.setCornerRadii(new float[] { 30, 30, 30, 30, 30, 30, 30, 30 });
				shape.setColor(Color.parseColor("#ff0000"));
				shape.setStroke(2, Color.parseColor("#ff0000"));
				textView.setBackground(shape);
				textView.invalidate();
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
			//	params.setMargins(0,0,20,0);
				textView.setLayoutParams(params);

				layoutMessagesNotification.addView(textView);

			}
			layoutMessagesNotification.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

						notificationsDialog = new NotificationsDialog(MainActivity.this ,user.getNotifications(), MainActivity.this);
						notificationsDialog.show();

				}
			});
			if(user.getNotifications().getMessages() != null && user.getNotifications().getMessages().size() >0){
				for(SystemSubNotifications messages : user.getNotifications().getMessages()){
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date dateLastActivity = null;
					try {
						dateLastActivity = sdf.parse(user.getUser().getLast_activity());
					}catch (Exception e){

					}
					Date dateMessage = null;
					try {
						dateMessage = sdf.parse(messages.getCreated_at());
					}catch (Exception e){

					}
					if(dateLastActivity != null && dateMessage != null){
						if(dateLastActivity.getTime() < dateMessage.getTime()){
							messagePlayerAnchorTagDialog = new MessagePlayerAnchorTagDialog(MainActivity.this, messages.getTitle(), messages.getMessage(), MainActivity.this);
							messagePlayerAnchorTagDialog.show();
						}
					}
				}
			}

			int playersNotificationAccount = 0;
			playersNotificationAccount = user.getNotifications().getInjuries().size()
					+ user.getNotifications().getRetiring().size()
					+ user.getNotifications().getSuspensions().size()
					+ user.getNotifications().getTransferables().size()
					+ user.getNotifications().getUpgraded().size();

			if(playersNotificationAccount > 0) {
				TextView textView = new TextView(getApplicationContext());
				textView.setText(String.valueOf(playersNotificationAccount));
				textView.setTextColor(Color.parseColor("#FFFFFF"));
				textView.setTextSize(11);
				textView.setPadding(10, 1, 10, 1);
				textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
				GradientDrawable shape = new GradientDrawable();
				shape.setShape(GradientDrawable.RECTANGLE);
				shape.setCornerRadii(new float[]{30, 30, 30, 30, 30, 30, 30, 30});
				if(user.getNotifications().getSuspensions().size() > 0 || user.getNotifications().getInjuries().size() > 0){
					shape.setColor(Color.parseColor("#ff0000"));
					shape.setStroke(2, Color.parseColor("#ff0000"));
				}else {
					shape.setColor(Color.parseColor("#3b6c8e"));
					shape.setStroke(2, Color.parseColor("#3b6c8e"));
				}
				textView.setBackground(shape);
				textView.invalidate();
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
				//	params.setMargins(0,0,20,0);
				textView.setLayoutParams(params);

				layoutPlayersNotification.addView(textView);

				layoutPlayersNotification.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						playersNotificationDialog = new PlayersNotificationDialog(MainActivity.this, user.getNotifications(), MainActivity.this);
						playersNotificationDialog.show();
					}
				});
			}
		}
	}
    public void getAllNotifications(){

		SvcApi svc = Svc.initAuth(getApplicationContext());
		if(dialog != null && dialog.isShowing()){
			dialog.dismiss();
			dialog = null;
		}
		dialog = new ProgressDialog(MainActivity.this);
		dialog.setTitle(getResources().getString(R.string.wait));
		dialog.setMessage(getResources().getString(R.string.loading_notifications));
		dialog.show();

		Call<getNotifications> call = svc.getNotifications(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
		call.enqueue(new Callback<getNotifications>() {
			@Override
			public void onResponse(Call<getNotifications> call, Response<getNotifications> response) {
				final getNotifications notifications = response.body();
				final Response<getNotifications> responsefinal = response;

				if(dialog != null && dialog.isShowing()){
					dialog.dismiss();
					dialog = null;
				}

				if (response.isSuccessful()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Log.e("getNotification","Success, getNotification = " + responsefinal);
							if(responsefinal.code() == 400){

							}else if(responsefinal.code() == 201 || responsefinal.code() == 200){
								notificationsAllDialog = new NotificationsAllDialog(MainActivity.this, notifications.getNotifications(), MainActivity.this);
								notificationsAllDialog.show();
							}
						}
					});
				} else {
					try {
						String error = response.errorBody().string();

						if(error.contains("live_match") && error.contains("Broadcasting live match")){
							changeToLiveFragment();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(responsefinal.code() == 500|| responsefinal.code() == 503){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call<getNotifications> call, Throwable t) {
				t.printStackTrace();
				Log.e("onFailure!", "onFailure!");
				if(dialog != null && dialog.isShowing()){
					dialog.dismiss();
					dialog = null;
				}
			}
		});
    }
	public getUser getUser(){
		return user;
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
        Log.e("MainActivity", "onDestroy !!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}
	public void showOptionsDialog(int trainCount){
		trainmentRachaDialog = new TwoButtonsDialog(MainActivity.this, getResources().getString(R.string.train_title),
				getResources().getString(R.string.trainment_lost_1) + " " + trainCount +
						getResources().getString(R.string.trainment_lost_2)

				, MainActivity.this, 3, null);
		trainmentRachaDialog.show();
	}
	void postShoppingBuyTrainRacha(){

		svc = Svc.initAuth(getApplicationContext());
		PostShoppingBuy postShoppingBuy = new PostShoppingBuy();
		postShoppingBuy.setDevice_id(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
		postShoppingBuy.setId(7);

		Call<getTrain> call = svc.postShoppingBuyTrain(Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), 7);
		call.enqueue(new Callback<getTrain>() {
			@Override
			public void onResponse(Call<getTrain> call, Response<getTrain> response) {

				final Response<getTrain> responsefinal = response;

				final getTrain train = response.body();
				if (response.isSuccessful()) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if(!train.isShow_options()){
								String title = "Entrenamiento";
								String text = getResources().getString(R.string.trainment_done_1) + " " + train.getCount() +
										getResources().getString(R.string.trainment_done_2) + " " + train.getPoints() +
										" " + getResources().getString(R.string.trainment_done_3) + " " + train.getPoints() +
										" " + getResources().getString(R.string.trainment_done_4)
										+ (train.getPoints() < 10 ? train.getPoints() + 2 : 10) + " "
										+ getResources().getString(R.string.trainment_done_5);

								messageDialog = new MessageDialog(MainActivity.this, title, text, mainActivity);
								messageDialog.show();
								dateToTrain = train.getNext() * 1000;
								trainmentButton.setOnClickListener(null);
								user.getUser().setCredits(train.getCredits());
								db = new DatabaseManager(getApplicationContext());
								if (db.ExistsNotification()) {
									Notification notification = db.findNotification();
									notification.setTrainNotification(false);
									db.saveNotification(notification);
								}
								setTrainment(true);
								updateTrainningNotifications(train.getNext() * 1000);
							}else{
								showOptionsDialog(train.getCount());
							}
						}
					});
				} else {
					try {
						if(response.errorBody().string().contains("live_match") && response.errorBody().string().contains("Broadcasting live match")){
							Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
							//   mainActivity.putExtra("user", null);
							startActivity(mainActivity);
							finish();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
					if(responsefinal.code() == 500|| responsefinal.code() == 503){
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_try_again), Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(Call<getTrain> call, Throwable t) {

				Log.e("failure", "");
				t.printStackTrace();
			}
		});

	}
	void setMarketNotificationsAlarm(){
/*
		Log.e("SUPER", "SUPER PERIODIC ALARMA WORKING NOW");
		AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		Intent serviceIntent = new Intent(getApplicationContext(), MarketNotificationService.class);

		PendingIntent servicePendingIntent =
				PendingIntent.getService(getApplicationContext(),
						Defaultdata.MARKET_SERVICE, // integer constant used to identify the service
						serviceIntent,
						PendingIntent.FLAG_CANCEL_CURRENT);


//			am.setTimeZone("America/Buenos_Aires");

		am.setRepeating(
				AlarmManager.RTC,//type of alarm. This one will wake up the device when it goes off, but there are others, check the docs
				new GregorianCalendar().getTimeInMillis(),
				1000 * 60 * 60 * 2,
				servicePendingIntent
		);
		*/
	}
	public void hideBarLayout(boolean hide){
	//	if(hide) barLayout.setVisibility(View.INVISIBLE);
	//	else barLayout.setVisibility(View.VISIBLE);
		if(guidleLineFragmentHeader != null) {
			if (hide) {
				guidleLineFragmentHeader.setGuidelinePercent((float) 0.01);
			} else {
				guidleLineFragmentHeader.setGuidelinePercent((float) 0.1);
			}
		}
	}
}
