package dao;

public class DAO {
    private DAO dao = new DAO();
    private DAO() {}
    public DAO getInstance(){
        return dao;
    }
}
