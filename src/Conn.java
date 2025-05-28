public class Conn {
    Conn(){
        try{
            Class.forName("con.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
