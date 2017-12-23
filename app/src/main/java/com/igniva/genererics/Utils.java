package com.igniva.genererics;

import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by ignivaandroid23 on 5/9/17.
 */

public class Utils {

    static CallBackPojo callBackPojo=new  CallBackPojo<String>();
    static CallBackPojo callBackPojoType=new  CallBackPojo<PojoType>();


    public interface onCallBack<T> {
        void onSuccess(T t);
        void onFailure(T t);
    }

    public static <T> void callResponse(Activity activity, Collection<T> arrayList, TextView tv_click, Utils.onCallBack onCallBack) {



        for (int i=0;i<arrayList.size();i++)
        {
            if (((ArrayList)arrayList).get(i) instanceof CallBackPojo)
            {
                CallBackPojo callBackPojo=(CallBackPojo) ((ArrayList)arrayList).get(i);
                Toast.makeText(activity, callBackPojo.getValue().toString(), Toast.LENGTH_SHORT).show();
            }
            else
            if (((ArrayList)arrayList).get(i) instanceof PojoType)
            {
                PojoType pojoType=(PojoType) ((ArrayList)arrayList).get(i);
                Toast.makeText(activity, pojoType.getName().toString()+" "+pojoType.getCount()+" "+pojoType.getLocation().toString(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(activity, ((ArrayList)arrayList).get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        }

        if(tv_click.getText().equals("Click Me"))
        {
            callBackPojo.setValue("Clicked");
            onCallBack.onSuccess(callBackPojo);
        }
        else if(tv_click.getText().equals("Clicked"))
        {
            callBackPojo.setValue("Click Me");
            onCallBack.onFailure(callBackPojo);
        }
    }

    public static <T> void summary1(List<T> list){
        // type T is available to code in here
    }

}
