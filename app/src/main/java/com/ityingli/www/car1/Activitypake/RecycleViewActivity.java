package com.ityingli.www.car1.Activitypake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.ityingli.www.car1.Adapter.CarPhotoRecycleViewAdapter;
import com.ityingli.www.car1.ItemDecoration.RecycleViewItemDecoration;
import com.ityingli.www.car1.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    private RecyclerView recycleView_carPhoto;
    private List<String> imgDatas = new ArrayList<>();
    private static  final  String url ="http://image.baidu.com/data/imgs?col=汽车&tag=汽车&sort=0&pn=0&rn=30&p=channel&from=1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_carphoto);
        initView();
        //数据源
        //设置布局管理器
        recycleView_carPhoto.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //添加RecycleView分隔
        recycleView_carPhoto.addItemDecoration(new RecycleViewItemDecoration(4));
        //利用xUtil请求数据
        requestNet_get();
        //适配器
        /*
        * 注意遇到一坑：这里设置适配器中的imgDatas的时候呢，里面的值还是空的，去获取图片的时候其实已经有线程了，这就是一坑B
        * 现在最直接的解决方法就是把适配器放到获取数据的子线程里面去
        * */
        //recycleView_carPhoto.setAdapter(new CarPhotoRecycleViewAdapter(imgDatas, RecycleViewActivity.this));
        recycleView_carPhoto.setAdapter(new CarPhotoRecycleViewAdapter(imgDatas, RecycleViewActivity.this));
    }



    private void requestNet_get() {
        HttpUtils h = new HttpUtils();
        h.send(HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                processData(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
            }
        });
    }

    /*
    * 解释json数据
    * */
    private void processData(String result) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            JSONArray imgsArray =     jsonObject.getJSONArray("imgs");
            for(int i=0;i<30;i++) {
                JSONObject imagObje = (JSONObject) imgsArray.get(i);
                String imgUri = imagObje.getString("imageUrl");
                imgDatas.add(imgUri);
            }
            recycleView_carPhoto.setAdapter(new CarPhotoRecycleViewAdapter(imgDatas, RecycleViewActivity.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void initView() {
        recycleView_carPhoto = (RecyclerView) this.findViewById(R.id.recycleView_carPhoto_id);

    }
}
