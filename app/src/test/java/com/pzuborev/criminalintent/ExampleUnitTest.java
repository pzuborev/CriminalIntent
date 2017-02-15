package com.pzuborev.criminalintent;

import android.content.Context;

import com.pzuborev.criminalintent.Crime.Crime;
import com.pzuborev.criminalintent.Crime.CrimeLab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() throws Exception {
//        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
//
//        System.out.println(df.format(new Date()));
//        assertEquals(4, 2 + 2);
//    }

    @Mock
    Context mMockContext;

    @Test
    public void checkCrimeLab() throws Exception {
        CrimeLab crimeLab = CrimeLab.get(mMockContext);

        for (Crime c: crimeLab.getCrimes()
                ) {
            System.out.println(c.getTitle());
        }

    }

}