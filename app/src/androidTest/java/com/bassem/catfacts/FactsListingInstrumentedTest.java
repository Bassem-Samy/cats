package com.bassem.catfacts;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.bassem.catfacts.application.CatFactsApplication;
import com.bassem.catfacts.utils.Constants;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by mab on 11/14/17.
 */
@RunWith(AndroidJUnit4.class)

public class FactsListingInstrumentedTest {
    static final int FIRST_PAGE_ITEMS_COUNT = 20;
    static final int TOTAL_ITEMS_COUNT = 40;
    private static final String FIRSTPAGE_PATH = "/test/facts?max_length=500&limit=20&page=1";
    private static final String FIRST_PAGE_JSON_FILE_NAME = "facts_response_page_1.json";
    private static final String SECONDPAGE_PATH = "/test/facts?max_length=500&limit=20&page=2";
    private static final String SECOND_PAGE_JSON_FILE_NAME = "facts_response_page_2.json";
    private static final String LAST_ITEM_IN_FIRST_PAGE_EXPECTED_FACT = "Every year, nearly four million cats are eaten in Asia. and this is really not something that is usual to many people, because rarely there is no body that eat cats but anyways this is what happens in Asia and it is not that happy for cat lovers.";
    private static final String LAST_ITEM_EXPECTED_FACT = "You check your cats pulse on the inside of the back thigh, where the leg joins to the body. Normal for cats: 110-170 beats per minute.";

    private static MockWebServer mockWebServer;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @BeforeClass
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();

        mockWebServer.start();
        Constants.BASE_URL = mockWebServer.url("test/").toString();
        Constants.SEEK_BAR_WAIT_TIME_IN_MILLIS = 0L;
        // return first and second json files depending on the url requested, whether page=1 or 2
        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
                if (recordedRequest.getPath().equalsIgnoreCase(FIRSTPAGE_PATH)) {
                    try {
                        Log.e("recordedRequest", recordedRequest.getPath());
                        return new MockResponse().setResponseCode(200)

                                .setBody(getMockResponseBody(FIRST_PAGE_JSON_FILE_NAME));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else if (recordedRequest.getPath().equalsIgnoreCase(SECONDPAGE_PATH)) {
                    try {
                        Log.e("recordedRequest2", recordedRequest.getPath());
                        return new MockResponse().setResponseCode(200)

                                .setBody(getMockResponseBody(SECOND_PAGE_JSON_FILE_NAME));
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
                return null;
            }
        });
        CatFactsApplication app = (CatFactsApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
        app.initAppComponent();
    }

    private static String getMockResponseBody(String fileName) throws IOException {
        return AssetFileReader.readFileAsString(fileName);
    }

    @Test
    public void test() throws InterruptedException {
        // wait for fragment transaction :#
        Thread.sleep(1000L);
        // first check the total number of displayed item is correct
        onView(withId(R.id.rclr_facts)).check(new RecyclerViewItemsCountAssertion(FIRST_PAGE_ITEMS_COUNT));
        // then scroll to the last item and check if the displayed fact is correct
        onView(withId(R.id.rclr_facts)).perform(scrollToPosition(FIRST_PAGE_ITEMS_COUNT - 1));
        onView(withId(R.id.rclr_facts)).check(new RecyclerViewItemStringDataAssertion(R.id.txt_fact,
                LAST_ITEM_IN_FIRST_PAGE_EXPECTED_FACT,
                FIRST_PAGE_ITEMS_COUNT - 1));
        // now check if the second page is loaded
        onView(withId(R.id.rclr_facts)).check(new RecyclerViewItemsCountAssertion(TOTAL_ITEMS_COUNT));
        // perform scroll to last item
        onView(withId(R.id.rclr_facts)).perform(scrollToPosition(TOTAL_ITEMS_COUNT - 1));
        onView(withId(R.id.rclr_facts)).check(new RecyclerViewItemStringDataAssertion(R.id.txt_fact,
                LAST_ITEM_EXPECTED_FACT,
                TOTAL_ITEMS_COUNT - 1));


    }
}
