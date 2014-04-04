joviansoft-android-framework
============================

Joviansoft-android-core

此模块提供访问restful服务的核心功能。通过对HttpClient的封装，完成对资源的异步请求。

返回的资源数据通过RequestListner进行回调。这是一个泛型监听类，泛型参数为返回数据对象类型，框架核心根据泛型参数，自动将
返回数据反序列化为业务领域对象。


客户端业务逻辑的访问模块，只需引用此模块，提供服务接口访问所需要的参数（用JovianParamenter对象构建参数），
来完成对服务资源的访问。


        String url = GPS_SERVICE_ROOT;
        JovianParameter params = new JovianParameter();
        params.addParam("userid",userId);
        params.addParam("gpsdata", jsonBeanList);
        request(url, params, GpsResponse.class, listener, HttpMethod.POST);

  返回的数据将在Listener中进行回调,回调函数是在UI主线程，可以完成对UI的操作。
  
           @Override
            public void onComplete(GpsResponse gpsResponse) {
               
            }

            @Override
            public void onApiExceptions(JovianException e) {
               
            }
