package za.ac.uj.eve.gradhack_mobile;

import java.util.Comparator;

public class UserComparator implements Comparator<User>
{
    @Override
    public int compare(User x, User y){
        return Integer.compare(x.Points,y.Points);
    }

}
