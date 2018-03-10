package al_muntaqimcrescent2018.com.salahtimerandqiblalocator;

import android.provider.BaseColumns;

/**
 * Created by Imran on 03-01-2018.
 */
public final class PetsContract {

    public static  abstract  class PetEntry implements BaseColumns {

        public  static final String TABLE_NAME = "pets";
        public  static final String COLUMN_ID = BaseColumns._ID;
        public  static final String COLUMN_PET_NAME = "names";
        public  static final String COLUMN_City = "city";
        public  static final String COLUMN_Country = "Country";
        public  static final String COLUMN_PET_WEIGHT = "weight";


        public static final int GENDER_MALE =1;
        public static final int GENDER_FEMALE =2;
        public static final int GENDER_UNKNOWN =0;

    }

}