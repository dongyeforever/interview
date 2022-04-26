package vip.lovek.interview.mvvm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * author： yuzhirui@douban.com
 * date: 2022-04-13 17:38
 */
public class MyViewModel extends ViewModel {

    private final MutableLiveData<String> name = new MutableLiveData<>();

    public LiveData<String> getName() {
        return name;
    }

    public void setName() {
        name.setValue("更新数据");
    }

    public void postName() {
        name.postValue("更新数据");
    }

}
