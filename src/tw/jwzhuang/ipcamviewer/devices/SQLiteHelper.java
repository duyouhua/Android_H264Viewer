package tw.jwzhuang.ipcamviewer.devices;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteHelper extends SQLiteOpenHelper {
	public String TableNames[];
	public String FieldNames[][];
	public String FieldTypes[][];
	public static String NO_CREATE_TABLES = "no tables";
	private String message = "";
//	private String DB_PATH;
//	private String DB_NAME;
//	private Context context = null;

	public SQLiteHelper(Context context, String dbname,
			CursorFactory factory, int version, String tableNames[],
			String fieldNames[][], String fieldTypes[][]) {
		super(context, dbname, factory, version);
		TableNames = tableNames;
		FieldNames = fieldNames;
		FieldTypes = fieldTypes;
	}
	
	public SQLiteHelper(Context context, String dbname,
			CursorFactory factory, int version){
		super(context, dbname, factory, version);
//		this.context = context;
	}
	
//	/**
//	 * 開啟資料庫檔案
//	 * 
//	 * @param db_PATH_NAME
//	 *            資料庫路徑+檔名
//	 * @param openMode
//	 * 			  SQLiteDatabase.OPEN_READWRITE		:讀寫方式開啟
//	 *            SQLiteDatabase.OPEN_READONLY		:只讀取檔案
//	 *            SQLiteDatabase.CREATE_IF_NECESSARY:如果資料庫不存在則建立
//	 */
//    private void openDataBase(String db_PATH_NAME,int openMode){    	 
//    	SQLiteDatabase db = null; 
//    	db = SQLiteDatabase.openDatabase(db_PATH_NAME, null, openMode);
//    }
//    
//    /**
//	 * 開啟資料庫檔案
//	 * 
//	 * @param db_PATH_NAME
//	 *            資料庫路徑+檔名
//	 */
//    private void openDataBase(String db_PATH_NAME){
//    	
//    	openDataBase(db_PATH_NAME,SQLiteDatabase.CREATE_IF_NECESSARY);
//    }


	@Override
	public void onCreate(SQLiteDatabase db) {
		if (TableNames == null) {
			message = NO_CREATE_TABLES;
			return;
		}
		
		/* 建立table */
		for (int i = 0; i < TableNames.length; i++) {
			
			
			String sql = "CREATE TABLE " + TableNames[i] + " (";
			for (int j = 0; j < FieldNames[i].length; j++) {
				sql += FieldNames[i][j] + " " + FieldTypes[i][j] + ",";
			}
			sql = sql.substring(0, sql.length() - 1);
			sql += ")";
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		for (int i = 0; i < TableNames[i].length(); i++) {
			String sql = "DROP TABLE IF EXISTS " + TableNames[i];
			db.execSQL(sql);
		}
		onCreate(db);
	}

	public void execSQL(String sql) throws java.sql.SQLException {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(sql);
	}

	/**
	 * 查詢資料
	 * 
	 * @param table
	 *            查詢的table name
	 * @param columns
	 *            查詢的資料的欄位名稱
	 * @param selection
	 *            查詢條件字串 如：field1 = ? and field2 = ?
	 * @param selectionArgs
	 *            查詢條件的值 如：["a","b"]
	 * @param groupBy
	 *            groupBy後面的字串 如：field1,field2
	 * @param having
	 *            having後面的字串
	 * @param orderBy
	 *            orderBy後面的字串
	 * @return Cursor 包含了取得的資料集
	 */
	public Cursor select(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
		return cursor;
	}
	
	/**
	 * 查詢資料
	 * 
	 * @param table
	 *            查詢的table name
	 * @param columns
	 *            查詢的資料的欄位名稱
	 */
	public Cursor select(String table, String[] columns) {
		// TODO Auto-generated method stub
		return select(table, columns, null, null, null, null, null);
	}
	
	public Cursor select(String table, String[] columns, String selection,
			String[] selectionArgs) {
		return select(table, columns, selection, selectionArgs, null, null, null);
	}

	/**
	 * 新增資料
	 * 
	 * @param table
	 *            新增資料的table name
	 * @param fields
	 *            新增資料的欄位名稱
	 * @param values
	 *            新增資料的欄位值
	 * @return long row id
	 */
	public long insert(String table, String fields[], String values[]) {
		SQLiteDatabase db = this.getWritableDatabase();
		/* 將新增的值放入ContentValues */
		ContentValues cv = new ContentValues();
		for (int i = 0; i < fields.length; i++) {
			cv.put(fields[i], values[i]);
		}
		return db.insert(table, null, cv);
	}

	/**
	 * 刪除資料
	 * 
	 * @param table
	 *            刪除資料的table name
	 * @param where
	 *            刪除資料的條件
	 * @param whereValue
	 *            刪除資料的條件值
	 * @return int 刪除的筆數
	 */
	public int delete(String table, String where, String[] whereValue) {
		SQLiteDatabase db = this.getWritableDatabase();

		return db.delete(table, where, whereValue);
	}

	/**
	 * 更新資料
	 * 
	 * @param table
	 *            更新資料的table name
	 * @param fields
	 *            更新資料的欄位名稱
	 * @param values
	 *            更新資料的欄位值
	 * @param where
	 *            更新除資料的條件
	 * @param whereValue
	 *            更新資料的條件值
	 * @return int 更新的筆數
	 */
	public int update(String table, String updateFields[],
			String updateValues[], String where, String[] whereValue) {
		SQLiteDatabase db = this.getWritableDatabase();

		/* 將修改的值放入ContentValues */
		ContentValues cv = new ContentValues();
		for (int i = 0; i < updateFields.length; i++) {
			cv.put(updateFields[i], updateValues[i]);
		}
		return db.update(table, cv, where, whereValue);
	}
	
	/**
	 * 更新資料
	 * 
	 * @param table
	 *            更新資料的table name
	 * @param fields
	 *            更新資料的欄位名稱
	 * @param values
	 *            更新資料的欄位值
	 * @param where
	 *            更新除資料的條件
	 * @param whereValue
	 *            更新資料的條件值
	 * @return int 更新的筆數
	 */
	public int update(String table, String[] updateFields, int[] updateValues,
			String where, String[] whereValue) {
		SQLiteDatabase db = this.getWritableDatabase();

		/* 將修改的值放入ContentValues */
		ContentValues cv = new ContentValues();
		for (int i = 0; i < updateFields.length; i++) {
			cv.put(updateFields[i], updateValues[i]);
		}
		return db.update(table, cv, where, whereValue);
	}

	public String getMessage() {
		return message;
	}

	@Override
	public synchronized void close() {
		// TODO Auto-generated method stub
		super.close();
	}
	
}
