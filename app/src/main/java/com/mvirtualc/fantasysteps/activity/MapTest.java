package com.mvirtualc.fantasysteps.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;


import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.PolygonOptions;
import com.mvirtualc.fantasysteps.R;

public class MapTest extends Activity implements OnClickListener {

    private MapView mapView;
    private AMap aMap;
    private Button basicmap;
    private Button rsmap;


    private float latConv;
    private float lngConv;
    private float lat;
    private float lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        //addArc();
        //addArc(new LatLng(30.179382,120.218866),100,180,360);
        addArcTest(new LatLng(30.179382,120.218866),new LatLng(30.180382,120.219866),100);
        //addLine(new LatLng(30.179382,120.218866));
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();

        }
        basicmap = (Button) findViewById(R.id.basicmap);
        basicmap.setOnClickListener(this);
        rsmap = (Button) findViewById(R.id.rsmap);
        rsmap.setOnClickListener(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basicmap:
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模式
                break;
            case R.id.rsmap:
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
                break;
        }

    }

    // point 圆弧中心点(经纬度),redis圆弧半径,start 0度-180度  end 0度-180度 且大于start
    public void addArc(LatLng point, double redis, int start, int end) {
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(point);
        latConv = AMapUtils.calculateLineDistance(point,new LatLng(point.latitude+0.1,point.longitude))*10;
        lngConv = AMapUtils.calculateLineDistance(point,new LatLng(point.latitude,point.longitude+0.1))*10;
        for (int i = start * 10; i <= end * 10; i++) {
            lat = (float) (redis*Math.cos(Math.PI * i * 0.1 / 180)/latConv);
            lng = (float) (redis*Math.sin(Math.PI * i * 0.1 / 180)/lngConv);
            polygonOptions.add(new LatLng(point.latitude + lat, point.longitude + lng));
        }
        polygonOptions.strokeWidth(1) // 多边形的边框
                .strokeColor(Color.argb(50, 1, 1, 1)) // 边框颜色
                .fillColor(Color.RED);// 多边形的填充色
        aMap.addPolygon(polygonOptions);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(point, 13, 0, 0)));
    }

    public void addArcTest(LatLng boss, LatLng player,double redis){
        double x,y,z;
        int start,end;
        x = AMapUtils.calculateLineDistance(boss,new LatLng(player.longitude,boss.latitude));
        y = AMapUtils.calculateLineDistance(player,new LatLng(player.longitude,boss.latitude));
        z = Math.atan(y/x);
        z = 90-z*(180/Math.PI);
        start = (int)(z-30);
        end = (int)(z+30);
        PolygonOptions polygonOptions = new PolygonOptions();
        polygonOptions.add(boss);
        latConv = AMapUtils.calculateLineDistance(boss,new LatLng(boss.latitude+0.1,boss.longitude))*10;
        lngConv = AMapUtils.calculateLineDistance(boss,new LatLng(boss.latitude,boss.longitude+0.1))*10;
        for (int i = start * 10; i <= end * 10; i++) {
            lat = (float) (redis*Math.cos(Math.PI * i * 0.1 / 180)/latConv);
            lng = (float) (redis*Math.sin(Math.PI * i * 0.1 / 180)/lngConv);
            polygonOptions.add(new LatLng(boss.latitude + lat, boss.longitude + lng));
        }
        polygonOptions.strokeWidth(1) // 多边形的边框
                .strokeColor(Color.argb(50, 1, 1, 1)) // 边框颜色
                .fillColor(Color.RED);// 多边形的填充色
        aMap.addPolygon(polygonOptions);
        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(boss, 13, 0, 0)));
    }


}
