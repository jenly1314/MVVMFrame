package com.king.mvvmframe.app.oil;


import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.bean.Resource;
import com.king.frame.mvvmframe.data.IDataRepository;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.king.mvvmframe.api.ApiService;
import com.king.mvvmframe.app.Constants;
import com.king.mvvmframe.bean.OilPrice;
import com.king.mvvmframe.bean.Result;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;

/**
 * 标准版
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class OilPriceModel extends BaseModel {

    private MutableLiveData<Resource<List<OilPrice>>> oilLiveData = new MutableLiveData<>();

    @Inject
    public OilPriceModel(IDataRepository dataRepository) {
        super(dataRepository);
    }

    /**
     * 查询油价信息
     * @return
     */
    public LiveData<Resource<List<OilPrice>>> getOilPriceInfo(){
        oilLiveData.setValue(Resource.loading());
        getRetrofitService(ApiService.class)
                .getOilPriceInfo(Constants.OIL_PRICE_KEY)
                .enqueue(new ApiCallback<Result<List<OilPrice>>>() {
                    @Override
                    public void onResponse(Call<Result<List<OilPrice>>> call, Result<List<OilPrice>> result) {
                        if (result != null) {
                            if(result.isSuccess()){
                                oilLiveData.postValue(Resource.success(result.getData()));
                                return;
                            }
                            oilLiveData.postValue(Resource.failure(result.getMessage()));
                        }
                        oilLiveData.postValue(Resource.failure(null));
                    }

                    @Override
                    public void onError(Call<Result<List<OilPrice>>> call, Throwable t) {
                        oilLiveData.postValue(Resource.error(t));
                    }
                });

        return oilLiveData;
    }
}
