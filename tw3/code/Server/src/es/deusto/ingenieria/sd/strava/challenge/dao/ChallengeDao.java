package es.deusto.ingenieria.sd.strava.challenge.dao;

import es.deusto.ingenieria.sd.strava.challenge.model.Challenge;
import es.deusto.ingenieria.sd.strava.common.BaseDao;

import javax.persistence.NoResultException;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class ChallengeDao extends BaseDao<Challenge> {

    private static ChallengeDao instance;

    private ChallengeDao() {
        super(Challenge.class);
    }

    public static synchronized ChallengeDao getInstance() {
        if (instance == null) {
            instance = new ChallengeDao();
        }
        return instance;
    }

    public List<Challenge> findAllActive(Date endDate) {
        var tx = em.getTransaction();

        List<Challenge> result;

        try {
            tx.begin();
            result = em.createNamedQuery("Challenge.findAllActive", Challenge.class)
                     .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
                     .getResultList();
            tx.commit();
        } catch (NoResultException e) {
            return null;
        }
        return result;
    }
}
