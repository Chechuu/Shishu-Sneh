package com.shishusneh.core.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BabyDatabase_Impl extends BabyDatabase {
  private volatile BabyDao _babyDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `growth_records` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER NOT NULL, `weight` REAL NOT NULL, `height` REAL NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `milestones` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `category` TEXT NOT NULL, `isAchieved` INTEGER NOT NULL, `achievedDate` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `vaccinations` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `disease` TEXT NOT NULL, `dueDateMillis` INTEGER NOT NULL, `isDone` INTEGER NOT NULL, `dateLabel` TEXT NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '97e4e0ac379261f391a99095b9f84438')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `growth_records`");
        db.execSQL("DROP TABLE IF EXISTS `milestones`");
        db.execSQL("DROP TABLE IF EXISTS `vaccinations`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsGrowthRecords = new HashMap<String, TableInfo.Column>(4);
        _columnsGrowthRecords.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrowthRecords.put("date", new TableInfo.Column("date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrowthRecords.put("weight", new TableInfo.Column("weight", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrowthRecords.put("height", new TableInfo.Column("height", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGrowthRecords = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGrowthRecords = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGrowthRecords = new TableInfo("growth_records", _columnsGrowthRecords, _foreignKeysGrowthRecords, _indicesGrowthRecords);
        final TableInfo _existingGrowthRecords = TableInfo.read(db, "growth_records");
        if (!_infoGrowthRecords.equals(_existingGrowthRecords)) {
          return new RoomOpenHelper.ValidationResult(false, "growth_records(com.shishusneh.core.data.GrowthRecord).\n"
                  + " Expected:\n" + _infoGrowthRecords + "\n"
                  + " Found:\n" + _existingGrowthRecords);
        }
        final HashMap<String, TableInfo.Column> _columnsMilestones = new HashMap<String, TableInfo.Column>(5);
        _columnsMilestones.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("isAchieved", new TableInfo.Column("isAchieved", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMilestones.put("achievedDate", new TableInfo.Column("achievedDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMilestones = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMilestones = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMilestones = new TableInfo("milestones", _columnsMilestones, _foreignKeysMilestones, _indicesMilestones);
        final TableInfo _existingMilestones = TableInfo.read(db, "milestones");
        if (!_infoMilestones.equals(_existingMilestones)) {
          return new RoomOpenHelper.ValidationResult(false, "milestones(com.shishusneh.core.data.Milestone).\n"
                  + " Expected:\n" + _infoMilestones + "\n"
                  + " Found:\n" + _existingMilestones);
        }
        final HashMap<String, TableInfo.Column> _columnsVaccinations = new HashMap<String, TableInfo.Column>(6);
        _columnsVaccinations.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("disease", new TableInfo.Column("disease", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("dueDateMillis", new TableInfo.Column("dueDateMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("isDone", new TableInfo.Column("isDone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVaccinations.put("dateLabel", new TableInfo.Column("dateLabel", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVaccinations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVaccinations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVaccinations = new TableInfo("vaccinations", _columnsVaccinations, _foreignKeysVaccinations, _indicesVaccinations);
        final TableInfo _existingVaccinations = TableInfo.read(db, "vaccinations");
        if (!_infoVaccinations.equals(_existingVaccinations)) {
          return new RoomOpenHelper.ValidationResult(false, "vaccinations(com.shishusneh.core.data.Vaccination).\n"
                  + " Expected:\n" + _infoVaccinations + "\n"
                  + " Found:\n" + _existingVaccinations);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "97e4e0ac379261f391a99095b9f84438", "9668d567bc77d34dbb87d0082e526762");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "growth_records","milestones","vaccinations");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `growth_records`");
      _db.execSQL("DELETE FROM `milestones`");
      _db.execSQL("DELETE FROM `vaccinations`");
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
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(BabyDao.class, BabyDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public BabyDao babyDao() {
    if (_babyDao != null) {
      return _babyDao;
    } else {
      synchronized(this) {
        if(_babyDao == null) {
          _babyDao = new BabyDao_Impl(this);
        }
        return _babyDao;
      }
    }
  }
}
