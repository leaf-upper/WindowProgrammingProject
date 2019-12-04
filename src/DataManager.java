public class DataManager {

    private static DataManager _instance;


    private DataManager()
    {

    }

    public static DataManager getInstance()
    {
        if(_instance == null)
            _instance = new DataManager();

        return _instance;
    }

}
