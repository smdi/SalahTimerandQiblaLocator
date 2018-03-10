package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class City_Country_store extends SQLiteOpenHelper {
    public static final String LOG_TAG = City_Country_store.class.getSimpleName();

    private static final String DATABASE_NAME = "citycountry.db";
    private static final int DATABASE_VERSION = 1;


    public City_Country_store(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE IF NOT EXISTS " + "pets" + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "city" + " TEXT  , " + "Country" + " TEXT  " + " );";

        sqLiteDatabase.execSQL(SQL_CREATE_PETS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onUpgrade(sqLiteDatabase ,i,i1);

    }

}
