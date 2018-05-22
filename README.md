# MaterialDesignFrontHead
## Practice Android App to set material design elements:
MaterialDesignFrontHead presents a mock application screen with mock elements. 
When you tap on them, the actual elements will be created and presented. All the
logic sits in the MainActivity and it is broken down into focused methods.

The app shows how to work with:
* Status Bar - it is where android presents icons for notifications, the time, batery, etc).
* AppBar - is a toolbar with the title of the app plus menus or icons
* AppBar Menu - it shows up at the right hand side with three dots arranged vertically. 
The menu has one of the elements presented as a (heart) icon
* AppBar Menu items
  * Items in the menu are tappable and start some action.
  * AppBar Menu Items are presented with their corresponding icon
* "Like" button (heart) changes color when tapped
* Navigation Drawer - it is a overlapped menu which slides from the left with a swipe gesture
  * Navigation Drawer button - it shows up at the left of the screen, replacing the back navigation button
The menu. It opens the Navigation Drawer
  * Navigation Drawer Header - based on an XML Layout, you could present your company's logo
* FAB or Floating Action Button - it is the rounded floating icon that shows up in many apps.
  * FAB Drag and Drop
  * FAB is definded in a Constraint Layout and therefore it is handled different than in a FrameLayout.
    * Drop places the icon in a different possition 
    * the position is achieved by modifying the constraintHorizontal_Bias or the constraintVertical_Bias, 
    a percentage number in between 0 and 1
    * the position can also be set with pixel coordinates.
    * Fab click...

## Links
Material design is very interesting and important. Check it at:

[Bring your app to life with Material Design](https://developer.android.com/distribute/best-practices/develop/use-material-design)

[Material Design for Android](https://developer.android.com/guide/topics/ui/look-and-feel/?authuser=2)

[Material Design](https://material.io/develop/android/)

## Luis Virueña!

