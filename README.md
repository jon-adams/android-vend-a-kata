# android-vend-a-kata

A little Android 'vending machine' app kata.

## Installation

Standard Android development requirements: 

* Install the Android SDK, using SDK 26 (though the app is configured to run on devices with v20 and newer, but must compile with v26)
* Gradle build system
* Android Studio is recommended for ease of testing
* Clone or otherwise acquire the repository

## Usage

* Open the provided Android Studio project, or do the following with the standard gradle equivalent commands
* Use the `debug` variant (because `release` mode does not have a signing key setup since this is a demo)
* Run the `app` configuration in an emulator or device running Android 7+

### Automated tests

* Run the usual gradle Android test tasks (`test`, etc.) from the command line, or use Android Studio and right-click under the app `/java` path and either the androidTest (integration) or test (unit) structures to run the appropriate tests
* Don't forget to run `adb shell monkey -p firetiger.net.vendakata -v 2500` to chaos monkey test random input

## Kata requirements

* accept money, make change, maintain inventory, and dispense products
* one or more views
* network access should not be required
* > "No special consideration should be made for screen orientation."
* accept coins
  * accept valid coins (nickels, dimes, and quarters) and reject invalid ones (pennies)
  * when a valid coin is inserted the amount of the coin will be added to the current amount and the display will be updated
  * when there are no coins inserted, the machine displays "INSERT COIN"
  * rejected coins are placed in the coin return
  * accept user input which is not strictly limited to the valid coins
  * use strings, enums, constants, or something of that nature to represent coins
* select a product
  * three available products: cola/$1.00, chips/$0.50, and candy/$0.65
  * when the respective button is pressed and enough money has been inserted, the product is dispensed and the machine displays "THANK YOU"
  * if the display is checked again, it will display "INSERT COIN" and the current amount will be set to $0.00
  * if there is not enough money inserted then the machine displays "PRICE" and the price of the item and subsequent checks of the display will display either "INSERT COIN" or the current amount as appropriate
* make change
  * when a product is selected that costs less than the amount of money in the machine, then the remaining amount is placed in the coin return
* return coins
  * when the return coins button is pressed, the money the customer has placed in the machine is returned and the display shows "INSERT COIN"
* sold out
  * when the item selected by the customer is out of stock, the machine displays "SOLD OUT"
  * if the display is checked again, it will display the amount of money remaining in the machine or INSERT COIN if there is no money in the machine.
* exact change only
  * when the machine is not able to make change with the money in the machine for any of the items that it sells, it will display "EXACT CHANGE ONLY" instead of "INSERT COIN"

## Developer Notes

* I was not sure if "No special consideration should be made for screen orientation" meant that it *should* have alternate views and support for orientation changes in the middle of the app, or if it meant it does *not* need to implement that robustness?
For time purposes, I'm going with the latter for now. That means the layout does not really work on a small screen landscape orientation, but portrait small screen or either orientation on a tablet should work fine.
* Demo "snacks" image only provided in HDPI resolution; a real app would have multiple DPI images available in the `mipmap-hdpi`, `mipmap-xxhdpi`, etc. directories, but this was just a random image I pulled from stock photos with no real aspect ratio or design needs met besides just adding some eye candy to an otherwise boring demo interface
* WIP: This is still a work-in-progress. All the current tests pass. But, for example, need to implement view models more properly, landscape small screen is not supported, the lifecycle is very naive and will reset the whole app on rotation change, etc. 