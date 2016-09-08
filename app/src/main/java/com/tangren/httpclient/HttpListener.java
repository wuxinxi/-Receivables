package com.tangren.httpclient;

import com.yolanda.nohttp.rest.Response;

public interface HttpListener<T> {

	void success(int what, Response<T> response);

	void fail(int what,  Response<T> response);
}
