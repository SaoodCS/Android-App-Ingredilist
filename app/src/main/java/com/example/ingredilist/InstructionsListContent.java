package com.example.ingredilist;
// content for the instructions list SQLite database

import android.provider.BaseColumns;

public class InstructionsListContent {

    private InstructionsListContent(){}

    public static final class InstructionsListEntry implements BaseColumns {

        public static final String TABLE_NAME = "instructionsList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DURATION = "duration";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}