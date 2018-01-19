package com.luxoft.carsapp.data.cache;

import android.content.Context;

import com.luxoft.carsapp.data.entity.CarEntity;
import com.luxoft.carsapp.data.entity.CarEntityDao;
import com.luxoft.carsapp.data.exception.CarNotFoundException;
import com.luxoft.carsapp.domain.executor.ThreadExecutor;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class CarCacheImpl implements CarCache {
    private static final String SETTINGS_FILE_NAME = "product_settings";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "product_last_cache_update";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;
    private final Context context;
    private final FileManager fileManager;
    private final GetDaoSession getDaoSession;
    private final ThreadExecutor threadExecutor;

    @Inject
    public CarCacheImpl(Context context, GetDaoSession getDaoSession, FileManager fileManager, ThreadExecutor executor) {
        if (context == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.getDaoSession = getDaoSession;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override
    public Observable<CarEntity> get(String model) {
        return Observable.create(subscriber -> {
            CarEntity productEntity = new CarEntity();
            CarEntityDao carEntityDao = getDaoSession.getDaoSession().getCarEntityDao();
            Query<CarEntity> query = carEntityDao.queryBuilder().where(CarEntityDao.Properties.Model.eq(model)).build();
            List<CarEntity> productEntities = query.list();
            productEntity = productEntities.get(0);
            if (productEntity != null) {
                subscriber.onNext(productEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new CarNotFoundException());
            }
        });
    }

    @Override
    public boolean isCached(String model) {
        if (getDaoSession.getDaoSession().getCarEntityDao().count() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();
        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);
        if (expired) {
            this.evictAll();
        }
        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor());
    }

    @Override
    public Observable<List<CarEntity>> getCars() {
        return null;
    }

    @Override
    public Observable<List<CarEntity>> getCars(String manufacturer) {
        return Observable.create(subscriber -> {
            CarEntityDao carEntityDao = getDaoSession.getDaoSession().getCarEntityDao();
            Query<CarEntity> query = carEntityDao.queryBuilder().orderAsc(CarEntityDao.Properties.Model).where(CarEntityDao.Properties.Manufacturer.eq(manufacturer)).build();
            List<CarEntity> carEntities = query.list();
            if (carEntities != null) {
                subscriber.onNext(carEntities);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new CarNotFoundException());
            }
        });
    }

    @Override
    public void putCar(CarEntity carEntity) {
    }

    @Override
    public void putCars(List<CarEntity> carEntities) {
        if (carEntities != null) {
            if (!isCarsCached()) {
                this.executeAsynchronously(new CacheWriter(getDaoSession, carEntities));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCarsCached() {
        if (getDaoSession.getDaoSession().getCarEntityDao().count() > 0) {
            return true;
        } else {
            return false;
        }
    }
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME, SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    private static class CacheWriter implements Runnable {
        private final GetDaoSession getDaoSession;
        private final List<CarEntity> carEntities;

        CacheWriter(GetDaoSession getDaoSession, List<CarEntity> productEntities) {
            this.getDaoSession = getDaoSession;
            this.carEntities = productEntities;
        }

        @Override
        public void run() {
            CarEntityDao carEntityDao = getDaoSession.getDaoSession().getCarEntityDao();
            for (CarEntity carEntity : carEntities) {
                carEntityDao.insert(carEntity);
            }
        }
    }

    private static class CacheEvictor implements Runnable {

        CacheEvictor() {
        }

        @Override
        public void run() {
        }
    }
}
