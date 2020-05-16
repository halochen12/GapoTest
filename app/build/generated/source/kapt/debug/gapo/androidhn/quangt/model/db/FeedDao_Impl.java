package gapo.androidhn.quangt.model.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import gapo.androidhn.quangt.model.entity.Avatar;
import gapo.androidhn.quangt.model.entity.Content;
import gapo.androidhn.quangt.model.entity.Feed;
import gapo.androidhn.quangt.model.entity.Image;
import gapo.androidhn.quangt.model.entity.Publisher;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FeedDao_Impl implements FeedDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Feed> __insertionAdapterOfFeed;

  private final DataConverter __dataConverter = new DataConverter();

  public FeedDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFeed = new EntityInsertionAdapter<Feed>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `feeds` (`title`,`document_id`,`description`,`content_type`,`origin_url`,`published_date`,`publisher`,`avatar`,`content`,`images`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Feed value) {
        if (value.getTitle() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getTitle());
        }
        if (value.getDocument_id() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getDocument_id());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getContent_type() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getContent_type());
        }
        if (value.getOrigin_url() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getOrigin_url());
        }
        if (value.getPublished_date() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getPublished_date());
        }
        final String _tmp;
        _tmp = __dataConverter.publisherToJson(value.getPublisher());
        if (_tmp == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, _tmp);
        }
        final String _tmp_1;
        _tmp_1 = __dataConverter.avatarToJson(value.getAvatar());
        if (_tmp_1 == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, _tmp_1);
        }
        final String _tmp_2;
        _tmp_2 = __dataConverter.contentToJson(value.getContent());
        if (_tmp_2 == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, _tmp_2);
        }
        final String _tmp_3;
        _tmp_3 = __dataConverter.imagesToJson(value.getImages());
        if (_tmp_3 == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, _tmp_3);
        }
      }
    };
  }

  @Override
  public void add(final List<Feed> products) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFeed.insert(products);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Feed> findAll() {
    final String _sql = "SELECT * FROM feeds";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDocumentId = CursorUtil.getColumnIndexOrThrow(_cursor, "document_id");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfContentType = CursorUtil.getColumnIndexOrThrow(_cursor, "content_type");
      final int _cursorIndexOfOriginUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "origin_url");
      final int _cursorIndexOfPublishedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "published_date");
      final int _cursorIndexOfPublisher = CursorUtil.getColumnIndexOrThrow(_cursor, "publisher");
      final int _cursorIndexOfAvatar = CursorUtil.getColumnIndexOrThrow(_cursor, "avatar");
      final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
      final int _cursorIndexOfImages = CursorUtil.getColumnIndexOrThrow(_cursor, "images");
      final List<Feed> _result = new ArrayList<Feed>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Feed _item;
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        final String _tmpDocument_id;
        _tmpDocument_id = _cursor.getString(_cursorIndexOfDocumentId);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        final String _tmpContent_type;
        _tmpContent_type = _cursor.getString(_cursorIndexOfContentType);
        final String _tmpOrigin_url;
        _tmpOrigin_url = _cursor.getString(_cursorIndexOfOriginUrl);
        final String _tmpPublished_date;
        _tmpPublished_date = _cursor.getString(_cursorIndexOfPublishedDate);
        final Publisher _tmpPublisher;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfPublisher);
        _tmpPublisher = __dataConverter.publisherFromJson(_tmp);
        final Avatar _tmpAvatar;
        final String _tmp_1;
        _tmp_1 = _cursor.getString(_cursorIndexOfAvatar);
        _tmpAvatar = __dataConverter.avatarFromJson(_tmp_1);
        final Content _tmpContent;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfContent);
        _tmpContent = __dataConverter.contentFromJson(_tmp_2);
        final List<Image> _tmpImages;
        final String _tmp_3;
        _tmp_3 = _cursor.getString(_cursorIndexOfImages);
        _tmpImages = __dataConverter.imagesFromJson(_tmp_3);
        _item = new Feed(_tmpTitle,_tmpDocument_id,_tmpDescription,_tmpContent_type,_tmpOrigin_url,_tmpPublished_date,_tmpPublisher,_tmpAvatar,_tmpContent,_tmpImages);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
