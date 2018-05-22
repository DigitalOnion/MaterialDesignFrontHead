package com.outerspace.materialdesignfronthead;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 *  MaterialDesignFrontHead
 *
 *  This is an android practice app. I created as an exercise and a coding sample. It contains
 *  the basic operations (Creation, Setting, Dismissing, Listening, etc) for the basic screen
 *  elements required in an app that follows the Material Design.
 *
 *  The screen shows a mock of a screen with mock elements. When Tapped, those elements are
 *  set on the screen.
 *
 *  Then, if you look for a quick reference, look first at the methods staring with "onClick..."
 *  those are the listeners to the mock screen elements
 *
 *  The elements that are shown are part of the AppCompat library and need an AppCompatActivity
 *
 *  The elements that are implemented are:
 *  * StatusBar
 *  * AppBar
 *  * AppBarMenu or OverflowMenu
 *  * Navigation Drawer
 *  * FAB - Floating Action Button (not yet implemented)
 *
 *  Enjoy. DigitalOnion - Luis Virueña .
 */


public class MainActivity extends AppCompatActivity implements MyFABCallback {

    private Menu overflowMenu = null;
    private ConstraintLayout rootLayout;

    private MyFAB fab;
    private float rootWidth;
    private float rootHeight;
    private float fabWidth;
    private float fabHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyFAB fab = (MyFAB) findViewById(R.id.fab);
        fab.setFabCallback(this);
    }

    /**
     *  onFabSizeChanged
     *
     *  This was a nightmare. Seems like there is something broken in .getWidth() and .getHeight()
     *  I tried to get the FAB's size by calling getWidth and getSize. it returns always zero.
     *  If you change and move the FAB sometimes those get the right size. But I could not
     *  figure out when.
     *
     *  In onCreate screen elements are being inflated. Therefore, the sizes are known after
     *  onCreate. I tried with OnStart and to my surprise, the sizes are wrong. I also tried
     *  in onPostCreate with the same fate. Even if you go so far as to check sizes when you click
     *  on the object on the screen.. of course there is a bug...
     *
     *  So I googled
     *
     *     https://stackoverflow.com/questions/3779173/determining-the-size-of-an-android-view-at-runtime
     *
     *  Following this post guidance. I extended the FloatingActionButton class into MyFAB, then,
     *  I overloaded the onSizeChanged method. I also created a callback - MyFABCallback - with
     *  a onFabSizeChanged.
     *
     *  So, in the Layout, I replaced the FloatingActionButton for MyFAB, I implemented the
     *  Callback on the MainActivity itself and passed it as the callback. When the MyFAB knows
     *  its new size, it runs the overloaded onSizeChanged which passes the proper values to the
     *  onFabSizeChanged on the MainActivity
     *
     */
    @Override
    public void onFabSizeChanged(int newWidth, int newHeight) {
        Log.d("FAB SIZE CHANGED" , "WIDTH:" + newWidth + " HEIGHT:" + newHeight );
        fabWidth = newWidth;
        fabHeight = newHeight;
    }

    /**
     * onClickAppBarMenu
     *
     * It toggles - show or hide the AppBar
     *
     * OnClick callback to a tap on the mock Application bar
     */

    public void onClickAppBarMenu(View view) {
        // I found the hideOverflowMenu, but IT DOES NOT WORK... do not know why.
        // Toolbar toolbar = findViewById(R.id.toolbar);
        // toolbar.hideOverflowMenu();

        overflowMenu.setGroupVisible(
                R.id.only_menu_group
                , !overflowMenu.hasVisibleItems());
    }

    /**
     * OnClickDrawer
     *
     * It sets a Navigaton Drawer menu at the left of the Application Bar.
     *
     * I found it difficult to remove or hide the bar. So this sample only
     * sets up the Navigation Drawer. Notice that the Menu is mainly defined
     * in XML layout and menus. In fact I preferred to pass the complete
     * activity_main.xml to a totally new xml file. activity_main_desktop.xml
     * (notice that it is the whole file) and placed the Drawer Layout in the
     * with an Include element.
     *
     * For the official documentation on how to implement a NAVIGATION DRAWER, check:
     *
     *      https://developer.android.com/training/implementing-navigation/nav-drawer
     */

    public void onClickDrawer(View view) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar bar = this.getSupportActionBar();
        if(bar != null ) {
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeAsUpIndicator(R.drawable.ic_menu);

            MyDrawerListener listener = new MyDrawerListener();
            NavigationView navigation = findViewById( R.id.nav_menu );
            navigation.setNavigationItemSelectedListener( listener );
        }
    }

    private class MyDrawerListener implements NavigationView.OnNavigationItemSelectedListener {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            item.setChecked(true);
            drawer.closeDrawers();
            switch (item.getItemId()) {
            case R.id.nav_happy:
                Log.d("ON OPTIONS SELECT ITEM", "BE HAPPY");
                Toast.makeText(drawer.getContext(),
                        R.string.be_happy, Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_fitness:
                Log.d("ON OPTIONS SELECT ITEM", "GET FIT");
                Toast.makeText(drawer.getContext(),
                        R.string.get_fit, Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_relax:
                Log.d("ON OPTIONS SELECT ITEM", "RELAX");
                Toast.makeText(drawer.getContext(),
                        R.string.relax, Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_hot_tub:
                Log.d("ON OPTIONS SELECT ITEM", "HOT TUB");
                Toast.makeText(drawer.getContext(),
                        R.string.hot_tub, Toast.LENGTH_SHORT).show();
                break;

            }
            return true;
        }
    }

    /**
     * onClickAppBar
     *
     * It toggles the AppBar (toolbar)
     *
     * The original AppBar is not compatible with Vintage Versions of Android. Therefore
     * Google added the Support Toolbar that replaces the AppBar (setSupportActionBar())
     * Once it is set up, it is difficult to remove, you could add a different Toolbar, but
     * I didn't find how to really remove it.
     *
     * The onClickAppBar method installs the Toolbar when does not find the SupportActionBar
     * and when it finds it, it only toggles the visibility.
     *
     */

    public void onClickAppBar(View view) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBar bar = this.getSupportActionBar();

        if(bar == null) {
            // Toolbar toolbar = new Toolbar(mainActivity, null, R.style.Theme_AppCompat_Light_NoActionBar);

            toolbar.setVisibility(View.VISIBLE);
            this.setSupportActionBar(toolbar);
        }
        else {
            toolbar.setVisibility(
                    toolbar.getVisibility() == View.VISIBLE ?
                            View.GONE :
                            View.VISIBLE
            );
        }
    }

    /**
     * onClickStatusBar
     *
     * The status bar is not part of your application. It is more like a system element
     * and therefore, we find the reference to it in the .getWindow().getDecorView()
     * You do not set it up, you do not create using a Layout.
     *
     * To toggle it, we need to set its visibility
     *
     */

    public void onClickStatusBar(View view) {
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions;
        uiOptions = decorView.getSystemUiVisibility();
        switch (uiOptions) {
        case View.SYSTEM_UI_FLAG_FULLSCREEN:
            uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            break;
        case View.SYSTEM_UI_FLAG_VISIBLE:
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            break;
        default:
            uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            break;
        }
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * onClickMockFAB
     *
     * Placing the FAB in a constraint layout is easy. Tough, placing it programmatically
     * can become elaborated.
     *
     * onClickMockFAB sets the visibility and sets listeners for Touch, Drag and Click.
     * I managed to cram all in the same listener by implementing the three listener
     * interfaces. Check it out.
     *
     * Then the tricky part starts, The FAB is anchored to the four borders of the parent
     * right, left, top and bottom. Constraint layout has horizontal and vertical bias
     * going from 0 to 1 (float values). You set bias in the XML layout.
     *
     * The ConstraintSet is a class that clones the constraints from a ConstraintLayout,
     * we can modify them programmatically and feed them back to the same or another
     * ConstraintLayout with applyTo. In the case of the FAB, I am changing the bias
     * with setHorizontalBias and setVerticalBias. I am doing that in the DragDrop
     * and the FAB gets placed where the user droped it
     *
     * Check out Android Developers for ConstraintSet and DragShadowBuilder for
     * more details.
     *      https://developer.android.com/reference/android/support/constraint/ConstraintSet
     *      https://developer.android.com/guide/topics/ui/drag-drop
     */

    public void onClickMockFAB(View view) {
        rootLayout = findViewById(R.id.root_layout);
        rootWidth = rootLayout.getMeasuredWidth();
        rootHeight = rootLayout.getMeasuredHeight();

        FloatingActionButton fab = findViewById(R.id.fab);
        if(fab.getVisibility() == View.VISIBLE) {
            fab.setVisibility(View.GONE);
        }
        else {
            fab.setVisibility(View.VISIBLE);
            moveFabBias(0.07f, 0.93f);
        }


        MyOnClickNTouchListener touchListener = new MyOnClickNTouchListener();
        fab.setOnTouchListener(touchListener);

        MyDropDragListener dropDragListener = new MyDropDragListener();
        rootLayout.setOnDragListener(dropDragListener);

    }

    private void moveFabBias(float biasX, float biasY) {
        // keep bias in range - 0 to 1
        if(biasX < 0.0f) biasX = 0.0f;
        if(biasY < 0.0f) biasY = 0.0f;
        if(biasX > 1.0f) biasX = 1.0f;
        if(biasY > 1.0f) biasY = 1.0f;

        // constraint set is a copy of the constraints in the root-layout
        rootLayout = findViewById(R.id.root_layout);
        ConstraintSet cset = new ConstraintSet();
        cset.clone(rootLayout);
        // move the FAB
        cset.setHorizontalBias(R.id.fab, biasX);
        cset.setVerticalBias(R.id.fab, biasY);

        // move the fake FAB - optional
        cset.setHorizontalBias(R.id.fake_fab, biasX);   // not needed is only the mock-fab
        cset.setVerticalBias(R.id.fake_fab, biasY);     // not needed is only the mock-fab

        cset.applyTo(rootLayout);
    }

    private void moveFabXY(float X, float Y) {
        Log.d("MOVE FAB XY", " X=" + X + " Y=" + Y);

        // keep X and Y in range - dimensions of target screen.
        if(X < fabWidth / 2.0f) X = fabWidth / 2.0f;
        if(X > rootWidth - fabWidth / 2.0f) X = rootWidth - fabWidth / 2.0f;
        if(Y < fabHeight / 2.0f) Y = fabHeight / 2.0f;
        if(Y > rootHeight - fabHeight / 2.0f) Y = rootHeight - fabHeight / 2.0f;

        // translate X, Y into BiasX, BiasY, bias is a percentage (from 0 to 1)
        float biasX = (X - fabWidth / 2.0f) / (rootWidth - fabWidth);
        float biasY = (Y - fabHeight / 2.0f) / (rootHeight - fabHeight);
        moveFabBias(biasX, biasY);
    }


    private class MyOnClickNTouchListener implements
            View.OnClickListener,
            View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("OnTouch ACTION DOWN", "X=" + x + " Y=" + y);
                View.DragShadowBuilder builder = new View.DragShadowBuilder(view);
                view.startDragAndDrop(null, builder, view, 0);
                return false;        // returning true prevents onClick event from being sent
            }
            return false;
        }

        @Override
        public void onClick(View view) {
            Log.d("OnClick:", "ON CLICK EVENT");
            Toast.makeText(MainActivity.this, "Clic on FAB", Toast.LENGTH_SHORT).show();
        }
    }

    private class MyDropDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            float x = dragEvent.getX();
            float y = dragEvent.getY();

            switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d("OnDrag DRAG STARTED", "X=" + x + " Y=" + y);
                return true;
            case DragEvent.ACTION_DROP:
                Log.d("OnDrag DRAG DROP", "X=" + x + " Y=" + y);
                moveFabXY(x, y);
                return true;
            }
            return false;
        }
    }

    /**
     * OnClickDesktop
     *
     */

    public void onClickDesktop(View view) {
        // not yet
    }


    /******************************************************************* */

    /**
     *  onCreateOptionsMenu is a callback that Android uses after setting up
     *  the Toolbar.
     *
     *   Notice that the AppBar Menu is added at this point and is left invisible
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        overflowMenu = menu;
        addAppBarMenu(menu);
        menu.setGroupVisible(
                R.id.only_menu_group
                , false);
        return true;
    }

    private void addAppBarMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);

        // this is a very efficient way to show the icon in the menu items...
        // check:
        // https://stackoverflow.com/questions/18374183/how-to-show-icons-in-overflow-menu-8in-actionbar
        //
        // The method replaces the Menu Title by a spannable into the menu item. it adds a *
        // and replaces "*" with icon.
        //
        int menuInfo[][] = {
                {R.id.menu_btn_like, R.drawable.ic_favorite},
                {R.id.menu_btn_delete, R.drawable.ic_delete},
                {R.id.menu_btn_save, R.drawable.ic_save},
        };
        for(int[] info : menuInfo) {
            MenuItem item = menu.findItem(info[0]);
            String itemText = "* " + item.getTitle().toString();
            SpannableStringBuilder builder = new SpannableStringBuilder(itemText);
            builder.setSpan(new ImageSpan(this,
                            info[1]),
                    0, 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            item.setTitle(builder);
        }

        MenuItem like = menu.findItem(R.id.menu_btn_like);
        like.setCheckable(true);
        like.setChecked(false);
    }

    /**
     * onOptionsItemSelected is where the Taps or Clicks on the AppBar and Drawer
     * buttons are listened. You launch your routines from here.
     *
     * Notice that the Drawer button is "android.R.id.home" (the android names-space)
     * while the others are R.id... (the app name-space)
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        case android.R.id.home:
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.START);
            break;
        case R.id.menu_btn_like:
            Toast.makeText(this, R.string.app_menu_like, Toast.LENGTH_SHORT).show();
            processLike();
            break;
        case R.id.menu_btn_save:
            Toast.makeText(this, R.string.app_menu_save, Toast.LENGTH_SHORT).show();
            break;
        case R.id.menu_btn_delete:
            Toast.makeText(this, R.string.app_menu_delete, Toast.LENGTH_SHORT).show();
            break;
        }
        return true;
    }

    /**
     * The Like menu item has a little heart. It is initially black and if the
     * user taps on it, it will turn red.
     *
     * We do that by changin the Icon on the button. (similar to what is done in
     * addAppBarMenu.
     */

    private void processLike() {
        MenuItem likeMenuItem = overflowMenu.findItem(R.id.menu_btn_like);
        boolean liked = !likeMenuItem.isChecked();
        if(liked) {
            likeMenuItem.setIcon(R.drawable.ic_favorite_red);
        } else {
            likeMenuItem.setIcon(R.drawable.ic_favorite);
        }

        likeMenuItem.setChecked(liked);
    }


}
