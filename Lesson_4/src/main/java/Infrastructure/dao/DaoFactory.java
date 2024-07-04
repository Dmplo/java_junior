package Infrastructure.dao;

import java.util.Arrays;
import java.util.List;

public abstract class DaoFactory {

    private static final List<iDao> daoList = Arrays.asList(
            new PostDAOImpl(),
            new PostCommentDAOImpl(),
            new UserDAOImpl()
    );

    public static <T extends iDao> T getDao(Class<T> cl){
        for(iDao dao : daoList)
            if (cl.isInstance(dao))
                return cl.cast(dao);
        return null;
    }
}