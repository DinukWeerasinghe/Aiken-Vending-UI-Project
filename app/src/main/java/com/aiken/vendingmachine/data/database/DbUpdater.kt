package com.aiken.vendingmachine.data.database

import android.content.Context
import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import android.util.SparseIntArray
import android.util.Xml
import java.io.IOException
import java.io.InputStream

object DbUpdater {
    private const val TAG = "DbUpdater"
    private const val UPDATE_FILE = "update_db.xml"

    fun loadUpdateXml(context: Context): Array<Migration>? {
        val inputStream: InputStream = try {
            context.assets.open(UPDATE_FILE)
        } catch (e: IOException) {
            Log.e(TAG, "update_db.xml not found", e)
            return null
        }

        val parser: XmlPullParser = Xml.newPullParser()
        val migrations = mutableListOf<ExtMigration>()

        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(inputStream, "UTF-8")

            var eventType = parser.eventType
            var migration: ExtMigration? = null

            while (eventType != XmlPullParser.END_DOCUMENT) {
                val nodeName = parser.name
                when (eventType) {
                    XmlPullParser.START_TAG -> when (nodeName) {
                        "version" -> {
                            val version = parser.getAttributeValue(null, "code")?.toIntOrNull()
                                ?: parser.getAttributeValue(0).toInt()
                            migration = ExtMigration(version)
                            migrations.add(migration)
                        }
                        "sql" -> {
                            val delayAttr = parser.getAttributeValue(null, "delay")
                            val sql = parser.nextText()
                            if (delayAttr != null) {
                                migration?.addSql(sql, delayAttr.toInt())
                            } else {
                                migration?.addSql(sql)
                            }
                        }
                    }
                }
                eventType = parser.next()
            }

            Log.d(TAG, "Loaded ${migrations.size} migrations")
            return if (migrations.isNotEmpty()) migrations.toTypedArray() else null

        } catch (e: XmlPullParserException) {
            Log.e(TAG, "XML parse error", e)
        } catch (e: IOException) {
            Log.e(TAG, "IO error", e)
        }

        return null
    }

    private class ExtMigration(version: Int) : Migration(version - 1, version) {
        private val sqls = mutableListOf<String>()
        private val delays = SparseIntArray()

        fun addSql(sql: String, delay: Int) {
            sqls.add(sql)
            delays.put(sqls.size - 1, delay)
        }

        fun addSql(sql: String) {
            sqls.add(sql)
        }

        override fun migrate(database: SupportSQLiteDatabase) {
            sqls.forEachIndexed { index, sql ->
                val delay = delays.get(index, 0)
                if (delay > 0) Thread.sleep(delay.toLong())
                database.execSQL(sql)
                Log.i(TAG, "Executed migration SQL: $sql")
            }
        }
    }
}
