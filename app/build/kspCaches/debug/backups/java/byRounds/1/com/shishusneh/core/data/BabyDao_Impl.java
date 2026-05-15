package com.shishusneh.core.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BabyDao_Impl implements BabyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<GrowthRecord> __insertionAdapterOfGrowthRecord;

  private final EntityInsertionAdapter<Milestone> __insertionAdapterOfMilestone;

  private final EntityInsertionAdapter<Vaccination> __insertionAdapterOfVaccination;

  public BabyDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGrowthRecord = new EntityInsertionAdapter<GrowthRecord>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `growth_records` (`id`,`date`,`weight`,`height`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final GrowthRecord entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getDate());
        statement.bindDouble(3, entity.getWeight());
        statement.bindDouble(4, entity.getHeight());
      }
    };
    this.__insertionAdapterOfMilestone = new EntityInsertionAdapter<Milestone>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `milestones` (`id`,`title`,`category`,`isAchieved`,`achievedDate`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Milestone entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getCategory());
        final int _tmp = entity.isAchieved() ? 1 : 0;
        statement.bindLong(4, _tmp);
        if (entity.getAchievedDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getAchievedDate());
        }
      }
    };
    this.__insertionAdapterOfVaccination = new EntityInsertionAdapter<Vaccination>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vaccinations` (`id`,`name`,`disease`,`dueDateMillis`,`isDone`,`dateLabel`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Vaccination entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getDisease());
        statement.bindLong(4, entity.getDueDateMillis());
        final int _tmp = entity.isDone() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getDateLabel());
      }
    };
  }

  @Override
  public Object insertGrowthRecord(final GrowthRecord record,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfGrowthRecord.insert(record);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertMilestone(final Milestone milestone,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMilestone.insert(milestone);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertVaccination(final Vaccination vaccination,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfVaccination.insert(vaccination);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<GrowthRecord>> getAllGrowthRecords() {
    final String _sql = "SELECT * FROM growth_records ORDER BY date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"growth_records"}, new Callable<List<GrowthRecord>>() {
      @Override
      @NonNull
      public List<GrowthRecord> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfHeight = CursorUtil.getColumnIndexOrThrow(_cursor, "height");
          final List<GrowthRecord> _result = new ArrayList<GrowthRecord>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final GrowthRecord _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final long _tmpDate;
            _tmpDate = _cursor.getLong(_cursorIndexOfDate);
            final float _tmpWeight;
            _tmpWeight = _cursor.getFloat(_cursorIndexOfWeight);
            final float _tmpHeight;
            _tmpHeight = _cursor.getFloat(_cursorIndexOfHeight);
            _item = new GrowthRecord(_tmpId,_tmpDate,_tmpWeight,_tmpHeight);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Milestone>> getAllMilestones() {
    final String _sql = "SELECT * FROM milestones";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"milestones"}, new Callable<List<Milestone>>() {
      @Override
      @NonNull
      public List<Milestone> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfIsAchieved = CursorUtil.getColumnIndexOrThrow(_cursor, "isAchieved");
          final int _cursorIndexOfAchievedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "achievedDate");
          final List<Milestone> _result = new ArrayList<Milestone>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Milestone _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            final boolean _tmpIsAchieved;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsAchieved);
            _tmpIsAchieved = _tmp != 0;
            final Long _tmpAchievedDate;
            if (_cursor.isNull(_cursorIndexOfAchievedDate)) {
              _tmpAchievedDate = null;
            } else {
              _tmpAchievedDate = _cursor.getLong(_cursorIndexOfAchievedDate);
            }
            _item = new Milestone(_tmpId,_tmpTitle,_tmpCategory,_tmpIsAchieved,_tmpAchievedDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Vaccination>> getAllVaccinations() {
    final String _sql = "SELECT * FROM vaccinations ORDER BY dueDateMillis ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"vaccinations"}, new Callable<List<Vaccination>>() {
      @Override
      @NonNull
      public List<Vaccination> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDisease = CursorUtil.getColumnIndexOrThrow(_cursor, "disease");
          final int _cursorIndexOfDueDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateMillis");
          final int _cursorIndexOfIsDone = CursorUtil.getColumnIndexOrThrow(_cursor, "isDone");
          final int _cursorIndexOfDateLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "dateLabel");
          final List<Vaccination> _result = new ArrayList<Vaccination>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Vaccination _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpDisease;
            _tmpDisease = _cursor.getString(_cursorIndexOfDisease);
            final long _tmpDueDateMillis;
            _tmpDueDateMillis = _cursor.getLong(_cursorIndexOfDueDateMillis);
            final boolean _tmpIsDone;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDone);
            _tmpIsDone = _tmp != 0;
            final String _tmpDateLabel;
            _tmpDateLabel = _cursor.getString(_cursorIndexOfDateLabel);
            _item = new Vaccination(_tmpId,_tmpName,_tmpDisease,_tmpDueDateMillis,_tmpIsDone,_tmpDateLabel);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
