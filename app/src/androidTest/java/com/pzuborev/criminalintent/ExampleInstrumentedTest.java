package com.pzuborev.criminalintent;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.pzuborev.criminalintent.Crime.Crime;
import com.pzuborev.criminalintent.Crime.CrimeLab;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.pzuborev.criminalintent", appContext.getPackageName());
    }

    @Test
    public void checkCrimeLab() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        CrimeLab crimeLab = CrimeLab.get(appContext);

        for (Crime c: crimeLab.getCrimes()
             ) {
            System.out.println(c.getTitle());
        }
    }
}
