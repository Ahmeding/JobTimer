package tn.com.hitechart.eds.data;

public interface Key {

    String EXTRA_NAME = "name";
    String USER = "user";

    String KEY_AUTHENTIFICATION = "vjmajxydeeoqhvzj";
    String ADMIN_RAPPORT_EMAIL = "eds.rapport@gmail.com";
    //
    String TASK_TYPE_LABO = "Labo";
    String TASK_TYPE_Deplacement = "Deplacement";
    String TASK_TYPE_AUTRE = "Autre";
    //
    String KEY_TASK="Task";
    //
    String KEY_TASK_MONO="Mono Tache";
    String KEY_TASK_MULTI="Multi Tache";
    //
    int TASK_FLAG_CREATE= 11;
    int TASK_FLAG_START = 10;
    int TASK_FLAG_PAUSE = 20;
    int TASK_FLAG_STOP = 30;
    //
    int POINTAGE_IN_M = 1;
    int POINTAGE_OUT_M = 2;
    int POINTAGE_IN_AM = 3;
    int POINTAGE_OUT_AM = -1;
    //
    String TASK_STATUS_ECOURS = "E";
    String TASK_STATUS_REPARE = "R";
    String TASK_STATUS_FINIT = "F";
    String TASK_STATUS_SIMULE = "S";
    String TASK_STATUS_OK = "O";
    String TASK_STATUS_DIAGNOSTIC = "D";
}
