package com.pzuborev.criminalintent.Crime;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class CrimeIntentJSONSerializer {
    private  Context mContext;
    private  String mFilename;

    public CrimeIntentJSONSerializer(Context context, String filename) {
        mContext = context.getApplicationContext();
        mFilename = filename;
    }

    public void saveCrimes(ArrayList<Crime> crimes) throws JSONException, IOException {
        JSONArray jsonArray = new JSONArray();
        for (Crime c: crimes) {
            jsonArray.put(c.toJSON());
        }

        Writer writer = null;
        try {
            FileOutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonArray.toString());
        } finally {
            writer.close();
        }
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        InputStream in = mContext.openFileInput(mFilename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder jsonString = new StringBuilder();
        String line = null;

        while((line = reader.readLine()) != null){
            jsonString.append(line);
        }

        JSONArray jsonArray = (JSONArray) (new JSONTokener(jsonString.toString())).nextValue();
        ArrayList<Crime> crimes = new ArrayList<Crime>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            crimes.add(new Crime((JSONObject) jsonArray.get(i)));
        }
        return crimes;
    }
}
