package com.example.adutucart5.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.adutucart5.Database.FirebaseRepo;
import com.example.adutucart5.model.CustomerOrderList;
import com.google.firebase.database.DatabaseError;

import java.util.List;

public class FirebaseViewModel extends ViewModel implements FirebaseRepo.OnRealTimeDbTaskComplete {

    private MutableLiveData<List<CustomerOrderList>> orderMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<DatabaseError> databaseErrorMutableLiveData = new MutableLiveData<>();
    private FirebaseRepo firebaseRepo;

    public MutableLiveData<List<CustomerOrderList>> getOrderMutableLiveData() {
        return orderMutableLiveData;
    }

    public MutableLiveData<DatabaseError> getDatabaseErrorMutableLiveData() {
        return databaseErrorMutableLiveData;
    }

    public FirebaseViewModel(){
        firebaseRepo = new FirebaseRepo(this);
    }

    public void getAllData(){
        firebaseRepo.getAllOrderData();
    }

    @Override
    public void onSuccess(List<CustomerOrderList> customerOrderLists) {
        orderMutableLiveData.setValue(customerOrderLists);
    }

    @Override
    public void onFailure(DatabaseError error) {
        databaseErrorMutableLiveData.setValue(error);
    }
}
