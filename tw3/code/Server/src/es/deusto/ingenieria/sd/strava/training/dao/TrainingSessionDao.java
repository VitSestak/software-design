package es.deusto.ingenieria.sd.strava.training.dao;

import es.deusto.ingenieria.sd.strava.common.BaseDao;
import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;

public class TrainingSessionDao extends BaseDao<TrainingSession> {

    private static TrainingSessionDao instance;

    private TrainingSessionDao() {
        super(TrainingSession.class);
    }

    public static synchronized TrainingSessionDao getInstance() {
        if (instance == null) {
            instance = new TrainingSessionDao();
        }
        return instance;
    }
}
