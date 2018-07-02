package za.ac.uj.eve.gradhack_mobile;

public class User
{
    public String Name;
    public String ContactNumber;
    public String Email;
    public String Location;
    public int Points;
    public int Type;

    public User()
    {

    }

    public User(String name,String contactNo,String email, String location, int points,int type)
    {
        Name = name;
        ContactNumber = contactNo;
        Email = email;
        Location = location;
        Points = points;
        Type = type;
    }
}
