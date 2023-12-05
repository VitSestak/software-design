package es.deusto.ingenieria.sd.strava.user.dao;

import es.deusto.ingenieria.sd.strava.common.BaseDao;
import es.deusto.ingenieria.sd.strava.user.model.UserProfile;

import javax.persistence.PersistenceException;
import java.util.Objects;

public class UserProfileDao extends BaseDao<UserProfile> {

    private static UserProfileDao instance;

    private UserProfileDao() {
        super(UserProfile.class);
    }

    public static synchronized UserProfileDao getInstance() {
        if (instance == null) {
            instance = new UserProfileDao();
        }
        return instance;
    }

    public UserProfile findUserProfileByEmail(String email) {
        Objects.requireNonNull(email);
        var tx = em.getTransaction();

        UserProfile result;

        try {
            tx.begin();
            result = em.find(UserProfile.class, email);
            tx.commit();
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
        return result;
    }
}
