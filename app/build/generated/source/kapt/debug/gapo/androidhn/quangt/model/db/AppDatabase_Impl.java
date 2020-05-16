package gapo.androidhn.quangt.model.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile FeedDao _feedDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `feeds` (`title` TEXT NOT NULL, `document_id` TEXT NOT NULL, `description` TEXT NOT NULL, `content_type` TEXT NOT NULL, `origin_url` TEXT NOT NULL, `published_date` TEXT NOT NULL, `publisher` TEXT NOT NULL, `avatar` TEXT, `content` TEXT, `images` TEXT, PRIMARY KEY(`title`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '53e348e18875c2dc58398da9ae650293')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `feeds`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFeeds = new HashMap<String, TableInfo.Column>(10);
        _columnsFeeds.put("title", new TableInfo.Column("title", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("document_id", new TableInfo.Column("document_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("content_type", new TableInfo.Column("content_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("origin_url", new TableInfo.Column("origin_url", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("published_date", new TableInfo.Column("published_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("publisher", new TableInfo.Column("publisher", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("avatar", new TableInfo.Column("avatar", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFeeds.put("images", new TableInfo.Column("images", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFeeds = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFeeds = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFeeds = new TableInfo("feeds", _columnsFeeds, _foreignKeysFeeds, _indicesFeeds);
        final TableInfo _existingFeeds = TableInfo.read(_db, "feeds");
        if (! _infoFeeds.equals(_existingFeeds)) {
          return new RoomOpenHelper.ValidationResult(false, "feeds(gapo.androidhn.quangt.model.entity.Feed).\n"
                  + " Expected:\n" + _infoFeeds + "\n"
                  + " Found:\n" + _existingFeeds);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "53e348e18875c2dc58398da9ae650293", "bd2906b76bc2a8b2b9ba1a4e5a8ff5b8");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "feeds");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `feeds`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public FeedDao getFeedDao() {
    if (_feedDao != null) {
      return _feedDao;
    } else {
      synchronized(this) {
        if(_feedDao == null) {
          _feedDao = new FeedDao_Impl(this);
        }
        return _feedDao;
      }
    }
  }
}
