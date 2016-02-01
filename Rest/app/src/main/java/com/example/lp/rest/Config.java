package com.example.lp.rest;

/**
 * Created by maxencebeno on 28/01/2016.
 */
public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_ADD = "http://10.0.2.2/api.php";
    public static final String URL_GET_ALL = "http://10.0.2.2/api.php?action=all";
    public static final String URL_GET_VILLE = "http://10.0.2.2/api.php?filtre=codeinsee&q=";
    public static final String URL_UPDATE_VILLE = "http://10.0.2.2/api.php";
    public static final String URL_DELETE_VILLE = "http://10.0.2.2/api.php";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_VILLE_NOM = "nom";
    public static final String KEY_VILLE_MAJ = "maj";
    public static final String KEY_VILLE_CODE_POSTAL = "codepostal";
    public static final String KEY_VILLE_CODE_INSEE = "codeinsee";
    public static final String KEY_VILLE_CODE_REGION = "coderegion";
    public static final String KEY_VILLE_LATITUDE = "latitude";
    public static final String KEY_VILLE_LONGITUDE = "longitude";
    public static final String KEY_VILLE_ELOIGNEMENT = "eloignement";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "villes";
    public static final String TAG_CODE_INSEE = "Code_INSEE";
    public static final String TAG_NOM = "Nom_Ville";
    public static final String TAG_MAJ = "MAJ";
    public static final String TAG_CP = "Code_Postal";
    public static final String TAG_CODE_REGION = "Code_Region";
    public static final String TAG_LONGITUDE = "Longitude";
    public static final String TAG_LATITUDE = "Latitude";
    public static final String TAG_ELOIGNEMENT = "Eloignement";

    //employee id to pass with intent
    public static final String VILLE_ID = "codeinsee";
}
