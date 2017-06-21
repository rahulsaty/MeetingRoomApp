package com.threepillar.meetingroomfinder.database.repository;




import com.threepillar.meetingroomfinder.database.model.University;

import io.realm.RealmResults;


public interface IUniversityRepository extends IBaseRepository {

    interface OnAddUniversityCallback {
        void onSuccess();
        void onError(String message);
    }

    interface OnGetAllUniversityCallback {
        void onSuccess(RealmResults<University> universities);
        void onError(String message);
    }

    interface OnGetUniversityByIdCallback {
        void onSuccess(University university);
        void onError(String message);
    }

    interface OnDeleteUniversityCallback {
        void onSuccess();
        void onError(String message);
    }

    void addUniversity(University university, OnAddUniversityCallback callback);

    void deleteUniversityById(String Id, OnDeleteUniversityCallback callback);

    void deleteUniversityByPosition(int position, OnDeleteUniversityCallback callback);

    void getAllUniversities(OnGetAllUniversityCallback callback);

    void getUniversityById(String id, OnGetUniversityByIdCallback callback);
}
