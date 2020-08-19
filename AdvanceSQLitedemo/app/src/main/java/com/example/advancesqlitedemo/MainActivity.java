package com.example.advancesqlitedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
        // SQL is Structured Query Language
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            SQLiteDatabase my_database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

            my_database.execSQL("CREATE TABLE IF NOT EXISTS users(name VARCHAR,age INT(4))");
            /*
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Lorem',19)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Ipsum',46)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Joe Tim',47)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Dog Cat',18)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Albert Monster',35)");
            */
            /*
            Cursor c = my_database.rawQuery("SELECT * FROM users", null);
            int name_index = c.getColumnIndex("name");
            int age_index = c.getColumnIndex("age");
            c.moveToFirst();
            while (c != null) {
                System.out.println("Name: " + c.getString(name_index) + "\tAge: " + c.getInt(age_index));
                c.moveToNext();
            }
            */
            /*
            //Finding data with age < 25
            Cursor c1=my_database.rawQuery("SELECT * FROM users WHERE age<25",null);
            int name_index=c1.getColumnIndex("name");
            int age_index=c1.getColumnIndex("age");
            c1.moveToFirst();
            while (c1!=null){
                System.out.println("Name: "+c1.getString(name_index)+"\tAge: "+c1.getInt(age_index));
                c1.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        */
        /*
        //Finding data with name=Bir Anmol Singh
        Cursor c1=my_database.rawQuery("SELECT * FROM users WHERE name='Dog Cat'",null);
        int name_index=c1.getColumnIndex("name");
        int age_index=c1.getColumnIndex("age");
        c1.moveToFirst();
        while (c1!=null){
            System.out.println("Name: "+c1.getString(name_index)+"\r\tAge: "+c1.getInt(age_index));
            c1.moveToNext();
        }

    }catch(Exception e){
        e.printStackTrace();
    }*/
        /*
            //Finding data with age >25 and name=Sarabjit Singh
            Cursor c1=my_database.rawQuery("SELECT * FROM users WHERE age>25 AND name='John'",null);
            int name_index=c1.getColumnIndex("name");
            int age_index=c1.getColumnIndex("age");
            c1.moveToFirst();
            while (c1!=null){
                System.out.println("Name: "+c1.getString(name_index)+"\tAge: "+c1.getInt(age_index));
                c1.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        */
        /*
            //Finding data with name starting from D
            Cursor c1=my_database.rawQuery("SELECT * FROM users WHERE name LIKE 'D%'",null);
            int name_index=c1.getColumnIndex("name");
            int age_index=c1.getColumnIndex("age");
            c1.moveToFirst();
            while (c1!=null){
                System.out.println("Name: "+c1.getString(name_index)+"\tAge: "+c1.getInt(age_index));
                c1.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }*/
        /*
            //Finding data with age name having i in it
            Cursor c1=my_database.rawQuery("SELECT * FROM users WHERE name LIKE '%i%'",null);
            int name_index=c1.getColumnIndex("name");
            int age_index=c1.getColumnIndex("age");
            c1.moveToFirst();
            while (c1!=null){
                System.out.println("Name: "+c1.getString(name_index)+"\tAge: "+c1.getInt(age_index));
                c1.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        */
        /*
            //Finding data with age name having i in it but limiting results to 2
            Cursor c1=my_database.rawQuery("SELECT * FROM users WHERE name LIKE '%i%' LIMIT 2",null);
            int name_index=c1.getColumnIndex("name");
            int age_index=c1.getColumnIndex("age");
            c1.moveToFirst();
            while (c1!=null){
                System.out.println("Name: "+c1.getString(name_index)+"\tAge: "+c1.getInt(age_index));
                c1.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }*/
            //for deleting any user
            // my_database.rawQuery("DELETE FROM users WHERE name='Albert Monster",null);
            //You can also add LIMIT in the query

            // UPDATING A USER
            //my_database.rawQuery("UPDATE users SET age=20 WHERE name='Lorem'");

            my_database.execSQL("CREATE TABLE IF NOT EXISTS newusers(name VARCHAR,age INT(4),id INTEGER PRIMARY KEY)");

            //this id or key starts at 1 and auto increase when we add elements
            /*
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Lorem',19)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Ipsum',46)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Joe Tim',47)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Dog Cat',18)");
            my_database.execSQL("INSERT INTO users(name,age) VALUES ('Albert Monster',35)");
            */
            /*
            Cursor c = my_database.rawQuery("SELECT * FROM newusers", null);
            int name_index=c.getColumnIndex("name");
            int age_index=c.getColumnIndex("age");
            int id_index=c.getColumnIndex("id");
            c.moveToFirst();
            while (c!=null){
                System.out.println("Name: "+c.getString(name_index)+"\tAge: "+c.getInt(age_index)+"\n"+c.getInt(id_index));
                c.moveToNext();
            }
            */
            my_database.execSQL("DELETE FROM newusers WHERE id=4");
            Cursor c=my_database.rawQuery("SELECT * FROM newusers",null);
            int name_index=c.getColumnIndex("name");
            int age_index=c.getColumnIndex("age");
            int id_index=c.getColumnIndex("id");
            c.moveToFirst();
            while (c!=null){
                System.out.println("Name: "+c.getString(name_index)+"\tAge: "+c.getInt(age_index)+"\n"+c.getInt(id_index));
                c.moveToNext();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
