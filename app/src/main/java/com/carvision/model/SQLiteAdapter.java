package com.carvision.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAdapter {

    private Context context;
    private SQLiteOpenHelper sqLiteOpenHelper;

    private String CREATE_TABLE_CARS_SQL = "create table tbl_cars (id integer primary key, image text)";

    private String CREATE_TABLE_COLORS_SQL = "create table tbl_colors (id integer primary key, score real, red integer, green integer, blue integer, hex_color text, car_id integer)";

    private String CREATE_TABLE_LABELS_SQL = "create table tbl_labels (id integer primary key, label text, score real, car_id integer)";

    public SQLiteAdapter(Context context) {
        this.context = context;

        sqLiteOpenHelper = new SQLiteOpenHelper(context, "database.db", null, 1) {

            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_TABLE_CARS_SQL);
                db.execSQL(CREATE_TABLE_COLORS_SQL);
                db.execSQL(CREATE_TABLE_LABELS_SQL);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };
    }

    public long saveCar(Car car) {
        long carId = -1;

        SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();

        try {
            ContentValues values = new ContentValues();
            values.put("image", car.getImage());
            carId = database.insert("tbl_cars", null, values);
            car.setId(carId);

            database.beginTransaction();
            for (Label label : car.getLabels()) {
                label.setCarId(carId);
                values.clear();
                values.put("label", label.getLabel());
                values.put("score", label.getScore());
                values.put("car_id", label.getCarId());

                long labelId = database.insert("tbl_labels", null, values);
                Log.d("DB", "LABEL_ID:" + labelId);
            }

            for (Color color : car.getColors()) {
                color.setCarId(carId);
                values.clear();
                values.put("score", color.getScore());
                values.put("red", color.getRed());
                values.put("green", color.getGreen());
                values.put("blue", color.getBlue());
                String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
                values.put("hex_color", hex);
                values.put("car_id", color.getCarId());

                long colorId = database.insert("tbl_colors", null, values);
                Log.d("DB", "COLOR_ID:" + colorId);
            }
            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Exception ex) {
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return carId;
    }

    public List<Car> loadAll() {
        List<Car> cars = new ArrayList<>();
        SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();

        try {
            Cursor cursor = database.query(
                    "tbl_cars",
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);

            Log.d("DB", DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Car car = new Car();
                    car.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    car.setImage(cursor.getString(cursor.getColumnIndex("image")));
                    cars.add(car);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        for (Car car : cars) {
            car = loadCarLabels(car);
            car = loadCarColors(car);
        }

        return cars;
    }

    public Car load(Long id) {
        Car car = null;
        SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
        try {
            Cursor cursor = database.query(
                    "tbl_cars",
                    null,
                    "id=?",
                    new String[]{id + ""},
                    null,
                    null,
                    null,
                    null);
            Log.d("DB", DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.moveToFirst()) {
                car = new Car();
                car.setId(cursor.getLong(cursor.getColumnIndex("id")));
                car.setImage(cursor.getString(cursor.getColumnIndex("image")));
                car = loadCarLabels(car);
                car = loadCarColors(car);
            }
        } catch (Exception ex) {
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return car;
    }

    private Car loadCarLabels(Car car) {
        SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
        try {
            // load labels
            Cursor cursor = database.query(
                    "tbl_labels",
                    null,
                    "car_id=?",
                    new String[]{"" + car.getId()},
                    null,
                    null,
                    null,
                    null);

            Log.d("DB", DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Label label = new Label();
                    label.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    label.setCarId(cursor.getLong(cursor.getColumnIndex("car_id")));
                    label.setScore(cursor.getDouble(cursor.getColumnIndex("score")));
                    label.setLabel(cursor.getString(cursor.getColumnIndex("label")));

                    car.addLabel(label);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally {
            database.close();
        }

        return car;
    }

    private Car loadCarColors(Car car) {
        SQLiteDatabase database = sqLiteOpenHelper.getReadableDatabase();
        try {
            // Load colors
            Cursor cursor = database.query(
                    "tbl_colors",
                    null,
                    "car_id=?",
                    new String[]{"" + car.getId()},
                    null,
                    null,
                    null,
                    null);

            Log.d("DB", DatabaseUtils.dumpCursorToString(cursor));
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Color color = new Color();
                    color.setId(cursor.getLong(cursor.getColumnIndex("id")));
                    color.setCarId(cursor.getLong(cursor.getColumnIndex("car_id")));
                    color.setScore(cursor.getDouble(cursor.getColumnIndex("score")));
                    color.setRed(cursor.getInt(cursor.getColumnIndex("red")));
                    color.setGreen(cursor.getInt(cursor.getColumnIndex("green")));
                    color.setBlue(cursor.getInt(cursor.getColumnIndex("blue")));
                    color.setHexColor(cursor.getString(cursor.getColumnIndex("hex_color")));

                    car.addColor(color);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            Log.d("Database", "Exception:" + ex.getMessage());
        } finally {
            database.close();
        }

        return car;
    }

}
