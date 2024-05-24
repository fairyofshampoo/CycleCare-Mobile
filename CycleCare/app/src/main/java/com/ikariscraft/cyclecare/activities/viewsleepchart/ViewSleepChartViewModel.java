package com.ikariscraft.cyclecare.activities.viewsleepchart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ikariscraft.cyclecare.api.RequestStatus;
import com.ikariscraft.cyclecare.api.responses.SleepChartJSONResponse;
import com.ikariscraft.cyclecare.model.SleepHoursInformation;
import com.ikariscraft.cyclecare.repository.ChartRepository;
import com.ikariscraft.cyclecare.repository.IEmptyProcessListener;
import com.ikariscraft.cyclecare.repository.IProcessStatusListener;
import com.ikariscraft.cyclecare.repository.ProcessErrorCodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewSleepChartViewModel extends ViewModel {

    private final MutableLiveData<RequestStatus> sleepChartRequestStatus = new MutableLiveData<>();
    private final MutableLiveData<List<SleepHoursInformation>> sleepHoursLiveData = new MutableLiveData<>();

    public ViewSleepChartViewModel(){

    }

    public LiveData<RequestStatus> getSleepChartRequestStatus() {return sleepChartRequestStatus;}

    public LiveData<List<SleepHoursInformation>> getSleepHours() {return sleepHoursLiveData;}

    public void sleepHoursChart(String token){
        sleepChartRequestStatus.setValue(RequestStatus.LOADING);

       new ChartRepository().ObtainSleppStadistics(
               token,
               new IProcessStatusListener() {
                   @Override
                   public void onSuccess(Object data) {
                       List<SleepHoursInformation> sleepHours = (List<SleepHoursInformation>) data;
                       sleepHoursLiveData.setValue(sleepHours);
                       sleepChartRequestStatus.setValue(RequestStatus.DONE);
                   }

                   @Override
                   public void onError(ProcessErrorCodes errorCode) {
                       sleepHoursLiveData.setValue(null);
                       sleepChartRequestStatus.setValue(RequestStatus.ERROR);
                   }
               }
       );
    }

}
