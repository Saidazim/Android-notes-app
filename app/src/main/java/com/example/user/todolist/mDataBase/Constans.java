package com.example.user.todolist.mDataBase;

/**
 * Created by User on 02.04.2018.
 */

public class Constans {
    //COLUMNS
    static final String ROW_ID="id";
    static final String NAME="name";


    //DB PROPERTIES
    static final String DB_NAME="todo_DB";
    static final String TB_NAME="todo_TB";
    static final int DB_VERSION=1;

    //CREATE TB STMT
    static final String CREATE_TB="CREATE TABLE todo_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL);";

    //INSERT  SAMPLE DATA
    static final String INSERT_DATA_1="INSERT INTO todo_TB VALUES (0, 'To do');";
    static final String INSERT_DATA_2="INSERT INTO todo_TB VALUES (1, 'To go');";
    static final String INSERT_DATA_3="INSERT INTO todo_TB VALUES (2, 'To eat');";
    static final String INSERT_DATA_4="INSERT INTO todo_TB VALUES (3, 'To sit');";
    static final String INSERT_DATA_5="INSERT INTO todo_TB VALUES (5, 'To jump');";
    static final String INSERT_DATA_6="INSERT INTO todo_TB VALUES (6, 'To drink');";



    //DROP TB STMT
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;

}
