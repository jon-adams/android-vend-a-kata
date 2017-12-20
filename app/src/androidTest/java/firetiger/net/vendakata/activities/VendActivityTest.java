package firetiger.net.vendakata.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import firetiger.net.vendakata.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class VendActivityTest {

    @Rule
    public ActivityTestRule<VendActivity> mActivityTestRule = new ActivityTestRule<>(VendActivity.class);

    @Test
    public void smokeTestVendActivity() throws Exception {
        onView(withId(R.id.vend_btn_return))
                .perform(click());
    }

    @Test
    public void vendActivityTest() {
        ViewInteraction display = onView(allOf(withId(R.id.vend_display), isDisplayed()));

        // assert initial text
        display.check(matches(withText("INSERT COIN")));

        // fail to purchase a product
        onView(allOf(withId(R.id.vend_btn_purchase_1), withText("Cola"), isDisplayed()))
                .perform(click());
        display.check(matches(withText("PRICE $1.00")));

        // add some "moneys"
        ViewInteraction insertCoins = onView(
                allOf(withId(R.id.vend_btn_insert), isDisplayed()));
        insertCoins.perform(click());
        insertCoins.perform(click());
        insertCoins.perform(click());
        insertCoins.perform(click());

        // buy some chips
        onView(allOf(withId(R.id.vend_btn_purchase_2), withText("Chips"), isDisplayed()))
                .perform(click());

        // assert display updated
        display.check(matches(withText("THANK YOU")));
    }
}
