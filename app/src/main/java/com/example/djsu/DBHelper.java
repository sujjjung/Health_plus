package com.example.djsu;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private  static final int DB_VERSION = 1;
    private  static final String DB_NAME = "food.db";
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스 생성 시 호출
        // 데이터베이스 > 테이블 > 컬럼 > 값
        db.execSQL("CREATE TABLE IF NOT EXISTS foodList(id TEXT PRIMARY KEY, FoodName TEXT NOT NULL,FoodKcal TEXT NOT NULL, writeDate TEXT NOT NULL)"); //테이블 이미 있을시 실행하지 말라는 뜻
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
    // SELECT 문
    private ArrayList<Food> getFoodList(){
        ArrayList<Food> foodArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM foodList ORDER BY writeDate DESC", null);
        if(cursor.getCount() != 0){
            while(cursor.moveToNext()){
               String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String FoodName = cursor.getString(cursor.getColumnIndexOrThrow("FoodName"));
                String FoodKcal = cursor.getString(cursor.getColumnIndexOrThrow("FoodKcal"));
                String writeDate = cursor.getString(cursor.getColumnIndexOrThrow("writeDate"));

                Food food = new Food();
                food.setId(id);
                food.setFoodName(FoodName);
                food.setFoodKcal(FoodKcal);
                food.setWriteDate(writeDate);
                foodArrayList.add(food);
            }
        }
        cursor.close();

        return foodArrayList;
    }
    // INSERT 문
    public void InsertFood(String FoodName, String FoodKcal, String writeDate){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO foodList (FoodName, FoodKcal, writeDate) VALUES('" + FoodName + "','" + FoodKcal + "','" + writeDate + "' );");
    }

    // UPDATE 문
    public void UpdateFood(String FoodName, String FoodKcal, String writeDate, int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE foodList SET FoodName = '" + FoodName + "', FoodKcal = '" + FoodKcal + "', writeDate = '" + writeDate + "' WHERE id'" + id + "'");
    }
    // DELETE 문
    public void DeleteFood(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM foodList WHERE id = '" + id + "'");
    }
}
