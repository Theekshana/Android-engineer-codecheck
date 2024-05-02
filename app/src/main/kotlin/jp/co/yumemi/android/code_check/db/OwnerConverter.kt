package jp.co.yumemi.android.code_check.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import jp.co.yumemi.android.code_check.model.Owner

/**
 * TypeConverter class responsible for converting Owner objects to and from JSON format using Gson.
 * This class provides methods for converting Owner objects to JSON strings and vice versa.
 */
class OwnerConverter {

    /**
     * Converts an Owner object to a JSON string.
     *
     * @param owner The Owner object to convert.
     * @return A JSON string representing the Owner object, or null if the input is null.
     */
    @TypeConverter
    fun fromOwner(owner: Owner?): String? {
        return owner?.let { Gson().toJson(owner) }
    }

    /**
     * Converts a JSON string to an Owner object.
     *
     * @param ownerString The JSON string representing the Owner object.
     * @return The Owner object parsed from the JSON string, or null if the input is null.
     */
    @TypeConverter
    fun toOwner(ownerString: String?): Owner? {
        return ownerString?.let { Gson().fromJson(ownerString, Owner::class.java) }
    }

}