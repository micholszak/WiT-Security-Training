/*
 * DIVA Android - Damn Insecure and Vulnerable App for Android
 *
 * Copyright 2016 © Payatu
 * Author: Aseem Jakhar aseem[at]payatu[dot]com
 * Websites: www.payatu.com  www.nullcon.net  www.hardwear.io www.null.co.in
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */
package jakhar.aseem.diva

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import java.lang.IllegalArgumentException

// Implementation reference from
// - http://examples.javacodegeeks.com/android/core/content/contentprovider/android-content-provider-example/
class NotesProvider : ContentProvider() {
    // DB stuff
    private var mDB: SQLiteDatabase? = null

    companion object {
        const val DBNAME = "divanotes.db"
        const val DBVERSION = 1
        const val TABLE = "notes"
        const val C_ID = "_id"
        const val C_TITLE = "title"
        const val C_NOTE = "note"
        const val CREATE_TBL_QRY = " CREATE TABLE " + TABLE +
                " (" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                C_TITLE + " TEXT NOT NULL, " +
                C_NOTE + " TEXT NOT NULL);"
        const val DROP_TBL_QRY = "DROP TABLE IF EXISTS $TABLE"
        const val PATH_TABLE = 1
        const val PATH_ID = 2
        const val AUTHORITY = "jakhar.aseem.diva.provider.notesprovider"
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/$TABLE")
        val urimatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, TABLE, PATH_TABLE)
            addURI(AUTHORITY, "$TABLE/#", PATH_ID)
        }
    }

    private class DBHelper(context: Context?) : SQLiteOpenHelper(context, DBNAME, null, DBVERSION) {
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DROP_TBL_QRY)
            db.execSQL(CREATE_TBL_QRY)
            db.execSQL("INSERT INTO $TABLE($C_TITLE,$C_NOTE) VALUES ('office', '10 Meetings. 5 Calls. Lunch with CEO');")
            db.execSQL("INSERT INTO $TABLE($C_TITLE,$C_NOTE) VALUES ('home', 'Buy toys for baby, Order dinner');")
            db.execSQL("INSERT INTO $TABLE($C_TITLE,$C_NOTE) VALUES ('holiday', 'Either Goa or Amsterdam');")
            db.execSQL("INSERT INTO $TABLE($C_TITLE,$C_NOTE) VALUES ('Expense', 'Spent too much on home theater');")
            db.execSQL("INSERT INTO $TABLE($C_TITLE,$C_NOTE) VALUES ('Exercise', 'Alternate days running');")
            db.execSQL("INSERT INTO $TABLE($C_TITLE,$C_NOTE) VALUES ('Weekend', 'b333333333333r');")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onCreate(db)
        }
    }

    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val count = when (urimatcher.match(uri)) {
            PATH_TABLE ->                 // delete all records
                mDB?.delete(TABLE, selection, selectionArgs) ?: 0
            PATH_ID -> {
                val id = uri.lastPathSegment
                val currentSelection = if (selection.isNullOrEmpty().not()) "AND ($selection)" else ""
                mDB?.delete(TABLE, "$C_ID = $id $currentSelection", selectionArgs)  ?: 0
            }
            else -> throw IllegalArgumentException("Divanotes(delete): Unsupported URI $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        return when (urimatcher.match(uri)) {
            PATH_TABLE -> "vnd.android.cursor.dir/vnd.jakhar.notes"
            PATH_ID -> "vnd.android.cursor.item/vnd.jakhar.notes"
            else -> throw IllegalArgumentException("Divanotes: Unsupported URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val row = mDB?.insert(TABLE, "", values) ?: 0
        if (row > 0) {
            // Record added
            val newUri = ContentUris.withAppendedId(CONTENT_URI, row)
            context?.contentResolver?.notifyChange(newUri, null)
            return newUri
        }
        throw SQLException("Divanotes: Fail to add a new record into $uri")
    }

    override fun onCreate(): Boolean {
        val dbHelper = DBHelper(context)
        mDB = dbHelper.writableDatabase
        return mDB != null
    }

    override fun query(
        uri: Uri, projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()

        // The table to query
        queryBuilder.tables = TABLE
        when (urimatcher.match(uri)) {
            PATH_TABLE -> {}
            PATH_ID -> queryBuilder.appendWhere("$C_ID=${uri.lastPathSegment}")
            else -> throw IllegalArgumentException("Divanotes(query): Unknown URI $uri")
        }

        val newSortOrder = if (sortOrder.isNullOrEmpty()) {
            // If no sorting specified by called then sort on title by default
            C_TITLE
        } else sortOrder
        val cursor = queryBuilder.query(mDB, projection, selection, selectionArgs, null, null, newSortOrder)

        // register to watch a content URI for changes and notify the content resolver
        val resolver = context?.contentResolver
        if(resolver != null) {
            cursor.setNotificationUri(resolver, uri)
        }
        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val count = when (urimatcher.match(uri)) {
            PATH_TABLE -> mDB?.update(TABLE, values, selection, selectionArgs) ?: 0
            PATH_ID -> {
                val currentSelection = if (selection.isNullOrEmpty().not()) "AND ($selection)" else ""
                mDB?.update(TABLE, values, "$C_ID = ${uri.lastPathSegment} $currentSelection", selectionArgs) ?: 0
            }
            else -> throw IllegalArgumentException("Divanotes(update): Unsupported URI $uri")
        }
        context?.contentResolver?.notifyChange(uri, null)
        return count
    }
}