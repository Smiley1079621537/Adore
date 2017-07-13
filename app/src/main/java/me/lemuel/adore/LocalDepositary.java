package me.lemuel.adore;

import android.database.sqlite.SQLiteDatabase;

public class LocalDepositary {

    private static final String DAO_TABLE_NAME = "dao";

    private static class LocalDepositaryHolder {
        private static LocalDepositary depositary = new LocalDepositary();
    }

    public static LocalDepositary getInstance() {
        return LocalDepositaryHolder.depositary;
    }

    public void getNote(AdoreCallback<NoteDao> callback) {
        SQLiteDatabase database = new DaoMaster
                .DevOpenHelper(App.getAppContext(), DAO_TABLE_NAME, null).getWritableDatabase();
        DaoSession daoSession = new DaoMaster(database).newSession();
        NoteDao noteDao = daoSession.getNoteDao();
        callback.onSuccess(noteDao);
    }
}
